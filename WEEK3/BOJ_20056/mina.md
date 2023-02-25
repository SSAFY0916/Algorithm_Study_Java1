![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2020056&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 20056 마법사 상어와 파이어볼](https://www.acmicpc.net/problem/20056)

<br>
<br>

# **💻Code**

```java
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

```

<br>
<br>

# **🔑Description**

> 이동하는 함수와 공을 합치는 함수를 만들어서 K번 수행하도록 하였다.\
> ball을 저장하는 1차원 리스트(ballList)와 해당 위치의 ball들을 저장하는 2차원 배열(ballMap)을 사용했다.\
> 공을 움직일 땐 ballList만 쓰고 공 합칠때는 ballMap을 사용하여 공을 조작하는데에 시간을 단축시키려고 하였다.

<br>
<br>

# **📑Related Issues**

> 처음엔 ballMap 을 만들지 않고 그 위치의 개수를 기록하는 count 2차원 배열만 만든 다음에 그 위치의 공 개수가 2 이상이면 또 ballList를 돌면서 그 위치에 있는 공들을 가져와서 합치는 연산을 하도록 구현했다.\
> 시간초과 났음 당연함\
> 그래서 공 리스트를 각각 갖고있는 2차원 배열을 다시 만들어서 공들을 직접 그 자리에 넣어줬다.\
> 공을 움직일 땐 ballList만 쓰고 공 합칠때는 ballMap을 쓰는 식으로 구현했더니 시간을 많이 단축 할 수 있었다.\
> 또 문제 상황이 배열범위가 연속되어 있다는 것이었는데 이것도 한칸 이동하고 범위 넘어가면 처리하는 걸 속력만큼 for문으로 돌렸었다.\
> 다른사람들 보니까 나보다 실행시간이 적게 나와서 젤 의심가는 이 부분을 고쳤다.\
> 속력\*방향 한다음에 범위 넘어갔을 경우에만 처리하는 방식으로 고쳤더니 시간이 많이 줄었다!\
> \
> **수학연산은 한번에 끝내자**

<br>
<br>

# **🕛Resource**

| Memory  | Time  | Implementation Time |
| ------- | ----- | ------------------- |
| 24680KB | 516ms | 1 Hour 30 Minutes   |
