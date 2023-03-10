![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2012100&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **๐Problem**

> [BOJ ๋ฌธ์ ๋ฒํธ ๋ฌธ์ ์ด๋ฆ](https://www.acmicpc.net/problem/12100)

<br>
<br>

# **Code**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_12100 {
    static BufferedReader br;
    static BufferedWriter bw;
    // ์๋ ฅ ๋ฐฐ์ด ์ ์ฅ
    static int[][] board;
    // board์ size
    static int size;
    // ๋ง๋ค ์ ์๋ ์ต๋๊ฐ์ ์ ์ฅํ๋ ๋ณ์
    static int max = -1;

    // ์ ํค๋ฅผ ๋๋ ์ ๋์ ๋์
    public static int[][] onPressUp(int[][] board) {
        // ๋์ ํ์ ์ํ๋ฅผ ์๋กญ๊ฒ ์ ์ฅํ๋ ๋ฐฐ์ด
        int[][] newBoard = new int[size][size];

        for (int i = 0; i < size; i++) {
            // ๊ฐ ์ด๋ณ๋ก ๋ง๋ค์ด์ง๋ ๊ฐ์ ์๋ก์ด 1์ฐจ์ ๋ฐฐ์ด์ ์ ์ฅ
            int[] newLine = new int[size];
            // newLine์ ์ ์ฅ๋์ด์ผ ํ๋ index
            int idx = 0;
            int curValue = -1;
            // 0ํ๋ถํฐ size-1ํ๊น์ง ํ์
            for (int j = 0; j < size; j++) {
                // 0์ด๋ฉด ๋ค์ ํ์ ๊ฒ์ํ๋๋ก ํจ
                if (board[j][i] == 0)
                    continue;
                // curValue๊ฐ์ด -1์ผ ๋
                // 1. ์์ง 0 ์ด์ธ์ ๋ค๋ฅธ ๊ฐ์ ์ฐพ์ง ๋ชปํ์ ๊ฒฝ์ฐ
                // 2. ์ด๋ฏธ 2๊ฐ์ ๊ฐ์ด ํฉ์ณ์ ธ์ curValue๊ฐ์ด -1๋ก ์ด๊ธฐํ ๋์์ ๊ฒฝ์ฐ
                // ๋์ : curValue๊ฐ์ ํ์ฌ ํ์ ๊ฐ์ผ๋ก ๋ณ๊ฒฝ
                if (curValue == -1) {
                    curValue = board[j][i];
                    continue;
                }
                // curValue๊ฐ์ด ํ์ฌ ํ์์ค์ธ ํ์ ๊ฐ๊ณผ ๊ฐ์ ๊ฒฝ์ฐ
                // ๋ ๊ฐ๋ฅผ ํ๋์ ๊ฐ์ผ๋ก ํฉ์น๊ณ  newLine์ ๋ฃ์ด์ค ๋ค curValue ๊ฐ์ -1๋ก ์ด๊ธฐํ
                if (curValue == board[j][i]) {
                    newLine[idx++] = curValue * 2;
                    curValue = -1;
                    continue;
                }
                // curValue๊ฐ์ด ํ์ฌ ํ์์ค์ธ ํ์ ๊ฐ๊ณผ ๋ค๋ฅผ ๊ฒฝ์ฐ
                // ๋ ๊ฐ์ ํฉ์ณ์ง ์ ์์ผ๋ฏ๋ก newLine์ ๋ฃ์ด์ฃผ๊ณ  curValue ๊ฐ์ ํ์ฌ ํ์ ์ค์ธ ํ์ผ๋ก ๋ณ๊ฒฝ
                if (curValue != board[j][i]) {
                    newLine[idx++] = curValue;
                    curValue = board[j][i];
                    continue;
                }
            }
            // ๋ชจ๋  ํ์์ด ๋๋ฌ์ ๋ curValue๊ฐ์ด -1์ด ์๋๋ผ๋ฉด ์ด ๊ฐ๋ newLine์ ์ ์ฅ
            if (curValue != -1) {
                newLine[idx++] = curValue;
            }
            // newLine์ ๊ฐ๋ค์ newBoard์ ๋ฃ์ด์ค
            for (int j = 0; j < size; j++) {
                newBoard[j][i] = newLine[j];
            }
        }
        // ๋์์ ์ํํ ์ดํ์ ๋ฐฐ์ด์ ๋ฐํ
        return newBoard;
    }

    // onPressDown, onPressLeft, onPressRight ํจ์๋ onPressUp ํจ์์ Index ํ์ ์์ ์ด์ธ์ ๋์
    // ๋ฐฉ์์ ๋์ผ
    public static int[][] onPressDown(int[][] board) {
        int[][] newBoard = new int[size][size];

        for (int i = size - 1; i >= 0; i--) {
            int[] newLine = new int[size];
            int idx = size - 1;
            int curValue = -1;
            for (int j = size - 1; j >= 0; j--) {
                if (board[j][i] == 0)
                    continue;
                if (curValue == -1) {
                    curValue = board[j][i];
                    continue;
                }
                if (curValue == board[j][i]) {
                    newLine[idx--] = curValue * 2;
                    curValue = -1;
                    continue;
                }
                if (curValue != board[j][i]) {
                    newLine[idx--] = curValue;
                    curValue = board[j][i];
                    continue;
                }
            }
            if (curValue != -1) {
                newLine[idx--] = curValue;
            }
            for (int j = size - 1; j >= 0; j--) {
                newBoard[j][i] = newLine[j];
            }
        }

        return newBoard;
    }

    public static int[][] onPressLeft(int[][] board) {
        int[][] newBoard = new int[size][size];

        for (int i = 0; i < size; i++) {
            int[] newLine = new int[size];
            int idx = 0;
            int curValue = -1;
            for (int j = 0; j < size; j++) {
                if (board[i][j] == 0)
                    continue;
                if (curValue == -1) {
                    curValue = board[i][j];
                    continue;
                }
                if (curValue == board[i][j]) {
                    newLine[idx++] = curValue * 2;
                    curValue = -1;
                    continue;
                }
                if (curValue != board[i][j]) {
                    newLine[idx++] = curValue;
                    curValue = board[i][j];
                    continue;
                }
            }
            if (curValue != -1) {
                newLine[idx++] = curValue;
            }
            newBoard[i] = newLine;
        }

        return newBoard;
    }

    public static int[][] onPressRight(int[][] board) {
        int[][] newBoard = new int[size][size];

        for (int i = size - 1; i >= 0; i--) {
            int[] newLine = new int[size];
            int idx = size - 1;
            int curValue = -1;
            for (int j = size - 1; j >= 0; j--) {
                if (board[i][j] == 0)
                    continue;
                if (curValue == -1) {
                    curValue = board[i][j];
                    continue;
                }
                if (curValue == board[i][j]) {
                    newLine[idx--] = curValue * 2;
                    curValue = -1;
                    continue;
                }
                if (curValue != board[i][j]) {
                    newLine[idx--] = curValue;
                    curValue = board[i][j];
                    continue;
                }
            }
            if (curValue != -1) {
                newLine[idx--] = curValue;
            }
            newBoard[i] = newLine;
        }

        return newBoard;
    }

    // ๋์ ์ํ ์  ๋ฐฐ์ด๊ณผ, ์ํ ์ดํ ๋ฐฐ์ด์ ๋น๊ตํ์ฌ ๋์ผํ ๋ฐฐ์ด์ด๋ฉด false๋ฅผ ๋ฐํ
    // ์ด๋ฅผ ์ด์ฉํ์ฌ ๋์ผํ ๋ฐฐ์ด์ ๋ํ์ฌ ๋ค์ ํ์ํ๋ ํ์๋ฅผ ๋ฐฉ์ง
    public static boolean check(int[][] b1, int[][] b2) {
        boolean flag = false;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (b1[i][j] != b2[i][j]) {
                    flag = true;
                    break;
                }
            }
            if (flag)
                break;
        }

        return flag;
    }

    // ๋ฐฐ์ด์ ์ ์ฒด ํ์ํ์ฌ ํ์ฌ ๋ฐฐ์ด์ ์๋ ๊ฐ ์ค ๊ฐ์ฅ ํฐ ๊ฐ์ ์ฐพ๊ธฐ ์ํ ํจ์
    public static int maxValue(int[][] b) {
        int max = -1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                max = Math.max(max, b[i][j]);
            }
        }

        return max;
    }

    // ์ฌ๊ท์ ์ผ๋ก ๋์ํ๋ ํจ์
    public static void recursive(int n, int[][] board) {
        // 5๋ฒ ๋์์ ์ํํ์๋ค๋ฉด (n==5) ํ์ฌ ๋ฐฐ์ด์์ ์ต๋๊ฐ์ ์ฐพ์ ํ ํจ์ ์ข๋ฃ
        if (n == 5) {
            max = Math.max(max, maxValue(board));
            return;
        }

        // ๊ธฐ์กด ๋ฐฐ์ด์ ๋ณต์ฌํ์ฌ ์ฌ์ฉ
        int[][] newBoard = new int[size][size];
        for (int i = 0; i < size; i++) {
            newBoard[i] = Arrays.copyOf(board[i], size);
        }

        // Up, Down, Left, Right ๋์๋ค์ ๋ํด ๊ฐ๊ฐ ์ฌ๊ท์ ์ผ๋ก ๋์
        int[][] tmp = onPressUp(newBoard);
        if (check(newBoard, tmp)) {
            recursive(n + 1, tmp);
        }

        tmp = onPressDown(newBoard);
        if (check(newBoard, tmp)) {
            recursive(n + 1, tmp);
        }

        tmp = onPressLeft(newBoard);
        if (check(newBoard, tmp)) {
            recursive(n + 1, tmp);
        }

        tmp = onPressRight(newBoard);
        if (check(newBoard, tmp)) {
            recursive(n + 1, tmp);
        }
    }

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));

        size = Integer.parseInt(br.readLine());
        board = new int[size][size];

        for (int i = 0; i < size; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < size; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        max = Math.max(max, maxValue(board));

        recursive(0, board);

        System.out.println(max);
    }

}

```

<br>
<br>

# **๐Description**

> ๊ฐ ๋ฐฉํฅํค๋ฅผ ๋๋ ์ ๋ ๋์ํ๋ ํจ์๋ค์ ๊ฐ๊ฐ ๊ตฌํํด ์ฃผ์์ต๋๋ค.
> ๋ชจ๋  ๊ฒฝ์ฐ์ ์๋ฅผ ํ์ํ์ง๋ง ํจ์ ์ํ ํ์ check() ํจ์๋ฅผ ์ด์ฉํ์ฌ ์ด์ ์ ๋ฐฐ์ด๊ณผ ๋น๊ตํด ๋ฐฐ์ด์ด ๋ณํ์ง ์์ ๊ฒฝ์ฐ์ ๋ ์ด์ ํ์์ ์งํํ์ง ์๋๋ก ํจ์ผ๋ก์จ ์คํ์๊ฐ์ ๋จ์ถ์์ผฐ์ต๋๋ค.

<br>
<br>

# **๐Related Issues**

> ๋ฐฐ์ด์ ํฌ๊ธฐ๊ฐ 1์ธ ์ผ์ด์ค์์ max๊ฐ์ ์ด๊ธฐํ ํด์ฃผ์ง ์์์ ์ด ๋ฌธ์ ๋ฅผ ๋ฐ๊ฒฌํ๊ธฐ ๊ฐ์ง ์ค๋ ๊ฑธ๋ ธ์ต๋๋ค.
> ์ ์ญ ๋ณ์๋ช๊ณผ, ํจ์ ์์ญ์์ parameter๋ก ๋๊ธฐ๋ ์ง์ญ ๋ณ์๋ช์ ๋์ผํ๊ฒ ์ฌ์ฉํ๋ค ๋ณด๋, parameter์์ ์ค์ํ ์คํ(board -> boardd๋ก ์คํ)๋ก ์ธํด ์ฝ๋๊ฐ ์ฌ๋ฐ๋ฅด๊ฒ ๋์ํ์ง ๋ชปํ์๊ณ , ์ฝ๋์ ๊ธธ์ด ๋ํ ๊ธธ๋ค ๋ณด๋ ์คํ๋ฅผ ๋ฐ๊ฒฌํ๊ธฐ๊น์ง ๋ง์ ์๊ฐ์ด ๊ฑธ๋ ธ์ต๋๋ค.

<br>
<br>

# **๐Resource**

| Memory | Time   |
| ------ | ------ |
| 21592KB | 200ms |