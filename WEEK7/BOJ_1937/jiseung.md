![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%201937&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> BOJ 1937 욕심쟁이 판다

# 💻**Code**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {

    static int n;
    static int[][] nums, dp;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, -1, 0, 1};
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        n = Integer.parseInt(br.readLine());
        nums = new int[n][n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                nums[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        // 현재 위치에서 갈 수 있는 최대 거리 저장하는 배열
        dp = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dfs(i, j);
            }
        }

        int answer = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                answer = Math.max(answer, dp[i][j]);
            }
        }
        bw.write(answer + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    // dfs로 격자를 탐색하면서 현재 칸에서 갈 수 있는 최대 거리를 갱신
    static int dfs(int r, int c) {
        if (dp[r][c] > 0) { // 이미 방문한 칸이면 구해놓은 값 리턴
            return dp[r][c];
        }
        
        int maxDist = 0; // 이동해서 갈 수 있는 최대 거리를 저장
        for (int k = 0; k < 4; k++) {
            int newr = r + dr[k];
            int newc = c + dc[k];
            if (newr < 0 || newr >= n || newc < 0 || newc >= n) {
                continue;
            }
            if (nums[newr][newc] < nums[r][c]) {
                maxDist = Math.max(maxDist, dfs(newr, newc)); // 인접한 칸으로 이동했을 때 최대 거리와 비교
            }
        }
        dp[r][c] = Math.max(dp[r][c], maxDist+1); // 이동가능한 최대 거리 갱신
        return maxDist+1;
    }
}
```

# **🔑Description**

> 모든 칸에서 인접한 칸들을 탐색하면서 이동할 수 있는 최대 거리를 저장하는 방식으로 해결하려 했었다.\
> 그래프 탐색과 동적 계획법을 사용하는 것은 맞는 것 같았는데 입력 데이터에 따라 탐색했던 칸을 다시 탐색하는 일이 빈번하게 일어나서 계속 시간초과가 났었다.\
> 오래 잡고 풀면서 생각했던 것을 풀이로 제대로 구현할 수 있었다.\
> 다시 푼 흔적때문에 아직 잘못된 부분이 있었다.

# **📑Related Issues**

> 잘못된 풀이로 대충 맞으려고 풀이를 비틀기만 하는 요행을 부려서 결국 잘못된 풀이에 매몰되어 빠져나오는데 오래 걸렸다.\

# **🕛Resource**

| Memory    | Time    |
| --------- | ------- |
| 37336`KB` | 480`ms` |
