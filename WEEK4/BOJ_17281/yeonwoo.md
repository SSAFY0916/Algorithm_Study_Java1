![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%20N&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **๐Problem**

> [BOJ 17281 ์ผ๊ตฌ๊ณต](https://www.acmicpc.net/problem/17)

<br>
<br>

# **Code**

```java
package daily.y_2023.m_03.d_01.bj_17281_์ผ๊ตฌ๊ณต;

/*
12:25 ์์, 12:30 ๊ตฌํ ์์ 13:55 ์ ๋ต์๋๋ค. ์ด 90๋ถ
N = input

์ผ๊ตฌ์ ์ ์ด๋๋ณ ์ ์
man[9][N]


์์ด
์๋ฎฌ
 */

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int n;
    static int[][] man;
    static boolean[] visited = new boolean[10];
    static int max = Integer.MIN_VALUE;

    static int[] seq = new int[10];

    static int res = Integer.MIN_VALUE;
    public static void main(String[] args) throws IOException{
        n = Integer.parseInt(br.readLine());


        man = new int[10][n+1];//[ํ์][์ด๋]
        for(int i=1;i<=n;i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int j=1;j<=9;j++){
                man[j][i]=Integer.parseInt(st.nextToken());
            }
        }

        dfs(1);
        System.out.println(res);
    }


    static void dfs(int tasoon){//ํ์ 1์์ ์์ํ์ฌ 9๊น์ง
        if(tasoon==4){//4๋ฒํ์๋ 1๋ฒ ์ ์
            seq[tasoon] = 1;
            dfs(tasoon+1);
        }
        if(tasoon==10){//ํ์ ์ธํ ์๋ฃ
            int point = simulate();//์ ์ํ์ธ
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
        int batmanIdx = 1;//ํ์๋ฒํธ

        int outCount = 0;//๋ช์์
        //๋ฃจ์ ์ ์ ์กด์ฌ
        boolean[] loo = new boolean[4];

        int point = 0;

        while(ining<=n){
            // System.out.printf("point = %d, ining=%d\n",point,ining);

            int batmanNo = seq[batmanIdx];//ํ์ฌํ์๋ฒํธ
            int curManRes = man[batmanNo][ining];//ํ์ฌํ์์ ํ์ฌ ์ด๋ ๊ฒฐ๊ณผ
            // System.out.printf("batmanIdx=%d, batmanNo=%d curManRes=%d\n",batmanIdx,batmanNo,curManRes);

            //์์
            if(curManRes ==0){
                outCount+=1;
                if(outCount==3){//3์์์ด๋ฉด ์ด๋ ์ฒด์ธ์ง
                    outCount=0;
                    ining++;

                    //๋ฃจ ์ด๊ธฐํ
                    resetLoo(loo);
                }
            }else{
                point += aantaa(curManRes,loo);
            }

            //๋ค์ํ์๋ก ๋ณ๊ฒฝ๊ฒฝ
            batmanIdx = batmanIdx%9+1;

        }
        return point;
    }

    static void resetLoo(boolean[] loo){
        Arrays.fill(loo,false);
    }

    static int aantaa(int ta, boolean[] loo){
        int point = 0;
        // System.out.printf("%d๋ฃจํ!",ta);
        // printArr(loo);

        loo[0] = true;//ํ์ฌํ์
        for(int i=3;i>=0;i--){
            if(!loo[i]) continue;//๋ฃจ ๋น์ด์์
            //i๋ฃจ์ ํ์ ์์
            loo[i] = false;//์ ์งํ๋๊น ์์ฐ


            //๋ค์ ๋ฃจ ๊ณ์ฐ
            if(i+ta >= 4) {//๋ฒ์์ด๊ณผ
                point++;
            }else{
                loo[i+ta]= true;
            }
        }
        // printArr(loo);
        // System.out.printf("%n์  ํ๋\n",point);
        return point;
    }


    //๋๋ฒ๊น์ ์์====================================
    static void printSeq(){
        System.out.println("seq ์๋๋ค");
        for(int i=0;i<seq.length;i++){
            System.out.print(seq[i]+" ");
        }
        System.out.println();
    }

    static void printman(){
        System.out.println("๋ฐฐํธ๋งจ ๋ฒํธ");
        for(int i=0;i<man.length;i++){
            System.out.print(i+ ": ");
            for(int j=0;j<man[i].length-1;j++){
                System.out.print(man[i][j]+ " ");
            }
            System.out.println(man[i][man[i].length-1]);
        }
    }
    static void printArr(boolean[] loo){
        System.out.println("๋ฃจ");
        for(int i=0;i<loo.length;i++){
            if(loo[i])System.out.print("1 ");
            else System.out.print("0 ");
        }
        System.out.println();
    }
            /*
        seq ์๋๋ค
        0 2 3 4 1 5 6 7 8 9
        ๋ฐฐํธ๋งจ ๋ฒํธ
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
    //๋๋ฒ๊น์ ์ข๋ฃ====================================
}

```

<br>
<br>

# **๐Description**
> ์์ด์ ์ด์ฉํ์ฌ ํ์ ์์๋ฅผ ๋ฝ์์ต๋๋ค.(4๋ฒํ์ ๋ณ๋์ฒ๋ฆฌ)
>
> ์๋ฎฌ๋ ์ดํ ๋ฉ์๋๋ก ๊ฐ ์์ด์ ์ ์๋ฅผ ๋์ถํ์ต๋๋ค.
>
> #์๋ฎฌ๋ ์ดํ ๋ฉ์๋
>
> ์ด๋์ด n ์ดํ์ผ ๋์ ๋๋ while๋ฌธ ์์์ ํ์ ์๋ฒ์ 1์ฉ ์ฆ๊ฐ์ํค๋ฉด์ ์๋ฎฌ๋ ์ดํ
>
> while(์ด๋<=n)
>
> โ	์ด์ฐจ์ ๋ฐฐ์ด man(์ ์๋ฒํธ์ ์ด๋๋ณ ์ค์ ์ ์ ๋ณด๊ฐ ๋ค์ด์์)์์ ํ ์ ์ ํ ์ด๋์ ์ค์  ๊ฐ์ ธ์ด
>
> โ		์์์ด๋ฉด ์์์ฒ๋ฆฌ(3์์์ resetLoo๋ฅผ ํตํด ๋ชจ๋  ๋ฃจ ๋น์์ค)
>
> โ		์์์๋๋ฉด aantaa๋ฉ์๋๋ก ์ ์ง ๋ฐ ์ ์ ์ฒ๋ฆฌ

<br>
<br>

# **๐Related Issues**

> - 1๋ฒ์ธ๋ฑ์ค์์ ์์ํ๋ ๊ฒ์ผ๋ก ์ ํด๋๊ณ  0์์ ์์์ผ๋ก ์ฒ๋ฆฌํ ๋ถ๋ถ ์์์ด์ ํ๋ ธ์ต๋๋ค.
> - n๋ฃจํ์ ํ์ ์ ์ง ์ฒ๋ฆฌ๋ฅผ ํ์์น+n์ผ๋ก ํด์ผ๋๋๋ฐ ํ์์น๋ก ํด ๋์ ํ๋ ธ์ต๋๋ค
> - ๋ฐฐ์ด ๋์ด๊ฐ๋ ์ซ์ %๋ก ๋ค์ ๋งจ ์์ผ๋ก ๋๋ฆฌ๋  ์ด๋ ค์ ์ต๋๋ค

<br>
<br>

# **๐Resource**

| Memory  | Time  | Implementation Time |
| ------- | ----- | ------------------- |
| 17640KB | 524ms | 1 Hour 30 Minutes   |
