![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:FF658D,100:FFCB32&text=BOJ%2014621&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 14621 나만 안되는 연애](https://www.acmicpc.net/problem/14621)

<br>
<br>

# **💻Code**

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_14621 {

    static List<List<Pair>> graph = new ArrayList<>();
    static boolean[] selected;
    static boolean[] schoolType;
    static int schoolNum;

    static class Pair {
        int first;
        int second;

        public Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }
    }

    static int prim() {
        int result = 0;
        Queue<Pair> dist = new PriorityQueue<>((o1, o2) -> o1.second - o2.second);
        dist.add(new Pair(1, 0));
        for (int i = 1; i <= schoolNum; i++) {
            int cur = -1;
            int minDist = Integer.MAX_VALUE;
            while(!dist.isEmpty()) {
                Pair tmp = dist.poll();
                cur = tmp.first;
                if (!selected[cur]) {
                    minDist = tmp.second;
                    break;
                }
            }
            if (minDist == Integer.MAX_VALUE) return -1;
            result += minDist;
            selected[cur] = true;
            for (int j = 0; j < graph.get(cur).size(); j++) {
                dist.add(graph.get(cur).get(j));
            }
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        schoolNum = Integer.parseInt(st.nextToken());
        int roadNum = Integer.parseInt(st.nextToken());
        schoolType = new boolean[schoolNum + 1];
        selected = new boolean[schoolNum + 1];
        for (int i = 0; i < schoolNum + 1; i++) {
            graph.add(new ArrayList<>());
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= schoolNum; i++) {
            if (st.nextToken().equals("M")) {
                schoolType[i] = true;
            } else {
                schoolType[i] = false;
            }
        }

        for (int i = 0; i < roadNum; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int length = Integer.parseInt(st.nextToken());
            if (schoolType[from] == schoolType[to])
                continue;
            graph.get(from).add(new Pair(to, length));
            graph.get(to).add(new Pair(from, length));
        }

        System.out.println(prim());
    }
}
```

<br>
<br>

# **🔑Description**

> Prim 알고리즘을 이용하여 최소 신장트리를 구하는 방식으로 문제를 풀이했습니다.

<br>
<br>

# **📑Related Issues**

> 문제가 최소 신장 트리를 구하는 문제라는 것만 파악한다면 쉽게 풀 수 있는 문제인것 같습니다.

<br>
<br>

# **🕛Resource**

| Memory   | Time   |
| -------- | ------ |
| 20924KB  | 240ms  |
