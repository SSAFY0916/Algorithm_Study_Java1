import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {

    static int n, m, k;
    static int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
    static int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};
    static char[][] map;

    static HashMap<String, Integer> answer;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        map = new char[n][m];
        answer = new HashMap<>();

        for (int i = 0; i < n; i++) {
            String s = br.readLine();
            for (int j = 0; j < m; j++) {
                map[i][j] = s.charAt(j);
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                dfs(i, j, String.valueOf(map[i][j]));
            }
        }

        for (int i = 0; i < k; i++) {
            String query = br.readLine();
            System.out.println(answer.getOrDefault(query, 0));
        }
    }

    private static void dfs(int x, int y, String str) {

        answer.put(str, answer.getOrDefault(str, 0) + 1);
        if (str.length() == 5) {
            return;
        }

        for (int i = 0; i < 8; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            if (nx < 0) {
                nx = n - 1;
            } else if (nx >= n) {
                nx = 0;
            }

            if (ny < 0) {
                ny = m - 1;
            } else if (ny >= m) {
                ny = 0;
            }
            dfs(nx, ny, str + map[nx][ny]);
        }
    }
}