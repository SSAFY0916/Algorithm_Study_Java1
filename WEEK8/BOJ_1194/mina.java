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

