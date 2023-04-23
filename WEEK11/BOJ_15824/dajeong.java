package aaaaapractice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main_15824_너봄에는캡사이신이맛있단다 {

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
            pow[i] = t;
            t = (t << 1) % MOD;
        }

        int min = 0, max = 0;
        for (int i = N-1; i >= 0; i--) {
            min += pow[i] * list.get(N-1-i);
            max += pow[i] * list.get(i);
            min %= MOD;
            max %= MOD;
        }

        long answer = (max - min + MOD) % MOD;
        System.out.println(answer);
    }


}
