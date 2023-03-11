import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class Main {

    static BufferedWriter bw;
    static int n, count;
    // 입력받은 수들을 저장할 배열과 dp에 사용할 배열
    static int[] nums, dp;
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));
        n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        nums = new int[n];
        for(int i=0; i<n; i++) {
            nums[i] = Integer.parseInt(st.nextToken());
        }
        dp = new int[nums.length + 1]; // i번째 인덱스에는 길이 i개의 증가하는 부분수열을 찾았을 때의 그 부분수열의 최댓값을 저장, 최대 n개의 부분수열까지 생길 수있으므로 크기는 n+1
        Arrays.fill(dp, 1_000_001); // 일단 찾은게 없다는 의미로 입력값보다 1큰 값으로 초기화
        dp[0] = 0; // 길이 0짜리 부분수열엔 입력값보다 1작은 값
        count = 0; // 부분수열의 최대 길이 저장
        for (int num : nums) { // 수열에서 수를 하나씩 가져옴
            int left = 0, right = count+1, mid; // 여태까지 찾은 부분수열에서 
            while(left < right) { // 이분탐색으로 현재 숫자를 저장할 위치를 찾음
                mid = (left + right) / 2;
                if(dp[mid] == num) { // 같은 숫자가 이미 부분수열에 있으면 갱신은 필요없지만 break를 위해 탐색
                    right = mid;
                    break;
                }else if(dp[mid] < num) {
                    left = mid + 1;
                }else {
                    right = mid;
                }
            }
            dp[right] = num; // dp[right-1]보다는 크면서 기존의 dp[right]값보다는 작은 num으로 dp[right] 갱신
            count = Math.max(count, right); // 1_000_001인 인덱스 찾아서 최대 길이 구하면 되는데 반복문 돌리기 귀찮아서 
        }
        bw.write(count + "\n");
        bw.flush();
        bw.close();
        br.close();
    }
}