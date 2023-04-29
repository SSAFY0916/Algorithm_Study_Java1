import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));


    public static void main(String[] args) throws Exception {

        Map<Integer, Integer> map = new HashMap<>();    // (Key, Value) => (숫자, 그 숫자의 개수)

        int N = Integer.parseInt(br.readLine());
        int[] nums = new int[N];    // 숫자 저장 배열
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            nums[i] = Integer.parseInt(st.nextToken());
            if (map.containsKey(nums[i])) {
                map.put(nums[i], map.get(nums[i]) + 1);
            } else {
                map.put(nums[i], 1);
            }
        }


        int result = 0;
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                int n = nums[i] + nums[j];  // nC2 조합
                if (nums[i] == n && nums[j] == n) {  // 0 + 0 = 0 조합
                    if (map.containsKey(n)) { // 좋은 수 n이 있으면 n의 개수 가져오기
                        int m = map.remove(n);
                        result += (m > 2) ? m : 0; // 0이 3개 이상이어야 모든 0에 대하여 0+0으로 좋은 수로 만들 수 있음
                    }
                } else if (nums[i] == n || nums[j] == n) { // 0 + n = n 조합
                    if (map.containsKey(n)) { // 좋은 수 n이 있으면 n의 개수 가져오기
                        int m = map.get(n);
                        if (m > 1) {    // n이 2개 이상이어야 모든 n에 대하여 0+n으로 좋은 수를 만들 수 있음
                            result += m;
                            map.remove(n);
                        }
                    }
                } else {
                    if (map.containsKey(n)) // 좋은 수 n이 있으면 n의 개수 가져오기
                        result += map.remove(n);
                }

            }
        }

        bw.write(Integer.toString(result));
        bw.flush();
        bw.close();
        br.close();
    }
}
