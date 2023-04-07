![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%205972&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> BOJ 5972 택배배송

# 💻**Code**

```java
import java.io.*;
import java.util.*;
/*
 * 최소한의 소를 만나고 싶다
 * 지도 50000개 헛간, 길 M 50000 양방향 길
 * 각 길에 C마리 소가 있다. 
 * 소들은 A 와 B를 잇는다
 * 두개 헛간 -> 하나 이상 길로 연결되어 있을 수 있다. 
 * 현서 - 1 찬홍 - N
 * */

public class Main {
	static class Node implements Comparable<Node> {
		int index;
		int cost;
		
		public Node(int index, int cost) {
			this.index = index;
			this.cost = cost;
		}

		@Override
		public String toString() {
			return "Link [index=" + index + ", cost=" + cost + "]";
		}
		
		@Override
		public int compareTo(Node o) {
			return this.cost - o.cost;			
		}// 비용으로 오름차순 정렬
	}
	static int N,M;
	static boolean check[];// 각 노드 방문 여부 관리
	static int dist[];// start ~ i까지 최소비용 저장 
	static List<Node> edge[];
	static int INF = Integer.MAX_VALUE;
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		st = new StringTokenizer(in.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		dist = new int[N+1];
		check = new boolean[N+1];
		edge = new ArrayList[N+1];
		for(int i=0;i<=N;i++) {
			edge[i] = new ArrayList<Node>();
		}
		int from, to, cost;
		for(int i=0;i<M;i++) {
			st = new StringTokenizer(in.readLine());
			from = Integer.parseInt(st.nextToken());
			to = Integer.parseInt(st.nextToken());
			cost = Integer.parseInt(st.nextToken());
			edge[from].add(new Node(to, cost));
			edge[to].add(new Node(from, cost));
		}
		
		// dist 최소 비용 저장 배열 최대값으로 초기화
		Arrays.fill(dist, INF);
		dist[1] = 0; //시작 노드 1 비용 0으로 초기화
		
		// 가장 비용이 작은, 방문하지 않은 노드를 선택하기 위해 PQ사용 
		PriorityQueue<Node> pq = new PriorityQueue<>();
		pq.offer(new Node(1,0));
		while(!pq.isEmpty()){
			int nowVertex = pq.poll().index;
			
			// 방문한 노드라면 건너뜀
			if(check[nowVertex]) continue;
			check[nowVertex] = true;
			
			// 방문하지 않은 노드라면 방문 후, 인접 노드 확인
			// dist[i] -> 시작 정점에서 i번 정점까지 가는 최소비용 저장(어떤 경로를 지났는지 상관X)
			for(Node next: edge[nowVertex]) {
				if(dist[next.index] > dist[nowVertex] + next.cost) {// 최소비용 갱신
					dist[next.index] = dist[nowVertex] + next.cost;
					pq.offer(new Node(next.index, dist[next.index]));// 같은 노드로 가는 여러 경로가 있어도 최소 비용을 먼저 탐색
				}
			}
		}// 모든 정점 방문할때까지 진행
		
        //시작점 1번에서 N번으로 가는 최소거리 출력
		System.out.println(dist[N]);
	}

}

```

# **🔑Description**

> 1번에서 N번으로 도착하는 최소한의 비용을 계산하는 것이었는데
>
> N과 edge개수가 너무 많아서 우선순위 큐를 이용한 다익스트라로 풀이했습니다.
>
> -  우선순위 큐 사용할 경우 - O(ElogV) 
>
> - 선형 탐색으로 최단거리가 짧은 노드를 찾고 탐색할 경우 - O(V^2)
>
> 시작 정점과 도착정점이 1, N으로 정해져있어서 고려할 사항이 더 적었던 것 같습니다.  

# **📑Related Issues**

>플로이드 워셜도 고려했지만 N이 너무 커서 불가능함을 판단했습니다.
>
>N - 50000개, 간선 50000개 다익스트라 문제인데 N edge가 크다보니 우선순위 걱정이 앞섰던거 같습니다.
>
>

# **🕛Resource**

| Memory    | Time    |
| --------- | ------- |
| 39608`KB` | 432`ms` |