import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int C = Integer.parseInt(st.nextToken());
        int R = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(br.readLine());

        int[][] seats = new int[R][C];

        int[] dix = {-1, 0, 1, 0};
        int[] diy = {0, 1, 0, -1};

        int x =R-1;
        int y = 0;
        int dir = 0;
        if (K > R * C) {
            System.out.println(0);
            return;
        }
        for (int i = 1; i <= C * R; i++) {
            seats[x][y] = i;
            if (i == K) {
                System.out.printf("%d %d%n", y+1, R-x);
                return;
            }
            int nx = x + dix[dir];
            int ny = y + diy[dir];
            if (nx < 0 || nx >= R || ny < 0 || ny >= C|| seats[nx][ny] !=0) {
                dir = (dir + 1)%4;
                nx = x + dix[dir];
                ny = y + diy[dir];
            }
            x = nx;
            y = ny;

        }
        System.out.println(0);

    }


}