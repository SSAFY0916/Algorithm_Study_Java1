![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2011066&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **๐Problem**

> [BOJ 11066 ํ์ผ ํฉ์น๊ธฐ](https://www.acmicpc.net/problem/11066)

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

	static int[] sum;

	public static void main(String[] args) throws Exception {

		int t = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();

		while (t-- > 0) {
			int n = Integer.parseInt(br.readLine());
			int[] chapters = new int[n + 1];
			sum = new int[n + 1]; // ๋์ ํจ ์ ์ฅ
			int[][] dp = new int[n + 1][n + 1]; // dp[i][j] = i๋ถํฐ j๊น์ง ํฉ์น๋๋ฐ ๋๋ ์ต์ ๋น์ฉ
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			for (int i = 1; i < n + 1; i++) {
				chapters[i] = Integer.parseInt(st.nextToken());
				sum[i] = sum[i - 1] + chapters[i];
			}
			for (int i = 1; i < n; i++) {
				dp[i][i + 1] = chapters[i] + chapters[i + 1];
			}
			for (int i = 2; i < n + 1; i++) {
				for (int j = 1; i + j < n + 1; j++) {
					for (int k = j; k < i + j; k++) {

						// a to k ๊น์ง์ ์ต์๋น์ฉ์ ๊ตฌํ๋ค๊ณ  ํ๋ฉด
						// a to b + c to k
						// a to c + d to k
						// a to d + e to k
						// a to e + f to k
						// ...
						// a to i + j to k
						// ์ค ์ต์๊ฐ์ ๊ตฌํจ

						if (dp[j][i + j] == 0) { // j to i + j ๊น์ง์ ์ฒซ ๋น์ฉ -> (j to j+1) + (j+2 to i+j)
							dp[j][i + j] = dp[j][k] + dp[k + 1][i + j] + getSum(j, i + j); // getSum์ dp[j][k] + dp[k + 1][i + j] ํฉ์น๋๋ฐ ๋๋ ๋น์ฉ
						} else { // ํ์นธ์ฉ ์ด๋ํ๋ฉด์ ์ต์๊ฐ ๊ตฌํด์ฃผ๊ธฐ
							dp[j][i + j] = Math.min(dp[j][i + j], dp[j][k] + dp[k + 1][i + j] + getSum(j, i + j));
						}
					}
				}
			}

			sb.append(dp[1][n]).append("\n");
		}
		bw.write(sb.toString());
		bw.close();
	}

	static int getSum(int start, int end) {
		if (start == 0) {
			return sum[end];
		}

		return sum[end] - sum[start - 1];
	}
}

```

<br>
<br>

# **๐Description**

> ํ์ผ์ด a๋ถํฐ k๊น์ง ์๋ค๊ณ  ํ ๋ a to k์ ์ต์๋น์ฉ์ ๊ตฌํ๋ ๋ฐฉ๋ฒ์ ๋ค์ ์ค์์ ๊ตฌํ๋ค.\
> (a to b) + (c to k) + (a๋ถํฐ k๊น์ง ํฉ์น๋ ๋น์ฉ)\
> (a to c) + (d to k) + (a๋ถํฐ k๊น์ง ํฉ์น๋ ๋น์ฉ)\
> (a to d) + (e to k) + (a๋ถํฐ k๊น์ง ํฉ์น๋ ๋น์ฉ)\
> (a to e) + (f to k) + (a๋ถํฐ k๊น์ง ํฉ์น๋ ๋น์ฉ)\
> ...\
> (a to i) + (j to k) + (a๋ถํฐ k๊น์ง ํฉ์น๋ ๋น์ฉ)\
> ์ด ์ค์์ ์ต์๊ฐ์ dp[a to k]์ ๋ฃ์๋ค.\
> i to j ์์์ ๋ฐ๋ณต๋ฌธ N๋ฒ ๋ ์์๊ณ  i ๋ j๋ N๋ฒ์ฉ ๋๋๊น O(N^3) ๋งํผ ๋์ค๋ ๊ฒ ๊ฐ๋ค.

<br>
<br>

# **๐Related Issues**

> ์ค๊ณํ๋๋ฐ 4์๊ฐ, ๊ตฌํํ๋๋ฐ 2์๊ฐ ๊ฑธ๋ ธ๋ค...\
> ํ์ผ a,b,c, ... , h ๊น์ง ์จ๋๊ณ \
> ๊ทธ ์์์ ๊ฐ๊ฐ ๊ตฌ๊ฐ๋ณ๋ก ์ต์๊ฐ์ด ๋  ์์๋ ๊ณ์ฐ์ ๋ค ์ด๋ด์ ๊ฑฐ๊ธฐ์ ๊ท์น ์ฐพ์๋ณด๋ ค๊ณ  ํ๋ค.\
> ์ธ๋ฑ์ค ์ ๋ ๊ตฌ๊ฐ(32~52๋ผ์ธ)์ด ์ง์ง ๋๋ฌด ํท๊ฐ๋ ธ๋ค...ใ ใ ใ ใ \

<br>
<br>

# **๐Resource**

| Memory  | Time  | Implementation Time |
| ------- | ----- | ------------------- |
| 27304KB | 732ms | 6 Hours             |
