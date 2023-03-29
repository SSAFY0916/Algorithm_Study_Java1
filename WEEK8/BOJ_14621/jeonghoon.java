import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_14621 {

    // prim 알고리즘에 사용할 graph
    static List<List<Pair>> graph = new ArrayList<>();
    // selected로 연결 여부 확인
    static boolean[] selected;
    // schoolType의 true/false값에 따라 남초학교, 여초학교 구분
    static boolean[] schoolType;
    static int schoolNum;

    static class Pair {
        int first;
        int second;

        public Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }
    }

    static int prim() {
        int result = 0;
        // 가중치 기준으로 오름차순 정렬되는 PriorityQueue 선언
        Queue<Pair> dist = new PriorityQueue<>((o1, o2) -> o1.second - o2.second);
        // 초기 한개의 노드를 우선 Queue에 add
        dist.add(new Pair(1, 0));
        // 모든 노드가 연결되어야 하므로 schoolNum 만큼 반복
        for (int i = 1; i <= schoolNum; i++) {
            int cur = -1;
            int minDist = Integer.MAX_VALUE;
            while(!dist.isEmpty()) {
                Pair tmp = dist.poll();
                // Queue에서 꺼낸 값이 방문하지 않은 노드라면
                cur = tmp.first;
                // PQ에 의해 정렬되어 있으므로 현재 poll된 가중치가 그래프와 연결할 수 있는 가장 작은 노드 + 가중치
                if (!selected[cur]) {
                    minDist = tmp.second;
                    break;
                }
            }
            // queue가 전부 빌 때까지 연결할 수 있는 노드가 발견되지 않은 경우
            if (minDist == Integer.MAX_VALUE) return -1;
            result += minDist;
            selected[cur] = true;
            // 새롭게 연결된 노드의 간선들을 모두 PQ에 add
            for (int j = 0; j < graph.get(cur).size(); j++) {
                dist.add(graph.get(cur).get(j));
            }
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        schoolNum = Integer.parseInt(st.nextToken());
        int roadNum = Integer.parseInt(st.nextToken());
        schoolType = new boolean[schoolNum + 1];
        selected = new boolean[schoolNum + 1];
        for (int i = 0; i < schoolNum + 1; i++) {
            graph.add(new ArrayList<>());
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= schoolNum; i++) {
            if (st.nextToken().equals("M")) {
                schoolType[i] = true;
            } else {
                schoolType[i] = false;
            }
        }

        for (int i = 0; i < roadNum; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int length = Integer.parseInt(st.nextToken());
            if (schoolType[from] == schoolType[to])
                continue;
            graph.get(from).add(new Pair(to, length));
            graph.get(to).add(new Pair(from, length));
        }

        System.out.println(prim());
    }
}