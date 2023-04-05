![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:FF658D,100:FFCB32&text=BOJ%201194&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 14621 달이 차오른다, 가자.](https://www.acmicpc.net/problem/1194)

<br>
<br>

# **💻Code**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    static char[][] maze;   // 미로 저장

    static int[][][] depth; // 이동한 거리 저장

    static boolean[][][] visited;   // 방문 check

    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};

    static int N, M;

    public static void main(String[] args) throws Exception {
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        maze = new char[N][M];
        depth = new int[N][M][64];
        visited = new boolean[N][M][64];

        Tuple start = null;

        for (int i = 0; i < N; i++) {
            String input = br.readLine();
            for (int j = 0; j < M; j++) {
                maze[i][j] = input.charAt(j);

                if (maze[i][j] == '0')
                    start = new Tuple(i, j, 0);
            }
        }

        bw.write(Integer.toString(bfs(start)));
        bw.close();
        br.close();
    }

    static int bfs(Tuple start) {
        Queue<Tuple> queue = new ArrayDeque<>();
        visited[start.x][start.y][start.key] = true;
        queue.offer(start);

        while (!queue.isEmpty()) {
            int x = queue.peek().x;
            int y = queue.peek().y;
            int key = queue.poll().key;

            if (maze[x][y] == '1')  // 도착 지점 도달
                return depth[x][y][key];

            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];

                if (nx < 0 || nx >= N || ny < 0 || ny >= M
                        || maze[nx][ny] == '#' || visited[nx][ny][key])
                    continue;


                if (maze[nx][ny] == '.' || maze[nx][ny] == '0' || maze[nx][ny] == '1') {    //이동할 수 있는 칸
                    visited[nx][ny][key] = true;
                    depth[nx][ny][key] = depth[x][y][key] + 1;
                    queue.offer(new Tuple(nx, ny, key));    // 갖고있던 키 그대로 들고 이동
                } else if (maze[nx][ny] >= 'A' && maze[nx][ny] <= 'Z') {    // 문에 도착
                    int door = 1 << (maze[nx][ny] - 'A');


                    if ((key & door) > 0) { // 뽑은 키로 열 수 있는지 확인
                        visited[nx][ny][key] = true;
                        depth[nx][ny][key] = depth[x][y][key] + 1;
                        queue.offer(new Tuple(nx, ny, key));
                    }

                } else if (maze[nx][ny] >= 'a' && maze[nx][ny] <= 'z') {    // 열쇠 주움
                    int nkey = (1 << (maze[nx][ny] - 'a')) | key;

                    if (visited[nx][ny][nkey])
                        continue;

                    visited[nx][ny][nkey] = true;
                    visited[nx][ny][key] = true;
                    depth[nx][ny][key] = depth[x][y][key] + 1;
                    depth[nx][ny][nkey] = depth[x][y][key] + 1;

                    queue.offer(new Tuple(nx, ny, nkey));   // 새로 뽑은 열쇠 들고 이동

                }
            }
        }

        return -1;  // 미로를 못 빠져 나옴
    }

    static class Tuple {
        int x, y, key;  // 행, 열, 갖고있는 key

        Tuple(int x, int y, int key) {
            this.x = x;
            this.y = y;
            this.key = key;
        }
    }
}

```

<br>
<br>

# **🔑Description**

> bfs와 비트마스킹을 이용하여 풀었다.\
> 방문배열을 3차원으로 선언해서 맨 마지막 레이어에는 내가 뽑은 열쇠를 비트마스킹으로 표시해서 들고다닌다.\

<br>
<br>

# **📑Related Issues**

> 넘 어렵다.... 열쇠 뽑은 경우/안뽑은 경우 나눠서 생각했어야 했는데 그냥 [N][m][8] 이렇게 만들어서 새 열쇠 뽑았을 때 그 레이어로 이동하고 있던거 뽑으면 이동 안하고 암튼ㄴ 이런식으로 하면 안되나??? 싶엇다\
> 생각 못한 부분이 잇는건지 예제에서부터 틀려서... 다른 사람들 푼 것처럼 비트마스킹으로 풀었다.\
> 그리고 어차피 출발지점이 하나니까 73번째 라인 조건문에서 maze[nx][ny] == '0' 를 첨엔 안넣었는데 다시 생각해보니까 열쇠 뽑아서 레이어 바뀌면 출발지점 다시 갈수 있음...\
> 이것땜에 또 +30분 삽질함.....ㅠㅅㅠ\
> 지승오빠가 이거랑 비슷한 문제가 있다고 알려줘서 읽어봤는데 이 문제 문해기 들을때 나온 문제였던것 같음... 꼭꼭꼮 풀어보자 [BOJ 9328 열쇠](https://www.acmicpc.net/problem/9328)

<br>
<br>

# **🕛Resource**

| Memory  | Time  |
| ------- | ----- |
| 14192KB | 112ms |
