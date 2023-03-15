![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%202146&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 2146 다리만들기](https://www.acmicpc.net/problem/2146)

<br>
<br>

# **Code**

```java
package bfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.StringTokenizer;

class Bridge {
    int row,col;

    public Bridge(int row, int col) {
        this.row = row;
        this.col = col;
    }
}
public class Main_2146_다리만들기_2 {

    static int[][] map; // 원본 데이터
    static int[][] dis; // 거리 배열
    static int[][] landNumMap; // 섬 번호를 저장할 배열
    static boolean[][] vis; // 방문 배열
    static int[] dix = {-1, 0, 1, 0}; //상우하좌
    static int[] diy = {0, 1, 0, -1};
    static int N;
    static ArrayDeque<Bridge> queue = new ArrayDeque<>(); // 다리를 놓는 bfs를 수행할 큐
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        map = new int[N][N];
        landNumMap = new int[N][N];
        dis = new int[N][N];
        for (int row = 0; row < N; row++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int col = 0; col < N; col++) {
                map[row][col] = Integer.parseInt(st.nextToken());
            }
        }

        vis = new boolean[N][N];
        int cnt = 0;
        // 아직 방문하지 않은 섬이면 섬번호 체크 bfs 실행 -> 동시에 섬에 가장 인접한 섬 위치 큐에 넣기
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                if (map[row][col] == 1 && !vis[row][col]) {
                    cnt++;
                    bfs(row,col, cnt);
                }
            }
        }

        vis = new boolean[N][N]; // 방문 배열 갱신
        int ans = Integer.MAX_VALUE; // 정답 (최소 다리 갯수)
        while(!queue.isEmpty()) {
            Bridge cur = queue.poll();
            for (int i = 0; i < 4; i++) {
                int nx = cur.row + dix[i];
                int ny = cur.col + diy[i];
                if (nx <0 || nx >= N || ny < 0 || ny >= N) continue;
                if (!vis[nx][ny] && landNumMap[nx][ny] == 0) { // 바다를 만날 경우
                    dis[nx][ny] = dis[cur.row][cur.col] + 1; // 최단 거리 기록
                    landNumMap[nx][ny] = landNumMap[cur.row][cur.col]; // 어느 섬에서 온 다리인지 표시
                    queue.offer(new Bridge(nx,ny)); // Bridge 객체 저장
                    vis[nx][ny] = true;
                } else if (dis[nx][ny] > 0 || landNumMap[nx][ny] >= 1) { // 이미 방문한 바다 혹은 섬에 다다를경우
                    if (landNumMap[nx][ny] != landNumMap[cur.row][cur.col]) { // 다른 섬일 때
                        ans = Math.min(ans, dis[nx][ny] + dis[cur.row][cur.col]); // 최솟값 비교 후 답에 저장
                    }
                }
            }
        }

        System.out.println(ans);
    }

    // landNumMap 이용하기
    // 바다가 아니라 바다에 인접한 섬을 큐에 넣기
    private static void bfs(int row, int col, int cnt) {
        ArrayDeque<Bridge> checkNumQueue = new ArrayDeque<>();
        checkNumQueue.offer(new Bridge(row, col));
        vis[row][col] = true;
        landNumMap[row][col] = cnt;

        while(!checkNumQueue.isEmpty()) {
            Bridge cur = checkNumQueue.poll();
            for (int i = 0; i < 4; i++) {
                int nx = cur.row + dix[i];
                int ny = cur.col + diy[i];
                if (nx <0 || nx >= N || ny < 0 || ny >= N || vis[nx][ny]) continue;
                if (map[nx][ny] == 0) { // 섬에 인접한 바다일 경우 현재 위치를 큐에 넣어준다.
                    queue.offer(new Bridge(cur.row, cur.col)); //  위의 주석과 다르게 구현해서 실수함ㅜ
                } else { // 섬일 경우 섬 번호 체크
                    checkNumQueue.offer(new Bridge(nx,ny));
                    vis[nx][ny] = true;
                    landNumMap[nx][ny] = cnt;
                }
            }
        }

    }

}
```

<br>
<br>

# **🔑Description**

> 설계 시간: 여러번 걸쳐서 생각했기 때문에 의미없음.. 1~2시간 걸린듯

> 구현 시간: 1~2hr
<aside>
💡 설계 아이디어

- 섬과 상하좌우로 인접한 바다에 다리를 놓고, 서로 다른 섬 두 개를 이을 수 있는 다리의 최소 갯수를 구하는 문제.
    - 거리, 섬번호 체크, 방문을 관리할 배열 3개 두기
    - bfs를 실행하면서 최단거리(다리 수)를 구해야 한다.
    - **서로 다른 섬을 잇는 다리를 놓아야 하므로, 섬 번호를 따로 체크해준다. bfs1**
    - 바다에 인접한 섬에서부터 bfs를 돌면서, 다른 섬 혹은 다른 최단 거리 기록(이미 방문한 바다)에 도달하면,이 때 현재 위치의 최단거리와 도달한 장소의 최단거리의 합을 구한다.
- 1트: 다른 섬 체크 안함
- 2트: 첫번째 시도에서 구현했었던 “물과 인접한 섬부터 시작 조건”을 빼먹고 구현 + 배열 관리 미숙
- 3트: 주석 조건대로 구현하지 못한 실수… cur.row 대신 nx 넣음
</aside>

<br>
<br>

# **📑Related Issues**

> Related Issues
<aside>

- 괜히 한 배열에 여러 역할 줘서 편하게 풀려고 하다가 더 꼬인다. 그냥 한 배열에는 하나의 역할만 주자. srp..
- 왜 주석 적어놓고 그걸 빼니. 중간에 긴장 놓으면 꼭 실수가 나오는 것 같다.
    
    ```java
    queue.offer(new Bridge(cur.row, cur.col)); // 섬을 넣어주라고 위에 적었으면서.. 바다 넣음
    ```
    
- 머릿속으로는 당연히 다른 섬을 이어야한다고 인지하고 있었으면서, 섬 혹은 이미 다리를 놓은 바다 위치에 도달했을 때 합을 계산하는 로직을 구현할 때는 해당 조건을 고려하지 못했다.
- 머릿속에서 인지하고 있는 것과 로직에서 그 조건을 따져서 구현하는 것은 다르다.. 내가 설계한 로직을 그대로 시행했을 때 반례는 없는지, 내 로직과 다른 점이 있지는 않은지 따져보는 연습하자.
</aside>

<br>
<br>

# **🕛Resource**

| Memory | Time   | Implementation Time |
| ------ | ------ | ------------------- |
| 16616KB | 184ms | 2 Hour 0 Minutes    |
