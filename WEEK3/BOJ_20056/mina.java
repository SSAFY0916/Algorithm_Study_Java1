import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

class Fireball {
	int x;
	int y;
	int m;
	int s;
	int d;

	Fireball(int x, int y, int m, int s, int d) {
		this.x = x;
		this.y = y;
		this.m = m;
		this.s = s;
		this.d = d;
	}
}

public class Main {

	static BufferedWristter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	static int[] dx = { -1, -1, 0, 1, 1, 1, 0, -1 };
	static int[] dy = { 0, 1, 1, 1, 0, -1, -1, -1 };

	static int N, M, K;

	static List<Fireball>[][] ballMap; // 공이 위치한 배열

	static List<Fireball> ballList; // 공 리스트

	public static void main(String[] args) throws Exception {

		StringTokenizer st = new StringTokenizer(br.readLine(), " ");

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		ballList = new ArrayList<Fireball>();

		ballMap = new ArrayList[N][N];

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				ballMap[i][j] = new ArrayList<Fireball>();
			}
		}

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int x = Integer.parseInt(st.nextToken()) - 1;
			int y = Integer.parseInt(st.nextToken()) - 1;
			int m = Integer.parseInt(st.nextToken());
			int s = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());

			Fireball f = new Fireball(x, y, m, s, d);
			ballList.add(f); // 공 리스트에 공 추가
		}

		for (int i = 0; i < K; i++) {
			move(); // 공 움직이기
			fireball(); // 공 합치기
		}

		// 질량 계산
		int sum = 0;
		for (int i = 0; i < ballList.size(); i++) {
			sum += ballList.get(i).m;
		}

		bw.write(Integer.toString(sum));

		bw.close();
	}

	static void move() {

		// 공 리스트에서 공 가져와서 이동시킨담에 배열의 이동한 위치에 그 공 추가

		for (int i = 0; i < ballList.size(); i++) {
			int x = ballList.get(i).x + ballList.get(i).s * dx[ballList.get(i).d];
			int y = ballList.get(i).y + ballList.get(i).s * dy[ballList.get(i).d];

			// N-1 0 1 2 3 ... N-1 0 ... 이런식으로 이어진 배열 상태임
			x = (x < 0) ? (N - Math.abs(x) % N) % N : x % N;
			y = (y < 0) ? (N - Math.abs(y) % N) % N : y % N;

			ballList.get(i).x = x;
			ballList.get(i).y = y;
			ballMap[x][y].add(ballList.get(i));
		}
	}

	static void fireball() {

		// 공이 합쳐질 수 있으므로 공 리스트 초기화 하고 합쳐진 공들을 리스트에 새로 넣음

		ballList.clear();

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (ballMap[i][j].size() > 1) { // 공이 2개인 경우 합치기
					int sumM = 0;
					int sumS = 0;
					int count = 0;
					int countEven = 0;

					for (int k = 0; k < ballMap[i][j].size(); k++) {
						count++;
						sumM += ballMap[i][j].get(k).m;
						sumS += ballMap[i][j].get(k).s;
						if (ballMap[i][j].get(k).d % 2 == 0) // 짝수 방향 count
							countEven++;
					}

					if (sumM / 5 == 0) { // 질량의 합/5가 0이 될 경우 공은 소멸
						ballMap[i][j].clear();
						continue;
					}

					for (int k = 0; k < 4; k++) { // 공들을 4개로 쪼갬
						int d = 0;

						if (countEven == count || countEven == 0) // 방향이 모두 홀수거나 짝수
							d = k * 2;
						else // 방향이 홀짝 섞여있음
							d = k * 2 + 1;

						// 새로운 공들을 리스트에 추가
						ballList.add(new Fireball(i, j, sumM / 5, sumS / count, d));
					}
				} else if (ballMap[i][j].size() == 1) { // 공이 1개인 경우, 합쳐질게 없음
					ballList.add(ballMap[i][j].get(0));
				}

				ballMap[i][j].clear();
			}
		}
	}
}
