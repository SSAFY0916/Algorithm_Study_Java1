![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2017281&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 문제번호 문제이름](https://www.acmicpc.net/problem/17281)

<br>
<br>

# **Code**

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_17281 {
	static int inning;
	static int[][] hits;

	// 순열을 이용한 타순을 저장하기 위한 배열
	static int[] order = new int[9 + 1];
	// 선택되어 있는 타자인지 저장
	static boolean[] selected = new boolean[9 + 1];
	// 현재 베이스에 주자가 있는지 확인하기 위한 배열
	static boolean[] base = new boolean[4];
	static int maxScore = 0;

	public static void calc() {
		int score = 0;
		int curPlayer = 0;

		for (int i = 1; i <= inning; i++) {
			// 이닝이 초기화 될 때마다 베이스 초기화 해주어야 한다는 것을 생각하지 못함
			Arrays.fill(base, false);
			int out = 0;
			// 아웃을 세번 당할 때까지 타순을 반복
			while (out < 3) {
				curPlayer++;
				// 타순이 1번부터 진행되기 때문에 %9를 사용할 수 없어서 10번이 되면 1번으로 바꿔주는 방식 사용
				if (curPlayer == 10)
					curPlayer = 1;
				// hits[i][curPlayer] -> i이닝 에 curPlayer타순 타자가 공을 쳐서 얻는 결과

				// 0이라면 타자가 아웃 되는 경우
				if (hits[i][order[curPlayer]] == 0) {
					out++;
					// 1일 때 3루에 주자가 있다면 1점 획득
					// 나머지 주자는 한 베이스씩 진출
				} else if (hits[i][order[curPlayer]] == 1) {
					if (base[3] == true) {
						score++;
						base[3] = false;
					}
					if (base[2] == true) {
						base[3] = true;
						base[2] = false;
					}
					if (base[1] == true) {
						base[2] = true;
						base[1] = false;
					}
					base[1] = true;
					// 2일 때 2, 3루에 주자가 있다면 1점씩 획득
					// 나머지 주자는 두 베이스씩 진출
				} else if (hits[i][order[curPlayer]] == 2) {
					if (base[3] == true) {
						score++;
						base[3] = false;
					}
					if (base[2] == true) {
						score++;
						base[2] = false;
					}
					if (base[1] == true) {
						base[3] = true;
						base[1] = false;
					}
					base[2] = true;
					// 3일 때 1, 2, 3루에 주자가 있다면 1점씩 획득
					// 타자는 3루
				} else if (hits[i][order[curPlayer]] == 3) {
					if (base[3] == true) {
						score++;
						base[3] = false;
					}
					if (base[2] == true) {
						score++;
						base[2] = false;
					}
					if (base[1] == true) {
						score++;
						base[1] = false;
					}
					base[3] = true;
					// 4일 때 주자가 있는 베이스들의 수만큼 1점씩 획득
					// 타자도 1점 획득
				} else if (hits[i][order[curPlayer]] == 4) {
					if (base[3] == true) {
						score++;
						base[3] = false;
					}
					if (base[2] == true) {
						score++;
						base[2] = false;
					}
					if (base[1] == true) {
						score++;
						base[1] = false;
					}
					score++;
				}
			}
		}

		maxScore = Math.max(maxScore, score);
	}

	// 타순을 만들기 위한 순열 함수
	public static void permutation(int cnt) {
		if (cnt == 10) {
			calc();
			return;
		}
		// 4번타자는 첫번째 선수로 고정되어 있기 때문에 다음 타순 선정하기 위한 재귀적 호출
		if (cnt == 4) {
			permutation(cnt + 1);
			return;
		}

		for (int i = 2; i <= 9; i++) {
			if (selected[i])
				continue;
			order[cnt] = i;
			selected[i] = true;
			permutation(cnt + 1);
			selected[i] = false;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		inning = Integer.parseInt(br.readLine());

		hits = new int[inning + 1][9 + 1];

		for (int i = 1; i <= inning; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= 9; j++) {
				hits[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		selected[1] = true;
		order[4] = 1;

		permutation(1);

		System.out.println(maxScore);
	}

}
```

<br>
<br>

# **🔑Description**

> 입력받은 선수들 중 첫번째 선수는 4번타자로 고정 후 나머지 선수들의 타순을 선택합니다.
> 이후 완성된 타순으로 경기가 끝날 때 까지 얻을 수 있는 점수를 계산한 이후, maxScore 값을 갱신해 나가는 방식으로 풀이하였습니다.

<br>
<br>

# **📑Related Issues**

> 야구 규칙 중 이닝이 바뀔 때 베이스의 상태가 초기화 된다는 내용을 생각하지 못했습니다.
> 순열에서 하나의 값이 고정일 때 해당 인덱스를 넘기고 다음 인덱스부터 다시 재귀적 호출을 진행하도록, 재귀함수를 작성할 때 다소 어려움이 있었습니다.

<br>
<br>

# **🕛Resource**

| Memory | Time   |
| ------ | ------ |
| 17272KB | 476ms |