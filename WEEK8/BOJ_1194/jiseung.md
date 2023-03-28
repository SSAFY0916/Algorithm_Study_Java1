![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%201194&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> BOJ 1194 달이 차오른다, 가자.

# 💻**Code**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        char[][] maze = new char[n][m];
        int[] start = new int[2]; // 시작 위치 저장
        for (int i = 0; i < n; i++) {
            maze[i] = br.readLine().toCharArray();
            for (int j = 0; j < m; j++) {
                if(maze[i][j] == '0') {
                    start = new int[]{i, j};
                }
            }
        }
        int answer = -1;
        int[] dr = {-1, 0, 1, 0};
        int[] dc = {0, -1, 0, 1};
        int[][][] dp = new int[n][m][1<<6]; // 획득한 열쇠별로 각 위치까지 도달하는 최소 이동횟수를 저장
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                Arrays.fill(dp[i][j], Integer.MAX_VALUE);
            }
        }
        Queue<int[]> q = new ArrayDeque<>();
        q.add(new int[]{start[0], start[1], 0, 0}); // 행, 열, 이동횟수, 획득한 열쇠들
        while(!q.isEmpty()) {
            int r = q.peek()[0];
            int c = q.peek()[1];
            int d = q.peek()[2];
            int keys = q.poll()[3];
            if(maze[r][c] == '1') { // bfs로 탐색하기 때문에 처음 만나는 해가 최적해
                answer = d;
                break;
            }
            if(dp[r][c][keys] <= d) { // 더 적은 이동횟수로도 현재 칸이 올 수 있었다면 건너뜀
                continue;
            }
            dp[r][c][keys] = d; // 이동횟수 갱신
            for (int i = 0; i < 4; i++) {
                int newr = r + dr[i];
                int newc = c + dc[i];
                if(newr<0 || newr>=n || newc<0 || newc>=m) {
                    continue;
                }
                if(maze[newr][newc] == '#') { // 벽이면 건너뜀
                    continue;
                }
                if('a' <= maze[newr][newc] && maze[newr][newc] <= 'f') { // 열쇠 획득
                    int newKeys = keys | (1<<(maze[newr][newc] - 'a'));
                    q.add(new int[]{newr, newc, d+1, newKeys});
                } else if('A' <= maze[newr][newc] && maze[newr][newc] <= 'F') {
                    if((keys & (1<<(maze[newr][newc] - 'A'))) != 0) { // 문에 맞는 열쇠가 있으면 이동
                        q.add(new int[]{newr, newc, d+1, keys});
                    }
                } else { // 빈 칸 혹은 출발 지점
                    q.add(new int[]{newr, newc, d+1, keys});
                }
            }
        }
        bw.write(answer + "\n");
        bw.flush();
        bw.close();
        br.close();
    }
}
```

# **🔑Description**

> 최대 2500칸의 미로와 6가지의 열쇠 밖에 없기 때문에\
> 각 칸 별로 가지고 있는 열쇠의 종류별로 최소 이동횟수를 저장하는 방식으로\
> 비트마스킹을 활용하여 bfs로 돌리면 가능할 것 같았다.\

# **📑Related Issues**

> 비슷한 문제로 백준에 [열쇠](https://www.acmicpc.net/problem/9328)가 있다.
> 개인적으로는 열쇠가 더 좋았다.

# **🕛Resource**

| Memory    | Time    |
| --------- | ------- |
| 20104`KB` | 184`ms` |
