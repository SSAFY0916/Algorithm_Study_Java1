![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2017471&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **๐Problem**

> [BOJ ๋ฌธ์ ๋ฒํธ ๋ฌธ์ ์ด๋ฆ](https://www.acmicpc.net/problem/17471)

<br>
<br>

# **Code**

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;

public class BOJ_17471 {
    // ๊ตฌ์ญ์ ๊ฐ์
    static int cityNum;
    // ๊ตฌ์ญ๋ณ ์ธ๊ตฌ ์ ์ ์ฅ ๋ฐฐ์ด
    static int[] populations;
    // ๊ตฌ์ญ๋ค์ ์ฐ๊ฒฐ ์ฌ๋ถ๋ฅผ ์ ์ฅํ๋ ๋ฐฐ์ด
    static boolean[][] connection;
    // ์ ๋์จ ํ์ธ๋๋ฅผ ์ฌ์ฉํ๊ธฐ ์ํ ๋ฐฐ์ด
    static int[] parent;
    // ๋ถ๋ถ์งํฉ์ ๊ณ์ฐํ  ๋ ์ฌ์ฉํ๋ ๋ฐฐ์ด
    static boolean[] selected;
    // ์ต์๊ฐ ๋๋ ๊ฒฐ๊ณผ๊ฐ
    static int min = 0x7fffffff;

    static class Pair {
        int city1;
        int city2;

        public Pair(int city1, int city2) {
            this.city1 = city1;
            this.city2 = city2;
        }
    }

    static int find(int x) {
        if (parent[x] == x)
            return x;
        return parent[x] = find(parent[x]);
    }

    static boolean isUnion(int x, int y) {
        if (find(x) == find(y))
            return true;
        return false;
    }

    static void merge(int x, int y) {
        x = find(x);
        y = find(y);

        if (x < y)
            parent[y] = x;
        else
            parent[x] = y;
    }

    // ๊ตฌ์ญ๋ค์ด ํ๋๋ก ์ฐ๊ฒฐ๋์ด ์๋์ง ํ์ธํ๋ ํจ์
    static boolean canMerge(List<Integer> list) {
        Set<Integer> set = new HashSet<>();
        set.add(list.get(0));
        Queue<Integer> q = new ArrayDeque<>();
        q.add(list.get(0));
        boolean[] visited = new boolean[cityNum];
        visited[list.get(0)] = true;

        // bfs๋ฅผ ์ด์ฉํ์ฌ ํ์
        while (!q.isEmpty()) {
            int tmp = q.poll();
            for (int i = 0; i < cityNum; i++) {
                // i๊ฐ ํ์ฌ ๊ตฌ์ญ๊ณผ ์ฐ๊ฒฐ๋์ด ์์ผ๋ฉฐ, list์ i๊ฐ ์๋ ๊ฒฝ์ฐ set์ ๋ฃ์ด์ค
                if (!visited[i] && connection[tmp][i] && list.contains(i)) {
                    visited[i] = true;
                    set.add(i);
                    q.add(i);
                }
            }
        }

        // list์ ํฌ๊ธฐ์, set์ ํฌ๊ธฐ๊ฐ ๊ฐ๋ค๋ฉด ๋ชจ๋  ๊ตฌ์ญ๋ค์ด ์ฐ๊ฒฐ๋์ด ์์
        if (list.size() == set.size())
            return true;
        return false;
    }

    // ๋ถ๋ถ์งํฉ์ ๊ตฌํ๋ ํจ์
    static void subSet(int cnt) {
        if (cnt == cityNum) {
            List<Integer> list1 = new LinkedList<>();
            List<Integer> list2 = new LinkedList<>();

            // ์ ํ ๋ ๊ตฌ์ญ๋ค์ list1์
            // ๊ทธ๋ ์ง ์์ ๊ตฌ์ญ๋ค์ list2์ ์ ์ฅ
            for (int i = 0; i < cityNum; i++) {
                if (selected[i])
                    list1.add(i);
                else
                    list2.add(i);
            }

            // ๋ ๊ตฌ์ญ ์ค ํ๋๋ผ๋ size๊ฐ 0์ด๋ผ๋ฉด, ๊ตฌ์ญ์ด 2๊ฐ๋ก ๋๋์ด์ง์ง ์์ ๊ฒฝ์ฐ์ด๋ฏ๋ก ์ข๋ฃ
            if (list1.size() == 0 || list2.size() == 0)
                return;

            // list1๊ณผ list2๊ฐ ๊ฐ๊ฐ ๋ชจ๋  ๊ตฌ์ญ๋ค์ด ์ฐ๊ฒฐ๋์ด ์๋ค๋ฉด
            // ๋ list์ ์ธ๊ตฌ ์๋ฅผ ๊ณ์ฐ ํ ํ์ ์ธ๊ตฌ ์์ ์ฐจ๋ฅผ ๊ตฌํจ
            if (canMerge(list1) && canMerge(list2)) {
                int list1Sum = 0;
                for (int i = 0; i < list1.size(); i++) {
                    list1Sum += populations[list1.get(i)];
                }
                int list2Sum = 0;
                for (int i = 0; i < list2.size(); i++) {
                    list2Sum += populations[list2.get(i)];
                }
                min = Math.min(min, Math.abs(list1Sum - list2Sum));
            }
            return;
        }
        // cnt๊ฐ cityNum๊ณผ ๋์ผํด ์ง ๋๊น์ง ์ฌ๊ท์ ์ผ๋ก ํธ์ถํ๋ฉฐ ๋ถ๋ถ์งํฉ์ ๊ตฌํ๊ธฐ
        selected[cnt] = true;
        subSet(cnt + 1);
        selected[cnt] = false;
        subSet(cnt + 1);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        cityNum = Integer.parseInt(br.readLine());
        populations = new int[cityNum];

        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 0; i < cityNum; i++) {
            populations[i] = Integer.parseInt(st.nextToken());
        }

        connection = new boolean[cityNum][cityNum];
        parent = new int[cityNum];
        selected = new boolean[cityNum];

        for (int i = 0; i < cityNum; i++)
            parent[i] = i;

        // ์ฒซ ์๋ ฅ์ ๋ฐ์ ๋ ๊ตฌ์ญ์ ๋๋๊ธฐ ์  ์ด ๋ช๊ฐ์ ๊ตฌ์ญ์ผ๋ก ๊ตฌ๋ณ๋์ด ์๋์ง ํ์ธํ๊ธฐ ์ํด ์ ๋์จ ํ์ธ๋๋ฅผ ์ฌ์ฉ
        for (int i = 0; i < cityNum; i++) {
            StringTokenizer tmp = new StringTokenizer(br.readLine());
            int connNum = Integer.parseInt(tmp.nextToken());
            for (int j = 0; j < connNum; j++) {
                int neighbor = Integer.parseInt(tmp.nextToken());
                connection[i][neighbor - 1] = true;
                if (!isUnion(i, neighbor - 1))
                    merge(i, neighbor - 1);
            }
        }

        // ์ ๋์จ ํ์ธ๋๊ฐ ๋๋ ์ดํ ๋ชจ๋  ๊ตฌ์ญ๋ค์ด ๋ถ๋ชจ ๊ตฌ์ญ์ ๊ฐ๋ฆฌํค๋๋ก
        // ๋ชจ๋  ๊ตฌ์ญ๋ค์ ๋ํด์ find() ํจ์ ์คํ
        for (int i = 0; i < cityNum; i++) {
            find(i);
        }

        // ๋๋์ด์ ธ ์๋ ๊ตฌ์ญ์ ๊ฐ์๋ฅผ ์ธ๊ธฐ
        int flag = 0;
        // ๊ฐ ๊ตฌ์ญ๋ค์ ์ํํ๋ฉฐ ๋ถ๋ชจ ๊ตฌ์ญ์ flag๋ฅผ ์ผ์ค
        // ์ด flag๋ฅผ ์ฌ์ฉํ๋ฉด ์ผ์ง flag์ ๊ฐ์ == ๋๋์ด์ง ๊ตฌ์ญ์ ๊ฐ์
        for (int i = 0; i < cityNum; i++) {
            flag = (flag) | 1 << parent[i];
        }

        // ๋๋์ด์ง ๊ตฌ์ญ์ ๊ฐ์๋ฅผ ๊ณ์ฐ
        int areaNum = 0;
        for (int i = 0; i < cityNum; i++) {
            if ((flag & 1 << i) == (1 << i))
                areaNum++;
        }

        // ๋๋์ด์ง ๊ตฌ์ญ์ด 2๋ณด๋ค ํฌ๋ค๋ฉด, 2๊ฐ์ ๊ตฌ์ญ์ผ๋ก ๋๋ ์ ์๋ ๊ฒฝ์ฐ
        if (areaNum > 2)
            System.out.println(-1);
        // ๋๋์ด์ง ๊ตฌ์ญ์ด 2๊ฐ๋ผ๋ฉด ์ด ๋ฐฉ๋ฒ ์ด์ธ์ ๋ค๋ฅธ ๋ฐฉ๋ฒ์ผ๋ก๋ ๊ตฌ์ญ์ ๋๋ ์ ์์
        // ์ด ๋ ๊ฐ์ ๊ตฌ์ญ์ ์ธ๊ตฌ์ฐจ๋ฅผ ์ถ๋ ฅ
        else if (areaNum == 2) {
            int[] tmp = new int[cityNum];
            for (int i = 0; i < cityNum; i++) {
                tmp[parent[i]] += populations[i];
            }
            int[] popSum = new int[2];
            for (int i = 0; i < cityNum; i++) {
                if (tmp[i] != 0 && popSum[0] == 0) {
                    popSum[0] = tmp[i];
                } else if (tmp[i] != 0) {
                    popSum[1] = tmp[i];
                }
            }
            System.out.println(Math.abs(popSum[1] - popSum[0]));
            // ๋๋์ด์ง ๊ตฌ์ญ์ด ํ๊ฐ์ธ ๊ฒฝ์ฐ
        } else {
            // ๊ตฌ์ญ์ ๋๊ฐ๋ก ๋๋๊ธฐ ์ํด ๋ถ๋ถ์งํฉ์ ๊ตฌํ๊ธฐ
            subSet(0);
            if (min != 0x7fffffff)
                System.out.println(min);
            else
                System.out.println(-1);
        }
    }

}
```

<br>
<br>

# **๐Description**

> ๊ตฌ์ญ์ ๋ถ๋ถ์งํฉ์ ์ด์ฉํ์ฌ ๋๋ ์ดํ, ๊ตฌ์ญ์ ์ธ๊ตฌ์๋ฅผ ๊ณ์ฐ, ์ฐจ๋ฅผ ๊ตฌํ๋ ๋ฐฉ์์ผ๋ก ๋ฌธ์ ๋ฅผ ํด๊ฒฐํ์์ต๋๋ค.
> ์ ๋์จ ํ์ธ๋๋ฅผ ์ด์ฉํด์ ํ์ฌ ์ ํ๋ ๊ตฌ์ญ๋ค์ด ๋ชจ๋ ์ฐ๊ฒฐ๋์ด ์๋์ง ํ์ธํ์์ต๋๋ค.

<br>
<br>

# **๐Related Issues**

> ๋ ๊ตฌ์ญ์ ์ฒ์๋ถํฐ ์ด๋ป๊ฒ ์ฐ๊ฒฐ๋์ด ์์ง ์๋ ๋ฐฉ์์ผ๋ก ์ชผ๊ฐค ์ ์์์ง ์๊ฐํ์์ต๋๋ค. ํ์ง๋ง ์ด ๋ฐฉ์์ ์ฌ์ฉํ์ง ์๊ณ , ๋ถ๋ถ์งํฉ์ผ๋ก ์ฐ์  ์ ๊ฑฐ๊ตฌ๋ฅผ ๋๋ ํ ์ ๊ฑฐ๊ตฌ ์์ ๊ตฌ์ญ๋ค์ด ๋ชจ๋ ์ฐ๊ฒฐ๋์ด ์๋์ง ํ์ธํ๋ ๋ฐฉ๋ฒ์ผ๋ก ํ์ดํ์์ต๋๋ค.

<br>
<br>

# **๐Resource**

| Memory | Time   |
| ------ | ------ |
| 15996KB | 164ms |