![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2023288&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 문제번호 문제이름](https://www.acmicpc.net/problem/23288)

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

public class BOJ_23288 {
    // input 받은 주사위 판
    static int[][] board;
    // 해당 위치의 점수를 계산해 놓은 배열
    static int[][] scoreBoard;
    static int rowSize, colSize;
    static int cmdNum;
    // 주사위의 이동 방향에 따른 주사위의 위치 변화량
    static int[][] dir = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
    // scoreBoard를 계산할 때 방문 여부를 체크
    static boolean[][] visited;
    // dice의 현재 위치를 row, col로 저장
    static int[] dicePos = { 1, 1 };
    // 주사위 초기 값 세팅
    static int[] diceColBoard = { 6, 3, 1, 4 };
    static int[] diceRowBoard = { 6, 2, 1, 5 };
    // 주사위의 이동 방향 -> 0 : 오른쪽, 1 : 아래, 2 : 오른쪽; 3 : 왼쪽
    static int curDir = 0;

    static class Pair {
        int row;
        int col;

        public Pair(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    // 주사위 위치에 따라 얻게되는 점수를 계산하는 함수 (여기서 점수는 연결되어 있는 판의 개수)
    public static void initScore(int row, int col) {
        // bfs를 위한 Queue
        Queue<Pair> bfsQueue = new ArrayDeque<>();
        // 지나갔던 위치를 모두 Queue에 넣어두고 이 위치들을 모두 점수를 통일시켜주기 위한 Queue
        Queue<Pair> saveQueue = new ArrayDeque<>();
        bfsQueue.offer(new Pair(row, col));
        saveQueue.offer(new Pair(row, col));
        visited[row][col] = true;

        int score = 0;

        // bfs 동작
        while (!bfsQueue.isEmpty()) {
            Pair tmp = bfsQueue.poll();
            score++;

            for (int i = 0; i < 4; i++) {
                int nextRow = tmp.row + dir[i][0];
                int nextCol = tmp.col + dir[i][1];

                if (!visited[nextRow][nextCol] && board[nextRow][nextCol] == board[tmp.row][tmp.col]) {
                    bfsQueue.offer(new Pair(nextRow, nextCol));
                    saveQueue.offer(new Pair(nextRow, nextCol));
                    visited[nextRow][nextCol] = true;
                }
            }
        }

        // 지나간 위치들의 점수를 모두 최종 점수로 세팅
        while (!saveQueue.isEmpty()) {
            Pair tmp = saveQueue.poll();

            scoreBoard[tmp.row][tmp.col] = score;
        }
    }

    // 주사위 굴리기 명령
    public static void roll(int direction) {
        // 다음 위치가 범위를 벗어나게 되면 방향을 반대로 바꿔주고 시작
        if (dicePos[0] + dir[direction][0] < 1 || dicePos[0] + dir[direction][0] > rowSize
                || dicePos[1] + dir[direction][1] < 1 || dicePos[1] + dir[direction][1] > colSize) {
            curDir = (curDir + 2) % 4;
            direction = curDir;
        }

        // 방향에 따라서 전개도를 회전
        if (direction == 0) {
            int tmp = diceColBoard[0];

            for (int i = 0; i < 3; i++) {
                diceColBoard[i] = diceColBoard[i + 1];
            }
            diceColBoard[3] = tmp;
            diceRowBoard[0] = diceColBoard[0];
            diceRowBoard[2] = diceColBoard[2];

            dicePos[1] += 1;
        } else if (direction == 1) {
            int tmp = diceRowBoard[3];

            for (int i = 3; i > 0; i--) {
                diceRowBoard[i] = diceRowBoard[i - 1];
            }
            diceRowBoard[0] = tmp;
            diceColBoard[0] = diceRowBoard[0];
            diceColBoard[2] = diceRowBoard[2];

            dicePos[0] += 1;
        } else if (direction == 2) {
            int tmp = diceColBoard[3];

            for (int i = 3; i > 0; i--) {
                diceColBoard[i] = diceColBoard[i - 1];
            }
            diceColBoard[0] = tmp;
            diceRowBoard[0] = diceColBoard[0];
            diceRowBoard[2] = diceColBoard[2];

            dicePos[1] -= 1;
        } else if (direction == 3) {
            int tmp = diceRowBoard[0];

            for (int i = 0; i < 3; i++) {
                diceRowBoard[i] = diceRowBoard[i + 1];
            }
            diceRowBoard[3] = tmp;
            diceColBoard[0] = diceRowBoard[0];
            diceColBoard[2] = diceRowBoard[2];

            dicePos[0] -= 1;
        }
    }

    // 주사위의 위치가 바뀌고 난 후 다음 이동 방향을 결정하는 함수
    public static void changeDir() {
        if (diceRowBoard[0] > board[dicePos[0]][dicePos[1]])
            curDir = (curDir + 1) % 4;
        else if (diceRowBoard[0] < board[dicePos[0]][dicePos[1]])
            curDir = (curDir + 3) % 4;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        rowSize = Integer.parseInt(st.nextToken());
        colSize = Integer.parseInt(st.nextToken());
        cmdNum = Integer.parseInt(st.nextToken());

        board = new int[rowSize + 2][colSize + 2];
        scoreBoard = new int[rowSize + 2][colSize + 2];
        visited = new boolean[rowSize + 2][colSize + 2];

        for (int i = 1; i <= rowSize; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= colSize; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 1; i <= rowSize; i++) {
            for (int j = 1; j <= colSize; j++) {
                if (scoreBoard[i][j] == 0)
                    initScore(i, j);
            }
        }

        int scoreSum = 0;

        while (cmdNum-- > 0) {
            roll(curDir);
            changeDir();
            // 주사위가 이동하고 난 후 해당 위치의 scoreBoard * board의 숫자
            scoreSum += scoreBoard[dicePos[0]][dicePos[1]] * board[dicePos[0]][dicePos[1]];
        }

        System.out.println(scoreSum);
    }

}

```

<br>
<br>

# **🔑Description**

> 전개도를 두개의 배열로 분리하여 회전을 조금 더 간단하게 구현해보고자 하였습니다.
> scoreBoard를 이용해서 같은 수끼리 연결되어 있는 개수를 미리 구해놓은 후 주사위 이동명령을 수행한 이후에는 따로 BFS를 수행하지 않고, 점수만 계산할 수 있게끔 하려고 했습니다.
> 주사위를 이동해야하는 방향으로 굴린 이후, 다음 굴리기 명령에 대해 이동해야하는 방향으로 바꿔주고, 해당 판의 점수를 최종 점수에 더해주는 방식으로 구현하였습니다.

<br>
<br>

# **📑Related Issues**

> 전개도를 두개로 분리하다 보니 굴리기 동작을 한 후 다른 전개도도 위, 아래에 해당하는 곳을 변경해주어야 하는데 한 면을 바꿔주지 않는 실수를 하였습니다.

<br>
<br>

# **🕛Resource**

| Memory | Time   |
| ------ | ------ |
| 14432KB | 136ms |