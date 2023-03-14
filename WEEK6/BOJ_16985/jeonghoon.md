![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2016985&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 문제번호 문제이름](https://www.acmicpc.net/problem/16985)

<br>
<br>

# **Code**

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_16985 {
    // 초기 입력받은 cube 배열
    static int[][][] cube = new int[5][5][5];
    // cube의 방문 여부 + depth를 저장
    static int[][][] visited = new int[5][5][5];
    // permutation 함수를 통해 계산된 층의 순서 + 회전 횟수 저장
    static int[][] pick = new int[5][2];
    // permutation 함수에 사용되는 선택여부
    static boolean[] selected = new boolean[5];
    // 최소 이동 횟수 저장 변수
    static int minMove = 0x7fffffff;
    // bfs에서 사용하는 3차원 이동 거리
    static int[][] dir = new int[][] { { 0, 0, 1 }, { 0, 0, -1 }, { 0, 1, 0 }, { 0, -1, 0 }, { 1, 0, 0 },
            { -1, 0, 0 } };

    // 탐색 가능한 영역인지 확인하는 함수
    static boolean check(int x, int y, int z) {
        return x >= 0 && x < 5 && y >= 0 && y < 5 && z >= 0 && z < 5;
    }

    // C++의 Tuple 클래스와 비슷하게 사용
    static class Tuple {
        int x;
        int y;
        int z;

        public Tuple(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    // ReFactoring 할 때 지워도 될거 같은 클래스... ㅎ.....
    static class Cube {
        int[][][] cube = new int[5][5][5];
    }

    // 각 층들을 회전시키기 위한 함수
    static int[][] rotate(int[][] board) {
        int[][] newBoard = new int[5][5];

        for (int i = 0, newi = 4; i < 5; i++, newi--) {
            for (int j = 0; j < 5; j++) {
                newBoard[i][j] = board[j][newi];
            }
        }

        return newBoard;
    }

    // 순열을 통해서 층의 순서 조절
    static void permutation(int cnt) {
        if (cnt == 5) {
            // 순열이 완성되었다면 큐브 생성
            makeCube();
            return;
        }
        for (int i = 0; i < 5; i++) {
            if (selected[i])
                continue;
            for (int j = 0; j < 4; j++) {
                pick[cnt][0] = i;
                pick[cnt][1] = j;
                selected[i] = true;
                permutation(cnt + 1);
                selected[i] = false;
            }
        }
    }

    // 각 bfs 실행 전 visited 함수 초기화
    static void initVisited() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Arrays.fill(visited[i][j], 0);
            }
        }
    }

    // cube를 회전 여부와 층의 순서에 따라서 새롭게 생성
    private static void makeCube() {
        Cube tmpCube = new Cube();
        int[][] tmpBoard = new int[5][5];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                tmpBoard[j] = Arrays.copyOf(cube[pick[i][0]][j], 5);
            }
            for (int j = 0; j < pick[i][1]; j++) {
                tmpBoard = rotate(tmpBoard);
            }
            for (int j = 0; j < 5; j++) {
                tmpCube.cube[i][j] = Arrays.copyOf(tmpBoard[j], 5);
            }
        }

        // visited 배열을 초기화 한 이후에
        initVisited();
        // 생성된 Cube로 bfs 수행
        bfs(tmpCube);
    }

    private static void bfs(Cube tmpCube) {
        Queue<Tuple> q = new ArrayDeque<>();
        // 시작지점부터 0이라면 탐색할 필요가 없음 (여기서 끝나는 지점의 0 여부도 넣으면 백트래킹 효율이 조금 더 상승할지도)
        if (tmpCube.cube[0][0][0] == 0)
            return;
        // 첫 지점의 방문 여부를 체크하기 위해서 우선 1부터 시작한 후 마지막에 1을 빼주는 방식을 사용
        visited[0][0][0] = 1;
        q.add(new Tuple(0, 0, 0));

        while (!q.isEmpty()) {
            Tuple tmp = q.poll();
            // 끝점이 poll 되었다면 minMove 횟수 초기화
            if (tmp.x == 4 && tmp.y == 4 && tmp.z == 4) {
                if (visited[tmp.x][tmp.y][tmp.z] - 1 < minMove) {
                    // 처음에 시작점에 더해준 1을 여기서 빼주기
                    minMove = visited[tmp.x][tmp.y][tmp.z] - 1;
                }
                return;
            }
            // 가능한 6방향 탐색
            for (int i = 0; i < 6; i++) {
                int nextX = tmp.x + dir[i][0];
                int nextY = tmp.y + dir[i][1];
                int nextZ = tmp.z + dir[i][2];
                if (check(nextX, nextY, nextZ)
                        && tmpCube.cube[nextX][nextY][nextZ] == 1
                        && visited[nextX][nextY][nextZ] == 0) {
                    visited[nextX][nextY][nextZ] = visited[tmp.x][tmp.y][tmp.z] + 1;
                    q.add(new Tuple(nextX, nextY, nextZ));
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                for (int k = 0; k < 5; k++) {
                    cube[i][j][k] = Integer.parseInt(st.nextToken());
                }
            }
        }

        permutation(0);

        System.out.println(minMove == 0x7fffffff ? -1 : minMove);
    }
}
```

<br>
<br>

# **🔑Description**

> 모든 경우의 수의 Cube들을 생성한 이후 BFS를 이용해 최단 이동 경로를 구하는 방식으로 풀이하였습니다.

<br>
<br>

# **📑Related Issues**

> Cube가 3차원 배열로 이루어져 있다보니 이 상황을 다루는 것이 조금 어려웠습니다.
> 이외에는 코드짜는 중간에 헷갈리지만 않는다면 크게 어려운 부분은 없었던 것 같습니다.

<br>
<br>

# **🕛Resource**

| Memory   | Time   |
| -------- | ------ |
| 318052KB | 1588ms |
