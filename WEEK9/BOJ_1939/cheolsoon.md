![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%201939&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> BOJ 1939 중량제한

# 💻**Code**

```java
import java.util.*;
import java.io.*;

public class Main {
	/*
	 * 중량제한
	 * N개 섬 최대 10000개 -> 인접 리스트 
	 * 다리 - 차들 다닐 수 있다
	 * 두개 섬 공장 - 물품 생산
	 * 물품 수송
	 * 각 다리마다 중량 제한
	 * 중량 제한 초과할 수없다. 
	 * 한번의 이동에서 옮길 수 있는 물품들의 최댓값을 구해라
	 * 다리 - 양방향
	 * */
	static int N, M, factory1, factory2, result;
	
//	static ArrayList<ArrayList<int []>> graph; 메모리 초과 -> ArrayList 배열로 초기화.
	static ArrayList<int []> graph[];
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
//		graph = new ArrayList<ArrayList<int []>>();
		graph = new ArrayList[N+1];
		for(int i=0;i<=N;i++) {
			graph[i] = new ArrayList<>();
		}
		for(int i=0;i<M;i++) {
			st = new StringTokenizer(in.readLine());
			int A = Integer.parseInt(st.nextToken());
			int B = Integer.parseInt(st.nextToken());
			int C = Integer.parseInt(st.nextToken());
			graph[A].add(new int[] {B, C});
			graph[B].add(new int[] {A, C});				
		}
		st = new StringTokenizer(in.readLine());
		factory1 = Integer.parseInt(st.nextToken());
		factory2 = Integer.parseInt(st.nextToken());
		
		// 이분탐색 시작
		// 답이 X라고 가정하고 시작, mid의 값으로 통과할 수 있다고 가정
		int left = 1;
		int right = 1000000000;
		while(left <= right) {
			int mid = (left+right)/2;
			if(bfs(mid)) {// mid로 통과할 수 있는가
				left = mid+1;
				result = mid;
			}else {
				right = mid-1;
			}
		}
		System.out.println(result);
	}
	
	private static boolean bfs(int mid) {
		// mid 중량으로 목적지까지 갈 수 있다면 return true
		// 						없다면 return false
		Deque<Integer> q = new ArrayDeque<>();
		boolean visited[] = new boolean[N+1];
		visited[factory1] = true;
		q.offer(factory1);		
		while(!q.isEmpty()) {
			int curr= q.pollFirst();

			if(curr==factory2) {
				return true;
			}
			
			for(int i=0;i<graph[curr].size();i++) {
				int temp[] = graph[curr].get(i);
				int next = temp[0];
				int nextW = temp[1];
				if(visited[next]) continue;
				if(nextW >= mid) {
					q.offer(next);
					visited[next] = true;
				}
			}
		}
		return false;
	}
}
```

# **🔑Description**

> 섬 최대 10000개 => 인접행렬X, 인접리스트를 떠올렸다.
> 
>이분탐색으로 X무게로 건널 수 있다고 가정하고, X무게로 건널 수 있다면 그보다 큰 값을 도전(left=mid+1)하고, 건널 수 없다면 (right=mid-1) 더 작은 값으로 도전하는 방식으로 했다.
> 
>min, max는 문제에서 주어진 대로1, 1000000000을 넣었다.

# **📑Related Issues**

>처음에는 BFS로 이동마다 가장 최소 중량을 기억하면서 그 중량을 넘으면 못가고, 같거나 작으면 이동하고 도착지가 공장2면 가능한 방식으로 했는데 메모리 초과 발생했다.
>
>이분탐색 힌트를 봐버리고, 바로 적용하니까 스르륵..
>
>자바 인접리스트가 익숙치 않은데 더 익숙해지도록 해야겠다.

# **🕛Resource**

| Memory    | Time    |
| --------- | ------- |
| 57488`KB` | 580`ms` |