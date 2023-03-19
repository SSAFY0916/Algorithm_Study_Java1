import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    static int K, N, result;
    static int[] vocaFlag;

    static List<Character> nominee = new ArrayList<Character>();

    public static void main(String[] args) throws Exception {

        StringTokenizer st = new StringTokenizer(br.readLine(), " ");

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        boolean[][] alphabet = new boolean[N]['z' + 1];
        String[] voca = new String[N];
        vocaFlag = new int[N];

        int flag = 0;

        // a, n, t, i, c 는 필수적으로 선택해야함
        flag = flag | 1 << 'a' - 'a';
        flag = flag | 1 << 'n' - 'a';
        flag = flag | 1 << 't' - 'a';
        flag = flag | 1 << 'i' - 'a';
        flag = flag | 1 << 'c' - 'a';

        for (int i = 0; i < N; i++) {
            voca[i] = br.readLine();
            for (int j = 0; j < voca[i].length(); j++) {
                //각 단어마다 필요한 알파벳 추가
                vocaFlag[i] = vocaFlag[i] | 1 << voca[i].charAt(j) - 'a';
            }
        }

        if (K < 5) {    // a, n ,t, i ,c 선택 못하는 경우
            result = 0;
        } else {
            // 조합 돌면서 알파벳 K-5개 선택
            combination(0, flag, 0);
        }

        bw.write(Integer.toString(result));

        bw.close();

    }

    static void combination(int count, int flag, int start) {   // 알파벳 26개 중 count 만큼 선택
        if (count == K - 5) {
            int c = 0;
            for (int i = 0; i < N; i++) {
                if ((vocaFlag[i] & flag) == vocaFlag[i]) {  // 선택 된 알파벳들 중에 단어가 포함되어있는 경우
                    c++;
                }
            }
            result = Math.max(result, c);
            return;
        }

        for (int i = start; i < 26; i++) {
            if ((flag & 1 << i) != 0) continue;
            combination(count + 1, flag | 1 << i, i + 1);
        }
    }
}
