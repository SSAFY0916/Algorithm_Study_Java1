![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2023288&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 23288 주사위 굴리기 2](https://www.acmicpc.net/problem/23288)

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

public class Main {

	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	static int[][] map;

	static boolean[][] visited;

	static int N, M, K, score, D;

	static int[] dx = { 0, 1, 0, -1 };
	static int[] dy = { 1, 0, -1, 0 };

	static Dice dice;   //주사위

	static Pair diceRoc;    //주사위 위치

	static class Dice { //주사위
		int w;  //왼쪽면 숫자
		int e;  //오른쪽면 숫자
		int n;  //뒤쪽면 숫자
		int s;  //앞쪽면 숫자
		int t;  //위쪽면 숫자
		int b;  //아래쪽면 숫자

		Dice(int w, int e, int n, int s, int t, int b) {
			this.w = w;
			this.e = e;
			this.n = n;
			this.s = s;
			this.t = t;
			this.b = b;
		}
	}

	static class Pair { //좌표
		int x;
		int y;

		Pair(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	public static void main(String[] args) throws Exception {

		StringTokenizer st = new StringTokenizer(br.readLine(), " ");

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		D = 0;  //초기 방향(동쪽)

		map = new int[N][M];
		visited = new boolean[N][M];

		dice = new Dice(4, 3, 2, 5, 1, 6);  //처음 주사위 상태
		diceRoc = new Pair(0, 0);   //처음 주사위 위치

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		do {
			roll(); // 굴리기
			bfs(diceRoc);// 점수획득
			rotate((dice.b - map[diceRoc.x][diceRoc.y]));// 방향전환
			K--;
		} while (K != 0);

		bw.write(Integer.toString(score));

		bw.close();

	}

	static void rotate(int c) {
		if (c > 0) {    //시계방향으로 90도 회전
			D = (D + 1) % 4;
		} else if (c < 0) { //반시계방향으로 90도 회전
			if (D == 0) {
				D = 3;
			} else {
				D--;
			}
		}
	}

	static void bfs(Pair start) {   //시작점이랑 같은 숫자를 가진 칸 탐색

		// visited 초기화
		for (int i = 0; i < N; i++) {
			Arrays.fill(visited[i], false);
		}

		int count = 1;

		int n = map[start.x][start.y];
		visited[start.x][start.y] = true;

		Queue<Pair> q = new LinkedList<Pair>();

		q.offer(start);

		while (!q.isEmpty()) {
			int curX = q.peek().x;
			int curY = q.poll().y;

			for (int i = 0; i < 4; i++) {
				int rx = curX + dx[i];
				int ry = curY + dy[i];

				if (rx < 0 || rx >= N || ry < 0 || ry >= M || visited[rx][ry] || map[rx][ry] != n)
					continue;

				visited[rx][ry] = true;
				count++;
				q.offer(new Pair(rx, ry));
			}
		}

		score += count * n;

	}

	static void roll() {    //주사위 굴리기

		int rx = diceRoc.x + dx[D];
		int ry = diceRoc.y + dy[D];

		if (rx < 0 || rx >= N || ry < 0 || ry >= M) //범위 벗어날 경우 방향 반대쪽으로 세팅
			D = (D + 2) % 4;

        //주사위 위치 이동
		diceRoc.x = diceRoc.x + dx[D];
		diceRoc.y = diceRoc.y + dy[D];

        //주사위 상태 변경
		if (D == 0) {// 동
			int temp = dice.e;
			dice.e = dice.t; // 위 -> 오른
			int temp2 = dice.b;
			dice.b = temp; // 오른 -> 아래
			temp = dice.w;
			dice.w = temp2; // 아래 -> 왼
			dice.t = temp; // 왼 -> 위

			// 위 -> 오른
			// 오른 -> 아래
			// 아래 -> 왼
			// 왼 -> 위
		} else if (D == 1) {// 남
			int temp = dice.s;
			dice.s = dice.t;    // 위 -> 남
			int temp2 = dice.b;
			dice.b = temp;  // 남 -> 아래
			temp = dice.n;
			dice.n = temp2; // 아래 -> 북
			dice.t = temp;  // 북 -> 위

			// 위 -> 남
			// 남 -> 아래
			// 아래 -> 북
			// 북 -> 위
		} else if (D == 2) {// 서
			int temp = dice.w;
			dice.w = dice.t;    // 위 -> 왼
			int temp2 = dice.b;
			dice.b = temp;  // 왼 -> 아래
			temp = dice.e;
			dice.e = temp2; // 아래 -> 오른
			dice.t = temp;  // 오른 -> 위

			// 위 -> 왼
			// 왼 -> 아래
			// 아래 -> 오른
			// 오른 -> 위
		} else if (D == 3) {// 북
			int temp = dice.n;
			dice.n = dice.t;    // 위 -> 북
			int temp2 = dice.b;
			dice.b = temp;  // 북 -> 아래
			temp = dice.s;
			dice.s = temp2; // 아래 -> 남
			dice.t = temp;  // 남 -> 위

			// 위 -> 북
			// 북 -> 아래
			// 아래 -> 남
			// 남 -> 위
		}

	}

}

```

<br>
<br>

# **🔑Description**

> K번 이동하는데 이동할 때 굴러감/점수획득/이동방향변경 3가지가 순서대로 일어나서 이 3가지 기능을 하는 함수를 만들었고 이 행위들을 K번 동안 반복했다.
>
> 1. roll
>    현재 방향으로 굴러갈 수 있는지 먼저 체크하고 범위를 넘어가게되면 방향을 반대방향으로 바꾼다.
>    방향에 따라서 영향 받는 면들끼리만 swap했다.
> 2. bfs
>    현재 칸에 있는 점수와 같으면서도 연속된 위치에 있는 칸의 개수를 가져와 점수를 계산한다
> 3. rotate
>    파람에 따라서 시계방향 / 반시계방향으로 방향을 회전한다.

<br>
<br>

# **📑Related Issues**

> 주사위가 굴러가고 회전하면서 각 면의 숫자가 바뀌는걸 어떻게 구현할 지 엄청 고민했다.\
> 처음에는 주사위 클래스 안에 주사위 좌표, 방향, 아랫면만 넣어놨었는데 아랫면 숫자가 같아도 회전하면 면의 상태가 바뀐다는걸 깨닫고(...) 결국 6면 다 추가한다음에 주사위 좌표랑 방향은 전역으로 뺐다.\
> 개인적으로는 가독성이 더 좋아진 것 같아서 맘에 든다. 주사위 클래스 고민하는데 오래걸렸지만 정하고 나니까 금방 완성 했다.

<br>
<br>

# **🕛Resource**

| Memory  | Time  | Implementation Time |
| ------- | ----- | ------------------- |
| 32952KB | 192ms | 50 Minutes          |
