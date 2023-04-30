import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
//장은 연속되어야한다
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    public static void main(String[] args) throws IOException {
        int t = Integer.parseInt(br.readLine());

        for(int tc=0;tc<t;tc++){
            int k;//장의 수
            int[] novel;//각 장인듯
            int[] sum;//i까지 구간합
            int[][] dp;

            k = Integer.parseInt(br.readLine());//장의 수
            novel = new int[k+1];
            dp = new int[k+1][k+1];
            sum = new int[k+1];

            st = new StringTokenizer(br.readLine());
            for(int i=1;i<=k;i++){
                novel[i] = Integer.parseInt(st.nextToken());
                sum[i] = sum[i-1]+novel[i];
            }

            for(int n=1;n<=k;n++){//장 순회
                for(int from=1;from+n<=k;from++){//??
                    int to = from+n;//??

                    dp[from][to] = Integer.MAX_VALUE;
                    for(int divide = from; divide<to ; divide++){
                        dp[from][to] = Math.min(dp[from][to],
                                            dp[from][divide]+dp[divide+1][to]
                                            +sum[to]-sum[from-1]);
                    }

                }
            }
            System.out.println(dp[1][k]);
        }
    }
}