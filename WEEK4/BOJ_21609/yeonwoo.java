package d230227.bj_21609_상어중학교;
//13:10 시작
/*
N * N 격자에서 진행
모든 칸에 블록 있음
블록종류: 검, 무, 일반(M가지색 1~M으로 표현)
    검= -1, 무 = 0, 일 = [1~M]
인접 = 사방탐색

#그룹
-1개 이상의 동일색 일반블록
-검은불록 x
-무지개블록 제한x

-그룹은 블록2개 이상 있어야함
-bfs연결이어야함
-그룹기준은 최저행 최저열

#오토
1.가장 큰 ㄱ룹 찾기
동점처리 = 무지개블록수>가장큰행>가장큰열

2.1의 모든 블록 제거, (제거된 블록 수)^2 획득

3.중력
검정은 그대로 부유, 나머지는 중력
4.회전
5.중력
 */
//13:30
//13:30 ~ 14:10 구현
//15:00~15:40 구현

import java.io.*;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.Queue;
import java.util.ArrayDeque;
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int[] dr = {-1,0,1,0};
    static int[] dc = {0,-1,0,1};
    static int N,M;
    static int[][] map;
    static int[][] tempMap;
    static boolean[][] visited;

    //maxGroup용 변수 시작
    static ArrayList<int[]> maxGroup = new ArrayList<>();//블록좌표들
    static int maxRainBowBlock = 0;//무지개 블록 수
    //maxGroup용 변수 끝

    static int NR, NC;
    static int finalPoint= 0;
    public static void main(String[] args) throws IOException{
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[N][N];
        tempMap = new int[N][N];

        for(int i=0;i<N;i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<N;j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        while(true) {
            //새 턴 시작
            //최대그룹찾기
            findMax();
            if (maxGroup.size() == 0) {//더이상 그룹이 없다면 게임 종료
                break;
            }

            removeMax();//맥스그룹제거와 함께 점수처리

            //중력
            gravity();


            //회전
            rotate();

            //중력
            gravity();

        }
        System.out.println(finalPoint);
    }
    static void rotate(){
        for(int c=N-1;c>=0;c--){
            for(int r=0;r<N;r++){
                tempMap[N-1-c][r] = map[r][c];
            }
        }
        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                map[i][j] = tempMap[i][j];
            }
        }

    }
    static void gravity(){
        for(int i=N-1;i>=0;i--){
            for(int j=0;j<N;j++){
//                System.out.printf("%d,%d\n",N,j);

                if(map[i][j] == -1) continue;//검정은 그대로 유지
                NR = i;
                NC = j;
                //검정이 아닌칸은 다음칸 찾는다 (NR,NC)에 저장된다
                shoot(i,j);

                if(NR==i && NC==j) {
                    continue;//이동불가
                }
                //시작위치값 저장
                int val = map[i][j];
                map[NR][NC] = val;//이동위치에 값 저장
                map[i][j] = -2;//-2가 공허
            }
        }
    }
    static void shoot(int r, int c){
        int nr = r + dr[2];
        int nc = c + dc[2];
        //다음칸이 범위 밖
        if(!isIn(nr,nc)){
            return;
        }
        //담칸에 뭐 있으면
        if(map[nr][nc]!=-2)
            return;
        //전역사용하여 메모리 아끼기
        NR = nr;
        NC = nc;
        shoot(NR,NC);
    }
    static void removeMax(){
        finalPoint += (int)Math.pow(maxGroup.size(),2);
        for(int[] cur: maxGroup){
            map[cur[0]][cur[1]] = -2;//소멸칸 = -2
        }
    }

    //1.가장 큰 그룹 찾기
    //동점처리 = 무지개블록수>가장큰행>가장큰열
    static void findMax(){
        visited = new boolean[N][N];
        maxGroup.clear();//

        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                if(visited[i][j]) continue;//방문
                if(map[i][j]==-1) continue; // 검정블록은 대표가 될 수없다
                if(map[i][j]==0) continue;//무지개블록은 대표가 될 수없다
                if(map[i][j]==-2) continue;//공허는 대표가 될 수 없다

                //방문한적 없으면서 일반블록만 대표가 될 수있다

                bfs(i,j,map[i][j]);

            }
        }
    }

    static void bfs(int sr, int sc, int color){
//        System.out.printf("%d, %d에서 시작\n",sr,sc);
        ArrayList<int[]> positions = new ArrayList<>();//최대그룹일시 사용
        Queue<int[]> q = new ArrayDeque<>();//bfs탐색에 사용
        int rainbowCount = 0;//그룹의 무지개 블록 수

        q.offer(new int[]{sr,sc});//시작 블록 추가
        visited[sr][sc] = true;//시작행열 방문처리

        int turn = -1;
        while(!q.isEmpty()){
            int size = q.size();
            while(size-->0){
                int[] cur = q.poll();
                positions.add(cur);
                int r = cur[0];
                int c = cur[1];

                int nr, nc;
                for(int i=0;i<4;i++){
                    nr = r+dr[i];
                    nc = c+dc[i];
                    if(!isIn(nr,nc)) continue; //범위초과
                    if(visited[nr][nc]) continue;//방문초과
                    if(map[nr][nc] == -1) continue;//검정블록
                    if(map[nr][nc]!=color && map[nr][nc]!=0) continue;//색깔초과

                    //map[nr][nc] == color || map[nr][nc] == 0
                    //그룹멤바 발견
                    if(map[nr][nc]==0) rainbowCount++;
                    visited[nr][nc] = true;

                    q.offer(new int[]{nr,nc});
                }
            }

        }
        for(int tt=0;tt<N;tt++){
            for(int kk=0;kk<N;kk++){
                if(map[tt][kk]==0){
                    visited[tt][kk]=false;
                }
            }
        }
        //그룹크기가 2가 안되면 리턴
        if(positions.size()<2) return;
        
        if(positions.size()>maxGroup.size()){//신종그룹이 기존최강보다 크다
            changeList(maxGroup,positions);//maxGroup을 position으로 변경
            maxRainBowBlock = rainbowCount;
        }else if(positions.size()==maxGroup.size()){//신규 그룹이 기존 최대 그룹과 같은 크기
            if(rainbowCount>maxRainBowBlock){//신규그룹 무지개가 더 많다
                changeList(maxGroup,positions);
                maxRainBowBlock = rainbowCount;
            }else if(rainbowCount == maxRainBowBlock){//신규그룹무지개수==최대무지개수
                /*
                여기에 r이더 큰지, c가 더 큰지 우선순위 따져야하긴 하는데
                순회순서상 나중에찾은애 r이 무조건 기존 것보다 크거나 같음
                마찬가지로 c 또한 나중쪽이 무조건 기존 왕보다 크거나 같음
                 */
                changeList(maxGroup,positions);
            }
        }

    }
    static void changeList(ArrayList<int[]> from, ArrayList<int[]> to){
        //from = to; //아래 방법으로 변경

        from.clear();
        for(int[] arr:to){
            from.add(arr);
        }
    }
    static boolean isIn(int r, int c){
        return r>=0 && r<N && c>=0 &&c<N;
    }

    static void printArr(){
        System.out.println();
        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                System.out.printf("%2d",map[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
}
