package daily.y_2023.bj_14621;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

class Edge {
    int start;
    int end;
    int weight;

    Edge(int start, int end, int weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }
}

public class BJ_14621 {
    static int[] parent;
    static ArrayList<Edge> edgeList;

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        char[] university = new char[N + 1]; // 남초대학인지 여초대학인지 구분.

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            university[i] = st.nextToken().charAt(0);
        }

        edgeList = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());

            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());

            edgeList.add(new Edge(u, v, d));
        }

        parent = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            parent[i] = i;
        }

        // 사심 경로를 기준으로 오름차순 정렬.
        Collections.sort(edgeList, (e1, e2) -> e1.weight - e2.weight);

        int cnt = 0, ans = 0;

        // 크루스칼 알고리즘 수행.
        for (int i = 0; i < edgeList.size(); i++) {
            Edge edge = edgeList.get(i);

            if (find(edge.start) != find(edge.end)) {
                if (university[edge.start] != university[edge.end]) {
                    cnt++;
                    ans += edge.weight;

                    union(edge.start, edge.end);
                }
            }
        }

        // N - 1개의 경로가 없으면 최소 신장 트리를 만들 수 없다는 의미임.
        bw.write((cnt != N - 1 ? -1 : ans) + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    public static int find(int x) {
        if (x == parent[x]) {
            return x;
        }

        return parent[x] = find(parent[x]);
    }

    public static void union(int x, int y) {
        x = find(x);
        y = find(y);

        if (x != y) {
            parent[y] = x;
        }
    }

}