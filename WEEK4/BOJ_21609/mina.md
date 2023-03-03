![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2021609&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 21609 상어 중학교](https://www.acmicpc.net/problem/21609)

<br>
<br>

# **💻Code**

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

	// 좌표 클래스
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

		board = new int[N][N]; // 격자
		boardCopy = new int[N][N]; // 회전된 격자
		visited = new boolean[N][N]; // 방문 체크

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		while (findBlocks()) { // 블록그룹을 찾을 수 있는 동안만 반복
			removeBlocks(new Pair(mainX, mainY)); // 해당 좌표가 기준 블록인 블록 그룹 제거
			answer += mainCount * mainCount; // 점수 획득
			fallDown(); // 중력 작용
			rotate(); // 90도 반시계 방향으로 회전
			fallDown(); // 중력 작용
		}

		bw.write(Integer.toString(answer));
		bw.close();
	}

	static boolean findBlocks() {
		mainX = N; // 기준 블록의 x좌표
		mainY = N; // 기준 블록의 y좌표
		mainCount = 0; // 블록 그룹의 총 블록 수
		mainRainbow = 0; // 블록 그룹의 무지개 블록 수

		initVisited(1); // 방문 배열 전체 초기화

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (!visited[i][j] && board[i][j] > 0) { // 방문 안한 일반 블록에서만 bfs로 블록 그룹 찾기 시작
					initVisited(0); // 방문 배열의 무지개 블록 칸만 초기화 - 블록 그룹에 이미 들어간 블록들은 방문으로 냅둬서 이전과 같은 그룹을 찾지 못하게함
					bfs(new Pair(i, j));
				}
			}
		}

		// 블록그룹 못찾은 경우에는 mainCount가 변경x -> false 리턴
		return (mainCount == 0) ? false : true;
	}

	static void bfs(Pair start) { // start 좌표부터 bfs 돌면서 블록그룹 찾기
		int count = 1, rainbow = 0; // count : 총 블록 갯수, rainbow : 무지개 블록 갯수
		Queue<Pair> queue = new LinkedList<Pair>();
		visited[start.x][start.y] = true;
		queue.offer(start);

		int minX = N, minY = N;

		while (!queue.isEmpty()) {
			int curX = queue.peek().x;
			int curY = queue.poll().y;

			if (board[curX][curY] != 0 && (curX < minX || curX == minX && curY < minY)) { // 기준 블록
				minX = curX;
				minY = curY;
			}

			for (int i = 0; i < 4; i++) {
				int rx = curX + dx[i];
				int ry = curY + dy[i];

				if (rx < 0 || rx >= N || ry < 0 || ry >= N || visited[rx][ry]
						|| board[rx][ry] != board[start.x][start.y] && board[rx][ry] != 0)
					continue;

				if (board[rx][ry] == 0) // 무지개 블록
					rainbow++;
				count++;
				visited[rx][ry] = true;
				queue.offer(new Pair(rx, ry));
			}
		}

		if (count < 2) // 블록 그룹의 블록 갯수가 2 미만 - 블록 그룹으로 인정x
			return;

		// 우선 순위에 따라 블록 그룹, 기준 블록 설정
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

	static void removeBlocks(Pair p) { // p 좌표를 기준블록으로 하는 블록 그룹 제거

		initVisited(1); // 방문배열 전체 초기화

		int n = board[p.x][p.y];

		Queue<Pair> queue = new LinkedList<Pair>();
		visited[p.x][p.y] = true;
		board[p.x][p.y] = -2;
		queue.offer(p);

		// 기준 블록을 시작으로 bfs 탐색하면서 블록 그룹 제거
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
				board[rx][ry] = -2; // 제거된 자리는 -2로 표시
				queue.offer(new Pair(rx, ry));
			}
		}
	}

	// 방문 배열 초기화
	static void initVisited(int input) {

		// 방문 배열의 무지개 블록칸만 초기화
		if (input == 0) {
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (board[i][j] == 0)
						visited[i][j] = false;
				}
			}

			return;
		}

		// 전체 방문 배열 초기화
		for (int i = 0; i < N; i++) {
			Arrays.fill(visited[i], false);
		}
	}

	// 90도 반시계방향으로 회전
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

	// 중력 작용
	static void fallDown() {
		for (int i = 0; i < N; i++) {
			int bottom = N - 1; // 블럭이 내려와야할 맨 밑 빈칸의 x인덱스
			int next = bottom - 1; // 내려올 블럭의 x인덱스
			while (bottom > 0 && next >= 0) {
				if (board[bottom][i] == -2 && board[next][i] == -2) { // 빈칸은 찾았는데 내려올 블럭을 못찾음
					next--;
				} else if (board[bottom][i] == -2 && board[next][i] == -1) { // 빈칸 찾았는데 내려올 블록으로 검은색을 찾음
					bottom = next - 1;
					next = bottom - 1;
				} else if (board[bottom][i] == -2 && board[next][i] >= 0) { // 빈칸도 찾고 내려올 블럭도 찾음
					int temp = board[next][i];
					board[bottom][i] = temp;
					board[next][i] = -2;
					bottom--;
					next = bottom - 1;
				} else { // 빈칸도 못찾고 내려올 블럭도 못찾음
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

# **🔑Description**

> 크게 블록 그룹 찾기, 블록 제거, 회전, 중력 작용으로 나눠 4가지 메서드를 만들었고 추가적으로 필요한 bfs, 방문 배열 초기화 메서드를 구현했다.\
> 블록 그룹을 찾을때, 한 블록으로 찾은 블록 그룹에서 그 안에있는 다른 블록들로 또 같은 블록 그룹을 찾는 일이 일어나지 않도록 무지개 블록들만 방문을 초기화했다.

<br>
<br>

# **📑Related Issues**

> 블록으로 블록 그룹을 찾을 때 같은 블록 그룹 내에 있는 블록들로 또 같은 그룹을 찾지 않도록 무지개 블록의 방문 배열만 초기화를 시키는 방법을 사용했다.\
> 근데 블록 그룹 찾는 거랑 그 블록 그룹의 기준 블록을 선택하는 과정에서 좀 꼬였었다.\
> 왼쪽 위 블록부터 블록 그룹을 찾는데 쓰기때문에 그 블록이 기준 블록이 될거라고 간주한게 잘못됐다... 거기서부터 탐색하더라도 다른 블록이 기준 블록이 될 수 있다.\
> 그리고 블록 그룹 고를때 무지개 블록 수도 고려해야 하는 걸 생각 안했다...\
> 문제 젭알 제대로 읽기ㅠㅠㅠㅠㅠ

<br>
<br>

# **🕛Resource**

| Memory   | Time   | Implementation Time |
| -------- | ------ | ------------------- |
| 13420 KB | 100 ms | 2 Hours             |
