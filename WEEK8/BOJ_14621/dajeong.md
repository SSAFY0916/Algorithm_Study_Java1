![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2014621&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 14621 나만안되는연애](https://www.acmicpc.net/problem/14621)

<br>
<br>

# **Code**

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
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
        for (int i = 1; i <= N; i++) {
            char gender = st.nextToken().charAt(0);
            genderArr[i] = gender;
        }
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            if (genderArr[from] == genderArr[to]) continue; // 같은 성별을 잇는 도로라면 넘어가기
            edgeList.add(new Edge(from, to, w));
        }
        parent = new int[N+1];
        Arrays.fill(parent, -1); // 서로소 집합으로 만들어줌

        Collections.sort(edgeList); // 가중치 오름차순 정렬
        int eCnt = 0; // 최소신장트리 간선 수 (size-1일 때 종료)
        int minSum = 0; // 경로의 최소 길이


        for (Edge edge : edgeList) {
            if(union(edge.from, edge.to)) {
                minSum+=edge.w;
                if (++eCnt == N-1) break;
            }
        }

        int pCnt = 0;
        for (int i = 1; i < N + 1; i++) {
            if (parent[i] == -1) pCnt++;
        }

        if (eCnt != N-1 || pCnt != 1) System.out.println(-1);
        else System.out.println(minSum);

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
```

<br>
<br>

# **🔑Description**

> 설계 시간: 15min + a

> 구현 시간: 40min
<aside>
💡 설계 아이디어

    - 최소신장트리 - 크루스칼 이용
    - 간선리스트에 성별이 다른 학교를 잇는 간선만 넣기

</aside>

<br>
<br>

# **📑Related Issues**

> Related Issues
<aside>

    * 반례를 직접 만들어서 문제를 내 스스로 해결해서 매우 뿌듯했다.
    * 처음에 최소신장트리 문제라는 것을 직관적으로 느끼긴했는데, 문제 내에서 최단경로라고 적혀있어서 순간적으로 최단경로 문제인지 헷갈렸다. 하지만 이참에 이 두개의 알고리즘 차이를 나름 확실하게 구분할 수 있게 되었다.
    
    실수
    1) union find에서 findSet할 때 parent[x] = findSet(parent[x]);을 하지 않고 findSet(x)를 했었다.
    2) 최소신장트리가 만들어질 수 없는 조건 체크를 하지 못했었다. ⇒ -1 리턴
        2-1) (정점 수-1)만큼의 최소신장트리 내 간선 수가 만들어지지 않았을 경우
            ⇒ edgeList를 애초에 가능한 edge만 넣도록 응용해서 풀었는데(성별 다를 경우), 전체 정점들의 수 -1을 하지 않고 edgeList.size-1이 안될 경우 최소신장트리가 되지 않는다고 착각했다. 정점들이 모두 연결되어야하므로 정점수-1을 생각하는걸 잊지 말자.
        
        2-2) 최소신장트리 내 간선들이 모두 연결되어있지 않을 경우(parent 수가 여러개)
</aside>

<br>
<br>

# **🕛Resource**

| Memory | Time  | Implementation Time |
| -- |-------|---------------------|
| 20308KB | 236ms | 1 Hour  |
