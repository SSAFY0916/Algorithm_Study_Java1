![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%202151&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> BOJ 2151 거울설치

# 💻**Code**

```java
import java.io.*;
import java.util.*;

public class Main {
	static class Pos implements Comparable<Pos>{
		int x;
		int y;
		int dir;
		int cnt;
		
		public Pos(int x, int y, int dir,int cnt) {
			super();
			this.x = x;
			this.y = y;
			this.dir = dir;
			this.cnt = cnt;
		}

		@Override
		public int compareTo(Pos o) {
			return this.cnt - o.cnt;
		}
	}
	static int N, start[] = {-1,-1};//시작 거울을 하나만 찾기 위해 -1로 초기화.
	static char graph[][];
    // 해당 좌표에 대해서 4방향으로 갈 수 있다.(빛이 지나가지 않은 방향이라면 지나갈 수 있다.)
	static boolean visit[][][];
	
	// 상 우 하 좌(+2 mod 4 -> 방향을 상 -> 우,좌 2방향으로 바꿔주기 위해)
    // 상하좌우보다 상 우 하 좌로 놓는 것이 더 편하다.
	static int dx[] = {-1,0,1,0};
	static int dy[] = {0,1,0,-1};
	
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		N = Integer.parseInt(in.readLine());
		visit = new boolean[N][N][4];
		graph = new char[N][N];
		for(int i=0;i<N;i++) {
			graph[i] = in.readLine().toCharArray();
			for(int j=0;j<N;j++) {
				if(graph[i][j] == '#' && start[0] == -1) {//초기 거울 위치 세팅
					start[0] = i;
					start[1] = j;
                    graph[i][j] = '.';// 처음 거울 위치 . 으로 바꾸어줌
				}
			}
		}
		/*
		 * 1. 문에서 시작 - 직선 방향(4방향)으로 이동
		 * 2. 거울 만나면 90도 꺾이거나(좌우)(거울개수 cnt++), 거울을 지나치거나
		 *     빛이 통과할 수 없는 곳이면 종료.
		 * 반복
		 * 3. 새로운 문 만나면 종료(그동안 지나온 거울 개수 출력)
		 * */
        // 거울 개수를 적게 사용하면서 탐색해야 하기 때문에 PQ 사용
		PriorityQueue<Pos> pq = new PriorityQueue<>();
		
        // 처음 거울에서 사방으로 직진탐색
		for(int i=0;i<4;i++) {
			int nx = start[0] + dx[i];
			int ny = start[1] + dy[i];
			if(nx<0||nx>=N||ny<0||ny>=N) continue;
			if(graph[nx][ny] == '*') continue;
			pq.offer(new Pos(start[0],start[1],i,0));
			visit[start[0]][start[1]][i] = true;
		}
		
		while(!pq.isEmpty()) {
			Pos temp = pq.poll();
			int x = temp.x;
			int y = temp.y;
			int dir = temp.dir;
			int cnt = temp.cnt;
			
			if(graph[x][y] == '#') {//새로운 문 만나면 종료
				System.out.println(cnt);
				return;
			}
			visit[x][y][dir] = true;
			
			int nx = x+dx[dir];
			int ny = y+dy[dir];
			if(nx<0||nx>=N||ny<0||ny>=N) continue;
			if(graph[nx][ny] == '*') continue;
			if(visit[nx][ny][dir]) continue;
			
			if(graph[nx][ny] == '!') {
                    // 예) (거울발견) 상0->좌1,우3 
					pq.offer(new Pos(nx,ny,(dir+1)%4,cnt+1));
					pq.offer(new Pos(nx,ny,(dir+3)%4,cnt+1));
			}
			pq.offer(new Pos(nx,ny,dir,cnt));// 현재방향으로 가고	
		}	
	}
}
/*
    처음에 PQ안써서 틀렸다.
    visit을 방향을 고려안하고 지나온 길이어도 
    다른 방향으로 향하는 것이라면 지나갈 수 있음
*/

```

# **🔑Description**

> 거울을 적게 쓰면서 이동해야했기 때문에 Priority Queue를 사용해야했고
>
> 한 지점을 지나갈때에 왼쪽으로 가는 방향, 오른쪽으로 가는 방향, 위로 가는 방향. 
>
> 즉, 상하좌우 모두 이동 가능하므로 한 점에 대해 visit을 상하좌우로 관리해줬다. 
>
> 거울을 만났을 때 상 -> 좌, 우 모두 가도록 + 거울 설치안했을 때에는 직진으로도 갈 수 있으므로 총 3번 queue에 넣어주었다.

# **📑Related Issues**

> Priority queue로 거울을 적게 썼을때에 이동을 먼저하도록 했다.
>
> Visit도 여러방향이 가능하다는 것을 늦게 깨달았다.

# **🕛Resource**

| Memory    | Time    |
| --------- | ------- |
| 15132`KB` | 140`ms` |