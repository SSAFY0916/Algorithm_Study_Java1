![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2017281&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **๐Problem**

> [BOJ 17281 โพ](https://www.acmicpc.net/problem/17281)

<br>
<br>

# **๐ปCode**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

	static int N, inning, now, score, max;

	static boolean[] visited;
	static int[] player;
	static int[][] result;

	public static void main(String[] args) throws Exception {

		visited = new boolean[8];
		player = new int[9];
		player[3] = 0;  // 4๋ฒ ํ์๋ 1๋ฒ ์ ์๋ก ๊ณ ์ 

		N = Integer.parseInt(br.readLine());

		result = new int[N][9]; // N๊ฒฝ๊ธฐ ๋์์ ๊ฒฐ๊ณผ๊ฐ ์ ์ฅ

		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < 9; j++) {
				result[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		permutation(0); // ์์ด ์์ฑํ๊ณ  ๊ฒฝ๊ธฐ ์๋ฎฌ ๋๋ฆฌ๊ธฐ

		bw.write(Integer.toString(max));
		bw.close();
	}

	static void permutation(int count) {
		if (count == 8) {
			now = 0;    // ํ์ฌ ํ์๋ฒํธ
			inning = 0; // ์ด๋ ์
			score = 0;  // ์ ์
			while (inning != N) {
				baseball();
				inning++;
			}

			max = Math.max(max, score);
			return;
		}

		for (int i = 0; i < 8; i++) {
			if (visited[i])
				continue;

			visited[i] = true;
			if (count >= 3) //1๋ฒํ์๊ฐ 4๋ฒ์ ๊ณ ์  ๋์ด์์
				player[count + 1] = i + 1;
			else
				player[count] = i + 1;
			permutation(count + 1);
			visited[i] = false;
		}
	}

	static void baseball() {
		int out = 0;

		int[] roo = new int[4]; //0: ํ, 1: 1๋ฃจ, 2: 2๋ฃจ, 3: 3๋ฃจ

		while (out != 3) {
			int r = result[inning][player[now]];

			if (r == 0) {   //์์
				out++;
			} else {
				roo[0] = 1; // ํ์ ๋์ด
				for (int i = 4 - r; i < 4; i++) {
					if (roo[i] == 1) {    //๋ฃจ์ ์๋ ์ ์๋ค ์ค ํ์ผ๋ก ์ฌ ์ ์๋ ์ ์๋ค ๋งํผ ์ ์ ํ๋
						score++;
						roo[i] = 0;
					}
				}

				for (int i = 4 - r - 1; i >= 0; i--) {  //๋ฃจ์ ๋จ์์๋ ์ ์๋ค ๋ค์ ๋ฃจ๋ก ์ฎ๊ธฐ๊ธฐ
					roo[i + r] = roo[i];
					roo[i] = 0;
				}
			}

			now = (now + 1) % 9;    //๋ค์ ํ๋ ์ด์ด
		}
	}

}

```

<br>
<br>

# **๐Description**

> ํ์ ์์๋ค์ ์์ด๋ก ๋ฝ๊ณ  ๊ทธ ์์๋๋ก ์ผ๊ตฌ ๊ฒฝ๊ธฐ์ ๋๋ ค์ ์ ์๋ฅผ ๊ณ์ฐํ๋ค.

<br>
<br>

# **๐Related Issues**

> ๋ฃจ์ ๋จ์์๋ ์ ์๋ค ๋ค์ ๋ฃจ๋ก ์ฎ๊ธธ๋ ์ฎ๊ธฐ๊ณ  ๋ ์๋ฆฌ ์ด๊ธฐํ๋ฅผ ์ํด์คฌ์๋ค. (101๋ผ์ธ)\
> ๊ทธ๋ ์ด์ฐจํผ ๋ฎ์ด์์์ง๊ฑฐ๋ผ๊ณ  ์๊ฐํ๋๊ฒ ๊ฐ๋ค.\
> ๊ทธ๋ฅ ์ฒจ๋ถํฐ ํ์คํ๊ฒ ์ฒ๋ฆฌํ์...!!

<br>
<br>

# **๐Resource**

| Memory   | Time   | Implementation Time |
| -------- | ------ | ------------------- |
| 78100 KB | 608 ms | 1 Hour              |
