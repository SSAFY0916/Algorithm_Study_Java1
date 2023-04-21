import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;

public class BOJ_2151 {
    static char[][] board;
    
    // 우, 하, 좌, 상 순서
    static int[][] dir = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    static Point sPoint;

    static class Point {
        int row;
        int col;

        public Point(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    static class Mirror {
        Point point;
        int direction;

        public Mirror(Point point, int direction) {
            this.point = point;
            this.direction = direction;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int size = Integer.parseInt(br.readLine());

        board = new char[size][size];

        for (int i = 0; i < size; i++) {
            String tmp = br.readLine();
            for (int j = 0; j < size; j++) {
                board[i][j] = tmp.charAt(j);
                // 처음 #가 나온 지점은 이 지점부터 탐색을 시작하여야 하기 때문에 따로 저장
                if (board[i][j] == '#' && sPoint == null)
                    sPoint = new Point(i, j);
            }
        }

        System.out.println(bfs());
    }

    private static int bfs() {
        Queue<Mirror> q = new ArrayDeque<>();
        // 어느 방향으로 들어왔는지에 따라서 탐색을 여부를 따로 지정해 주기 위하여 3차원 배열로 생성
        boolean[][][] visited = new boolean[board.length][board.length][4];
        // 4방향에 대해서 모두 탐색 진행
        q.add(new Mirror(sPoint, 0));
        q.add(new Mirror(sPoint, 1));
        q.add(new Mirror(sPoint, 2));
        q.add(new Mirror(sPoint, 3));
        visited[sPoint.row][sPoint.col][0] = true;
        visited[sPoint.row][sPoint.col][1] = true;
        visited[sPoint.row][sPoint.col][2] = true;
        visited[sPoint.row][sPoint.col][3] = true;

        int cnt = 0;

        while (!q.isEmpty()) {
            int qSize = q.size();
            while (qSize --> 0) {
                Mirror tmp = q.poll();
                int curRow = tmp.point.row;
                int curCol = tmp.point.col;
                int curDir = tmp.direction;

                for (int i = 0; i < 4; i++) {
                    // 들어온 방향에 따라서 꺾일 수 없는 각도는 탐색하지 않음
                    if (curDir % 2 == 1 && (i == 1 || i == 3))
                        continue;
                    if (curDir % 2 == 0 && (i == 0 || i == 2))
                        continue;
                    int nextRow = curRow;
                    int nextCol = curCol;
                    // 거울을 설치하지 않고 다음 점으로 탐색할 수도 있기 때문에 while 문으로 해당 방향에 가능한 모든 점들에 대해 탐색 
                    while (check(nextRow + dir[i][0], nextCol + dir[i][1]) ) {
                        nextRow += dir[i][0];
                        nextCol += dir[i][1];
                        
                        // 벽이 있다면 빛은 더이상 지나갈 수 없기 때문에 반복문 종료
                        if (board[nextRow][nextCol] == '*')
                            break;

                        if (visited[nextRow][nextCol][curDir])
                            continue;
                        
                        // 다른 문을 발견했다면 해당 지점으로 최소 횟수로 탐색 할 수 있는 경우, 따라서 함수 종료
                        if (board[nextRow][nextCol] == '#') {
                            return cnt;
                        }

                        // 일반 지점이라면 방문 여부만 체크 후 통과
                        if (board[nextRow][nextCol] == '.') {
                            visited[nextRow][nextCol][curDir] = true;
                            continue;
                        }

                        // 거울을 설치할 수 있는 위치라면 45도 각도로 두개의 거울을 설치하는 경우를 Queue에 Add
                        if (board[nextRow][nextCol] == '!') {
                            visited[nextRow][nextCol][curDir] = true;
                            if (curDir % 2 == 0) {
                                q.add(new Mirror(new Point(nextRow, nextCol), 1));
                                q.add(new Mirror(new Point(nextRow, nextCol), 3));
                            }
                            else {
                                q.add(new Mirror(new Point(nextRow, nextCol), 0));
                                q.add(new Mirror(new Point(nextRow, nextCol), 2));
                            }
                            continue;
                        }
                    }
                }
            }
            cnt++;
        }

        return cnt;
    }

    private static boolean check(int row, int col) {
        return row >= 0 && row < board.length && col >= 0 && col < board.length;
    }
}