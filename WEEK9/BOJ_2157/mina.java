import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws  Exception{
        StringTokenizer st = new StringTokenizer(br.readLine()," ");
        int N = Integer.parseInt(st.nextToken());   // 도시 개수
        int M = Integer.parseInt(st.nextToken());   //
        int K = Integer.parseInt(st.nextToken());
        int max = 0;

        int[][] airline = new int[N + 1][N + 1];
        int[][] dp = new int[M][N+1];
        //dp[i, j] = (출발지 제외) i개의 도시를 지나면서 1번 to j번 도시까지 이동하는 동안 먹은 최대 기내식 점수 저장

        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine()," ");

            int a = Integer.parseInt(st.nextToken());   // 출발 도시
            int b = Integer.parseInt(st.nextToken());   // 도착 도시
            int c = Integer.parseInt(st.nextToken());   // 기내식 점수

            airline[a][b] = Math.max(airline[a][b], c); // 같은 구간에 여러개의 기내식이 들어올 수 있으므로 큰 것만 저장
        }

        dp[1] = Arrays.copyOf(airline[1], N+1);   // (도착지 포함) 1개 도시를 지나 도착하는 경우

        for (int i = 2; i < M; i++) {   // 최대 M-1개의 도시를 지남
            for (int j = i + 1; j <= N; j++) {  // N번 도시까지 도착 해야함
                for (int k = i; k <= j; k++) {  // i번째로 도착할 도시 선택
                    if(airline[k][j] == 0 || dp[i-1][k]==0) // 항로가 연결되지 않은 경우
                        continue;

                    dp[i][j] = Math.max(dp[i-1][k]+airline[k][j],dp[i][j]);
                }
            }
        }

        for (int i = 1; i < M; i++) {  // M개 이하의 도시를 지나면서 최댓값 찾기
            max = Math.max(dp[i][N], max);
        }

        bw.write(Integer.toString(max));
        bw.flush();
        bw.close();
        br.close();
    }
}