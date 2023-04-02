![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:FF658D,100:FFCB32&text=BOJ%201525&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 1525 퍼즐](https://www.acmicpc.net/problem/1525)

<br>
<br>

# **💻Code**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};

    public static void main(String[] args) throws Exception {

        StringBuilder start = new StringBuilder();

        for (int i = 0; i < 3; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < 3; j++) {
                start.append(st.nextToken());   // 2차원 배열 -> 1차원으로 바꾸기
            }
        }

        bw.write(Integer.toString(bfs(start.toString())));
        bw.close();
        br.close();
    }

    static int bfs(String start) {
        Queue<String> queue = new ArrayDeque<>();
        Map<String, Integer> map = new HashMap<>();

        queue.offer(start);
        map.put(start, 0);

        // start에서부터 시작해서 bfs탐색으로 그 배열에서 0이랑 교환해서 나올 수 있는 모든 경우의 수를 본다.
        // 이걸 맵에 저장해서 씀 - key:1차원배열(스트링), value:그때까지 이동한 depth

        while (!queue.isEmpty()) {
            String cur = queue.poll();
            int blank = cur.indexOf("0");   // 0위치
            int x = blank / 3;
            int y = blank % 3;

            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];
                int nn = nx * 3 + ny;

                if (nx < 0 || nx >= 3 || ny < 0 || ny >= 3)
                    continue;

                String next = cur;

                char nc = next.charAt(nn);

                // 0이랑 교환하기
                next = next.replace(nc, '9');
                next = next.replace('0', nc);
                next = next.replace('9', '0');

                if (!map.containsKey(next)) {   // 맵에 이미 있다는건 거기까지 최소횟수로 이미 도달했었다는 뜻
                    queue.offer(next);
                    map.put(next, map.get(cur) + 1);
                }

            }
        }

        return map.getOrDefault("123456780", -1);
    }
}
```

<br>
<br>

# **🔑Description**

> map에 퍼즐 모양(key)이랑 그 모양까지 가는데 걸린 이동 횟수(value)를 저장했다.\
> input에서부터 bfs로 0이랑 교환할 수 있으면 교환하고 그 모양이랑 이동 횟수를 맵에 저장하고 다시 큐에 넣었다. \
> 그래서 맨 마지막에 맵에 123456780 이 들어있지 않으면 퍼즐에서 이동이 불가능한 경우이다.

<br>
<br>

# **📑Related Issues**

> 봐도 모르겠어서 카테고리 보고 풀었다(당당)\
> 이미 최소횟수로 만들어졌던 모양은 어떻게 스루할지 고민했다.\
> 첨에는 방문배열 따로 써야하나 했는데 어차피 맵안에 최소이동횟수 같이 저장해서 방문배열 필요 없었음...! 이미 저장 되어있음 이전에 그 모양이 만들어진것임!

<br>
<br>

# **🕛Resource**

| Memory   | Time  |
| -------- | ----- |
| 119552KB | 872ms |
