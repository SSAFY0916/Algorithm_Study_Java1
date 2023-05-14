![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:FF658D,100:FFCB32&text=BOJ%2014466&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 14466 소가 길을 건너간 이유 6](https://www.acmicpc.net/problem/14466)

<br>
<br>

# **💻Code**

```java
import java.io.*;
import java.util.*;

import org.omg.CORBA.INTERNAL;

public class Main {
	
	/*
	 * 소가 길을 건나간 이유
	 * N x N
	 * 인접 목초지 자유롭게 이동한다. 
	 * 일부는 길을 건너간다. 
	 * 
	 * */
	static int N, K, R;
	// 상하좌우
	static int dx[] = {-1,1,0,0};
	static int dy[] = {0,0,-1,1};
	static int graph[][];
	static boolean visited[][];
	static List<int[]> bridge[][];
	static List<int[]> cow;

	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		st = new StringTokenizer(in.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		R = Integer.parseInt(st.nextToken());
		graph = new int[N][N];
		bridge = new ArrayList[N][N];
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				bridge[i][j] = new ArrayList<>();
			}
		}
		cow = new ArrayList<>();
		int r1,c1,r2,c2;
		for(int i=0;i<R;i++) {
			st = new StringTokenizer(in.readLine());
			r1 = Integer.parseInt(st.nextToken())-1; 
			c1 = Integer.parseInt(st.nextToken())-1;
			r2 = Integer.parseInt(st.nextToken())-1;
			c2 = Integer.parseInt(st.nextToken())-1;
			bridge[r1][c1].add(new int[] {r2, c2});
			bridge[r2][c2].add(new int[] {r1, c1});
		}

		int r,c;
		for(int i=0;i<K;i++) {
			st = new StringTokenizer(in.readLine());
			r = Integer.parseInt(st.nextToken())-1;
			c = Integer.parseInt(st.nextToken())-1;
			
			cow.add(new int[] {r, c});
		}
		
		int cnt=0;
		// 소가 한마리씩 길을 건넌다
		for(int i=0;i<K;i++) {
			visited = new boolean[N][N];
			moveCow(cow.get(i));
			
			for(int j=i;j<K;j++) {
				int temp[] = cow.get(j);
				// 방문하지 않은 곳에 소가 있으면 만날 수 없음.
				if(!visited[temp[0]][temp[1]]) cnt++;
			}
		}
		
		System.out.println(cnt);
		
	}

	private static void moveCow(int[] pos) {
		int x = pos[0];
		int y = pos[1];
		visited[pos[0]][pos[1]] = true;
		
		for(int dir=0;dir<4;dir++) {
			int nx = x+dx[dir];
			int ny = y+dy[dir];
			
			if(nx<0 || nx>=N || ny<0 || ny>=N) continue;
			if(visited[nx][ny]) continue;
			
//			if(bridge[x][y].contains(new int[] {nx,ny})) continue; ;
			boolean isBridge = false;
			for(int k=0;k<bridge[x][y].size();k++) {
				int temp [] = bridge[x][y].get(k);
				if(temp[0] == nx && temp[1] == ny) {
					isBridge = true;
					break;
				}
			}
			
			if(isBridge) continue;
			
			moveCow(new int[] {nx, ny});
		}
		
	}
}

```

<br>
<br>

# **🔑Description**

> 

<br>
<br>

# **📑Related Issues**

> 

<br>
<br>

# **🕛Resource**

| Memory    | Time    |
| --------- | ------- |
| `28524`KB | `320`ms |
