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
