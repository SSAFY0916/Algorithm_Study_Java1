![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%20N&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 1194 달이 차오른다, 가자.](https://www.acmicpc.net/problem/1194)

<br>
<br>

# **Code**

```java
package daily.y_2023.m_03.d_29.bj_1194;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static char[][] map;
    static boolean[][][] visited;
    static Node start;
    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        map = new char[n][m];
        visited = new boolean[n][m][64];

        for (int i = 0; i < n; i++) {
            map[i] = br.readLine().toCharArray();

            for (int j = 0; j < m; j++) {
                if (map[i][j] == '0') {
                    start = new Node(i, j, 0, 0);
                }
            }
        }

        bfs(start.x, start.y);
    }

    static void bfs(int x, int y) {
        Queue<Node> q = new LinkedList<>();

        q.add(new Node(x, y, 0, 0));
        visited[x][y][0] = true;

        while (!q.isEmpty()) {
            Node temp = q.poll();
            int count = temp.count;
            int key = temp.key;

            if (map[temp.x][temp.y] == '1') {
                System.out.println(temp.count);
                return;
            }

            for (int i = 0; i < 4; i++) {
                int nx = temp.x + dx[i];
                int ny = temp.y + dy[i];

                if (nx < 0 || ny < 0 || nx >= map.length || ny >= map[0].length) {
                    continue;
                }

                if (map[nx][ny] == '#' || visited[nx][ny][key]) {
                    continue;
                }

                if (map[nx][ny] - 'a' >= 0 && map[nx][ny] - 'a' < 6) {
                    // 열쇠일 때
                    int tempKey = (1 << (map[nx][ny] - 'a')) | key;

                    if (!visited[nx][ny][tempKey]) {
                        visited[nx][ny][tempKey] = true;
                        visited[nx][ny][key] = true;
                        q.add(new Node(nx, ny, count + 1, tempKey));
                    }
                } else if (map[nx][ny] - 'A' >= 0 && map[nx][ny] - 'A' < 6) {
                    // 문일 떄
                    int tempDoor = (1 << (map[nx][ny] - 'A')) & key;

                    // 일치하는 열쇠가 있으면
                    if (tempDoor > 0) {
                        visited[nx][ny][key] = true;
                        q.add(new Node(nx, ny, count + 1, key));
                    }
                } else {
                    visited[nx][ny][key] = true;
                    q.add(new Node(nx, ny, count + 1, key));
                }
            }
        }

        System.out.println(-1);
    }

    static class Node {
        int x;
        int y;
        int count;
        int key;

        public Node(int x, int y, int count, int key) {
            this.x = x;
            this.y = y;
            this.count = count;
            this.key = key;
        }
    }
}
```

<br>
<br>

# **🔑Description**
- bfs
- 111111(2진법)로 가진 열쇠 표현 x,y,z
- 열쇠 획득시 가진 열쇠 기준으로 z축이동

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
| 16360KB | 144ms |                     |
