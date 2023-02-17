import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    	StringTokenizer st = new StringTokenizer(br.readLine());
    	int n = Integer.parseInt(st.nextToken());
    	int c = Integer.parseInt(st.nextToken());
    	int[] nums = new int[n];
    	for(int i=0; i<n; i++) {
    		nums[i] = Integer.parseInt(br.readLine());
    	}
    	Arrays.sort(nums); // 집을 좌표 기준으로 정렬
    	int left = 1;      // 가능한 두 공유기 사이의 최소값
    	int right = 10_0000_0001; // 가능한 두 공유기 사이의 최대값 +1 <= [left, right)로 이분탐색
    	int answer = -1;   // 가능한 두 공유기 사이의 최대 거리를 저장할 변수
    	while(left < right) { // [left, right)이므로 left == right만 되어도 탐색할 구간이 없으므로 break
    		int mid = (left+right)/ 2; // 현재 탐색에 사용할 중간 거리
    		int prev = nums[0];
    		int count = 1;				// 가능한 공유기 개수
    		for(int i=1; i<n; i++) {
    			if(nums[i] - prev >= mid) { // 이전 집의 좌표와 mid 이상 차이 남 
    				count++;				// 공유기 개수 +1
    				prev = nums[i];			// 이전 집의 좌표를 갱신
    			}
    		}
    		if(count < c) {				// 가능한 공유기 개수가 부족하면	
    			right = mid;			// right를 이동시켜서 거리를 줄임
    		} else if (count >= c) {	// 가능한 공유기 개수가 많거나 같으면
    			answer = Math.max(answer, mid); // 최대 거리 갱신하고
    			left = mid+1;					// left를 이동시켜 거리를 늘림, mid일 때는 현재 반복에서 탐색 했으므로 mid+1로 갱신
    		}
    	}
    	bw.write(answer + "\n");
        bw.flush();
        bw.close();
        br.close();
    }
}
