import java.util.*;
import java.io.*;

public class Main {
	static class Node implements Comparable<Node> {
		int index;
		long cost;
		int state;// 늑대 - 빠르게, 느리게 설정
		public Node(int index, long cost) {//여우전용
			this.index = index;
			this.cost = cost;
		}
		public Node(int index, long cost, int state) {//늑대전용
			this.index = index;
			this.cost = cost;
			this.state = state;
		}
		@Override
		public int compareTo(Node o) {
			return (int) (this.cost - o.cost);
		}
	}
	
	static int N, M, count;
	static List<Node> graph[];
	static long foxDist[], wolfDist[][];
	static long INF = 4000 * 100000 * 3;
	
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		st = new StringTokenizer(in.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
        
		foxDist = new long[N+1];
		wolfDist = new long[2][N+1];// 0-빠르게 도착했을 때 최소시간, 1-느리게 도착했을때 최소시간
		
        graph = new ArrayList[N+1];
		for(int i=0;i<=N;i++) {
			graph[i] = new ArrayList<Node>();
		}
        
		int from, to, cost;
		for(int i=0;i<M;i++) {
			st = new StringTokenizer(in.readLine());
			from = Integer.parseInt(st.nextToken());
			to = Integer.parseInt(st.nextToken());
			cost = Integer.parseInt(st.nextToken());
			cost *= 2;// 늑대에서 /2 연산시 소수점이 나오지 않게 하기 위해
			graph[from].add(new Node(to, cost));
			graph[to].add(new Node(from, cost));
		}
		PriorityQueue<Node> pq = new PriorityQueue<>();
		// 1. 여우 최단경로 다익스트라로 거리 구하기
		Arrays.fill(foxDist, INF);
		foxDist[1] = 0;
		pq.offer(new Node(1,0));
		while(!pq.isEmpty()) {
			long currCost = pq.peek().cost;
			int curr = pq.poll().index;
			
			if(foxDist[curr] < currCost) continue;
			
			for(Node next: graph[curr]) {
				if(foxDist[next.index] > foxDist[curr] + next.cost) {
					foxDist[next.index] = foxDist[curr] + next.cost;
					pq.offer(new Node(next.index, foxDist[next.index]));
				}
			}
		}
        
		// 2. 늑대 최단 경로, 홀수번 이동 - 2배 빠르게(cost/2), 짝수번 이동 - 2배 느리게(cost*2)
		// cost/2 -> 소수점 나오는데 정수로 하고 싶다. -> (처음 비용을 * 2)
		Arrays.fill(wolfDist[0], INF);
		Arrays.fill(wolfDist[1], INF);
		wolfDist[0][1] = 0;
		pq.offer(new Node(1,0,0)); // state 0: 빠르게, state 1: 느리게
		while(!pq.isEmpty()) {
			int currState = pq.peek().state;
			long currCost = pq.peek().cost;
			int curr = pq.poll().index;
			
			if(wolfDist[currState][curr] < currCost) continue;
			
			for(Node next: graph[curr]) {
				int nextState = 1 - currState;// 빠르게(0) <-> 느리게(1) switch 
				long tmpCost = wolfDist[currState][curr] + (currState==0 ? next.cost/2 : next.cost*2);
				if(wolfDist[nextState][next.index] > tmpCost) {
					wolfDist[nextState][next.index] = tmpCost;
					pq.offer(new Node(next.index, tmpCost, nextState));
				}
			}
		}
		
		for(int i=2;i<=N;i++) {
			if(foxDist[i] < Math.min(wolfDist[0][i], wolfDist[1][i])) {
				count++;
			}
		}
		System.out.println(count);
	}
}
/*
 * 시간초과 -> 중간 가지치기 필요
 * */
