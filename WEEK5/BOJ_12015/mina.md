![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2012015&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **๐Problem**

> [BOJ 12015 ๊ฐ์ฅ ๊ธด ์ฆ๊ฐํ๋ ๋ถ๋ถ ์์ด 2](https://www.acmicpc.net/problem/12015)

<br>
<br>

# **๐ปCode**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

	public static void main(String[] args) throws Exception {
		int N = Integer.parseInt(br.readLine());
		int[] A = new int[N + 1];
		int[] dp = new int[N + 1];
		List<Integer> list = new ArrayList<Integer>();
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");

		for (int i = 1; i < N + 1; i++) {
			A[i] = Integer.parseInt(st.nextToken());
		}

		dp[1] = 1;
		list.add(A[1]);

		for (int i = 2; i < N + 1; i++) {
			if (list.get(list.size() - 1) < A[i]) { // ์์ด ๋งจ ๋ค์ ๋ถ์ผ ์ ์์ผ๋ฉด ๋ถ์
				list.add(A[i]);
			} else { // ๋ชป๋ถ์ด๋ ๊ฒฝ์ฐ ๊ตํํ  ์ ์๋ ๊ฐ ์ฐพ๊ธฐ - lowerbound
				int start = 0;
				int end = list.size();
				int middle = -1;

				while (start < end) {
					middle = (start + end) / 2;

					if (list.get(middle) == A[i]) { // ๊ฐ์ ์ซ์๊ฐ ์๋ ๊ฒฝ์ฐ
						start = middle;
						break;
					} else if (list.get(middle) < A[i]) {
						start = middle + 1;
					} else {
						end = middle;
					}
				}
				list.set(start, A[i]);
			}
		}

		bw.write(Integer.toString(list.size()));
		bw.close();

	}
}

```

<br>
<br>

# **๐Description**

> ์ซ์๋ฅผ ํ๋์ฉ ๋ฝ์์ ์์ด์ ๋ง๋ ๋ค.\
> ์ฆ๊ฐํด์ผํ๋ฏ๋ก ํ์ฌ๊น์ง ๋ง๋ค์ด์ง ์์ด ๋งจ ๋ค์์๋ ์ซ์๋ฅผ ํ์ธํด์ ๋ถ์ผ ์ ์์ผ๋ฉด ๋ถ์ธ๋ค.\
> ๋ชป ๋ถ์ด๋ฉด ์์ด ์์์ ๊ตํํ  ์ ์๋ ๊ฐ์ ์ฐพ์ ๊ตํํ๋ค.\

<br>
<br>

# **๐Related Issues**

> ์ฒจ์๋ ๊ตํํ์ง ์๊ณ  ์ ๋ถ ์ถ๊ฐํด์ฃผ๋ ๋ฐฉ์์ด์๋ค.\
> ์ธํ์ด 10 20 30 40 1 2 3 ์ด๋ ๊ฒ ์์ผ๋ฉด ๋ฆฌ์คํธ๊ฐ 1 2 3 10 20 30 40 ์ด๋ ๊ฒ ๋ง๋ค์ด์ง...\
> ๋ฌผ๋ก  ์ด๊ฒ ์์ด์ ์๋ ์  ์ซ์๋ ์  ์ซ์๋ฅผ ๊ฐ์ง ์ธ๋ฑ์ค๋ ๊ฐ์ด ์ ์ฅํด์ ์ธ๋ ค๊ณ ํจ\
> ๊ทผ๋ฐ ๋ฆฌ์คํธ๋ฅผ ์ ๋ ๊ฒ ๋ง๋ค๋ค๋ณด๋ฉด list.add(i, n) ์ด๋ ๊ฒ ์ฐ๊ฒ๋๋๋ฐ ์ด๊ฒ O(n)์ด๋ผ ์๊ฐ์ด๊ณผ ๋ฌ๋ค...\
> ์ฌ์ค ์ค๋ซ๋์ ๊ณ ๋ฏผํด๋ด๋ ๋ชจ๋ฅด๊ฒ ์ด์ ๋ธ๋ก๊ทธ์์ ์ด์ง ์ฝ์ด๋ดค๋๋ฐ ๊ตํํ ์ ์๋ ๊ฐ์ ์ฐพ์ผ๋ผ๊ทธ๋์ ๋ณด์๋ง์ lowerbound๋ก ๊ตํํ ์์๋๊ฑฐ ์ฐพ์์ ๊ตํํ๋ ๋ฐฉ์ ์ผ๋ค..ใ<-< ๋ ํ๋ค๊ณ  ์กธ๋ ค

<br>
<br>

# **๐Resource**

| Memory   | Time  | Implementation Time |
| -------- | ----- | ------------------- |
| 153268KB | 624ms | 5 Hours             |
