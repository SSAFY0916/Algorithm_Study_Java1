![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%20N&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **๐Problem**

> [BOJ 20056_๋ง๋ฒ์ฌ ์์ด์ ํ์ด์ด๋ณผ](https://www.acmicpc.net/problem/20056)

<br>
<br>

# **Code**

```java
//package daily.y_2023.m_02.d_21.bj_ํ์ด์ด๋ณผ;

import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import java.io.*;
import java.util.StringTokenizer;

public class Main {
  static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

  static int[] dr = {-1,-1,0,1,1,1,0,-1};
  static int[] dc = {0,1,1,1,0,-1,-1,-1};

  static int N,M,K;
  static Queue<FireBall>[][] map;
  static ArrayList<FireBall> fireBalls = new ArrayList<>();


  public static void main(String[] args) throws Exception{
      StringTokenizer st = new StringTokenizer(br.readLine());
      N = Integer.parseInt(st.nextToken());
      M = Integer.parseInt(st.nextToken());
      K = Integer.parseInt(st.nextToken());

      //map ์ด๊ธฐํ
      map = new Queue[N][N];
      for(int i=0; i<N;i++){
          for(int j=0;j<N;j++){
              map[i][j] = new LinkedList<>();
          }
      }

      //์ด๊ธฐํ๋ณผ
      for(int i=0;i<M;i++){
          st = new StringTokenizer(br.readLine());
          int r = Integer.parseInt(st.nextToken())-1;
          int c = Integer.parseInt(st.nextToken())-1;
          int m = Integer.parseInt(st.nextToken());
          int s = Integer.parseInt(st.nextToken());
          int d = Integer.parseInt(st.nextToken());

          fireBalls.add(new FireBall(r,c,m,s,d));
      }

      for(int i=0;i<K;i++){
          move();
          operation();
      }
      int res = 0;
      for(int i=0;i<fireBalls.size();i++){
          res+=fireBalls.get(i).m;
      }
      System.out.println(res);
  }
  static void operation(){
      for(int i=0;i<N;i++){
          for(int j=0;j<N;j++){
              //ํฉ์ฒด๋ถ๋ฆฌ ์ํด๋๋๋์์ญ
              if(map[i][j].size()<2){
                  //์๋ ์ด๋ํ์ธ์ฉ์ด๋ผ ์ง์
                  map[i][j].clear();// ์ง์ง๋ fireBalls์
                  continue;
              }

              //ํฉ์ฒด๋ถ๋ฆฌํด์ผํจ
              Queue<FireBall> curQ = map[i][j]; //ํ์ฌํ์ด์ ๋์ฐฉํ ํ๋ณผ๋ค

              //์๋ก๋ง๋ค์ด์ง ํ๋ณผ ์?๋ณด
              int sumM =0;
              int sumS = 0;
              int num = curQ.size();
              int odd =0;
              int even = 0;

              int size = curQ.size();
              while(size-->0){//==!curQ.isEmpty()
                  //ํ์ฌ ํ์ด์์ ํ๋ณผํ๋ ๋ฝ๊ธฐ
                  FireBall fb = curQ.poll();
                  //์?๋ณด์ถ์ถ
                  sumM += fb.m;
                  sumS += fb.s;
//                  num ++;์ด๊ฒ ๋๋ฌธ์ ํ๋ฆผ
                  if(fb.d%2==0) even++;
                  else odd++;
                  //ํฉ์ฒด๋๋ฏ๋ก ์ฌ๋ฃ ์?๊ฑฐ
                  fireBalls.remove(fb);
              }
              if(sumM/5==0) continue;//์ง๋ 0์ผ๋ก ๋๋?์ง
              if(odd==0||even==0){
                  fireBalls.add(new FireBall(i,j,sumM/5,sumS/num,0));
                  fireBalls.add(new FireBall(i,j,sumM/5,sumS/num,2));
                  fireBalls.add(new FireBall(i,j,sumM/5,sumS/num,4));
                  fireBalls.add(new FireBall(i,j,sumM/5,sumS/num,6));
              }else{
                  fireBalls.add(new FireBall(i,j,sumM/5,sumS/num,1));
                  fireBalls.add(new FireBall(i,j,sumM/5,sumS/num,3));
                  fireBalls.add(new FireBall(i,j,sumM/5,sumS/num,5));
                  fireBalls.add(new FireBall(i,j,sumM/5,sumS/num,7));
              }


          }
      }
  }

  static void move(){
      for(FireBall fb: fireBalls){
          fb.r = (N + fb.r + dr[fb.d] * (fb.s % N)) % N;
          fb.c = (N + fb.c + dc[fb.d] * (fb.s % N)) % N;

          // fb.r = (fb.r+dr[fb.d]*fb.s)%N;
          // fb.c = (fb.c+dc[fb.d]*fb.s)%N;
          map[fb.r][fb.c].offer(fb);
      }
  }


  static class FireBall{
      int r,c,m,s,d;
      public FireBall(int r, int c,int m, int s, int d){
          this.r = r;
          this.c = c;
          this.m = m;
          this.s = s;
          this.d = d;
      }
  }
}


```

<br>
<br>

# **๐Description**

> - ๊ฐ ํ์ด์ด๋ณผ์ ํ์ด์ด๋ณผ ํด๋์ค๋ฅผ ์์ฑํ์ฌ ๊ด๋ฆฌํ์์ต๋๋ค.
> 
> - ํ์ด์ด๋ณผ ๋ชฉ๋ก์ ArrayList๋ฅผ ์ด์ฉํ์ฌ ๊ด๋ฆฌํ์์ต๋๋ค.
> - ํ์ด์ด๋ณผ์ ๊ฒน์นจ์ `Queue<FireBall>[][] map` ์ ์ด์ฉํ์ฌ ๊ด๋ฆฌํ์์ต๋๋ค
> 
> 	#### ํด ๊ตฌ์ฑ
> 
> 1. ArrayList๋ฅผ ์ํํ๋ฉฐ ํ์ด์ด๋ณผ.r ํ์ด์ด๋ณผ.c๋ฅผ ๋ฐ๊ฟ์ฃผ์์ต๋๋ค.
> 2. ํ๋ ํ์ด์ด๋ณผ์์์น๋ฅผ map์ ๋ด์์ฃผ์์ต๋๋ค.
> 3. 
>    1. map์ ์ํํ๋ฉด์ `map[row][column].size()>=2`์ธ ํ์ ๋ค์ด์๋ ํ์ด์ด๋ณผ๋ค์ ๋ํด ''๊ฒฐํฉ๋ฐ๋ถํด''๋ฅผ ์งํํ์์ต๋๋ค.
>    2. ํ ์ฌ์ด์ฆ๊ฐ 2 ๋ฏธ๋ง์ ๊ฒฝ์ฐ,  ํ๋ฅผ ๋น์?์ต๋๋ค.
> 
> #### ๊ฒฐํฉ๋ฐ๋ถํด
> 
> 1. ๊ฒฐํฉ๋? ํ์ด์ด๋ณผ๋ค์ ์?๋ณด๋ฅผ ๋ด์ ๋ณ์ ์?์ธ
>2. ๊ฐ ํ์ด์ด๋ณผ์ ์?๋ณด๋ฅผ  1์๋ณ์์ ํ?๋นํ์์ต๋๋ค.
>3. ์?๋ณด๋ฅผ์ถ์ถํ ํ์ด์ด๋ณผ์ list์์ ์?๊ฑฐํ์์ต๋๋ค.
> 4. 
>    1. 1์ ๋ณ์์ ๋ฐ๋ผ ์๋ฉธ์กฐ๊ฑด์ ์ถฉ์กฑํ๋ฉด return ํ์์ต๋๋ค.
>    2. ์๋ฉธ์กฐ๊ฑด์ ์ถฉ์กฑํ์ง ์์ผ๋ฉด ์๋ก 4๊ฐ์ ํ์ด์ด๋ณผ์ ๋ง๋ค์ด list์ ๋ด๊ณ? return ํ์์ต๋๋ค.

<br>
<br>

# **๐Related Issues**

> ํ์ ํ์ด์ด๋ณผ์ด ๋ช ๊ฐ ์์๋์ง ์?์ฅํ๋ ๋ณ์ num์ ๊ฐ์
>
> ์ฒ์์ q.size()๋ฅผ ํตํด ๊ตฌํ๊ธฐ ๋๋ฌธ์ ํ ์ํ์ค์ ๋ํด์ค ํ์๊ฐ ์์์ต๋๋ค.
>
> ๊ทธ๋ฐ๋ฐ poll()ํ? ๋ ๋ง๋ค num++๋ฅผ ํด์ฃผ๋ ์ค์๋ฅผ ํ์์ต๋๋ค.
>
> 

<br>
<br>

# **๐Resource**

| Memory  | Time  | Implementation Time |
| ------- | ----- | ------------------- |
| 32056KB | 836ms | 4 Hour 50 Minutes   |
