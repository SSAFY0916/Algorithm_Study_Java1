import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws Exception {

        StringTokenizer st = new StringTokenizer(br.readLine());

        List<ArrayList<Node>> nodeList = new ArrayList<>(); // 인접 노드 리스트
        PriorityQueue<Node> pq = new PriorityQueue<>(); // 노드 비용 우선으로 뽑을 PQ

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int[] weight = new int[N + 1];

        final int INF = 10_0000_0000;

        for (int i = 0; i < N + 1; i++) {
            nodeList.add(new ArrayList<>());
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            // 양방향이라서 양 쪽 다 넣어줌
            nodeList.get(a).add(new Node(b, c));
            nodeList.get(b).add(new Node(a, c));
        }
        for (int i = 0; i < N + 1; i++) {
            weight[i] = INF;  // 최소비용 초기화
        }

        // 다익스트라
        weight[1] = 0;  // 1번노드에서 출발
        pq.offer(new Node(1, 0));

        while (!pq.isEmpty()){
            int n = pq.peek().num;
            int w = pq.poll().weight;

            if(weight[n] < w)   // 뽑은것보다 최소비용으로 갈 수 있는 경우 처리 안함
                continue;

            for (int i = 0; i < nodeList.get(n).size(); i++) {
                Node node = nodeList.get(n).get(i);
                if(weight[n] + node.weight < weight[node.num]) { // n번노드 거쳐서 다음 노드로 가는게 더 비용이 더 적게 드는 경우
                    weight[node.num] = weight[n] + node.weight; // 최소비용 업데이트
                    pq.offer(new Node(node.num, weight[node.num]));
                }
            }
        }

        bw.write(Integer.toString(weight[N]));  // N번 노드까지 가는 최소비용

        bw.flush();
        bw.close();
        br.close();
    }

    static class Node implements Comparable<Node>{
        int num, weight;

        Node(int num, int weight){
            this.num = num;
            this.weight = weight;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.weight, o.weight);
        }
    }
}