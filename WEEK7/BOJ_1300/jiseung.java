import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        long n = Integer.parseInt(br.readLine());
        long k = Integer.parseInt(br.readLine());

        // 1부터 n*n 사이를 이분탐색
        long left = 1;
        long right = n*n;
        while(left < right) {
            long mid = (left + right) / 2;
            long count = 0; // A 에 있는 mid 이하 숫자들의 개수
            for (int i = 1; i <= n; i++) {
                if (mid < i) break; // 모두 mid 보다 클테니까 그만
                count += Math.min(n, mid / i); // i 번째 열에서 mid 이하의 숫자의 개수
            }
            if(count < k) { // 원하는 숫자가 더 크다. 원하는 숫자보다 적다.
                left = mid+1;
            }else { // 원하는 숫자가 더 작다. 원하는 숫자보다 많다.
                right = mid;
            }
        }

        bw.write(left + "\n");
        bw.flush();
        bw.close();
        br.close();
    }
}