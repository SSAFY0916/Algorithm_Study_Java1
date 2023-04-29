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
            int d = Integer.parseInt(st.nextToken()) * 2;
            adj[a].add(new int[]{b, d});
            adj[b].add(new int[]{a, d});
        }

        int inf = 4000 * 100000 * 2;
        int[] dists_fox = new int[n + 1];
        int count = 0;
        Arrays.fill(dists_fox, inf);
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(o -> o[1]));
        pq.add(new int[]{1, 0});
        while (!pq.isEmpty()) {
            int curNode = pq.peek()[0];
            int curDist = pq.poll()[1];
            if (dists_fox[curNode] != inf) {
                continue;
            }
            dists_fox[curNode] = curDist;
            count++;
            if (count == n) {
                break;
            }
            for (int[] next : adj[curNode]) {
                if (dists_fox[next[0]] != inf) {
                    continue;
                }
                pq.add(new int[]{next[0], curDist + next[1]});
            }
        }

        int[][] dists_wolf = new int[2][n + 1];
        Arrays.fill(dists_wolf[0], inf);
        Arrays.fill(dists_wolf[1], inf);
        pq.clear();
        pq.add(new int[]{1, 0, 0});
        count = 0;
        while (!pq.isEmpty()) {
            int curNode = pq.peek()[0];
            int curDist = pq.peek()[1];
            int type = pq.poll()[2];
            if (dists_wolf[type][curNode] != inf) {
                continue;
            }
            dists_wolf[type][curNode] = curDist;
            count++;
            if (count == 2 * n) {
                break;
            }
            for (int[] next : adj[curNode]) {
                if (type == 0) {
                    if (dists_wolf[1][next[0]] != inf) {
                        continue;
                    }
                    pq.add(new int[]{next[0], curDist + next[1] / 2, 1});
                }
                else {
                    if (dists_wolf[0][next[0]] != inf) {
                        continue;
                    }
                    pq.add(new int[]{next[0], curDist + next[1] * 2, 0});
                }
            }
        }
        int answer = 0;
        for (int i = 2; i <= n; i++) {
            if (dists_fox[i] < Math.min(dists_wolf[0][i], dists_wolf[1][i])) {
                answer++;
            }
        }
        bw.write(answer + "\n");
        bw.close();
        br.close();
    }
}
