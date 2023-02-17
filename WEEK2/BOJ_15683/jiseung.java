import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	
	static int[] dr = {-1, 0, 1, 0}; // 상우하좌
	static int[] dc = {0, 1, 0, -1};
	static int[][][] directions = { // cctv별 방향
			{},
			{{0}, {1}, {2}, {3}}, // 1번 cctv
			{{0,2}, {1,3}},
			{{0,1}, {1,2}, {2,3}, {3,0}},
			{{0,1,2}, {1,2,3}, {2,3,0}, {3,0,1}},
			{{0,1,2,3}} // 5번 cctv
	};
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		int[][] nums = new int[n][m]; // 사무실 저장
		List<int[]> cctvs = new ArrayList<>(); // cctv의 위치 저장
		for(int i=0; i<n; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<m; j++) {
				nums[i][j] = Integer.parseInt(st.nextToken());
				if(nums[i][j] != 0 && nums[i][j] != 6) {
					cctvs.add(new int[]{i, j});
				}
			}
		}
		Queue<int[][]> q = new LinkedList<>(); // 가능한 사무실 모양을 저장하는 큐
		q.add(nums); // 초기 사무실 저장
		for(int[] cctv : cctvs) { // cctv마다 반복
			Queue<int[][]> newQ = new LinkedList<>(); // 가능한 새로운 사무실 모양을 저장하는 큐
			int type = nums[cctv[0]][cctv[1]]; // 현재 cctv 번호
			while(!q.isEmpty()) { // 가능한 모든 사무실 모양에 대하여
				for(int i=0; i<directions[type].length; i++) { // cctv 번호별 가능한 조합의 개수
					int[][] board = new int[n][m]; // 큐의 탑에 있는 사무실 모양을 복사
					for(int j=0; j<n; j++) {
						for(int k=0; k<m; k++) {
							board[j][k] = q.peek()[j][k];
						}
					}
					for(int j=0; j<directions[type][i].length; j++) { // 조합에서의 방향의 개수
						int r = cctv[0];
						int c = cctv[1];
						while(true) {
							r += dr[directions[type][i][j]];
							c += dc[directions[type][i][j]];
							if(r<0 || r>=n || c<0 || c>=m) break; // 범위 벗어남
							if(board[r][c] == 6) break; // 벽을 만남
							if(board[r][c] == 0) // 방문 처리
								board[r][c] = -1;
						}
					}
					newQ.add(board); // 새로운 큐에 새로운 모양 저장
				}
				q.poll();
			}
			q = newQ; // 기존의 큐를 새로운 큐로 대체
		}
		int answer = n*m; // 사무실 면적
		while(!q.isEmpty()) {
			int[][] board = q.poll();
			int count = 0;
			for(int i=0; i<n; i++) {
				for(int j=0; j<m; j++) {
					if(board[i][j] == 0)
						count++;
				}
			}
			answer = Math.min(answer, count);
		}
		bw.write(answer + "\n");
		bw.flush();
		bw.close();
		br.close();
	}
}
