import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static int[][][][] maze = new int[5][4][5][5]; //[층][방향][행][열]
    static boolean[][][] visited = new boolean[5][5][5]; // 미로 bfs 방문 체크
    static int[] direction = new int[5], order = new int[5]; // 방향 순서, 층 순서
    static int[][][] cube = new int[5][5][5], depth = new int[5][5][5]; // 미로 저장, 미로 최단경로 깊이 저장
    static Tuple[] entry = {new Tuple(0, 0, 0), new Tuple(0, 4, 0), new Tuple(4, 0, 0), new Tuple(4, 4, 0)};
    static Tuple[] exit = {new Tuple(4, 4, 4), new Tuple(4, 0, 4), new Tuple(0, 4, 4), new Tuple(0, 0, 4)};
    static int[] dx = {0, 1, 0, -1, 0, 0};
    static int[] dy = {1, 0, -1, 0, 0, 0};
    static int[] dz = {0, 0, 0, 0, 1, -1};
    static int result = Integer.MAX_VALUE;

    public static void main(String[] args) throws Exception {

        StringBuilder sb = new StringBuilder();


        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                StringTokenizer st = new StringTokenizer(br.readLine(), " ");
                for (int k = 0; k < 5; k++) {
                    maze[i][0][j][k] = Integer.parseInt(st.nextToken());
                }
            }
        }

        // 각 층별로 3방향으로 돌리기
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                rotate(maze[i][j], maze[i][j + 1]);
            }
        }

        makeDirection(0);

        if (result == Integer.MAX_VALUE) {
            bw.write("-1");
        } else {
            bw.write(Integer.toString(result));
        }

        bw.close();

    }

    static void rotate(int[][] before, int[][] after) { // 각 층별로 3방향으로 돌려서 저장
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                after[4 - j][i] = before[i][j];
            }
        }
    }

    static void makeOrder(int count, int flag) {
        if (count == 5) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    // 정해진 층, 방향대로 가져와서 cube에 넣기
                    cube[i][j] = Arrays.copyOf(maze[order[i]][direction[i]][j], 5);
                }
            }

            for (int i = 0; i < 4; i++) { // 4가지 입/출구 확인 -> 둘다 안막혀있을때만 bfs 탐색
                if (cube[entry[i].x][entry[i].y][entry[i].z] == 1 && cube[exit[i].x][exit[i].y][exit[i].z] == 1) {

                    int d = bfs(entry[i], exit[i]); // 입-출구까지 최단경로

                    // d가 -1이 아닐때 최솟값 가져오기
                    if (d != -1) {
                        result = Math.min(result, d);
                    }
                }
            }

            return;
        }

        for (int i = 0; i < 5; i++) {
            if ((flag & 1 << i) != 0)
                continue;

            order[count] = i;
            makeOrder(count + 1, flag | 1 << i);
        }
    }

    static void makeDirection(int count) {  // 층마다 방향 정하기
        if (count == 5) {
            makeOrder(0, 0);    // 층 순서 정하기

            return;
        }

        for (int i = 0; i < 4; i++) {
            direction[count] = i;
            makeDirection(count + 1);
        }
    }

    ;

    static int bfs(Tuple start, Tuple end) {
        Queue<Tuple> queue = new ArrayDeque<Tuple>();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Arrays.fill(visited[i][j], false);
                Arrays.fill(depth[i][j], 0);
            }
        }

        queue.offer(start);
        visited[start.x][start.y][start.z] = true;

        while (!queue.isEmpty()) {
            int curX = queue.peek().x;
            int curY = queue.peek().y;
            int curZ = queue.poll().z;

            if (end.x == curX && end.y == curY && end.z == curZ) {  //출구 만나는 경우 - 최단경로값 리턴
                return depth[curX][curY][curZ];
            }

            for (int i = 0; i < 6; i++) {
                int nextX = curX + dx[i];
                int nextY = curY + dy[i];
                int nextZ = curZ + dz[i];

                if (nextX < 0 || nextX >= 5 || nextY < 0 || nextY >= 5 || nextZ < 0 || nextZ >= 5
                        || visited[nextX][nextY][nextZ] || cube[nextX][nextY][nextZ] == 0)
                    continue;

                visited[nextX][nextY][nextZ] = true;
                depth[nextX][nextY][nextZ] = depth[curX][curY][curZ] + 1;
                queue.offer(new Tuple(nextX, nextY, nextZ));
            }
        }

        // 출구까지 못나가는 경우
        return -1;
    }

    static class Tuple {
        int x, y, z;

        Tuple(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}