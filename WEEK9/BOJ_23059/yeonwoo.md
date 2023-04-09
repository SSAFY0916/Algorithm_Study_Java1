![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%20N&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 23059 리그오브레게노](https://www.acmicpc.net/problem/23059)

<br>
<br>

# **Code**

```java
import java.io.*;
import java.util.*;

/* Topological Sort

#topological sort 재료
- 인접리스트로 만든 그래프
    Map<String,ArrayList<String>>
- 노드별 차수
    Map<String,Integer>

#문제에만 특별히 적용되는 부분
- 동일위상 정점에 대해 사전순정렬
    Node클래스 만들어서 Comparable 구현(출력순서가 같으면 사전순 우선순위 갖도록 함)

1. static class Node(노드이름,출력우선순위) 생성
- Comparable 구현: 출력우선순위 오름차순 > 노드이름 오름차순

2. 관계의 수 N 입력받음

3. N번 반복하며 그래프(Map<String,ArrayList<String>>)와 차수테이블(Map<String,Integer>) 작성
    | 그래프 작성
    1. lowItem = 하위템, highItem = 상위템
    2. 그래프에 lowItem, highItem 없으면 넣어줌(value=인접정점= new ArrayList<String>)
    3. lowItem의 인접정점으로 highItem 넣어줌

    | 차수테이블작성
    1. 차수테이블에 lowItem,highItem 없으면 넣어줌(value = 진입차수 = 0)
    2. highItem의 차수+=1
4. 위상정렬 실시
    1. q만듦(bfs용)
    2. pq만듦(출력시 사전순 출력용)

    3. q에 진입차수가 0인애들 new Node(노드이름=아이템명, 우선순위=0)로 넣어줌
    4. bfs
        1. q에서 뽑아서(cur)
        2. pq에 넣는다 // 출력우선순위는 아래 인접정점 순회에서 세팅
        3. q.아이템명으로 인접 정점(I) 찾아서 순회
            1. I의 차수를 1 줄이고
            2. I의 차수가 0이면
            3. q.offer(new Node(I.아이템명, cur+1)
    5. 싸이클이 있다면 pq.size()와 그래프.size()가 달라진다
        - 이유: 싸이클이 생기면, 해당부분에서 차수가 0이 안되면서 q에 공급이 끊긴다
        - **0이 안되는 원리 설명??**
    6. pq.size < 그래프.size()면 -1출력(문제조건임)
    - 아니면 pq에서 뽑으면서 출력

 */

public class Main{
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static HashMap<String,ArrayList<String>> map = new HashMap<>();
    static HashMap<String,Integer> indegree = new HashMap<>();

    static int N;

    public static void main(String[] args) throws IOException{
        N = Integer.parseInt(br.readLine());//관계 갯수

        //그래프, inner차수 계산
        for(int i=0;i<N;i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            String lowItem = st.nextToken();
            String highItem = st.nextToken();

            //그래프
            map.putIfAbsent(lowItem,new ArrayList<>());
            map.putIfAbsent(highItem,new ArrayList<>());
            map.get(lowItem).add(highItem);

            //차수
            indegree.putIfAbsent(lowItem,0);
            indegree.putIfAbsent(highItem,0);

            //상위템 차수 증가
            indegree.put(highItem,indegree.get(highItem)+1);
        }

        //시작노드 입력
        topologicalSort();

    }
    static void topologicalSort(){
        Queue<Node> q = new ArrayDeque<>();//BFS를 위한 큐
        PriorityQueue<Node> result = new PriorityQueue<>();//결과를 저장할 우선순위 큐

        //초기 진입차수가 0인 노드를 큐에 삽입
        for(String key:indegree.keySet()){
            int curDegree = indegree.get(key);
            if(curDegree==0) q.offer(new Node(key,0));
        }

        while(!q.isEmpty()){
            Node cur = q.poll();
            result.offer(cur);//Node cur에는 priority가 반영되어있음

            //cur의 인접 노드 차수 감소, 0이면 큐에 삽입
            for(String key:map.get(cur.key)){
                indegree.put(key,indegree.get(key)-1);
                if(indegree.get(key)==0){
                    q.offer(new Node(key,cur.priority+1));//출력우선순위를 여기서 올려준다
                }

            }
        }

        if(result.size() < map.size()){
            System.out.println(-1);
            return;
        }
        StringBuilder sb = new StringBuilder();
        while(!result.isEmpty()){
            sb.append(result.poll().key+"\n");
        }
        System.out.println(sb.toString());
    }

    static class Node implements Comparable<Node>{
        String key;
        int priority;

        public Node(String key, int degree){
            this.key = key;
            this.priority = degree;
        }

        public int compareTo(Node o){
            if(priority==o.priority){
                return key.compareTo(o.key);
            }
            return priority-o.priority;
        }
    }
}
```

<br>
<br>

# **🔑Description**
- 

- ```
  /* Topological Sort
  
  #topological sort 재료
  - 인접리스트로 만든 그래프
      Map<String,ArrayList<String>>
  - 노드별 차수
      Map<String,Integer>
  
  #문제에만 특별히 적용되는 부분
  - 동일위상 정점에 대해 사전순정렬
      Node클래스 만들어서 Comparable 구현(출력순서가 같으면 사전순 우선순위 갖도록 함)
  
  1. static class Node(노드이름,출력우선순위) 생성
  - Comparable 구현: 출력우선순위 오름차순 > 노드이름 오름차순
  
  2. 관계의 수 N 입력받음
  
  3. N번 반복하며 그래프(Map<String,ArrayList<String>>)와 차수테이블(Map<String,Integer>) 작성
      | 그래프 작성
      1. lowItem = 하위템, highItem = 상위템
      2. 그래프에 lowItem, highItem 없으면 넣어줌(value=인접정점= new ArrayList<String>)
      3. lowItem의 인접정점으로 highItem 넣어줌
  
      | 차수테이블작성
      1. 차수테이블에 lowItem,highItem 없으면 넣어줌(value = 진입차수 = 0)
      2. highItem의 차수+=1
  4. 위상정렬 실시
      1. q만듦(bfs용)
      2. pq만듦(출력시 사전순 출력용)
  
      3. q에 진입차수가 0인애들 new Node(노드이름=아이템명, 우선순위=0)로 넣어줌
      4. bfs
          1. q에서 뽑아서(cur)
          2. pq에 넣는다 // 출력우선순위는 아래 인접정점 순회에서 세팅
          3. q.아이템명으로 인접 정점(I) 찾아서 순회
              1. I의 차수를 1 줄이고
              2. I의 차수가 0이면
              3. q.offer(new Node(I.아이템명, cur+1)
      5. 싸이클이 있다면 pq.size()와 그래프.size()가 달라진다
          - 이유: 싸이클이 생기면, 해당부분에서 차수가 0이 안되면서 q에 공급이 끊긴다
          - **0이 안되는 원리 설명??**
      6. pq.size < 그래프.size()면 -1출력(문제조건임)
      - 아니면 pq에서 뽑으면서 출력
  
   */
  ```

<br>
<br>

# **📑Related Issues**

> 사이클이 생기면 왜 들어오는 차수가 0이 안될까?
>
> 

<br>
<br>

# **🕛Resource**

| Memory   | Time   | Implementation Time |
| -------- | ------ | ------------------- |
| 192720KB | 2700ms |                     |
