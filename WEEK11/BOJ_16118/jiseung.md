![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2016118&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> BOJ 16118 달빛 여우

# 💻**Code**

```java
import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        List<int[]>[] adj = new ArrayList[n+1];
        for (int i = 0; i < n + 1; i++) {
            adj[i] = new ArrayList<>();
        }
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken()) * 2; // 2로 나눌때 나누어떨어지게 2를 곱해서 저장
            adj[a].add(new int[]{b, d});
            adj[b].add(new int[]{a, d});
        }

        int inf = 4000 * 100000 * 2; // 노드의 개수 * 간선의 최대 길이
        int[] dists_fox = new int[n + 1]; // 여우의 각 노드별 최단 거리를 저장하는 배열
        int count = 0; // 여우가 도달한 노드의 개수
        Arrays.fill(dists_fox, inf);
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(o -> o[1])); // 거리순 정렬
        pq.add(new int[]{1, 0}); // (현재노드, 거리)
        while (!pq.isEmpty()) {
            int curNode = pq.peek()[0];
            int curDist = pq.poll()[1];
            if (dists_fox[curNode] != inf) { // 이미 방문한 노드면 건너뜀
                continue;
            }
            dists_fox[curNode] = curDist; // 최단 거리 갱신
            count++;
            if (count == n) { // 모든 노드를 방문했으면 다익스트라 종료
                break;
            }
            for (int[] next : adj[curNode]) {
                if (dists_fox[next[0]] != inf) { // 이미 방문한 노드면 건너뜀
                    continue;
                }
                pq.add(new int[]{next[0], curDist + next[1]}); // (다음노드, 현재까지의 거리와 이번 오솔길의 거리의 합)을 우선순위큐에 저장
            }
        }

        int[][] dists_wolf = new int[2][n + 1]; // 늑대의 각 노드별 최단 거리를 저장, 각 노드별로 올때 빠르게 왔는지 천천히 왔는지에 따라 2가지 거리 저장
        Arrays.fill(dists_wolf[0], inf);
        Arrays.fill(dists_wolf[1], inf);
        pq.clear(); // break해서 빠져 나온 경우를 위해 clear
        pq.add(new int[]{1, 0, 0});// (현재 노드, 거리, 빠르게 왔는지 천천히 왔는지)
        count = 0; // 늑대가 도달한 노드의 개수
        while (!pq.isEmpty()) {
            int curNode = pq.peek()[0];
            int curDist = pq.peek()[1];
            int type = pq.poll()[2];
            if (dists_wolf[type][curNode] != inf) { // 현재 방법으로 현재 노드에 방문했었으면 건너뜀
                continue;
            }
            dists_wolf[type][curNode] = curDist; // 최단 거리 갱신
            count++;
            if (count == 2 * n) { // 모든 방법으로 모든 노드에 방문했으면 다익스트라 종료
                break;
            }
            for (int[] next : adj[curNode]) {
                if (type == 0) { // 현재 노드엔 천천히 왔고 이번 오솔길을 빠르게 감
                    if (dists_wolf[1][next[0]] != inf) {
                        continue;
                    }
                    pq.add(new int[]{next[0], curDist + next[1] / 2, 1}); // 현재까지의 거리와 이번 오솔길의 거리/2 의 합
                }
                else { // 현재 노드에 빠르게 왔고 이번 오솔길은 천천히 감
                    if (dists_wolf[0][next[0]] != inf) {
                        continue;
                    }
                    pq.add(new int[]{next[0], curDist + next[1] * 2, 0}); // 현재까지의 거리와 이번 오솔길의 거리*2 의 합
                }
            }
        }
        int answer = 0;
        for (int i = 2; i <= n; i++) { // 각 노드별로 여우의 거리와 늑대의 두 가지 거리를 비교
            if (dists_fox[i] < Math.min(dists_wolf[0][i], dists_wolf[1][i])) {
                answer++;
            }
        }
        bw.write(answer + "\n");
        bw.close();
        br.close();
    }
}
```

# **🔑Description**

> 여우는 일반적인 다익스트라, 늑대는 두 가지의 방법을 각각 저장하면서 다익스트라 돌렸다.\
> 나누기 2를 해도 나누어떨어지도록 애초에 거리를 2배해서 저장했다.\
> 다음 오솔길을 빠르게 가서 늑대가 더 빠르게 도착할 수도 있어서 현재 노드가 가능성이 없어도 멈추지 않고 모든 경우를 구했다.\
> 0과 1을 번갈아가면서 우선순위 큐에 저장하고 0일 때와 1일 때의 각 노드별 최단 거리를 따로 저장했다.\

# **📑Related Issues**

> 입력이 m, 여우는 mlogm, 늑대는 하나의 오솔길이 거리가 절반인 오솔길, 두 배의 오솔길로 되었다고 생각하고 2mlog2m해서 총 mlogm이라고 생각했는데\
> 시간 초과가 났었다\
> 기존에 우선순위큐에 거리는 나누기 2때문에 double로 하고 노드번호는 int라서 클래스를 만들어 사용했는데 그래서 시간초과가 났는지 모르겠다.

# **🕛Resource**

| Memory    | Time    |
| --------- | ------- |
| 68944`KB` | 932`ms` |
