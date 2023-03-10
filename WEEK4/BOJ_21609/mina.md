![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2021609&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **๐Problem**

> [BOJ 21609 ์์ด ์คํ๊ต](https://www.acmicpc.net/problem/21609)

<br>
<br>

# **๐ปCode**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

	// ์ขํ ํด๋์ค
	static class Pair {
		int x, y;

		Pair(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

	static int[][] board, boardCopy;

	static boolean[][] visited;

	static int N, M, mainX, mainY, mainCount, mainRainbow, answer;

	static int[] dx = { 0, 1, 0, -1 };
	static int[] dy = { 1, 0, -1, 0 };

	public static void main(String[] args) throws Exception {
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		board = new int[N][N]; // ๊ฒฉ์
		boardCopy = new int[N][N]; // ํ์ ๋ ๊ฒฉ์
		visited = new boolean[N][N]; // ๋ฐฉ๋ฌธ ์ฒดํฌ

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		while (findBlocks()) { // ๋ธ๋ก๊ทธ๋ฃน์ ์ฐพ์ ์ ์๋ ๋์๋ง ๋ฐ๋ณต
			removeBlocks(new Pair(mainX, mainY)); // ํด๋น ์ขํ๊ฐ ๊ธฐ์ค ๋ธ๋ก์ธ ๋ธ๋ก ๊ทธ๋ฃน ์ ๊ฑฐ
			answer += mainCount * mainCount; // ์ ์ ํ๋
			fallDown(); // ์ค๋ ฅ ์์ฉ
			rotate(); // 90๋ ๋ฐ์๊ณ ๋ฐฉํฅ์ผ๋ก ํ์ 
			fallDown(); // ์ค๋ ฅ ์์ฉ
		}

		bw.write(Integer.toString(answer));
		bw.close();
	}

	static boolean findBlocks() {
		mainX = N; // ๊ธฐ์ค ๋ธ๋ก์ x์ขํ
		mainY = N; // ๊ธฐ์ค ๋ธ๋ก์ y์ขํ
		mainCount = 0; // ๋ธ๋ก ๊ทธ๋ฃน์ ์ด ๋ธ๋ก ์
		mainRainbow = 0; // ๋ธ๋ก ๊ทธ๋ฃน์ ๋ฌด์ง๊ฐ ๋ธ๋ก ์

		initVisited(1); // ๋ฐฉ๋ฌธ ๋ฐฐ์ด ์ ์ฒด ์ด๊ธฐํ

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (!visited[i][j] && board[i][j] > 0) { // ๋ฐฉ๋ฌธ ์ํ ์ผ๋ฐ ๋ธ๋ก์์๋ง bfs๋ก ๋ธ๋ก ๊ทธ๋ฃน ์ฐพ๊ธฐ ์์
					initVisited(0); // ๋ฐฉ๋ฌธ ๋ฐฐ์ด์ ๋ฌด์ง๊ฐ ๋ธ๋ก ์นธ๋ง ์ด๊ธฐํ - ๋ธ๋ก ๊ทธ๋ฃน์ ์ด๋ฏธ ๋ค์ด๊ฐ ๋ธ๋ก๋ค์ ๋ฐฉ๋ฌธ์ผ๋ก ๋๋ฌ์ ์ด์ ๊ณผ ๊ฐ์ ๊ทธ๋ฃน์ ์ฐพ์ง ๋ชปํ๊ฒํจ
					bfs(new Pair(i, j));
				}
			}
		}

		// ๋ธ๋ก๊ทธ๋ฃน ๋ชป์ฐพ์ ๊ฒฝ์ฐ์๋ mainCount๊ฐ ๋ณ๊ฒฝx -> false ๋ฆฌํด
		return (mainCount == 0) ? false : true;
	}

	static void bfs(Pair start) { // start ์ขํ๋ถํฐ bfs ๋๋ฉด์ ๋ธ๋ก๊ทธ๋ฃน ์ฐพ๊ธฐ
		int count = 1, rainbow = 0; // count : ์ด ๋ธ๋ก ๊ฐฏ์, rainbow : ๋ฌด์ง๊ฐ ๋ธ๋ก ๊ฐฏ์
		Queue<Pair> queue = new LinkedList<Pair>();
		visited[start.x][start.y] = true;
		queue.offer(start);

		int minX = N, minY = N;

		while (!queue.isEmpty()) {
			int curX = queue.peek().x;
			int curY = queue.poll().y;

			if (board[curX][curY] != 0 && (curX < minX || curX == minX && curY < minY)) { // ๊ธฐ์ค ๋ธ๋ก
				minX = curX;
				minY = curY;
			}

			for (int i = 0; i < 4; i++) {
				int rx = curX + dx[i];
				int ry = curY + dy[i];

				if (rx < 0 || rx >= N || ry < 0 || ry >= N || visited[rx][ry]
						|| board[rx][ry] != board[start.x][start.y] && board[rx][ry] != 0)
					continue;

				if (board[rx][ry] == 0) // ๋ฌด์ง๊ฐ ๋ธ๋ก
					rainbow++;
				count++;
				visited[rx][ry] = true;
				queue.offer(new Pair(rx, ry));
			}
		}

		if (count < 2) // ๋ธ๋ก ๊ทธ๋ฃน์ ๋ธ๋ก ๊ฐฏ์๊ฐ 2 ๋ฏธ๋ง - ๋ธ๋ก ๊ทธ๋ฃน์ผ๋ก ์ธ์ x
			return;

		// ์ฐ์  ์์์ ๋ฐ๋ผ ๋ธ๋ก ๊ทธ๋ฃน, ๊ธฐ์ค ๋ธ๋ก ์ค์ 
		if (count > mainCount) {
			mainCount = count;
			mainRainbow = rainbow;
			mainX = minX;
			mainY = minY;
		} else if (count == mainCount) {
			if (rainbow > mainRainbow) {
				mainCount = count;
				mainRainbow = rainbow;
				mainX = minX;
				mainY = minY;
			} else if (rainbow == mainRainbow) {
				if (mainX < minX) {
					mainCount = count;
					mainRainbow = rainbow;
					mainX = minX;
					mainY = minY;
				} else if (mainX == minX && mainY < minY) {
					mainCount = count;
					mainRainbow = rainbow;
					mainX = minX;
					mainY = minY;
				}
			}
		}

	}

	static void removeBlocks(Pair p) { // p ์ขํ๋ฅผ ๊ธฐ์ค๋ธ๋ก์ผ๋ก ํ๋ ๋ธ๋ก ๊ทธ๋ฃน ์ ๊ฑฐ

		initVisited(1); // ๋ฐฉ๋ฌธ๋ฐฐ์ด ์ ์ฒด ์ด๊ธฐํ

		int n = board[p.x][p.y];

		Queue<Pair> queue = new LinkedList<Pair>();
		visited[p.x][p.y] = true;
		board[p.x][p.y] = -2;
		queue.offer(p);

		// ๊ธฐ์ค ๋ธ๋ก์ ์์์ผ๋ก bfs ํ์ํ๋ฉด์ ๋ธ๋ก ๊ทธ๋ฃน ์ ๊ฑฐ
		while (!queue.isEmpty()) {
			int curX = queue.peek().x;
			int curY = queue.poll().y;

			for (int i = 0; i < 4; i++) {
				int rx = curX + dx[i];
				int ry = curY + dy[i];

				if (rx < 0 || rx >= N || ry < 0 || ry >= N || visited[rx][ry]
						|| board[rx][ry] != n && board[rx][ry] != 0)
					continue;

				visited[rx][ry] = true;
				board[rx][ry] = -2; // ์ ๊ฑฐ๋ ์๋ฆฌ๋ -2๋ก ํ์
				queue.offer(new Pair(rx, ry));
			}
		}
	}

	// ๋ฐฉ๋ฌธ ๋ฐฐ์ด ์ด๊ธฐํ
	static void initVisited(int input) {

		// ๋ฐฉ๋ฌธ ๋ฐฐ์ด์ ๋ฌด์ง๊ฐ ๋ธ๋ก์นธ๋ง ์ด๊ธฐํ
		if (input == 0) {
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (board[i][j] == 0)
						visited[i][j] = false;
				}
			}

			return;
		}

		// ์ ์ฒด ๋ฐฉ๋ฌธ ๋ฐฐ์ด ์ด๊ธฐํ
		for (int i = 0; i < N; i++) {
			Arrays.fill(visited[i], false);
		}
	}

	// 90๋ ๋ฐ์๊ณ๋ฐฉํฅ์ผ๋ก ํ์ 
	static void rotate() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				boardCopy[N - 1 - j][i] = board[i][j];
			}
		}

		for (int i = 0; i < N; i++) {
			board[i] = Arrays.copyOf(boardCopy[i], N);
		}
	}

	// ์ค๋ ฅ ์์ฉ
	static void fallDown() {
		for (int i = 0; i < N; i++) {
			int bottom = N - 1; // ๋ธ๋ญ์ด ๋ด๋ ค์์ผํ  ๋งจ ๋ฐ ๋น์นธ์ x์ธ๋ฑ์ค
			int next = bottom - 1; // ๋ด๋ ค์ฌ ๋ธ๋ญ์ x์ธ๋ฑ์ค
			while (bottom > 0 && next >= 0) {
				if (board[bottom][i] == -2 && board[next][i] == -2) { // ๋น์นธ์ ์ฐพ์๋๋ฐ ๋ด๋ ค์ฌ ๋ธ๋ญ์ ๋ชป์ฐพ์
					next--;
				} else if (board[bottom][i] == -2 && board[next][i] == -1) { // ๋น์นธ ์ฐพ์๋๋ฐ ๋ด๋ ค์ฌ ๋ธ๋ก์ผ๋ก ๊ฒ์์์ ์ฐพ์
					bottom = next - 1;
					next = bottom - 1;
				} else if (board[bottom][i] == -2 && board[next][i] >= 0) { // ๋น์นธ๋ ์ฐพ๊ณ  ๋ด๋ ค์ฌ ๋ธ๋ญ๋ ์ฐพ์
					int temp = board[next][i];
					board[bottom][i] = temp;
					board[next][i] = -2;
					bottom--;
					next = bottom - 1;
				} else { // ๋น์นธ๋ ๋ชป์ฐพ๊ณ  ๋ด๋ ค์ฌ ๋ธ๋ญ๋ ๋ชป์ฐพ์
					bottom--;
					next--;
				}
			}
		}
	}
}
```

<br>
<br>

# **๐Description**

> ํฌ๊ฒ ๋ธ๋ก ๊ทธ๋ฃน ์ฐพ๊ธฐ, ๋ธ๋ก ์ ๊ฑฐ, ํ์ , ์ค๋ ฅ ์์ฉ์ผ๋ก ๋๋  4๊ฐ์ง ๋ฉ์๋๋ฅผ ๋ง๋ค์๊ณ  ์ถ๊ฐ์ ์ผ๋ก ํ์ํ bfs, ๋ฐฉ๋ฌธ ๋ฐฐ์ด ์ด๊ธฐํ ๋ฉ์๋๋ฅผ ๊ตฌํํ๋ค.\
> ๋ธ๋ก ๊ทธ๋ฃน์ ์ฐพ์๋, ํ ๋ธ๋ก์ผ๋ก ์ฐพ์ ๋ธ๋ก ๊ทธ๋ฃน์์ ๊ทธ ์์์๋ ๋ค๋ฅธ ๋ธ๋ก๋ค๋ก ๋ ๊ฐ์ ๋ธ๋ก ๊ทธ๋ฃน์ ์ฐพ๋ ์ผ์ด ์ผ์ด๋์ง ์๋๋ก ๋ฌด์ง๊ฐ ๋ธ๋ก๋ค๋ง ๋ฐฉ๋ฌธ์ ์ด๊ธฐํํ๋ค.

<br>
<br>

# **๐Related Issues**

> ๋ธ๋ก์ผ๋ก ๋ธ๋ก ๊ทธ๋ฃน์ ์ฐพ์ ๋ ๊ฐ์ ๋ธ๋ก ๊ทธ๋ฃน ๋ด์ ์๋ ๋ธ๋ก๋ค๋ก ๋ ๊ฐ์ ๊ทธ๋ฃน์ ์ฐพ์ง ์๋๋ก ๋ฌด์ง๊ฐ ๋ธ๋ก์ ๋ฐฉ๋ฌธ ๋ฐฐ์ด๋ง ์ด๊ธฐํ๋ฅผ ์ํค๋ ๋ฐฉ๋ฒ์ ์ฌ์ฉํ๋ค.\
> ๊ทผ๋ฐ ๋ธ๋ก ๊ทธ๋ฃน ์ฐพ๋ ๊ฑฐ๋ ๊ทธ ๋ธ๋ก ๊ทธ๋ฃน์ ๊ธฐ์ค ๋ธ๋ก์ ์ ํํ๋ ๊ณผ์ ์์ ์ข ๊ผฌ์์๋ค.\
> ์ผ์ชฝ ์ ๋ธ๋ก๋ถํฐ ๋ธ๋ก ๊ทธ๋ฃน์ ์ฐพ๋๋ฐ ์ฐ๊ธฐ๋๋ฌธ์ ๊ทธ ๋ธ๋ก์ด ๊ธฐ์ค ๋ธ๋ก์ด ๋ ๊ฑฐ๋ผ๊ณ  ๊ฐ์ฃผํ๊ฒ ์๋ชป๋๋ค... ๊ฑฐ๊ธฐ์๋ถํฐ ํ์ํ๋๋ผ๋ ๋ค๋ฅธ ๋ธ๋ก์ด ๊ธฐ์ค ๋ธ๋ก์ด ๋  ์ ์๋ค.\
> ๊ทธ๋ฆฌ๊ณ  ๋ธ๋ก ๊ทธ๋ฃน ๊ณ ๋ฅผ๋ ๋ฌด์ง๊ฐ ๋ธ๋ก ์๋ ๊ณ ๋ คํด์ผ ํ๋ ๊ฑธ ์๊ฐ ์ํ๋ค...\
> ๋ฌธ์  ์ ญ์ ์ ๋๋ก ์ฝ๊ธฐใ ใ ใ ใ ใ 

<br>
<br>

# **๐Resource**

| Memory   | Time   | Implementation Time |
| -------- | ------ | ------------------- |
| 13420 KB | 100 ms | 2 Hours             |
