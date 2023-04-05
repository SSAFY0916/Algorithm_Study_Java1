import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_21609 {
    static int size;
    static int colorNum;
    static int[][] board; // board의 값이 -2이면 빈칸
    // BFS 방문 여부를 체크하기 위한 배열
    static boolean[][] visited;
    static int[][] dir = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
    static int score;
    // 무지개 블럭의 수를 계산
    static int rainbowBlock;
    // 현재 배열에서 지워야 하는 블럭들을 저장
    static Queue<Pair> deleteQueue = new ArrayDeque<>();

    static class Pair {
        int row;
        int col;

        public Pair(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public String toString() {
            return "Pair [row=" + row + ", col=" + col + "]";
        }
    }

    static boolean check(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size)
            return false;
        return true;
    }

    static void initVisited() {
        for (int i = 0; i < size; i++) {
            Arrays.fill(visited[i], false);
        }
    }

    static void zeroToNotVisit() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == 0)
                    visited[i][j] = false;
            }
        }
    }

    static void gravity() {
        int[][] newBoard = new int[size][size];
        for (int i = 0; i < size; i++) {
            Arrays.fill(newBoard[i], -2);
        }

        for (int col = 0; col < size; col++) {
            int idx = size - 1;
            for (int row = size - 1; row >= 0; row--) {
                if (board[row][col] == -1) {
                    idx = row - 1;
                    newBoard[row][col] = -1;
                    continue;
                }
                if (board[row][col] == -2)
                    continue;
                newBoard[idx--][col] = board[row][col];
            }
        }

        board = newBoard;
    }

    static void rotate() {
        int[][] newBoard = new int[size][size];

        for (int row = 0, newCol = 0; row < size; row++, newCol++) {
            for (int col = 0, newRow = size - 1; col < size; col++, newRow--) {
                newBoard[newRow][newCol] = board[row][col];
            }
        }

        board = newBoard;
    }

    static void bfs(int row, int col) {
        Queue<Pair> q = new ArrayDeque<>();
        // 현재 q에 저장된 곳을 지워야 할 경우가 있을 수 있기 때문에 q의 내용을 따로 저장할 서브 Queue 사용
        Queue<Pair> save = new ArrayDeque<>();
        q.add(new Pair(row, col));
        save.add(new Pair(row, col));
        visited[row][col] = true;
        int value = board[row][col];
        // 지울 수 있는 블럭이 같은 경우 무지개 블럭이 많이 포함된 곳을 지워야 함
        // 따라서 무지개 블럭을 저장할 변수 사용
        int curRainbow = 0;

        // bfs 실행
        while (!q.isEmpty()) {
            Pair tmp = q.poll();

            // 현재 위치에서 사방탐색 진행
            for (int i = 0; i < 4; i++) {
                int nextRow = tmp.row + dir[i][0];
                int nextCol = tmp.col + dir[i][1];

                // 범위가 벗어난 곳이라면 탐색하지 않음
                if (!check(nextRow, nextCol))
                    continue;

                // 방문하지 않은 위치이고, 해당 블럭이 현재 탐색중인 블럭의 색 또는 무지개 색이라면 q에 add
                if (!visited[nextRow][nextCol]
                        && (board[nextRow][nextCol] == value || board[nextRow][nextCol] == 0)) {
                    visited[nextRow][nextCol] = true;
                    // 무지개 블럭 색을 카운팅
                    if (board[nextRow][nextCol] == 0)
                        curRainbow++;
                    q.add(new Pair(nextRow, nextCol));
                    save.add(new Pair(nextRow, nextCol));
                }
            }
        }

        // 무지개 색 블럭을 방문하지 않는 블럭으로 변경
        zeroToNotVisit();

        // 탐색한 블럭의 사이즈가 삭제로 예정된 블럭의 사이즈보다 크다면 갱신
        if (save.size() > deleteQueue.size()) {
            deleteQueue = save;
            rainbowBlock = curRainbow;
            // 탐색한 블럭의 사이즈가 삭제로 예정된 블럭의 사이즈와 같다면
            // 무지개 색 블럭의 개수를 비교한 후 갱신
        } else if (save.size() == deleteQueue.size()) {
            if (curRainbow >= rainbowBlock) {
                deleteQueue = save;
                rainbowBlock = curRainbow;
            }
        }
    }

    // deleteQueue에 저장된 블럭들을 삭제
    static boolean delete() {
        // 2보다 작다면 더 이상 삭제가 불가능
        if (deleteQueue.size() < 2)
            return false;

        // 점수를 더해주기
        score += Math.pow(deleteQueue.size(), 2);
        while (!deleteQueue.isEmpty()) {
            Pair tmp = deleteQueue.poll();
            board[tmp.row][tmp.col] = -2;
        }
        rainbowBlock = 0;
        return true;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        size = Integer.parseInt(st.nextToken());
        colorNum = Integer.parseInt(st.nextToken());

        board = new int[size][size];
        visited = new boolean[size][size];

        for (int i = 0; i < size; i++) {
            StringTokenizer tmp = new StringTokenizer(br.readLine());
            for (int j = 0; j < size; j++) {
                board[i][j] = Integer.parseInt(tmp.nextToken());
            }
        }

        while (true) {
            // 방문 배열 초기화
            initVisited();
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    // 블럭의 번호가 1 이상인 경우에만 탐색을 진행하므로 0보다 작을 경우 다음 인덱스 탐색
                    if (board[i][j] <= 0)
                        continue;
                    // 방문하지 않은 곳이라면 해당 위치부터 bfs 실행
                    if (!visited[i][j]) {
                        bfs(i, j);
                    }
                }
            }
            // 더 이상 지울 수 있는 블럭이 없다면 반복문 탈출
            if (!delete())
                break;
            gravity();
            rotate();
            gravity();
        }

        System.out.println(score);
    }

}
