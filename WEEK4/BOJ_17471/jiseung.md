
![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2017471&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **๐Problem**

> BOJ 17471 ๊ฒ๋ฆฌ๋งจ๋๋ง
> 

# ๐ป**Code**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

	static int n;
	// ๊ฐ ๊ตฌ์ญ์ ์ธ๊ตฌ์๋ฅผ ์ ์ฅํ๋ ๋ฐฐ์ด
	static int[] populations;
	// ๊ฐ ๊ตฌ์ญ ๋ณ๋ก ์ธ์ ํ ๊ตฌ์ญ์ ์ ์ฅํ๋ ๋ฐฐ์ด
	static int[][] graph;
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		n = Integer.parseInt(br.readLine());
		populations = new int[n];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i=0; i<n; i++) {
			populations[i] = Integer.parseInt(st.nextToken());
		}
		graph = new int[n][];
		for(int i=0; i<n; i++) {
			st = new StringTokenizer(br.readLine());
			graph[i] = new int[Integer.parseInt(st.nextToken())];
			for(int j=0; j<graph[i].length; j++) {
				graph[i][j] = Integer.parseInt(st.nextToken()) -1;
			}
		}
		int answer = Integer.MAX_VALUE;
		for(int i=1; i<(1<<n)-1; i++) { // ๋นํธ๋ง์คํน์ ํ์ฉํ์ฌ ๋ ์ ๊ฑฐ๊ตฌ๋ก ๋๋
			int sum1 = 0; // ์ฒซ ๋ฒ์งธ ์ ๊ฑฐ๊ตฌ์ ์ธ๊ตฌ์์ ํฉ
			int sum2 = 0; // ๋ ๋ฒ์งธ ์ ๊ฑฐ๊ตฌ์ ์ธ๊ตฌ์์ ํฉ
			for(int j=0; j<n; j++) {
				if((i & (1<<j)) != 0) {
					sum1 += populations[j];
				}else {
					sum2 += populations[j];
				}
			}
			int diff = Math.abs(sum1 - sum2); // ๋ ์ ๊ฑฐ๊ตฌ์ ์ธ๊ตฌ์์ ์ฐจ
			if(diff >= answer) { // ๋ ์ ๊ฑฐ๊ตฌ์ ์ธ๊ตฌ์์ ์ฐจ๊ฐ ํ์ฌ ๋ต๋ณด๋ค ํฌ๊ฑฐ๋ ๊ฐ์ผ๋ฉด ์ด์ฐจํผ 49๋ฒ ์ค์์ ๊ฐฑ์ ํ์ง ์๊ธฐ ๋๋ฌธ์ ๊ฑด๋๋
				continue;
			}
			if(!check(i)) continue; // ๋ ์ ๊ฑฐ๊ตฌ๋ก ์ ๋๋์๋์ง ํ์ธ
			answer = diff; // ๋ ์์ ์ธ๊ตฌ์๋ก ๊ฐฑ์ 
		}
		bw.write((answer==Integer.MAX_VALUE ? -1 : answer) + "\n"); // 
		bw.flush();
		bw.close();
		br.close();
	}

	// bfs๋ฅผ ํ์ฉํ์ฌ ๊ฐ ์ ๊ฑฐ๊ตฌ๋ฅผ ํ์ํ์ฌ ๋ ์ ๊ฑฐ๊ตฌ๋ก ์ ๋๋์๋์ง ํ์ธํ๋ ๋ฉ์๋
	static boolean check(int flag) {
		boolean[] visited = new boolean[n]; // ๊ฐ ๊ตฌ์ญ์ ๋ํ ๋ฐฉ๋ฌธ์ฌ๋ถ๋ฅผ ์ ์ฅํ๋ ๋ฐฐ์ด
		Queue<Integer> q = new LinkedList<>();
		q.add(0); // 0๋ฒ ๊ตฌ์ญ ํ์ ๋ฃ์
		for(int i=0; i<n; i++) {
			if(((flag & (1<<i)) != 0) != ((flag & 1) != 0)) { // 0๋ฒ ๊ตฌ์ญ๊ณผ ๋ค๋ฅธ ์ ๊ฑฐ๊ตฌ๋ฅผ ํ์ ๋ฃ๊ณ  break
				q.add(i);
				break;
			}
		}
		while(!q.isEmpty()) { // bfs
			int cur = q.poll();
			if(visited[cur]) {
				continue;
			}
			visited[cur] = true;
			for(int next : graph[cur]) { // ํ์ฌ ๊ตฌ์ญ๊ณผ ์ธ์ ํ ๊ตฌ์ญ๋ค ์ค์์
				if(((flag & (1<<cur)) != 0) == ((flag & (1<<next)) != 0)) { // ํ์ฌ ๊ตฌ์ญ๊ณผ ๊ฐ์ ์ ๊ฑฐ๊ตฌ์ด๋ฉด ํ์ ๋ฃ์
					q.add(next);
				}
			}
		}
		for(int i=0; i<n; i++) {
			if(!visited[i]) { // ๋ฐฉ๋ฌธ๋์ง ์์ ๊ตฌ์ญ์ด ์๋ค๋ฉด ๋ ์ ๊ฑฐ๊ตฌ๋ก ์ ๋๋์ง ์์ ๊ฒ์ด๋ฏ๋ก false ๋ฆฌํด
				return false;
			}
		}
		return true;
	}
}
```

# **๐Description**

> n์ด ์ต๋ 10๋ฐ์ ์๋ผ์ ๋ฐ๋ก ์์ ํ์ํ  ์๊ฐ์ ํ์๋ค.
> ๋ ์ ๊ฑฐ๊ตฌ๋ก ๋ถ๋ฆฌํ ๋ค์ ์ธ๊ตฌ์๋ถํฐ ์กฐ์ฌํด์ ์ด๋ฒ ํ์์ด ์ฑ๊ณตํด์ ์ต์๊ฐ์ ๊ฐฑ์ ํ  ์ ์๋์ง ์ฌ๋ถ๋ฅผ ๋จผ์  ๋ณด์ ์กฐ๊ธ์ด๋ผ๋ ์ฐ์ฐ์ ์ค์ฌ๋ณด๋ ค๊ณ  ํ๋ค.
> ๋ ์ ๊ฑฐ๊ตฌ๋ก ์ ๋ถ๋ฆฌ๋์๋์ง๋ 0๋ฒ์งธ ๊ตฌ์ญ๊ณผ 0๋ฒ์งธ ๊ตฌ์ญ๊ณผ ๋ค๋ฅธ ์ ๊ฑฐ๊ตฌ์ธ ๊ตฌ์ญ ์ด๋ ๊ฒ 2๊ฐ์ ๊ตฌ์ญ์ ํ์ ๋ฃ๊ณ  ํ๋์ฉ ๊บผ๋ด๋ฉด์ ์ ๊ฑฐ๊ตฌ์ญ์ bfs๋ก ํ์ํ๋ค.
> bfs๊ฐ ๋๋๊ณ  ๋ฐฉ๋ฌธํ์ง ๋ชปํ ๊ตฌ์ญ์ด ํ๋๋ผ๋ ์กด์ฌํ๋ค๋ฉด ๋ ์ ๊ฑฐ๊ตฌ๋ก ์ ๋ถ๋ฆฌ๋์ง ์์ ๊ฒ์ผ๋ก ๊ฐ์ฃผํ๋ค.

# **๐Related Issues**

> ๋นํธ๋ง์คํน์ ํ์ฉํ๋ฉด์ & ์ฐ์ฐ์ ๊ฒฐ๊ณผ๋ฅผ 0๊ณผ ๋น๊ตํ๋ ๋ถ๋ถ์ ์๊พธ ๊น๋จน์๋ค.
> 

# **๐Resource**

| Memory | Time |
| --- | --- |
| 14472KB | 132ms |