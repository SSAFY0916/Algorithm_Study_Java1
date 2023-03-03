import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

	static int[][] dice;
	static class Pair {
		int x, y;

		public Pair(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		int[][] nums = new int[n][m];
		for(int i=0; i<n; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<m; j++) {
				nums[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		int[] dr = {0, 1, 0, -1}; // 우하좌상
		int[] dc = {1, 0, -1, 0};
		boolean[][] visited = new boolean[n][m];	// bfs에 사용하는 방문여부 저장 배열 
		int[][] scores = new int[n][m];				// 동서남북으로 이동할 수 있는 칸의 수를 저장하는 배열
		for(int i=0; i<n; i++) {
			for(int j=0; j<m; j++) {
				if(visited[i][j]) {					// 이미 방문했으면 건너뜀
					continue;
				}
				Queue<Pair> q = new LinkedList<>();	// bfs에 사용하는 큐
				Queue<Pair> path = new LinkedList<>();	// 경로를 저장하는 큐
				q.add(new Pair(i, j));
				int score = 0;
				while(!q.isEmpty()) {
					Pair pair = q.poll();
					if(visited[pair.x][pair.y]) {
						continue;
					}
					score++;							// 이동할 수 있는 칸의 수 증가
					path.add(pair);						// 경로 추가
					visited[pair.x][pair.y] = true;		// 방문 처리
					for(int l=0; l<4; l++) {
						int newx = pair.x + dr[l];
						int newy = pair.y + dc[l];
						if(newx<0 || newx>=n || newy<0 || newy>=m) {
							continue;
						}
						if(nums[newx][newy] == nums[i][j]) {	// 나와 같은 숫자를 가진 칸으로만 이동
							q.add(new Pair(newx, newy));
						}
					}
				}
				while(!path.isEmpty()) {				// 경로를 따라 점수 저장
					scores[path.peek().x][path.poll().y] = score;
				}
			}
		}
		dice = new int[][] {							// 주사위 전개도 초기화
				{-1, 2, -1},
				{4, 1, 3},
				{-1, 5, -1},
				{-1, 6, -1}
		};
		int answer = 0;
		int r = 0, c = 0, d = 0;						// 주사위 초기 위치 및 방향
		int cur = dice[3][1];							// 주사위 아랫 면 초기화
		while(k-- > 0) {								// k번 반복
			int newr = r + dr[d];
			int newc = c + dc[d];
			if(newr < 0 || newr >= n || newc < 0 || newc >= m) {	// 지도 벗어나면
				d = (d+2)%4;							// 방향 바꿈
				newr = r + dr[d];
				newc = c + dc[d];
			}
			answer += scores[newr][newc] * nums[newr][newc];	// 점수 증가
			cur = roll(d);								// 주사위 굴리기
			if(cur > nums[newr][newc]) {				// 주사위 방향 갱신
				d = (d+1)%4;
			}else if(cur < nums[newr][newc]) {
				d = (d+3)%4;
			}
			r = newr;									// 주사위 위치 갱신
			c = newc;
		}
		bw.write(answer + "\n");
		bw.flush();
		bw.close();
		br.close();
	}
	static int roll(int d) {
		int temp;
		switch(d) {										// 입력받은 방향(우하좌상)대로 주사위 전개도의 값을 바꿈
		case 0:
			temp = dice[1][2];
			dice[1][2] = dice[1][1];
			dice[1][1] = dice[1][0];
			dice[1][0] = dice[3][1];
			dice[3][1] = temp;
			break;
		case 1:
			temp = dice[3][1];
			dice[3][1] = dice[2][1];
			dice[2][1] = dice[1][1];
			dice[1][1] = dice[0][1];
			dice[0][1] = temp;
			break;
		case 2:
			temp = dice[1][0];
			dice[1][0] = dice[1][1];
			dice[1][1] = dice[1][2];
			dice[1][2] = dice[3][1];
			dice[3][1] = temp;
			break;
		case 3:
			temp = dice[0][1];
			dice[0][1] = dice[1][1];
			dice[1][1] = dice[2][1];
			dice[2][1] = dice[3][1];
			dice[3][1] = temp;
			break;
		}
		return dice[3][1];
	}
}