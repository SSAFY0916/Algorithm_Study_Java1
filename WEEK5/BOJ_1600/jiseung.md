
![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%201600&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> BOJ 1600 말이 되고픈 원숭이
> 

# 💻**Code**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class Main {

    static BufferedReader br;
    static BufferedWriter bw;
    static StringTokenizer st;

    public static void main(String[] args) throws Exception{
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int k = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        int w = Integer.parseInt(st.nextToken());
        int h = Integer.parseInt(st.nextToken());
        // 격자판을 저장하는 배열
        int[][] map = new int[h][w];
        for(int i=0; i<h; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<w; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 상하좌우 움직임
        int[] dr = {-1, 0, 1, 0};
        int[] dc = {0, -1, 0, 1};
        // 말처럼 움직임
        int[] drHorse = {-2, -1, 1, 2, 2, 1, -1, -2};
        int[] dcHorse = {1, 2, 2, 1, -1, -2, -2, -1};
        // bfs에 사용할 큐
        Queue<int[]> q = new ArrayDeque<>();
        // 말처럼 움직인 횟수, 행, 열 별로 최소 움직임을 저장할 dp배열
        int[][][] dp = new int[k+1][h][w];
        for(int i=0; i<=k; i++) {
            for(int j=0; j<h; j++) {
                Arrays.fill(dp[i][j], Integer.MAX_VALUE); // dp배열 초기화
            }
        }
        q.add(new int[]{0,0,0,0}); // 행, 열, 말처럼움직인횟수, 총움직인횟수
        while(!q.isEmpty()) {
            int x = q.peek()[0];
            int y = q.peek()[1];
            int horseCount = q.peek()[2];
            int totalCount = q.poll()[3];

            if(dp[horseCount][x][y] <= totalCount) { // 이 곳에 더 빠른 방법으로 오는 경우가 있었으면 건너뜀
                continue;
            }
            dp[horseCount][x][y] = totalCount;

            if(x == h-1 && y == w-1) { // 도착지점이면 건너뜀
                continue;
            }

            for(int i=0; i<4; i++) { // 인접한 4방향으로 움직이기
                int newx = x+dr[i];
                int newy = y+dc[i];
                if(newx<0 || newx>=h || newy<0 || newy>=w) {
                    continue;
                }
                if(map[newx][newy] == 1) { // 장애물이면 건너뜀
                    continue;
                }
                q.add(new int[]{newx, newy, horseCount, totalCount+1});
            }
            if(horseCount == k) { // 더이상 말처럼 움직일 수 없으면 건너뜀
                continue;
            }
            for(int i=0; i<8; i++) { // 말처럼 움직이기
                int newx = x+drHorse[i];
                int newy = y+dcHorse[i];
                if(newx<0 || newx>=h || newy<0 || newy>=w) {
                    continue;
                }
                if(map[newx][newy] == 1) {
                    continue;
                }
                q.add(new int[]{newx, newy, horseCount+1, totalCount+1}); // 말처럼 움직인 횟수 증가
            }
        }

        int answer = Integer.MAX_VALUE;
        for(int i=0; i<=k; i++) {
            answer = Math.min(answer, dp[i][h - 1][w - 1]); // 도착지점에서 말처럼움직인 횟수 별로 최소값 찾기
        }
        bw.write(((answer == Integer.MAX_VALUE) ? -1 : answer) + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

}
```

# **🔑Description**

> 말처럼 움직인 횟수는 감소하지 않고 증가하거나 유지되니까 63번째 줄에서 horseCount일 때만 비교하는게 아니라 0~horseCount일 때를 다 확인하면 더 많이 가지를 칠 수 있을 거라고 생각했었다.
> 
> 근데 더 오래 걸렸다.

# **📑Related Issues**

> 메모리 너무 많이 쓴게 아닌가 싶다.

# **🕛Resource**

| Memory | Time |
| --- | --- |
| 308748KB | 936ms |