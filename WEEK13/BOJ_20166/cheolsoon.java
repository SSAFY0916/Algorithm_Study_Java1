import java.io.*;
import java.util.*;

public class Main_20166 {
	static class Box {
		int x;
		int y;
		int size;
		String str;
		
		public Box(int x, int y, int size, String str) {
			super();
			this.x = x;
			this.y = y;
			this.size = size;
			this.str = str;
		}
	}
	static int N,M,K;
	static char[][] graph;
	static int maxLen;
	static String[] keys;
	static HashMap<String, Integer> god;
	// 상 우상 우 우하 하 좌하 좌 좌상
	static int dx[] = {-1,-1,0,1,1,1,0,-1};
	static int dy[] = {0,1,1,1,0,-1,-1,-1};
	
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		StringBuilder sb = new StringBuilder();
		
		st = new StringTokenizer(in.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		keys = new String[K];
		graph = new char[N][M];
		for(int i=0;i<N;i++) {
			graph[i] = in.readLine().toCharArray();
		}
		god = new HashMap<>();
		for(int i=0;i<K;i++) {
			String temp = in.readLine();
			keys[i] = temp; // 나중에 순서대로 출력해주기 위해 저장
			maxLen = Math.max(maxLen, temp.length()); // BFS의 탈출을 위해 저장
			god.put(temp, 0);  // 신이 원하는 문자열 저장
		}
		
		
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				bfs(i, j); // 8방 BFS진행
			}
		}
		for(int i=0;i<K;i++) {
			sb.append(god.get(keys[i])+"\n");
		}
		System.out.println(sb);
	}

	private static void bfs(int r, int c) {
		Deque<Box> q = new ArrayDeque<>();
		q.offer(new Box(r,c,1,String.valueOf(graph[r][c])));
		
		while(!q.isEmpty()) {
			Box temp = q.pollFirst();
			
			if(god.containsKey(temp.str)) {
				int value = god.get(temp.str);
				god.replace(temp.str, value+1); // 값 발견하면 +1
				// 처음에 여기서도 종료 시켜줬는데 진행하도록 해야 16개 다 맞음
				// // aaa도 있고 aaab가 있는 경우도 있으니 종료시켜주면 안됨.
			}
			if(temp.size >= maxLen) continue; // 사이즈 초과하면 종료
			
			for(int i=0;i<8;i++) {
				int nx = temp.x + dx[i];
				int ny = temp.y + dy[i];
				
				// 환형으로 이어져있음
				if(nx < 0) nx = N-1;
				if(ny < 0) ny = M-1;
				if(nx >= N) nx = 0;
				if(ny >= M) ny = 0;
				
				q.offer(new Box(nx, ny, temp.size+1, temp.str+graph[nx][ny]));
			}
		}
	}

}
