import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        List<Integer>[] adj = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }
        for (int i = 0; i < n - 1; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            adj[a].add(b);
            adj[b].add(a);
        }
        
        long g = 0L;
        Queue<int[]> q = new ArrayDeque<>();
        q.add(new int[]{0, 1});
        boolean[] visited = new boolean[n];
        int[] parents = new int[n];
        List<Integer>[] childrens = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            childrens[i] = new ArrayList<>();
        }
        while (!q.isEmpty()) {
            int cur = q.peek()[0];
            int dist = q.poll()[1];
            visited[cur] = true;
            int children = adj[cur].size();
            if (children >= 3) {
                g += (long) children * (children-1) * (children-2) / 6;
            }
            for (int next : adj[cur]) {
                if (visited[next]) {
                    continue;
                }
                childrens[cur].add(next);
                parents[next] = cur;
                q.add(new int[]{next, dist + 1});
            }
        }

        long d = 0L;
        q.add(new int[]{0, 1});
        Arrays.fill(visited, false);
        while (!q.isEmpty()) {
            int cur = q.peek()[0];
            int dist = q.poll()[1];
            if (dist >= 3) {
                d += childrens[parents[parents[cur]]].size() - 1;
            }
            if (dist >= 4) {
                d++;
            }
            visited[cur] = true;
            for (int next : adj[cur]) {
                if (visited[next]) {
                    continue;
                }
                q.add(new int[]{next, dist + 1});
            }
        }
        
        bw.write(d==g*3 ? "DUDUDUNGA" : (d>g*3 ? "D" : "G"));
        bw.flush();
        bw.close();
        br.close();
    }
}