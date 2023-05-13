import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int r = Integer.parseInt(st.nextToken());

        int[] dr = {-1, 0, 1, 0};
        int[] dc = {0, -1, 0, 1};

        boolean[][][] roads = new boolean[n][n][4];
        for (int i = 0; i < r; i++) {
            st = new StringTokenizer(br.readLine());
            int r1 = Integer.parseInt(st.nextToken()) - 1;
            int c1 = Integer.parseInt(st.nextToken()) - 1;
            int r2 = Integer.parseInt(st.nextToken()) - 1;
            int c2 = Integer.parseInt(st.nextToken()) - 1;

            if (r1 - r2 == 1) {
                roads[r1][c1][0] = true;
                roads[r2][c2][2] = true;
            } else if (r2 - r1 == 1) {
                roads[r1][c1][2] = true;
                roads[r2][c2][0] = true;
            } else if (c1 - c2 == 1) {
                roads[r1][c1][1] = true;
                roads[r2][c2][3] = true;
            } else {
                roads[r1][c1][3] = true;
                roads[r2][c2][1] = true;
            }
        }

        boolean[][] cows = new boolean[n][n];
        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine());
            cows[Integer.parseInt(st.nextToken()) - 1][Integer.parseInt(st.nextToken()) - 1] = true;
        }

        List<Integer> cowCounts = new ArrayList<>();
        boolean[][] visited = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (visited[i][j]) {
                    continue;
                }
                int cowCount = 0;
                Queue<int[]> q = new ArrayDeque<>();
                q.add(new int[]{i, j});
                while (!q.isEmpty()) {
                    int x = q.peek()[0];
                    int y = q.poll()[1];
                    if (visited[x][y]) {
                        continue;
                    }
                    visited[x][y] = true;
                    if (cows[x][y]) {
                        cowCount++;
                    }
                    for (int l = 0; l < 4; l++) {
                        if (roads[x][y][l]) {
                            continue;
                        }
                        int newx = x + dr[l];
                        int newy = y + dc[l];
                        if (newx < 0 || newx >= n || newy < 0 || newy >= n) {
                            continue;
                        }
                        q.add(new int[]{newx, newy});
                    }
                }
                cowCounts.add(cowCount);
            }
        }

        int answer = 0;
        for (int i = 0; i < cowCounts.size(); i++) {
            for (int j = i + 1; j < cowCounts.size(); j++) {
                answer += cowCounts.get(i) * cowCounts.get(j);
            }
        }
        bw.write(answer + "\n");
        bw.flush();
        bw.close();
        br.close();
    }
}