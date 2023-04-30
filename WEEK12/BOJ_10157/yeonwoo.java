import java.io.*;
import java.util.StringTokenizer;

public class Main{
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int[] dr = {-1,0,1,0};
    static int[] dc = {0,1,0,-1};

    static boolean[][] map;
    static int C, R; // 열크기, 행크기
    static int K; //k번째
    public static void main(String[] args) throws IOException{
        st = new StringTokenizer(br.readLine());
        C = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(br.readLine());

        if(C*R<K){
            System.out.println(0);
            System.exit(0);
        }

        map = new boolean[R][C];
        int turn = 0;
        int dir = 0;
        int r=R-1;
        int c = 0;
        while(++turn<K){
            map[r][c] = true;

            while(true){
                int nr = r + dr[dir];
                int nc = c + dc[dir];
                if(nr<0 || nr>=R || nc<0 || nc>=C) {
                    //범위초과
                    dir = (dir+1)%4;
                    continue;
                }
                if(map[nr][nc]) {
                    dir = (dir+1)%4;
                    continue;//방문초과
                }

                // System.out.printf("%d,%d turn= %d\n",r,c,turn);
                // printArr();
                r=nr;
                c=nc;
                break;
            }
        }
        System.out.printf("%d %d",c+1,R-r);


    }
    static void printArr(){
        for(int i=0;i<map.length;i++){
            for(int j=0;j<map[0].length;j++){
                System.out.print(map[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }

}
