![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2017281&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **๐Problem**

> [BOJ ๋ฌธ์ ๋ฒํธ ๋ฌธ์ ์ด๋ฆ](https://www.acmicpc.net/problem/17281)

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

	// ์์ด์ ์ด์ฉํ ํ์์ ์ ์ฅํ๊ธฐ ์ํ ๋ฐฐ์ด
	static int[] order = new int[9 + 1];
	// ์ ํ๋์ด ์๋ ํ์์ธ์ง ์ ์ฅ
	static boolean[] selected = new boolean[9 + 1];
	// ํ์ฌ ๋ฒ ์ด์ค์ ์ฃผ์๊ฐ ์๋์ง ํ์ธํ๊ธฐ ์ํ ๋ฐฐ์ด
	static boolean[] base = new boolean[4];
	static int maxScore = 0;

	public static void calc() {
		int score = 0;
		int curPlayer = 0;

		for (int i = 1; i <= inning; i++) {
			// ์ด๋์ด ์ด๊ธฐํ ๋  ๋๋ง๋ค ๋ฒ ์ด์ค ์ด๊ธฐํ ํด์ฃผ์ด์ผ ํ๋ค๋ ๊ฒ์ ์๊ฐํ์ง ๋ชปํจ
			Arrays.fill(base, false);
			int out = 0;
			// ์์์ ์ธ๋ฒ ๋นํ  ๋๊น์ง ํ์์ ๋ฐ๋ณต
			while (out < 3) {
				curPlayer++;
				// ํ์์ด 1๋ฒ๋ถํฐ ์งํ๋๊ธฐ ๋๋ฌธ์ %9๋ฅผ ์ฌ์ฉํ  ์ ์์ด์ 10๋ฒ์ด ๋๋ฉด 1๋ฒ์ผ๋ก ๋ฐ๊ฟ์ฃผ๋ ๋ฐฉ์ ์ฌ์ฉ
				if (curPlayer == 10)
					curPlayer = 1;
				// hits[i][curPlayer] -> i์ด๋ ์ curPlayerํ์ ํ์๊ฐ ๊ณต์ ์ณ์ ์ป๋ ๊ฒฐ๊ณผ

				// 0์ด๋ผ๋ฉด ํ์๊ฐ ์์ ๋๋ ๊ฒฝ์ฐ
				if (hits[i][order[curPlayer]] == 0) {
					out++;
					// 1์ผ ๋ 3๋ฃจ์ ์ฃผ์๊ฐ ์๋ค๋ฉด 1์  ํ๋
					// ๋๋จธ์ง ์ฃผ์๋ ํ ๋ฒ ์ด์ค์ฉ ์ง์ถ
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
					// 2์ผ ๋ 2, 3๋ฃจ์ ์ฃผ์๊ฐ ์๋ค๋ฉด 1์ ์ฉ ํ๋
					// ๋๋จธ์ง ์ฃผ์๋ ๋ ๋ฒ ์ด์ค์ฉ ์ง์ถ
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
					// 3์ผ ๋ 1, 2, 3๋ฃจ์ ์ฃผ์๊ฐ ์๋ค๋ฉด 1์ ์ฉ ํ๋
					// ํ์๋ 3๋ฃจ
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
					// 4์ผ ๋ ์ฃผ์๊ฐ ์๋ ๋ฒ ์ด์ค๋ค์ ์๋งํผ 1์ ์ฉ ํ๋
					// ํ์๋ 1์  ํ๋
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

	// ํ์์ ๋ง๋ค๊ธฐ ์ํ ์์ด ํจ์
	public static void permutation(int cnt) {
		if (cnt == 10) {
			calc();
			return;
		}
		// 4๋ฒํ์๋ ์ฒซ๋ฒ์งธ ์ ์๋ก ๊ณ ์ ๋์ด ์๊ธฐ ๋๋ฌธ์ ๋ค์ ํ์ ์ ์ ํ๊ธฐ ์ํ ์ฌ๊ท์  ํธ์ถ
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

# **๐Description**

> ์๋ ฅ๋ฐ์ ์ ์๋ค ์ค ์ฒซ๋ฒ์งธ ์ ์๋ 4๋ฒํ์๋ก ๊ณ ์  ํ ๋๋จธ์ง ์ ์๋ค์ ํ์์ ์ ํํฉ๋๋ค.
> ์ดํ ์์ฑ๋ ํ์์ผ๋ก ๊ฒฝ๊ธฐ๊ฐ ๋๋  ๋ ๊น์ง ์ป์ ์ ์๋ ์ ์๋ฅผ ๊ณ์ฐํ ์ดํ, maxScore ๊ฐ์ ๊ฐฑ์ ํด ๋๊ฐ๋ ๋ฐฉ์์ผ๋ก ํ์ดํ์์ต๋๋ค.

<br>
<br>

# **๐Related Issues**

> ์ผ๊ตฌ ๊ท์น ์ค ์ด๋์ด ๋ฐ๋ ๋ ๋ฒ ์ด์ค์ ์ํ๊ฐ ์ด๊ธฐํ ๋๋ค๋ ๋ด์ฉ์ ์๊ฐํ์ง ๋ชปํ์ต๋๋ค.
> ์์ด์์ ํ๋์ ๊ฐ์ด ๊ณ ์ ์ผ ๋ ํด๋น ์ธ๋ฑ์ค๋ฅผ ๋๊ธฐ๊ณ  ๋ค์ ์ธ๋ฑ์ค๋ถํฐ ๋ค์ ์ฌ๊ท์  ํธ์ถ์ ์งํํ๋๋ก, ์ฌ๊ทํจ์๋ฅผ ์์ฑํ  ๋ ๋ค์ ์ด๋ ค์์ด ์์์ต๋๋ค.

<br>
<br>

# **๐Resource**

| Memory | Time   |
| ------ | ------ |
| 17272KB | 476ms |