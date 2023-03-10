![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%201600&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **๐Problem**

> [BOJ ๋ฌธ์ ๋ฒํธ ๋ฌธ์ ์ด๋ฆ](https://www.acmicpc.net/problem/1600)

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
	// jump ํ์์ ๋ฐ๋ผ์ visited ๋ฐฐ์ด์ ๋ค๋ฅด๊ฒ ํ ๋น
	static boolean[][][] visited;
	// ์ผ๋ฐ ์ด๋
	static int[][] dir = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
	// ๋ง์ฒ๋ผ ์ด๋
	static int[][] jumpDir = { { 2, 1 }, { 1, 2 }, { -2, 1 }, { 1, -2 }, { 2, -1 }, { -1, 2 }, { -2, -1 }, { -1, -2 } };

	// ์ด๋ ์ ๋ณด๋ฅผ ๋ด์๋๊ธฐ ์ํ ํด๋์ค
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
		// ์ด๊ธฐ ์์น ์ ์ฅ
		q.add(new ProgInfo(0, 0, 0, 0));
		visited[0][0][0] = true;

		while (!q.isEmpty()) {
			ProgInfo tmp = q.poll();
			// ์ ๋ต์ ๋ฐ๊ฒฌํ๋ค๋ฉด ์ฆ์ ํจ์ ์ข๋ฃ
			if (tmp.row == rowLen - 1 && tmp.col == colLen - 1)
				return tmp.totalCnt;
			
			// 4๋ฐฉํฅ์ผ๋ก ์์ง์ด๋ฉฐ q์ add
			for (int i = 0; i < 4; i++) {
				int nextRow = tmp.row + dir[i][0];
				int nextCol = tmp.col + dir[i][1];
				// ํ์ฌ ์ ํ ํ์์ ๋์ผํ๋ฉด์ ๋ฐฉ๋ฌธํ์ง ์์๋ค๋ฉด Q์ add
				if (check(nextRow, nextCol) && !visited[tmp.jumpCnt][nextRow][nextCol] && board[nextRow][nextCol] != 1) {
					q.add(new ProgInfo(nextRow, nextCol, tmp.jumpCnt, tmp.totalCnt + 1));
					visited[tmp.jumpCnt][nextRow][nextCol] = true;
				}
			}
			// jump ๊ฐ๋ฅํ ํ์๊ฐ ๋จ์ ์์ ๋๋ง ์คํ
			if (tmp.jumpCnt < jumpNum) {
				for (int i = 0; i < 8; i++) {
					int nextRow = tmp.row + jumpDir[i][0];
					int nextCol = tmp.col + jumpDir[i][1];
					// ํ์ฌ ์ ํ ํ์์์ ํ๋ฒ ๋ ์ ํํ์ ๋ ๋ฐฉ๋ฌธํ์ง ์์๋ค๋ฉด Q์ add
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

# **๐Description**

> BFS๋ฅผ ์ฌ์ฉํด์ ํ์์ผ๋, ์ ํ ํ์์ ๋ฐ๋ผ visited ๋ฐฐ์ด์ ๋ค๋ฅด๊ฒ ํ ๋นํด์ ํด๋น ์ ํ ํ์์ ๋์ผ ํ  ๋ ๋ฐฉ๋ฌธํ ๊ฒฝ์ฐ๋ฅผ ๊ฐ๊ฐ ์ ์ฅํ์์ต๋๋ค.

<br>
<br>

# **๐Related Issues**

> ๊ธฐ์กด BFS ํ์ด ๋ฐฉ๋ฒ๋๋ก visited ๋ฐฐ์ด์ 2์ฐจ์์ผ๋ก๋ง ํ ๋นํ์ฌ ํ์ดํ๋ ค๊ณ  ํ๊ณ , ๋ง์ด ์ด๋ํ๋ ๊ฒฝ์ฐ์๋ visited ๋ฐฐ์ด์ true๋ก ๋ฐ๊พธ์ด ์ฃผ์ง ์๋ ๋ฐฉ์์ ์ฌ์ฉํ๋ ค๊ณ  ํ์์ง๋ง ํ๋ ธ์ต๋๋ค.
> ๋ฐ๋ผ์ ์๋กญ๊ฒ 3์ฐจ์ ๋ฐฐ์ด์ ์ด์ฉํ์ฌ visited ๋ฐฐ์ด์ ์ ํ ํ์์ ๋ฐ๋ผ ๊ฐ๊ฐ ํ ๋นํ์ฌ ํ์ดํ์์ต๋๋ค.

<br>
<br>

# **๐Resource**

| Memory | Time   |
| ------ | ------ |
| 61420KB | 552ms |