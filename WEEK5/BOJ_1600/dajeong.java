import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class dajeong {
    static int K, R, C;
    static int[][] board;
    static int[][][] dist;
    static int minCnt; // 정답
    static int[][] dirM = {{0, -1}, {0, 1}, {1, 0}, {-1, 0}};
    static int[][] dirH = {{-2, 1}, {-1, 2}, {1, 2}, {2, 1}, {2, -1}, {1, -2}, {-1, -2}, {-2, -1}};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        K = Integer.parseInt(br.readLine()); // 말로 이동할 수 있는 횟수
        StringTokenizer st = new StringTokenizer(br.readLine());
        C = Integer.parseInt(st.nextToken()); // 열 수
        R = Integer.parseInt(st.nextToken()); // 행 수
        board = new int[R][C];
        dist = new int[R][C][K+1]; // k는 횟수이므로 1부터 시작

        for (int r = 0; r < R; r++) {
            st = new StringTokenizer(br.readLine());
            for (int c = 0; c < C; c++) {
                board[r][c] = Integer.parseInt(st.nextToken());
            }
        }
        final int INF = Integer.MAX_VALUE;
        minCnt = INF;
        bfs();
        System.out.println(minCnt==INF ? -1 : minCnt);


    }

    private static void bfs() {
        boolean[][][] vis = new boolean[R][C][K+1];
        Queue<Monkey> queue = new ArrayDeque<>();
        queue.offer(new Monkey(0, 0, 0, 0)); // 원숭이 초기 위치: (0,0), 이동거리:0, 말이 된 횟수:0
        vis[0][0][0] = true;

        while(!queue.isEmpty()) {
            Monkey curM = queue.poll();
            // 원숭이 위치가 목적지(맨 오른쪽 아래)일 경우 원숭이 위치 저장 후 return
            if (curM.x == R-1 && curM.y == C-1) {
                minCnt = curM.dist;
                return;
            }
            // 원숭이처럼 이동
            moveLikeMonkey(queue, curM, vis);

            // 현재 원숭이의 말로 이동한 횟수가 K보다 작을 때 말로 이동
            if (curM.horseCnt < K) {
                moveLikeHorse(queue, curM, vis);
            }

        }

    }

    private static void moveLikeHorse(Queue<Monkey> queue, Monkey curM, boolean[][][] vis) {
        for (int i = 0; i < 8; i++) {
            int nx = dirH[i][0] + curM.x;
            int ny = dirH[i][1] + curM.y;
            int nHorseCnt = curM.horseCnt+1; // 말로 이동한 경우이므로 말로 이동한 횟수+1
            if (nx < 0 || nx >= R || ny < 0 || ny >= C || vis[nx][ny][nHorseCnt]) continue;
            if (board[nx][ny] == 1) continue; // 장애물이 있으면 이동 불가능
            Monkey monkey = new Monkey(nx, ny, curM.dist + 1, nHorseCnt);
            queue.offer(monkey);
            vis[nx][ny][nHorseCnt] = true;
        }
    }

    private static void moveLikeMonkey(Queue<Monkey> queue, Monkey curM, boolean[][][] vis) {
        // 원숭이는 사방탐색
        for (int i = 0; i < 4; i++) {
            int nx = dirM[i][0] + curM.x;
            int ny = dirM[i][1] + curM.y;

            if (nx < 0 || nx >= R || ny < 0 || ny >= C || vis[nx][ny][curM.horseCnt]) continue; // 범위 체크, 방문 체크
            if (board[nx][ny] == 1) continue; // 장애물이 있으면 이동 불가능
            Monkey monkey = new Monkey(nx, ny, curM.dist + 1, curM.horseCnt);
            queue.offer(monkey); // 사방탐색으로 이동한 원숭이 객체를 큐에 저장
            vis[nx][ny][curM.horseCnt] = true; // 방문 표시
        }
    }

    // 원숭이 객체
    private static class Monkey {
        int x; // 원숭이 행 위치
        int y; // 열 위치
        int dist; // 이동한 거리
        int horseCnt; // 말이된 횟수

        public Monkey(int x, int y, int dist, int horseCnt) {
            this.x = x;
            this.y = y;
            this.dist = dist;
            this.horseCnt = horseCnt;
        }

        @Override
        public String toString() {
            return "Monkey{" +
                "x=" + x +
                ", y=" + y +
                ", dist=" + dist +
                ", horseCnt=" + horseCnt +
                '}';
        }
    }
}