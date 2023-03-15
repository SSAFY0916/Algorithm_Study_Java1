![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%202146&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 2146 다리 만들기](https://www.acmicpc.net/problem/2146)

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

// 좌표 클래스
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

		input = new int[N][N];// 입력 받는 배열
		country = new int[N][N]; // input에 번호를 부여한 섬 배열
		depth = new int[N][N]; // 다리 길이 저장
		visited = new boolean[N][N]; // 방문 check

		min = N * N; // 최소 다리 길이

		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				input[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		// 섬마다 번호 부여하여 구별함
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (input[i][j] == 1 && !visited[i][j]) {
					island++; // 섬 개수(=번호)
					bfsForIsland(i, j);
				}
			}
		}

		// 섬에서 섬으로 다리놓기
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				// 땅에서 사방탐색하여 바다랑 맞닿아있는 땅에서만 BFS 시작
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

	// 섬 안에서만 BFS 돌면서 번호(island) 부여
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

	// 섬에서 다른 섬으로 BFS 돌기
	static void bfsForBridge(int x, int y) {
		int is = country[x][y]; // 시작한 섬 번호
		Queue<Pair> q = new LinkedList<Pair>();

		// 방문, depth 배열 초기화
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

			// 출발한 섬(is)가 아닌 다른 섬에 도착했을 경우 도착한 위치까지의 depth - 1이 다리길이
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

# **🔑Description**

> BFS롤 통해 어렵지 않게 풀었다.\
> 섬끼리 구분 하는 것도 BFS로 하고 섬과 섬사이의 최단거리를 찾는것도 BFS로 구현하였다.\
> 첨엔 BFS 너무 많이써서 시간초과 나는거 아닌가 했는데 다행히 이 방법이 맞은 것 같다.

<br>
<br>

# **📑Related Issues**

> 섬과 섬사이의 최단 거리를 찾을 때 마다 visited와 depth 배열을 새로 할당해가면서 써서 메모리를 많이 잡아먹었다.\
> 새로 할당하지 않고 Arrays.fill 로 초기화 하는 방법으로 고치니까 메모리는 1/12만큼 시간은 2/3만큼 줄일 수 있었다.\
> 앞으로도 최대한 초기화 해가면서 쓰쟈..!!\

<br>
<br>

# **🕛Resource**

| Memory  | Time  | Implementation Time |
| ------- | ----- | ------------------- |
| 23328KB | 204ms | 40 Minutes          |
