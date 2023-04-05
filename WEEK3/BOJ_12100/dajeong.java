import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;
// 설계 방식은 dajeong.md 참고
public class Main {

    static int N;
    static int[][] board, copyBoard;
    static int max;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        max = 0;
        board = new int[N][N];
        copyBoard = new int[N][N];

        for (int r = 0; r < N; r++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int c = 0; c < N; c++) {
                board[r][c] = Integer.parseInt(st.nextToken());
                max = Math.max(max, board[r][c]);
            }
        }
        execute();
    }

    private static void execute() {
        for (int i = 0; i < (1 << (2 * 5)); i++) { //1024 // 5번에 대해 각각 회전 방향을 정해줌. ("감시" 문제와 동일한 방법)
            // 각 방향 경우의 수에 따라 시뮬레이션 할 배열 복사
            for (int r = 0; r < N; r++) {
                copyBoard[r] = Arrays.copyOf(board[r], board[r].length);
            }
            int tmp = i;
            for (int t = 0; t < 5; t++) {
                int dir = tmp % 4;
                tmp /= 4;
                shift(dir); // dir= 0,1,2,3 (좌상우하 == 0도, 90도, 180도, 270도 돌리기)
            }
            checkMaxVal();
        }
        System.out.println(max);
    }

    private static void checkMaxVal() {
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                max = Math.max(max, copyBoard[r][c]);
            }
        }
    }

    private static void shift(int dir) { // 한 행을 기울여서 미는 작업
        while (dir-- > 0) {
            rotate(); // 배열을 시계방향으로 90도 돌림
        }
        for (int r = 0; r < N; r++) {
            int[] shiftArr = new int[N]; // 값을 민 결과를 저장할 새 배열
            int idx = 0; // 포인터 역할

            for (int i = 0; i < copyBoard[r].length; i++) {
                if (copyBoard[r][i] == 0) continue; // 원 배열의 값이 0이 아닐 때만 밀기 가능

                if (shiftArr[idx] == 0) {
                    shiftArr[idx] = copyBoard[r][i]; // 새 배열에 값이 없으면 원 배열 값 추가
                } else { // 새 배열에 값이 있을 경우 비교
                    if (shiftArr[idx] == copyBoard[r][i]) { // 이미 들어간 값과 같으면, 합쳐주기
                        shiftArr[idx++] *= 2;
                    } else { // 다르면 별도로 저장
                        shiftArr[++idx] = copyBoard[r][i];
                    }
                }
            }
            for (int c = 0; c < N; c++) {
                copyBoard[r][c] = shiftArr[c];
            }
        }
    }

    private static void rotate() { // 배열을 90도씩 회전하는 함수 (상하좌우 미는 동작을 각각 안만들고, 배열을 돌린 후 밀게끔 구현)
        int[][] tmp = new int[N][N];
        for (int r = 0; r < N; r++) {
            tmp[r] = Arrays.copyOf(copyBoard[r], copyBoard[r].length);
        }
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                copyBoard[r][c] = tmp[N-1-c][r];
            }
        }

    }

}