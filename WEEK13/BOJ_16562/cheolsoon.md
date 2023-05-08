![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2016562&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> BOJ 16562 친구비

# 💻**Code**

```java
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

```

# **🔑Description**

> 친구들에 대한 union find 를 하는데
>
> 비용이 적게 드는 친구가 부모노드가 되도록 진행했습니다. 
>
> 모든 친구가 이어질 수 있도록 부모가 자기자신인 친구만 비용을 더해주었습니다.

# **📑Related Issues**

> 

# **🕛Resource**

| Memory    | Time    |
| --------- | ------- |
| 18620`KB` | 228`ms` |