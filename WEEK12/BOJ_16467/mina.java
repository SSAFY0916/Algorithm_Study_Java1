import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    static long MOD = 100_000_007;

    public static void main(String[] args) throws Exception {

        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());
        while (T-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            int K = Integer.parseInt(st.nextToken());
            int N = Integer.parseInt(st.nextToken());


            if (K == 0) {   // 2의 N제곱을 MOD 연산함
                sb.append(power(N)).append("\n");
            } else if (N <= K) {    // K일이 안지나서 첫번째 알도 안태어남
                sb.append(1).append("\n");
            } else if (N == K + 1) {    // K일째라 첫번째 알만 태어남
                sb.append(2).append("\n");
            } else if (N == K + 2) {    // K+1일째라 병아리 2마리 태어남
                sb.append(3).append("\n");
            } else {
                int size = K + 2;
                int power = N - K - 2;

                // 2차원 행렬
                long[][] base = new long[size][size];

                base[size - 1][1] = 1;
                base[size - 1][size - 1] = 1;

                for (int i = 0; i < size - 1; i++) {
                    base[i][i + 1] = 1;
                }


                long[][] result = new long[size][size];

                result[size - 1][0] = 3;
                result[size - 2][0] = 2;

                for (int i = 0; i < size - 2; i++) {
                    result[i][0] = 1;
                }

                base = matrixPower(base, size, power);  // 행렬 거듭제곱
                result = matrixMultiplication(base, result, size);

                sb.append(result[size - 1][0]).append("\n");
            }
        }


        bw.write(sb.toString());

        bw.flush();
        bw.close();
        br.close();
    }

    static long power(long b) { // 2의 b제곱 MOD 연산
        long a = 2;
        long result = 1;
        while (b != 0) {
            if (b % 2 == 1) {
                result = (result * a) % MOD;
            }
            a = (a * a) % MOD;
            b /= 2;
        }
        return result;
    }

    static long[][] matrixMultiplication(long[][] a, long[][] b, int size) {    // 행렬 곱
        long[][] result = new long[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    long temp = (a[i][k] * b[k][j]) % MOD;
                    result[i][j] = (result[i][j] + temp) % MOD;
                }
            }
        }
        return result;
    }

    static long[][] matrixPower(long[][] base, int size, int power) {   // 행렬 거듭제곱
        long[][] result = new long[size][size];
        for (int i = 0; i < size; i++) {
            result[i][i] = 1;
        }
        while (power != 0) {
            if (power % 2 == 1) {
                result = matrixMultiplication(result, base, size);
            }
            base = matrixMultiplication(base, base, size);
            power /= 2;
        }
        return result;
    }
}
