![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%201600&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 1600 말이 되고픈 원숭이](https://www.acmicpc.net/problem/1600)

<br>
<br>

# **💻Code**

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
	static int[][][] depth; // 이동한 횟수 기록 - (x,y) 좌표까지 z번 능력 사용해서 이동함

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
			this.k = k; // 말처럼 움직인 횟수
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
			visited[0][0][i] = true;    //출발지점에 다시 오지 않도록 방문 처리
		}

		bfs();

		int result = H * W;
		for (int i = 0; i < 31; i++) {
			// 도착지점까지 최소로 이동한 횟수 가져오기
			if (depth[H - 1][W - 1][i] != 0)
				result = Math.min(result, depth[H - 1][W - 1][i]);
		}

		// 출발지점 == 도착지점일 경우가 있어서 따로 처리
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

			if (curK < K) { // 말처럼 이동
				for (int i = 0; i < 8; i++) {
					int nextX = curX + nx[i];
					int nextY = curY + ny[i];
					int nextK = curK + 1; // 말처럼 이동했으므로 +1

					if (nextX < 0 || nextX >= H || nextY < 0 || nextY >= W || map[nextX][nextY] == 1
							|| visited[nextX][nextY][nextK])
						continue;

					visited[nextX][nextY][nextK] = true;
					depth[nextX][nextY][nextK] = depth[curX][curY][curK] + 1;
					queue.offer(new Pair(nextX, nextY, nextK));
				}
			}

			// 인접한 영역으로 그냥 이동
			for (int i = 0; i < 4; i++) {
				int nextX = curX + dx[i];
				int nextY = curY + dy[i];
				// 그냥 이동했으므로 nextK는 curK로 유지

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

# **🔑Description**

> BFS 탐색으로 최단경로를 구하였다.\
> 근데 사방이동에 말처럼 이동하는 경우까지 생각해야해서 visited와 depth를 3차원으로 선언하였다.\
> depth[x][y][z] 에 저장되는 값은 (x,y)까지의 최단거리를 z번의 말 이동을 사용하며 이동한 거리이다.

<br>
<br>

# **📑Related Issues**

> 처음에는 visited랑 depth를 2차원으로 놓고 K번 먼저 다 이동한 다음에 사방으로 이동하는식으로 bfs를 구현했고 칼같이 틀렷다ㅎㅎ.\
> 반례 확인하고 결국 사방이동+말 이동으로 나오는 모든 조합을 봐야한다는걸 알고 visited와 depth를 3차원으로 바꿔서 해결했다..!

<br>
<br>

# **🕛Resource**

| Memory  | Time  | Implementation Time |
| ------- | ----- | ------------------- |
| 55824KB | 584ms | 2 Hours             |
