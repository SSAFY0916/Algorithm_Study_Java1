package bj_23288_주굴2;
import java.io.*;
import java.util.StringTokenizer;

import java.util.Queue;
import java.util.LinkedList;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//	static String[] dd = {"상","좌","하","우"};
	//상좌하우
	static int[] dr = {-1,0,1,0};
	static int[] dc = {0,-1,0,1};
	
	static int d = 3;//방향, 초깃값 우측(3)
	
	static int n,m,k;//행수,열수
	static int[][] map;//판
	
	static int result = 0; //최종점수
	
	static boolean[][] visited;
	public static void main(String[] args) throws IOException{
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		map = new int[n+1][m+1]; //1부터 시작
		
		//map 초기화
		for(int i=1;i<=n;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=1;j<=m;j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		//주사위 생성
		Marble m = new Marble(1,1);
		for(int i=0;i<k;i++) {//k번 구를것
//			System.out.println("dd = "+dd[d]);
			m.degool(d);//d방향으로 구르기
//			System.out.println("m.r, m.c = "+m.r+","+m.c);
			int val = getPoint(m.r,m.c);//주사위 r,c 포지션의 칸점수 계산후합산
//			System.out.println("val="+val);
			result+=val;
			nextDir(m);
			
			
		}
		
		System.out.println(result);
	}
	static void nextDir(Marble m) {
		int r = m.r;
		int c = m.c;
		int bottomOfMarble = m.side[1];
		
		
		int b = map[r][c];
		if(bottomOfMarble> b) d = (d+3)%4; //우로90도
		else if(bottomOfMarble<b) d= (d+1)%4;//좌로90도
		
		int nr = r+dr[d];
		int nc = c+dc[d];
		if(!isIn(nr,nc)) d = (d+2)%4;
		
	}
	static int getPoint(int sr,int sc) {//칸점수계산
		int c = 0;
		int b = map[sr][sc];
//		System.out.println(" b= "+b);
		c+=bfs(sr,sc);
		
		return b*c;
	}
	static int bfs(int sr, int sc) {
		int res = 1;//최초값은 들어있으니까
		visited = new boolean[n+1][m+1];//방문처리용
		
		Queue<int[]> q= new LinkedList<>();
		q.offer(new int[] {sr,sc});//최초값
		visited[sr][sc] = true;
		
		int turn = -1;
		int size;
		while(!q.isEmpty()) {
			turn ++;
			size = q.size();
			while(size-->0) {
				int[] cur = q.poll();
				int curR = cur[0];
				int curC = cur[1];
				
				int nr,nc;
				for(int i=0;i<4;i++) {
					nr = curR+dr[i];
					nc = curC+dc[i];
					
					if(!isIn(nr,nc)) continue; //범위초과
					if(visited[nr][nc]) continue;//방문초과
					if(map[nr][nc]!=map[sr][sc]) continue;//값다름
					
					visited[nr][nc] = true;
					q.offer(new int[] {nr,nc});
					res++;
				}
			}
		}
//		System.out.println("bfs결과 = "+res);
		return res;
		
		
	}

	//범위에 있는지
	static boolean isIn(int r, int c) {
		return r>=1 && r<=n && c>=1 && c<=m;
	}
	
	static class Marble{
		//{top 0, bot 1, left 2, right 3, front 4, back 5}
		int[] side;
		int r, c; //주사위 r, c
		
		public Marble(int sr, int sc) {
			this.side = new int[] {1,6,4,3,2,5};
//			this.side = new int[] {top,bot,left,right,front,back};
			this.r = sr;
			this.c = sc;
		}
		
		void degool(int d) {
			//새 포지션
			this.r = this.r + dr[d];
			this.c = this.c + dc[d];
			
			int[] newSide = new int[6];
			if(d==0) {//상
				// 방향에 대한 면처리
				newSide[0] = side[5]; //상
				newSide[1] = side[4]; //하
				newSide[2] = side[2]; //좌
				newSide[3] = side[3]; //우
				newSide[4] = side[0]; //앞
				newSide[5] = side[1]; //뒤
			}else if(d==1) {//좌
				// 방향에 대한 면처리
				newSide[0] = side[3]; //상
				newSide[1] = side[2]; //하
				newSide[2] = side[0]; //좌
				newSide[3] = side[1]; //우
				newSide[4] = side[4]; //앞
				newSide[5] = side[5]; //뒤
			}else if(d==2) {//하
				// 방향에 대한 면처리
				newSide[0] = side[4]; //상
				newSide[1] = side[5]; //하
				newSide[2] = side[2]; //좌
				newSide[3] = side[3]; //우
				newSide[4] = side[1]; //앞
				newSide[5] = side[0]; //뒤
			}else if(d==3) {//우
				//TODO 방향에 대한 면처리
				newSide[0] = side[2]; //상
				newSide[1] = side[3]; //하
				newSide[2] = side[1]; //좌
				newSide[3] = side[0]; //우
				newSide[4] = side[4]; //앞
				newSide[5] = side[5]; //뒤
			}else {
				throw new RuntimeException("방향이상");
			}
			side = newSide;//새 면으로 바꾸기
		}
	}
}
