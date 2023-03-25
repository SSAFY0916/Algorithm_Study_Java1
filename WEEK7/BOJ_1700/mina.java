import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;


public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    static int N, K;

    public static void main(String[] args) throws Exception {

        StringTokenizer st = new StringTokenizer(br.readLine(), " ");

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        int[] electrics = new int[K + 1];   // 전자제품 순서
        boolean[] plug = new boolean[K + 1];    // 꽂혀있는 플러그 표시
        boolean[] check = new boolean[K + 1];   // 안쓰거나 제일 나중에 쓰는 플러그 찾을때 씀
        int plugCount = 0;  // 사용중인 콘센트 개수
        int result = 0; // 플러그 빼는 횟수

        st = new StringTokenizer(br.readLine(), " ");
        for (int i = 1; i <= K; i++) {
            electrics[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 1; i <= K; i++) {
            if (plug[electrics[i]]) // 이미 꽂혀있음
                continue;

            if (plugCount < N) {  // 빈자리가 있어서 꽂을 수 있음
                plug[electrics[i]] = true;
                plugCount++;
                continue;
            }

            int index = -1;
            Arrays.fill(check, false);
            for (int j = i + 1; j <= K; j++) {  // 꽂혀있는 플래그 중 앞으로 사용 안하는 플래그 찾기
                check[electrics[j]] = true; // 앞으로 사용 예정인 플러그 표시
            }\
            for (int j = 1; j <= K; j++) {
                if (plug[electrics[j]] && !check[electrics[j]]) {   //꽂혀있으면서 사용예정 아닌 플러그
                    index = electrics[j];
                    break;
                }
            }

            if (index != -1) {  // 안쓰는 플러그가 있음
                plug[index] = false;
                plug[electrics[i]] = true;
                result++;
                continue;
            }


            Arrays.fill(check, false);
            for (int j = i + 1; j <= K; j++) {  // 꽂혀있는 플래그 중 제일 나중에 사용하는 플래그 찾기
                if (!check[electrics[j]] && plug[electrics[j]]) {
                    check[electrics[j]] = true; // 하나의 플래그가 여러번 사용될 경우 그 중 먼저 쓰는 플래그를 기준으로 함
                    index = electrics[j];
                }
            }

            if (index != -1) {  // 제일 나중에 쓰는 플러그 찾음
                plug[index] = false;
                plug[electrics[i]] = true;
                result++;
                continue;
            }

            if (index == -1 && i == K)  // 맨 마지막 플러그
                result++;
        }

        bw.write(Integer.toString(result));

        bw.close();
    }

}
