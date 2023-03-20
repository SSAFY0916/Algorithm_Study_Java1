![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%201941&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> BOJ 1941 소문난 칠공주

# 💻**Code**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.Queue;

public class Main {

	// 조합을 저장할 배열
	static int[][] comb;
	// 격자를 저장할 배열
	static boolean[][] types;
	static int[] dr = {-1, 0, 1, 0};
	static int[] dc = {0, -1, 0, 1};
	// 공주의 수
	static int countPrincess = 7;
	// 격자의 크기
	static int n = 5;
	static int answer;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		types = new boolean[n][n];
		for(int i=0; i<n; i++) {
			String line = br.readLine();
			for(int j=0; j<n; j++) {
				types[i][j] = line.charAt(j) == 'S';
			}
		}

		comb = new int[countPrincess][2];
		combination(0, 0);
		bw.write(answer + "\n");
		bw.flush();
		bw.close();
		br.close();
	}

	// 조합을 만드는 메소드
	static void combination(int count, int start) {
		if(count == countPrincess) {
			if(simulate()) {
				answer++;
			}
			return;
		}
		for(int i=start; i<n*n; i++) {
			comb[count][0] = i/n; // 행
			comb[count][1] = i%n; // 열
			combination(count+1, i+1);
		}
	}

	static boolean simulate() {
		boolean[] visited = new boolean[countPrincess];
		Queue<Integer> q = new ArrayDeque<>();
		q.add(0); // 1번 위치 큐에 넣음
		while(!q.isEmpty()) { // 인접한 칠공주들을 bfs로 탐색
			int cur = q.poll();
			if(visited[cur]) {
				continue;
			}
			visited[cur] = true;
			for(int i=0; i<4; i++) {
				int newr = comb[cur][0] + dr[i]; // 인접한 행
				int newc = comb[cur][1] + dc[i]; // 인접한 열
				for(int j=0; j<countPrincess; j++) {
					if(comb[j][0] == newr && comb[j][1] == newc) {
						q.add(j);
						break;
					}
				}
			}
		}
		for(boolean flag : visited) { // 탐색이 안 된 칠공주가 한 명이라도 있다면 false 리턴
			if(!flag)
				return false;
		}

		int s_count = 0; // 이다솜파 count
		for(int i=0; i<countPrincess; i++) {
			if(types[comb[i][0]][comb[i][1]]) {
				s_count++;
			}
		}
		return s_count >= 4;
	}
}
```

# **🔑Description**

> 격자의 인덱스를 행,열 두가지가 아니라 행\*5+열 한 가지 값으로 했었다.\

```
0 1 2 3 4
5 6 7 8 9
...
```

> 위와 같이 인덱스를 정하고 조합을 만들었었는데 인접한 인덱스를 구할 때 4번과 5번 경우가 서로 인접하다고 판별해서 자꾸 틀렸었다.

# **📑Related Issues**

> bfs 부분이 비효율적인거 같다

# **🕛Resource**

| Memory    | Time    |
| --------- | ------- |
| 77912`KB` | 516`ms` |
