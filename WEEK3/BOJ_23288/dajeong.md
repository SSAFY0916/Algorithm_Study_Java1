![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2023288&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **๐Problem**

> [BOJ 23288 ์ฃผ์ฌ์ ๊ตด๋ฆฌ๊ธฐ2](https://www.acmicpc.net/problem/23288)

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

class Pos {

    int x;
    int y;

    public Pos(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class Main_23288_์ฃผ์ฌ์๊ตด๋ฆฌ๊ธฐ2 {

    static int N, M, K, ans, dir;
    static int[][] board;
    static int[] dice;
    static Pos curPos;
    static int[] dix = {-1, 0, 1, 0}; // ๋ถ๋๋จ์
    static int[] diy = {0, 1, 0, -1};
    static Queue<Pos> queue;
    static boolean[][] vis; // ํธ๋ฆฌ๊ฐ ์๋ ๊ทธ๋ํ์์.. ๋ฐฉ๋ฌธ์ฒดํฌ๋ฅผ ์ํด์ฃผ๋ค๋..

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken()); // ํ
        M = Integer.parseInt(st.nextToken()); // ์ด
        K = Integer.parseInt(st.nextToken()); // ๋ช๋ น์ด ๊ฐฏ์

        // ๋ณด๋ ์๋ ฅ
        board = new int[N][M];
        for (int r = 0; r < N; r++) {
            st = new StringTokenizer(br.readLine());
            for (int c = 0; c < M; c++) {
                board[r][c] = Integer.parseInt(st.nextToken());
            }
        }
        curPos = new Pos(0, 0); // ์์ ์์น ์ค์ 
        dir = 1; // ๋์ชฝ๋ถํฐ ์์ ๋ช์
        ans = 0; // ์ด ์ ์์ ํฉ ๋ช์
        dice = new int[]{1, 2, 3, 4, 5, 6}; // ์ฃผ์ฌ์ ์ ๊ฐ๋ ๋ฐฐ์ด

        for (int k = 0; k < K; k++) { // ์ฃผ์ฌ์ ์ด๋ ํ์ (๊ฒ์ ์)
            dir %= 4;
            go(); // ๋ค์ด์ค ์์น ์ด๋

            if (dir == 1) { // ๋
                right();
            } else if (dir == 3) { // ์
                left();
            } else if (dir == 0) { // ๋ถ
                up();
            } else { // dir == 3 // ๋จ
                down();
            }

            calScore(); // ์ ์ ๊ณ์ฐ ํ ํ๋
            changeDir(); // ์ด๋๋ฐฉํฅ ๊ฒฐ์ 
        }
        System.out.println(ans);
    }

    private static void changeDir() {
        dir += 4;
        int bottom = dice[5];
        int boardNum = board[curPos.x][curPos.y];
        if (bottom > boardNum) {
            dir += 1; // ์๊ณ ๋ฐฉํฅ์ผ๋ก ์ด๋
        } else if (bottom < boardNum) {
            dir -= 1; // ๋ฐ์๊ณ ๋ฐฉํฅ์ผ๋ก ์ด๋
        }
    }

    private static void calScore() {
        queue = new ArrayDeque<>();
        vis = new boolean[N][M]; //๋ฐฉ๋ฌธ๋ฐฐ์ด ๊ฐฑ์ 
        queue.offer(new Pos(curPos.x, curPos.y));
        vis[curPos.x][curPos.y] = true;
        int boardNum = board[curPos.x][curPos.y];
        int sum = boardNum;

        while (!queue.isEmpty()) {
            Pos poll = queue.poll();
            int curX = poll.x;
            int curY = poll.y;
            for (int i = 0; i < 4; i++) {
                int nx = curX + dix[i];
                int ny = curY + diy[i];
                if (nx < 0 || nx >= N || ny < 0 || ny >= M || vis[nx][ny]) {
                    continue;
                }
                if (board[nx][ny] != boardNum) {
                    continue; // ์ฃผ์ฌ์ ์๋ซ๋ฉด์ ์ ์์ ์ํ์ข์ฐ ์ด๋๊ฐ๋ฅํ ์ ์๊ฐ ๊ฐ์ง ์์ ๊ฒฝ์ฐ ๋๊ธฐ๊ธฐ
                }
                sum += boardNum;
                queue.offer(new Pos(nx, ny));
                vis[nx][ny] = true;
            }
        }
        ans += sum;
    }

    private static void go() {// ๋ค์ด์ค ์์น ์ด๋
        dir %= 4;
        int x = curPos.x;
        int y = curPos.y;
        x += dix[dir];
        y += diy[dir];
        if (x < 0 || x >= N || y < 0 || y >= M) { // ๋ณด๋ ๋ฐ์ผ๋ก ์ด๋ํ๋ ค๊ณ  ํ  ๊ฒฝ์ฐ, ๋ฐฉํฅ ๋ฐ๋๋ก ๋ฐ๊พธ๊ธฐ
            dir += 2; // ๋ถ๋๋จ์
            go(); // ๋ฐฉํฅ ๋ฐ๋๋ก ๋ฐ๊พธ๊ณ  ๋ค์ ์ด๋
        } else {
            curPos.x = x;
            curPos.y = y;
        }
    }

    private static void down() {
        int[] shiftArr = new int[6];
        shiftArr[4] = dice[0];
        shiftArr[5] = dice[4];
        shiftArr[1] = dice[5];
        shiftArr[0] = dice[1];
        shiftArr[2] = dice[2];
        shiftArr[3] = dice[3];
        dice = shiftArr;
    }

    private static void up() {
        int[] shiftArr = new int[6];
        shiftArr[1] = dice[0];
        shiftArr[5] = dice[1];
        shiftArr[4] = dice[5];
        shiftArr[0] = dice[4];
        shiftArr[2] = dice[2];
        shiftArr[3] = dice[3];
        dice = shiftArr;
    }

    private static void left() {
        int[] shiftArr = new int[6];
        shiftArr[3] = dice[0];
        shiftArr[5] = dice[3];
        shiftArr[2] = dice[5];
        shiftArr[0] = dice[2];
        shiftArr[1] = dice[1];
        shiftArr[4] = dice[4];
        dice = shiftArr;
    }

    private static void right() {
        int[] shiftArr = new int[6];
        shiftArr[2] = dice[0];
        shiftArr[5] = dice[2];
        shiftArr[3] = dice[5];
        shiftArr[0] = dice[3];
        shiftArr[1] = dice[1];
        shiftArr[4] = dice[4];
        dice = shiftArr;
    }

}
```

<br>
<br>

# **๐Description**

> ์ค๊ณ ์๊ฐ: 30min

> ๊ตฌํ ์๊ฐ: 1hr
<aside>
๐ก ์ค๊ณ ์์ด๋์ด

๊ทธ๋ฅ ํ๋ผ๋๋๋ก ์์๋๋ก ํด์ฃผ์๋ค.
(์ฝ๋์ฐธ๊ณ )

</aside>

<br>
<br>

# **๐Related Issues**

> Related Issues
<aside>

- ์ฃผ์ฌ์2๋ง ๋ดค์ ๋๋ ์ด๋ ต๊ฒ ๋๊ปด์ ธ์ ๊ทธ ์ ์ ์ฃผ์ฌ์1์ ๋จผ์  ํ๊ณ ์จ ํ ๋ค์ ํ์๋ค. ๊ทธ๋์ ์์ฒญ๋๊ฒ ์ด๋ ต์ง ์์๋ค. ์ฌ์ค ์ฃผ์ฌ์ ์ ๊ฐ๋๋ฅผ ์ํ์ข์ฐ๋ก ๊ตด๋ฆด ๋ ๋ณํ์ํค๋ ๊ฒ์ด ์ฒ์ ๋ณผ ๋๋ ์กฐ๊ธ ์ด๋ ค์ธ ์ ์๋๋ฐ ํ๋ํ๋ ๊ทธ๋ ค๋ณด๋ ๊ด์ฐฎ์๋ค. (๊ตฌํ์ด ๊ท์ฐฎ์๋ฟ..)
- ์ผ๋ถ ์ค๋ณต๋๋ ์ฝ๋ ๋ณต๋ถ ๊ณผ์ ์์ dir ์ฒ๋ฆฌ๋ฅผ ์๋ชปํด์ ์ค๊ฐ์ ๋๋ฒ๊นํด๋ณด๋ฉด์ ์ค์๋ฅผ ๊นจ๋ซ๊ณ  ๊ณ ์ณค๋ค.. **์ฝ๋ ๋ณต๋ถํ  ๋ ๋ฐ๋์ ์ด ์๋์ง ์ ์ฒดํฌํ๊ธฐ!**
- ์ฃผ์ฌ์1์ ๋์๋ถ๋จ์ผ๋ก ํ๋๊ฒ ํธํ๋๋ฐ ์ฃผ์ฌ์2๋ ์๊ณ๋ฐฉํฅ, ๋ฐ์๊ณ๋ฐฉํฅ ์กฐ๊ฑด์ด ์์ด์ ๋ถ๋๋จ์์ฒ๋ผ ์ค์ ํ๋ ๊ฒ์ด ๋ ํธํ๋ค. ์ด๊ฑธ ๋์ค์ ๋ค ๋ฐ๊พธ๋๋ผ ์๊ฐ์ด ์กฐ๊ธ ๊ฑธ๋ ธ์๋ค. ์์ค์๋ ์์๊ณ ..
- ๋ฐฉ๋ฌธ ๋ฐฐ์ด ๋์ณ์ ๋ฌดํ๋ฃจํ๋ฅผ ๋์๋ค.. ๊ทธ๋ํ ํ์์์ ๋ฐฉ๋ฌธ๋ฐฐ์ด์ ๋์น๋ค๋!
</aside>

<br>
<br>

# **๐Resource**

| Memory | Time  | Implementation Time |
| -- |-------|---------------------|
| 27020KB | 220ms | 1 Hour 30 Minutes   |
