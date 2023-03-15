package daily.y_2023.m_03.d_01.bj_17281_야구공;

/*
12:25 시작, 12:30 구현 시작 13:55 정답입니다. 총 90분
N = input

야구선수 이닝별 점수
man[9][N]


순열
시뮬
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class yeonwoo {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int n;
    static int[][] man;
    static boolean[] visited = new boolean[10];
    static int max = Integer.MIN_VALUE;

    static int[] seq = new int[10];

    static int res = Integer.MIN_VALUE;
    public static void main(String[] args) throws IOException{
        n = Integer.parseInt(br.readLine());


        man = new int[10][n+1];//[타자][이닝]
        for(int i=1;i<=n;i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int j=1;j<=9;j++){
                man[j][i]=Integer.parseInt(st.nextToken());
            }
        }

        dfs(1);
        System.out.println(res);
    }


    static void dfs(int tasoon){//타순 1에서 시작하여 9까지
        if(tasoon==4){//4번타자는 1번 선수
            seq[tasoon] = 1;
            dfs(tasoon+1);
        }
        if(tasoon==10){//타순 세팅 완료
            int point = simulate();//점수확인
            res = Math.max(res,point);
            return;
        }

        for(int i=2;i<=9;i++){
            if(visited[i]) continue;
            visited[i] = true;
            seq[tasoon] = i;
            dfs(tasoon+1);
            visited[i] = false;
        }
    }

    static int simulate(){
        // printSeq();
        // printman();
        int ining = 1;
        int batmanIdx = 1;//타순

        int outCount = 0;//몇아웃
        //루에 선수 존재
        boolean[] loo = new boolean[4];

        int point = 0;

        while(ining<=n){
            // System.out.printf("point = %d, ining=%d\n",point,ining);

            int batmanNo = seq[batmanIdx];//현재타자의 선수번호
            int curManRes = man[batmanNo][ining];//현재타자의 현재 이닝 결과
            // System.out.printf("batmanIdx=%d, batmanNo=%d curManRes=%d\n",batmanIdx,batmanNo,curManRes);

            //아웃
            if(curManRes ==0){
                outCount+=1;
                if(outCount==3){//3아웃이면 이닝 체인지
                    outCount=0;
                    ining++;

                    //루 초기화
                    resetLoo(loo);
                }
            }else{
                point += aantaa(curManRes,loo);
            }

            //다음타자로 변경경
            batmanIdx = batmanIdx%9+1;

        }
        return point;
    }

    static void resetLoo(boolean[] loo){
        Arrays.fill(loo,false);
    }

    static int aantaa(int ta, boolean[] loo){
        int point = 0;
        // System.out.printf("%d루타!",ta);
        // printArr(loo);

        loo[0] = true;//현재타자
        for(int i=3;i>=0;i--){
            if(!loo[i]) continue;//루 비어있음
            //i루에 타자 있음
            loo[i] = false;//전진하니까 없앰


            //다음 루 계산
            if(i+ta >= 4) {//범위초과
                point++;
            }else{
                loo[i+ta]= true;
            }
        }
        // printArr(loo);
        // System.out.printf("%n점 획득\n",point);
        return point;
    }


    //디버깅셋 시작====================================
    static void printSeq(){
        System.out.println("seq 입니다");
        for(int i=0;i<seq.length;i++){
            System.out.print(seq[i]+" ");
        }
        System.out.println();
    }

    static void printman(){
        System.out.println("배트맨 번호");
        for(int i=0;i<man.length;i++){
            System.out.print(i+ ": ");
            for(int j=0;j<man[i].length-1;j++){
                System.out.print(man[i][j]+ " ");
            }
            System.out.println(man[i][man[i].length-1]);
        }
    }
    static void printArr(boolean[] loo){
        System.out.println("루");
        for(int i=0;i<loo.length;i++){
            if(loo[i])System.out.print("1 ");
            else System.out.print("0 ");
        }
        System.out.println();
    }
            /*
        seq 입니다
        0 2 3 4 1 5 6 7 8 9
        배트맨 번호
        0 0
        0 0
        4 4
        4 4
        4 4
        4 4
        4 4
        4 4
        4 4
        4 4
             */
    //디버깅셋 종료====================================
}
