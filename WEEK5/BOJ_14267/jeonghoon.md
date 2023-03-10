![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2014267&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **๐Problem**

> [BOJ ๋ฌธ์ ๋ฒํธ ๋ฌธ์ ์ด๋ฆ](https://www.acmicpc.net/problem/14267)

<br>
<br>

# **Code**

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class BOJ_14267 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int peopleNum = Integer.parseInt(st.nextToken());
        int compNum = Integer.parseInt(st.nextToken());

        // ๋ถํ ์ง์์ ์ ์ฅํ๊ธฐ ์ํ 2์ฐจ์ ๊ฐ๋ณ ๋ฐฐ์ด ์ด๊ธฐํ
        List<List<Integer>> subordinate = new ArrayList<>();
        for (int i = 0; i <= peopleNum; i++) {
            subordinate.add(new ArrayList<>());
        }

        st = new StringTokenizer(br.readLine());
        // ์ฌ์ฅ์ ์์ฌ๊ฐ ์์ผ๋ฏ๋ก ํ๊ฐ์ ํ ํฐ ๋ ๋ฆฌ๊ธฐ
        st.nextToken();

        // 2๋ฒ๋ถํฐ ์๋ ฅ๋ฐ์ ์์ฌ์ ๊ฐ๋ณ๋ฐฐ์ด ์์น์ ๋ณธ์ธ์ ๋ฒํธ๋ฅผ ์ถ๊ฐ
        for (int i = 2; i <= peopleNum; i++) {
            int superior = Integer.parseInt(st.nextToken());
            subordinate.get(superior).add(i);
        }

        int[] dp = new int[peopleNum + 1];

        // ์์ฌ๋ก๋ถํฐ ๋ฐ์ ์นญ์ฐฌ์ ์ ์ฅํ๊ธฐ
        for (int i = 0; i < compNum; i++) {
            st = new StringTokenizer(br.readLine());
            int num = Integer.parseInt(st.nextToken());
            int amount = Integer.parseInt(st.nextToken());

            dp[num] += amount;
        }

        // ์์ฌ๋ก๋ถํฐ ์์ ์ ์ง์ ๋ถํ๋ค์๊ฒ ์์ ์ด ์ง๊ธ๊น์ง ๋ฐ์ ์นญ์ฐฌ์ ๋ด๋ฆฌ์นญ์ฐฌ
        for (int i = 1; i <= peopleNum; i++) {
            for (int tmp : subordinate.get(i)) {
                dp[tmp] += dp[i];
            }
        }

        for (int i = 1; i <= peopleNum; i++) {
            System.out.print(dp[i] + " ");
        }
    }

}
```

<br>
<br>

# **๐Description**

> 2์ฐจ์ ๋ฆฌ์คํธ๋ฅผ ์ด์ฉํ์ฌ ๊ฐ ์ง์๋ค ๋ฒํธ์ ๋ฆฌ์คํธ์ ์ง์ ๋ถํ๋ค์ ๋ฒํธ๋ฅผ ์ ์ฅํ์์ต๋๋ค.
> dp[i] = dp[i] + dp[i - 1] ์ ์ ํ์์ ์ฌ์ฉํ์ฌ ๋ด๊ฐ ๊ธฐ์กด์ ์์ฌ๋ก ๋ถํฐ ๋ฐ์ ์นญ์ฐฌ + ์ง๊ธ๊น์ง ์์ฌ๊ฐ ๋ฐ์ ์นญ์ฐฌ์ ๋ํด๊ฐ๋ ๋ฐฉ์์ ์ฌ์ฉํ์ฌ ํ์ดํ์์ต๋๋ค.

<br>
<br>

# **๐Related Issues**

> ์ฒ์์๋ ์์ฌ๊ฐ ๋ถํ์ ๋ฒํธ๋ฅผ ์ ์ฅํ๋ ๋ฐฉ๋ฒ์ด ์๋ ๋ถํ๊ฐ ์์ฌ์ ๋ฒํธ๋ฅผ ์ ์ฅํด ๋๋ ๋ฐฉ์์ ์ฌ์ฉํ๊ณ ์ ํ์์ผ๋, ํธํฅํธ๋ฆฌ์ ๋ชจ์์ผ๋ก ๊ด๊ณ๋๊ฐ ๊ทธ๋ ค์ง๋ ๊ฒฝ์ฐ์ ์๊ฐ์ด๊ณผ๊ฐ ๋์ฌ ๊ฒ ๊ฐ์์ ๋ค๋ฅธ ๋ฐฉ๋ฒ์ผ๋ก ํ์ดํ์์ต๋๋ค.

<br>
<br>

# **๐Resource**

| Memory | Time   |
| ------ | ------ |
| 77376KB | 1536ms |