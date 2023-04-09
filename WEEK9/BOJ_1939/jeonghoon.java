import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_1939 {

    static int islandNum;
    static int bridgeNum;
    static List<List<Pair>> bridges = new ArrayList<>();

    static class Pair {
        int dst;
        int cost;

        public Pair(int dst, int cost) {
            this.dst = dst;
            this.cost = cost;
        }
    }

    public static boolean bfs(int start, int end, int cost) {
        Queue<Integer> q = new ArrayDeque<>();
        boolean[] visited = new boolean[islandNum + 1];
        visited[start] = true;
        q.add(start);

        while (!q.isEmpty()) {
            int curPos = q.poll();

            if (curPos == end)
                return true;

            for (int i = 0; i < bridges.get(curPos).size(); i++) {
                int nextPos = bridges.get(curPos).get(i).dst;
                int nextCost = bridges.get(curPos).get(i).cost;

                // 매개변수로 넘어온 cost보다 nextCost가 클 경우에만 BFS 탐색
                if (!visited[nextPos] && cost <= nextCost) {
                    visited[nextPos] = true;
                    q.add(nextPos);
                }
            }
        }
        return false;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        islandNum = Integer.parseInt(st.nextToken());
        bridgeNum = Integer.parseInt(st.nextToken());

        for (int i = 0; i <= islandNum; i++) {
            bridges.add(new ArrayList<>());
        };

        int maxCost = 0;

        for (int i = 0; i < bridgeNum; i++) {
            st = new StringTokenizer(br.readLine());
            int island1 = Integer.parseInt(st.nextToken());
            int island2 = Integer.parseInt(st.nextToken());
            int limitWeight = Integer.parseInt(st.nextToken());
        
            // 양방향 그래프로 입력
            bridges.get(island1).add(new Pair(island2, limitWeight));
            bridges.get(island2).add(new Pair(island1, limitWeight));
            // 이분 탐색의 Right 값에 사용하기 위하여 maxCost 값 저장
            maxCost = Math.max(maxCost, limitWeight);
        }

        st = new StringTokenizer(br.readLine());
        int startIsland = Integer.parseInt(st.nextToken());
        int endIsland = Integer.parseInt(st.nextToken());

        int low = 0, high = maxCost;

        // 이분 탐색을 통해 가능한 최대 무게 탐색
        while (low <= high) {
            int mid = (low + high) / 2;
        
            // BFS를 통해서 공장과 공장을 잇는 경로가 존재하는지를 판별
            if (bfs(startIsland, endIsland, mid))
                low = mid + 1;
            else
                high = mid - 1;
        }

        System.out.println(high);
    }
}