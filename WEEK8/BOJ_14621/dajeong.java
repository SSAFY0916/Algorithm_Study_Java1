import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_14621_나만안되는연애 {
    // 최소신장트리- 크루스칼 알고리즘 이용
    // ** 간선정보에서 w-w, m-m인 경우는 애초에 후보군으로 넣지 않는다.
    static int N,M;
    static List<Edge> edgeList;
    static int[] parent;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken()); // 정점 수(학교 수)
        M = Integer.parseInt(st.nextToken()); // 간선 수(연결된 도로의 수)
        edgeList = new ArrayList<>(); // 크루스칼 알고리즘을 위한 간선정보리스트
        st = new StringTokenizer(br.readLine());
        char[] genderArr = new char[N+1]; // 1-indexed

        // 정점별 성별 저장
        for (int i = 1; i <= N; i++) {
            char gender = st.nextToken().charAt(0);
            genderArr[i] = gender;
        }

        // 간선정보리스트 입력, 애초에 같은 성별을 잇는 도로라면 간선리스트에 추가하지 않는다.
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            if (genderArr[from] == genderArr[to]) continue;
            edgeList.add(new Edge(from, to, w));
        }

        // 크루스칼 - 유니온 파인드를 사용하기 위함
        parent = new int[N+1];
        Arrays.fill(parent, -1); // 서로소 집합으로 만들어줌

        Collections.sort(edgeList); // 가중치 오름차순 정렬

        int eCnt = 0; // 최소신장트리 간선 수 (size-1일 때 종료)
        int minSum = 0; // 경로의 최소 길이

        for (Edge edge : edgeList) {
            if(union(edge.from, edge.to)) {
                minSum+=edge.w;
                if (++eCnt == N-1) break; // 최소신장트리 내 간선 수가 전체 정점수 - 1일경우 최소신장트리 완성 => 종료
            }
        }

        int pCnt = 0;
        for (int i = 1; i < N + 1; i++) {
            if (parent[i] == -1) pCnt++;
        }

        // 최소신장트리가 될 수 없는 경우 -1리턴
        // 조건1) 연결그래프가 되지 못할 때 (조상 여러개)
        // 조건2) 최소신장트리 내 간선 수가 N-1이 아닐 때
        if (eCnt != N-1 || pCnt != 1) System.out.println(-1);
        else System.out.println(minSum); // 최소신장트리가 가능할 경우 최소 길이 합 리턴
    }

    private static boolean union(int x, int y) {
        int xRoot = findSet(x);
        int yRoot = findSet(y);
        if (xRoot==yRoot) return false;
        else {
            if (xRoot<yRoot) parent[yRoot] = xRoot;
            else parent[xRoot] = yRoot;
            return true;
        }
    }

    private static int findSet(int x) {
        if (parent[x] == -1) return x;
        return parent[x] = findSet(parent[x]);
    }

    private static class Edge implements Comparable<Edge>{
        int from;
        int to;
        int w;

        public Edge(int from, int to, int w) {
            this.from = from;
            this.to = to;
            this.w = w;
        }

        @Override
        public int compareTo(Edge o) {
            return this.w-o.w;
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "from=" + from +
                    ", to=" + to +
                    ", w=" + w +
                    '}';
        }
    }
}
