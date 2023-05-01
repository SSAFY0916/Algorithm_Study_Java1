import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int t = Integer.parseInt(br.readLine());
        while(t-- > 0) {
            Set<String> prefixes = new HashSet<>(); // 전화번호들을 저장하는 셋
            int n = Integer.parseInt(br.readLine());
            String[] nums = new String[n]; // 전화번호들을 저장하는 배열
            for (int i = 0; i < n; i++) {
                nums[i] = br.readLine();
                prefixes.add(nums[i]);
            }
            String answer = "YES\n";
            for (int i = 0; i < n; i++) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < nums[i].length() - 1; j++) {
                    sb.append(nums[i].charAt(j)); // 앞에서부터 한 숫자씩 추가
                    if (prefixes.contains(sb.toString())) { // 현재까지의 접두어가 셋에 전화번호로 저장되어 있으면 일관성 X
                        answer = "NO\n";
                        break;
                    }
                }
            }
            bw.write(answer);
        }
        bw.flush();
        bw.close();
        br.close();
    }
}