import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static char[][] maze;
    static int mazeRow, mazeCol;
    static int[][] dir = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    static int startRow, startCol;
    static int minMove = 0x7fffffff;

    static class Pos {
        int row;
        int col;
        int cost;
        int keyFlag = 0b000000;

        public Pos(int row, int col, int cost, int keyFlag) {
            this.row = row;
            this.col = col;
            this.cost = cost;
            this.keyFlag = keyFlag;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        mazeRow = Integer.parseInt(st.nextToken());
        mazeCol = Integer.parseInt(st.nextToken());
        maze = new char[mazeRow][mazeCol];

        for (int i = 0; i < mazeRow; i++) {
            String str = br.readLine();
            for (int j = 0; j < mazeCol; j++) {
                maze[i][j] = str.charAt(j);
                if (maze[i][j] == '0') {
                    startRow = i;
                    startCol = j;
                }
            }
        }

        bfs(startRow, startCol);

        System.out.println(minMove == 0x7fffffff ? -1 : minMove);
    }

    private static void bfs(int row, int col) {
        // 각 bfs 별 방문 여부 저장 (keyFlag 별로 visited 배열 별도로 저장)
        boolean[][][] visited = new boolean[1 << 6][mazeRow][mazeCol];
        Queue<Pos> q = new ArrayDeque<>();
        // 초기 값 Queue에 넣고 방문 체크
        q.add(new Pos(row, col, 0, 0));

        visited[0][row][col] = true;

        // bfs 수행
        while (!q.isEmpty()) {
            Pos tmp = q.poll();
            for (int i = 0; i < 4; i++) {
                int nextRow = tmp.row + dir[i][0];
                int nextCol = tmp.col + dir[i][1];
                // 범위 안에 있지 않거나 방문한 곳이면 continue
                if (!check(nextRow, nextCol) || visited[tmp.keyFlag][nextRow][nextCol])
                    continue;
                // 방문 여부 체크
                visited[tmp.keyFlag][nextRow][nextCol] = true;
                // 종료 지점을 만났다면 minMove값 갱신 후 return;
                if (maze[nextRow][nextCol] == '1') {
                    minMove = Math.min(minMove, tmp.cost + 1);
                    q.clear();
                    return;
                }
                // '.'이나 '0'이라면 이동할 수 있는 곳이므로 q에 add
                if (maze[nextRow][nextCol] == '.' || maze[nextRow][nextCol] == '0') {
                    q.add(new Pos(nextRow, nextCol, tmp.cost + 1, tmp.keyFlag));
                    continue;
                }
                // 'a'와 'f' 사이의 값이라면 키 획독 가능한 곳
                if (maze[nextRow][nextCol] >= 'a' && maze[nextRow][nextCol] <= 'f') {
                    // 키를 획득하였으므로 flag값 재설정
                    int nextFlag = tmp.keyFlag | (1 << (maze[nextRow][nextCol] - 'a'));
                    q.add(new Pos(nextRow, nextCol, tmp.cost + 1, nextFlag));
                    continue;
                }
                // 문이라면 키 획득 여부 확인 후 q에 add
                if (maze[nextRow][nextCol] >= 'A' && maze[nextRow][nextCol] <= 'F') {
                    int k = maze[nextRow][nextCol] - 'A';
                    if ((tmp.keyFlag & (1 << k)) == (1 << k)) {
                        q.add(new Pos(nextRow, nextCol, tmp.cost + 1, tmp.keyFlag));
                    }
                }
            }
        }
    }

    private static boolean check(int row, int col) {
        return row >= 0 && row < mazeRow && col >= 0 && col < mazeCol;
    }
}
