![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:FF658D,100:FFCB32&text=BOJ%202157&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 2157 여행](https://www.acmicpc.net/problem/2157)

<br>
<br>

# **💻Code**

```java
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
```

<br>
<br>

# **🔑Description**

> `dp[i][j]` 에 출발지 제외하고 i개의 도시를 지나면서 출발지 부터 j번째 도시까지 이동하는 동안 먹은 최대 기내식 점수를 저장했다.\
> 출발지가 1번도시로 고정이라 2차원 dp로 해결하였다.\
> `dp[1][j]` 의 경우는 1번 도시에서 출발해서 도착지로 바로 도착해야 하기 때문에 `airLine[1][j]`을 그대로 넣어줬다.\
> `dp[i][j]` 는 i-1까지 방문한 도시들의 경로의 기내식 dp값 + 그 도시들에서 j까지의 기내식값 을 더한 것 중에 최댓값을 넣어줬다.

<br>
<br>

# **📑Related Issues**

> 옛날에 푼 [파일합치기](https://www.acmicpc.net/problem/11066) 랑 비슷한 느낌이었다.\
> 그래서 아이디어는 금방 떠올렸는데 index 조절하느라 고생햇다....\
> 점화식 정확하게 안세우고 아이디어로만 구현하려니까 디버깅하는게 너무 어려웠다.\
> 담부턴 점화식 못 세우겠으면 A4용지에 식 빨리 나열해서 구하기....

<br>
<br>

# **🕛Resource**

| Memory  | Time  |
| ------- | ----- |
| 41184KB | 360ms |
