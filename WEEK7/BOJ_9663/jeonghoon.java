import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    static int res;
    static int len;
    static int board[];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        len = Integer.parseInt(br.readLine());

        board = new int[len];

        recursive(0);

        System.out.println(res);
    }

    private static void recursive(int cnt) {
        if (cnt == len) {
            res++;
            return;
        }

        boolean flag = true;

        /**
         * (y, x) => (cnt, i) 위치에 놓을 수 있는지 확인
         */
        for (int i = 0; i < len; i++) { // X축
            flag = true;
            for (int j = 0; j < cnt; j++) { // Y 축
                /**
                 * 현재 j열에 놓인 말이 i행에 있거나
                 * (cnt, i)와 (j, j열에 놓인 말의 행)이 대각선 위치에 있다면
                 * 불가능
                 */
                if (i == board[j] || Math.abs(cnt - j) == Math.abs(i - board[j])) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                board[cnt] = i;
                recursive(cnt + 1);
            }
        }
    }
}