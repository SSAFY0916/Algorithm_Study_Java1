![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%202146&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 문제번호 문제이름](https://www.acmicpc.net/problem/2146)

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
    // 섬과 바다의 정보를 저장하는 배열
    static int[][] map;
    // BFS 수행시 방문 여부 확인
    static boolean[][] visited;
    // 섬에 고유 ID를 부여하기 위한 변수
    static int mapId = 2;
    // BFS 수행 시 이동 방향을 저장하기 위한 배열
    static int[][] dir = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
    // 결과 값
    static int bridgeMin = 0x7fffffff;

    // row와 col을 Queue에 함께 담기 위하여 클래스 생성
    // BFS 수행 시 거리를 구하기 위한 len 변수 추가
    static class Pair {
        int row;
        int col;
        int len;

        // len을 사용하지 않을 때 생성자
        Pair(int row, int col) {
            this.row = row;
            this.col = col;
        }

        // len을 사용할 때의 생성자
        Pair(int row, int col, int len) {
            this.row = row;
            this.col = col;
            this.len = len;
        }
    }

    // visited 배열 초기화 함수
    public static void initVisited() {
        for (int i = 0; i < size; i++) {
            Arrays.fill(visited[i], false);
        }
    }

    // index가 배열의 범위를 벗어나는지, 방문하였는지 여부를 확인하기 위한 함수
    public static boolean check(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size)
            return false;
        if (visited[row][col])
            return false;
        return true;
    }

    // 각 섬들에 고유한 번호를 부여하기 위한 함수
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

    // 다리를 건설하기 위한 최소 거리를 구하는 BFS 함수
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

# **🔑Description**

> 각 대륙별로 번호를 붙여서 같은 대륙에 대해 다리를 건설하지 못하도록 하였습니다.
> 번호를 붙인 후 각 점에서 다른 대륙의 점까지의 최단 거리를 구하는 방식으로 구현하였습니다.

<br>
<br>

# **📑Related Issues**

> Pair 클래스에 다리의 길이를 저장하는 값을 추가하지 않고 bfs 함수 내에서 최단거리를 구하는 방법을 생각해보려고 했지만 구현하지 못했습니다.

<br>
<br>

# **🕛Resource**

| Memory | Time   |
| ------ | ------ |
| 296032KB | 2064ms |