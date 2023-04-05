import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

	public static void main(String[] args) throws Exception {

		int N = Integer.parseInt(br.readLine());

		int[] time = new int[N + 1]; // 상담에 소요되는 날짜
		int[] salary = new int[N + 1]; // 그 상담을 끝냈을 때 받는 돈
		int[] dp = new int[N + 51]; // 마지막날(N) + 최대 상담일 수(50)

		for (int i = 1; i <= N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			time[i] = Integer.parseInt(st.nextToken());
			salary[i] = Integer.parseInt(st.nextToken());
		}

		for (int i = 1; i <= N; i++) {

			// 3가지 중 최댓값 찾음
			// 1. 이미 계산 된 dp[i + time[i] - 1] (이전에 2, 3으로 이미 계산된 값이 들어있을 경우)
			// 2. i + time[i]까지 상담안함 : 전날까지 번돈(dp[i + time[i] - 2])) 그대로 유지
			// 3. i + time[i]까지 상담함 : i번째 날까지 번 돈의 최댓값에 그 날 할 수 있는 상담해서 돈 벌기(dp[i - 1] +
			// salary[i]))
			dp[i + time[i] - 1] = Math.max(Math.max(dp[i + time[i] - 1], dp[i + time[i] - 2]), dp[i - 1] + salary[i]);

			// 전날까지 번돈 vs 오늘까지 번돈 중 최댓값
			dp[i] = Math.max(dp[i - 1], dp[i]);
		}

		bw.write(Integer.toString(dp[N]));

		bw.close();
	}

}
