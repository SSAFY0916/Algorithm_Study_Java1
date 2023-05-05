import java.io.*;
import java.util.*;

public class Main {
	static int N, M, cost[];
	static long K;
	static int graph[][];
	static int parent[];
	static HashSet<Integer> freindSet;
	
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		st = new StringTokenizer(in.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Long.parseLong(st.nextToken());
		cost = new int[N+1];
		freindSet = new HashSet<>();
		parent = new int[N+1];
		for(int i=1;i<=N;i++) {
			parent[i] = i; // 각 부모 초기화
		}
		
		st = new StringTokenizer(in.readLine());
		for(int i=1;i<=N;i++) {
			cost[i] = Integer.parseInt(st.nextToken());
		}
		
		
		for(int i=0;i<M;i++) {
			st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
		
			union(a,b);
		}
		
		long totalCost = 0;		
		for(int i=1;i<=N;i++) {
			if(parent[i] == i) {
				totalCost += cost[i];
			}
		}
		

		if(totalCost <= K) {
			System.out.println(totalCost);
		}else {
			System.out.println("Oh no");
		}
		
	}
	
	private static void union(int A, int B) {
		// 비용이 적은 친구가 부모 노드가 되어야 한다.
		A = find(A);
		B = find(B);
		if(A != B) {
			if(cost[A] < cost[B]) {
				parent[B] = A;
			} else {
				parent[A] = B;
			}			
		}
	}
	
	private static int find(int A) {
		if(parent[A] == A) {
			return A;
		}else {
			return parent[A] = find(parent[A]);			
		}
	}

}
