import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_14890 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int boardSize = Integer.parseInt(st.nextToken());
        int[][] board = new int[boardSize][boardSize];
        int length = Integer.parseInt(st.nextToken());

        for (int i = 0; i < boardSize; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int cnt = 0;
        for (int i = 0; i < boardSize; i++) {
            int[] copyBoard = new int[boardSize];
            // 가로 방향으로 탐색
            for (int j = 0; j < boardSize; j++) {
                copyBoard[j] = board[i][j];
            }
            if (check(copyBoard, length)) {
                cnt++;
            }
            // 세로 방향으로 탐색
            for (int j = 0; j < boardSize; j++) {
                copyBoard[j] = board[j][i];
            }
            if (check(copyBoard, length)) {
                cnt++;
            }
        }

        System.out.println(cnt);
    }

    /**
     * 
     * @param board
     * main 함수에서 이 행/열이 지나갈 수 있는지 판단하기 위해 만든 copyBoard를 parameter로 전달
     * @param length 
     * 경사로의 최대 길이를 parameter로 전달
     * @return
     * 이동할 수 있는 길인지 없는 길인지 여부를 반환
     */
    private static boolean check(int[] board, int length) {
        // board[0]번부터 시작
        // curNumCnt 는 현재 인덱스가 위치한 곳 까지 같은 값이 연속되서 나온 횟수를 저장
        int curNumCnt = 1;
        int curNum = board[0];
        // curNumCnt 가 length 보다 커지면 경사로 건설이 가능
        boolean upEnable = false;
        if (curNumCnt >= length)
            upEnable = true;

        // 1번 인덱스부터 끝까지 탐색
        for (int i = 1; i < board.length; i++) {
            // curNum 과 board[i] 가 같으면 curNumCnt 를 증가시킴
            if (board[i] == curNum) {
                curNumCnt++;
                if (curNumCnt >= length)
                    upEnable = true;
            } 
            // 현재 위치가 이전 까지의 번호보다 1 감소한 곳이라면 
            else if (board[i] == curNum - 1) {
                // 이 지점부터 length 개 만큼 탐색하며 경사로 건설 가능한 곳인지 여부를 판별
                int tmpCnt = 0;
                int targetLength = i + length;
                for (; i < board.length; i++) {
                    if (curNum - 1 != board[i])
                        break;
                    tmpCnt++;
                    if (tmpCnt == length)
                        break;
                }
                // 건설이 가능하다면 (연속해서 length개 만큼 나온다면) upEnable, curNumCnt 값을 초기화 하고
                // 아직 i가 board.length 보다 작아서 더 탐색이 가능 하다면 curNum 값도 초기화
                if (tmpCnt == length) {
                    upEnable = false;
                    curNumCnt = 0;
                    if (i < board.length) {
                        curNum = board[i];
                    }
                }
                // 그렇지 않은 경우 건설이 불가능하므로 false 리턴
                else {
                    return false;
                }
            }
            // 현재 위치가 이전 까지의 번호보다 1 증가한 곳이라면
            else if (board[i] == curNum + 1) {
                // 증가 가능 여부를 확인 후 가능 하다면 curNumCnt 값과 upEnable 값을 초기화
                if (upEnable) {
                    curNumCnt = 1;
                    if (curNumCnt >= length) {
                        upEnable = true;
                    } else {
                        upEnable = false;
                    }
                    curNum = board[i];
                }
                // upEnable 이 false 인 경우 경사로 건설이 불가능 하므로 false 리턴
                else {
                    return false;
                }
            }
            // 나머지 경우는 최소 높이가 2 이상 차이가 나기 때문에 이동이 불가능
            // 따라서 false 를 리턴
            else {
                return false;
            }
        }
        // 끝까지 문제 없이 잘 탐색 했다면 true를 리턴
        return true;
    }
}