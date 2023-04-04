import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_5972 {
    private static List<List<Pair>> graphs = new ArrayList<>();
    private static int[] minCost;

    private static class Pair {
        int pos;
        int cost;

        public Pair(int pos, int cost) {
            this.pos = pos;
            this.cost = cost;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int placeNum = Integer.parseInt(st.nextToken());
        int roadNum = Integer.parseInt(st.nextToken());

        for (int i = 0; i <= placeNum; i++) {
            graphs.add(new ArrayList<>());
        }
        minCost = new int[placeNum + 1];
        Arrays.fill(minCost, 0x7fffffff);

        for (int i = 0; i < roadNum; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            graphs.get(from).add(new Pair(to, cost));
            graphs.get(to).add(new Pair(from, cost));
        }

        dijkstra(1);

        System.out.println(minCost[placeNum]);
    }

    /**
     * 다익스트라 알고리즘
     *
     * start : 시작 지점을 받아 옴
     * - minCost의 시작 지점을 0으로 설정
     * - pq에 시작 지점과 현재 지점까지의 비용이 담긴 정보를 add
     * - 현재 pq에 들어 있는 값을 추출하여, 추출한 curCost값이 현재 저장된 minCost 값보다 작을 때만 탐색 수행
     * - curPos에서 이동할 수 있는 모든 정점들에 대해서 최솟값을 갱신해 줄 수 있는지 확인
     *      - 갱신해줄 수 있다면 갱신 후 pq에 add
     *      - 갱신해 줄 수 없다면 아무런 작업도 수행하지 않음
     */
    private static void dijkstra(int start) {
        minCost[start] = 0;
        Queue<Pair> pq = new PriorityQueue<>((o1, o2) -> o1.cost - o2.cost);
        pq.add(new Pair(start, 0));

        while (!pq.isEmpty()) {
            Pair tmp = pq.poll();
            int curPos = tmp.pos;
            int curCost = tmp.cost;

            if (minCost[curPos] < curCost) continue;
            for (int i = 0; i < graphs.get(curPos).size(); i++) {
                int nextPos = graphs.get(curPos).get(i).pos;
                int nextCost = curCost + graphs.get(curPos).get(i).cost;
                if (nextCost < minCost[nextPos]) {
                    minCost[nextPos] = nextCost;
                    pq.add(new Pair(nextPos, nextCost));
                }
            }
        }
    }
}