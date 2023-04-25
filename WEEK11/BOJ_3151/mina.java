import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));


    public static void main(String[] args) throws Exception {
        long result = 0;

        long[] nums = new long[20001];

        int N = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            int n = Integer.parseInt(st.nextToken());
            nums[n + 10000]++; // 빈도수 count (-10000 ~ 10000 이므로 n+10000)
        }

        // 음수, 양수, 양수 조합
        for (int i = 2; i < 10001; i++) {   // i : 음수
            for (int j = 1; j < i / 2 + 1; j++) {
                int k = i - j;  // j, k: 양수

                if (j == k) {   // j와 k가 같은 수 인 경우 그 수의 개수 안에서 2개 뽑기
                    result += nums[10000 - i] * nums[j + 10000] * (nums[j + 10000] - 1) / 2;
                } else {    // j와 k가 다른 수 인 경우 각각의 경우의 수 곱하기
                    result += nums[10000 - i] * nums[j + 10000] * nums[k + 10000];
                }
            }
        }

        // 양수, 음수, 음수 조합
        for (int i = 2; i < 10001; i++) { // i : 양수
            for (int j = 1; j < i / 2 + 1; j++) {
                int k = i - j;  // j, k : 음수

                if (j == k) {   // j와 k가 같은 수 인 경우 그 수의 개수 안에서 2개 뽑기
                    result += nums[i + 10000] * nums[10000 - j] * (nums[10000 - j] - 1) / 2;
                } else {    // j와 k가 다른 수 인 경우 각각의 경우의 수 곱하기
                    result += nums[i + 10000] * nums[10000 - j] * nums[10000 - k];
                }
            }
        }

        // 음수, 0, 양수 조합
        for (int i = 1; i < 10001; i++) {
            // 음수의 경우의 수 * 0의 경우의 수 * 양수의 경우의 수
            result += nums[10000 - i] * nums[10000] * nums[i + 10000];
        }

        // 0으로만 이루어진 조합
        result += nums[10000] * (nums[10000] - 1) * (nums[10000] - 2) / 6;

        bw.write(Long.toString(result));
        bw.flush();
        bw.close();
        br.close();
    }

}
