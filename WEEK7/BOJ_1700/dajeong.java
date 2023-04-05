package greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_1700_멀티탭스케줄링 {

    static int N, M;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());

        // 들어간 기기 체크
        boolean[] check = new boolean[M + 1];
        // 필요한 총 기기 갯수 센 배열
        int[] cnt = new int[M + 1];
        // 사용하는 기기 순서
        int[] order = new int[M];
        for (int i = 0; i < M; i++) {
            int target = Integer.parseInt(st.nextToken());
            order[i] = target;
            cnt[target]++;
        }
        // 꽂은 멀티탭 수
        int size = 0;
        // 플러그 뺀 횟수 (정답)
        int ansCnt = 0;
        for (int i = 0; i < M; i++) {

            int target = order[i];
            boolean flag = false;
            // 이미 꽂혀있을 경우 넘어가기
            if (check[target]) {
                cnt[target]--;
                continue;
            }
            // 꽂혀있지 않는 경우
            if (size < N) {             // 꽂을 수 있는 갯수 미만일 경우
                check[target] = true;
                cnt[target]--;
                size++;
            } else if (size == N) {     // 기존에 있는 기기 플러그를 빼야할 경우
                // 1. 앞으로 나올 가능성이 없는데(cnt=0) 멀티탭이 꽂혀있는 경우 빼기
                for (int c = 1; c <= M; c++) {
                    if (check[c] && cnt[c] == 0) {
                        check[c] = false;
                        flag = true;
                        break;
                    }
                }
                // 1번에 해당되지 않으면
                if (!flag) {
                    // "마지막 수"가 아닌, 가장 미래에 사용될 자원을 찾고 바꿔주기 (페이지 교체 알고리즘-optimal algorithm 생각해서 품)
                    int last = 0;
                    boolean[] checkLast = new boolean[M+1];
                    for (int j = i; j < M; j++) {
                        int t = order[j];
                        if (check[t] && !checkLast[t]) {
                            checkLast[t] = true;
                            last = t;
                        }
                    }
                    check[last] = false;
                }
                // 현재 꽂아야 하는 기기 체크, 횟수 -1, 플러그 뺀 횟수 +1
                check[target] = true;
                cnt[target]--;
                ansCnt++;
            }
        }
        System.out.println(ansCnt);
    }

}
