![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%201939&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 1939_중량제한](https://www.acmicpc.net/problem/1939)

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
    
    **최소 최대만 정해주고 mid를 조정해나가면서 시뮬레이션 하듯이 그래프 탐색하는 아이디어**

    - 양방향 그래프 → 인접리스트 구현
    - 다리의 최대 중량을 max로 저장
    - 첫 인덱스, max를 기준으로 이분탐색을 통해 가능한 중량을 찾기
    - 가능한 중량을 기준으로 그래프 탐색(bfs 이용)이 가능한지 파악
        - 불가능 시 rt를 mid-1로 낮추기
        - 가능할 시 lt를 mid+1로 높이기
    - 최종적으로 가능한 최대 중량 출력

</aside>

<br>
<br>

# **📑Related Issues**

> Related Issues
<aside>

    - 처음에 다익스트라를 어찌저찌 좀 응용하면 풀 수 있지않을까 생각했다.
    - 설계하다가 아닌가? 싶어서 문제 분류를 봤는데 이분탐색+그래프 탐색 문제였다. 그래서 설계를 바꿔서 풀었다.
    - 나중에 찾아보니 내가 처음 생각한대로 다익스트라를 활용해서 문제를 푼 사람들도 있었다. 하지만 **이분탐색을 그래프 탐색에 응용한 아이디어**를 배울 수 있어서 좋았다. 다익스트라가 좀 더 익숙해지면 다익스트라로도 풀어봐야겠다.
    - 이분탐색을 할 때 배열이나 리스트를 정렬시키고, 정렬된 숫자 내에서 이분탐색을 해야한다는 `고정관념`이 있었는데 그냥 **최소 최대만 정해주고 mid를 조정해나가면서 시뮬레이션 하듯이 그래프 탐색하는 아이디어**가 신기하고 재미있었다.
    - 내가 아직 알고리즘 공부가 부족해서 신기하게 느껴지는걸까

</aside>

<br>
<br>

# **🕛Resource**

| Memory | Time  | Implementation Time |
| -- |-------|---------------------|
| 59096KB | 588ms | 2 Hour  |
