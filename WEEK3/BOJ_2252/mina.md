![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%202252&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **๐Problem**

> [BOJ 2252 ์ค ์ธ์ฐ๊ธฐ](https://www.acmicpc.net/problem/2252)

<br>
<br>

# **๐ปCode**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	static List<ArrayList<Integer>> graph;  //์ธ์ ๋ธ๋ ์ ์ฅ

	static int N, M, count;

	static int[] degree;    //๋ธ๋ ์ฐจ์ ๊ธฐ๋ก

	Queue<Integer> q;

	public static void main(String[] args) throws Exception {
		graph = new ArrayList<ArrayList<Integer>>();

		StringTokenizer st = new StringTokenizer(br.readLine(), " ");

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		degree = new int[N + 1];

		for (int i = 0; i < N + 1; i++) {
			graph.add(new ArrayList<Integer>());
		}

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			graph.get(a).add(b);
			degree[b]++;
		}

		topologicalSort();

		bw.close();
	}

	static void topologicalSort() throws Exception {
		Queue<Integer> q = new LinkedList<Integer>();

		for (int i = 1; i <= N; i++) {
			if (degree[i] == 0) { // ์ฐจ์ 0์ด๋ผ ์ ํ ๊ฐ๋ฅ
				q.offer(i);
			}
		}

		while (!q.isEmpty()) {
			int n = q.poll();   //์ ํ ๊ฐ๋ฅ - ๊ทธ๋ํ์์ ์ ์ธ
			bw.write(Integer.toString(n) + " ");

			for (int i = 0; i < graph.get(n).size(); i++) {
				int m = graph.get(n).get(i);
				degree[m]--;    //์ฐ๊ฒฐ๋ ๋ธ๋์ ๊ฐ์  ๋๊ธฐ
				if (degree[m] == 0) {   //์ฐจ์ 0์ด๋ฉด ๊ทธ๋ํ์์ ์ ์ธ ๊ฐ๋ฅ
					q.offer(m);
				}
			}

		}

	}

}
```

<br>
<br>

# **๐Description**

> ๋ฌธ์ ๋ฅผ ์ฝ๊ณ  ์์ ์ ๋ ฌ์ด ์๊ฐ๋ฌ๊ณ  ์ด๋ ต์ง ์๊ฒ ๊ตฌํํ๋ค.\
> ํ์์ ์ ๊ฑฐํ๋ ์์๋๋ก ์ถ๋ ฅํ๋ค.

<br>
<br>

# **๐Related Issues**

> ์ฌ์ค ์์์ ๋ ฌ ๊ตฌํํ๋ ๋ฐฉ๋ฒ์ด ์ ํํ๊ฒ ์๊ฐ๋์ง ์์์ ๊ธฐ์ต๋๋๋๋ก ๊ตฌํํ๋๋ ์ํ ์๊ฐ์ด 2340ms๊ฐ ๋์๋ค.\
> ์ฒ์์ ๊ตฌํํ ๋ q์์ ํ๋ ๋ฝ๋ while๋ฌธ ์์๋ค๊ฐ ๋ชจ๋  ๋ธ๋์ ์ฐจ์๊ฐ 0์ธ์ง ํ์ธํ๊ณ  q์ ์ง์ด๋ฃ๋ ๊ฑธ ๋ฃ์ด๋จ์๋๋ฐ ์ง๋ฉด์๋ ์ด๊ฒ ์๋์๋๊ฒ ๊ฐ์๋..? ํ์๊ณ  ์ง์ง ๊ทธ๊ฒ ์๋์์\
> \
> **๋ชจ๋  ๋ธ๋๋ฅผ ํ์ธํ๋๊ฑด ์ฒ์์๋ง ํด์ฃผ๊ณ  ๋์ค์ degree ์ค์ด๋  ๋ธ๋๋ง 0์ธ์ง ํ์ธํด์ ํ์ ์ง์ด๋ฃ์ผ๋ฉด ๋๋ค.**

<br>
<br>

# **๐Resource**

| Memory  | Time  | Implementation Time |
| ------- | ----- | ------------------- |
| 50352KB | 452ms | 30 Minutes          |
