import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        long p = 1_000_000_007;
        int n = Integer.parseInt(br.readLine());
        long[] scores = new long[n]; // 각 음식의 스코빌 지수 저장
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            scores[i] = Long.parseLong(st.nextToken());
        }
        Arrays.sort(scores); // 스코빌 지수 기준 정렬
        long answer = 0L; //주헌고통지수의 합
        long cur = 0L; // 스코빌 지수의 차의 합
        long power2n = 1L; // 2의 거듭제곱
        for (int i = 0; i < n; i++) {
            cur += scores[n-1-i] - scores[i] + p; // i번째 큰 스코빌 지수와 i번째 작은 스코빌 지수의 차이 더하기
            cur = (cur % p + p) % p; // 나누기
            answer += cur * power2n; // 2^i개의 경우가 있다
            answer %= p;
            power2n *= 2;
            power2n %= p;
        }
        bw.write(answer + "\n");
        bw.close();
        br.close();
    }
}