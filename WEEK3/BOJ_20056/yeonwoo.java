package bj_ㅠ파볼;

//package daily.y_2023.m_02.d_21.bj_파이어볼;

import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import java.io.*;
import java.util.StringTokenizer;

public class yeonwoo {
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

