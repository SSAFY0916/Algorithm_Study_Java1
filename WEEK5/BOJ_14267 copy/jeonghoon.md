![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%201600&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 문제번호 문제이름](https://www.acmicpc.net/problem/1600)

<br>
<br>

# **Code**

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_1600 {
	static int jumpNum;
	static int rowLen, colLen;
	static int[][] board;
	// jump 횟수에 따라서 visited 배열을 다르게 할당
	static boolean[][][] visited;
	// 일반 이동
	static int[][] dir = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
	// 말처럼 이동
	static int[][] jumpDir = { { 2, 1 }, { 1, 2 }, { -2, 1 }, { 1, -2 }, { 2, -1 }, { -1, 2 }, { -2, -1 }, { -1, -2 } };

	// 이동 정보를 담아두기 위한 클래스
	static class ProgInfo {
		int row;
		int col;
		int jumpCnt;
		int totalCnt;

		public ProgInfo(int row, int col, int jumpCnt, int totalCnt) {
			this.row = row;
			this.col = col;
			this.jumpCnt = jumpCnt;
			this.totalCnt = totalCnt;
		}
	}

	public static boolean check(int row, int col) {
		return row >= 0 && row < rowLen && col >= 0 && col < colLen;
	}

	public static int bfs() {
		Queue<ProgInfo> q = new ArrayDeque<>();
		// 초기 위치 저장
		q.add(new ProgInfo(0, 0, 0, 0));
		visited[0][0][0] = true;

		while (!q.isEmpty()) {
			ProgInfo tmp = q.poll();
			// 정답을 발견했다면 즉시 함수 종료
			if (tmp.row == rowLen - 1 && tmp.col == colLen - 1)
				return tmp.totalCnt;
			
			// 4방향으로 움직이며 q에 add
			for (int i = 0; i < 4; i++) {
				int nextRow = tmp.row + dir[i][0];
				int nextCol = tmp.col + dir[i][1];
				// 현재 점프 횟수와 동일하면서 방문하지 않았다면 Q에 add
				if (check(nextRow, nextCol) && !visited[tmp.jumpCnt][nextRow][nextCol] && board[nextRow][nextCol] != 1) {
					q.add(new ProgInfo(nextRow, nextCol, tmp.jumpCnt, tmp.totalCnt + 1));
					visited[tmp.jumpCnt][nextRow][nextCol] = true;
				}
			}
			// jump 가능한 횟수가 남아 있을 때만 실행
			if (tmp.jumpCnt < jumpNum) {
				for (int i = 0; i < 8; i++) {
					int nextRow = tmp.row + jumpDir[i][0];
					int nextCol = tmp.col + jumpDir[i][1];
					// 현재 점프 횟수에서 한번 더 점프했을 때 방문하지 않았다면 Q에 add
					if (check(nextRow, nextCol) && !visited[tmp.jumpCnt + 1][nextRow][nextCol] && board[nextRow][nextCol] != 1) {
						q.add(new ProgInfo(nextRow, nextCol, tmp.jumpCnt + 1, tmp.totalCnt + 1));
						visited[tmp.jumpCnt + 1][nextRow][nextCol] = true;
					}
				}
			}
		}

		return -1;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		jumpNum = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		colLen = Integer.parseInt(st.nextToken());
		rowLen = Integer.parseInt(st.nextToken());
		board = new int[rowLen][colLen];
		visited = new boolean[jumpNum + 1][rowLen][colLen];

		for (int i = 0; i < rowLen; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < colLen; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		System.out.println(bfs());
	}

}

```

<br>
<br>

# **🔑Description**

> BFS를 사용해서 풀었으나, 점프 횟수에 따라 visited 배열을 다르게 할당해서 해당 점프 횟수와 동일 할 때 방문한 경우를 각각 저장하였습니다.

<br>
<br>

# **📑Related Issues**

> 기존 BFS 풀이 방법대로 visited 배열을 2차원으로만 할당하여 풀이하려고 하고, 말이 이동하는 경우에는 visited 배열을 true로 바꾸어 주지 않는 방식을 사용하려고 하였지만 틀렸습니다.
> 따라서 새롭게 3차원 배열을 이용하여 visited 배열을 점프 횟수에 따라 각각 할당하여 풀이하였습니다.

<br>
<br>

# **🕛Resource**

| Memory | Time   |
| ------ | ------ |
| 61420KB | 552ms |