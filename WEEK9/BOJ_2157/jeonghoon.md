![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:FF658D,100:FFCB32&text=BOJ%202157&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 2157 여행](https://www.acmicpc.net/problem/2157)

<br>
<br>

# **💻Code**

```java
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
```

<br>
<br>

# **🔑Description**

> 도시의 인덱스가 작아지는 경우는 존재하지 않기 때문에 DP를 이용해서 현재 위치하고 있는 도시에서 다른 도시로 이동할 수 있는 경우를 방문한 도시의 수, 이동할 도시의 번호의 정보를 인덱스로 사용하는 DP에 기내식 점수를 담아가며 문제를 풀이하였습니다.

<br>
<br>

# **📑Related Issues**

> 초기에 1부터 시작해서 BFS를 통해 탐색해서 풀이를 하려고 하였으나, Queue에 같은 데이터들이 무수히 쌓이면서 메모리 초과가 발생했습니다.
> 이후 BFS로 풀이할 수 없었던 원인을 분석해보고, 이를 해결하기 위해 방문 횟수, 도시로 이루어진 2차원 DP 배열을 만들어서 해당 지점의 최댓값을 갱신해 나가는 방법으로 풀이하였습니다.

<br>
<br>

# **🕛Resource**

| Memory   | Time   |
| -------- | ------ |
| 43192KB  | 420ms  |
