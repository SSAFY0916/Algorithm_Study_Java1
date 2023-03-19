![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2016985&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 16985 Maaaaaaaaaze](https://www.acmicpc.net/problem/16985)

<br>
<br>

# **Code**

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

// BOJ 16985 Maaaaaaaaaze
public class Main {

    static int[][][] boardList, selectedBoard, simulBoard, dist; // z,x,y
    static boolean[][][] vis; // bfs 방문 배열
    static boolean[] checkSelected;
    static int minCnt;
    static int[][] points = {{0, 0}, {0, 4}, {4, 4}, {4, 0}}; // 꼭짓점 위치
    static int[] diz = {0, 0, 0, 0, -1, 1}; // 상하좌우위아래 z좌표
    static int[] dix = {-1, 1, 0, 0, 0, 0}; // 상하좌우위아래 x좌표
    static int[] diy = {0, 0, 1, -1, 0, 0}; // 상하좌우위아래 y좌표

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        boardList = new int[5][5][5]; // z,x,y 좌표 순서
        checkSelected = new boolean[5];

        // 입력
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                st = new StringTokenizer(br.readLine());
                for (int k = 0; k < 5; k++) {
                    boardList[i][j][k] = Integer.parseInt(st.nextToken());
                }
            }
        }

        solution();
    }

    private static void solution() {
        // 정답 (최소 이동 횟수)
        minCnt = Integer.MAX_VALUE;
        // 보드 판 선택 후 넣을 배열
        selectedBoard = new int[5][5][5];
        // 보드판 순서 정하기 - 순열
        selectBoardDfs(0);

        // 최소 거리가 갱신되지 않았을 경우, 미로탐색이 불가능하므로 -1리턴. 가능할 경우 최소 이동 횟수 리턴
        if (minCnt == Integer.MAX_VALUE) {
            System.out.println(-1);
        } else {
            System.out.println(minCnt);
        }

    }

    private static void selectBoardDfs(int cnt) {
        if (cnt == 5) {
            simulation();
            return;
        }

        for (int i = 0; i < 5; i++) {
            if (checkSelected[i])
                continue;
            checkSelected[i] = true;
            selectedBoard[cnt] = boardList[i];
            selectBoardDfs(cnt + 1);
            checkSelected[i] = false;
        }

    }

    private static void simulation() {
        for (int i = 0; i < 1 << (5 * 2); i++) {
            // 회전할 수 있는 경우의 수 (4^5)
            int tmp = i;

            // 각 보드마다 회전방향 정하고 시뮬레이션용 보드에 저장하기
            simulBoard = new int[5][5][5];
            int dir = 0;
            for (int k = 0; k < 5; k++) {
                dir = tmp % 4;
                tmp /= 4;
                simulBoard[k] = rotate(dir, k);
            }

            // 회전하기 때문에, 임의의 시작점(입구)라는 조건이 있어도 한 점만 확인해도 가능하다!
            if (simulBoard[0][0][0] == 1 && simulBoard[4][4][4] == 1) {
                // bfs 최소 탐색 거리 저장 배열 갱신
                dist = new int[5][5][5];
                // bfs 방문배열 갱신
                vis = new boolean[5][5][5];

                bfs();
                int d = dist[4][4][4];

                if (d != 0)
                    minCnt = Math.min(minCnt, d); // 최소거리가 0이 아닌 경우 (이동 가능한 경우) 최솟값 갱신
            }

        }
    }

    private static void bfs() {
        int startX = 0;
        int startY = 0;
        int endX = 4;
        int endY = 4;

        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{0, startX, startY}); // 입구
        vis[0][startX][startY] = true;

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int curZ = cur[0];
            int curX = cur[1];
            int curY = cur[2];
            if (curZ == 4 && curX == endX && curY == endY) { // 출구에 도착할 경우 종료
                break;
            }
            for (int i = 0; i < 6; i++) {
                int nZ = diz[i] + curZ;
                int nX = dix[i] + curX;
                int nY = diy[i] + curY;
                if (nZ < 0 || nZ >= 5 || nX < 0 || nX >= 5 || nY < 0 || nY >= 5 || vis[nZ][nX][nY])
                    continue;
                if (simulBoard[nZ][nX][nY] == 1) {
                    queue.offer(new int[]{nZ, nX, nY});
                    vis[nZ][nX][nY] = true;
                    dist[nZ][nX][nY] = dist[curZ][curX][curY] + 1;
                }
            }
        }

    }

    // 판 회전하는 함수
    private static int[][] rotate(int direction, int order) {
        int[][] b = selectedBoard[order];
        for (int r = 0; r < direction; r++) {
            int[][] nb = new int[5][5];
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    nb[i][j] = b[5 - 1 - j][i];
                }
            }
            b = nb;
        }
        return b;
    }

    // 보드 찍어보는 함수
    private static void printBoard(int[][][] b) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < 5; k++) {
                    System.out.print(b[i][j][k] + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
        System.out.println();
    }
}
```

<br>
<br>

# **🔑Description**

> 설계 시간: 며칠

> 구현 시간: ..
<aside>
💡 설계 아이디어

     - 중복여부와 상관없이 일단 보드 5개를 보드 리스트에 저장
     - 순열을 이용하여 보드 순서 정하기 <- 문제이해를 잘못해서 이걸 안했다. 
     - 4^5 만큼의 회전 경우의 수를 (0,1,2,3) 구하고 회전 => 시뮬레이션용 새 3차원 배열에 저장
     - bfs 탐색 경우의 수를 줄일 수 있는 방법이 중요한 문제 (회전 관점에서 입구의 경우의 수 1/4로 줄이기)

</aside>

<br>
<br>

# **📑Related Issues**

> Related Issues
<aside>

     - 굉장히 오래 잡고 있었던 문제이다..
     - 실수1) 처음에 문제 이해를 잘못해서 보드의 순서가 입력대로 주어진 것으로 알고 문제를 풀었다. 
     - 실수2) 부랴부랴 보드 순서를 정해주는 순열을 시행할 때 비트연산으로 풀다가 회전 방향 구하듯이 4로 나눠버렸다.. 
     - 이건 계속 왜 틀렸는지 못찾다가 다른 사람이 오류 있는 것을 찾아주었는데 쉬운 코드라고 습관적으로 코드를 작성하는 버릇에 대해 반성을 많이 했다.
     - 시간초과) 문제를 읽고 임의의 입구를 고를 수 있다고 하길래 꼭짓점 4개를 고르고 고른 입구에 따른 출구가 가능한지 확인하는 연산을 수행했었다. 하지만 이렇게 하니 시간초과가 났고, 회전을 4방향 가능하므로 경우의 수를 1/4로 줄일 수 있음을 알게 되었다.
     - 문제 이해, 실수, 시간초과까지... 시뮬레이션은 아직 어렵다...

</aside>

<br>
<br>

# **🕛Resource**

| Memory | Time | Implementation Time |
| -- |-------|---------------------|
| 301348KB | 1716ms | 며칠.. |
