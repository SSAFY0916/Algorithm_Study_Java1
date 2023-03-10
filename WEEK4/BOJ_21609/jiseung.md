
![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2021609&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **๐Problem**

> BOJ 21609 ์์ด ์คํ๊ต
> 

# ๐ป**Code**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class Main {

    static BufferedReader br;
    static BufferedWriter bw;
    static StringTokenizer st;
    static int n, m, answer; // ํ, ์ด, ์ ์์ ์ต๋๊ฐ
    static int[][] board; // ๊ฒฉ์์ ์๋ ๋ธ๋ก๋ค์ ์ข๋ฅ๋ฅผ ์ ์ฅํ๋ ๋ฐฐ์ด => -2๋ ๋น ์นธ
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, -1, 0, 1};

    static class Pair implements Comparable<Pair> { // ์ขํ๋ฅผ ์ ์ฅํ๋ ํด๋์ค
        int x, y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(Pair pair) { // ์ขํ ๋น๊ต๋ฅผ ์ํ ์ค๋ฒ๋ผ์ด๋ฉ, ํ๋ผ๋ฏธํฐ๋ก ๋ฐ์ pair๋ณด๋ค ํ์ด ์๊ฑฐ๋, ํ์ ๊ฐ์๋ฐ ์ด์ด ์์ ๋ -1 ๋ฆฌํด
            return (this.x == pair.x) ? Integer.compare(this.y, pair.y) : Integer.compare(this.x, pair.x);
        }
    }
    static class BlockGroup implements Comparable<BlockGroup> { // ๋ธ๋ญ๊ทธ๋ฃน์ ์ ์ฅํ๋ ๋ฐฐ์ด
        List<Pair> locations; // ๋ธ๋ญ๊ทธ๋ฃน์ ๋ธ๋ญ๋ค์ ์ขํ
        int countRainbow; // ๋ฌด์ง๊ฐ ๋ธ๋ญ์ ์
        Pair pair; // ๊ธฐ์ค๋ธ๋ญ์ ์ขํ

        public BlockGroup(int x, int y) {
            this.locations = new ArrayList<>();
            this.countRainbow = 0;
            this.pair = new Pair(x, y);
        }

        @Override
        public int compareTo(BlockGroup blockGroup) { // ๋ธ๋ญ ๊ฐ์, ๋ฌด์ง๊ฐ ๋ธ๋ญ ์, ์ขํ ์์๋ก ๋น๊ต
            if (this.locations.size() == blockGroup.locations.size()) {
                if(this.countRainbow == blockGroup.countRainbow) {
                    return this.pair.compareTo(blockGroup.pair);
                }
                return Integer.compare(this.countRainbow, blockGroup.countRainbow);
            }
            return Integer.compare(this.locations.size(), blockGroup.locations.size());
        }
    }

    public static void main(String[] args) throws Exception{
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        init();

        solve();

        bw.write(answer + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    // ์๋ ฅ์ ์ฒ๋ฆฌํ๋ ๋ฉ์๋
    static void init() throws Exception {
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        board = new int[n][n];
        for(int i=0; i<n; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<n; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }

    static void solve() {
        while(true) {
            BlockGroup maxBlockGroup = findMaxBlockGroup();
            if(maxBlockGroup.locations.size() < 2) { // ๊ฐ์ฅ ํฐ ๋ธ๋ญ๊ทธ๋ฃน์ ํฌ๊ธฐ๊ฐ 2๋ณด๋ค ์์ผ๋ฉด ๊ทธ๋ง
                break;
            }
            increaseScore(maxBlockGroup);
            removeMaxBlockGroup(maxBlockGroup);
            gravity();
            rotate();
            gravity();
        }
    }

    // ๊ฒฉ์์ ๋ธ๋ญ๋ค์์ ๊ฐ์ฅ ํฐ ๋ธ๋ญ๊ทธ๋ฃน์ ๋ฆฌํด
    static BlockGroup findMaxBlockGroup() {
        boolean[][] visited = new boolean[n][n]; // ์ด๋ฏธ ๋ธ๋ญ๊ทธ๋ฃน์ ์ฐพ์ ๋ธ๋ญ์ ๋ํด์ ๋ฐ๋ณต์ ํผํ๊ธฐ ์ํ ๋ฐฉ๋ฌธ์ฌ๋ถ ์ ์ฅ ๋ฐฐ์ด
        BlockGroup maxBlockGroup = new BlockGroup(-1, -1); // ๋ฆฌํด ํด์ค ๋ธ๋ญ๊ทธ๋ฃน ์ด๊ธฐํ
        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {
                if(visited[i][j] || board[i][j] <= 0) { // ์ด๋ฏธ ๋ฐฉ๋ฌธํ๊ฑฐ๋ ๊ฒ์ ๋ธ๋ญ์ด๋ฉด ๊ฑด๋๋
                    continue;
                }
                // ์ด๋ฒ bfs์์ ๋ฐ๋ณต์ ํผํ๊ธฐ ์ํด ์ฌ์ฉํ๋ ๋ฐฉ๋ฌธ ์ฌ๋ถ ์ ์ฅ ๋ฐฐ์ด
                // visited๋ง ์ด์ํ๋ฉด ๋ฌด์ง๊ฐ ๋ธ๋ญ์ ์ฌ๋ฌ ๊ทธ๋ฃน์ ๋ฃ์ ์ ์์ด์ ๋ ๊ฐ์ ๋ฐฐ์ด ์ฌ์ฉ
                boolean[][] visited2 = new boolean[n][n];
                Queue<Pair> q = new ArrayDeque<>();
                BlockGroup blockGroup = new BlockGroup(n, n); // ์ด๋ฒ bfs์์ ๋ง๋ค ๋ธ๋ญ๊ทธ๋ฃน ์ด๊ธฐํ
                q.add(new Pair(i, j));
                while(!q.isEmpty()) { // bfs
                    Pair pair = q.poll();
                    if(visited2[pair.x][pair.y]) { // ์ด๋ฒ bfs์์ ์ด๋ฏธ ๋ฐฉ๋ฌธํ ๋ธ๋ญ์ด๋ฉด ๊ฑด๋๋
                        continue;
                    }
                    blockGroup.locations.add(pair); // ๋ธ๋ญ๊ทธ๋ฃน์ ๋ฃ๊ธฐ
                    if(board[pair.x][pair.y] == 0) {
                        blockGroup.countRainbow++; // ๋ธ๋ญ๊ทธ๋ฃน ๋ฌด์ง๊ฐ ๋ธ๋ญ ์ ์ฆ๊ฐ
                    }else {
                        if(blockGroup.pair.compareTo(pair) > 0) {
                            blockGroup.pair = pair; // ๋ธ๋ญ๊ทธ๋ฃน์ ๊ธฐ์ค๋ธ๋ญ ๋ณ๊ฒฝ
                        }
                        visited[pair.x][pair.y] = true; // ์ด๋ฏธ ๋ธ๋ญ๊ทธ๋ฃน์ ์ฌ์ฉ๋ ์ผ๋ฐ ๋ธ๋ญ์ด๋ฏ๋ก ๋ฐฉ๋ฌธ ์ฒ๋ฆฌํ๊ณ  ์ด ๋ธ๋ญ์ผ๋ก ๋ธ๋ญ๊ทธ๋ฃน ๋ง๋ค๊ธฐ๋ฅผ ์๋ํ์ง ์๋๋ค
                    }
                    visited2[pair.x][pair.y] = true; // ์ด๋ฒ bfs์์์ ๋ฐฉ๋ฌธ ์ฒ๋ฆฌ
                    for(int k=0; k<4; k++) {
                        int newx = pair.x + dr[k];
                        int newy = pair.y + dc[k];
                        if(newx<0 || newx>=n || newy<0 || newy>=n)
                            continue;
                        if (board[newx][newy] == board[i][j] || board[newx][newy] == 0) { // ๊ฐ์์ ๋ธ๋ญ๊ณผ ๋ฌด์ง๊ฐ ๋ธ๋ญ๋ง ํ์ ๋ฃ์
                            q.add(new Pair(newx, newy));
                        }

                    }
                }
                if(blockGroup.locations.size() < 2) { // ํฌ๊ธฐ๊ฐ 2๋ณด๋ค ์์ผ๋ฉด ๋ธ๋ญ๊ทธ๋ฃน์ด ์๋๋ค
                    continue;
                }
                if(maxBlockGroup.compareTo(blockGroup) < 0) { // ๊ฐ์ฅ ํฐ ๋ธ๋ญ๊ทธ๋ฃน๊ณผ ๋น๊ต
                    maxBlockGroup = blockGroup;
                }
            }
        }
        return maxBlockGroup;
    }

    // ๋ธ๋ญ๊ทธ๋ฃน์ ์๋ ฅ๋ฐ์ ๋ธ๋ญ๊ทธ๋ฃน์ ๋ธ๋ญ ๊ฐ์์ ์ ๊ณฑ๋งํผ ์ ์๋ฅผ ์ฆ๊ฐ์ํจ๋ค
    static void increaseScore(BlockGroup blockGroup) {
        answer += blockGroup.locations.size() * blockGroup.locations.size();
    }

    // ๋ธ๋ญ๊ทธ๋ฃน์ ์๋ ฅ๋ฐ์ ๋ธ๋ญ๋ค์ ์ ๊ฑฐํ๋ค
    static void removeMaxBlockGroup(BlockGroup blockGroup) {
        for(Pair pair : blockGroup.locations) {
            board[pair.x][pair.y] = -2; // -2๋ก ๋ณ๊ฒฝ
        }
    }

    // ๊ฒ์์ ๋ธ๋ญ์ ์ ์ธํ๊ณ  ๋ค๋ฅธ ๋ธ๋ญ์ด๋ ๊ฒฉ์ ๋๊น์ง ๋ธ๋ญ๋ค์ ๋์ด๋ด๋ฆผ
    static void gravity() {
        for (int j = 0; j < n; j++) { // ์ผ์ชฝ์์ ์ค๋ฅธ์ชฝ์ผ๋ก
            int index = n-1; // ๋ค์์ ๋ธ๋ญ์ ๋์ด๋์ ์์น
            for (int i = n - 1; i >= 0; i--) { // ์๋์์๋ถํฐ ์๋ก
                if(board[i][j] == -2) { // ๋น ์นธ์ด๋ฉด ๊ฑด๋๋
                    continue;
                }
                if(board[i][j] == -1) { // ๊ฒ์  ๋ธ๋ญ์ด๋ฉด ๊ฒ์  ๋ธ๋ญ ์๋ก ๋์ด๋ด๋ ค์ง๊ฒ ํ์ฌ ์์น๋ณด๋ค ํ ์นธ์๋ก index ๋ณ๊ฒฝ
                    index = i-1;
                    continue;
                }
                if(index == i) { // ๋์ด๋ด๋ ค๋ดค์ ์ง๊ธ ์์น๋ฉด ๊ฑด๋๋
                    index--; // ๋ค์ ๋ธ๋ญ์ ์ด ๋ธ๋ญ ์๋ก
                    continue;
                }
                board[index--][j] = board[i][j]; // ๋์ด๋ด๋ฆผ
                board[i][j] = -2; // ์๋ ๋ธ๋ญ ์์น๋ ๋น ์นธ์ผ๋ก
            }
        }
    }

    // ๊ฒฉ์๋ฅผ ์๊ณ ๋ฐ๋ ๋ฐฉํฅ์ผ๋ก 90๋ ํ์ ์ํด
    static void rotate() {
        int[][] newBoard = new int[n][n]; // ์๋ก์ด ๊ฒฉ์ ๋ฐฐ์ด ์์ฑ
        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {
                newBoard[n-1-j][i] = board[i][j];
            }
        }
        board = newBoard; // ๊ธฐ์กด์ ๊ฒฉ์๋ฅผ ์๋ก์ด ๊ฒฉ์๋ก ๋ณ๊ฒฝ
    }
}
```

# **๐Description**

> ๊ธฐ์ค๋ธ๋ญ์ ์ ํ๋ ๋ฐฉ๋ฒ๊ณผ ๊ฐ์ฅ ํฐ ๋ธ๋ญ๊ทธ๋ฃน์ ์ ํ๋ ๋ฐฉ๋ฒ์ด ๋ฌ๋ผ์ ๊ณ ์น๋๋ฐ ์ค๋๊ฑธ๋ ธ๋ค.<br>
> comarable ์์๋ฐ์์ ๋น๊ตํจ์ ์ค๋ฒ๋ผ์ด๋ฉํ๋๋ฐ ํฐ๊ฒ ์์์ธ์ง ์์๊ฒ ์์์ธ์ง ํท๊ฐ๋ ธ๋ค.<br>
> ๋ธ๋ญ์ ๋์ด๋ด๋ฆฌ๊ณ  ์๋ ์์น๋ฅผ ๋ฐ๋ก ๋น ์นธ์ผ๋ก ๋ง๋ค์ด์ ๋์ด๋ด๋ ค๋ ์๋ ์์น์ธ ๋ธ๋ญ๋ค์ ์๊พธ ์ฌ๋ผ์ง๊ฒ ๋ง๋ค์๋ค.<br>
> ๊ธฐ์ค๋ธ๋ญ์ด ๋ฌด์ง๊ฐ ๋ธ๋ญ์ผ๋ก ์ ํ๋ ์ค ์์๋ค.<br>
> ๋ฐฉ๋ฌธ ๋ฐฐ์ด์ ์ด์ํ๋ ๋ฐฉ๋ฒ์ ์ด๋ ค์์ด ์์ด์ ๋ ๊ฐ๋ฅผ ์ฌ์ฉํ๋ค.<br>
> ๋ธ๋ญ ์ ๊ฑฐํ  ๋ ์ขํ๋ฅผ ์๋ชป ์๋ ฅํ๋๋ฐ ์ด๊ฑฐ ์ฐพ๋๋ฐ ์ค๋๊ฑธ๋ ธ๋ค.<br>

# **๐Related Issues**

> ์๋ชป ๊ตฌํํ ๋ถ๋ถ์ด ๋ง์์ ํธ๋๋ฐ ์ค๋๊ฑธ๋ ธ๋ค.

# **๐Resource**

| Memory | Time |
| --- | --- |
| 22776KB | 188ms |