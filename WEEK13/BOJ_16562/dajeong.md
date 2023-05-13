![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2016562&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 16562_친구비](https://www.acmicpc.net/problem/16562)

<br>
<br>

# **Code**

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    static List<Integer>[] adjList;
    static int[] mtf;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken()); //  학생 수
        int M = Integer.parseInt(st.nextToken()); // 친구관계 수
        int K = Integer.parseInt(st.nextToken()); // 가지고 있는 돈
        mtf = new int[N+1]; // money to friend
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            mtf[i] = Integer.parseInt(st.nextToken());
        }
        adjList = new List[N+1];
        for (int i = 0; i < N + 1; i++) {
            adjList[i] = new ArrayList<>();
        }
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            adjList[from].add(to);
            adjList[to].add(from); // 양방향
        }
        int minSum = 0;
        boolean[] vis = new boolean[N+1];
        // 집합별로 최소 친구비 더하기
        for (int i = 1; i <= N; i++) {
            if (vis[i]) continue;
            int min = bfs(i, vis);
            minSum += min;
        }
        if (K >= minSum) {
            System.out.println(minSum);
        } else {
            System.out.println("Oh no");
        }

    }

    private static int bfs(int start, boolean[] vis) {
        int tmpMin = Integer.MAX_VALUE;
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(start);
        vis[start] = true;
        while(!queue.isEmpty()) {
            int cur = queue.poll();
            if (mtf[cur] < tmpMin) tmpMin = mtf[cur];
            for(int a : adjList[cur]) {
                if (!vis[a]) queue.offer(a);
                vis[a] = true;
            }
        }
        return tmpMin;
    }

}

```

<br>
<br>

# **🔑Description**


<aside>
💡 설계 아이디어
    
    그래프탐색(bfs)로 연결되어있는 정점들을 순회하면서 최소 친구비 구하기
    주인공이 갖고 있는 돈(K)이 각 집합별 친구비의 합 이상일 때만 모든 사람들과 친구가 될 수 있다.

</aside>

<br>
<br>

# **📑Related Issues**

> Related Issues
<aside>

    기본 그래프 탐색 문제

</aside>

<br>
<br>

# **🕛Resource**

| Memory | Time  | Implementation Time |
| -- |-------|---------------------|
| 21264KB | 272ms |  |
