![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%20N&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 15684 사다리 조작](https://www.acmicpc.net/problem/15684)

<br>
<br>

# **Code**

```java
package d230302.bj_사다리;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
/*Time
구상 10m
5:35~5:45

구현 2h45m
5:45~6:30
9:00~11:00
 */

//메모리와 시간
// 18312KB, 2828ms

/*구상
사다리 조작

사다리 표현
1인덱스 2차원배열 H+1,N+1
M = 이미있는연결
true와 true로 연결 확인

사다리 시뮬
아래로이동
트루면 옆으로 이동

사다리 추가
1,1 ~ n,m-1까지 추가 가능, 놨으면 r,c와 r,c+1 트루처리
중복순열

 */

/*ISSUE

issue0시작-------------------------
1과 2가 연결되어있음을
1 2 3 4
t t f f
형식으로 표기한 결과

1-2연결이 있고, 3-4 연결이 있을 때
1 2 3 4
t t t t가 되어서
3에서 출발하면 3->2 이동이 가능한 버그가 발생함

열결의 좌측에만 표기를 하는 방식으로 해결함
ex) 1과 2가 연결되어있다면
1 2 3 4
t f f f
issue0종료-------------------------



issue1시작-------------------------
dfs니까 한 가지에서 3에서 찾고
그 다음 가지에서 1에서 찾을 수 있었는데
result!=초기값이면 가지치는 로직 넣었음
if result!=-1 then return을
if result<=depth then return으로 수정하여 해결하였음
issue1종료-------------------------
 */

public class BJ_15684_사다리_조작 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N; // C
    static int M; //연결 수
    static int H; //R
    static boolean[][] ladder;
    static boolean[][] visited;

    static int result = Integer.MAX_VALUE;
    static int simulate(int sc){
        int sr = 1;

        int nr = sr;
        int nc = sc;
        while(isIn(nr,nc)){
            if(ladder[nr][nc]){//선발견
                nc++;
            }else if(isIn(nr,nc-1)){
                if(ladder[nr][nc-1]){
                    nc--;
                }
            }
            nr++;
        }
//        System.out.printf("%d -> %d\n",sc,nc);
        return nc;

    }
    static boolean isIn(int r, int c){
        return r>=1 && r<=H && c>=0 && c<=N;
    }

    static boolean iIsi(){
        for(int i=1;i<=N;i++){
            if(simulate(i)!=i)return false;
        }
        return true;
    }
    static void dfs(int depth){
//        printArr(ladder);
        if(result<=depth) return;
        if(iIsi()) {
            result = depth;
            return;
        }
        if(depth==3){
            return;
        }

        for(int r=1;r<=H;r++){
            for(int c=1;c<N;c++){
                if(ladder[r][c]) continue;//못놈 - 존재하는 선
                if(ladder[r][c-1]) continue;// 못놈 - 좌측에서이음
                if(visited[r][c]) continue;
                //사다리 놓기
                ladder[r][c] = true;
                visited[r][c] = true;
                dfs(depth+1);
                ladder[r][c] = false;
                visited[r][c]=false;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());//C
        M = Integer.parseInt(st.nextToken());//연결
        H = Integer.parseInt(st.nextToken());//R
        //사다리 생성
        ladder = new boolean[H+1][N+1];
        visited = new boolean[H+1][N+1];

        for(int i=0;i<M;i++){
            //st로 써도 메모리 안놔줌
            StringTokenizer edge = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(edge.nextToken());
            int b = Integer.parseInt(edge.nextToken());
            ladder[a][b] = true;


        }
//         printArr(ladder);

//
//         simulate(1);
//         for(int i=1;i<=N;i++){
//             System.out.println(i+" "+simulate(i));
//         }
        dfs(0);
        System.out.println(result==Integer.MAX_VALUE?-1:result);

    }



    //디버깅 함수
    static void printArr(boolean[][] map){
        System.out.println();
        for(int i=0;i<map.length;i++){
            for(int j=0;j<map[0].length;j++){
                System.out.print(map[i][j]?"1 ":"0 ");
            }
            System.out.println();
        }
        System.out.println();
    }
    //
}
```

<br>
<br>

# **🔑Description**
- 이차원 boolean 배열로 사다리를 표현하였습니다.
- 열결이 존재한다면 연결되어있는 선 중 좌측 선에 true 표기를 하였습니다.
- 백트래킹을 통해 추가할 사다리를 선택하였습니다
  - 가지치기조건
    1. 추가된 사다리가 기존 최솟값 이상임
    2. 현재 사다리가 문제 조건을 충족할 경우
       - 조건 충족 확인 방법
         1. 단순 시뮬레이션으로 확인하였습니다.


<br>
<br>

# **📑Related Issues**

> ```
> issue0시작-------------------------
> 1과 2가 연결되어있음을
> 1 2 3 4
> t t f f
> 형식으로 표기한 결과
> 
> 1-2연결이 있고, 3-4 연결이 있을 때
> 1 2 3 4
> t t t t가 되어서
> 3에서 출발하면 3->2 이동이 가능한 버그가 발생함
> 
> 열결의 좌측에만 표기를 하는 방식으로 해결함
> ex) 1과 2가 연결되어있다면
> 1 2 3 4
> t f f f
> issue0종료-------------------------
> 
> 
> 
> issue1시작-------------------------
> dfs니까 한 가지에서 3에서 찾고
> 그 다음 가지에서 1에서 찾을 수 있었는데
> result!=초기값이면 가지치는 로직 넣었음
> if result!=-1 then return을
> if result<=depth then return으로 수정하여 해결하였음
> issue1종료-------------------------
> 
> 
> issue2시작-------------------------
> 가지가 끝나면 visited를 풀어줬어야 했는데
> visited를 해제했어야 했는데 하지 않았음
> dfs 코드를 평소와 다르게 짜게 됐는데
> 그러면서 구조 이해가 잘 되지 않았는듯
> 
> 평소 짜던 구조로(파라미터로 r과 c를 주는 형태) 재구성 해보려 했으나
> 쉽지 않았다
> issue2종료-------------------------
> ```

<br>
<br>

# **🕛Resource**

| Memory  | Time   | Implementation Time |
| ------- | ------ | ------------------- |
| 18312KB | 2828ms | 2h 45m              |
