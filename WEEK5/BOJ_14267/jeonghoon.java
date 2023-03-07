import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class BOJ_14267 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int peopleNum = Integer.parseInt(st.nextToken());
        int compNum = Integer.parseInt(st.nextToken());

        // 부하 직원을 저장하기 위한 2차원 가변 배열 초기화
        List<List<Integer>> subordinate = new ArrayList<>();
        for (int i = 0; i <= peopleNum; i++) {
            subordinate.add(new ArrayList<>());
        }

        st = new StringTokenizer(br.readLine());
        // 사장은 상사가 없으므로 한개의 토큰 날리기
        st.nextToken();

        // 2번부터 입력받은 상사의 가변배열 위치에 본인의 번호를 추가
        for (int i = 2; i <= peopleNum; i++) {
            int superior = Integer.parseInt(st.nextToken());
            subordinate.get(superior).add(i);
        }

        int[] dp = new int[peopleNum + 1];

        // 상사로부터 받은 칭찬을 저장하기
        for (int i = 0; i < compNum; i++) {
            st = new StringTokenizer(br.readLine());
            int num = Integer.parseInt(st.nextToken());
            int amount = Integer.parseInt(st.nextToken());

            dp[num] += amount;
        }

        // 상사로부터 자신의 직속 부하들에게 자신이 지금까지 받은 칭찬을 내리칭찬
        for (int i = 1; i <= peopleNum; i++) {
            for (int tmp : subordinate.get(i)) {
                dp[tmp] += dp[i];
            }
        }

        for (int i = 1; i <= peopleNum; i++) {
            System.out.print(dp[i] + " ");
        }
    }

}