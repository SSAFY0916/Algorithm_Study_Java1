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