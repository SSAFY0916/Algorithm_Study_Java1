![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%202146&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **๐Problem**

> [BOJ ๋ฌธ์ ๋ฒํธ ๋ฌธ์ ์ด๋ฆ](https://www.acmicpc.net/problem/2146)

<br>
<br>

# **Code**

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_2146 {
    static int size;
    // ์ฌ๊ณผ ๋ฐ๋ค์ ์ ๋ณด๋ฅผ ์ ์ฅํ๋ ๋ฐฐ์ด
    static int[][] map;
    // BFS ์ํ์ ๋ฐฉ๋ฌธ ์ฌ๋ถ ํ์ธ
    static boolean[][] visited;
    // ์ฌ์ ๊ณ ์  ID๋ฅผ ๋ถ์ฌํ๊ธฐ ์ํ ๋ณ์
    static int mapId = 2;
    // BFS ์ํ ์ ์ด๋ ๋ฐฉํฅ์ ์ ์ฅํ๊ธฐ ์ํ ๋ฐฐ์ด
    static int[][] dir = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
    // ๊ฒฐ๊ณผ ๊ฐ
    static int bridgeMin = 0x7fffffff;

    // row์ col์ Queue์ ํจ๊ป ๋ด๊ธฐ ์ํ์ฌ ํด๋์ค ์์ฑ
    // BFS ์ํ ์ ๊ฑฐ๋ฆฌ๋ฅผ ๊ตฌํ๊ธฐ ์ํ len ๋ณ์ ์ถ๊ฐ
    static class Pair {
        int row;
        int col;
        int len;

        // len์ ์ฌ์ฉํ์ง ์์ ๋ ์์ฑ์
        Pair(int row, int col) {
            this.row = row;
            this.col = col;
        }

        // len์ ์ฌ์ฉํ  ๋์ ์์ฑ์
        Pair(int row, int col, int len) {
            this.row = row;
            this.col = col;
            this.len = len;
        }
    }

    // visited ๋ฐฐ์ด ์ด๊ธฐํ ํจ์
    public static void initVisited() {
        for (int i = 0; i < size; i++) {
            Arrays.fill(visited[i], false);
        }
    }

    // index๊ฐ ๋ฐฐ์ด์ ๋ฒ์๋ฅผ ๋ฒ์ด๋๋์ง, ๋ฐฉ๋ฌธํ์๋์ง ์ฌ๋ถ๋ฅผ ํ์ธํ๊ธฐ ์ํ ํจ์
    public static boolean check(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size)
            return false;
        if (visited[row][col])
            return false;
        return true;
    }

    // ๊ฐ ์ฌ๋ค์ ๊ณ ์ ํ ๋ฒํธ๋ฅผ ๋ถ์ฌํ๊ธฐ ์ํ ํจ์
    public static void mapInit(int row, int col) {
        Queue<Pair> q = new ArrayDeque<>();

        q.add(new Pair(row, col));
        visited[row][col] = true;

        while (!q.isEmpty()) {
            Pair tmp = q.poll();
            row = tmp.row;
            col = tmp.col;
            for (int i = 0; i < 4; i++) {
                int nextRow = row + dir[i][0];
                int nextCol = col + dir[i][1];

                if (check(nextRow, nextCol) && map[nextRow][nextCol] == map[row][col]) {
                    q.add(new Pair(nextRow, nextCol));
                    visited[nextRow][nextCol] = true;
                }
            }
            map[tmp.row][tmp.col] = mapId;
        }

        mapId++;
    }

    // ๋ค๋ฆฌ๋ฅผ ๊ฑด์คํ๊ธฐ ์ํ ์ต์ ๊ฑฐ๋ฆฌ๋ฅผ ๊ตฌํ๋ BFS ํจ์
    public static void bfs(int row, int col) {
        Queue<Pair> q = new ArrayDeque<>();

        q.add(new Pair(row, col, 0));
        visited[row][col] = true;
        int island = map[row][col];

        while (!q.isEmpty()) {
            Pair tmp = q.poll();
            row = tmp.row;
            col = tmp.col;
            int len = tmp.len;

            for (int i = 0; i < 4; i++) {
                int nextRow = row + dir[i][0];
                int nextCol = col + dir[i][1];

                if (check(nextRow, nextCol) && map[nextRow][nextCol] != island) {
                    if (map[nextRow][nextCol] != 0 && len < bridgeMin) {
                        bridgeMin = len;
                        return;
                    }
                    q.add(new Pair(nextRow, nextCol, len + 1));
                    visited[nextRow][nextCol] = true;
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        size = Integer.parseInt(br.readLine());
        map = new int[size][size];
        visited = new boolean[size][size];

        for (int i = 0; i < size; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < size; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (map[i][j] == 1)
                    mapInit(i, j);
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (map[i][j] != 0) {
                    initVisited();
                    bfs(i, j);
                }
            }
        }

        System.out.println(bridgeMin);
    }

}

```

<br>
<br>

# **๐Description**

> ๊ฐ ๋๋ฅ๋ณ๋ก ๋ฒํธ๋ฅผ ๋ถ์ฌ์ ๊ฐ์ ๋๋ฅ์ ๋ํด ๋ค๋ฆฌ๋ฅผ ๊ฑด์คํ์ง ๋ชปํ๋๋ก ํ์์ต๋๋ค.
> ๋ฒํธ๋ฅผ ๋ถ์ธ ํ ๊ฐ ์ ์์ ๋ค๋ฅธ ๋๋ฅ์ ์ ๊น์ง์ ์ต๋จ ๊ฑฐ๋ฆฌ๋ฅผ ๊ตฌํ๋ ๋ฐฉ์์ผ๋ก ๊ตฌํํ์์ต๋๋ค.

<br>
<br>

# **๐Related Issues**

> Pair ํด๋์ค์ ๋ค๋ฆฌ์ ๊ธธ์ด๋ฅผ ์ ์ฅํ๋ ๊ฐ์ ์ถ๊ฐํ์ง ์๊ณ  bfs ํจ์ ๋ด์์ ์ต๋จ๊ฑฐ๋ฆฌ๋ฅผ ๊ตฌํ๋ ๋ฐฉ๋ฒ์ ์๊ฐํด๋ณด๋ ค๊ณ  ํ์ง๋ง ๊ตฌํํ์ง ๋ชปํ์ต๋๋ค.

<br>
<br>

# **๐Resource**

| Memory | Time   |
| ------ | ------ |
| 296032KB | 2064ms |