//package daily.y_2023.m_03.d_16.bj_16985;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    //
    static int[][][] map = new int [5][5][5];//
    static int[][][] copy= new int [5][5][5];//?


    static int ans=Integer.MAX_VALUE;
    static int rotationCnt[] = new int[5];
    static int order[]= new int[5];//판 쌓는 순서
    static boolean check[] = new boolean[5];

    static boolean[][][] visit;//bfs용
    static int dr[] = {-1,1,0,0,0,0};
    static int dc[] = {0,0,-1,1,0,0};
    static int dz[] = {0,0,0,0,-1,1};

    static class Node{
        int r,c,z,cnt;
        public Node(int r, int c,int z, int cnt){
            this.r = r;
            this.c = c;
            this.z = z;
            this.cnt = cnt;
        }
    }
    public static void main(String[] args) throws IOException{
        //map에 인풋받기
        for(int z=0;z<5;z++){
            for(int r=0;r<5;r++){
                StringTokenizer st = new StringTokenizer(br.readLine());
                for(int c=0;c<5;c++){
                    map[z][r][c] = Integer.parseInt(st.nextToken());
                }
            }
        }
        permutation(0);
        System.out.println(ans==Integer.MAX_VALUE?-1:ans);
    }

    //order[]에 판 쌓는 순서 배정
    static void permutation(int depth){
        if(depth==5){
            copy = new int[5][5][5];
            for(int i=0;i<order.length;i++){
                setRotation(0);
            }
            return;
        }
        for(int i=0;i<5;i++){
            if(!check[i]){//아직 안쓴 판
                check[i] = true;
                order[depth] = i;
                permutation(depth+1);
                check[i]=false;
            }
        }
    }

    //층별 방향
    private static void setRotation(int idx){
        if(idx==5){//5번쨰 판까지 방향 설정 완료
            for(int i=0;i<order.length;i++){//i번층
                int pan = order[i];//i번층에 쓸 판
                int cnt = rotationCnt[i];//i번 층의 회전수

                //최전적용
                for(int r=0;r<5;r++){//행
                    for(int c=0;c<5;c++){//열
                        if(cnt==0) {//이번판 무회전
                            copy[i][r][c] = map[pan][r][c];
                        }else if(cnt==1){//1회전
                            copy[i][c][4-r] = map[pan][r][c];
                        }else if(cnt==2) {//2회전
                            copy[i][4-r][4-c] = map[pan][r][c];
                        }else if(cnt==3){//3회전
                            copy[i][4-c][r] = map[pan][r][c];
                        }
                    }
                }
            }
            if(copy[0][0][0]==1 && copy[4][4][4]==1){
                int cur = bfs(0,0,0);
                ans = Math.min(cur,ans);

            }
            return;
        }
        for(int cnt=0;cnt<4;cnt++){
            rotationCnt[idx] = cnt;
            setRotation(idx+1);
        }
    }

    //최소경로
    static int bfs(int z, int r, int c){
        Queue<int[]> q = new ArrayDeque<>();
        q.offer(new int[]{z,r,c});
        visit = new boolean[5][5][5];
        visit[z][r][c] = true;

        int turn = -1;
        while(!q.isEmpty()){
            turn++;
            if(turn>ans) return Integer.MAX_VALUE;
            int size = q.size();
            while(size-->0){
                int[] cur = q.poll();
                int cz = cur[0];
                int cr = cur[1];
                int cc = cur[2];

                if(cz==4 && cr==4 && cc==4){
                    if(turn==12){
                        System.out.println(12);
                        System.exit(0);
                    }
                    return turn;
                }

                int nz,nr,nc;
                for(int d=0;d<6;d++){
                    nz = cz+dz[d];
                    nr = cr+dr[d];
                    nc = cc+dc[d];
                    if(nz<0 || nr<0 || nc<0 || nz>4 || nr>4 || nc>4) continue;
                    if(visit[nz][nr][nc]) continue;
                    if(copy[nz][nr][nc]==0) continue;

                    visit[nz][nr][nc] = true;
                    q.offer(new int[] {nz,nr,nc});

                }
            }
        }
        return Integer.MAX_VALUE;
    }

}