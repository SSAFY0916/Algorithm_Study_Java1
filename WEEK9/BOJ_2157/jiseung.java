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
        int k = Integer.parseInt(st.nextToken());
        List<int[]>[] graph = new ArrayList[n + 1]; // 각 도시에서 출발하는 비행편을 저장하는 리스트
        for (int i = 0; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            if(a < b) // 서쪽으로 가는 비행편만 저장
                graph[a].add(new int[]{b, c});
        }

        int[][] dp = new int[n + 1][m]; // 도시마다, 비행횟수마다 먹은 기내식 점수의 최대값을 저장
        for (int i = 1; i < n; i++) { // 도시는 1base이므로 1부터 시작해서 n번째 도시에 도착하면 이동할 필요가 없으니까 n-1까지 반복
            for (int j = 0; j < m-1; j++) { // m개 이하의 도시를 방문하려면 비행은 m-1번 까지만 가능
                if(i > 1 && dp[i][j] == 0) continue; // i번째 도시에 j번의 비행으로 도달할 수 없는 경우는 건너뜀
                for (int[] flight : graph[i]) { // i번째 도시에서 출발하는 모든 비행편에 대하여
                    dp[flight[0]][j+1] = Math.max(dp[flight[0]][j+1], dp[i][j] + flight[1]); // 도착도시에 j+1번의 비행으로 먹는 기내식 점수 갱신
                }
            }
        }
        bw.write(Arrays.stream(dp[n]).max().getAsInt() + "\n"); // n번째 도시에 도착하는 경우들 중에서 최대값
        bw.flush();
        bw.close();
        br.close();
    }
}