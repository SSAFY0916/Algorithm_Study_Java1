
![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2023288&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **๐Problem**

> BOJ 23288 ์ฃผ์ฌ์ ๊ตด๋ฆฌ๊ธฐ 2
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

	static int[][] dice;
	static class Pair {
		int x, y;

		public Pair(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		int[][] nums = new int[n][m];
		for(int i=0; i<n; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<m; j++) {
				nums[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		int[] dr = {0, 1, 0, -1}; // ์ฐํ์ข์
		int[] dc = {1, 0, -1, 0};
		boolean[][] visited = new boolean[n][m];	// bfs์ ์ฌ์ฉํ๋ ๋ฐฉ๋ฌธ์ฌ๋ถ ์ ์ฅ ๋ฐฐ์ด 
		int[][] scores = new int[n][m];				// ๋์๋จ๋ถ์ผ๋ก ์ด๋ํ  ์ ์๋ ์นธ์ ์๋ฅผ ์ ์ฅํ๋ ๋ฐฐ์ด
		for(int i=0; i<n; i++) {
			for(int j=0; j<m; j++) {
				if(visited[i][j]) {					// ์ด๋ฏธ ๋ฐฉ๋ฌธํ์ผ๋ฉด ๊ฑด๋๋
					continue;
				}
				Queue<Pair> q = new LinkedList<>();	// bfs์ ์ฌ์ฉํ๋ ํ
				Queue<Pair> path = new LinkedList<>();	// ๊ฒฝ๋ก๋ฅผ ์ ์ฅํ๋ ํ
				q.add(new Pair(i, j));
				int score = 0;
				while(!q.isEmpty()) {
					Pair pair = q.poll();
					if(visited[pair.x][pair.y]) {
						continue;
					}
					score++;							// ์ด๋ํ  ์ ์๋ ์นธ์ ์ ์ฆ๊ฐ
					path.add(pair);						// ๊ฒฝ๋ก ์ถ๊ฐ
					visited[pair.x][pair.y] = true;		// ๋ฐฉ๋ฌธ ์ฒ๋ฆฌ
					for(int l=0; l<4; l++) {
						int newx = pair.x + dr[l];
						int newy = pair.y + dc[l];
						if(newx<0 || newx>=n || newy<0 || newy>=m) {
							continue;
						}
						if(nums[newx][newy] == nums[i][j]) {	// ๋์ ๊ฐ์ ์ซ์๋ฅผ ๊ฐ์ง ์นธ์ผ๋ก๋ง ์ด๋
							q.add(new Pair(newx, newy));
						}
					}
				}
				while(!path.isEmpty()) {				// ๊ฒฝ๋ก๋ฅผ ๋ฐ๋ผ ์ ์ ์ ์ฅ
					scores[path.peek().x][path.poll().y] = score;
				}
			}
		}
		dice = new int[][] {							// ์ฃผ์ฌ์ ์ ๊ฐ๋ ์ด๊ธฐํ
				{-1, 2, -1},
				{4, 1, 3},
				{-1, 5, -1},
				{-1, 6, -1}
		};
		int answer = 0;
		int r = 0, c = 0, d = 0;						// ์ฃผ์ฌ์ ์ด๊ธฐ ์์น ๋ฐ ๋ฐฉํฅ
		int cur = dice[3][1];							// ์ฃผ์ฌ์ ์๋ซ ๋ฉด ์ด๊ธฐํ
		while(k-- > 0) {								// k๋ฒ ๋ฐ๋ณต
			int newr = r + dr[d];
			int newc = c + dc[d];
			if(newr < 0 || newr >= n || newc < 0 || newc >= m) {	// ์ง๋ ๋ฒ์ด๋๋ฉด
				d = (d+2)%4;							// ๋ฐฉํฅ ๋ฐ๊ฟ
				newr = r + dr[d];
				newc = c + dc[d];
			}
			answer += scores[newr][newc] * nums[newr][newc];	// ์ ์ ์ฆ๊ฐ
			cur = roll(d);								// ์ฃผ์ฌ์ ๊ตด๋ฆฌ๊ธฐ
			if(cur > nums[newr][newc]) {				// ์ฃผ์ฌ์ ๋ฐฉํฅ ๊ฐฑ์ 
				d = (d+1)%4;
			}else if(cur < nums[newr][newc]) {
				d = (d+3)%4;
			}
			r = newr;									// ์ฃผ์ฌ์ ์์น ๊ฐฑ์ 
			c = newc;
		}
		bw.write(answer + "\n");
		bw.flush();
		bw.close();
		br.close();
	}
	static int roll(int d) {
		int temp;
		switch(d) {										// ์๋ ฅ๋ฐ์ ๋ฐฉํฅ(์ฐํ์ข์)๋๋ก ์ฃผ์ฌ์ ์ ๊ฐ๋์ ๊ฐ์ ๋ฐ๊ฟ
		case 0:
			temp = dice[1][2];
			dice[1][2] = dice[1][1];
			dice[1][1] = dice[1][0];
			dice[1][0] = dice[3][1];
			dice[3][1] = temp;
			break;
		case 1:
			temp = dice[3][1];
			dice[3][1] = dice[2][1];
			dice[2][1] = dice[1][1];
			dice[1][1] = dice[0][1];
			dice[0][1] = temp;
			break;
		case 2:
			temp = dice[1][0];
			dice[1][0] = dice[1][1];
			dice[1][1] = dice[1][2];
			dice[1][2] = dice[3][1];
			dice[3][1] = temp;
			break;
		case 3:
			temp = dice[0][1];
			dice[0][1] = dice[1][1];
			dice[1][1] = dice[2][1];
			dice[2][1] = dice[3][1];
			dice[3][1] = temp;
			break;
		}
		return dice[3][1];
	}
}
```

# **๐Description**

> ์ฃผ์ฌ์ ์ ๊ฐ๋๋ฅผ ํญ์ ์ ์งํ๋ฉด์ ์๋ซ๋ฉด์ ์ด๋ค ์๊ฐ ์๋์ง ํญ์ ์ ์ ์๊ฒ ํ๋ค.
์นธ๋ง๋ค ํ๋ํ  ์ ์๋ ์ ์๋ฅผ BFS๋ฅผ ํตํด ๋ฏธ๋ฆฌ ๊ณ์ฐํด ๋์๋ค.
> 

# **๐Related Issues**

> ์ฃผ์ฌ์๋ฅผ ๊ตด๋ฆฌ๋ ๊ฒ์ ์์ํ๋ฉด์ ๊ตฌํํ๋๊ฒ ์ด๋ ค์ ๋ค.
์ค๊ฐ์ ์ฃผ์ฌ์์ ์๋ซ๋ฉด์ด ๊ฐ์ผ๋ฉด ์๋ฉด์ ๋ชจ์๋ ๊ฐ๋ค๊ณ  ์ฐฉ๊ฐํ์๋ค.
์ฃผ์ฌ์๋ฅผ ๊ตด๋ฆฌ๋ ์ฝ๋๋ฅผ ์ข ์ฌ์ฌ์ฉํ  ์ ์๊ฒ ์์ฑํ์ผ๋ฉด ์ข์์ ๊ฒ ๊ฐ๋ค.
> 

# **๐Resource**

| Memory | Time |
| --- | --- |
| 14424KB | 140ms |