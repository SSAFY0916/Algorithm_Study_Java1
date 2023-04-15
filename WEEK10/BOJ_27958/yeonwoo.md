![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2027958&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 27958 사격연습](https://www.acmicpc.net/problem/27958)

<br>
<br>

# **Code**

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int[] bullets;//총알뎀지
    static Target[][] board;//맵
    static int N;//맵크기
    static int K;//총알 수
    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, 1, 0, -1};
    static int result = 0;

    public static void main(String[] args) throws IOException {

        input();//인풋세팅

        //점수, 총알번호
        dfs(0, 0);

        System.out.println(result);
    }

    private static void dfs(int point, int bulletNumber) {
        if (bulletNumber >= K) { // k 번째 총알까지 다 쐈을 때
            result = Math.max(result, point);
            return;
        }

        for (int i = 0; i < N; i++) { // N 번째 행에서 쐇을 때
            for (int j = 0; j < N; j++) { // 타겟이 있는 곳까지 N 번째 열까지 탐색

                if (board[i][j].curLife > 0) { // i,j에 타겟이 있을 때

                    if (board[i][j].curLife >= 10) {
                        // 보너스 일 때
                        //단순히 지우고 분기생성하고 원복
                        
                        int before = board[i][j].curLife;//점수저장
                        board[i][j].curLife = 0;//타겟제거
                        dfs(point + board[i][j].initialLife, bulletNumber + 1);//포인트추가,총알인덱스추가
                        board[i][j].curLife = before;//원복
                        break;

                    } else if (board[i][j].curLife <= bullets[bulletNumber]) { 
                        // 타겟이 총알에 맞고 터질 때
                        //4띵해보고 0아니면 [네,칸,짜,리] 만들어서 복원할 때 IDX랑 DIR랑 맞춰서 복원
                        
                        Target[] isCreated = new Target[4]; // 사방에 생긴 새로운 타겟 위치
                        int createTargetPoint = board[i][j].initialLife / 4;//4띵

                        if (createTargetPoint >= 1) {//4띵결과 0아님
                            for (int k = 0; k < 4; k++) { // 사방에 생길 수 있는 지 확인
                                //분기 여기서 만드는거 아님(타겟 터지면 4방에 '동시'에 생김)
                                int createX = i + dx[k];//NR
                                int createY = j + dy[k];//NC

                                //범위내면서 해당칸 현재피0 == 새 타겟 만들 수 있음
                                if (isPossible(createX, createY)) {
                                    isCreated[k] = board[createX][createY];//K방향기존값 저장
                                    //K방향 MAP에 새 값 저장
                                    board[createX][createY] = new Target(createTargetPoint);
                                }
                            }
                            
                            //분기생성
                            int before = board[i][j].curLife;//현재칸 기존값저장
                            board[i][j].curLife = 0;//현재칸 타겟 제거
                            dfs(point + board[i][j].initialLife, bulletNumber + 1);//분기생성
                            board[i][j].curLife = before;//현재칸복구
                            for (int k = 0; k < 4; k++) {
                                //isCreated[d]에 객체 있으면 새 타겟 생성한것
                                if (isCreated[k] != null) {
                                    //새 타겟 생성했으면 복구
                                    board[i + dx[k]][j + dy[k]] = isCreated[k];
                                }
                            }
                            break;

                        } else {
                            //현재칸 4띵하니까 0
                            //단순 분기 생성
                            int before = board[i][j].curLife;
                            board[i][j].curLife = 0;
                            dfs(point + board[i][j].initialLife, bulletNumber + 1);
                            board[i][j].curLife = before; // 롤백
                            break;

                        }

                    } else {                                            
                        // 총알 데미지가 모자랄 때
                        board[i][j].curLife -= bullets[bulletNumber];
                        dfs(point, bulletNumber + 1);
                        board[i][j].curLife += bullets[bulletNumber];
                        break;

                    }
                }
            }
        }


    }

    //맵 범위 안이고 타겟피가 0이다 == 탁겟이 없다
    private static boolean isPossible(int x, int y) {
        return x >= 0 && y >= 0 && x < N && y < N && board[x][y].curLife == 0;
    }

    private static void input() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(bf.readLine());
        K = Integer.parseInt(bf.readLine());
        board = new Target[N][N];
        bullets = new int[K];

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(bf.readLine());
            for (int j = 0; j < N; j++) {
                int point = Integer.parseInt(st.nextToken());
                board[i][j] = new Target(point);
            }
        }

        StringTokenizer st = new StringTokenizer(bf.readLine());
        for (int i = 0; i < K; i++) {
            bullets[i] = Integer.parseInt(st.nextToken());
        }
    }


    private static class Target {

        int initialLife;
        int curLife;

        public Target(int initialLife) {
            this.initialLife = initialLife;
            this.curLife = initialLife;
        }
    }
}
```

<br>
<br>

# **🔑Description**
- dfs

- ```
  /*
  static class Target을 만들어 2차원 배열에 타겟 표현
  
  dfs
  	바깥포문 == 경우의수
  		안쪽포문 == 타겟 탐색
  			타겟 찾으면
  			보너스인지
  			아니면 잡을 수 있는 타겟인지
  				타겟 4띵하면 0 넘는지
  			못잡는앤지 확인
  */
  ```

<br>
<br>

# **📑Related Issues**

> 
>
> 

<br>
<br>

# **🕛Resource**

| Memory  | Time  | Implementation Time |
| ------- | ----- | ------------------- |
| 16960KB | ms136 |                     |
