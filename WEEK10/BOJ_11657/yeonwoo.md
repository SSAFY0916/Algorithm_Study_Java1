![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2011657&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 11657 타임머신](https://www.acmicpc.net/problem/11657)

<br>
<br>

# **Code**

```java
import java.io.*;
import java.util.StringTokenizer;
import java.util.ArrayList;

/* 벨만포드
벨만포드 기본재료
    class Node(정점, 정점까지 거리) 
    List<List<Node>> == 인접리스트 == 지도
    dist[N+1] == 1~N번 노드까지의 거리 (INF로 초기화, 출발점은 0으로 초기화)
    
벨만포드 메모
    N-1번 모든 간선을 업데이트한다
        왜냐면 아무리 멀리있는 노드도 N-1번 이동하면 도착한다
        최단거리 찾는데 N번이상걸리는 순간 사이클이 있는 것임!
        
    N-1번 돌면서 업데이트 되는거 확인
    N-1번까지 안돌았어도 업데이트 없으면 그냥 break하고 끝내면됨
    N-1번째까지 업뎃됐으면 한버 더 돌아서 사이클 확인해야함(위에 참조)
        한번 더 돌았는데 또 업데이트면 사이클 있다고하고 리턴
        
    여까지 정상 도착시 dist[i] == 출발점에서 i번 노드까지 거리임

 */
public class Main{
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer st;


    static int N; //정점 수
    static int M; //간선 수
    static ArrayList<Node>[] map;

    static long[] dist;//각 노드까지 거리
    static int INF = Integer.MAX_VALUE;

    static class Node{
        int end;
        int weight;

        public Node(int end, int weight){
            this.end = end;
            this.weight = weight;
        }
    }

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());//정점
        M = Integer.parseInt(st.nextToken());//간선

        //노드까지 거리
        dist = new long[N+1];
        for(int i=1;i<=N;i++){
            dist[i] = INF;
        }

        //인접리스트
        map = new ArrayList[N+1];//1번부터 N번
        for(int i=1;i<=N;i++){
            map[i] = new ArrayList<>();
        }

        //정점별 인접노드 배치
        for(int i=0;i<M;i++){
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());

            map[start].add(new Node(end,weight));
        }

        //벨만포도
        int startNode = 1;//이 문제에서는 1에서 출발로 고정
        boolean isCycle = bellmanFord(startNode);//음싸이클존재시 true

        //결과세팅
        StringBuilder sb = new StringBuilder();
        if(isCycle){
            sb.append("-1");
        }else{
            for(int i=1;i<=N;i++){
                if(i==startNode) continue;
                if(dist[i]==INF){
                    sb.append("-1\n");
                }else{
                    sb.append(dist[i]+"\n");////각 노드까지의 거리
                }

            }
        }
        bw.write(sb.toString());
        bw.close();//출력
    }

    static boolean bellmanFord(int s){
        //시작노드까지의 거리는 0
        dist[s] = 0;
        /*
        (a노드 -> b노드) 최대 n-1번의 길을 건넘
        그 이상 건넌다면 음사이클 존재를 나타냄
         */
        boolean change = false;
        for(int i=0;i<N-1;i++){

            change = false;
            //모든 정점 순회하면서 이어진 노드와의 거리 설정
            for(int j=1;j<=N;j++){
                long curDist = dist[j];
                if(curDist==INF) continue;//아직 닿지 않은 노드

                ArrayList<Node> nodeList = map[j];//j번노드의 간선들
                for(Node n:nodeList){
                    int end = n.end;//인접노드번호
                    int weight = n.weight;//이번 간선의 가중치


                    if(dist[end] > curDist + weight){
                        dist[end] = curDist + weight;
                        change = true;
                    }

                }
            }
            if(!change) break;//업뎃 없으면 더 돌아도 의미 x
        }
        //마지막 순회까지 change있었으면 함더 돌면서 음의 사이클 탐색
        if(change){
            //모든 정점 순회하면서 이어진 노드와의 거리 설정
            for(int j=1;j<=N;j++){
                long curDist = dist[j];
                if(curDist==INF) continue;//아직 닿지 않은 노드

                ArrayList<Node> nodeList = map[j];//j번노드의 간선들
                for(Node n:nodeList){
                    int end = n.end;//인접노드번호
                    int weight = n.weight;//이번 간선의 가중치


                    if(dist[end] > curDist + weight){
                        return true;//음의사이클 존재
                    }
                }
            }
        }
        return false;
    }
}
```

<br>
<br>

# **🔑Description**
- 

- ```
  /* 벨만포드
  벨만포드 기본재료
      class Node(정점, 정점까지 거리)
      List<List<Node>> == 인접리스트 == 지도
      dist[N+1] == 1~N번 노드까지의 거리 (INF로 초기화, 출발점은 0으로 초기화)
  
  벨만포드 메모
      N-1번 모든 간선을 업데이트한다
          왜냐면 아무리 멀리있는 노드도 N-1번 이동하면 도착한다
          최단거리 찾는데 N번이상걸리는 순간 음사이클이 있는 것임!
  
      N-1번 돌면서 업데이트 되는거 확인
      N-1번까지 안돌았어도 업데이트 없으면 그냥 break하고 끝내면됨
      N-1번째까지 업뎃됐으면 한버 더 돌아서 음사이클 확인해야함(위에 참조)
          한번 더 돌았는데 또 업데이트면 음사이클 있다고하고 리턴
  
      여까지 정상 도착시 dist[i] == 출발점에서 i번 노드까지 거리임
  
   */
  ```

<br>
<br>

# **📑Related Issues**

> 
>
> 

<br>
<br>

# **🕛Resource**

| Memory  | Time  | Implementation Time |
| ------- | ----- | ------------------- |
| 26176KB | 300ms |                     |
