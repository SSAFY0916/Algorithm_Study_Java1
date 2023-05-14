![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2020166&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 20166_문자열 지옥에 빠진 호석](https://www.acmicpc.net/problem/20166)

<br>
<br>

# **Code**

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {

    static int n, m, k;
    static int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
    static int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};
    static char[][] map;

    static HashMap<String, Integer> answer;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        map = new char[n][m];
        answer = new HashMap<>();

        for (int i = 0; i < n; i++) {
            String s = br.readLine();
            for (int j = 0; j < m; j++) {
                map[i][j] = s.charAt(j);
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                dfs(i, j, String.valueOf(map[i][j]));
            }
        }

        for (int i = 0; i < k; i++) {
            String query = br.readLine();
            System.out.println(answer.getOrDefault(query, 0));
        }
    }

    private static void dfs(int x, int y, String str) {

        answer.put(str, answer.getOrDefault(str, 0) + 1);
        if (str.length() == 5) {
            return;
        }

        for (int i = 0; i < 8; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            if (nx < 0) {
                nx = n - 1;
            } else if (nx >= n) {
                nx = 0;
            }

            if (ny < 0) {
                ny = m - 1;
            } else if (ny >= m) {
                ny = 0;
            }
            dfs(nx, ny, str + map[nx][ny]);
        }
    }
}

```

<br>
<br>

# **🔑Description**


<aside>
💡 설계 아이디어
    
    1. "환형"으로 dfs로 그래프 탐색하면서 생성 가능한 문자열 HashMap에 저장 (조회, 삽입 O(1))
    2. (종료조건) 가능한 문자열 최대 길이(5)일 때 종료

</aside>

<br>
<br>

# **📑Related Issues**

> Related Issues
<aside>

    그래프 탐색 + Hash 문제였다
    처음에 종료조건을 생각하기가 힘들었는데, 문제를 다시 읽어보고 알 수 있었다.
    구현 자체는 어렵지 않은데 HashMap 쓰는 아이디어를 떠올리는게 살짝 시간이 걸렸다.
    dfs인데 생각보다 시간이 오래 안걸려서 놀랬다. 시간 계산하는게 어려운 것 같다.
    HashMap을 쓰느라 메모리는 어쩔 수 없이 많이 쓴 것 같다.

</aside>

<br>
<br>

# **🕛Resource**

| Memory | Time  | Implementation Time |
| -- |-------|---------------------|
| 57664KB | 556ms |  |
