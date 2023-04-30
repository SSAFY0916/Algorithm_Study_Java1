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
