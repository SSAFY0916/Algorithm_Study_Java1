![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2015486&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **๐Problem**

> [BOJ ๋ฌธ์ ๋ฒํธ ๋ฌธ์ ์ด๋ฆ](https://www.acmicpc.net/problem/15486)

<br>
<br>

# **Code**

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_15486 {
    static int[] dp;
    static Consulting[] consultings;

    // ์ปจ์คํ์ ๊ฑธ๋ฆฌ๋ ์๊ฐ๊ณผ ๋ฐ์ ์ ์๋ ๋น์ฉ
    static class Consulting {
        int time;
        int price;

        public Consulting(int time, int price) {
            this.time = time;
            this.price = price;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int remainDate = Integer.parseInt(br.readLine());

        dp = new int[remainDate + 1];
        consultings = new Consulting[remainDate + 1];

        // ์๋ ฅ
        for (int i = 1; i <= remainDate; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int time = Integer.parseInt(st.nextToken());
            int price = Integer.parseInt(st.nextToken());
            consultings[i] = new Consulting(time, price);
            // ์ปจ์คํ์ด ๋๋๋ ์์ ์ด ํด์ฌ ์ด์ ์ด๋ผ๋ฉด ๋๋๋ ์ง์ ์ dp๊ฐ ๊ฐฑ์ 
            if (i + time - 1 <= remainDate)
                // ํ์ฌ ์ ์ฅ๋์ด ์๋ ๊ฐ๊ณผ, ์๋ ฅ๋ฐ์ ์ปจ์คํ์ ๋น์ฉ์ ๋น๊ตํ์ฌ ๊ฐฑ์ 
                dp[i + time - 1] = Math.max(price, dp[i + time - 1]);
        }

        for (int i = 1; i <= remainDate; i++) {
            // ํ๋ฃจ ์ ๋ ๊น์ง์ dp๊ฐ๊ณผ ์ง๊ธ๊น์ง์ dp๊ฐ์ ๋น๊ตํ์ฌ dp๊ฐ ๊ฐฑ์ 
            dp[i] = Math.max(dp[i], dp[i - 1]);
            if (i + consultings[i].time - 1 <= remainDate) {
                // ํ์ฌ ์์๋๋ ์ปจ์คํ์ ์๊ฐ์ด ์ข๋ฃ๋๋ ์์ ์ ๊ฐ ๊ฐฑ์ 
                /***
                 * // ํ๋ฃจ ์ ๋ ๊น์ง์ dp๊ฐ + ํ์ฌ ์์๋๋ ์ปจ์คํ์ ๋น์ฉ๊ณผ
                 * // ํ์ฌ ์ ์ฅ๋์ด ์๋ ์ปจ์คํ์ด ์๊ฐ์ด ์ข๋ฃ๋๋ ์์ ์ dp๊ฐ์ ๋น๊ต
                 */
                dp[i + consultings[i].time - 1] = Math.max(dp[i - 1] + consultings[i].price,
                        dp[i + consultings[i].time - 1]);
            }
        }

        System.out.println(dp[remainDate]);
    }

}
```

<br>
<br>

# **๐Description**

> ํ์ฌ ์ธ๋ฑ์ค์ ์ปจ์คํ ์๊ฐ๊ณผ ๋น์ฉ์ ์ด์ฉํ์ฌ, ๋๋๋ ์ง์ ๊น์ง์ ์ต๋ ๋น์ฉ์ ๊ตฌํด ๋๊ฐ๋ ๋ฐฉ์์ผ๋ก ๋ฌธ์ ๋ฅผ ํ์์ต๋๋ค.

<br>
<br>

# **๐Related Issues**

> dp๋ฅผ ์ด์ฉํ ๋ฌธ์ ํ์ด๋ฅผ ํ  ๋ ๋งค๋ฒ ํ์ฌ ์ธ๋ฑ์ค๊น์ง์ ๊ฐ์ ๊ฐฑ์ ํ๋ ๋ฐฉ์์ผ๋ก ํ์ด๋ฅผ ํ์์ง๋ง, ์ด๋ฒ ๋ฌธ์ ์ ๊ฒฝ์ฐ ํ์ฌ ์ธ๋ฑ์ค ๊ฐ์ ์ด์ฉํ์ฌ ๋ค๋ฅธ ์ธ๋ฑ์ค์ dp๊ฐ์ ๊ฐฑ์ ํด ๋๊ฐ๋ ๋ฐฉ์์ผ๋ก ํ์์ต๋๋ค. ์ด๋ฌํ ๋ฐฉ์์ผ๋ก ํ์ด๋ฅผ ํด๋ณธ ๊ฒฝํ์ด ์์ด์ ์ ํ์์ ์ธ์ฐ๋ ๊ฒ์ด ๋ค์ ์ด๋ ค์ ์ต๋๋ค.

<br>
<br>

# **๐Resource**

| Memory | Time   |
| ------ | ------ |
| 339004KB | 812ms |