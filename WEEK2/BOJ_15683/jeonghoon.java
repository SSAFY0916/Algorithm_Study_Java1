import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Main {
    static char[][] board;
    static int rowLen, colLen;
    static LinkedList<Pair> cctvPosList = new LinkedList<>();
    static int min = 0x7fffffff;

    static class Pair {
        int row;
        int col;

        Pair(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    public static void solve(int cnt, char[][] parmBoard) {
        if (cnt == cctvPosList.size()) {
            int showArea = 0;

            for (int i = 0; i < rowLen; i++) {
                for (int j = 0; j < colLen; j++) {
                    if (parmBoard[i][j] == '0')
                        showArea++;
                }
            }
            if (showArea < min)
                min = showArea;
            return;
        }

        int[][] direct = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };

        int row = cctvPosList.get(cnt).row;
        int col = cctvPosList.get(cnt).col;
        char cctvType = board[row][col];

        char[][] copyBoard = new char[rowLen][colLen];

        switch (cctvType) {
            case '1':
                for (int i = 0; i < 4; i++) {
                    int nextRow = row;
                    int nextCol = col;

                    for (int j = 0; j < rowLen; j++) {
                        copyBoard[j] = Arrays.copyOf(parmBoard[j], parmBoard[j].length);
                    }

                    while (((nextRow + direct[i][0]) >= 0) && ((nextRow + direct[i][0]) < rowLen) &&
                            ((nextCol + direct[i][1]) >= 0) && ((nextCol + direct[i][1]) < colLen) &&
                            board[nextRow + direct[i][0]][nextCol + direct[i][1]] != '6') {
                        nextRow += direct[i][0];
                        nextCol += direct[i][1];
                        if (board[nextRow][nextCol] != '0')
                            continue;
                        copyBoard[nextRow][nextCol] = '#';
                    }
                    solve(cnt + 1, copyBoard);
                }
                break;
            case '2':
                for (int i = 0; i < 2; i++) {
                    int nextRow = row;
                    int nextCol = col;

                    for (int j = 0; j < rowLen; j++) {
                        copyBoard[j] = Arrays.copyOf(parmBoard[j], parmBoard[j].length);
                    }

                    while (((nextRow + direct[i][0]) >= 0) && ((nextRow + direct[i][0]) < rowLen) &&
                            ((nextCol + direct[i][1]) >= 0) && ((nextCol + direct[i][1]) < colLen) &&
                            board[nextRow + direct[i][0]][nextCol + direct[i][1]] != '6') {
                        nextRow += direct[i][0];
                        nextCol += direct[i][1];
                        if (board[nextRow][nextCol] != '0')
                            continue;
                        copyBoard[nextRow][nextCol] = '#';
                    }
                    nextRow = row;
                    nextCol = col;
                    while (((nextRow + direct[i + 2][0]) >= 0) && ((nextRow + direct[i + 2][0]) < rowLen) &&
                            ((nextCol + direct[i + 2][1]) >= 0) && ((nextCol + direct[i + 2][1]) < colLen) &&
                            board[nextRow + direct[i + 2][0]][nextCol + direct[i + 2][1]] != '6') {
                        nextRow += direct[i + 2][0];
                        nextCol += direct[i + 2][1];
                        if (board[nextRow][nextCol] != '0')
                            continue;
                        copyBoard[nextRow][nextCol] = '#';
                    }
                    solve(cnt + 1, copyBoard);
                }
                break;
            case '3':
                for (int i = 0; i < 4; i++) {
                    int nextRow = row;
                    int nextCol = col;

                    for (int j = 0; j < rowLen; j++) {
                        copyBoard[j] = Arrays.copyOf(parmBoard[j], parmBoard[j].length);
                    }

                    while (((nextRow + direct[i][0]) >= 0) && ((nextRow + direct[i][0]) < rowLen) &&
                            ((nextCol + direct[i][1]) >= 0) && ((nextCol + direct[i][1]) < colLen) &&
                            board[nextRow + direct[i][0]][nextCol + direct[i][1]] != '6') {
                        nextRow += direct[i][0];
                        nextCol += direct[i][1];
                        if (board[nextRow][nextCol] != '0')
                            continue;
                        copyBoard[nextRow][nextCol] = '#';
                    }
                    nextRow = row;
                    nextCol = col;
                    while (((nextRow + direct[(i + 1) % 4][0]) >= 0) && ((nextRow + direct[(i + 1) % 4][0]) < rowLen) &&
                            ((nextCol + direct[(i + 1) % 4][1]) >= 0) && ((nextCol + direct[(i + 1) % 4][1]) < colLen)
                            &&
                            board[nextRow + direct[(i + 1) % 4][0]][nextCol + direct[(i + 1) % 4][1]] != '6') {
                        nextRow += direct[(i + 1) % 4][0];
                        nextCol += direct[(i + 1) % 4][1];
                        if (board[nextRow][nextCol] != '0')
                            continue;
                        copyBoard[nextRow][nextCol] = '#';
                    }
                    solve(cnt + 1, copyBoard);
                }
                break;
            case '4':
                for (int i = 0; i < 4; i++) {
                    int nextRow = row;
                    int nextCol = col;

                    for (int j = 0; j < rowLen; j++) {
                        copyBoard[j] = Arrays.copyOf(parmBoard[j], parmBoard[j].length);
                    }

                    while (((nextRow + direct[i][0]) >= 0) && ((nextRow + direct[i][0]) < rowLen) &&
                            ((nextCol + direct[i][1]) >= 0) && ((nextCol + direct[i][1]) < colLen) &&
                            board[nextRow + direct[i][0]][nextCol + direct[i][1]] != '6') {
                        nextRow += direct[i][0];
                        nextCol += direct[i][1];
                        if (board[nextRow][nextCol] != '0')
                            continue;
                        copyBoard[nextRow][nextCol] = '#';
                    }
                    nextRow = row;
                    nextCol = col;
                    while (((nextRow + direct[(i + 1) % 4][0]) >= 0) && ((nextRow + direct[(i + 1) % 4][0]) < rowLen) &&
                            ((nextCol + direct[(i + 1) % 4][1]) >= 0) && ((nextCol + direct[(i + 1) % 4][1]) < colLen)
                            &&
                            board[nextRow + direct[(i + 1) % 4][0]][nextCol + direct[(i + 1) % 4][1]] != '6') {
                        nextRow += direct[(i + 1) % 4][0];
                        nextCol += direct[(i + 1) % 4][1];
                        if (board[nextRow][nextCol] != '0')
                            continue;
                        copyBoard[nextRow][nextCol] = '#';
                    }
                    nextRow = row;
                    nextCol = col;
                    while (((nextRow + direct[(i + 2) % 4][0]) >= 0) && ((nextRow + direct[(i + 2) % 4][0]) < rowLen) &&
                            ((nextCol + direct[(i + 2) % 4][1]) >= 0) && ((nextCol + direct[(i + 2) % 4][1]) < colLen)
                            &&
                            board[nextRow + direct[(i + 2) % 4][0]][nextCol + direct[(i + 2) % 4][1]] != '6') {
                        nextRow += direct[(i + 2) % 4][0];
                        nextCol += direct[(i + 2) % 4][1];
                        if (board[nextRow][nextCol] != '0')
                            continue;
                        copyBoard[nextRow][nextCol] = '#';
                    }
                    solve(cnt + 1, copyBoard);
                }
                break;
            case '5':
                int nextRow = row;
                int nextCol = col;

                for (int j = 0; j < rowLen; j++) {
                    copyBoard[j] = Arrays.copyOf(parmBoard[j], parmBoard[j].length);
                }

                while (((nextRow + direct[0][0]) >= 0) && ((nextRow + direct[0][0]) < rowLen)
                        && ((nextCol + direct[0][1]) >= 0) && ((nextCol + direct[0][1]) < colLen)
                        && board[nextRow + direct[0][0]][nextCol + direct[0][1]] != '6') {
                    nextRow += direct[0][0];
                    nextCol += direct[0][1];
                    if (board[nextRow][nextCol] != '0')
                        continue;
                    copyBoard[nextRow][nextCol] = '#';
                }
                nextRow = row;
                nextCol = col;
                while (((nextRow + direct[1][0]) >= 0) && ((nextRow + direct[1][0]) < rowLen)
                        && ((nextCol + direct[1][1]) >= 0) && ((nextCol + direct[1][1]) < colLen)
                        && board[nextRow + direct[1][0]][nextCol + direct[1][1]] != '6') {
                    nextRow += direct[1][0];
                    nextCol += direct[1][1];
                    if (board[nextRow][nextCol] != '0')
                        continue;
                    copyBoard[nextRow][nextCol] = '#';
                }
                nextRow = row;
                nextCol = col;
                while (((nextRow + direct[2][0]) >= 0) && ((nextRow + direct[2][0]) < rowLen)
                        && ((nextCol + direct[2][1]) >= 0) && ((nextCol + direct[2][1]) < colLen)
                        && board[nextRow + direct[2][0]][nextCol + direct[2][1]] != '6') {
                    nextRow += direct[2][0];
                    nextCol += direct[2][1];
                    if (board[nextRow][nextCol] != '0')
                        continue;
                    copyBoard[nextRow][nextCol] = '#';
                }
                nextRow = row;
                nextCol = col;
                while (((nextRow + direct[3][0]) >= 0) && ((nextRow + direct[3][0]) < rowLen)
                        && ((nextCol + direct[3][1]) >= 0) && ((nextCol + direct[3][1]) < colLen)
                        && board[nextRow + direct[3][0]][nextCol + direct[3][1]] != '6') {
                    nextRow += direct[3][0];
                    nextCol += direct[3][1];
                    if (board[nextRow][nextCol] != '0')
                        continue;
                    copyBoard[nextRow][nextCol] = '#';
                }
                solve(cnt + 1, copyBoard);
                break;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        rowLen = Integer.parseInt(st.nextToken());
        colLen = Integer.parseInt(st.nextToken());
        board = new char[rowLen][colLen];

        for (int i = 0; i < rowLen; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < colLen; j++) {
                board[i][j] = st.nextToken().charAt(0);
                if (board[i][j] != '0' && board[i][j] != '6') {
                    cctvPosList.add(new Pair(i, j));
                }
            }
        }

        solve(0, board);

        System.out.println(min);
    }
}