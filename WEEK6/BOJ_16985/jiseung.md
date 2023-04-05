![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2016985&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> BOJ 16985 Maaaaaaaaaze

# 💻**Code**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

	// 판들을 저장할 배열
	static int[][][][] board;
	// 판들을 어떤 순서로 쌓을 것인지 조합을 저장하는 배열
	static int[] order;
	// 판들의 회전 횟수를 중복순열로 저장하는 배열
	static int[][] perm;
	static int[] dz = {-1, 0, 0, 0, 0, 1};
	static int[] dr = {0, -1, 0, 1, 0, 0};
	static int[] dc = {0, 0, -1, 0, 1, 0};
	// 판의 크기
	static int n = 5;
	static int answer;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;
		board = new int[4][n][n][n]; // 각 판을 회전한 결과도 저장해줄거라서 차원을 하나 더 추가해 4차원으로 만듦, (회전횟수, 판번호, 행, 열)
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				st = new StringTokenizer(br.readLine());
				for(int k=0; k<n; k++) {
					board[0][i][j][k] = Integer.parseInt(st.nextToken());
				}
			}
		}

		init();

		ordering(0, 0);

		bw.write(((answer == Integer.MAX_VALUE) ? -1 : answer) + "\n");
		bw.flush();
		bw.close();
		br.close();
	}

	// 각 배열들 초기화와 판을 회전시킨 정보를 저장
	static void init() {
		answer = Integer.MAX_VALUE; // 이동횟수
		order = new int[n];
		perm = new int[(int)Math.pow(4, n)][n]; // 각 판마다 시계방향으로 0번~3번 회전시킬 수 있으므로 4^5개의 중복 순열을 저장

		for(int i=1; i<4; i++) { // 회전을 1번~3번
			for(int j=0; j<n; j++) { // 판 번호
				for(int k=0; k<n; k++) { // 행
					for(int l=0; l<n; l++) { // 열
						board[i][j][l][n-1-k] = board[i-1][j][k][l]; // 시계방향으로 회전
					}
				}
			}
		}

		for(int i=0; i<Math.pow(4, n); i++) {
			int index = i;
			for(int j=0; j<n; j++) {
				perm[i][n-1-j] = index % 4; // 4로 나눈 머지를 이번 판의 회전 횟수로 저장
				index >>= 2; // 나누기 4
			}
		}
	}

	// 각 판들을 조합해서 순서를 정함
	static void ordering(int count, int flag) {
		if(count == n) {
			for(int i=0; i<Math.pow(4, n); i++) { // 모든 회전 경우의 수에 대해서 bfs
				bfs(i);
			}
			return;
		}
		for(int i=0; i<n; i++) {
			if((flag & (1<<i)) != 0) {
				continue;
			}
			order[count] = i;
			ordering(count+1, flag | (1<<i));
		}
	}

	static void bfs(int index) {
		int[] directions = perm[index];
		if(board[directions[order[0]]][order[0]][0][0] == 0 || board[directions[order[n-1]]][order[n-1]][n-1][n-1] == 0) { // 출발이나 도착이 도달할 수 없다면 리턴
			return;
		}
		boolean[][][] visited = new boolean[n][n][n];
		Queue<int[]> q = new ArrayDeque<>();
		q.add(new int[]{0, 0, 0, 0}); // 판번호, 행, 열, 이동횟수
		while(!q.isEmpty()) {
			int z = q.peek()[0];
			int x = q.peek()[1];
			int y = q.peek()[2];
			int dist = q.poll()[3];
			if(dist >= answer) { // bfs를 더 돌아도 answer를 갱신하지 않는다
				break;
			}
			if(visited[z][x][y]) {
				continue;
			}
			if(z == n-1 && x == n-1 && y == n-1) { // 도착지점
				answer = Math.min(answer, dist);
				break;
			}
			visited[z][x][y] = true;
			for(int i=0; i<6; i++) {
				int newz = z + dz[i];
				int newx = x + dr[i];
				int newy = y + dc[i];
				if(newz<0 || newz>=n || newx<0 || newx>=n || newy<0 || newy>=n) {
					continue;
				}
				if(board[directions[order[newz]]][order[newz]][newx][newy] == 0) { // 이동할 수 없는 칸, (이번판의 회전횟수, 이번판, 행, 열)
					continue;
				}
				q.add(new int[] {newz, newx, newy, dist+1});
			}
		}
	}
}
```

# **🔑Description**

> 시간초과가 나서 방향을 구하는 중복순열을 미리 구해서 저장해놓는 방식으로 바꿨지만 여전히 시간초과였다.\
> 각 경우마다 한 번의 bfs호출만 하도록 바꾸면서 맞았다.\
> 위쪽에서 각 꼭짓점마다 출발하는 경우를 탐색해야 된다고 생각했었는데 어차피 회전시키니까 한 번만 탐색해도 됐다.

# **📑Related Issues**

> 4차원 배열이라서 구현이 힘들었다.

# **🕛Resource**

| Memory     | Time     |
| ---------- | -------- |
| 297564`KB` | 2196`ms` |
