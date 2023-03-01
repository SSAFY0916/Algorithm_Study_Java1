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
