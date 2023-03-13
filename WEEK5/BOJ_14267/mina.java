import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

	public static void main(String args[]) throws Exception {
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		StringBuilder sb = new StringBuilder();
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());

		int[] emp = new int[n + 1]; // 직속상사 번호 저장
		int[] compliment = new int[n + 1];   // n번 사원이 받은 칭찬
		int[] dp = new int[n + 1];

		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 1; i < n + 1; i++) {
			emp[i] = Integer.parseInt(st.nextToken());
		}

		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int idx = Integer.parseInt(st.nextToken());
			int p = Integer.parseInt(st.nextToken());
			compliment[idx] += p;   // 같은 사원을 여러번 칭찬 할 수 있음
		}

		emp[1] = 0;

		for (int i = 2; i < n + 1; i++) {
			dp[i] = dp[emp[i]] + compliment[i]; // 내 상사가 받은 누적 칭찬 + 내가 받은 칭찬
		}

		for (int i = 1; i < n + 1; i++) {
			sb.append(dp[i]).append(" ");
		}
		bw.write(sb.toString());
		bw.close();

	}
}