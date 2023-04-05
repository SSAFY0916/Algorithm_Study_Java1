![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:FF658D,100:FFCB32&text=BOJ%2013424&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 13424 비밀 모임](https://www.acmicpc.net/problem/13424)

<br>
<br>

# **💻Code**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws Exception {

        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        while (T-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine(), " ");

            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            int K = 0;

            int[][] distance = new int[N + 1][N + 1];

            for (int i = 0; i < N + 1; i++) {
                for (int j = 0; j < N + 1; j++) {
                    distance[i][j] = 1000000;   // 초기 거리 최대로 초기화
                }
                distance[i][i] = 0;
            }

            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());

                // 입력 받은 거리 저장
                distance[a][b] = c;
                distance[b][a] = c;
            }

            // 플로이드-와샬로 모든 지점에 대해 다른 모든 지점으로 가는 최단거리 구하기
            for (int k = 1; k < N + 1; k++) {
                for (int i = 1; i < N + 1; i++) {
                    for (int j = 1; j < N + 1; j++) {
                        distance[i][j] = Math.min(distance[i][j], distance[i][k] + distance[k][j]);
                    }
                }
            }

            K = Integer.parseInt(br.readLine());
            int[] friends = new int[K];

            int result = Integer.MAX_VALUE;
            int resultIdx = -1;

            // 모임에 참여하는 친구들 입력받기
            st = new StringTokenizer(br.readLine(), " ");
            for (int i = 0; i < K; i++) {
                friends[i] = Integer.parseInt(st.nextToken());
            }

            for (int i = 1; i < N + 1; i++) {
                int total = 0;
                //모든 지점에서 모임에 참여하는 친구들까지의 최단경로 합산
                for (int j = 0; j < K; j++) {
                    total += distance[i][friends[j]];
                }

                // 그 중 최소가 나오는 지점 저장하기
                if (result > total) {
                    result = total;
                    resultIdx = i;
                }
            }

            sb.append(Integer.toString(resultIdx)).append("\n");

        }

        bw.write(sb.toString());
        bw.close();

    }
}


```

<br>
<br>

# **🔑Description**

> 플로이드-와샬로 모든지점에서 다른 모든 지점으로 가는 최단경로를 저장했다.\
> 그 다음엔 모든 지점에서 친구들이 있는 방으로의 최단경로의 합을 구했고 이 중 최소가 나오는 지점을 출력했다.
> <br>
> <br>

# **📑Related Issues**

> 문제랑 입력값 보고 헉 이거 플로이드 와샬이다 하고 신나게 풀었는데 내자마자 틀렸다...\
> 지승오빠가 인덱스 확인해보라고 해서 봤는데... 보니까 인덱스 잘못 썼다ㅎㅎ\
> 중간지점인 k는 맨 겉에다가 반복문 둘러줘야함...

<br>
<br>

# **🕛Resource**

| Memory  | Time  |
| ------- | ----- |
| 27996KB | 320ms |
