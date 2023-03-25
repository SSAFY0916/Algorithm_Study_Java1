import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    static int N, K;

    public static void main(String[] args) throws Exception {

        N = Integer.parseInt(br.readLine());
        K = Integer.parseInt(br.readLine());

        long start = 1;
        long end = K;
        long result = 0;

        while (start <= end) {
            long middle = (start + end) / 2;
            long total = 0;

            // 1부터 N번째 열에서 각 열의 middle 이하의 숫자의 개수를 전부 합함
            for (int i = 1; i <= N; i++) {
                total += Math.min(middle / i, N);
            }

            if (total < K)  // 그 개수가 K개가 안될때 - 범위 옮기기
                start = middle + 1;
            else {
                end = middle - 1;
                result = middle;
            }
        }

        bw.write(Long.toString(result));

        bw.close();
    }

}
