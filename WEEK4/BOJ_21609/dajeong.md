![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2021609&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **๐Problem**

> [BOJ 21609 ์์ด ์คํ๊ต](https://www.acmicpc.net/problem/21609)

<br>
<br>

# **Code**

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    static int[][] board;
    static boolean[][] visOutBfs, visInBfs;
    static int N, M;
    static int[] dix = {-1, 1, 0, 0}; // ์ํ์ข์ฐ
    static int[] diy = {0, 0, -1, 1};
    static List<ZzangInfo> zzangList;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken()); // ํ๋ณ ๊ธธ์ด
        M = Integer.parseInt(st.nextToken()); // ์ผ๋ฐ๋ธ๋ก ์์ ๊ฐฏ์ -> ์์ค ์ด๊ฑฐ ์ด ์๋๋ผ๊ตฌใ 

        // ์๋ ฅ
        board = new int[N][N];
        visOutBfs = new boolean[N][N]; // ๋ธ๋ก ๊ทธ๋ฃน ์ฐพ์ ๋ ์ฐ๋ ๋ฐฉ๋ฌธ ๋ฐฐ์ด (๋ฌด์ง๊ฐ ๋ธ๋ก x)
        visInBfs = new boolean[N][N]; // bfs ๋ด์์ ์ธ์ ํ ๋ธ๋ก ์ฐพ์ ๋ ์ฌ์ฉํ  ๋ฐฉ๋ฌธ๋ฐฐ์ด (๋ฌด์ง๊ฐ ๋ธ๋ก ํฌํจ)

        // ์๋ ฅ
        for (int r = 0; r < N; r++) {
            st = new StringTokenizer(br.readLine());
            for (int c = 0; c < N; c++) { // M์ผ๋ก ์๋ชป ์ ์ด์ ๊ณ์ ํ๋ ธ๋ค..
                board[r][c] = Integer.parseInt(st.nextToken());
            }
        }

        int ans = 0; // ์ ์ํฉ (์ ๋ต)
        while (true) { // ์คํ ํ๋ ์ด ๋ฐ๋ณต๋ฌธ
            // 1. ๋ธ๋ก ๊ทธ๋ฃน ํ์ (bfs)
            zzangList = new ArrayList<>(); // ํ์ํ  ๋๋ง๋ค ๋ธ๋ก๊ทธ๋ฃน๋ณ ๋ํ์ ๋ฆฌ์คํธ ๊ฐฑ์ 
            for (int r = 0; r < N; r++) { // ๋ธ๋ก ๊ทธ๋ฃน ์ฐพ๊ธฐ์ฉ ๋ฐฉ๋ฌธ ๋ฐฐ์ด ๊ฐฑ์ 
                Arrays.fill(visOutBfs[r], false);
            }

            for (int r = 0; r < N; r++) {
                for (int c = 0; c < N; c++) {
                    if (board[r][c] > 0
                        && !visOutBfs[r][c]) { // ๋ธ๋ก๊ทธ๋ฃน ํ์ํ์ง ์์ ์ผ๋ฐ ๋ธ๋ก์ผ ๋ bfs๋ก ๋ธ๋ก ๊ทธ๋ฃน ํ์
                        bfs(r, c);
                    }
                }
            }

            //2. ์ ๊ฑฐํ  ๋ธ๋ก ๊ทธ๋ฃน ์ฐพ๊ธฐ
            if (zzangList.isEmpty()) {
                break; // ์ ์ฒด ๋ธ๋ก ๊ทธ๋ฃน๋ค ์ค ๋ํ์(๋ธ๋ก ์ ๋ง์ ๊ทธ๋ฃน)์ ๋ธ๋ก ์๊ฐ 0์ด๋ฉด ์คํ ํ๋ ์ด ์ข๋ฃ
            }
            // ์ ๋ ฌ
            Collections.sort(zzangList);

            ZzangInfo target = zzangList.get(0); // ์ง์ธ ๋ธ๋ก ๊ทธ๋ฃน ์ ํจ(์ ๋ ฌ๋ ์ฒซ๋ฒ์งธ ์์)

            // 3. ๋ธ๋ก๊ทธ๋ฃน ์(cnt) ์ ๊ณฑ๋งํผ ์ ์(ans) ํ๋, board int[][]์์ ์ง์ฐ๊ธฐ
            ans += target.cnt * target.cnt;

            bfs(target.zzangX, target.zzangY); // ๋ํ์ ๋ธ๋ก์ ์ด์ฉํด์ ๋ค์ bfs ์ํ -> ์ง์ธ ๋ธ๋ก ์ฐพ๊ธฐ
            // ์ฌ์ค ์ง์ธ ๋ธ๋ก์ ์ฐพ๋ bfs์์๋ ๋ํ์๋ฅผ ๋ค์ ์ฐพ์ ํ์ ์๋ ๋ฑ ํ์์๋ ์ฐ์ฐ์ด ์ข ์์ด์ ๋ฐ๋ก boolean flag๋ฅผ ๋๋ ค๊ณ  ํ์ผ๋, ์ผ๋ฐํ ์ํค๋ ๊ณผ์ ์์ ๊ดํ ๊ผฌ์ด๋ ๋ฐ๋์ ๊ทธ๋ฅ ํ๋๋ก ์
            removeBlocks(visInBfs); // bfs ๋๋ฆฐ ๊ฒฐ๊ณผ ๊ธฐ๋กํ ๋ฐฉ๋ฌธ๋ฐฐ์ด์ ์ด์ฉํด์ ํ๊ฒ ๋ธ๋ก ๊ทธ๋ฃน ๋ธ๋ก๋ค ๋ค ์ ๊ฑฐํ๊ธฐ

            //     4. ์ค๋ ฅ ์์ฉ
            gravity();
            //    *** 5. ๋ณด๋ ๋ฐ์๊ณ ๋ฐฉํฅ ํ์ .. ***
            int[][] newBoard = new int[N][N];
            for (int x = 0; x < N; x++) {
                for (int y = 0; y < N; y++) {
                    newBoard[x][y] = board[y][N-1-x];
                }
            }
            board = newBoard;

            // 6. ์ค๋ ฅ ๋ ์์ฉ
            gravity();

        }
        // 6. ์ ์ํฉ ๋ฆฌํด
        System.out.println(ans);
    }

    private static void gravity() {
        for (int c = 0; c < N; c++) { // ์ด ์ํ
            ArrayDeque<Integer> stack = new ArrayDeque<>();
            int black = 0;
            for (int r = 0; r < N; r++) {
                if (board[r][c] >= 0) { // ๋ฌด์ง๊ฐ๋ธ๋ก, ์ผ๋ฐ๋ธ๋ก์ด๋ฉด
                    stack.push(board[r][c]);
                    board[r][c] = -2;
                } else if (r > black && board[r][c] == -1) { // ์ฒซ๋ฒ์งธ ํ์ด ์๋ ์ํฉ์์ ๊ฒ์ ๋ธ๋ก์ด๋ฉด
                    black = r; // ๊ฒ์ ๋ธ๋ก ํ ์ ์ฅ
                    while (!stack.isEmpty()) {
                        board[black - 1][c] = stack.pop();
                        black--;
                    }
                }
            }

            int idx = N-1;
            while(!stack.isEmpty()) { // if๋ฌธ์ด ์๋๋ผ while..
                board[idx--][c] = stack.pop();
            }
        }
    }

    private static void removeBlocks(boolean[][] vis) {
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                if (vis[r][c]) {
                    board[r][c] = -2; // ๋น์นธ ํ์ (0์ ๋ฌด์ง๊ฐ๋ธ๋ก์)
                }
            }
        }
    }

    private static void bfs(int r, int c) { // ๋ธ๋ก ๊ทธ๋ฃน ์ฐพ๊ธฐ์ฉ bfs
        for (int i = 0; i < N; i++) { // ๋งค bfs๋ง๋ค ๊ฒฝ๋กํ์์ฉ ๋ฐฉ๋ฌธ๋ฐฐ์ด ๊ฐฑ์ 
            Arrays.fill(visInBfs[i], false);
        }
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{r, c}); // ์์ ์ผ๋ฐ๋ธ๋ก ํ์ ๋ฃ๊ธฐ
        visOutBfs[r][c] = true; // ๊ทธ๋ฃน์ ์ฐ์ธ ์ผ๋ฐ ๋ธ๋ก ๊ธฐ๋ก์ฉ
        visInBfs[r][c] = true; // ๊ฒฝ๋ก ํ์์ฉ

        int cnt = 1; // ๋ธ๋ก ๊ทธ๋ฃน์ ๋ธ๋ก ์
        int rbCnt = 0; // ๋ฌด์ง๊ฐ ๋ธ๋ก ์
        int color = board[r][c];
        List<int[]> zzang = new ArrayList<>(); // ๋ํ์(์งฑ)์ด ๋  ์ ์๋ ํ๋ณด ๋ฆฌ์คํธ
        zzang.add(new int[]{r, c}); // ** ์ฒซ๋ฒ์งธ ๋ธ๋ก๋ ์งฑ ํ๋ณด ๋ฆฌ์คํธ์ ๋ฃ์ด์ผ ํจ!!

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int curX = cur[0];
            int curY = cur[1];
            for (int i = 0; i < 4; i++) {
                int nx = curX + dix[i];
                int ny = curY + diy[i];
                if (nx < 0 || nx >= N || ny < 0 || ny >= N || visInBfs[nx][ny]) {
                    continue;
                }

                if (board[nx][ny] < 0) {
                    continue; // ๊ฒ์ ๋ธ๋ก์ด๊ฑฐ๋ ๋น์นธ์ผ ๊ฒฝ์ฐ ๋์ด๊ฐ๊ธฐ
                }

                if (board[nx][ny] != color && board[nx][ny] != 0) {
                    continue;
                }

                if (board[nx][ny] == 0) { // ๋ฌด์ง๊ฐ ๋ธ๋ก์ด๋ฉด
                    rbCnt++; // ๊ฐฏ์ ์ธ๊ธฐ
                } else if (board[nx][ny] > 0) { // ์ผ๋ฐ ๋ธ๋ก์ด๋ฉด (๋น์นธ์ด ์์ผ๋ฏ๋ก, else if๋ก ๋ช์)
                    zzang.add(new int[]{nx, ny}); // ์งฑ ํ๋ณด ๋ฆฌ์คํธ์ ๋ฃ๊ธฐ
                }

                queue.offer(new int[]{nx, ny});
                cnt++;

                visInBfs[nx][ny] = true; // ๊ฒฝ๋กํ์์ฉ ๋ฐฉ๋ฌธ๋ฐฐ์ด์ ๊ธฐ๋ก
                if (board[nx][ny] > 0) { // ์ผ๋ฐ๋ธ๋ก์ด๋ฉด ์ด๋ฏธ ๊ทธ๋ฃน์ด ์ง์ด์ง ๋ธ๋ก์ด๋ฏ๋ก visOutBfs ๊ธฐ๋ก
                    visOutBfs[nx][ny] = true;
                }
            }
        }
        if (cnt >= 2) { //***
            // bfs ์ดํ์ ๋ํ์ ์ฐพ๊ธฐ
            Collections.sort(zzang, new Comparator<int[]>() { // ๋ํ์ ์ฐพ๊ธฐ ์ํด ์ ๋ ฌ (ํ, ์ด ์ค๋ฆ์ฐจ์)
                @Override
                public int compare(int[] o1, int[] o2) {
                    if (o1[0] == o2[0]) {
                        return o1[1] - o2[1];
                    }
                    return o1[0] - o2[0];
                }
            });
            int[] z = zzang.get(0); // ์ ํ๋ ๋ํ์
            zzangList.add(new ZzangInfo(cnt, rbCnt, z[0], z[1]));
        }
    }

    // ๊ฐ ๋ธ๋ก ๊ทธ๋ฃน๋ณ ์ ํ๋ ๋ํ์์ ์ ๋ณด ๊ฐ์ฒด
    static class ZzangInfo implements Comparable<ZzangInfo> {

        int cnt; // ํด๋น ๋ธ๋ก ๊ทธ๋ฃน ๋ธ๋ก ์
        int rbCnt; // ๋ฌด์ง๊ฐ ๋ธ๋ก ์
        int zzangX; // ๋ํ์ ํ
        int zzangY; // ๋ํ์ ์ด

        public ZzangInfo(int cnt, int rbCnt, int zzangX, int zzangY) {
            this.cnt = cnt;
            this.rbCnt = rbCnt;
            this.zzangX = zzangX;
            this.zzangY = zzangY;
        }

        @Override // ์ฐ์ด๋ณด๊ธฐ์ฉ
        public String toString() {
            return "ZzangInfo{" +
                "cnt=" + cnt +
                ", rbCnt=" + rbCnt +
                ", zzangX=" + zzangX +
                ", zzangY=" + zzangY +
                '}';
        }

        //(cnt ๋ด๋ฆผ์ฐจ์, rbCnt ๋ด๋ฆผ์ฐจ์, ๊ธฐ์ค๋ธ๋ก r,c ๋ด๋ฆผ์ฐจ์ *** ์ดํด...
        @Override
        public int compareTo(ZzangInfo o) {
            if (this.cnt == o.cnt) {
                if (this.rbCnt == o.rbCnt) {
                    if (this.zzangX == o.zzangX) {
                        return o.zzangY - this.zzangY;
                    }
                    return o.zzangX - this.zzangX;
                }
                return o.rbCnt - this.rbCnt;
            }
            return o.cnt - this.cnt;
        }
    }

}
```

<br>
<br>

# **๐Description**

> ์ค๊ณ ์๊ฐ: 30min

> ๊ตฌํ ์๊ฐ: 3hr + a
<aside>
๐ก ์ค๊ณ ์์ด๋์ด
    - ํน๋ณํ ๋ก์ง ์์ ํ๋ผ๋ ๋๋ก ํด์ค..

    ๊ฒ์์ ๋ธ๋ก์ -1,
    ๋ฌด์ง๊ฐ ๋ธ๋ก์ 0
    ์ผ๋ฐ ๋ธ๋ก m ์ดํ์ ์์ฐ์ (1์ด์ m์ดํ)

    ๋ธ๋ก ๊ทธ๋ฃน
    ๋ํ์: ์ผ๋ฐ๋ธ๋ก(0,-1 ์๋) ์ค ํ ์์์, ๊ฐ์ผ๋ฉด ์ด ์์์
    ์ผ๋ฐ๋ธ๋ก ์ต์ 1๊ฐ - ๋ชจ๋ ์ซ์ ๊ฐ์์ผ ํจ (์)
    ๊ฒ์์๋ธ๋ก (-1) ์์ผ๋ฉด ์๋จ
    ๋ฌด์ง๊ฐ๋ธ๋ก 0 ๊ฐฏ์ ์๊ด ์์

    int ans = ์ ์ํฉ
    <์คํ ํ๋ ์ด ์งํ> - ๋ธ๋ก ๊ทธ๋ฃน์ด ์กด์ฌํ๋ ๋์ ๋ฐ๋ณต
    board int[][]
    1. ๋ธ๋ก๊ทธ๋ฃน ํ์ (bfs)
    -> ์์ ๋ธ๋ก ๊ทธ๋ฃน ์กฐ๊ฑด์ ์๋ง์ผ๋ฉด ์ธ์ง ์๊ธฐ
    -> ๋ธ๋ก๊ทธ๋ฃน ์นธ ์ (cnt), ๋ฌด์ง๊ฐ ๋ธ๋ก ์(rbCnt), ๋ํ์ ๋ธ๋ก(int[]{r,c}) ๊ตฌํ๋ค.=> ๊ฐ์ฒด zzang list์ ์ ์ฅ (cnt, rbCnt, ๋ํ์ ๋ธ๋ก(int[]{r,c})

    2. ์ ๊ฑฐํ  ๋ธ๋ก ๊ทธ๋ฃน ์ฐพ๊ธฐ ( ๊ทธ๋ฃน์ ์ํ ๋ธ๋ก์ ๊ฐ์๋ 2๋ณด๋ค ํฌ๊ฑฐ๋ ๊ฐ์์ผ ํจ!!!!)
    *** -> zzang list ์ ๋ ฌ (cnt ๋ด๋ฆผ์ฐจ์, rbCnt ๋ด๋ฆผ์ฐจ์, ๊ธฐ์ค๋ธ๋ก r,c ๋ด๋ฆผ์ฐจ์ ***
    -> ๋ธ๋ก ๊ทธ๋ฃน ์ต๋ ์นธ์๊ฐ 0์ผ ๋ ์คํ ํ๋ ์ด ์ข๋ฃ.
    3. ๋ธ๋ก๊ทธ๋ฃน ์(cnt) ์ ๊ณฑ๋งํผ ์ ์(ans) ํ๋, board int[][]์์ ์ง์ฐ๊ธฐ
    4. ์ค๋ ฅ ์์ฉ
        - stack ์ฌ์ฉ (๋ฒฝ๋๊นจ๊ธฐ ๋ผ์ด๋ธ ๋ฐฉ์ก ๋ฐฉ์ ์ฌ์ฉ - ๋ค๋ฅธ ๋ฐฉ๋ฒ๋ ํ์ธ)
    *** 5. ๋ณด๋ ๋ฐ์๊ณ ๋ฐฉํฅ ํ์ .. ***
    6. ์ค๋ ฅ ๋ ํ๋ค.
    <์คํ ํ๋ ์ด ์ข๋ฃ>
    7. ์ ์ํฉ ๋ฆฌํด
</aside>

<br>
<br>

# **๐Related Issues**

> Related Issues
<aside>

- ๋๋ฌด ๊ณ ์ํ ๋ฌธ์ ..
- ์ฌ์ค ๋ก์ง์ด ์ด๋ ต๋ค๊ธฐ๋ณด๋ค๋ ๊ตฌํ์์ ๋๋ฌด ๊ท์ฐฎ๊ณ ,, ์ค์ ๋๋ฌธ์ ๋ค์ ๋นก์๋ค..
- ์ค๊ฐ์ค๊ฐ์ ํ๋ฆฐํธ ์ฐ์ด๋ณด๋ฉด์ ํ๋ฆฐ๊ฑฐ ๊ณ์ ๊ณ ์ณ๋๊ฐ๋ค.
- ์ค์ ๋ฐ ์ธ์ฌ์ดํธ
    - N,M์ด ํ๊ณผ ์ด์ธ์ค ์ฐฉ๊ฐํจ. ์ด๋ฐ ๋์์ ์์ง๋ ๋์๋๋ค๋..
    - ๋ธ๋ก ๊ทธ๋ฃน์ ๋ธ๋ก ์๊ฐ 2์ด์์ธ๋ฐ, ํด๋น ์กฐ๊ฑด์ ๋ฃ์ง ์์์ ๋ ๋ง์ ์ ์ํฉ์ด ๋์์๋ค. (1๊ฐ์ธ ๋ธ๋ก๋ ๊ทธ๋ฃน์ผ๋ก ์ธ๊ธฐ ๋๋ฌธ). ๋ฌธ์  ์กฐ๊ฑด์ ๋ค ๊ธฐ์ต ๋ชปํ ๊ฑฐ๋ฉด ์ฝ๋์ ์ ์ด๋๊ณ  ์ค๊ฐ์ค๊ฐ ๋ง๋์ง ์๋์ง ํ๋ฆฐํธ ์ฐ๊ฑฐ๋ ๋ฌธ์  ๋ค์ ํ์ธํ์. (๋๋ฅผ ๋ฏฟ์ง ๋ง์..)
    - ์ผ๋ฐํ ํ๋ค๊ฐ ๊ผฌ์ผ๊ฑฐ๋ฉด, ๊ทธ๋ฅ ๋ฐ๋ก๋ฐ๋ก ๊ตฌํํ์!
        - bfs ์ผ๋ฐํ ํด๋ณด๋ ค๊ณ  ๊ดํ boolean ๋ ์ฐ๋ ค๋ค๊ฐ ๋จธ๋ฆฟ์์์ ๊ผฌ์ฌ์ ๊ทธ๋ฅ bfs ํ๋๋ก ํ์ณค๋ค. ์๊ฐ ๋ณต์ก๋์ ํฐ ์ํฅ ์์ผ๋ฉด ํ์น๋ ๊ฒ๋ ์ข์ ๊ฒ ๊ฐ๋ค
        - ๋ฐฉ๋ฌธ๋ฐฐ์ด๋ ํ๋๋ก ํ์น๋ ค๋ค๊ฐ, bfs ๋ด์์ ๊ฒฝ๋ก (๋ฌด์ง๊ฐ ๋ธ๋ก ํฌํจ) ํ์์ฉ ๋ฐฉ๋ฌธ๋ฐฐ์ด๊ณผ bfs ๋ฐ์์ ์ฌ์ฉํ  ์ผ๋ฐ ๋ธ๋ก์ฉ ๋ฐฉ๋ฌธ๋ฐฐ์ด์ ๋ฐ๋ก ๋๋๊ฒ ์๊ผฌ์ฌ์ ๋ค์ ๊ตฌํ์ ํ์๋ค.
    - ์ค๋ ฅ ๊ตฌํ์์ ์๊ฐ ์ข ์กํ..
        - stack์ผ๋ก ๊ตฌํํด๋ณด์๋๋ฐ ๋ฐฉํด๋ฌผ -1์ด ์ด์์ ๋์ค์ง ์์์ ๋์๋ ์ถ๋ ฅํด์ฃผ์ด์ผ ํ๋๋ฐ ๋ชปํด์ค
        - stack์ด ๋น์ด์์ง ์์ ๋ ๊ธฐ์ค์ผ๋ก while๋ฌธ ๋์์ผ ํ๋๋ฐ if๋ฌธ์ ๋์๋ค.
</aside>

<br>
<br>

# **๐Resource**

| Memory | Time  | Implementation Time |
| -- |-------|---------------------|
| 22680KB | 208ms | 3 Hour 30 Minutes   |
