![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2023288&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **๐Problem**

> [BOJ ๋ฌธ์ ๋ฒํธ ๋ฌธ์ ์ด๋ฆ](https://www.acmicpc.net/problem/23288)

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
    // input ๋ฐ์ ์ฃผ์ฌ์ ํ
    static int[][] board;
    // ํด๋น ์์น์ ์ ์๋ฅผ ๊ณ์ฐํด ๋์ ๋ฐฐ์ด
    static int[][] scoreBoard;
    static int rowSize, colSize;
    static int cmdNum;
    // ์ฃผ์ฌ์์ ์ด๋ ๋ฐฉํฅ์ ๋ฐ๋ฅธ ์ฃผ์ฌ์์ ์์น ๋ณํ๋
    static int[][] dir = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
    // scoreBoard๋ฅผ ๊ณ์ฐํ  ๋ ๋ฐฉ๋ฌธ ์ฌ๋ถ๋ฅผ ์ฒดํฌ
    static boolean[][] visited;
    // dice์ ํ์ฌ ์์น๋ฅผ row, col๋ก ์ ์ฅ
    static int[] dicePos = { 1, 1 };
    // ์ฃผ์ฌ์ ์ด๊ธฐ ๊ฐ ์ธํ
    static int[] diceColBoard = { 6, 3, 1, 4 };
    static int[] diceRowBoard = { 6, 2, 1, 5 };
    // ์ฃผ์ฌ์์ ์ด๋ ๋ฐฉํฅ -> 0 : ์ค๋ฅธ์ชฝ, 1 : ์๋, 2 : ์ค๋ฅธ์ชฝ; 3 : ์ผ์ชฝ
    static int curDir = 0;

    static class Pair {
        int row;
        int col;

        public Pair(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    // ์ฃผ์ฌ์ ์์น์ ๋ฐ๋ผ ์ป๊ฒ๋๋ ์ ์๋ฅผ ๊ณ์ฐํ๋ ํจ์ (์ฌ๊ธฐ์ ์ ์๋ ์ฐ๊ฒฐ๋์ด ์๋ ํ์ ๊ฐ์)
    public static void initScore(int row, int col) {
        // bfs๋ฅผ ์ํ Queue
        Queue<Pair> bfsQueue = new ArrayDeque<>();
        // ์ง๋๊ฐ๋ ์์น๋ฅผ ๋ชจ๋ Queue์ ๋ฃ์ด๋๊ณ  ์ด ์์น๋ค์ ๋ชจ๋ ์ ์๋ฅผ ํต์ผ์์ผ์ฃผ๊ธฐ ์ํ Queue
        Queue<Pair> saveQueue = new ArrayDeque<>();
        bfsQueue.offer(new Pair(row, col));
        saveQueue.offer(new Pair(row, col));
        visited[row][col] = true;

        int score = 0;

        // bfs ๋์
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

        // ์ง๋๊ฐ ์์น๋ค์ ์ ์๋ฅผ ๋ชจ๋ ์ต์ข ์ ์๋ก ์ธํ
        while (!saveQueue.isEmpty()) {
            Pair tmp = saveQueue.poll();

            scoreBoard[tmp.row][tmp.col] = score;
        }
    }

    // ์ฃผ์ฌ์ ๊ตด๋ฆฌ๊ธฐ ๋ช๋ น
    public static void roll(int direction) {
        // ๋ค์ ์์น๊ฐ ๋ฒ์๋ฅผ ๋ฒ์ด๋๊ฒ ๋๋ฉด ๋ฐฉํฅ์ ๋ฐ๋๋ก ๋ฐ๊ฟ์ฃผ๊ณ  ์์
        if (dicePos[0] + dir[direction][0] < 1 || dicePos[0] + dir[direction][0] > rowSize
                || dicePos[1] + dir[direction][1] < 1 || dicePos[1] + dir[direction][1] > colSize) {
            curDir = (curDir + 2) % 4;
            direction = curDir;
        }

        // ๋ฐฉํฅ์ ๋ฐ๋ผ์ ์ ๊ฐ๋๋ฅผ ํ์ 
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

    // ์ฃผ์ฌ์์ ์์น๊ฐ ๋ฐ๋๊ณ  ๋ ํ ๋ค์ ์ด๋ ๋ฐฉํฅ์ ๊ฒฐ์ ํ๋ ํจ์
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
            // ์ฃผ์ฌ์๊ฐ ์ด๋ํ๊ณ  ๋ ํ ํด๋น ์์น์ scoreBoard * board์ ์ซ์
            scoreSum += scoreBoard[dicePos[0]][dicePos[1]] * board[dicePos[0]][dicePos[1]];
        }

        System.out.println(scoreSum);
    }

}

```

<br>
<br>

# **๐Description**

> ์ ๊ฐ๋๋ฅผ ๋๊ฐ์ ๋ฐฐ์ด๋ก ๋ถ๋ฆฌํ์ฌ ํ์ ์ ์กฐ๊ธ ๋ ๊ฐ๋จํ๊ฒ ๊ตฌํํด๋ณด๊ณ ์ ํ์์ต๋๋ค.
> scoreBoard๋ฅผ ์ด์ฉํด์ ๊ฐ์ ์๋ผ๋ฆฌ ์ฐ๊ฒฐ๋์ด ์๋ ๊ฐ์๋ฅผ ๋ฏธ๋ฆฌ ๊ตฌํด๋์ ํ ์ฃผ์ฌ์ ์ด๋๋ช๋ น์ ์ํํ ์ดํ์๋ ๋ฐ๋ก BFS๋ฅผ ์ํํ์ง ์๊ณ , ์ ์๋ง ๊ณ์ฐํ  ์ ์๊ฒ๋ ํ๋ ค๊ณ  ํ์ต๋๋ค.
> ์ฃผ์ฌ์๋ฅผ ์ด๋ํด์ผํ๋ ๋ฐฉํฅ์ผ๋ก ๊ตด๋ฆฐ ์ดํ, ๋ค์ ๊ตด๋ฆฌ๊ธฐ ๋ช๋ น์ ๋ํด ์ด๋ํด์ผํ๋ ๋ฐฉํฅ์ผ๋ก ๋ฐ๊ฟ์ฃผ๊ณ , ํด๋น ํ์ ์ ์๋ฅผ ์ต์ข ์ ์์ ๋ํด์ฃผ๋ ๋ฐฉ์์ผ๋ก ๊ตฌํํ์์ต๋๋ค.

<br>
<br>

# **๐Related Issues**

> ์ ๊ฐ๋๋ฅผ ๋๊ฐ๋ก ๋ถ๋ฆฌํ๋ค ๋ณด๋ ๊ตด๋ฆฌ๊ธฐ ๋์์ ํ ํ ๋ค๋ฅธ ์ ๊ฐ๋๋ ์, ์๋์ ํด๋นํ๋ ๊ณณ์ ๋ณ๊ฒฝํด์ฃผ์ด์ผ ํ๋๋ฐ ํ ๋ฉด์ ๋ฐ๊ฟ์ฃผ์ง ์๋ ์ค์๋ฅผ ํ์์ต๋๋ค.

<br>
<br>

# **๐Resource**

| Memory | Time   |
| ------ | ------ |
| 14432KB | 136ms |