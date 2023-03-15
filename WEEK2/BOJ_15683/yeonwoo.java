package bj_15683_가시;
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {
	static BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
	static int[] dr = {-1,0,1,0};
	static int[] dc = {0,-1,0,1};
	static int res = Integer.MAX_VALUE;
	public static void main(String[] args) throws IOException{
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int n = Integer.parseInt(st.nextToken());//가로
		int m = Integer.parseInt(st.nextToken());//세로
		int[][] map = new int[n][m];//맵
		
		//init map, cameraList
		ArrayList<int[]> cameraList = new ArrayList<>();//[r,c,type]
		for(int i=0;i<n;i++) {
			st = new StringTokenizer(br.readLine());//
			for(int j=0;j<m;j++) {
				int cur = Integer.parseInt(st.nextToken());
				map[i][j] = cur;
				if(cur>0&&cur<6) {//cctv
					cameraList.add(new int[] {i,j,cur});
				}
			}
		}
		
//		int[][] cameraArray = (int[][]) cameraList.toArray();//각 ㄱ카메라 r,c,type
		
		int[][] cameraArray = new int[cameraList.size()][3];
		int lenCameras = cameraArray.length;
		for(int i=0;i<lenCameras;i++) {
			cameraArray[i] =cameraList.get(i); 
		}
		
		int[] cameraDir = new int[lenCameras];//각 카메라 방향
		
		//디버깅용 카메라리스트 출력
//		for(int i=0;i<cameraList.size();i++) {
//			System.out.printf("%d번 카메라 타입= %d, r=%d, c=%d\n", i,cameraList.get(i)[2],cameraList.get(i)[0],cameraList.get(i)[1]);
//		}
		
		
		exhaustiveSearch(0,lenCameras,cameraArray,cameraDir,map);
		System.out.println(res);
	}
	static void exhaustiveSearch(int idx, int lenCameras, int[][] cameraArray,int[] cameraDir,int[][] map) {
//		pp("ex");
		if(idx==lenCameras) {//모든 카메라 방향 설정 완료
			//map의 복사배열 temp 생성
			int[][] temp = new int[map.length][map[0].length];
			for(int i=0;i<temp.length;i++) {
				temp[i] = map[i].clone();
			}
			
			for(int i=0;i<cameraDir.length;i++) {//카메라 방향 배열 순회
				//caemraArray에서 r,c,type 꺼내서 temp에서 cameraDir[i]방향으로 시뮬레이팅
				int[] cameraInfo = cameraArray[i];//i번째 카메라
				int cameraR = cameraInfo[0];
				int cameraC = cameraInfo[1];
				int cameraType = cameraInfo[2];
				int d = cameraDir[i];//i번째 카메라의 방향
				//카메라 쏜다
				fill(temp,cameraR,cameraC,cameraType,d);
			}
//			printarr(temp);
			//min = temp 빈공간 체크
			int min = 0;
			for(int i=0;i<temp.length;i++) {
				for(int j=0;j<temp[0].length;j++) {
					if(temp[i][j]==0) min++;
				}
			}
			//min =min( res,min)
//			System.out.println("min="+min);
			res = Math.min(res, min);
			return;
		}
		
		for(int i=0;i<4;i++) {
			cameraDir[idx]=i;
			exhaustiveSearch(idx+1,lenCameras,cameraArray,cameraDir,map);

			//TODO camera[idx]가 5번이면 안돌리는 코드 추가
			if(cameraArray[idx][2]==5) break;//넣으면 더 걸리는데 논리적으로 넣는게 맞지 않나요?
		}
		
		
		
	}
	static void fill(int[][] map, int sr, int sc, int cameraType,int d) {
		if(cameraType == 1) {
			shoot(map,sr,sc,d);
		}
		// 타입별로 얼마나 쏴야되는지 지정
		else if(cameraType==2) {
			shoot(map,sr,sc,d);
			shoot(map,sr,sc,(d+2)%4);
		}
		else if(cameraType==3) {
			shoot(map,sr,sc,d);
			shoot(map,sr,sc,(d+1)%4);
		}
		else if(cameraType==4) {
			for(int i=0;i<3;i++) {
				shoot(map,sr,sc,d);
				d=(d+1)%4;
			}
		}
		else if(cameraType==5) {
			for(int i=0;i<4;i++) {
				shoot(map,sr,sc,d);
				d=(d+1)%4;
			}
		}
	}
	
	static void shoot(int[][] map, int sr, int sc, int d) {
		int nr = sr;
		int nc = sc;
		while(nr<map.length && nr>=0 && nc<map[0].length && nc>=0) {
			if(map[nr][nc]==6) break;//벽이면 종료
			map[nr][nc] = 7;

			nr = nr+dr[d];
			nc = nc+dc[d];	
		}
	}
}
