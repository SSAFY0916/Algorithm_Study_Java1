import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class Main {

    static int[] unions; // 유니온파인드에 사용할 각 노드의 가능한 루트노드를 저장하는 배열

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        char[] types = new char[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            types[i] = st.nextToken().charAt(0);
        }
        PriorityQueue<int[]> edges = new PriorityQueue<>(Comparator.comparingInt(o -> o[2])); // 간선의 가중치의 오름차순으로 간선을 저장할 우선순위 큐
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken()) - 1;
            int v = Integer.parseInt(st.nextToken()) - 1;
            int d = Integer.parseInt(st.nextToken());
            if(types[u] == types[v]) { // 같은 종류의 대학을 연결하는 간선이면 저장안하고 건너뜀
                continue;
            }
            edges.add(new int[]{u, v, d});
            edges.add(new int[]{v, u, d});
        }

        int count = 0; // 선택한 간선의 개수
        int answer = 0; // 선택한 간선의 가중치의 합
        unions = new int[n];
        for (int i = 0; i < n; i++) {
            unions[i] = i; // 최초에는 자기 자신이 루트노드
        }
        while(!edges.isEmpty()) { // 우선순위 큐가 빌 때까지 반복
            if(count == n-1) { // 간선을 n-1개 선택해 트리를 완성했으면 그만
                break;
            }
            int[] edge = edges.poll();
            if(isUnion(edge[0], edge[1])) { // 간선의 양끝이 이미 연결되어있다면 건너뜀
                continue;
            }
            count++;
            answer += edge[2];
            unionify(edge[0], edge[1]); // 두 노드 연결
        }
        bw.write((count < n-1 ? -1 : answer) + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    // 노드의 루트노드로 가까워지면서 루트노드를 반환하는 메소드
    static int root(int a) {
        if(unions[a] == a) return a;
        return unions[a] = root(unions[a]);
    }

    // 서로 연결되어 있는지 반환하는 메소드
    static boolean isUnion(int a, int b) {
        a = root(a);
        b = root(b);
        return a == b;
    }

    // 노드를 연결시키는 메소드
    static void unionify(int a, int b) {
        a = root(a);
        b = root(b);
        unions[Math.max(a, b)] = Math.min(a, b);
    }
}