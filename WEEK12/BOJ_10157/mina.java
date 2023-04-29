import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws Exception {

        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine());

        int C = Integer.parseInt(st.nextToken());
        int R = Integer.parseInt(st.nextToken());

        int K = Integer.parseInt(br.readLine());

        // 사방 탐색용
        int[] dx = {1, 0, -1, 0};
        int[] dy = {0, 1, 0, -1};

        // 자리
        boolean[][] seat = new boolean[R + 1][C + 1];

        if (C * R < K) {    // 자리 개수보다 사람이 더 많음
            sb.append(0);
        } else {
            int n = 1;  // 자리 번호
            int x = 1;  // x 좌표
            int y = 1;  // y 좌표
            int d = 0;  // 방향
            seat[x][y] = true;  // 첫 자리 배정
            while (n != K) {
                // 다음 자리
                int nx = x + dx[d];
                int ny = y + dy[d];

                if (nx < 1 || nx > R || ny < 1 || ny > C || seat[nx][ny]) { // 범위 벗어나거나 이미 앉은 자리

                    //방향 전환하고 새로운 자리 찾음
                    d = (d + 1) % 4;
                    nx = x + dx[d];
                    ny = y + dy[d];
                }

                seat[nx][ny] = true;
                x = nx;
                y = ny;
                n++;
            }
            sb.append(y).append(" ").append(x);
        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
}
