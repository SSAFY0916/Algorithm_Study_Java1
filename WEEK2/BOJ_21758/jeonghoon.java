import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int len = Integer.parseInt(br.readLine());

        int[] arr = new int[len + 1];

        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 1; i <= len; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        int[] sumArr = new int[len + 1];

        for (int i = 1; i <= len; i++) {
            sumArr[i] = sumArr[i - 1] + arr[i];
        }

        int max = 0;

        // 벌통이 오른쪽에 있을 경우
        for (int i = 2; i < len; i++) {
            int tmp = (sumArr[len] - arr[1] - arr[i]) + (sumArr[len] - sumArr[i]);
            max = Math.max(max, tmp);
        }
        // 벌통이 가운데에 있을 경우
        for (int i = 2; i < len; i++) {
            int tmp = (sumArr[i] - arr[1]) + (sumArr[len - 1] - sumArr[i - 1]);
            max = Math.max(max, tmp);
        }
        // 벌통이 왼쪽에 있을 경우
        for (int i = 2; i < len; i++) {
            int tmp = (sumArr[i - 1]) + (sumArr[len - 1] - arr[i]);
            max = Math.max(max, tmp);
        }

        System.out.println(max);
    }
}