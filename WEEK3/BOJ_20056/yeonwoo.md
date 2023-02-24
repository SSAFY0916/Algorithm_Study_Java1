![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%20N&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 20056_마법사 상어와 파이어볼](https://www.acmicpc.net/problem/20056)

<br>
<br>

# **Code**

```java
package bj_ㅠ파볼;

//package daily.y_2023.m_02.d_21.bj_파이어볼;

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

      //map 초기화
      map = new Queue[N][N];
      for(int i=0; i<N;i++){
          for(int j=0;j<N;j++){
              map[i][j] = new LinkedList<>();
          }
      }

      //초기파볼
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
              //합체분리 안해도되는영역
              if(map[i][j].size()<2){
                  //얘는 이동확인용이라 지움
                  map[i][j].clear();// 진짜는 fireBalls임
                  continue;
              }

              //합체분리해야함
              Queue<FireBall> curQ = map[i][j]; //현재행열에 도착한 파볼들

              //새로만들어질 파볼 정보
              int sumM =0;
              int sumS = 0;
              int num = curQ.size();
              int odd =0;
              int even = 0;

              int size = curQ.size();
              while(size-->0){//==!curQ.isEmpty()
                  //현재 행열에서 파볼하나 뽑기
                  FireBall fb = curQ.poll();
                  //정보추출
                  sumM += fb.m;
                  sumS += fb.s;
//                  num ++;이것 떄문에 틀림
                  if(fb.d%2==0) even++;
                  else odd++;
                  //합체되므로 재료 제거
                  fireBalls.remove(fb);
              }
              if(sumM/5==0) continue;//질량 0으로 나눠짐
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

# **🔑Description**

> - 각 파이어볼은 파이어볼 클래스를 작성하여 관리하였습니다.
> 
> - 파이어볼 목록은 ArrayList를 이용하여 관리하였습니다.
> - 파이어볼의 겹침은 `Queue<FireBall>[][] map` 을 이용하여 관리하였습니다
> 
> 	#### 턴 구성
> 
> 1. ArrayList를 순회하며 파이어볼.r 파이어볼.c를 바꿔주었습니다.
> 2. 파뀐 파이어볼의위치를 map에 담아주었습니다.
> 3. 
>    1. map을 순회하면서 `map[row][column].size()>=2`인 큐에 들어있는 파이어볼들에 대해 ''결합및분해''를 진행하였습니다.
>    2. 큐 사이즈가 2 미만의 경우,  큐를 비웠습니다.
> 
> #### 결합및분해
> 
> 1. 결합될 파이어볼들의 정보를 담을 변수 선언
>2. 각 파이어볼의 정보를  1의변수에 할당하였습니다.
>3. 정보를추출한 파이어볼은 list에서 제거하였습니다.
> 4. 
>    1. 1의 변수에 따라 소멸조건을 충족하면 return 하였습니다.
>    2. 소멸조건을 충족하지 않으면 새로 4개의 파이어볼을 만들어 list에 담고 return 하였습니다.

<br>
<br>

# **📑Related Issues**

> 큐에 파이어볼이 몇 개 있었는지 저장하는 변수 num의 값은
>
> 처음에 q.size()를 통해 구하기 때문에 큐 순회중에 더해줄 필요가 없었습니다.
>
> 그런데 poll()할 때 마다 num++를 해주는 실수를 하였습니다.
>
> 

<br>
<br>

# **🕛Resource**

| Memory  | Time  | Implementation Time |
| ------- | ----- | ------------------- |
| 32056KB | 836ms | 4 Hour 50 Minutes   |
