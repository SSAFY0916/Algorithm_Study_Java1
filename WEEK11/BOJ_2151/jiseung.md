![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%202151&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> BOJ 2151 거울 설치

# 💻**Code**

```java
import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        char[][] board = new char[n][n]; // 집
        List<int[]> doors = new ArrayList<>(); // 문
        for (int i = 0; i < n; i++) {
            board[i] = br.readLine().toCharArray();
            for (int j = 0; j < n; j++) {
                if (board[i][j] == '#') {
                    doors.add(new int[]{i, j});
                }
            }
        }

        int[] dr = {-1, 0, 1, 0};
        int[] dc = {0, -1, 0, 1};
        int[][][] dp = new int[4][n][n]; // (현재 방향, 행, 열)에 대하여 문과 거울에 대한 설치한 거울의 최소 개수를 저장하는 배열
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < n; j++) {
                Arrays.fill(dp[i][j], Integer.MAX_VALUE); // 최소값 저장을 위해 맥스로 초기화
            }
        }
        Queue<int[]> q = new ArrayDeque<>(); // 탐색을 위한 (행, 열, 설치 거울 개수, 방향)을 저장하는 큐
        for (int i = 0; i < 4; i++) {
            q.add(new int[]{doors.get(0)[0], doors.get(0)[1], 0, i}); // 첫 번째 문에서 4가지 방향을 초기 상태로 큐에 넣음
        }
        int[] exit = doors.get(1); // 두 번째 문이 탈출구
        while (!q.isEmpty()) {
            int r = q.peek()[0];
            int c = q.peek()[1];
            int count = q.peek()[2];
            int dir = q.poll()[3];
            if (dp[dir][r][c] <= count) { // 더 적거나 같은 개수의 거울로 현재 (행, 열, 방향)에 도달할 수 있었다면 건너뜀 
                continue;
            }
            dp[dir][r][c] = count; // 거울 개수 갱신
            if (r == exit[0] && c == exit[1]) { // 탈출구에 도착하면 더 이상 탐색 X
                continue;
            }

            for (int i = 1; i < n; i++) {
                int newr = r + dr[dir] * i; // 방향으로 계속 나아감
                int newc = c + dc[dir] * i;
                if (newr < 0 || newr >= n || newc < 0 || newc >= n) { // 집 밖으로 나가면 break
                    break;
                }
                if (board[newr][newc] == '*') { // 벽에 막혀도 break
                    break;
                }
                if (board[newr][newc] == '!' || board[newr][newc] == '#') { // 거울 설치 가능 장소 혹은 탈출구
                    q.add(new int[]{newr, newc, count, dir}); // 직진, 거울 설치 X
                    q.add(new int[]{newr, newc, count+1, (dir+1)%4}); // 거울 설치해서 반시계 방향으로 90도 회전
                    q.add(new int[]{newr, newc, count+1, (dir+3)%4}); // 시계
                    break;
                }
                // 빈칸은 그냥 지나감
            }
        }

        int answer = dp[0][exit[0]][exit[1]]; // 탈출구에 도착한 모든 경우(방향)에서 최소값 찾기
        for (int i = 1; i < 4; i++) {
            answer = Math.min(answer, dp[i][exit[0]][exit[1]]);
        }
        bw.write(answer + "\n");
        bw.close();
        br.close();
    }
}
```

# **🔑Description**

> 행, 열, 거울 설치 개수, 방향으로 거울을 설치가능한 곳과 문들을 정점으로 생각하고 탐색했다.\
> 방향을 가지고 있으므로 탐색에서는 직진만 하고 큐에 3 가지 방향을 넣는 방식으로 탐색했다.

# **📑Related Issues**

> 거울을 한 번 설치했을 때 빛이 두 번 이상 거울에 도달하고 서로 다른 방향으로 반사되기를 원한다면 어떡하지 생각했었다.\
> 하나의 거울에 두 번 이상 도달하는 것 자체가 일종의 사이클이라 생각하고 발생하지 않을거라 가정하고 풀었다.

# **🕛Resource**

| Memory    | Time    |
| --------- | ------- |
| 15476`KB` | 144`ms` |
