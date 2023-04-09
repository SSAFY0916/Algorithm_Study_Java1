import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_1939_중량제한 {
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
                if(!vis[nxtV] && nxtW >= weight) { // ** 부등호 주의.. 다리의 중량이 더 커야함
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
