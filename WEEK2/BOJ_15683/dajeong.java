import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main_15683_감시 {

    static int[] dix = {-1, 0, 1, 0}; // 북 동 남 서
    static int[] diy = {0, 1, 0, -1};
    static int N,M;
    static int[][] board, copyBoard;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        board = new int[N+2][M+2]; // 최초에 입력받은 board를 저장할 변수
        copyBoard = new int[N+2][M+2]; // 사각 지대의 개수를 세기 위해 사용할 변수
        List<int[]> cctv = new ArrayList<>(); // cctv의 좌표를 저장할 리스트

        // 원본 board 입력 받고 cctv 탐색
        int min = 0; // 사각지대 최소 갯수 (정답)
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
                if (board[i][j] != 0 && board[i][j] != 6) {
                    cctv.add(new int[]{i,j}); // cctv 좌표 넣기
                }
                if (board[i][j] == 0) min++; // cctv가 없을 수 있으므로 min을 초기 사각지대 갯수로 설정
            }
        }

        // 각 경우의 수별로 시뮬레이션
        // tmp를 4진법으로 뒀을 때 각 자리수를 cctv의 방향으로 생각할 것
        // cctv 갯수(k) -> 0부터 (4^k)-1개 가능
        for (int tmp = 0; tmp < (1 << (2 * cctv.size())); tmp++) { // 1 << (2*cctv.size())는 4의 cctv.size()승을 의미.
            // 사각지대를 살펴볼 copyBoard에 원본 board 복사
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    copyBoard[i][j] = board[i][j];
                }
            }
            int brute = tmp;
            // 각각의 cctv 방향 경우의 수에 따라 감시 실행
            for (int[] ct : cctv) {
                int dir = brute % 4;
                brute /= 4;
                int x = ct[0];
                int y = ct[1];

                if (board[x][y] == 1) {
                    watch(x, y, dir);
                } else if (board[x][y] == 2) {
                    watch(x, y, dir);
                    watch(x, y, dir + 2);
                } else if (board[x][y] == 3) {
                    watch(x, y, dir);
                    watch(x, y, dir + 1);
                } else if (board[x][y] == 4) {
                    watch(x, y, dir);
                    watch(x, y, dir + 1);
                    watch(x, y, dir + 3);
                } else { // board1[x][y] == 5
                    watch(x, y, dir);
                    watch(x, y, dir + 1);
                    watch(x, y, dir + 2);
                    watch(x, y, dir + 3);
                }
            }
            // 사각지대 갯수 구하기
            int val = 0;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    if (copyBoard[i][j] == 0) {
                        val++;
                    }
                }
            }
            // 최솟값 갱신 후 정답 출력
            min = Math.min(min, val);
        }
        System.out.println(min);
    }

    // 방향에 따라 쭉~ 감시하는 함수
    // (x,y)에서 dir 방향으로 진행하면서 벽을 만날 때 까지 지나치는 모든 빈칸을 7로 바꿔버림
    private static void watch(int x, int y, int dir) {
        dir %= 4;
        while(true) {
            x += dix[dir];
            y += diy[dir];
            if ((x < 0 || x >= N || y < 0 || y >= M) || copyBoard[x][y] == 6) return; // 범위를 벗어났거나 벽을 만나면 함수를 탈출
            if (copyBoard[x][y] != 0) continue; // 해당 칸이 빈칸이 아닐 경우(=cctv가 있을 경우) 넘어감
            copyBoard[x][y] = 7; // 빈칸을 7로 덮음
        }
    }

}
