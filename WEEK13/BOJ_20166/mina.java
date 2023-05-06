import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    static int N, M, K;

    static Set<String> set;

    static char[][] world;

    static String[] beloved;

    static int[] dx = {0, 1, 0, -1, -1, 1, -1, 1};
    static int[] dy = {1, 0, -1, 0, -1, -1, 1, 1};

    public static void main(String[] args) throws Exception {

        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        world = new char[N][M]; // 격자 세상
        beloved = new String[K];    // 신이 좋아하는 문자열

        Map<String, Integer> map = new HashMap<>(); // (key, value) = (문자열, 만들 수 있는 경우의 수)

        for (int i = 0; i < N; i++) {
            world[i] = br.readLine().toCharArray();
        }

        for (int i = 0; i < K; i++) {
            beloved[i] = br.readLine();
        }

        set = new HashSet<>();  // 문자열의 조합 저장
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < K; i++) {
            set.clear();
            if (map.get(beloved[i]) != null) {  // 문자열의 경우의 수를 구해놓음
                sb.append(map.get(beloved[i])).append("\n");    // 맵에서 경우의 수 가져옴
                continue;
            }

            // 문자열의 경우의 수를 구해야함
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < M; k++) {
                    recur(0, beloved[i], j, k, "");
                }
            }
            map.put(beloved[i], set.size());    // 맵에 저장
            sb.append(set.size()).append("\n");
        }

        bw.write(sb.toString());

        bw.flush();
        bw.close();
        br.close();
    }

    static void recur(int depth, String beloved, int x, int y, String ret) {
        if (world[x][y] == beloved.charAt(depth)) { // 선택한 좌표의 알파벳이 문자열의 depth위치의 알파벳이랑 일치함
            ret += Integer.toString(x * M + y) + ",";   // 좌표값의 인덱스값 ret에 이어 붙임
        } else {    // 선택한 알파벳이 문자열의 depth위치의 알파벳이랑 일치함
            return;
        }

        if (depth == beloved.length() - 1) {    // 문자열 끝까지 다 만듦
            set.add(ret);   // set에 추가
            return;
        }

        for (int i = 0; i < 8; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            // x좌표 환형 처리
            if (nx < 0)
                nx += N;
            else if (nx >= N)
                nx %= N;

            // y좌표 환형 처리
            if (ny < 0)
                ny += M;
            else if (ny >= M)
                ny %= M;

            recur(depth + 1, beloved, nx, ny, ret);
        }
    }

}
