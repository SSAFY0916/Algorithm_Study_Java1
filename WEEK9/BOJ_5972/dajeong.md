![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%205972&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 5972 택배배송](https://www.acmicpc.net/problem/5972)

<br>
<br>

# **Code**

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {

    static int N,M;
    static final int INF = Integer.MAX_VALUE;
    static ArrayList<Edge>[] adjList;
    static int[] dist;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken()); // 헛간 수
        M = Integer.parseInt(st.nextToken()); // 간선 수(소 길)

        adjList = new ArrayList[N+1]; // 1-indexed
        // 인접리스트 초기화
        for (int i = 1; i <= N; i++) {
            adjList[i] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            // 무방향
            adjList[a].add(new Edge(b,c));
            adjList[b].add(new Edge(a,c));
        }

        // 최단거리 저장 배열
        dist = new int[N+1];
        Arrays.fill(dist, INF);

        dijkstra(1); // 시작 정점 1인 다익스트라 알고리즘 수행

        System.out.println(dist[N]); // 목적지 N번 정점까지의 최단 경로 출력
    }

    private static void dijkstra(int start) {
        // PQ 세팅 및 처음 정점 넣기
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        dist[start] = 0;
        pq.offer(new Edge(start, dist[start]));

        while(!pq.isEmpty()) {
            Edge cur = pq.poll();
            if (dist[cur.v] != cur.w) continue;
            for (Edge nxt : adjList[cur.v]) {
                if (dist[nxt.v] <= dist[cur.v] + nxt.w) continue;
                dist[nxt.v] = dist[cur.v] + nxt.w;
                pq.offer(new Edge(nxt.v, dist[nxt.v]));
            }

        }

    }

    private static class Edge implements Comparable<Edge>{
        int v; // 정점 번호
        int w; // 비용

        public Edge(int v, int w) {
            this.v = v;
            this.w = w;
        }

        @Override
        public int compareTo(Edge o) {
            return Integer.compare(this.w, o.w);
        }
    }
}
```

<br>
<br>

# **🔑Description**

> 설계 시간: 40min (다익스트라 복습)

> 구현 시간: 20min
<aside>
💡 설계 아이디어

    다익스트라 (최단경로) 기본 문제

</aside>

<br>
<br>

# **📑Related Issues**

> Related Issues
<aside>

    오랜만에 다익스트라를 구현하다보니 .. 구현 방식이 잘 생각이 안났다
    다시 복습하면서 더듬더듬 풀었는데 돌아오는 주에 mst, 플로이드, 다익스트라 싹 다 정리 좀 해봐야겠다
    

</aside>

<br>
<br>

# **🕛Resource**

| Memory | Time  | Implementation Time |
| -- |-------|---------------------|
| 39684KB | 444ms | 1 Hour  |
