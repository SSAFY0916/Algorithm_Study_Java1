![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2014890&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 14890 경사로](https://www.acmicpc.net/problem/14890)

<br>
<br>

# **Code**

```java
rt java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
/*
맵세팅을 하고
N번 포문 돌면서 check(0,i,열탐색)으로 열탐색, check(i,0,행탐색)으로 행탐색을 합니다

check
int[] height을 만듭니다.(행탐새인지 열탐색인지에 따라 map에서 적절한 값 가져와서 채움)
boolean[] visited를 만듭니다(경사로 깔려있는 것을 판단합니다)

height를 포문 순회합니다
이번칸과 다음칸을 비교할꺼니까 N-1개까지만 돕니다
    i칸과 i+1칸이 같으면 컨티뉴
    i칸 - i+1칸 == -1이면 오르막 판단
    i칸 - i+1칸 == 1이면 내리막 판단
    else return false
return true

오르막 판단
    j=i+1 ; j<=i+L ; j++
        //return false caeses
        1.범위초과
        2.값초과(j칸값이 i+1칸 값과 다르다 == 오르막 깔릴 칸 값이 일정치 않다)
        3.방문초과(이미 다른 경사로가 깔려있는지)

        방문체크
내리막 판단
    j=i;j>=i+1-L;j--
        1. 범위초과
        2. 값초과
        3. 방문초과 확인

        방문체크


 */
// 경사로
public class Main {

    static int N; // 지도 크기 NxN
    static int L; // 경사로 길이
    static int[][] map;
    static boolean[][] visited; // 경사로 놓은지 확인
    static int pathCnt;//답

    public static void main(String[] args) throws IOException{
        //기본값 세팅 시작
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());


        map = new int[N][N];
        visited = new boolean[N][N];

        for(int i=0;i<N;i++){
            st = new StringTokenizer(br.readLine()," ");
            for(int j=0;j<N;j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        //기본값 세팅 종료


        //가능한 길인지 아닌지 체크
        for(int i=0; i<N; i++){
            if(checkPath(i,0,true))//i,0부터 행탐색
                pathCnt++;
            if(checkPath(0,i,false))//0,i부터 열탐색
                pathCnt++;
        }

        System.out.println(pathCnt);
    }

    //isRowCheck=>행검사
    //!isRowCheck=>열검사
    static boolean checkPath(int r, int c, boolean isRowCheck){
        int[] height = new int[N];
        boolean[] visited = new boolean[N];

        //height 구성
        for(int i=0;i<N;i++){
            if(isRowCheck)//행검사
                height[i] = map[r][i];
            else{//열검사
                height[i] = map[i][c];
            }
        }

        for(int i=0;i<N-1;i++){
            //다음칸 볼꺼니까 N-1까지

            //동일높이
            if(height[i]==height[i+1]) continue;

            //내려가는경사
            else if(height[i]-height[i+1]==1){
                for(int j=i+1;j<=i+L;j++){
                    //다음칸부터 L개칸 확인

                    if(j>=N) return false;//범위초과
                    if(height[i+1]!=height[j]) return false;//값초과
                    if(visited[j]) return false;//경사로초과
                    visited[j] = true;//경사로 놓기
                }
            }else if(height[i]-height[i+1]==-1){
                //올라가는 경사

                for(int j=i;j>=i+1-L;j--){
                    if(j<0) return false;//범위초과
                    if(height[j]!=height[i]) return false;//값초과
                    if(visited[j]) return false;

                    visited[j] = true;
                }
            }else{//2이상차이나는높이
                return false;
            }
        }
        return true;
    }

}
```

<br>
<br>

# **🔑Description**
- ```
  맵세팅을 하고
  N번 포문 돌면서 check(0,i,열탐색)으로 열탐색, check(i,0,행탐색)으로 행탐색을 합니다
  
  check
  int[] height을 만듭니다.(행탐새인지 열탐색인지에 따라 map에서 적절한 값 가져와서 채움)
  boolean[] visited를 만듭니다(경사로 깔려있는 것을 판단합니다)
  
  height를 포문 순회합니다
  이번칸과 다음칸을 비교할꺼니까 N-1개까지만 돕니다
      i칸과 i+1칸이 같으면 컨티뉴
      i칸 - i+1칸 == -1이면 오르막 판단
      i칸 - i+1칸 == 1이면 내리막 판단
      else return false
  return true
  
  오르막 판단
      j=i+1 ; j<=i+L ; j++
          //return false caeses
          1.범위초과
          2.값초과(j칸값이 i+1칸 값과 다르다 == 오르막 깔릴 칸 값이 일정치 않다)
          3.방문초과(이미 다른 경사로가 깔려있는지)
  
          방문체크
  내리막 판단
      j=i;j>=i+1-L;j--
          1. 범위초과
          2. 값초과
          3. 방문초과 확인
  
          방문체크
  ```

  

<br>
<br>

# **📑Related Issues**

> 

<br>
<br>

# **🕛Resource**

| Memory  | Time  | Implementation Time |
| ------- | ----- | ------------------- |
| 15208KB | 152ms |                     |




