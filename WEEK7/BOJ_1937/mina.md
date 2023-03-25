![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:FF658D,100:FFCB32&text=BOJ%201937&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 1937 욕심쟁이 판다](https://www.acmicpc.net/problem/1937)

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

    static int N;

    static int[][] bamboo, depth; // 대나무, 깊이 depth
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};


    public static void main(String[] args) throws Exception {

        N = Integer.parseInt(br.readLine());

        bamboo = new int[N][N];
        depth = new int[N][N];

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < N; j++) {
                bamboo[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (depth[i][j] == 0)  // depth 값이 없는 경우만 dfs로 계산
                    dfs(i, j);
            }
        }

        int max = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                max = Math.max(depth[i][j], max);
            }
        }


        bw.write(Integer.toString(max));
        bw.close();
    }

    static int dfs(int x, int y) {
        if (depth[x][y] != 0) {    // 이미 저장된 depth값이 있는 경우 그대로 리턴
            return depth[x][y];
        }

        boolean flag = false;
        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            if (nx < 0 || nx >= N || ny < 0 || ny >= N || bamboo[x][y] >= bamboo[nx][ny])
                continue;

            flag = true;

            // 그 위치로부터 나오는 트리의 depth 중 가장 큰 값을 저장
            depth[x][y] = Math.max(depth[x][y], dfs(nx, ny) + 1);
        }

        if (!flag) {    // 넘어갈 수 있는 칸이 없는 경우 - 리프 노드
            return depth[x][y] = 1;
        }

        return depth[x][y];
    }

}

```

<br>
<br>

# **🔑Description**

> (i, j)를 루트노드로 하는 트리에서 가장 깊은 depth가 나오는 경우를 depth(i, j)에 저장했다.\
> 이미 저장된 값이 있는 경우에는 그 값을 그냥 리턴하도록 해서 dp로 푼 것 같기도 하다.

<br>
<br>

# **📑Related Issues**

> 어이디어를 떠올리는데 조금 걸렸지만 그래도 금방 풀었다.ㅎㅅㅎ

<br>
<br>

# **🕛Resource**

| Memory  | Time  |
| ------- | ----- |
| 40488KB | 412ms |
