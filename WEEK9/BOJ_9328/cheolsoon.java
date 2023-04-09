import java.util.*;
import java.io.*;

public class Main_9328 {
	static int n, m, score;
	static char graph[][];
	static List<int[]> gate[];
	static int key;
	static boolean visit[][];
	static int dx[] = {-1,1,0,0};
	static int dy[] = {0,0,-1,1};
	static ArrayDeque<int []> q;
	
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(in.readLine());
		for (int testCase=1; testCase<=T; testCase++) {
			st = new StringTokenizer(in.readLine());
			n = Integer.parseInt(st.nextToken());
			m = Integer.parseInt(st.nextToken());
			graph = new char[n+2][m+2];
			key = 0; // 열쇠 초기화
			gate = new ArrayList[27]; // 게이트를 발견했지만 방문하지 못한 게이트 담음.
			for(int i=0;i<27;i++) {
				gate[i] = new ArrayList<int[]>();
			}
			visit = new boolean[n+2][m+2]; // 테두리 만들어준다.(side가 벽이 아닌 모든 곳을 자유롭게 이동 가능하도록)
			score = 0;
			q = new ArrayDeque<>();
			// 테두리 초기화
			for(int i=0;i<n+2;i++) {
				for(int j=0;j<m+2;j++) {					
					graph[i][j] = '.';
				}
			}
			
			for(int i=1;i<=n;i++) {
				String temp = in.readLine();
				for(int j=1;j<=m;j++) {
					graph[i][j] = temp.charAt(j-1);
				}
			}
			
			// 초기 열쇠 등록
			String keyList = in.readLine();
			if(!keyList.equals("0")) {
				for(int i=0;i<keyList.length();i++) {
					int k = keyList.charAt(i)-'a';
					key= key|(1<<k);
				}
			}
			// BFS
			q.offer(new int[] {0,0});
			while(!q.isEmpty()) {
				int temp[] = q.pollFirst();
				int x=temp[0]; int y=temp[1];				
				int nx, ny;
				for(int i=0;i<4;i++) {
					nx = x+dx[i];
					ny = y+dy[i];
					if(nx<0 || nx>=n+2 || ny<0 || ny>=m+2) continue;
					if(graph[nx][ny] == '*') continue;
					if(visit[nx][ny]) continue;
					// 문일때
					if(graph[nx][ny] >= 'A' && graph[nx][ny] <= 'Z'){
						int need = graph[nx][ny] - 'A';
						if((key&(1<<need)) > 0) {
							graph[nx][ny] = '.';
						}else {
							gate[need].add(new int[] {nx,ny});
							continue;
						}
					}
					
					// 열쇠 획득
					if(graph[nx][ny] >= 'a' && graph[nx][ny] <= 'z'){
						int k = graph[nx][ny] - 'a';
						key = key|(1<<k);
						graph[nx][ny] = '.';
						if(gate[k].size() > 0) {
							for(int g=0;g<gate[k].size();g++) {
								int tmp[] = gate[k].get(g);
								visit[tmp[0]][tmp[1]] = true;
								graph[tmp[0]][tmp[1]] = '.';
								q.offer(new int[] {tmp[0], tmp[1]});
							}							
						}
					}
					// 점수 증가
					if(graph[nx][ny] == '$') score++;
					// 이동 처리
					q.offer(new int[] {nx,ny});
					visit[nx][ny] = true;
				}
			}			
			sb.append(score+"\n");
		}
		System.out.println(sb);
	}
}
