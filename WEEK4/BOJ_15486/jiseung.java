import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br;
    static BufferedWriter bw;
    static StringTokenizer st;
    static int n;
    static int[] dp;
    static int[][] nums;
    public static void main(String[] args) throws Exception{
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        n = Integer.parseInt(br.readLine());

        nums = new int[n][2];
        for(int i=0; i<n; i++) {
            st = new StringTokenizer(br.readLine());
            nums[i][0] = Integer.parseInt(st.nextToken())-1;
            nums[i][1] = Integer.parseInt(st.nextToken());
        }

        dp = new int[n+1]; // 
        for(int i=n-1; i>=0; i--) {
            if(i + nums[i][0] >= n) {
                dp[i] = dp[i+1];
                continue;
            }
            dp[i] = Math.max(dp[i+1], dp[i+1 + nums[i][0]] + nums[i][1]);
        }
        bw.write(dp[0] + "\n");
        bw.flush();
        bw.close();
        br.close();
    }
}