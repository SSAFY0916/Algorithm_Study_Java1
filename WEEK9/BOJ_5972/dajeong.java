import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_5972_택배배송 {

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
