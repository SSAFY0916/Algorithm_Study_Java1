import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;

public class BOJ_1941 {
    static char[][] arr = new char[5][5];
    static boolean[][] selected = new boolean[5][5];

    static int res = 0;

    static class Pair {
        int first;
        int second;

        public Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        for (int i = 0; i < 5; i++) {
            String tmp = br.readLine();
            arr[i] = tmp.toCharArray();
        }
        // 조합으로 7명의 학생 선택
        comb(0, 0);
        System.out.println(res);
    }

    private static void comb(int cnt, int start) {
        if (cnt == 7) {
            // 선택이 완료되었다면 BFS를 이용하여 탐색
            bfs();
            return;
        }
        for (int i = start; i < 25; i++) {
            selected[i / 5][i % 5] = true;
            comb(cnt + 1, i + 1);
            selected[i / 5][i % 5] = false;
        }
    }

    private static void bfs() {
        Queue<Pair> q = new ArrayDeque<>();
        boolean[][] visited = new boolean[5][5];
        int[][] dir = new int[][] { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
        boolean flag = false;

        // 선택된 학생들 중 가장 첫번째 학생을 선택한 뒤 Queue에 넣어줌
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (selected[i][j]) {
                    flag = true;
                    q.add(new Pair(i, j));
                    visited[i][j] = true;
                    break;
                }
            }
            if (flag)
                break;
        }

        // 이다솜파와 임도연파의 수
        int sCnt = 0;
        int yCnt = 0;

        // BFS를 수행하며 poll된 학생의 파에 따라서 각 Cnt 증가
        while (!q.isEmpty()) {
            Pair tmp = q.poll();
            if (arr[tmp.first][tmp.second] == 'Y') {
                yCnt++;
            } else if (arr[tmp.first][tmp.second] == 'S') {
                sCnt++;
            }
            for (int i = 0; i < 4; i++) {
                int nextRow = tmp.first + dir[i][0];
                int nextCol = tmp.second + dir[i][1];

                if (check(nextRow, nextCol) && !visited[nextRow][nextCol] && selected[nextRow][nextCol]) {
                    q.add(new Pair(nextRow, nextCol));
                    visited[nextRow][nextCol] = true;
                }
            }
        }

        // 두 파의 학생 수의 합이 7이면 모두 이어져 있고, 다솜파가 도연파보다 사람이 많다면 성공
        if (yCnt + sCnt == 7 && sCnt > yCnt)
            res++;
    }

    private static boolean check(int nextRow, int nextCol) {
        return nextRow >= 0 && nextRow < 5 && nextCol >= 0 && nextCol < 5;
    }
}
