import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    static int h1, w1, h2, w2;

    public static void main(String[] args) throws Exception {


        StringTokenizer st = new StringTokenizer(br.readLine(), " ");

        h1 = Integer.parseInt(st.nextToken());  // 첫번째 배치 height
        w1 = Integer.parseInt(st.nextToken());  // 첫번째 배치 width

        char[][] origin = new char[h1][w1]; // 첫번째 배치

        char[][] board1 = new char[30][30]; // 첫번째 배치 놓을 보드
        char[][] board2;    // 두번째 배치 놓을 보드

        int coin = 0;   // 첫번째 배치의 동전 개수

        for (int i = 0; i < h1; i++) {
            String input = br.readLine();
            for (int j = 0; j < w1; j++) {
                origin[i][j] = input.charAt(j);
                if (origin[i][j] == 'O')
                    coin++;
            }
        }

        st = new StringTokenizer(br.readLine(), " ");

        h2 = Integer.parseInt(st.nextToken());  // 두번째 배치 height
        w2 = Integer.parseInt(st.nextToken());  // 두번째 배치 width

        char[][] dest = new char[h2][w2];   // 두번째 배치

        for (int i = 0; i < h2; i++) {
            String input = br.readLine();
            for (int j = 0; j < w2; j++) {
                dest[i][j] = input.charAt(j);
            }
        }

        // 보드1에 첫번째 배치 놓기
        for (int i = 0; i < h1; i++) {
            for (int j = 0; j < w1; j++) {
                board1[h2 + i - 1][w2 + j - 1] = origin[i][j];
            }
        }

        int max = 0;

        // 두번째 배치를 보드2에 놓고 보드1과 겹치는 최대 동전수 찾기
        // 두번째 배치를 보드1의 오른쪽-아래의 1칸부터 겹치게 놓는다
        // 왼쪽-위로 두번째 배치 이동하면서 놓음
        for (int i = h1 + h2 - 2; i >= 0; i--) {
            for (int j = w1 + w2 - 2; j >= 0; j--) {
                board2 = copy(dest, i, j);  // 보드2에 두번째 배치 놓기
                max = Math.max(max, check(board1, board2));
            }
        }

        bw.write(Integer.toString(coin - max));
        bw.close();
        br.close();
    }

    static char[][] copy(char[][] dest, int row, int col) {
        // row, col 위치에 두번째 배치 놓고 리턴

        char[][] board = new char[30][30];
        for (int i = 0; i < h2; i++) {
            for (int j = 0; j < w2; j++) {
                board[row + i][col + j] = dest[i][j];
            }
        }
        return board;
    }

    static int check(char[][] origin, char[][] dest) {
        int count = 0;

        // 보드1과 보드2에서 겹치는 동전 개수 리턴
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                if (origin[i][j] == 'O' && dest[i][j] == 'O')
                    count++;
            }
        }
        return count;
    }
}