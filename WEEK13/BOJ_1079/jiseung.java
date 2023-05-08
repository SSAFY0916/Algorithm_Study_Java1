import java.io.*;
import java.util.*;

public class Main {

    static int n, num, answer;
    static int[][] reacts;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] scores = new int[n];
        for (int i = 0; i < n; i++) {
            scores[i] = Integer.parseInt(st.nextToken());
        }
        reacts = new int[n][n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                reacts[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        num = Integer.parseInt(br.readLine());

        int suspect = -1; // 낮에 죽을 사람
        if (n % 2 == 1) { // 홀수면 낮부터 시작
            for (int i = 0; i < n; i++) {
                if (suspect < 0 || scores[suspect] < scores[i]) { // 유죄지수가 높은 사람이 죽음
                    suspect = i;
                }
            }
        }
        permutation(1, 1<<suspect, scores);

        bw.write(answer + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    static void permutation(int count, int flag, int[] scores) {
        for (int i = 0; i < n; i++) { // 밤에 마피아가 죽일 사람 고르기
            if (i == num || (flag & (1 << i)) != 0) // 은진이거나 이미 죽은 사람은 건너뜀
                continue;

            for (int j = 0; j < n; j++) { // i번째 사람을 죽인 유죄 지수 반영하기
                scores[j] += reacts[i][j];
            }
            
            int suspect = -1; // 낮에 죽을 사람
            for (int j = 0; j < n; j++) {
                if (i == j || (flag & (1 << j)) != 0) // 직전 밤에 죽엇거나 이미 죽은 사람이면 건너 뜀
                    continue;
                if (suspect < 0 || scores[suspect] < scores[j]) { // 유죄지수가 높은 사람이 죽음
                    suspect = j;
                }
            }
            
            if (suspect == num) // 은진이가 죽으면 여기까지 탐색
                answer = Math.max(answer, count);
            else // 은진이가 살아있으면 한 턴 더 진행
                permutation(count + 1, flag | (1 << i) | (1 << suspect), scores);

            for (int j = 0; j < n; j++) { // 밤에 죽일 사람 다시 고르기 위해 유죄 지수 되돌리기
                scores[j] -= reacts[i][j];
            }
        }
    }
}
