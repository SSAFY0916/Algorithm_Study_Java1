import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_2157 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int cityNum = Integer.parseInt(st.nextToken());
        int maxVisit = Integer.parseInt(st.nextToken());
        int inputNum = Integer.parseInt(st.nextToken());

        // 비행 경로를 담은 인접 행렬
        int[][] airlines = new int[cityNum + 1][cityNum + 1];
        int[][] dp = new int[maxVisit + 1][cityNum + 1];

        // 비행 경로가 동쪽 도시에서 서쪽 도시로 이동하는 경우에만 최댓값들을 인접 행렬에 저장
        for (int i = 0; i < inputNum; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            if (from < to) {
                airlines[from][to] = Math.max(airlines[from][to], cost);
            }
        }

        // 1번 도시에서 i번 도시까지 이동하는 경로를 dp배열에 저장
        for (int i = 1; i <= cityNum; i++) {
            dp[2][i] = airlines[1][i];
        }

        /**
         * i : 현재 위치 중인 도시 (1에서 현재 i까지 이동했음을 의미)
         * j : 이동하려고자 하는 도시
         * k : 방문한 도시의 개수
         *
         * 1에서 현재 도시까지 k개의 도시를 방문해서 이동하는 경로가 존재 하고 (dp[k][i] != 0)
         * i에서 j까지 이동 경로가 존재 할 때 (airlines[i][j] != 0)
         * k + 1개의 도시를 방문하여 j까지 이동하는 경로 갱신 (dp[k + 1][j])
         */
        for (int i = 2; i <= cityNum; i++) {
            for (int j = i + 1; j <= cityNum; j++) {
                for (int k = 2; k < maxVisit; k++) {
                    if (dp[k][i] != 0 && airlines[i][j] != 0) {
                        dp[k + 1][j] = Math.max(dp[k + 1][j], dp[k][i] + airlines[i][j]);
                    }
                }
            }
        }

        int maxCost = 0;
        // N번 도시까지 이동하는 경로 중 최댓값을 출력
        for (int i = 1; i <= maxVisit; i++) {
            maxCost = Math.max(dp[i][cityNum], maxCost);
        }

        System.out.println(maxCost);
    }
}