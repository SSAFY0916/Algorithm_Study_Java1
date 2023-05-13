import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int Q = Integer.parseInt(st.nextToken());

        // 이진검색트리 (트리셋) 사용
        // 명소만 저장하기
        TreeSet<Integer> popular = new TreeSet<>();

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            int card = Integer.parseInt(st.nextToken());
            if (card == 1) {
                popular.add(i);
            }
        }

        int pos = 1;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Q; i++) {
            st = new StringTokenizer(br.readLine());
            int command = Integer.parseInt(st.nextToken());
            if (command == 1) {
                int idx = Integer.parseInt(st.nextToken());
                if (popular.contains(idx)) {
                    popular.remove(idx);
                } else {
                    popular.add(idx);
                }
            } else if (command == 2) {
                int move = Integer.parseInt(st.nextToken());
                pos = (pos + move) % N;
                if (pos == 0) pos = N;
            } else if (command == 3) {
                if (popular.isEmpty()) {
                    sb.append(-1).append("\n"); // 명소가 없는 경우 불가능
                } else { // 명소가 있는 경우
                    Integer t = popular.ceiling(pos); // upperbound (도현이 다음 명소의 위치)
                    if (t == null) { // 도현이 이후에 명소가 없는 경우
                        sb.append((popular.first() + N - pos)%N).append("\n");
                    } else { // 도현이 이후에 명소가 있는 경우
                        sb.append((t - pos)%N).append("\n");
                    }
                }
            }
        }

        System.out.println(sb.toString());
    }
}