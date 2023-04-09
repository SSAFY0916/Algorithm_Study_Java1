import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        List<int[]>[] edges = new ArrayList[n+1]; // 간선들을 저장하는 인접리스트
        for (int i = 0; i < n+1; i++) {
            edges[i] = new ArrayList<>();
        }
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            edges[a].add(new int[]{b, c});
            edges[b].add(new int[]{a, c});
        }
        st = new StringTokenizer(br.readLine());
        int f1 = Integer.parseInt(st.nextToken());
        int f2 = Integer.parseInt(st.nextToken());

        PriorityQueue<int[]> q = new PriorityQueue<>(Comparator.comparingInt(o -> -o[1])); // 현재 섬 번호와 가능한 최대 무게를 우선순위 큐에 최대 무게의 내림차순으로 저장
        int[] dp = new int[n+1]; // 각 섬 별 가능한 최대 무게 저장하는 배열
        q.add(new int[]{f1, Integer.MAX_VALUE});
        dp[f1] = Integer.MAX_VALUE; // 시작하는 섬은 최대값으로 저장
        while(!q.isEmpty()) {
            int cur = q.peek()[0];
            int w = q.poll()[1];
            if(cur == f2) { // 최대무게의 내림차순으로 저장했으니까 이보다 더 큰 최대 무게로 도착하는 경우는 없으므로 break
                break;
            }
            for(int[] next : edges[cur]) {
                int newWeight = Math.min(next[1], w); // 간선을 건넜을 때 최대 무게 갱신
                if(dp[next[0]] < newWeight) { // 넘어간 섬에 더 무거운 최대 무게로 온 경우가 있었다면 건너뜀
                    dp[next[0]] = newWeight; // 해당 섬의 최대 무게 갱신
                    q.add(new int[]{next[0], newWeight});
                }
            }
        }

        bw.write(dp[f2] + "\n");
        bw.flush();
        bw.close();
        br.close();
    }
}