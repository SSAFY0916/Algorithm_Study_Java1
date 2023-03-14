import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		int[] nums = new int[n];
		st = new StringTokenizer(br.readLine());
		for(int i=0; i<n; i++) {
			nums[i] = Integer.parseInt(st.nextToken()) % m; // m으로 나눈 나머지를 저장
		}
		long[] dp = new long[m]; // i번째에는 m으로 나눴을 때 나머지가 i인 부분합들의 개수가 저장
		int index = 0; // 배열을 회전시켜야하는데 뺏다넣는 작업을 직접하면 오래 걸리니까 인덱스를 조절
		long answer = 0;
		for(int i=0; i<n; i++) { // [0, i], [1, i], ..., [i, i]의 부분합을 계산
			index = (index + m-nums[i]) % m; // 현재 숫자가 더해진다면 나머지들이 바뀌는걸 인덱스에 반영
			dp[(index+nums[i])%m]++; // i번째 숫자 하나만 속하는 부분합을 적용
			answer += dp[index]; // 개수 증가
		}
		
		bw.write(answer + "\n");
		bw.flush();
		bw.close();
		br.close();
	}
}