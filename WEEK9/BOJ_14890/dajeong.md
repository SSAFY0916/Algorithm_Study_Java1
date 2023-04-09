![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2014890&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 14890_경사로](https://www.acmicpc.net/problem/14890)

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

> 구현 시간: 2hr+a
<aside>
💡 설계 아이디어

      - p1(기준점), p2(기준점을 중심으로 이동) 포인터 두기
      - p1, p2 지형 차이가 1이하일 경우에만 가능한 길인지 탐색
      - p1 포인터가 행의 끝까지 올 경우(모든 경우 탐색했는데 불가능해서 종료되지 않은 경우) 가능한 길로 카운트
      - p1, p2 지형 차이에 따라서 동작 수행
          - p1<p2
              - bigCnt++
          - p1==p2
              - 중간에 323, 343식으로 불가능한 경우가 있는지 확인
              - curCnt++
          - p1>p2
              - smallCnt++
      - 올라가는 경사로가 필요할 경우(bigCnt>0)
          - curCnt 확인 후 경사로 가능한지 확인.
          - p1, p2 이동. cnt 갱신
      - 내려가는 경사로가 가능할 경우(smallCnt==L)
          - p1, p2 이동. cnt 갱신.

</aside>

<br>
<br>

# **📑Related Issues**

> Related Issues
<aside>

    - 내려가는 경사로, 올라가는 경사로를 나눠서 생각하지 못해 처음 설계에서 조건등이 덕지덕지 붙게 되었다. ㅠ
    - 다른 사람 풀이를 보니 올라가거나 내려가는 칸을 만날 경우, 현재 칸부터 경사로 범위까지 체크하고 방문표시를 했던데 이 방식이 남에게 설명하기에도 더 좋고 이해가 잘 되는 방법인 것 같다. 
    - 그리고 처음에 행, 열을 따로따로 수행했는데 90도 회전하는 방식으로 하면 불필요한 코드를 줄일 수 있을 것 같아서 고쳤다.

</aside>

<br>
<br>

# **🕛Resource**

| Memory | Time  | Implementation Time |
| -- |-------|---------------------|
| 15216KB | 152ms | 2 Hour  |
