![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:FF658D,100:FFCB32&text=BOJ%202151&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 2151 거울 설치](https://www.acmicpc.net/problem/2151)

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

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    static int N;
    static char[][] house;

    static int[][][] depth;
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};

    public static void main(String[] args) throws Exception {


        final int INF = 10000;
        N = Integer.parseInt(br.readLine());
        house = new char[N][N];
        depth = new int[N][N][4];
        Queue<Light> queue = new ArrayDeque<>();
        boolean flag = false;

        Light start = null, end = null;

        for (int i = 0; i < N; i++) {
            house[i] = br.readLine().toCharArray();
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // depth 초기화
                depth[i][j][0] = INF;
                depth[i][j][1] = INF;
                depth[i][j][2] = INF;
                depth[i][j][3] = INF;
                if (house[i][j] == '#') {   // 입구
                    if (!flag) {
                        flag = true;
                        for (int k = 0; k < 4; k++) {   // 입구에서 어느 방향으로 빛을 보낼 수 있는지 확인
                            int x = i + dx[k];
                            int y = j + dy[k];
                            depth[i][j][k] = 0; // 입구의 depths 0으로 시작
                            if (x < 0 || x >= N || y < 0 || y >= N)
                                continue;

                            if (house[x][y] == '!') {   // 입구 옆칸이 거울인 경우
                                queue.offer(new Light(i, j, k));    // 직진
                                int d = (k + 1) % 4;
                                queue.offer(new Light(i, j, d));    // 오른쪽
                                d = (d + 2) % 4;
                                queue.offer(new Light(i, j, d));    // 왼쪽
                            } else if (house[x][y] == '.') {    // 입구 옆칸이 공기인 경우
                                queue.offer(new Light(i, j, k));    // 직진
                            }
                        }

                    } else {    // 출구
                        end = new Light(i, j, 0);
                    }
                }


            }
        }

        bw.write(Integer.toString(mirror(end, queue)));
        bw.flush();
        bw.close();
        br.close();
    }

    static int mirror(Light end, Queue<Light> queue) {

        while (!queue.isEmpty()) {
            int x = queue.peek().x;
            int y = queue.peek().y;
            int d = queue.poll().d;

            int nx = x + dx[d];
            int ny = y + dy[d];


            // 다음 좌표가 . 일때 -> 이전과 똑같은 한 방향으로만 탐색
            // 다음 좌표가 ! 일때 -> 3방향으로 탐색
            // 다음 좌표가 * 일때

            if (nx < 0 || nx >= N || ny < 0 || ny >= N || house[nx][ny] == '*') {   // 범위 넘어가거나 벽인 경우
                continue;
            } else if (house[nx][ny] == '.') {  // 오던 방향으로 통과
                if (depth[nx][ny][d] <= depth[x][y][d])     // 더 적은 개수의 거울로 도착할 수 있음
                    continue;
                depth[nx][ny][d] = depth[x][y][d];
                queue.offer(new Light(nx, ny, d));

            } else if (house[nx][ny] == '!') {  // 거울 -> 3방향 통과
                if (depth[nx][ny][d] <= depth[x][y][d]) // 오던 방향으로 통과
                    continue;
                depth[nx][ny][d] = depth[x][y][d];
                queue.offer(new Light(nx, ny, d));

                int nd = (d + 1) % 4;   // 오른쪽으로 반사

                if (depth[nx][ny][nd] <= depth[x][y][d] + 1)
                    continue;

                depth[nx][ny][nd] = depth[x][y][d] + 1;
                queue.offer(new Light(nx, ny, nd));

                nd = (nd + 2) % 4; // 왼쪽으로 반사

                if (depth[nx][ny][nd] <= depth[x][y][d] + 1)
                    continue;

                depth[nx][ny][nd] = depth[x][y][d] + 1;
                queue.offer(new Light(nx, ny, nd));

            } else if (nx == end.x && ny == end.y) {    // 출구에 도착 -> 더 적은 개수로 도착하는 경우가 있을 수 있어서 바로 리턴시키지 않음
                if (depth[nx][ny][d] <= depth[x][y][d])
                    continue;

                depth[nx][ny][d] = depth[x][y][d];
            }
        }

        int result = 10000;
        for (int i = 0; i < 4; i++) {
            result = Math.min(result, depth[end.x][end.y][i]);  // 출구에 도착하면서 사용한 거울의 개수 중 최솟값 가져오기
        }
        return result;

    }

    static class Light {
        int x, y, d;    // x, y 에 d방향으로 빛이 도착함

        Light(int x, int y, int d) {
            this.x = x;
            this.y = y;
            this.d = d;
        }
    }
}

```

<br>
<br>

# **🔑Description**

> BFS 탐색하듯이 구현했다.\
> 그 위치가 .인지 ! 인지에 따라서 직진 방향으로만 탐색할 지, 3 방향으로 탐색할 지 결정하였다.\
> visited를 사용하는 대신 depth만 사용하여 더 적은 거울 개수로 도달할 수 있을때마다 갱신해줬다.

<br>
<br>

# **📑Related Issues**

> 처음엔 이전위치와 다음위치의 depth를 비교하는 대신에 visited를 사용해서 먼저 방문한 공간을 걸러냈다.\
> 근데 방문처리 해버리면 더 적은 거울 개수로 갈 수 있는데도 걸려져버려서 visited를 사용하지 않고 depth로 비교해서 걸러냈다.\
> End에 도착했을때도 도착하자마자 리턴시켰는데 위와 비슷한 이유로 끝까지 탐색한 후에 가장 적은 개수의 depth[End]를 리턴했다.\
> 다 풀고 카테고리 보니까 이게 왜 다익스트라인지 알겠음..!

<br>
<br>

# **🕛Resource**

| Memory  | Time |
| ------- | ---- |
| 12212KB | 96ms |
