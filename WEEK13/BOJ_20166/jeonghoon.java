import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class BOJ_20166 {
    static int row, col, strNum;
    // 8방 탐색
    static int[][] dir = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
    static char[][] board;
    static Map<String, Integer> map = new HashMap<>();
    static String[] loveStr;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        row = Integer.parseInt(st.nextToken());
        col = Integer.parseInt(st.nextToken());
        strNum = Integer.parseInt(st.nextToken());

        loveStr = new String[strNum];

        board = new char[row][col];

        for (int i = 0; i < row; i++) {
            board[i] = br.readLine().toCharArray();
        }

        for (int i = 0; i < strNum; i++) {
            loveStr[i] = br.readLine();
        }

        // 모든 시작 점에 대해서 깊이 우선 탐색 진행
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                String tmp = "";
                dfs(i, j,tmp + board[i][j], 0);
            }
        }

        for (int i = 0; i < strNum; i++) {
            System.out.println(map.getOrDefault(loveStr[i], 0));
        }
    }

    private static void dfs(int r, int c, String s, int depth) {
        // 최대 길이는 5 이므로 5인 경우까지만 탐색하고 종료
        if (depth == 5)
            return;
        // 키를 포함하고 있는 경우와 그렇지 않은 경우를 나눠서 진행
        if (map.containsKey(s)) {
            map.put(s, map.get(s) + 1);
        } else {
            map.put(s, 1);
        }
        // 8방 탐색 진행
        for (int i = 0; i < dir.length; i++) {
            int nextRow = (r + dir[i][0] + row) % row;
            int nextCol = (c + dir[i][1] + col) % col;
            dfs(nextRow, nextCol, s + board[nextRow][nextCol], depth + 1);
        }
    }

}
