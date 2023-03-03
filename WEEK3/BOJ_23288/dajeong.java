import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

class Pos {

    int x;
    int y;

    public Pos(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class Main_23288_주사위굴리기2 {

    static int N, M, K, ans, dir;
    static int[][] board;
    static int[] dice;
    static Pos curPos;
    static int[] dix = {-1, 0, 1, 0}; // 북동남서
    static int[] diy = {0, 1, 0, -1};
    static Queue<Pos> queue;
    static boolean[][] vis; // 트리가 아닌 그래프에서.. 방문체크를 안해주다니..

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken()); // 행
        M = Integer.parseInt(st.nextToken()); // 열
        K = Integer.parseInt(st.nextToken()); // 명령어 갯수

        // 보드 입력
        board = new int[N][M];
        for (int r = 0; r < N; r++) {
            st = new StringTokenizer(br.readLine());
            for (int c = 0; c < M; c++) {
                board[r][c] = Integer.parseInt(st.nextToken());
            }
        }
        curPos = new Pos(0, 0); // 시작 위치 설정
        dir = 1; // 동쪽부터 시작 명시
        ans = 0; // 총 점수의 합 명시
        dice = new int[]{1, 2, 3, 4, 5, 6}; // 주사위 전개도 배열

        for (int k = 0; k < K; k++) { // 주사위 이동 횟수 (게임 수)
            dir %= 4;
            go(); // 다이스 위치 이동

            if (dir == 1) { // 동
                right();
            } else if (dir == 3) { // 서
                left();
            } else if (dir == 0) { // 북
                up();
            } else { // dir == 3 // 남
                down();
            }

            calScore(); // 점수 계산 후 획득
            changeDir(); // 이동방향 결정
        }
        System.out.println(ans);
    }

    private static void changeDir() {
        dir += 4;
        int bottom = dice[5];
        int boardNum = board[curPos.x][curPos.y];
        if (bottom > boardNum) {
            dir += 1; // 시계 방향으로 이동
        } else if (bottom < boardNum) {
            dir -= 1; // 반시계 방향으로 이동
        }
    }

    private static void calScore() {
        queue = new ArrayDeque<>();
        vis = new boolean[N][M]; //방문배열 갱신
        queue.offer(new Pos(curPos.x, curPos.y));
        vis[curPos.x][curPos.y] = true;
        int boardNum = board[curPos.x][curPos.y];
        int sum = boardNum;

        while (!queue.isEmpty()) {
            Pos poll = queue.poll();
            int curX = poll.x;
            int curY = poll.y;
            for (int i = 0; i < 4; i++) {
                int nx = curX + dix[i];
                int ny = curY + diy[i];
                if (nx < 0 || nx >= N || ny < 0 || ny >= M || vis[nx][ny]) {
                    continue;
                }
                if (board[nx][ny] != boardNum) {
                    continue; // 주사위 아랫면의 점수와 상하좌우 이동가능한 점수가 같지 않을 경우 넘기기
                }
                sum += boardNum;
                queue.offer(new Pos(nx, ny));
                vis[nx][ny] = true;
            }
        }
        ans += sum;
    }

    private static void go() {// 다이스 위치 이동
        dir %= 4;
        int x = curPos.x;
        int y = curPos.y;
        x += dix[dir];
        y += diy[dir];
        if (x < 0 || x >= N || y < 0 || y >= M) { // 보드 밖으로 이동하려고 할 경우, 방향 반대로 바꾸기
            dir += 2; // 북동남서
            go(); // 방향 반대로 바꾸고 다시 이동
        } else {
            curPos.x = x;
            curPos.y = y;
        }
    }

    private static void down() {
        int[] shiftArr = new int[6];
        shiftArr[4] = dice[0];
        shiftArr[5] = dice[4];
        shiftArr[1] = dice[5];
        shiftArr[0] = dice[1];
        shiftArr[2] = dice[2];
        shiftArr[3] = dice[3];
        dice = shiftArr;
    }

    private static void up() {
        int[] shiftArr = new int[6];
        shiftArr[1] = dice[0];
        shiftArr[5] = dice[1];
        shiftArr[4] = dice[5];
        shiftArr[0] = dice[4];
        shiftArr[2] = dice[2];
        shiftArr[3] = dice[3];
        dice = shiftArr;
    }

    private static void left() {
        int[] shiftArr = new int[6];
        shiftArr[3] = dice[0];
        shiftArr[5] = dice[3];
        shiftArr[2] = dice[5];
        shiftArr[0] = dice[2];
        shiftArr[1] = dice[1];
        shiftArr[4] = dice[4];
        dice = shiftArr;
    }

    private static void right() {
        int[] shiftArr = new int[6];
        shiftArr[2] = dice[0];
        shiftArr[5] = dice[2];
        shiftArr[3] = dice[5];
        shiftArr[0] = dice[3];
        shiftArr[1] = dice[1];
        shiftArr[4] = dice[4];
        dice = shiftArr;
    }

}