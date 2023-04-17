![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2011657&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> BOJ 11657 타임머신

# 💻**Code**

```java
import java.util.*;
import java.io.*;
/* [벨만 포드]
 * dist 출발지로부터 최단거리 저장
 * 출발지는 0 나머지는 INF로 초기화
 * 1. 간선 m개  하나씩 모두 살펴본다
 *  현재 가중치 cost v, w -> v에서 W로 가는 비용
 *  dist[v] - 현재까지 V까지 최소거리
 *  dist[v] 무한대가 아니면 -> 2 진행
 * 2. dist[v] = min (dist[v], dist[w] + cost(w, v) )
 * */

public class Main {
	static class Edge {
		int from;
		int to;
		int cost;
		
		public Edge(int from, int to, int cost) {
			super();
			this.from = from;
			this.to = to;
			this.cost = cost;
		}
	}
	static int N, M;
	static long dist[];
	static List<Edge> edgeList;
	
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		StringBuilder sb = new StringBuilder();
		st = new StringTokenizer(in.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		edgeList = new ArrayList<>();

		dist = new long[N+1];
		Arrays.fill(dist, Long.MAX_VALUE);
		dist[1] = 0; // 거리 배열 초기화, 시작정점 1번
		
		for(int i=0;i<M;i++) {
			st = new StringTokenizer(in.readLine());
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			edgeList.add(new Edge(from, to, cost));
		} // 간선 리스트 추가
		
		for(int i=1;i<=N;i++) {
			for(Edge edge: edgeList) { // 모든 간선을 확인
                if(dist[edge.from] == Long.MAX_VALUE) continue;
				
				if(dist[edge.to] > dist[edge.from] + edge.cost) {//거리 갱신
					dist[edge.to] = dist[edge.from]+edge.cost;
					if(i == N) {//음의 사이클이 존재 
						System.out.println("-1");
						return;
					}
				}
			}
		}
		
		for(int i=2;i<=N;i++) {
			if(dist[i] == Long.MAX_VALUE) {// 경로가 없다면
				System.out.println("-1");
			}else {// 경로가 있다면
				System.out.println(dist[i]);
			}
		}
	}
}
/*
 * 1번째 시도 - 플로이드 워셜
 *  -> 음의 싸이클 판단 가능 -> 0으로 초기화 해준 부분 -> -가 되면 음의 사이클 존재
 *  -> 
 * 2번째 시도 - 벨만 포드 
 * 	음의 가중치를 가진 간선 -> 벨만 포드 or 플로이드 워셜 
 * 	출발지가 하나의 정점.
 * 	
 * */

```

# **🔑Description**

> 음의 가중치를 가진 간선이어서 벨만포드와 플로이드워셜을 떠올렸습니다.
>
> 벨만포드 알고리즘으로 탐색하다가 음의 사이클이 발견되면, -1 출력 후 리턴하도록 했습니다.
>
> O(N M) 

# **📑Related Issues**

> 플로이드 워셜로 하려고 했으나 잘 안되어 벨만 포드로 풀이했습니다.
>

# **🕛Resource**

| Memory    | Time    |
| --------- | ------- |
| 19404`KB` | 340`ms` |