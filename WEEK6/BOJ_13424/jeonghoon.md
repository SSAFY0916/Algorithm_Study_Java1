![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2013424&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 문제번호 문제이름](https://www.acmicpc.net/problem/13424)

<br>
<br>

# **Code**

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_13424 {
    static int[][] dist;
    static final int INF = 100_000_000;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int testCase = Integer.parseInt(br.readLine());
        while (testCase --> 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int edgeNum = Integer.parseInt(st.nextToken());
            int vertexNum = Integer.parseInt(st.nextToken());

            dist = new int[edgeNum + 1][edgeNum + 1];

            for (int i = 0; i <= edgeNum; i++) {
                for (int j = 0; j <= edgeNum; j++) {
                    if (i == j) {
                        dist[i][j] = 0;
                        continue;
                    }
                    dist[i][j] = INF;
                }
            }

            for (int i = 0; i < vertexNum; i++) {
                st = new StringTokenizer(br.readLine());
                int edge1 = Integer.parseInt(st.nextToken());
                int edge2 = Integer.parseInt(st.nextToken());
                int distance = Integer.parseInt(st.nextToken());

                dist[edge1][edge2] = Math.min(dist[edge1][edge2], distance);
                dist[edge2][edge1] = Math.min(dist[edge2][edge1], distance);
            }

            /**
             * Floyd Warshall Algorithm
             * Edge에서 Edge로 이동하는 모든 경로를 구하기
             */
            for (int k = 1; k <= edgeNum; k++) {
                for (int i = 1; i <= edgeNum; i++) {
                    for (int j = 1; j <= edgeNum; j++) {
                        dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                    }
                }
            }

            int friendsNum = Integer.parseInt(br.readLine());
            int[] friends = new int[friendsNum];
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < friendsNum; i++) {
                friends[i] = Integer.parseInt(st.nextToken());
            }

            /**
             * friends에 저장된 Edge Number들에서 특정 하나의 방으로의 모든 거리를 더해 최소가 되는 지점 찾기
             */
            int minSum = INF;
            int minIdx = -1;
            for (int i = 1; i <= edgeNum; i++) {
                int sumTmp = 0;
                for (int j = 0; j < friendsNum; j++) {
                    sumTmp += dist[i][friends[j]];
                }
                if (sumTmp < minSum) {
                    minSum = sumTmp;
                    minIdx = i;
                }
            }

            sb.append(minIdx).append("\n");
        }
        System.out.println(sb.toString());
    }
}
```

<br>
<br>

# **🔑Description**

> Floyd-Warshall Algorithm을 사용하여 방에서 방까지의 거리를 모두 구해, 방들과 특정 한 방까지의 거리의 합이 최소가 되는 지점의 방 번호를 구하는 방식으로 풀었습니다.

<br>
<br>

# **📑Related Issues**

> Floyd-Warshall Algorithm을 알고 있다면 크게 어렵지 않은 문제였습니다.

<br>
<br>

# **🕛Resource**

| Memory  | Time  |
| ------- | ----- |
| 41892KB | 476ms |
