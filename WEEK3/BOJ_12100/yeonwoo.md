![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%20N&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 12100 2048(Easy)](https://www.acmicpc.net/problem/12100)

<br>
<br>

# **Code**

```java
package bj_12100_2048easy;
//설계시작=10:50
//설계종료=14:15
//구현종료=15:50
import java.io.*;
import java.util.StringTokenizer;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static int[] dr = {-1,0,1,0};
	static int[] dc = {0,-1,0,1};
	
	
	static int n;
	static int[][] map;
	static int[][] cloneMap;
	static boolean[][] combined;
	
	static int maxBlock = Integer.MIN_VALUE;
	
	static int[] seq = new int[5];
	static boolean[] visited = new boolean[5];
	//2048 게임 구현
	//1. 게임판 생성
		//1-1 행열크기(N)에 맞는 배열 생성
		//1-2 배열에 값 반영
		
	//2. 이동 구현
		//2-0 결합 초기화
		//2-1 방향선택
		//2-2 방향에 따른 블록이동순서선택
		//2-3 순서대로 이동
			//1.맵 밖이나 다음 블럭 나올떄까지 방향 탐색
			//*이동설정이란 - 기존위치 삭제 새 위치 설정
			//2-1.이 다음 좌표가 맵 밖이거나 방문좌표면 한칸전으로 이동설정하고 다음블록
			//2-2. 이 다음 좌표가 비방문이면 이 다음좌표 방문처리하고 이 다음좌표로 이동설정하고 다음블록
	

	//3. 5번 이동시켜 얻을 수 있는 가장 큰 블록 구현
	//dfs 상우하좌
	
	public static void main(String[] args) throws IOException{
		//init();
		n = Integer.parseInt(br.readLine());
		map = new int[n][n];
		cloneMap = new int[n][n];
		combined = new boolean[n][n];
		
		StringTokenizer st;
		for(int i=0;i<n;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<n;j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				cloneMap[i][j] = map[i][j];
				maxBlock = Math.max(maxBlock, map[i][j]);
			}
		}
//		//move
//		printArr(map);
//		move(3);
//		printArr(map);
//		move(3);
//		printArr(map);
		dfs(0);
		System.out.println(maxBlock);
		
	}
	static void dfs(int depth) {
		if(depth==5) {
			for(int i=0;i<n;i++) {
				map[i] = cloneMap[i].clone();
			}
			
			for(int i=0;i<5;i++) {
//				System.out.println(seq[i]+" ");
				int dir = seq[i];
				move(dir);
//				printArr(map);
			}
//			System.out.println("===================================================");
			return;
		}
		
		for(int i=0;i<4;i++) {
			seq[depth] = i;
			dfs(depth+1);
		}
		
	}
	static void move(int d) {
		combined = new boolean[n][n];
		
		if(d==0) {
			for(int r=0;r<n;r++) {
				for(int c=0;c<n;c++) {
					//1.맵 밖이나 다음 블럭 나올떄까지 방향 탐색
					int[] nextRC = shoot(r,c,d);
					int nr = nextRC[0];
					int nc = nextRC[1];
					
					//2-1.이 다음 좌표가 맵 밖이거나 방문좌표면 한칸전으로 이동설정하고 다음블록
					if(!isIn(nr,nc) || combined[nr][nc] || map[r][c]!=map[nr][nc]) {
						changePosition(r,c,nr-dr[d],nc-dc[d]);
					}else {//결합 || 자기자리이동
						if(r==nr && c==nc) continue;//자기자리이동
						changePosition(r,c,nr,nc);
						
						
						combined[nr][nc] = true;
						map[nr][nc]*=2;
						maxBlock = Math.max(map[nr][nc], maxBlock);
					}
					
				}
			}
		}
		else if(d==2) {
			for(int r=n-1;r>=0;r--) {
				for(int c=0;c<n;c++) {
					//1.맵 밖이나 다음 블럭 나올떄까지 방향 탐색
					int[] nextRC = shoot(r,c,d);
					int nr = nextRC[0];
					int nc = nextRC[1];
					
					//2-1.이 다음 좌표가 맵 밖이거나 방문좌표면 한칸전으로 이동설정하고 다음블록
					if(!isIn(nr,nc) || combined[nr][nc] || map[r][c]!=map[nr][nc]) {
						changePosition(r,c,nr-dr[d],nc-dc[d]);
					}else {//결합 || 자기자리이동
						if(r==nr && c==nc) continue;//자기자리이동
						changePosition(r,c,nr,nc);
						
						
						combined[nr][nc] = true;
						map[nr][nc]*=2;
						maxBlock = Math.max(map[nr][nc], maxBlock);
					}
					
				}
			}
		}
		else if(d==1) {
			for(int c=0;c<n;c++) {
				for(int r=0;r<n;r++) {
					//1.맵 밖이나 다음 블럭 나올떄까지 방향 탐색
					int[] nextRC = shoot(r,c,d);
					int nr = nextRC[0];
					int nc = nextRC[1];
					
					//2-1.이 다음 좌표가 맵 밖이거나 방문좌표면 한칸전으로 이동설정하고 다음블록
					if(!isIn(nr,nc) || combined[nr][nc] || map[r][c]!=map[nr][nc]) {
						changePosition(r,c,nr-dr[d],nc-dc[d]);
					}else {//결합 || 자기자리이동
						if(r==nr && c==nc) continue;//자기자리이동
						changePosition(r,c,nr,nc);
						
						
						combined[nr][nc] = true;
						map[nr][nc]*=2;
						maxBlock = Math.max(map[nr][nc], maxBlock);
					}
					
				}
			}
		}
		else if(d==3) {
			for(int c=n-1;c>=0;c--) {
				for(int r=0;r<n;r++) {
					//1.맵 밖이나 다음 블럭 나올떄까지 방향 탐색
					int[] nextRC = shoot(r,c,d);
					int nr = nextRC[0];
					int nc = nextRC[1];
					
					//2-1.이 다음 좌표가 맵 밖이거나 방문좌표면 한칸전으로 이동설정하고 다음블록
					if(!isIn(nr,nc) || combined[nr][nc] || map[r][c]!=map[nr][nc]) {
						changePosition(r,c,nr-dr[d],nc-dc[d]);
					}else {//결합 || 자기자리이동
						if(r==nr && c==nc) continue;//자기자리이동
						changePosition(r,c,nr,nc);
						
						
						combined[nr][nc] = true;
						map[nr][nc]*=2;
						maxBlock = Math.max(map[nr][nc], maxBlock);
					}
					
				}
			}
		}
		
	}
	static void changePosition(int fromR, int fromC, int toR, int toC) {
//		System.out.printf("fromR = %d, fromC = %d, toR = %d, toC = %d\n",fromR,fromC,toR,toC);
//		if(fromR == toR && fromC == toC) return;
		int val = map[fromR][fromC];
		map[fromR][fromC] = 0;
		map[toR][toC] = val;
		
		
		
	}

	
	static int[] shoot(int r, int c, int d) {
		int nr = r+dr[d];
		int nc = c+dc[d];
		if(!isIn(nr,nc)) return new int[]{nr,nc};//범위초과
		if(map[nr][nc]!=0) return new int[] {nr,nc};//다른블록
		return shoot(nr,nc,d);
	}
	static boolean isIn(int r, int c) {
		return r>=0 && r<n && c>=0 && c<n;
	}
	
	static void printArr(int[][] map) {
		System.out.println();
		
		for(int i=0;i<n;i++) {
			for(int j=0;j<n;j++) {
				System.out.printf("%d ", map[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}
}

```

<br>
<br>

# **🔑Description**

> ```java
> //2048 게임 구현
> //1. 게임판 생성
> 	//1-1 행열크기(N)에 맞는 배열 생성
> 	//1-2 배열에 값 반영
> 	
> //2. 이동 구현: move함수 안에 방향별로 if, else if 로 4개 구현하였습니다
> 	//2-0 결합 초기화
> 	//2-1 방향선택
> 	//2-2 방향에 따른 블록이동순서선택
> 	//2-3 순서대로 이동
> 		//1.맵 밖이나 다음 블럭 나올떄까지 방향 탐색
> 		//*이동설정이란 - 기존위치 삭제 새 위치 설정
> 		//2-1.이 다음 좌표가 맵 밖이거나 방문좌표면 한칸전으로 이동설정하고 다음블록
> 		//2-2. 이 다음 좌표가 비방문이면 이 다음좌표 방문처리하고 이 다음좌표로 이동설정하고 다음블록
> ```
>
>
> ```java
> //3. 5번 이동시켜 얻을 수 있는 가장 큰 블록 구현
> //dfs 상우하좌
> 
> 
> 가장 큰 블럭값은 
>     1. 처음에 Integer.MIN_VALUE 할당
> 	2. map 만들 때 Math.max(최대블록, 현재칸값)으로 확인,
>     3. 블록 합쳐질 때 Math.max(최대블록, 합쳐진값)으로 확인 하였습니다.
> ```

<br>
<br>

# **📑Related Issues**

> 블록 결합시 블록 최댓값을 갱신하는 로직을 마지막에 넣었는데
>
> 방향별 케이스를 따로 구현해놓고 한 방향에만 최댓값 갱신 로직을 넣어서
>
> 값이 제대로 나오지 않는 문제를 겪었습니다
>
> 

<br>
<br>

# **🕛Resource**

| Memory  | Time  | Implementation Time |
| ------- | ----- | ------------------- |
| 77828KB | 476ms | 5 Hour 00 Minutes   |
