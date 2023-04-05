![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%201939&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 1939 중량제한](https://www.acmicpc.net/problem/1939)

<br>
<br>

# **Code**

```java
//package res;

import java.io.*;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/*
크루스칼
가중치 내림차순 간선 뽑
간선의 s와 e를 잇는다
sv와 ev가 이어지면 현재 간선의 weight가 답이다
*/


public class Main{
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static class Edge implements Comparable<Edge>{
        int s,e,w;//출발 도착 가중

        public Edge(int s, int e, int w){
            this.s = s;
            this.e = e;
            this.w = w;
        }

        //가중치 기준 내림차순
        public int compareTo(Edge o){
            return o.w-w;
        }
    }
    static int N;//노드 수
    static int M;//간선 수
    static int sv,ev;//시작노드번호 도착노드번호

    //유니온파인드
    static int[] parent;

    public static void main(String[] args) throws IOException{
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());//node
        M = Integer.parseInt(st.nextToken());//edge

        //Prepare the ingredients for the kruskal
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        for(int i=0;i<M;i++){
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());

            Edge edge = new Edge(s,e,w);
            pq.offer(edge);
        }

        //For UnionFind
        parent = new int[N+1];//Node numbers start at 1
        //First, register itself as its parent
        for(int i=1;i<=N;i++){
            parent[i] = i;
        }

        //starting & arrival node number
        st = new StringTokenizer(br.readLine());
        sv = Integer.parseInt(st.nextToken());
        ev = Integer.parseInt(st.nextToken());

        int res = kruskal(pq);
        System.out.println(res);

    }
    static int kruskal(PriorityQueue<Edge> pq){
        while(!pq.isEmpty()){
            Edge cur = pq.poll();
            int curs = cur.s;
            int cure = cur.e;
            int curw = cur.w;

            unionFind(curs,cure);
            if(find(sv)==find(ev)){
                return curw;
            }
        }
        return -1;//It's never going to here
    }

    static void unionFind(int numA, int numB){
        int pA = find(numA);
        int pB = find(numB);
        //return if numA and numB have the same parent.
        if(pA ==pB) return;

        union(pA,pB);
    }

    static int find(int num){
        //return if num and parent[num] have the same number.
        if(num==parent[num]) return num;
        return parent[num] = find(parent[num]);
    }

    static void union(int pA,int pB){
        if(pA<pB){
            parent[pB] = pA;
        }else{
            parent[pA] = pB;
        }
    }



}
```

<br>
<br>

# **🔑Description**
- 크루스칼 알고리즘을 이용한 최소신장트리 만들기를 응용하였습니다

  - Comparable을 구현하는 Edge 클래스를 만들고
  - 가중치 기준 오름차순이 아닌 내림차순으로 정렬되도록 하였습니다

- 가중치가 큰 간선부터 뽑는다

  - 집합이 다른 노드를 연결하는 간선일 때만 두 노드에 대해 union
  - union이후 출발지와 도착지가 같은 그룹이지 확인하고 같은 그룹이면 이번 간선의 가중치를 리턴합니다
    - 왜냐면 가중치 기준 내림차순 정렬이기 때문에 출발지-도착지를 잇는 순간의 가중치가 모든 다리 중 가장 약한 다리이기 때문입니다.

  ---

  

  

<br>
<br>

# **📑Related Issues**

> union이후 문제의출발지와 문제의도착지가 같은 집합인지 확인해야하는데
>
> 현재뽑은 노드의 출발지와 도착지가 같은 집합인지 확인해 틀렸습니다.

<br>
<br>

# **🕛Resource**

| Memory  | Time  | Implementation Time |
| ------- | ----- | ------------------- |
| 47476KB | 444ms |                     |





---

# 이분탐색

# **Code**

```java
package bj_1939;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;
import java.util.ArrayList;
/*
섬은 1번부터 시작

1. static class Edge(end, weight) 생성
2. 인접리스트로 노드간 연결 표현
    -하면서 최대하중(max) 확인
3. 최소하중(1) ~ 최대하중(max)까지 binarySearch하면서 통과 확인
    - 통과하면 mid+1 ~ right 까지 binarySearch
    - 통과 못하면 left~mid-1 binarySearch
4. 통과확인 startNode에서
binarySearch상 현재 하중에 대해
현재하중 이상의 하중을 가진 간선만 가지고 bfs

 */
public class Bj_1939 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static class Edge{
        int end,weight;
        public Edge(int end, int weight){
            this.end = end;
            this.weight = weight;
        }
    }

    static int N;//정점의 수(1번부터 N번까지 존재)
    static int M;//간선의 수

    static ArrayList<Edge>[] vertexList;//정점 인접리스트

    static int startV,endV;//출발정점, 도착정점
    static int max = 0;//최대하중
    public static void main(String[] args) throws IOException{
        //N,M 세팅
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());


        //정점 생성
        vertexList = new ArrayList[N+1];
        for(int i=1;i<=N;i++){
            vertexList[i] = new ArrayList<>();
        }

        //간선 생성
        for(int i=0;i<M;i++){
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());

            //양방향
            vertexList[s].add(new Edge(e,w));
            vertexList[e].add(new Edge(s,w));

            max = Math.max(max,w);//최대하중 체크
        }

        //출발정점, 도착정점
        st = new StringTokenizer(br.readLine());
        startV = Integer.parseInt(st.nextToken());
        endV = Integer.parseInt(st.nextToken());

        System.out.println(binarySearch(1,max));
    }

    //최대하중 탐색
    static int binarySearch(int low,int high){
        int mid;
        int res=0;
        while(low<=high){
            mid = low+(high-low)/2;
            if(canGo(mid)){//mid가 최대하중일때 이동 가능한지 판단
                res = mid;
                low=mid+1;
            }else{
                high=mid-1;
            }
        }
        return res;
    }

    //maxWeight가 최대하중일때 startV에서 endV까지 갈 수 있냐
    static boolean canGo(int curWeight){
        Queue<Integer> q = new ArrayDeque<>();
        boolean[] visited = new boolean[N+1];

        q.offer(startV);
        visited[startV] = true;

        while(!q.isEmpty()){
            int cur = q.poll();//현재 노드 번호
            if(cur==endV) return true;//endV에 도달하다

            for(Edge edge:vertexList[cur]){//현재 노드의 간선들 탐색
                if(visited[edge.end]) continue;//방문초과
                if(edge.weight<curWeight) continue;//curWeight가 현재 간선의 하중을 초과

                visited[edge.end] = true;
                q.offer(edge.end);

            }
        }
        return false;
    }
}
```

<br>
<br>

# **🔑Description**

- 섬은 1번부터 시작

  1. static class Edge(end, weight) 생성
  2. 인접리스트로 노드간 연결 표현
     - 하면서 최대하중(max) 확인
  3. 최소하중(1) ~ 최대하중(max)까지 binarySearch하면서 통과 확인
     - 통과하면 mid+1 ~ right 까지 binarySearch
     - 통과 못하면 left~mid-1 binarySearch
     - 통과확인 startNode에서 binarySearch상 현재 하중에 대해 현재하중 이상의 하중을 가진 간선만 가지고 bfs

- 

  ---

  

  

<br>
<br>

# **📑Related Issues**

> if(edge.weight<curWeight) continue;//curWeight가 현재 간선의 하중을 초과 
>
> 
>
> 다리의하중이 현재무게보다 낮으면 continue 해야하는데 반대로 표기하였음 
>
> 원인: 변수명의 모호함, 풀이를 외워서

<br>
<br>

# **🕛Resource**

| Memory  | Time  | Implementation Time |
| ------- | ----- | ------------------- |
| 61696KB | 624ms |                     |

