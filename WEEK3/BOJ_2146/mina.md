![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%202146&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **๐Problem**

> [BOJ 2146 ๋ค๋ฆฌ ๋ง๋ค๊ธฐ](https://www.acmicpc.net/problem/2146)

<br>
<br>

# **Code**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

// ์ขํ ํด๋์ค
class Pair {
	int x;
	int y;

	Pair(int x, int y) {
		this.x = x;
		this.y = y;
	}
}

public class Main {

	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	static int N, island, min;
	static int[][] input, country, depth;
	static boolean[][] visited;

	static int[] dx = { 0, 1, 0, -1 };
	static int[] dy = { 1, 0, -1, 0 };

	public static void main(String[] args) throws Exception {

		N = Integer.parseInt(br.readLine());

		input = new int[N][N];// ์๋ ฅ ๋ฐ๋ ๋ฐฐ์ด
		country = new int[N][N]; // input์ ๋ฒํธ๋ฅผ ๋ถ์ฌํ ์ฌ ๋ฐฐ์ด
		depth = new int[N][N]; // ๋ค๋ฆฌ ๊ธธ์ด ์ ์ฅ
		visited = new boolean[N][N]; // ๋ฐฉ๋ฌธ check

		min = N * N; // ์ต์ ๋ค๋ฆฌ ๊ธธ์ด

		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				input[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		// ์ฌ๋ง๋ค ๋ฒํธ ๋ถ์ฌํ์ฌ ๊ตฌ๋ณํจ
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (input[i][j] == 1 && !visited[i][j]) {
					island++; // ์ฌ ๊ฐ์(=๋ฒํธ)
					bfsForIsland(i, j);
				}
			}
		}

		// ์ฌ์์ ์ฌ์ผ๋ก ๋ค๋ฆฌ๋๊ธฐ
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				// ๋์์ ์ฌ๋ฐฉํ์ํ์ฌ ๋ฐ๋ค๋ ๋ง๋ฟ์์๋ ๋์์๋ง BFS ์์
				if (country[i][j] != 0)
					for (int k = 0; k < 4; k++) {

						int rx = i + dx[k];
						int ry = j + dy[k];

						if (rx < 0 || rx >= N || ry < 0 || ry >= N)
							continue;
						if (country[rx][ry] == 0) {
							bfsForBridge(i, j);
							break;
						}
					}
			}
		}

		bw.write(Integer.toString(min));

		bw.close();
	}

	// ์ฌ ์์์๋ง BFS ๋๋ฉด์ ๋ฒํธ(island) ๋ถ์ฌ
	static void bfsForIsland(int x, int y) {

		Queue<Pair> q = new LinkedList<Pair>();

		q.offer(new Pair(x, y));
		visited[x][y] = true;
		country[x][y] = island;

		while (!q.isEmpty()) {
			Pair p = q.poll();
			int curX = p.x;
			int curY = p.y;
			for (int i = 0; i < 4; i++) {

				int rx = curX + dx[i];
				int ry = curY + dy[i];

				if (rx < 0 || rx >= N || ry < 0 || ry >= N || visited[rx][ry])
					continue;
				if (input[rx][ry] == 1) {
					country[rx][ry] = island;
					visited[rx][ry] = true;
					q.offer(new Pair(rx, ry));
				}
			}
		}

	}

	// ์ฌ์์ ๋ค๋ฅธ ์ฌ์ผ๋ก BFS ๋๊ธฐ
	static void bfsForBridge(int x, int y) {
		int is = country[x][y]; // ์์ํ ์ฌ ๋ฒํธ
		Queue<Pair> q = new LinkedList<Pair>();

		// ๋ฐฉ๋ฌธ, depth ๋ฐฐ์ด ์ด๊ธฐํ
		for (int i = 0; i < N; i++) {
			Arrays.fill(visited[i], false);
			Arrays.fill(depth[i], 0);
		}

		q.offer(new Pair(x, y));
		visited[x][y] = true;

		while (!q.isEmpty()) {
			Pair p = q.poll();
			int curX = p.x;
			int curY = p.y;

			// ์ถ๋ฐํ ์ฌ(is)๊ฐ ์๋ ๋ค๋ฅธ ์ฌ์ ๋์ฐฉํ์ ๊ฒฝ์ฐ ๋์ฐฉํ ์์น๊น์ง์ depth - 1์ด ๋ค๋ฆฌ๊ธธ์ด
			if (country[curX][curY] > 0 && country[curX][curY] != is) {
				min = Math.min(min, depth[curX][curY] - 1);
				break;
			}
			for (int i = 0; i < 4; i++) {
				int rx = curX + dx[i];
				int ry = curY + dy[i];

				if (rx < 0 || rx >= N || ry < 0 || ry >= N || visited[rx][ry])
					continue;
				if (country[rx][ry] != is) {
					visited[rx][ry] = true;
					depth[rx][ry] = depth[curX][curY] + 1;
					q.offer(new Pair(rx, ry));
				}
			}
		}

	}

}
```

<br>
<br>

# **๐Description**

> BFS๋กค ํตํด ์ด๋ ต์ง ์๊ฒ ํ์๋ค.\
> ์ฌ๋ผ๋ฆฌ ๊ตฌ๋ถ ํ๋ ๊ฒ๋ BFS๋ก ํ๊ณ  ์ฌ๊ณผ ์ฌ์ฌ์ด์ ์ต๋จ๊ฑฐ๋ฆฌ๋ฅผ ์ฐพ๋๊ฒ๋ BFS๋ก ๊ตฌํํ์๋ค.\
> ์ฒจ์ BFS ๋๋ฌด ๋ง์ด์จ์ ์๊ฐ์ด๊ณผ ๋๋๊ฑฐ ์๋๊ฐ ํ๋๋ฐ ๋คํํ ์ด ๋ฐฉ๋ฒ์ด ๋ง์ ๊ฒ ๊ฐ๋ค.

<br>
<br>

# **๐Related Issues**

> ์ฌ๊ณผ ์ฌ์ฌ์ด์ ์ต๋จ ๊ฑฐ๋ฆฌ๋ฅผ ์ฐพ์ ๋ ๋ง๋ค visited์ depth ๋ฐฐ์ด์ ์๋ก ํ ๋นํด๊ฐ๋ฉด์ ์จ์ ๋ฉ๋ชจ๋ฆฌ๋ฅผ ๋ง์ด ์ก์๋จน์๋ค.\
> ์๋ก ํ ๋นํ์ง ์๊ณ  Arrays.fill ๋ก ์ด๊ธฐํ ํ๋ ๋ฐฉ๋ฒ์ผ๋ก ๊ณ ์น๋๊น ๋ฉ๋ชจ๋ฆฌ๋ 1/12๋งํผ ์๊ฐ์ 2/3๋งํผ ์ค์ผ ์ ์์๋ค.\
> ์์ผ๋ก๋ ์ต๋ํ ์ด๊ธฐํ ํด๊ฐ๋ฉด์ ์ฐ์..!!\

<br>
<br>

# **๐Resource**

| Memory  | Time  | Implementation Time |
| ------- | ----- | ------------------- |
| 23328KB | 204ms | 40 Minutes          |
