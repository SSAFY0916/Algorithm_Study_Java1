import java.io.*;
import java.util.*;

public class dajeong {
	public static int N,M;
	public static ArrayList<Integer> adj[];
	public static int ans[];
	public static int weight[];
	public static void dfs(int person, int cost) {
		ans[person]+= cost;
		for(int i=0;i<adj[person].size();i++) {
			int next = adj[person].get(i);
			dfs(next,cost + weight[next]);
		}
	}
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		adj = new ArrayList[N+1];
		for(int i=1;i<=N;i++) {
			adj[i] = new ArrayList<Integer>();
		}
		weight = new int[N+1];
		ans = new int[N+1];
		st = new StringTokenizer(br.readLine());
		st.nextElement();
		for(int i=2;i<=N;i++) {
			int num = Integer.parseInt(st.nextToken());
			adj[num].add(i);
		}
		for(int i=0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			int person = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			weight[person] += cost;
		}
		dfs(1,weight[1]);
		StringBuilder sb = new StringBuilder();
		for(int i=1;i<=N;i++) {
			sb.append(ans[i] + " ");
		}
		System.out.println(sb.toString());
	}

}