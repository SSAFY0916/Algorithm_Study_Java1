import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class Main {

    static BufferedReader br;
    static BufferedWriter bw;
    static StringTokenizer st;

    public static void main(String[] args) throws Exception{
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(br.readLine());
        // 주어진 간선들을 양방향으로 저장할 리스트
        List<Integer>[] edges = new ArrayList[n+1];
        for(int i=0; i<=n; i++) {
            edges[i] = new ArrayList<>();
        }
        for(int i=0; i<n-1; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            edges[u].add(v); // 간선을 양방향으로 저장
            edges[v].add(u);
        }


        Queue<Integer> q = new ArrayDeque<>();
        q.add(1); // 1번 정점을 루트로 생각하고 bfs를 돌면서 트리를 완성
        boolean[] visited = new boolean[n + 1]; // bfs에서 방문여부 저장하는 배열
        int[] depths = new int[n+1]; // 각 정점의 깊이를 저장하는 배열
        int[] parents = new int[n+1]; // 각 정점의 깊이를 저장하는 배열, 1번 정점은 0을 부모로
        List<Integer>[] children = new ArrayList[n+1]; // 각 정점의 자식을 저장하는 리스트
        for (int i = 0; i <= n; i++) {
            children[i] = new ArrayList<>();
        }
        while(!q.isEmpty()) {
            int cur = q.poll();
            visited[cur] = true;
            for (Integer child: edges[cur]) {
                if(visited[child]) { // 이미 방문한 정점이면 건너뜀
                    continue;
                }
                depths[child] = depths[cur] + 1; // 자식의 깊이는 내 깊이 + 1
                parents[child] = cur;
                children[cur].add(child); // 자식 리스트에 추가
                q.add(child);
            }
        }

        // [깊이, 정점번호]를 저장하는 우선순위큐, 깊이의 내림차순으로 정렬
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(ints -> ints[0]));

        for (int i = 1; i <= n; i++) {
            if(children[i].isEmpty()) {
                pq.add(new int[]{-depths[parents[i]], parents[i]}); // 리프 노드의 부모 노드들을 우선순위 큐에 저장
            }
        }
//        for (int i = 0; i < n+1; i++) {
//            System.out.println(children[i]);
//        }
        boolean[] earlyadaptors = new boolean[n+1]; // 얼리어답터 여부를 저장하는 배열
        int answer = 0; // 얼리어답터 수
        while(!pq.isEmpty()) {
            int depth = pq.peek()[0];
            int cur = pq.poll()[1];
//            System.out.println(cur);
            if(earlyadaptors[cur] || cur == 0) { // 이미 얼리어답터이거나 루트노드보다 부모인 트리를 벗어난 노드일때 건너뜀
                continue;
            }
            boolean flag = false; // 자식 중에 얼리어답터가 아닌 노드가 있는지 여부
            for (Integer child : children[cur]) {
                if(!earlyadaptors[child]) {
                    flag = true;
                    break;
                }
            }
            if(!flag) { // 자식들이 모두 얼리어답터이면
                if(earlyadaptors[parents[cur]]) { // 내 부모도 얼리어답터 => 난 아이디어를 받아들임
                    pq.add(new int[]{depth+2, parents[parents[cur]]}); // 내 부모도 얼리어답터라고 했으니 부모의 부모를 탐색
                }else {                             // 내 부모는 얼리어답터가 아님
                    pq.add(new int[]{depth+1, parents[cur]});           // 내 자식들은 얼리어답터니까 나까지 얼리어답터일 필요는 없고 내 부모를 얼리어답터로 할것인지 탐색
                }
                continue;
            }
            earlyadaptors[cur] = true; // 현재 노드를 얼리어답터로
            answer++;
            pq.add(new int[]{depth+2, parents[parents[cur]]}); // 내가 얼리어답터니까 나때문에 내부모는 얼리어답터일 필요는 없으니까 부모의 부모를 탐색
        }

        bw.write(answer + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

}