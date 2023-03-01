
![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%202146&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> BOJ 2146 다리 만들기
> 

<br>
<br>

# 💻**Code**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	
	public static void main(String[] args) throws Exception {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    	int n = Integer.parseInt(br.readLine());
    	int[][] map = new int[n][n]; 		// 지도
    	for(int i=0; i<n; i++) {
    		StringTokenizer st = new StringTokenizer(br.readLine());
    		for(int j=0; j<n; j++) {
    			map[i][j] = Integer.parseInt(st.nextToken());
    		}
    	}
    	int[] dr = {-1, 0 ,1 ,0};
    	int[] dc = {0, -1, 0 ,1};
    	int count = 0; 						// 대륙의 개수
    	boolean[][] visited = new boolean[n][n];
    	for(int i=0; i<n; i++) {			// 서로 다른 대륙임을 표현하기 위해 대륙에 대륙 번호를 표시
    		for(int j=0; j<n; j++) {
    			if(visited[i][j] || map[i][j] == 0) { // 이미 표시했거나 바다면 건너뜀
    				continue;
    			}
    			Queue<int[]> q = new LinkedList<>();
    			q.add(new int[] {i, j});
    			count++;					// 대륙 개수 ++
    			while(!q.isEmpty()) {
    				int[] pair = q.poll();
    				if(visited[pair[0]][pair[1]]) {
    					continue;
    				}
    				visited[pair[0]][pair[1]] = true;
    				map[pair[0]][pair[1]] = count;	// 대륙 번호 표시
    				for(int k=0; k<4; k++) {
    					int newx = pair[0] + dr[k];
    					int newy = pair[1] + dc[k];
    					if(newx < 0 || newx >= n || newy < 0 || newy >= n) {
    						continue;
    					}
    					if(map[newx][newy] == 0) {
    						continue;
    					}
    					q.add(new int[] {newx, newy});
    				}
    			}
    		}
    	}
    	int answer = n*n; // 다리의 길이
    	for(int i=0; i<n; i++) {
    		for(int j=0; j<n; j++) {
    			if(map[i][j] == 0) { // 바다면 건너뜀
    				continue;
    			}
    			Queue<int[]> q = new LinkedList<>();	// 현재 칸 주변 바다를 저장 
    			for(int k=0; k<4; k++) {
    				int newi = i+dr[k];
    				int newj = j+dc[k];
    				if(newi<0 || newi>=n || newj<0 || newj>=n) {
    					continue;
    				}
    				if(map[newi][newj] == 0) {
    					q.add(new int[] {newi, newj, 0}); // 반복문을 돌아도 저장된 바다가 없다면 대륙의 가장자리가 아니므로 탐색 X
    				}
    			}
    			visited = new boolean[n][n];
    			while(!q.isEmpty()) {
    				int r = q.peek()[0];
    				int c = q.peek()[1];
    				int d = q.poll()[2];
    				if(map[r][c] > 0 && map[r][c] != map[i][j]) { // 대륙이면서 탐색을 출발한 대륙이 아님
    					answer = Math.min(answer, d); 			// 최소값 비교
    					break;									// bfs니까 종료
    				}
    				if(visited[r][c] || d >= answer || map[r][c] == map[i][j]) { // 이미 방문했거나, 더 짧은 다리를 이미 찾았거나, 탐색을 출발한 대륙이면 건너뜀
    					continue;
    				}
    				visited[r][c] = true;				// 방문 처리
    				for(int k=0; k<4; k++) {
    					int newr = r+dr[k];
    					int newc = c+dc[k];
    					if(newr<0 || newr>=n || newc<0 || newc>=n) {
    						continue;
    					}
    					q.add(new int[] {newr, newc, d+1});
    				}
    			}
    		}
    	}
    	bw.write(answer + "\n");
    	bw.flush();
        bw.close();
        br.close();
    }
}
```

<br>
<br>

# **🔑Description**

> 먼저 지도에 대륙별로 번호를 붙여서 나중에 서로 다른 대륙임을 알 수 있도록 했다. 
다시 지도를 탐색하면서 주변에 바다가 있는 대륙 칸에서 바다로 BFS를 한다. 도중에 다른 대륙을 만나면 탐색을 중지하고 거리를 저장한다.
다음 대륙 칸에서도 BFS를 반복하지만 이전에 저장된 거리보다 멀리 탐색하지는 않도록 한다.
> 

<br>
<br>

# **📑Related Issues**

> 대륙별로 번호를 붙일 때도 대륙 간의 다리를 탐색할 때도 BFS를 사용하는데 두 과정을 하나로 합치고 싶었지만 쉽지 않았다.
주변에 바다가 있는 대륙 칸에서 시작할 지 아니면 주변에 대륙이 있는 바다 칸에서 BFS를 시작할지 고민했었다.
> 

<br>
<br>

# **🕛Resource**

| Memory | Time |
| --- | --- |
| 139376KB | 316ms |