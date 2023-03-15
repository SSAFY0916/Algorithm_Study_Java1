package bj_12100_2048easy;
//설계시작=10:50
//설계종료=14:15
//구현종료=15:50
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

import java.io.*;
import java.util.StringTokenizer;

public class yeonwoo {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static int[] dr = {-1,0,1,0};
	static int[] dc = {0,-1,0,1};
	
	
	static int n;
	
	static int[][] map;
	static int[][] cloneMap;	//dfs가 maxDepth에 도달하면 cloneMap -> map 하여 map 초기화
	static boolean[][] combined; //결합 했는지 체크용
	
	static int maxBlock = Integer.MIN_VALUE; //최대블록값 계산용
	
	static int[] seq = new int[5];//무빙순서 담는용
	

	public static void main(String[] args) throws IOException{
		//기본 게임 판 구성
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

		//모든 경우의 수 탐색
		dfs(0);
		System.out.println(maxBlock);
		
	}
	static void dfs(int depth) {
		//5번의 이동 순서 구성 완료
		if(depth==5) {
			
			//map 원상복구
			for(int i=0;i<n;i++) {
				map[i] = cloneMap[i].clone();
			}
			
			//순서대로 move
			for(int i=0;i<5;i++) {
				int dir = seq[i];
				move(dir);
			}
			return;
		}
		
		//이동순서 정하기
		for(int i=0;i<4;i++) {
			seq[depth] = i;
			dfs(depth+1);
		}
		
	}
	//이동(방향)
	static void move(int d) {
		combined = new boolean[n][n];//결합 체크용 배열 원상 복구
		
		//위로 이동
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
		//아래로 이동
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
		//좌로 이동
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
		
		//우로이동
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
	//위치변경
	static void changePosition(int fromR, int fromC, int toR, int toC) {
//		System.out.printf("fromR = %d, fromC = %d, toR = %d, toC = %d\n",fromR,fromC,toR,toC);
//		if(fromR == toR && fromC == toC) return;
		int val = map[fromR][fromC];
		map[fromR][fromC] = 0;
		map[toR][toC] = val;
		
		
		
	}

	//한방향 탐색
	static int[] shoot(int r, int c, int d) {
		int nr = r+dr[d];
		int nc = c+dc[d];
		if(!isIn(nr,nc)) return new int[]{nr,nc};//범위초과
		if(map[nr][nc]!=0) return new int[] {nr,nc};//다른블록
		return shoot(nr,nc,d);
	}
	//배열내부인지 확인
	static boolean isIn(int r, int c) {
		return r>=0 && r<n && c>=0 && c<n;
	}
	
	//디버깅용 배열 출력
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
