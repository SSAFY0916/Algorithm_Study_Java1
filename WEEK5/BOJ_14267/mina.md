![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2014267&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **๐Problem**

> [BOJ 14267 ํ์ฌ ๋ฌธํ 1](https://www.acmicpc.net/problem/14267)

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

	public static void main(String args[]) throws Exception {
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		StringBuilder sb = new StringBuilder();
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());

		int[] emp = new int[n + 1]; // ์ง์์์ฌ ๋ฒํธ ์ ์ฅ
		int[] compliment = new int[n + 1];   // n๋ฒ ์ฌ์์ด ๋ฐ์ ์นญ์ฐฌ
		int[] dp =  int[n + 1];

		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 1; i < n + 1; i++) {
			emp[i] = Integer.parseInt(st.nextToken());
		}

		for (int i = 0; i < m; i++) {new
			st = new StringTokenizer(br.readLine(), " ");
			int idx = Integer.parseInt(st.nextToken());
			int p = Integer.parseInt(st.nextToken());
			compliment[idx] += p;   // ๊ฐ์ ์ฌ์์ ์ฌ๋ฌ๋ฒ ์นญ์ฐฌ ํ  ์ ์์
		}

		emp[1] = 0;

		for (int i = 2; i < n + 1; i++) {
			dp[i] = dp[emp[i]] + compliment[i]; // ๋ด ์์ฌ๊ฐ ๋ฐ์ ๋์  ์นญ์ฐฌ + ๋ด๊ฐ ๋ฐ์ ์นญ์ฐฌ
		}

		for (int i = 1; i < n + 1; i++) {
			sb.append(dp[i]).append(" ");
		}
		bw.write(sb.toString());
		bw.close();

	}
}
```

<br>
<br>

# **๐Description**

> 1. ํ ์ฌ์์ด ์นญ์ฐฌ์ ๋ฐ์ผ๋ฉด ๊ทธ ์ฌ์์ ์ง์ ๋ถํ๋ค์ด ์ฐ์์ ์ผ๋ก ๋ด๋ฆฌ ์นญ์ฐฌ๋ฐ์\
> 2. ์ง์ ์์ฌ์ ๋ฒํธ๋ ์์ ์ ๋ฒํธ๋ณด๋ค ์์\
>    ์ด ๋๊ฐ์ง ์กฐ๊ฑด์ ๋ณด๊ณ  ๋ฐฐ์ด ํธ๋ฆฌ๋ฅผ ๋ง๋ค์ด์ dp๋ก ํ์๋ค.\
>    ์์ ์ ๋ถ๋ชจ ๋ฒํธ๋ฅผ ๋ฐฐ์ด์ ๋ฃ์ด์ค์ ๊ทธ ๋ฐฐ์ด์์ ์์ ์ ๋ถ๋ชจ ์ ๋ณด๋ฅผ ๊ฐ์ ธ์ค๊ณ  ๊ทธ ๋ถ๋ชจ๊ฐ ๋ฐ์ ์นญ์ฐฌ + ๋ด๊ฐ ๋ฐ์ ์นญ์ฐฌ์ dp์ ๋ฃ์ด์คฌ๋ค.

<br>
<br>

# **๐Related Issues**

> ์ฌ์๋ค์ด ์นญ์ฐฌ์ ์ค๋ณต์ผ๋ก ๋ฐ์ ์ ์๋๊ฑด์ง ๋ชจ๋ฅด๊ฒ ์ด์ ์ค๋ณต์ด ์๋ค๊ณ  ์๊ฐํ๊ณ  ํ์ด์ ํ๋ฒ ํ๋ ธ๋ค.\
> ์ฌ์ค ๋ฌธ์ ์ ๊ทธ๋ฐ ๋ง ์์ด์ ์ค๋ณต์ด ์๋ค๊ณ  ์๊ฐํ๋๊ฒ ๋ง๊ธดํจ....ใใใ

<br>
<br>

# **๐Resource**

| Memory  | Time  | Implementation Time |
| ------- | ----- | ------------------- |
| 53016KB | 472ms | 30 Minutes          |
