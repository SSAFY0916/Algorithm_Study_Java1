![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%201600&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **๐Problem**

> [BOJ 1600 ๋ง์ด ๋๊ณ ํ ์์ญ์ด](https://www.acmicpc.net/problem/1600)

<br>
<br>

# **๐ปCode**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

	static int K, W, H;

	static int[][] map;
	static boolean[][][] visited;
	static int[][][] depth; // ์ด๋ํ ํ์ ๊ธฐ๋ก - (x,y) ์ขํ๊น์ง z๋ฒ ๋ฅ๋ ฅ ์ฌ์ฉํด์ ์ด๋ํจ

	static int[] nx = { -2, -1, 1, 2, 2, 1, -1, -2 };
	static int[] ny = { 1, 2, 2, 1, -1, -2, -2, -1 };
	static int[] dx = { 0, 1, 0, -1 };
	static int[] dy = { 1, 0, -1, 0 };

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

	static class Pair {
		int x, y, k;

		Pair(int x, int y, int k) {
			this.x = x;
			this.y = y;
			this.k = k; // ๋ง์ฒ๋ผ ์์ง์ธ ํ์
		}
	}

	public static void main(String[] args) throws Exception {
		K = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");

		W = Integer.parseInt(st.nextToken());
		H = Integer.parseInt(st.nextToken());

		map = new int[H][W];
		visited = new boolean[H][W][31];
		depth = new int[H][W][31];

		for (int i = 0; i < H; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < W; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		for (int i = 0; i < 31; i++) {
			visited[0][0][i] = true;    //์ถ๋ฐ์ง์ ์ ๋ค์ ์ค์ง ์๋๋ก ๋ฐฉ๋ฌธ ์ฒ๋ฆฌ
		}

		bfs();

		int result = H * W;
		for (int i = 0; i < 31; i++) {
			// ๋์ฐฉ์ง์ ๊น์ง ์ต์๋ก ์ด๋ํ ํ์ ๊ฐ์ ธ์ค๊ธฐ
			if (depth[H - 1][W - 1][i] != 0)
				result = Math.min(result, depth[H - 1][W - 1][i]);
		}

		// ์ถ๋ฐ์ง์  == ๋์ฐฉ์ง์ ์ผ ๊ฒฝ์ฐ๊ฐ ์์ด์ ๋ฐ๋ก ์ฒ๋ฆฌ
		if (H == 1 && W == 1 && map[0][0] == 0) {
			bw.write("0");
		} else if (H == 1 && W == 1 && map[0][0] == 1 || result == H * W)
			bw.write("-1");
		else
			bw.write(Integer.toString(result));

		bw.close();

	}

	static void bfs() {
		Queue<Pair> queue = new ArrayDeque<Pair>();
		queue.offer(new Pair(0, 0, 0));

		while (!queue.isEmpty()) {
			int curX = queue.peek().x;
			int curY = queue.peek().y;
			int curK = queue.poll().k;

			if (curK < K) { // ๋ง์ฒ๋ผ ์ด๋
				for (int i = 0; i < 8; i++) {
					int nextX = curX + nx[i];
					int nextY = curY + ny[i];
					int nextK = curK + 1; // ๋ง์ฒ๋ผ ์ด๋ํ์ผ๋ฏ๋ก +1

					if (nextX < 0 || nextX >= H || nextY < 0 || nextY >= W || map[nextX][nextY] == 1
							|| visited[nextX][nextY][nextK])
						continue;

					visited[nextX][nextY][nextK] = true;
					depth[nextX][nextY][nextK] = depth[curX][curY][curK] + 1;
					queue.offer(new Pair(nextX, nextY, nextK));
				}
			}

			// ์ธ์ ํ ์์ญ์ผ๋ก ๊ทธ๋ฅ ์ด๋
			for (int i = 0; i < 4; i++) {
				int nextX = curX + dx[i];
				int nextY = curY + dy[i];
				// ๊ทธ๋ฅ ์ด๋ํ์ผ๋ฏ๋ก nextK๋ curK๋ก ์ ์ง

				if (nextX < 0 || nextX >= H || nextY < 0 || nextY >= W || map[nextX][nextY] == 1
						|| visited[nextX][nextY][curK])
					continue;

				visited[nextX][nextY][curK] = true;
				depth[nextX][nextY][curK] = depth[curX][curY][curK] + 1;
				queue.offer(new Pair(nextX, nextY, curK));
			}

		}
	}
}
```

<br>
<br>

# **๐Description**

> BFS ํ์์ผ๋ก ์ต๋จ๊ฒฝ๋ก๋ฅผ ๊ตฌํ์๋ค.\
> ๊ทผ๋ฐ ์ฌ๋ฐฉ์ด๋์ ๋ง์ฒ๋ผ ์ด๋ํ๋ ๊ฒฝ์ฐ๊น์ง ์๊ฐํด์ผํด์ visited์ depth๋ฅผ 3์ฐจ์์ผ๋ก ์ ์ธํ์๋ค.\
> depth[x][y][z] ์ ์ ์ฅ๋๋ ๊ฐ์ (x,y)๊น์ง์ ์ต๋จ๊ฑฐ๋ฆฌ๋ฅผ z๋ฒ์ ๋ง ์ด๋์ ์ฌ์ฉํ๋ฉฐ ์ด๋ํ ๊ฑฐ๋ฆฌ์ด๋ค.

<br>
<br>

# **๐Related Issues**

> ์ฒ์์๋ visited๋ depth๋ฅผ 2์ฐจ์์ผ๋ก ๋๊ณ  K๋ฒ ๋จผ์  ๋ค ์ด๋ํ ๋ค์์ ์ฌ๋ฐฉ์ผ๋ก ์ด๋ํ๋์์ผ๋ก bfs๋ฅผ ๊ตฌํํ๊ณ  ์นผ๊ฐ์ด ํ๋ ท๋คใใ.\
> ๋ฐ๋ก ํ์ธํ๊ณ  ๊ฒฐ๊ตญ ์ฌ๋ฐฉ์ด๋+๋ง ์ด๋์ผ๋ก ๋์ค๋ ๋ชจ๋  ์กฐํฉ์ ๋ด์ผํ๋ค๋๊ฑธ ์๊ณ  visited์ depth๋ฅผ 3์ฐจ์์ผ๋ก ๋ฐ๊ฟ์ ํด๊ฒฐํ๋ค..!

<br>
<br>

# **๐Resource**

| Memory  | Time  | Implementation Time |
| ------- | ----- | ------------------- |
| 55824KB | 584ms | 2 Hours             |
