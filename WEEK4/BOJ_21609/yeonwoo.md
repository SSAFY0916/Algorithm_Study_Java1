![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%20N&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **๐Problem**

> [BOJ 21609 ์์ด์คํ๊ต](https://www.acmicpc.net/problem/21609)

<br>
<br>

# **Code**

```java
package d230227.bj_21609_์์ด์คํ๊ต;
//13:10 ์์
/*
N * N ๊ฒฉ์์์ ์งํ
๋ชจ๋  ์นธ์ ๋ธ๋ก ์์
๋ธ๋ก์ข๋ฅ: ๊ฒ, ๋ฌด, ์ผ๋ฐ(M๊ฐ์ง์ 1~M์ผ๋ก ํํ)
    ๊ฒ= -1, ๋ฌด = 0, ์ผ = [1~M]
์ธ์  = ์ฌ๋ฐฉํ์

#๊ทธ๋ฃน
-1๊ฐ ์ด์์ ๋์ผ์ ์ผ๋ฐ๋ธ๋ก
-๊ฒ์๋ถ๋ก x
-๋ฌด์ง๊ฐ๋ธ๋ก ์ ํx

-๊ทธ๋ฃน์ ๋ธ๋ก2๊ฐ ์ด์ ์์ด์ผํจ
-bfs์ฐ๊ฒฐ์ด์ด์ผํจ
-๊ทธ๋ฃน๊ธฐ์ค์ ์ต์ ํ ์ต์ ์ด

#์คํ 
1.๊ฐ์ฅ ํฐ ใฑ๋ฃน ์ฐพ๊ธฐ
๋์ ์ฒ๋ฆฌ = ๋ฌด์ง๊ฐ๋ธ๋ก์>๊ฐ์ฅํฐํ>๊ฐ์ฅํฐ์ด

2.1์ ๋ชจ๋  ๋ธ๋ก ์ ๊ฑฐ, (์ ๊ฑฐ๋ ๋ธ๋ก ์)^2 ํ๋

3.์ค๋ ฅ
๊ฒ์ ์ ๊ทธ๋๋ก ๋ถ์ , ๋๋จธ์ง๋ ์ค๋ ฅ
4.ํ์ 
5.์ค๋ ฅ
 */
//13:30
//13:30 ~ 14:10 ๊ตฌํ
//15:00~15:40 ๊ตฌํ

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

    //maxGroup์ฉ ๋ณ์ ์์
    static ArrayList<int[]> maxGroup = new ArrayList<>();//๋ธ๋ก์ขํ๋ค
    static int maxRainBowBlock = 0;//๋ฌด์ง๊ฐ ๋ธ๋ก ์
    //maxGroup์ฉ ๋ณ์ ๋

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
            //์ ํด ์์
            //์ต๋๊ทธ๋ฃน์ฐพ๊ธฐ
            findMax();
            if (maxGroup.size() == 0) {//๋์ด์ ๊ทธ๋ฃน์ด ์๋ค๋ฉด ๊ฒ์ ์ข๋ฃ
                break;
            }

            removeMax();//๋งฅ์ค๊ทธ๋ฃน์ ๊ฑฐ์ ํจ๊ป ์ ์์ฒ๋ฆฌ

            //์ค๋ ฅ
            gravity();


            //ํ์ 
            rotate();

            //์ค๋ ฅ
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

                if(map[i][j] == -1) continue;//๊ฒ์ ์ ๊ทธ๋๋ก ์ ์ง
                NR = i;
                NC = j;
                //๊ฒ์ ์ด ์๋์นธ์ ๋ค์์นธ ์ฐพ๋๋ค (NR,NC)์ ์ ์ฅ๋๋ค
                shoot(i,j);

                if(NR==i && NC==j) {
                    continue;//์ด๋๋ถ๊ฐ
                }
                //์์์์น๊ฐ ์ ์ฅ
                int val = map[i][j];
                map[NR][NC] = val;//์ด๋์์น์ ๊ฐ ์ ์ฅ
                map[i][j] = -2;//-2๊ฐ ๊ณตํ
            }
        }
    }
    static void shoot(int r, int c){
        int nr = r + dr[2];
        int nc = c + dc[2];
        //๋ค์์นธ์ด ๋ฒ์ ๋ฐ
        if(!isIn(nr,nc)){
            return;
        }
        //๋ด์นธ์ ๋ญ ์์ผ๋ฉด
        if(map[nr][nc]!=-2)
            return;
        //์ ์ญ์ฌ์ฉํ์ฌ ๋ฉ๋ชจ๋ฆฌ ์๋ผ๊ธฐ
        NR = nr;
        NC = nc;
        shoot(NR,NC);
    }
    static void removeMax(){
        finalPoint += (int)Math.pow(maxGroup.size(),2);
        for(int[] cur: maxGroup){
            map[cur[0]][cur[1]] = -2;//์๋ฉธ์นธ = -2
        }
    }

    //1.๊ฐ์ฅ ํฐ ใฑ๋ฃน ์ฐพ๊ธฐ
    //๋์ ์ฒ๋ฆฌ = ๋ฌด์ง๊ฐ๋ธ๋ก์>๊ฐ์ฅํฐํ>๊ฐ์ฅํฐ์ด
    static void findMax(){
        visited = new boolean[N][N];
        maxGroup.clear();//

        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                if(visited[i][j]) continue;//๋ฐฉ๋ฌธ
                if(map[i][j]==-1) continue; // ๊ฒ์ ๋ธ๋ก์ ๋ํ๊ฐ ๋  ์์๋ค
                if(map[i][j]==0) continue;//๋ฌด์ง๊ฐ๋ธ๋ก์ ๋ํ๊ฐ ๋  ์์๋ค
                if(map[i][j]==-2) continue;//๊ณตํ๋ ๋ํ๊ฐ ๋  ์ ์๋ค

                //๋ฐฉ๋ฌธํ์  ์์ผ๋ฉด์ ์ผ๋ฐ๋ธ๋ก๋ง ๋ํ๊ฐ ๋  ์์๋ค

                bfs(i,j,map[i][j]);

            }
        }
    }

    static void bfs(int sr, int sc, int color){
//        System.out.printf("%d, %d์์ ์์\n",sr,sc);
        ArrayList<int[]> positions = new ArrayList<>();//์ต๋๊ทธ๋ฃน์ผ์ ์ฌ์ฉ
        Queue<int[]> q = new ArrayDeque<>();//bfsํ์์ ์ฌ์ฉ
        int rainbowCount = 0;//๊ทธ๋ฃน์ ๋ฌด์ง๊ฐ ๋ธ๋ก ์

        q.offer(new int[]{sr,sc});//์์ ๋ธ๋ก ์ถ๊ฐ
        visited[sr][sc] = true;//์์ํ์ด ๋ฐฉ๋ฌธ์ฒ๋ฆฌ

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
                    if(!isIn(nr,nc)) continue; //๋ฒ์์ด๊ณผ
                    if(visited[nr][nc]) continue;//๋ฐฉ๋ฌธ์ด๊ณผ
                    if(map[nr][nc] == -1) continue;//๊ฒ์ ๋ธ๋ก
                    if(map[nr][nc]!=color && map[nr][nc]!=0) continue;//์๊น์ด๊ณผ

                    //map[nr][nc] == color || map[nr][nc] == 0
                    //๊ทธ๋ฃน๋ฉค๋ฐ ๋ฐ๊ฒฌ
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
        //๊ทธ๋ฃนํฌ๊ธฐ๊ฐ 2๊ฐ ์๋๋ฉด ๋ฆฌํด
        if(positions.size()<2) return;
        
        if(positions.size()>maxGroup.size()){//์ ์ข๊ทธ๋ฃน์ด ๊ธฐ์กด์ต๊ฐ๋ณด๋ค ํฌ๋ค
            changeList(maxGroup,positions);//maxGroup์ position์ผ๋ก ๋ณ๊ฒฝ
            maxRainBowBlock = rainbowCount;
        }else if(positions.size()==maxGroup.size()){//์ ๊ท ๊ทธ๋ฃน์ด ๊ธฐ์กด ์ต๋ ๊ทธ๋ฃน๊ณผ ๊ฐ์ ํฌ๊ธฐ
            if(rainbowCount>maxRainBowBlock){//์ ๊ท๊ทธ๋ฃน ๋ฌด์ง๊ฐ๊ฐ ๋ ๋ง๋ค
                changeList(maxGroup,positions);
                maxRainBowBlock = rainbowCount;
            }else if(rainbowCount == maxRainBowBlock){//์ ๊ท๊ทธ๋ฃน๋ฌด์ง๊ฐ์==์ต๋๋ฌด์ง๊ฐ์
                /*
                ์ฌ๊ธฐ์ r์ด๋ ํฐ์ง, c๊ฐ ๋ ํฐ์ง ์ฐ์ ์์ ๋ฐ์ ธ์ผํ๊ธด ํ๋๋ฐ
                ์ํ์์์ ๋์ค์์ฐพ์์  r์ด ๋ฌด์กฐ๊ฑด ๊ธฐ์กด ๊ฒ๋ณด๋ค ํฌ๊ฑฐ๋ ๊ฐ์
                ๋ง์ฐฌ๊ฐ์ง๋ก c ๋ํ ๋์ค์ชฝ์ด ๋ฌด์กฐ๊ฑด ๊ธฐ์กด ์๋ณด๋ค ํฌ๊ฑฐ๋ ๊ฐ์
                 */
                changeList(maxGroup,positions);
            }
        }

    }
    static void changeList(ArrayList<int[]> from, ArrayList<int[]> to){
        //from = to; //์๋ ๋ฐฉ๋ฒ์ผ๋ก ๋ณ๊ฒฝ

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


```

<br>
<br>

# **๐Description**
#์๋ ์์๋ก ๊ทธ๋ฃน์ด ์์ ๋ ๊น์ง ๋ฐ๋ณตํ์์ต๋๋ค
1.๊ฐ์ฅ ํฐ ๊ทธ๋ฃน ์ฐพ๊ธฐ
๋์ ์ฒ๋ฆฌ = ๋ฌด์ง๊ฐ๋ธ๋ก์>๊ฐ์ฅํฐํ>๊ฐ์ฅํฐ์ด

2.1์ ๋ชจ๋  ๋ธ๋ก ์ ๊ฑฐ, (์ ๊ฑฐ๋ ๋ธ๋ก ์)^2 ํ๋

3.์ค๋ ฅ
๊ฒ์ ์ ๊ทธ๋๋ก ๋ถ์ , ๋๋จธ์ง๋ ์ค๋ ฅ
4.ํ์ 
5.์ค๋ ฅ



#๊ฐ์ฅ ํฐ ๊ทธ๋ฃน ์ฐพ๊ธฐ
- ํ์ดํ์์ ํ๋ฉด์ ์ผ๋ฐ๋ธ๋ก์ด ๋์ค๋ฉด bfs ํ์์ ํ์์ต๋๋ค
- ํ์ดํ์์ ํ๋ฉด ๋ฌด์ง๊ฐ ๋ธ๋ก์ ์๋ฅผ ์ ์ธํ ๋์ ์ฒ๋ฆฌ๊ฐ ์๋์ผ๋ก ๋ฉ๋๋ค.
- bfsํ์์ด ํ ๋ฒ ๋๋  ๋ ๋ง๋ค ๋ฌด์ง๊ฐ ๋ธ๋ก๋ง visit = false ์ฒ๋ฆฌ๋ฅผ ํด์ 
๋ฌด์ง๊ฐ๋ธ๋ก๋ง ์ฌ์ฌ์ฉ ๊ฐ๋ฅํ๋๋ก ํ์์ต๋๋ค.


<br>
<br>

# **๐Related Issues**

> ๋ฌด์ง๊ฐ๋ธ๋ก ์ด๊ธฐํ ์ํด์ ์ต๋ ๊ทธ๋ฃน ํ์์ ๋ํญ์ ๊ฒช์์ต๋๋ค.
>
> 

<br>
<br>

# **๐Resource**

| Memory  | Time  | Implementation Time |
| ------- | ----- | ------------------- |
| 20908KB | 248ms | 4 Hour 50 Minutes   |
