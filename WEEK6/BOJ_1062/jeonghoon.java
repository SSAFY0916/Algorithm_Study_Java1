import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_1062 {
    private static int teachNum;
    private static int wordNum;
    private static String[] words;
    private static int max = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        wordNum = Integer.parseInt(st.nextToken());
        teachNum = Integer.parseInt(st.nextToken());
        words = new String[wordNum];
        for (int i = 0; i < wordNum; i++) {
            words[i] = br.readLine();
        }

        if (teachNum < 5) {
            System.out.println(0);
            return;
        }

        // 꼭 필요한 {a, c, i, n, t}가 등록된 flag
        int flag = 0b10_1000_0010_0001_0000_0100_0000;

        select(5, 0, flag);

        System.out.println(max);
    }

    private static void select(int cnt, int start, int flag) {
        if (cnt == teachNum) {
            // 완성된 flag를 이용하여 유효성 검증
            validation(flag);
            return;
        }

        // 비트 flag를 이용해서 부분집합 생성
        for (int i = start + 1; i < 26; i++) {
            // 이미 1이면 continue (이 경우는 기존에 등록해준 {a, c, i, n, t}가 이 조건문을 실행하게 됨)
            if (((flag >> 25 - i) & 1) == 1)
                continue;
            flag |= 1 << 25 - i;
            select(cnt + 1, i, flag);
            flag &= ~(1 << 25 - i);
        }
    }

    private static void validation(int flag) {
        int cnt = 0;
        loop: for (int i = 0; i < words.length; i++) {
            // word별로 순회하며 각 character들의 flag bit가 켜져있는지 확인
            for (int j = 0; j < words[i].length(); j++) {
                // 켜져있지 않은 경우 불가능
                if ((flag >> (25 - (words[i].charAt(j) - 'a')) & 1) == 0)
                    continue loop;
            }
            cnt++;
        }
        if (cnt > max)
            max = cnt;
    }
}