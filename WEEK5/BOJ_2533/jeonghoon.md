![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%202533&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **๐Problem**

> [BOJ ๋ฌธ์ ๋ฒํธ ๋ฌธ์ ์ด๋ฆ](https://www.acmicpc.net/problem/2533)

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

public class BOJ_2533 {
    // ๊ทธ๋ํ ์ ๋ณด ์ธ์ ๋ฆฌ์คํธ๋ก ์ ์ฅ
    static List<List<Integer>> node = new ArrayList<>();
    // ์ผ๋ฆฌ์ด๋ตํฐ์ธ์ง ์ ์ฅ
    static boolean[] earlyAdapter;
    // ํธ๋ฆฌ ํ์์ ์ฌ์ฉํ  visited ๋ฐฐ์ด
    static boolean[] visited;

    public static boolean findEarlyAdapter(int num) {
        // visited ๋ฐฐ์ด์ ์ฌ์ฉํ์ฌ graph๋ฅผ tree์ฒ๋ผ ์ฌ์ฉํ๊ฒ๋ ํจ
        visited[num] = true;

        // flag๊ฐ true์ด๋ฉด ์์ ๋ธ๋๋ค์ด ๋ชจ๋ ์ผ๋ฆฌ ์ด๋ตํฐ
        // ์์ ๋ธ๋๊ฐ ์๋ ๊ฒฝ์ฐ๋ ํฌํจ
        boolean flag = true;
        for (int i = 0; i < node.get(num).size(); i++) {
            // ๋ฐฉ๋ฌธํ node์ด๋ฉด ํ์์ ์งํํ์ง ์์
            if (visited[node.get(num).get(i)])
                continue;
            // ์ธ์  ๋ธ๋์ ๋ํด์ ์ผ๋ฆฌ์ด๋ตํฐ ์ฌ๋ถ ํ์ธ
            // ์์ ๋ธ๋์ค ํ๋๋ผ๋ ์ผ๋ฆฌ ์ด๋ตํฐ๊ฐ ์๋๋ผ๋ฉด num ๋ธ๋๋ ์ผ๋ฆฌ์ด๋ตํฐ ์ด์ด์ผ ํจ
            if (!findEarlyAdapter(node.get(num).get(i))) {
                flag = false;
            }
            visited[node.get(num).get(i)] = true;
        }

        // flag ๊ฐ์ ๋ฐ๋ผ์ earlyAdapter ์ฌ๋ถ๋ฅผ ์ค์ ํ๊ณ  ํจ์ ๋ฆฌํด
        if (!flag) {
            return earlyAdapter[num] = true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int edgeNum = Integer.parseInt(br.readLine());
        earlyAdapter = new boolean[edgeNum + 1];
        visited = new boolean[edgeNum + 1];

        for (int i = 0; i <= edgeNum; i++) {
            node.add(new ArrayList<>());
        }

        for (int i = 0; i < edgeNum - 1; i++) {
            // ์๋ฐฉํฅ ๊ทธ๋ํ๋ก ์ฐ์  ์ธ์ ๋ฆฌ์คํธ์ ์ ์ฅ
            StringTokenizer st = new StringTokenizer(br.readLine());
            int parent = Integer.parseInt(st.nextToken());
            int child = Integer.parseInt(st.nextToken());
            node.get(parent).add(child);
            node.get(child).add(parent);
        }

        findEarlyAdapter(1);

        int cnt = 0;
        for (int i = 1; i <= edgeNum; i++) {
            if (earlyAdapter[i])
                cnt++;
        }

        System.out.println(cnt);
    }
}
```

<br>
<br>

# **๐Description**

> ํธ๋ฆฌ๋ฅผ ๋ฆฌํ๋ธ๋๊น์ง ํ์ํ์ฌ ๋ฆฌํ๋ธ๋๋ค์ ์ผ๋ฆฌ์ด๋ตํฐ๊ฐ ์๋ ๊ฒ์ผ๋ก ์ค์ 
> ๋ธ๋์ ์์ ๋ธ๋ ์ค ํ๋๋ผ๋ ์ผ๋ฆฌ์ด๋ตํฐ๊ฐ ์๋๋ผ๋ฉด ํ์ฌ ๋ธ๋๋ ์ผ๋ฆฌ์ด๋ตํฐ์ด๋๋ก ํ์ด

<br>
<br>

# **๐Related Issues**

> ํธ๋ฆฌ๋ฅผ ๋ฆฌํ ๋ธ๋์์๋ถํฐ ์๋ก ์ฌ๋ผ๊ฐ๋ฉด์ ์ผ๋ฆฌ์ด๋ตํฐ ์ฌ๋ถ๋ฅผ ์ค์ ํ๋ ๊ณผ์ ์ด ์ด๋ ค์ ์ต๋๋ค.

<br>
<br>

# **๐Resource**

| Memory   | Time   |
| -------- | ------ |
| 404164KB | 1884ms |
