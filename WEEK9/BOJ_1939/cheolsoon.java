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
