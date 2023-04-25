![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:FF658D,100:FFCB32&text=BOJ%2016118&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 16118 달빛 여우](https://www.acmicpc.net/problem/16118)

<br>
<br>

# **💻Code**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws Exception {

        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        List<Stump>[] forest = new ArrayList[N + 1];    // 기본 거리
        List<Stump>[] forestDouble = new ArrayList[N + 1];  // 2배 늘어난 거리
        List<Stump>[] forestHalf = new ArrayList[N + 1];    // 2배 줄어든 거리

        for (int i = 0; i < N + 1; i++) {
            forest[i] = new ArrayList<>();
            forestDouble[i] = new ArrayList<>();
            forestHalf[i] = new ArrayList<>();
        }

        int[] fox = new int[N + 1];    // 여우 dist
        int[][] wolf = new int[N + 1][2];  // 늑대 dist

        final int INF = 1000000000;

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());

            forest[a].add(new Stump(b, 2 * d));    // 기본 거리, 양방향
            forest[b].add(new Stump(a, 2 * d));

            forestDouble[a].add(new Stump(b, 4 * d));  // 2배 늘어난 거리, 양방향
            forestDouble[b].add(new Stump(a, 4 * d));

            forestHalf[a].add(new Stump(b, d));  // 2배 줄어든 거리, 양방향
            forestHalf[b].add(new Stump(a, d));
        }

        for (int i = 0; i < N + 1; i++) {
            // dist 초기화
            fox[i] = INF;
            wolf[i][0] = INF;
            wolf[i][1] = INF;
        }

        // 여우 dist[1]만 0으로 초기화하기
        // 늑대는 dist[1] = 0은 초기화 안함 -> 노드 1에서 출발해서 돌아온다음에 체력 0으로 출발해야하는 경우가 있음

        // 여우 다익스트라
        fox[1] = 0;   // 1번 그루터기에서 출발

        PriorityQueue<Stump> pq = new PriorityQueue<>();

        pq.offer(new Stump(1, 0));

        while (!pq.isEmpty()) {
            int dest = pq.peek().dest;
            int dist = pq.poll().dist;

            if (fox[dest] < dist)
                continue;

            for (int i = 0; i < forest[dest].size(); i++) {
                if (fox[forest[dest].get(i).dest] > fox[dest] + forest[dest].get(i).dist) {
                    fox[forest[dest].get(i).dest] = fox[dest] + forest[dest].get(i).dist;
                    pq.offer(new Stump(forest[dest].get(i).dest, fox[forest[dest].get(i).dest]));
                }
            }
        }


        // 늑대 다익스트라
        pq.offer(new Stump(1, 0, 1));   // 1번 그루터기에서 출발

        while (!pq.isEmpty()) {
            int dest = pq.peek().dest;  // 그루터기 번호
            int dist = pq.peek().dist;  // 그루터기 거리
            int status = pq.poll().status;  // 체력 상태

            if (status == 0) {   // 걸어가야함
                if (wolf[dest][1] < dist) { //뛰어서 dest까지 도착했던 비용 vs 현재 비용
                    continue;
                }

                for (int i = 0; i < forestDouble[dest].size(); i++) {
                    if (wolf[forestDouble[dest].get(i).dest][0] > dist + forestDouble[dest].get(i).dist) {
                        wolf[forestDouble[dest].get(i).dest][0] = dist + forestDouble[dest].get(i).dist;
                        pq.offer(new Stump(forest[dest].get(i).dest, wolf[forestDouble[dest].get(i).dest][0], 1));
                    }
                }
            } else {     // 뛰어갈수있음
                if (wolf[dest][0] < dist) { //걸어서 dest까지 도착했던 비용 vs 현재 비용
                    continue;
                }

                for (int i = 0; i < forestHalf[dest].size(); i++) {
                    if (wolf[forestHalf[dest].get(i).dest][1] > dist + forestHalf[dest].get(i).dist) {
                        wolf[forestHalf[dest].get(i).dest][1] = dist + forestHalf[dest].get(i).dist;
                        pq.offer(new Stump(forestHalf[dest].get(i).dest, wolf[forestHalf[dest].get(i).dest][1], 0));
                    }
                }
            }
        }
        int result = 0;
        for (int i = 2; i < N + 1; i++) {   // 1번 그루터기는 고려하지 않음
            // 여우가 먼저 도착하는 그루터기
            if (fox[i] < Math.min(wolf[i][0], wolf[i][1]))
                result++;
        }

        bw.write(Integer.toString(result));
        bw.flush();
        bw.close();
        br.close();
    }

    static class Stump implements Comparable {
        int dest, dist, status; // 그루터기 번호, 그루터기 거리, 체력 상태

        Stump(int dest, int dist) {
            this.dest = dest;
            this.dist = dist;
        }

        Stump(int dest, int dist, int status) {
            this.dest = dest;
            this.dist = dist;
            this.status = status;
        }

        @Override
        public int compareTo(Object o) {
            Stump s = (Stump) o;
            return Integer.compare(this.dist, s.dist);
        }
    }

}

```

<br>
<br>

# **🔑Description**

> 여우가 다른 그루터기에 도착하는 최소 비용은 일반적인 다익스트라를 이용하여 구현하였다.\
> 늑대는 속도를 조절하여 이동하기 때문에 dist배열을 2차원으로 만들어서 걸어왔는지/뛰어왔는지를 구별하여 최소 비용을 저장하였다.\
> 늑대는 출발할 때 무조건 뛰어서 출발하기 때문에 1에서 뛰어서 출발한 다음 1로 돌아와 걸어서 다시 출발해야만 최소비용으로 도착하는 그루터기가 존재할 수 있다.\
> 따라서 늑대의 dist[1]은 0으로 설정하지 않았다.

<br>
<br>

# **📑Related Issues**

> 시간초과 나거나 시간초과 안나면 wrong answer가 떴다.\
> 질문게시판 보다가 1에서 걸어서 출발해야하는 경우가 있다는걸 알고 늑대의 dist[1]를 0으로 할당하는 부분을 지웠다.\
> 아슬아슬하게 통과하긴 했지만 모 통과하면 된거 아닌가(당당)

<br>
<br>

# **🕛Resource**

| Memory  | Time   |
| ------- | ------ |
| 82584KB | 1000ms |
