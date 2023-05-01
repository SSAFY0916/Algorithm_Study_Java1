import java.io.*;
import java.util.*;

public class Main {

    // 행렬들의 거듭제곱을 저장하는 리스트
    static List<int[][]> matrices = new ArrayList<>();
    // 2의 거듭제곱들을 저장하는 배열
    static int[] power2ns = new int[31];
    static int k, n;
    static int p = 100_000_007;
    
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int t = Integer.parseInt(br.readLine());
        power2ns[0] = 1;
        for (int i = 1; i < 31; i++) {
            power2ns[i] = power2ns[i - 1] * 2;
        }
        while(t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            k = Integer.parseInt(st.nextToken());
            n = Integer.parseInt(st.nextToken());
            if (k == 0) { // 알이 바로 부화할 때 -> 2^n 구하기
                long answer = 1;
                long temp = 2;
                while(n > 0) {
                    if (n % 2 == 1) {
                        answer = answer * temp % p;
                    }
                    temp = temp * temp % p;
                    n >>= 1;
                }
                bw.write(answer + "\n");
            } else { // 알이 바로 부화하지 않을 때
                int count = init();
                int[][] answer = new int[k + 1][1]; // k+1개의 날짜의 병아리들의 수를 저장하는 배열
                answer[0][0] = 1; // 0일에는 1마리의 병아리가 있으니까 0을 1로 초기화
                while (count >= 0) {
                    if (n >= power2ns[count]) {
                        n -= power2ns[count];
                        answer = mul(matrices.get(count), answer); // 행렬의 거듭제곱을 곱해서 병아리의 수를 구함
                    }
                    count--;
                }
                bw.write(answer[0][0] + "\n");
            }
        }
        bw.flush();
        bw.close();
        br.close();
    }

    // 행렬의 거듭제곱들을 구하는 메소드
    static int init() {
        matrices.clear(); // 이전 테스트케이스에서 사용한 리스트 초기화
        int[][] matrix = new int[k+1][k+1]; // 초기 행렬
        matrix[0][0] = 1;
        matrix[0][k] = 1;
        for (int i = 1; i < k+1; i++) {
            matrix[i][i-1] = 1;
        }
        matrices.add(matrix);

        int count = 0;
        while (n >= power2ns[count+1]) { // 2의 거듭제곱이 n보다 커질때까지 행렬의 거듭제곱을 구해서 리스트에 저장 
            matrices.add(mul(matrices.get(matrices.size() - 1), matrices.get(matrices.size() - 1)));
            count++;
        }
        return count; // 2의 몇제곱까지 갔었는지 반환, 2^count가 n보다 작거나 같은 가장 큰 2의 거듭제곱이다
    }

    // 두 개의 행렬을 받아서 행렬 곱셈을 수행하는 메소드
    static int[][] mul(int[][] a, int[][] b) {
        int[][] ret = new int[a.length][b[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                for (int l = 0; l < b[0].length; l++) {
                    ret[i][l] += ((long) a[i][j] * b[j][l]) % p;
                    ret[i][l] %= p;
                }
            }
        }
        return ret;
    }
}
