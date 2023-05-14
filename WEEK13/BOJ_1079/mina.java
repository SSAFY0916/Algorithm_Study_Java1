import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    static int[] guilty;

    static int[][] R;

    static int mafia, N, ret;

    public static void main(String[] args) throws Exception {

        N = Integer.parseInt(br.readLine());
        guilty = new int[N];    // 유죄 지수
        R = new int[N][N];  // 참가자들 반응
        ret = 0;    // 정답

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            guilty[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                R[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        mafia = Integer.parseInt(br.readLine());    // 마피아 번호

        game(N, 0, 0);

        bw.write(Integer.toString(ret));

        bw.flush();
        bw.close();
        br.close();
    }

    static void game(int people, int dead, int night) {
        // people : 살아남은 사람 수, dead : 죽은 사람들 비트마스킹, night : 지나간 밤의 수

        if (people == 1) {  // 마피아만 살아남은 경우 - 제일 오랫동안 게임 함
            ret = Math.max(ret, night);
            return;
        }

        if (people % 2 == 0) { // 밤 - 순서대로 한명 고름, 유죄 지수 바뀜

            for (int i = 0; i < N; i++) {   // 순서대로 고르기

                if (i == mafia || (1 << i & dead) != 0) {   // 이미 죽은사람과 마피아는 고르지 않기
                    continue;
                }

                for (int j = 0; j < N; j++) {   // 유죄지수 바뀜
                    guilty[j] = guilty[j] + R[i][j];
                }

                game(people - 1, 1 << i | dead, night + 1); // i번째 사람 죽이고 재귀 호출

                for (int j = 0; j < N; j++) {   // 유죄지수 되돌리기
                    guilty[j] = guilty[j] - R[i][j];
                }
            }

        } else {    // 낮 - 유죄지수로 고름
            int criminal = Integer.MIN_VALUE;
            int idx = -1;
            for (int i = 0; i < N; i++) {
                if ((1 << i & dead) != 0)   // 이미 죽은사람은 고르지 않기
                    continue;

                if (criminal < guilty[i]) { // 유죄지수가 가장 큰 사람 찾기
                    criminal = guilty[i];
                    idx = i;
                }
            }

            if (mafia == idx) { // 마피아가 선택 된 경우 게임 종료
                ret = Math.max(ret, night);
                return;
            } else {    // 선택 된 시민 죽이고 재귀 호출
                game(people - 1, 1 << idx | dead, night);
            }
        }
    }

}
