import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;

public class Main{
    static int N;//격자
    static int M;//상어의 수
    static int k; // 향 지속시간
    static int[][] resttime;
    static int[][] smell;//향 주인
    static int[][][] priority;//우선순위
    static Shark[] shark;
    static int[] dr = {0,-1,1,0,0};//1234
    static int[] dc = {0,0,0,-1,1};//1234

    static class Shark{
        int r;
        int c;
        int d;//방향


        Shark(int r,int c, int d){
            this.r = r;
            this.c = c;
            this.d = d;
        }
    }

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        String[] input = br.readLine().split(" ");
        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);
        k = Integer.parseInt(input[2]);

        resttime = new int[N+1][N+1];
        smell = new int[N+1][N+1];
        priority = new int [M+1][5][4];
        shark = new Shark[M+1];

        //격자에 상어 배정
        for(int i=1;i<=N;i++){
            input = br.readLine().split(" ");
            for(int j= 1; j<=N;j++){//i행j열
                int n = Integer.parseInt(input[j-1]);

                if(n>0){
                    shark[n] = new Shark(i,j,0);//일단 방향 0으로 설정
                    resttime[i][j] = k;//현상어칸 잔여시간 등록
                    smell[i][j] = n;//냄새주인등록
                }
            }
        }

        //초기 방향
        input = br.readLine().split(" ");
        for(int i=1;i<=M;i++){
            shark[i].d = Integer.parseInt(input[i-1]);
        }

        for(int i=1;i<=M;i++){
            for(int j=1;j<=4;j++){
                input = br.readLine().split(" ");
                for(int k= 0 ; k < 4; k++){
                    priority[i][j][k] = Integer.parseInt(input[k]);//i번째 상어 j방향 우선순위 k k k k
                }
            }
        }

        bw.write(solve()+"\n");
        bw.flush();

    }

    public static int solve(){
        int time = 0;
        while (true){

            int count =0; //생존 상어 수

            //생존상어 마리수 체크
            for(int m=1;m<=M;m++){
                if(shark[m]!=null){//죽은 상어인지 확인
                    count ++;
                }
            }

            //1번 상어만 생존
            if(count ==1 && shark[1] != null){
                return time;
            }

            //1000회 이샹 루프
            if(time>=1000){
                return -1;
            }


            //상어 이동 기록
            int[][] tmp = new int[N+1][N+1];

            //생존상어 이동
            for(int m = 1; m<= M; m++){
                if(shark[m] != null){
                    moveShark(tmp,m);
                }
            }

            //냄새 줄이기
            for(int i=1; i<=N;i++){
                for(int j=1;j<=N;j++){
                    if(resttime[i][j] > 0){
                        resttime[i][j]--;
                    }
                    if(resttime[i][j]==0){
                        smell[i][j]=0;//새 상어 들어올 수 있도록
                    }
                }
            }
            //상어공간 냄새 세팅
            for(int i=1;i<=N;i++){
                for(int j=1;j<=N;j++){
                    if(tmp[i][j]>0){
                        resttime[i][j] = k;
                        smell[i][j] = tmp[i][j];
                    }
                }
            }
            time ++;
        }
    }

    public static void moveShark(int[][] tmp,int m){
        int nr = 0;
        int nc = 0;
        int d = 0;

        boolean flag = false;

        //냄새 없을 때 nr 및 nc 지정
        for(int i=0;i<4;i++){
            //m번상어의 우선순위, m번상어가 보고있는 방향, i번째 우선순위
            d = priority[m][shark[m].d][i];
            nr = shark[m].r+dr[d];
            nc = shark[m].c+dc[d];

            if((1<=nr && nr<=N) && (1<=nc && nc<=N) && smell[nr][nc] ==0){
                flag = true;
                break;
            }
        }

        //냄새 없는 곳 존재하지 않을 때 다시 사방 보면서 내냄새 있는 곳 있나 확인
        if(!flag){
            for(int i=0; i< 4; i++){
                d = priority[m][shark[m].d][i];
                nr = shark[m].r + dr[d];
                nc = shark[m].c + dc[d];

                if((1<=nr&&nr<=N) && (1<=nc && nc<=N) && smell[nr][nc] == m){
                    flag=true;
                    break;
                }
            }
        }
        if(flag) {
            if (tmp[nr][nc] == 0) {//0일때 드가는 상어가 무조건 서열1위
                tmp[nr][nc] = m;
                shark[m].r = nr;
                shark[m].c = nc;
                shark[m].d = d;
            } else {
                shark[m] = null;//싸움에서 패배한 상어
            }
        }
    }
}