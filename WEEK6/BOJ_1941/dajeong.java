import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

// BOJ 1941 소문난 칠공주

// 처음부터 모든 좌표마다 bfs로 풀면, 각각에 대해 모두 bfs 돌면서 사방탐색 해야 함.
// + 탐색의 경우의 수와 조건처리해야할 것이 많아 복잡해지고 시간초과가 난다.
// 조합을 이용해서 25개의 자리 중 7개를 선택한 후, bfs로 이다솜파가 가능한지 확인하는 방식을 사용해야 한다.

public class Main {

    static char[][] board;
    static boolean[][] selectedPP, bfsVis;
    static boolean[] vis;
    static int[] dix = {-1, 1, 0, 0};
    static int[] diy = {0, 0, -1, 1};
    static int ans;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        board = new char[5][5];
        for (int i = 0; i < 5; i++) {
            String s = br.readLine();
            for (int j = 0; j < 5; j++) {
                board[i][j] = s.charAt(j);
            }
        }

        vis = new boolean[25];
        selectedPP = new boolean[5][5];
        bfsVis = new boolean[5][5];

        // 5*5 배열을 차례대로 0부터 24까지 번호 부여
        // 25개의 좌표 중 7개의 경우의 수 뽑는 조합 시행
        backtracking(0, 0);
        // 정답 출력
        System.out.println(ans);
    }

    private static void backtracking(int cnt, int start) {
        if (cnt == 7) {
            
            // 갱신
            for (int r = 0; r < 5; r++) {
                Arrays.fill(selectedPP[r], false);
            }

            // 선택한 좌표 기록
            int row = 0;
            int col = 0;
            int cntDY = 0; // 임도연파 수
            // 선택한 좌표 번호를 행, 열 번호로 변환 + 임도연파 수 세기
            for (int i = 0; i < 25; i++) {
                if (vis[i]) {
                    row = i / 5;
                    col = i % 5;
                    selectedPP[row][col] = true;
                    if (board[row][col] == 'Y') {
                        cntDY++;
                    }
                }
            }
            // 임도연 파의 수가 4 미만일 때만 bfs 수행
            if (cntDY < 4) {
                // 한 좌표값을 가지고 bfs 수행 => 이다솜파가 되기 위한 조건이 맞는지 확인
                for (int i = 0; i < 5; i++) {
                    Arrays.fill(bfsVis[i], false);
                }
                bfs(row, col);
            }
            return;
        }
        
        for (int i = start; i < 25; i++) {
            if (vis[i]) {
                continue;
            }
            vis[i] = true;
            backtracking(cnt + 1, i + 1);
            vis[i] = false;
        }
    }

    private static void bfs(int x, int y) {
        int cnt = 1;
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{x, y});
        bfsVis[x][y] = true;

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            for (int i = 0; i < 4; i++) {
                int nx = cur[0] + dix[i];
                int ny = cur[1] + diy[i];
                if (nx < 0 || nx >= 5 || ny < 0 || ny >= 5 || bfsVis[nx][ny]) {
                    continue;
                }
                // 선택된 사람이 아닐 경우 continue
                if (!selectedPP[nx][ny]) {
                    continue;
                }
                queue.offer(new int[]{nx, ny});
                bfsVis[nx][ny] = true;
                cnt++;
            }
        }
        // 사방탐색 후 서로 인접해있는 사람들의 수가 7명일 때(파벌 생성 가능) 가능한 파벌의 경우의 수로 기록
        if (cnt == 7) {
            ans++;
        }
    }


}