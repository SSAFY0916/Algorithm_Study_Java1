![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2017471&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 17471 게리맨더링](https://www.acmicpc.net/problem/17471)

<br>
<br>

# **Code**

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    static int N;
    static int[] peopleCnt;
    static int[][] map;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine()); // 1~N -> 0~N-1
        peopleCnt = new int[N];
        map = new int[N][N]; // N 크기 작아서 편의상 인접행렬로 구현함
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            peopleCnt[i] = Integer.parseInt(st.nextToken());
        }
        for (int n = 0; n < N; n++) {
            st = new StringTokenizer(br.readLine());
            int m = Integer.parseInt(st.nextToken());
            for (int i = 0; i < m; i++) {
                int e = Integer.parseInt(st.nextToken()) - 1; // 정점 0부터 시작하게 맞춰줌
                map[n][e] = 1; // 무향그래프이기때문에 from, to 둘다 넣기
                map[e][n] = 1;
            }
        }

        boolean[] area1 = new boolean[N]; // 선거구1에서 가능한 구역을 boolean으로 표현한 부분집합
        boolean[] area2 = new boolean[N]; // 선거구2에서 가능한 구역 boolean으로 표현한 부분집합
        int ans = Integer.MAX_VALUE; // 최소 인구수 차이 (정답)
        for (int i = 0; i < (1 << N); i++) { // 비트연산으로 가능한 부분집합 만들기
            if (Integer.bitCount(i) == 0 || Integer.bitCount(i) == N) { // 공집합이거나 전체집합일 경우 선거구가 2개가 안되므로 무시
                continue;
            }
            // 선거구 경우의 수 초기화
            Arrays.fill(area1, false);
            Arrays.fill(area2, false);

            // 공집합, 전체집합이 아닌 부분집합(선거구 별 구역) 정하기
            for (int k = 0; k < N; k++) {
                if ((i & (1 << k)) != 0) {
                    area1[k] = true;
                } else {
                    area2[k] = true;
                }
            }
            // 가능한 선거구일 경우 인구 수 차이 구하기
            if (isAvailable(area1, area2)) {
                int diff = calculate(area1);
                ans = Math.min(diff, ans); // 인구 수 차이가 최소가 되는 것을 정답으로 출력
            }
        }
        // 선거구가 획정이 안될 경우, -1리턴. 가능할 경우 최소 인구수 차이 출력
        if (ans == Integer.MAX_VALUE) {
            System.out.println(-1);
        } else {
            System.out.println(ans);
        }
    }

    // 가능한 선거구인지 체크하는 함수
    // 각 선거구1, 선거구2로 살펴볼 부분집합별로 인접한 구역끼리 모여있는지 체크
    private static boolean isAvailable(boolean[] area1, boolean[] area2) {
        int startIdx1 = 0; // 선거구1에서 탐색을 시작할 정점1
        int startIdx2 = 0; // 선거구2에서 탐색을 시작할 정점2

        // 선거구1의 첫 시작 정점(구역) 고르기
        for (int n = 0; n < N; n++) {
            if (area1[n]) {
                startIdx1 = n;
                break;
            }
        }

        // 선거구2의 첫 시작 정점(구역) 고르기
        for (int n = 0; n < N; n++) {
            if (area2[n]) {
                startIdx2 = n;
                break;
            }
        }
        // 부분집합 구역들끼리 서로 인접해있는지 체크. 인접하지 않았다면, false 리턴
        if (!adjCheck(startIdx1, area1)) {
            return false;
        }
        if (!adjCheck(startIdx2, area2)) {
            return false;
        }
        // 가능한 선거구일 경우 true 리턴
        return true;
    }

    // 인접 구역인지 체크하는 메서드 (bfs 사용)
    private static boolean adjCheck(int v, boolean[] area) {
        boolean[] vis = new boolean[N];
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(v);
        vis[v] = true;

        while (!queue.isEmpty()) {
            int cur = queue.poll();
            for (int i = 0; i < N; i++) {
                if (map[cur][i] == 1 && !vis[i]) { // 지도에서 인접하고, 아직 탐색하지 않은 정점일 경우 큐에 넣기
                    if (!area[i]) {
                        continue; // 다만, 선거구 지역에 포함 안되면 확인할 필요 없으므로 pass
                    }
                    queue.offer(i); // 선거구에 포함되고, 인접할 경우 큐에 넣고 방문 표시하기
                    vis[i] = true;
                }
            }

        }

        // 선거구 부분집합과 bfs로 탐색한 인접 정점 방문 배열을 비교하며, 하나라도 틀리면 false 리턴
        for (int i = 0; i < N; i++) {
            if (vis[i] != area[i]) {
                return false;
            }
        }
        return true;
    }

    // 인수 수 차이 계산하는 메서드
    private static int calculate(boolean[] area1) {
        int sum1 = 0;
        int sum2 = 0;
        for (int i = 0; i < N; i++) {
            if (area1[i]) {
                sum1 += peopleCnt[i];
            } else {
                sum2 += peopleCnt[i];
            }
        }

        return Math.abs(sum1 - sum2);
    }
}
```

<br>
<br>

# **🔑Description**

> 설계 시간: 30min

> 구현 시간: 2hr + a
<aside>
💡 설계 아이디어

    - 최소 인구수 변수를 Integer.MAX_VALUE로 저장
    - 전체 집합을 2개의 부분집합으로 나누기 (비트연산 이용) - 공집합/전체집합은 안됨
    - 각 부분집합별로 원소들끼리 서로 인접해있는지 체크 (bfs 이용)
        - 처음 원소를 큐에 집어넣고(시작 정점) 인접해있는데 선거구 부분집합에 해당할 경우에만 vis 방문표시 했다.
        - 마지막에 vis 배열과 area 배열을 따로 확인해서 이 선거구가 가능한 선거구인지 확인함
    - 가능한 선거구들일 경우, 인구수 차이를 계산하고 최소 인구수에 min 연산하여 저장 (반복)
    - 만약 최소 인구수 변수가 max value일 경우, 선거구 획정 자체가 안되었다는 뜻이므로 -1 출력 선거구 획정 가능할 경우 최소 인구수 출력

</aside>

<br>
<br>

# **📑Related Issues**

> Related Issues
<aside>

- 알고리즘 기본기를 탄탄히 하자.
    - 그래프 인접 행렬, 인접 리스트 탐색 방식이 헷갈려서 얼탔다..ㅋㅋ 맨날 사방탐색 로직만 짜다가 .. ㅜ 그래서 이참에 저번에 대충 넘어갔었던 DFS와 BFS 백준 문제도 풀었다..
    - **부분집합 원소들끼리 인접해있는지 확인하는 로직을 설계하기가 너무너무 생각이 안났다. (왜지?) 결국 해냈다! 그냥. 한번에 다 예외처리 하려고 하지말고, vis 배열 따로 두고 원래 area 배열이랑 비교함.**
    - 그리고 처음에 당연히 모든 구역들이 다 이어져있어서 bfs 특성상 모든 정점을 다 순회하는데, 분명 알고 있는데 구현할 때는 왜 인지를 못하는지... 왜 vis 배열이 아무 조건이 없으면 다 true가 되는지 이해를 못했었다 ㅋㅋㅋㅋㅋ
- 좀 더 효율적으로 짤 수 있었을텐데 아쉽다.
    - 그리고 지금 생각해보니 부분집합을 굳이 따로따로 2개 다 구하지 않아도 됐을 것 같다. 하나만 구하고, 다른 하나는 그 여집합으로서 처리해준다면 더 효율적이었을텐데.. 좀 아쉽다.
    - 그리고 부분집합을 둘로 나누더라도 결국 선거구 a인지 b인지 구분은 필요 없는 문제이므로 이것도 한 구역에 미리 한 원소를 넣어두면 2^9 안에 풀 수 있었을 것 같다.
</aside>

<br>
<br>

# **🕛Resource**

| Memory | Time  | Implementation Time |
| -- |-------|---------------------|
| 15956KB | 136ms | 2 Hour 30 Minutes   |
