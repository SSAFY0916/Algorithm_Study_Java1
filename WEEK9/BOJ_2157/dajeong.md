![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%202157&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 2157_여행](https://www.acmicpc.net/problem/2157)

<br>
<br>

# **Code**

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    static int N, M, K;
    static int[][] dp, adjMatrix;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken()); // 도시 수
        M = Integer.parseInt(st.nextToken()); // 여행에 필요한 최대 도시 수
        K = Integer.parseInt(st.nextToken()); // 항공로의 수 (간선)
        adjMatrix = new int[N+1][N+1]; // 인접행렬 1-indexed
        dp = new int[N + 1][N + 1]; // dp(기내식 최대합) 1-indexed

        // dp 배열 초기화
        for (int i = 1; i <= N; i++) {
            Arrays.fill(dp[i], -1);
        }


        /* 점화식 정의)
         * dp[j][m] =  도시 1부터 다음 도시 j까지 고려한 여행경로 중 기내식 점수 합의 최댓값. m개의 도시를 거쳐야 함 (1,j도시 포함)
         * dp[nxt][m] = max(dp[cur][m-1]+score[cur][nxt], dp[cur][m])
         * 현재 도시를 알기 위해 연결된 항공로(간선) 인접 행렬 확인해야함 (밀집그래프이고, 출발 정점과 도착 정점이 구분되어 있으므로 인접행렬 사용)
         * dp[1][1] = 0
         */

        // 서->동, 기내식 점수가 더 적은 경로는 애초에 저장하지 않음
        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()); // from
            int b = Integer.parseInt(st.nextToken()); // to
            int c = Integer.parseInt(st.nextToken()); // score

            if (b<a) continue;

            if (adjMatrix[a][b] < c) {
                adjMatrix[a][b] = c;
            }

        }

        dp[1][1] = 0;
        for (int m = 2; m <= M; m++) {
            for (int cur = 2; cur <= N; cur++) {
                for (int prev = 1; prev < cur; prev++) {
                    if (adjMatrix[prev][cur] > 0 && dp[prev][m-1] != -1) {
                        dp[cur][m] = Math.max(dp[cur][m], dp[prev][m-1]+adjMatrix[prev][cur]);
                    }
                }
            }
        }

/*
        for (int cur = 1; cur <= N; cur++) { // 현재 도시
            for (int m = 2; m < M; m++) { // 들린 도시
                if (dp[cur][m]==0) continue; // 갈 수 없는 곳
                for (int nxt = 2; nxt < adjMatrix[cur].length; nxt++) { // 다음 도시
                    if (adjMatrix[cur][nxt] ==0 || cur>=nxt) continue; // 현재도시->다음도시 경로가 없거나 동->서 방향이 아닌 경로일 때 넘어가기
                    dp[nxt][m+1] = dp[cur][m]+adjMatrix[cur][nxt];
                }

            }
        }
 */

        int max = 0;
        for (int i = 2; i <= M; i++) {
            if (dp[N][i] != -1) max = Math.max(max, dp[N][i]);
        }
        System.out.println(max);


    }

}
```

<br>
<br>

# **🔑Description**

> 설계 시간: 1hr

> 구현 시간: 1hr+a
<aside>
💡 설계 아이디어
    
    점화식 정의)

    dp[j][m] = 도시 1부터 다음 도시 j까지 고려한 여행경로 중 기내식 점수 합의 최댓값. m개의 도시를 거쳐야 함 (1,j도시 포함)
    
    dp[nxt][m] = max(dp[cur][m-1]+score[cur][nxt], dp[cur][m])
    
    현재 도시를 알기 위해 연결된 항공로(간선) 인접 행렬 확인해야함 (밀집그래프이고, 출발 정점과 도착 정점이 구분되어 있으므로 인접행렬 사용)
    
    dp[1][1] = 0

</aside>

<br>
<br>

# **📑Related Issues**

> Related Issues
<aside>

    - 점화식은 나름 세웠다고 생각했는데 dp에 저장하는 과정 어딘가에서 잘못 꼬인 문제.. 처음 코드에서 어디가 잘못된건지 모르겠다
    - dp 공부를 더 많이 해봐야겠다

</aside>

<br>
<br>

# **🕛Resource**

| Memory | Time  | Implementation Time |
| -- |-------|---------------------|
| 43016KB | 468ms | 2 Hour  |
