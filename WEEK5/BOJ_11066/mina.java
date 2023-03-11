import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

	static int[] sum;

	public static void main(String[] args) throws Exception {

		int t = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();

		while (t-- > 0) {
			int n = Integer.parseInt(br.readLine());
			int[] chapters = new int[n + 1];
			sum = new int[n + 1]; // 누적함 저장
			int[][] dp = new int[n + 1][n + 1]; // dp[i][j] = i부터 j까지 합치는데 드는 최소 비용
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			for (int i = 1; i < n + 1; i++) {
				chapters[i] = Integer.parseInt(st.nextToken());
				sum[i] = sum[i - 1] + chapters[i];
			}
			for (int i = 1; i < n; i++) {
				dp[i][i + 1] = chapters[i] + chapters[i + 1];
			}
			for (int i = 2; i < n + 1; i++) {
				for (int j = 1; i + j < n + 1; j++) {
					for (int k = j; k < i + j; k++) {

						// a to k 까지의 최소비용을 구한다고 하면
						// a to b + c to k
						// a to c + d to k
						// a to d + e to k
						// a to e + f to k
						// ...
						// a to i + j to k
						// 중 최솟값을 구함

						if (dp[j][i + j] == 0) { // j to i + j 까지의 첫 비용 -> (j to j+1) + (j+2 to i+j)
							dp[j][i + j] = dp[j][k] + dp[k + 1][i + j] + getSum(j, i + j); // getSum은 dp[j][k] + dp[k + 1][i + j] 합치는데 드는 비용 
						} else { // 한칸씩 이동하면서 최솟값 구해주기
							dp[j][i + j] = Math.min(dp[j][i + j], dp[j][k] + dp[k + 1][i + j] + getSum(j, i + j));
						}
					}
				}
			}

			sb.append(dp[1][n]).append("\n");
		}
		bw.write(sb.toString());
		bw.close();
	}

	static int getSum(int start, int end) {
		if (start == 0) {
			return sum[end];
		}

		return sum[end] - sum[start - 1];
	}
}
