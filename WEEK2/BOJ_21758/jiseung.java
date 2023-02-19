import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		int n = Integer.parseInt(br.readLine());
		int[] nums = new int[n]; // 꿀의 양을 저장하는 배열
		StringTokenizer st = new StringTokenizer(br.readLine());
		int sum = 0; // 꿀의 총 양
		int max = 0;
		for(int i=0; i<n; i++) {
			nums[i] = Integer.parseInt(st.nextToken());
			sum += nums[i];
			if(i != 0 && i != n-1) {
				max = Math.max(max, nums[i]);
			}
		}
    // 벌통이 양 끝이 아닌 곳에 있는 경우, 벌이 양 끝에서 시작하는 것이 최대가 되고 벌통은 꿀이 최대로 많은 곳에 두어야 한다.
		int answer = sum - nums[0] - nums[n-1] + max;
		int temp = 0;
		for(int i=1; i<n-1; i++) { // 벌통이 가장 왼쪽에 있는 경우, 한 마리는 오른쪽끝에 고정하고 다른 한마리 위치를 찾는다.
			temp += nums[i-1];
			answer = Math.max(answer, sum - nums[n-1] + temp - nums[i]);
		}
		temp = 0;
		for(int i=n-2; i>=1; i--) { // 벌통이 가장 오른쪽에 있는 경우, 한 마리는 왼쪽끝에 고정하고 다른 한마리 위치를 찾는다.
			temp += nums[i+1];
			answer = Math.max(answer, sum - nums[0] + temp - nums[i]);
		}
		bw.write(answer + "\n");
		bw.flush();
		bw.close();
		br.close();
	}
}
