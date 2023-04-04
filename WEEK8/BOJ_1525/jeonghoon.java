import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_1525 {
    static Board board = new Board();
    static int[][] dir = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    static class Board {
        /**
         * board : 표를 저장하기 위한 3 * 3 배열
         * zeroIdxRow : 0의 Row 위치 저장
         * zeroIdxCol : 0의 Col 위치 저장
         * moveCnt : 현재 Board가 만들어지기까지 움직인 횟수를 저장
         */
        int[][] board = new int[3][3];
        int zeroIdxRow;
        int zeroIdxCol;
        int moveCnt = 0;

        /**
         * hashSet 사용을 위한 hashCode(), equals() 오버라이딩
         * hashCode() : 0,0 ~ 2,2 까지 모든 수를 뒤에 이어 붙인 String을 Hash값으로 변환
         * equals() : 9개의 값 중 다른 값이 하나라도 있다면 false를 return;
         */
        @Override
        public int hashCode() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    sb.append(board[i][j]);
                }
            }
            return sb.toString().hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            int[][] objBoard = ((Board)obj).board;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (objBoard[i][j] != board[i][j])
                        return false;
                }
            }
            return true;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        for (int i = 0; i < 3; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 3; j++) {
                /**
                 * 초기 배열을 저장할 board에 입력을 받기
                 * 0의 위치인 곳의 index를 zeroIdxRow, zeroIdxCol에 각각 저장
                 */
                board.board[i][j] = Integer.parseInt(st.nextToken());
                if (board.board[i][j] == 0) {
                    board.zeroIdxRow = i;
                    board.zeroIdxCol = j;
                }
            }
        }

        System.out.println(bfs());
    }

    private static int bfs() {
        /**
         * q : BFS를 수행하기 위한 Queue
         * set : 이미 탐색을 해본 board를 저장해 두기 위한 set
         */
        Queue<Board> q = new ArrayDeque<>();
        Set<Board> set = new HashSet<>();
        /**
         * copyBoard에 기존 board를 복사 (moveCnt 값은 초기 상태에서 0이므로 따로 복사하지 않음)
         * 복사한 copyBoard를 Queue와 Set에 각각 add
         */
        Board copyBoard = new Board();
        for (int i = 0; i < 3; i++) {
            copyBoard.board[i] = Arrays.copyOf(board.board[i], 3);
        }
        copyBoard.zeroIdxRow = board.zeroIdxRow;
        copyBoard.zeroIdxCol = board.zeroIdxCol;
        q.add(copyBoard);
        set.add(copyBoard);

        /**
         * BFS 수행
         */
        while (!q.isEmpty()) {
            /**
             * 현재 값들은 tmp.~~~와 같이 사용하기에 변수명이 길어서 새로운 변수에 저장
             * 현재 값들을 조금 더 쉽게 사용할 수 있도록 curRow, curCol, curMoveCnt에 각각 저장
             */
            Board tmp = q.poll();
            /**
             * poll한 Board를 possible 함수로 아래의 표와 같은 형태로 만들어 져 있는지 확인
             * 아래와 같은 형태라면 이 Board를 만들기 까지의 moveCnt값을 리턴
             * 
             * 1 2 3
             * 4 5 6
             * 7 8 0
             */
            if (possble(tmp)) {
                return tmp.moveCnt;
            }
            int curRow = tmp.zeroIdxRow;
            int curCol = tmp.zeroIdxCol;
            int curMoveCnt = tmp.moveCnt;

            /**
             * 사방을 탐색하며 nextBoard에 복사한 이후 0이 이동할 방향의 위치에 있는 값과 swap
             * 이후 새롭게 만들어진 nextBoard가 set에 아직 저장되지 않은 값이라면 BFS를 수행해도 되는 Board
             * 따라서 Queue와 Set에 새롭게 add
             */
            for (int i = 0; i < 4; i++) {
                Board nextBoard = new Board();
                for (int j = 0; j < 3; j++) {
                    nextBoard.board[j] = Arrays.copyOf(tmp.board[j], 3);
                }
                int nextRow = curRow + dir[i][0];
                int nextCol = curCol + dir[i][1];
                int nextMoveCnt = curMoveCnt + 1;
                if (!check(nextRow, nextCol))
                    continue;
                int swapTmp = nextBoard.board[curRow][curCol];
                nextBoard.board[curRow][curCol] = nextBoard.board[nextRow][nextCol];
                nextBoard.board[nextRow][nextCol] = swapTmp;
                nextBoard.zeroIdxRow = nextRow;
                nextBoard.zeroIdxCol = nextCol;
                nextBoard.moveCnt = nextMoveCnt;

                if (!set.contains(nextBoard)) {
                    q.add(nextBoard);
                    set.add(nextBoard);
                }
            }
        }
        /**
         * Queue가 빌 때까지 possible 함수의 값이 true인 Board가 나오지 않았다면 가능한 Board가 없음
         * 따라서 -1을 리턴
         */
        return -1;
    }

    private static boolean possble(Board tmp) {
        for (int i = 0; i < 8; i++) {
            if (tmp.board[i / 3][i % 3] != i + 1)
                return false;
        }
        return true;
    }

    private static boolean check(int row, int col) {
        return row >= 0 && row < 3 && col >= 0 && col < 3;
    }
}
