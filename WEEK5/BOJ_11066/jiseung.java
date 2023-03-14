import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	static int k;
	static int[] nums, sums;
	static int[][] dp;
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		int t = Integer.parseInt(br.readLine());

		while(t-- > 0) {
			k = Integer.parseInt(br.readLine());
			// 각 챕터의 크기를 저장하는 배열
			nums = new int[k];
			// 각 챕터의 크기의 부분합을 저장하는 배열, i+1번째 인덱스에는 nums[0]부터 nums[i]까지의 합이 저장되어 있다.
			sums = new int[k+1];
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int i=0; i<k; i++) {
				nums[i] = Integer.parseInt(st.nextToken());
			}
			for(int i=1; i<=k; i++) {
				sums[i] = sums[i-1]+nums[i-1];
			}
			// d[i][j]에는 챕터i부터 챕터j까지 합치는 최소 비용을 저장
			dp = new int[k][k];
			for(int i=0; i<k; i++) {
				Arrays.fill(dp[i], Integer.MAX_VALUE); // 최대값으로 초기화
				dp[i][i] = 0; // 챕터 하나는 합치는데 비용이 들지 않는다.
			}
			for(int i=0; i<k; i++) { // 붙이는 챕터 수
				for(int j=0; j<k; j++) { // 붙일 챕터의 인덱스
					method(j, j+i); // 챕터를 붙이는 비용을 순서대로 탐색
				}
			}
			bw.write(dp[0][k-1] + "\n");
		}
		bw.flush();
		bw.close();
		br.close();
	}

	static void method(int start, int end) {
		if(end >= k) { // 입력받은 챕터 수보다 커지면 건너뜀
			return;
		}

		// [start][end]는 start부터 i까지 붙이는 비용 + (i+1)부터 end까지 붙이는 비용 + start에서 end까지의 부분합
		for(int i=start; i<end; i++) {
			dp[start][end] = Math.min(dp[start][end], dp[start][i] + dp[i+1][end] + sums[end+1] - sums[start]);
		}
	}
}