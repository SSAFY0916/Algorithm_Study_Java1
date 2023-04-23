import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/*
 * 틀린 풀이
 */
public class Main {

    static final int MOD = 1000000007;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            list.add(Integer.parseInt(st.nextToken()));
        }

        Collections.sort(list);

        // 2^n - 1을 메모이제이션할 배열 선언
        long[] pow = new long[N + 1];
        long t = 1;
        for (int i = 0; i < N; i++) {
            pow[i] = t - 1;
            t = (t << 1) % MOD;
        }

        int min = 0, max = 0;
        for (int i = 0; i < N; i++) {
            min += pow[i] * list.get(i);
            max += pow[i] * list.get(N - 1 - i);
            min %= MOD;
            max %= MOD;
        }

        long answer = max - min;
        if (answer < 0) {
            answer += MOD;
        }
        System.out.println(answer);
    }

}