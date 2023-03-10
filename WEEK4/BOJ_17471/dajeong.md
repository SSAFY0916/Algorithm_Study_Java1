![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2017471&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **๐Problem**

> [BOJ 17471 ๊ฒ๋ฆฌ๋งจ๋๋ง](https://www.acmicpc.net/problem/17471)

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

public class Main {

    static int N;
    static int[] peopleCnt;
    static int[][] map;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine()); // 1~N -> 0~N-1
        peopleCnt = new int[N];
        map = new int[N][N]; // N ํฌ๊ธฐ ์์์ ํธ์์ ์ธ์ ํ๋ ฌ๋ก ๊ตฌํํจ
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            peopleCnt[i] = Integer.parseInt(st.nextToken());
        }
        for (int n = 0; n < N; n++) {
            st = new StringTokenizer(br.readLine());
            int m = Integer.parseInt(st.nextToken());
            for (int i = 0; i < m; i++) {
                int e = Integer.parseInt(st.nextToken()) - 1; // ์ ์  0๋ถํฐ ์์ํ๊ฒ ๋ง์ถฐ์ค
                map[n][e] = 1; // ๋ฌดํฅ๊ทธ๋ํ์ด๊ธฐ๋๋ฌธ์ from, to ๋๋ค ๋ฃ๊ธฐ
                map[e][n] = 1;
            }
        }

        boolean[] area1 = new boolean[N]; // ์ ๊ฑฐ๊ตฌ1์์ ๊ฐ๋ฅํ ๊ตฌ์ญ์ boolean์ผ๋ก ํํํ ๋ถ๋ถ์งํฉ
        boolean[] area2 = new boolean[N]; // ์ ๊ฑฐ๊ตฌ2์์ ๊ฐ๋ฅํ ๊ตฌ์ญ boolean์ผ๋ก ํํํ ๋ถ๋ถ์งํฉ
        int ans = Integer.MAX_VALUE; // ์ต์ ์ธ๊ตฌ์ ์ฐจ์ด (์ ๋ต)
        for (int i = 0; i < (1 << N); i++) { // ๋นํธ์ฐ์ฐ์ผ๋ก ๊ฐ๋ฅํ ๋ถ๋ถ์งํฉ ๋ง๋ค๊ธฐ
            if (Integer.bitCount(i) == 0 || Integer.bitCount(i) == N) { // ๊ณต์งํฉ์ด๊ฑฐ๋ ์ ์ฒด์งํฉ์ผ ๊ฒฝ์ฐ ์ ๊ฑฐ๊ตฌ๊ฐ 2๊ฐ๊ฐ ์๋๋ฏ๋ก ๋ฌด์
                continue;
            }
            // ์ ๊ฑฐ๊ตฌ ๊ฒฝ์ฐ์ ์ ์ด๊ธฐํ
            Arrays.fill(area1, false);
            Arrays.fill(area2, false);

            // ๊ณต์งํฉ, ์ ์ฒด์งํฉ์ด ์๋ ๋ถ๋ถ์งํฉ(์ ๊ฑฐ๊ตฌ ๋ณ ๊ตฌ์ญ) ์ ํ๊ธฐ
            for (int k = 0; k < N; k++) {
                if ((i & (1 << k)) != 0) {
                    area1[k] = true;
                } else {
                    area2[k] = true;
                }
            }
            // ๊ฐ๋ฅํ ์ ๊ฑฐ๊ตฌ์ผ ๊ฒฝ์ฐ ์ธ๊ตฌ ์ ์ฐจ์ด ๊ตฌํ๊ธฐ
            if (isAvailable(area1, area2)) {
                int diff = calculate(area1);
                ans = Math.min(diff, ans); // ์ธ๊ตฌ ์ ์ฐจ์ด๊ฐ ์ต์๊ฐ ๋๋ ๊ฒ์ ์ ๋ต์ผ๋ก ์ถ๋ ฅ
            }
        }
        // ์ ๊ฑฐ๊ตฌ๊ฐ ํ์ ์ด ์๋  ๊ฒฝ์ฐ, -1๋ฆฌํด. ๊ฐ๋ฅํ  ๊ฒฝ์ฐ ์ต์ ์ธ๊ตฌ์ ์ฐจ์ด ์ถ๋ ฅ
        if (ans == Integer.MAX_VALUE) {
            System.out.println(-1);
        } else {
            System.out.println(ans);
        }
    }

    // ๊ฐ๋ฅํ ์ ๊ฑฐ๊ตฌ์ธ์ง ์ฒดํฌํ๋ ํจ์
    // ๊ฐ ์ ๊ฑฐ๊ตฌ1, ์ ๊ฑฐ๊ตฌ2๋ก ์ดํด๋ณผ ๋ถ๋ถ์งํฉ๋ณ๋ก ์ธ์ ํ ๊ตฌ์ญ๋ผ๋ฆฌ ๋ชจ์ฌ์๋์ง ์ฒดํฌ
    private static boolean isAvailable(boolean[] area1, boolean[] area2) {
        int startIdx1 = 0; // ์ ๊ฑฐ๊ตฌ1์์ ํ์์ ์์ํ  ์ ์ 1
        int startIdx2 = 0; // ์ ๊ฑฐ๊ตฌ2์์ ํ์์ ์์ํ  ์ ์ 2

        // ์ ๊ฑฐ๊ตฌ1์ ์ฒซ ์์ ์ ์ (๊ตฌ์ญ) ๊ณ ๋ฅด๊ธฐ
        for (int n = 0; n < N; n++) {
            if (area1[n]) {
                startIdx1 = n;
                break;
            }
        }

        // ์ ๊ฑฐ๊ตฌ2์ ์ฒซ ์์ ์ ์ (๊ตฌ์ญ) ๊ณ ๋ฅด๊ธฐ
        for (int n = 0; n < N; n++) {
            if (area2[n]) {
                startIdx2 = n;
                break;
            }
        }
        // ๋ถ๋ถ์งํฉ ๊ตฌ์ญ๋ค๋ผ๋ฆฌ ์๋ก ์ธ์ ํด์๋์ง ์ฒดํฌ. ์ธ์ ํ์ง ์์๋ค๋ฉด, false ๋ฆฌํด
        if (!adjCheck(startIdx1, area1)) {
            return false;
        }
        if (!adjCheck(startIdx2, area2)) {
            return false;
        }
        // ๊ฐ๋ฅํ ์ ๊ฑฐ๊ตฌ์ผ ๊ฒฝ์ฐ true ๋ฆฌํด
        return true;
    }

    // ์ธ์  ๊ตฌ์ญ์ธ์ง ์ฒดํฌํ๋ ๋ฉ์๋ (bfs ์ฌ์ฉ)
    private static boolean adjCheck(int v, boolean[] area) {
        boolean[] vis = new boolean[N];
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(v);
        vis[v] = true;

        while (!queue.isEmpty()) {
            int cur = queue.poll();
            for (int i = 0; i < N; i++) {
                if (map[cur][i] == 1 && !vis[i]) { // ์ง๋์์ ์ธ์ ํ๊ณ , ์์ง ํ์ํ์ง ์์ ์ ์ ์ผ ๊ฒฝ์ฐ ํ์ ๋ฃ๊ธฐ
                    if (!area[i]) {
                        continue; // ๋ค๋ง, ์ ๊ฑฐ๊ตฌ ์ง์ญ์ ํฌํจ ์๋๋ฉด ํ์ธํ  ํ์ ์์ผ๋ฏ๋ก pass
                    }
                    queue.offer(i); // ์ ๊ฑฐ๊ตฌ์ ํฌํจ๋๊ณ , ์ธ์ ํ  ๊ฒฝ์ฐ ํ์ ๋ฃ๊ณ  ๋ฐฉ๋ฌธ ํ์ํ๊ธฐ
                    vis[i] = true;
                }
            }

        }

        // ์ ๊ฑฐ๊ตฌ ๋ถ๋ถ์งํฉ๊ณผ bfs๋ก ํ์ํ ์ธ์  ์ ์  ๋ฐฉ๋ฌธ ๋ฐฐ์ด์ ๋น๊ตํ๋ฉฐ, ํ๋๋ผ๋ ํ๋ฆฌ๋ฉด false ๋ฆฌํด
        for (int i = 0; i < N; i++) {
            if (vis[i] != area[i]) {
                return false;
            }
        }
        return true;
    }

    // ์ธ์ ์ ์ฐจ์ด ๊ณ์ฐํ๋ ๋ฉ์๋
    private static int calculate(boolean[] area1) {
        int sum1 = 0;
        int sum2 = 0;
        for (int i = 0; i < N; i++) {
            if (area1[i]) {
                sum1 += peopleCnt[i];
            } else {
                sum2 += peopleCnt[i];
            }
        }

        return Math.abs(sum1 - sum2);
    }
}
```

<br>
<br>

# **๐Description**

> ์ค๊ณ ์๊ฐ: 30min

> ๊ตฌํ ์๊ฐ: 2hr + a
<aside>
๐ก ์ค๊ณ ์์ด๋์ด

    - ์ต์ ์ธ๊ตฌ์ ๋ณ์๋ฅผ Integer.MAX_VALUE๋ก ์ ์ฅ
    - ์ ์ฒด ์งํฉ์ 2๊ฐ์ ๋ถ๋ถ์งํฉ์ผ๋ก ๋๋๊ธฐ (๋นํธ์ฐ์ฐ ์ด์ฉ) - ๊ณต์งํฉ/์ ์ฒด์งํฉ์ ์๋จ
    - ๊ฐ ๋ถ๋ถ์งํฉ๋ณ๋ก ์์๋ค๋ผ๋ฆฌ ์๋ก ์ธ์ ํด์๋์ง ์ฒดํฌ (bfs ์ด์ฉ)
        - ์ฒ์ ์์๋ฅผ ํ์ ์ง์ด๋ฃ๊ณ (์์ ์ ์ ) ์ธ์ ํด์๋๋ฐ ์ ๊ฑฐ๊ตฌ ๋ถ๋ถ์งํฉ์ ํด๋นํ  ๊ฒฝ์ฐ์๋ง vis ๋ฐฉ๋ฌธํ์ ํ๋ค.
        - ๋ง์ง๋ง์ vis ๋ฐฐ์ด๊ณผ area ๋ฐฐ์ด์ ๋ฐ๋ก ํ์ธํด์ ์ด ์ ๊ฑฐ๊ตฌ๊ฐ ๊ฐ๋ฅํ ์ ๊ฑฐ๊ตฌ์ธ์ง ํ์ธํจ
    - ๊ฐ๋ฅํ ์ ๊ฑฐ๊ตฌ๋ค์ผ ๊ฒฝ์ฐ, ์ธ๊ตฌ์ ์ฐจ์ด๋ฅผ ๊ณ์ฐํ๊ณ  ์ต์ ์ธ๊ตฌ์์ min ์ฐ์ฐํ์ฌ ์ ์ฅ (๋ฐ๋ณต)
    - ๋ง์ฝ ์ต์ ์ธ๊ตฌ์ ๋ณ์๊ฐ max value์ผ ๊ฒฝ์ฐ, ์ ๊ฑฐ๊ตฌ ํ์  ์์ฒด๊ฐ ์๋์๋ค๋ ๋ป์ด๋ฏ๋ก -1 ์ถ๋ ฅ ์ ๊ฑฐ๊ตฌ ํ์  ๊ฐ๋ฅํ  ๊ฒฝ์ฐ ์ต์ ์ธ๊ตฌ์ ์ถ๋ ฅ

</aside>

<br>
<br>

# **๐Related Issues**

> Related Issues
<aside>

- ์๊ณ ๋ฆฌ์ฆ ๊ธฐ๋ณธ๊ธฐ๋ฅผ ํํํ ํ์.
    - ๊ทธ๋ํ ์ธ์  ํ๋ ฌ, ์ธ์  ๋ฆฌ์คํธ ํ์ ๋ฐฉ์์ด ํท๊ฐ๋ ค์ ์ผํ๋ค..ใใ ๋งจ๋  ์ฌ๋ฐฉํ์ ๋ก์ง๋ง ์ง๋ค๊ฐ .. ใ ๊ทธ๋์ ์ด์ฐธ์ ์ ๋ฒ์ ๋์ถฉ ๋์ด๊ฐ์๋ DFS์ BFS ๋ฐฑ์ค ๋ฌธ์ ๋ ํ์๋ค..
    - **๋ถ๋ถ์งํฉ ์์๋ค๋ผ๋ฆฌ ์ธ์ ํด์๋์ง ํ์ธํ๋ ๋ก์ง์ ์ค๊ณํ๊ธฐ๊ฐ ๋๋ฌด๋๋ฌด ์๊ฐ์ด ์๋ฌ๋ค. (์์ง?) ๊ฒฐ๊ตญ ํด๋๋ค! ๊ทธ๋ฅ. ํ๋ฒ์ ๋ค ์์ธ์ฒ๋ฆฌ ํ๋ ค๊ณ  ํ์ง๋ง๊ณ , vis ๋ฐฐ์ด ๋ฐ๋ก ๋๊ณ  ์๋ area ๋ฐฐ์ด์ด๋ ๋น๊ตํจ.**
    - ๊ทธ๋ฆฌ๊ณ  ์ฒ์์ ๋น์ฐํ ๋ชจ๋  ๊ตฌ์ญ๋ค์ด ๋ค ์ด์ด์ ธ์์ด์ bfs ํน์ฑ์ ๋ชจ๋  ์ ์ ์ ๋ค ์ํํ๋๋ฐ, ๋ถ๋ช ์๊ณ  ์๋๋ฐ ๊ตฌํํ  ๋๋ ์ ์ธ์ง๋ฅผ ๋ชปํ๋์ง... ์ vis ๋ฐฐ์ด์ด ์๋ฌด ์กฐ๊ฑด์ด ์์ผ๋ฉด ๋ค true๊ฐ ๋๋์ง ์ดํด๋ฅผ ๋ชปํ์๋ค ใใใใใ
- ์ข ๋ ํจ์จ์ ์ผ๋ก ์งค ์ ์์์ํ๋ฐ ์์ฝ๋ค.
    - ๊ทธ๋ฆฌ๊ณ  ์ง๊ธ ์๊ฐํด๋ณด๋ ๋ถ๋ถ์งํฉ์ ๊ตณ์ด ๋ฐ๋ก๋ฐ๋ก 2๊ฐ ๋ค ๊ตฌํ์ง ์์๋ ๋์ ๊ฒ ๊ฐ๋ค. ํ๋๋ง ๊ตฌํ๊ณ , ๋ค๋ฅธ ํ๋๋ ๊ทธ ์ฌ์งํฉ์ผ๋ก์ ์ฒ๋ฆฌํด์ค๋ค๋ฉด ๋ ํจ์จ์ ์ด์์ํ๋ฐ.. ์ข ์์ฝ๋ค.
    - ๊ทธ๋ฆฌ๊ณ  ๋ถ๋ถ์งํฉ์ ๋๋ก ๋๋๋๋ผ๋ ๊ฒฐ๊ตญ ์ ๊ฑฐ๊ตฌ a์ธ์ง b์ธ์ง ๊ตฌ๋ถ์ ํ์ ์๋ ๋ฌธ์ ์ด๋ฏ๋ก ์ด๊ฒ๋ ํ ๊ตฌ์ญ์ ๋ฏธ๋ฆฌ ํ ์์๋ฅผ ๋ฃ์ด๋๋ฉด 2^9 ์์ ํ ์ ์์์ ๊ฒ ๊ฐ๋ค.
</aside>

<br>
<br>

# **๐Resource**

| Memory | Time  | Implementation Time |
| -- |-------|---------------------|
| 15956KB | 136ms | 2 Hour 30 Minutes   |
