![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:FF658D,100:FFCB32&text=BOJ%201937&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 1937 욕심쟁이 판다](https://www.acmicpc.net/problem/1937)

<br>
<br>

# **💻Code**

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[][] board;
    static int[][] maxDepth;
    static int size;
    static int[][] dir = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    static int result = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        size = Integer.parseInt(br.readLine());

        board = new int[size][size];
        maxDepth = new int[size][size];

        for (int i = 0; i < size; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < size; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                dfs(y, x);
            }
        }

        System.out.println(result);
    }

    private static int dfs(int y, int x) {
        if (maxDepth[y][x] != 0)
            return maxDepth[y][x];
        int max = 1;
        for (int direction = 0; direction < 4; direction++) {
            int nextY = y + dir[direction][0];
            int nextX = x + dir[direction][1];
            if (check(nextY, nextX) && board[nextY][nextX] > board[y][x]) {
                if (maxDepth[nextY][nextX] != 0)
                    max = Math.max(max, 1 + maxDepth[nextY][nextX]);
                else {
                    max = Math.max(max, 1 + dfs(nextY, nextX));
                }
            }
        }

        result = Math.max(result, max);
        return maxDepth[y][x] = max;
    }

    private static boolean check(int y, int x) {
        return y >= 0 && y < size && x >= 0 && x < size;
    }
}
```

<br>
<br>

# **🔑Description**

> DFS를 이용해 현재 위치에서 가장 이동할 수 있는 큰 값을 저장합니다.
> 이미 탐색한 칸이라면 해당 위치를 탐색할 때는 현재 위치에 저장된 값을 더하는 것으로 DFS를 종료합니다.
> 이러한 방식으로 모든 칸에 대해서 DFS를 수행한 후 이동할 수 있는 최댓값을 찾았습니다.

<br>
<br>

# **📑Related Issues**

> ...

<br>
<br>

# **🕛Resource**

| Memory   | Time   |
| -------- | ------ |
| 37228KB  | 548ms  |
