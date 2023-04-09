![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%202157&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 2157_여행](https://www.acmicpc.net/problem/5972)

<br>
<br>

# **Code**

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int N,M;
    static ArrayList<Node>[] adjList;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken()); // 섬 갯수
        M = Integer.parseInt(st.nextToken()); // 다리 정보
        adjList = new ArrayList[N+1]; // 1-indexed
        for (int i = 1; i <= N; i++) {
            adjList[i] = new ArrayList<>();
        }

        int maxW = 0;
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            // 양방향
            adjList[from].add(new Node(to,w));
            adjList[to].add(new Node(from, w));
            if (maxW<w) maxW = w;
        }

        st = new StringTokenizer(br.readLine());
        int start = Integer.parseInt(st.nextToken());
        int end = Integer.parseInt(st.nextToken());

        int lt = 1; // 1-indexed
        int rt = maxW;

        int ans = 0;
        // 이분탐색을 통해 한번에 갈 수 있는 최대 중량 구하기(모의 트럭을 보낸다고 생각)
        while(lt <= rt) {
            int mid = (lt+rt)/2;

            // 가능한 mid(최댓값 idx)을 조정하면서 bfs 수행하며 해당 중량으로 이동할 수 있는지 확인
            if (bfs(mid, start, end)) {
                ans = mid;
                lt = mid + 1;
            } else rt = mid-1;
        }

        System.out.println(ans);

    }

    private static boolean bfs(int weight, int start, int end) {
        Queue<Integer> queue = new ArrayDeque<>();
        boolean[] vis = new boolean[N+1];
        queue.offer(start);
        vis[start] = true;

        while(!queue.isEmpty()) {
            int cur = queue.poll();
            if (cur == end) return true; // 도착지에 도달하면 종료(해당 중량으로 이동 가능)
            for(Node node : adjList[cur]) {
                int nxtV = node.to;
                int nxtW = node.w;
                if(!vis[nxtV] && nxtW >= weight) {
                    vis[nxtV] = true;
                    queue.offer(nxtV);
                }
            }
        }
        return false; // 중간에 종료되지 않으면 해당 중량으로 도착지에 도착을 못한다는 뜻이므로 false 리턴
    }

    private static class Node {
        int to;
        int w;

        public Node(int to, int w) {
            this.to = to;
            this.w = w;
        }
    }

}
```

<br>
<br>

# **🔑Description**

> 설계 시간: 1hr

> 구현 시간: 1hr+a
<aside>
💡 설계 아이디어
    
    점화식 정의)

    dp[j][m] = 도시 1부터 다음 도시 j까지 고려한 여행경로 중 기내식 점수 합의 최댓값. m개의 도시를 거쳐야 함 (1,j도시 포함)
    
    dp[nxt][m] = max(dp[cur][m-1]+score[cur][nxt], dp[cur][m])
    
    현재 도시를 알기 위해 연결된 항공로(간선) 인접 행렬 확인해야함 (밀집그래프이고, 출발 정점과 도착 정점이 구분되어 있으므로 인접행렬 사용)
    
    dp[1][1] = 0

</aside>

<br>
<br>

# **📑Related Issues**

> Related Issues
<aside>

    - 점화식은 나름 세웠다고 생각했는데 dp에 저장하는 과정 어딘가에서 잘못 꼬인 문제.. 처음 코드에서 어디가 잘못된건지 모르겠다
    - dp 공부를 더 많이 해봐야겠다

</aside>

<br>
<br>

# **🕛Resource**

| Memory | Time  | Implementation Time |
| -- |-------|---------------------|
| 59096KB | 588ms | 2 Hour  |
