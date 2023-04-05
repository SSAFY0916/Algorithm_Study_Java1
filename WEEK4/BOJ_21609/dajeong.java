import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    static int[][] board;
    static boolean[][] visOutBfs, visInBfs;
    static int N, M;
    static int[] dix = {-1, 1, 0, 0}; // 상하좌우
    static int[] diy = {0, 0, -1, 1};
    static List<ZzangInfo> zzangList;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken()); // 한변 길이
        M = Integer.parseInt(st.nextToken()); // 일반블록 색상 갯수 -> 아오 이거 열 아니라구ㅠ

        // 입력
        board = new int[N][N];
        visOutBfs = new boolean[N][N]; // 블록 그룹 찾을 때 쓰는 방문 배열 (무지개 블록 x)
        visInBfs = new boolean[N][N]; // bfs 내에서 인접한 블록 찾을 때 사용할 방문배열 (무지개 블록 포함)

        // 입력
        for (int r = 0; r < N; r++) {
            st = new StringTokenizer(br.readLine());
            for (int c = 0; c < N; c++) { // M으로 잘못 적어서 계속 틀렸다..
                board[r][c] = Integer.parseInt(st.nextToken());
            }
        }

        int ans = 0; // 점수합 (정답)
        while (true) { // 오토플레이 반복문
            // 1. 블록 그룹 탐색 (bfs)
            zzangList = new ArrayList<>(); // 탐색할 때마다 블록그룹별 대표자 리스트 갱신
            for (int r = 0; r < N; r++) { // 블록 그룹 찾기용 방문 배열 갱신
                Arrays.fill(visOutBfs[r], false);
            }

            for (int r = 0; r < N; r++) {
                for (int c = 0; c < N; c++) {
                    if (board[r][c] > 0
                        && !visOutBfs[r][c]) { // 블록그룹 탐색하지 않은 일반 블록일 때 bfs로 블록 그룹 탐색
                        bfs(r, c);
                    }
                }
            }

            //2. 제거할 블록 그룹 찾기
            if (zzangList.isEmpty()) {
                break; // 전체 블록 그룹들 중 대표자(블록 수 많은 그룹)의 블록 수가 0이면 오토플레이 종료
            }
            // 정렬
            Collections.sort(zzangList);

            ZzangInfo target = zzangList.get(0); // 지울 블록 그룹 정함(정렬된 첫번째 원소)

            // 3. 블록그룹 수(cnt) 제곱만큼 점수(ans) 획득, board int[][]에서 지우기
            ans += target.cnt * target.cnt;

            bfs(target.zzangX, target.zzangY); // 대표자 블록을 이용해서 다시 bfs 시행 -> 지울 블록 찾기
            // 사실 지울 블록을 찾는 bfs에서는 대표자를 다시 찾을 필요 없는 등 필요없는 연산이 좀 있어서 따로 boolean flag를 두려고 했으나, 일반화 시키는 과정에서 괜히 꼬이는 바람에 그냥 하나로 씀
            removeBlocks(visInBfs); // bfs 돌린 결과 기록한 방문배열을 이용해서 타겟 블록 그룹 블록들 다 제거하기

            //     4. 중력 작용
            gravity();
            //    *** 5. 보드 반시계 방향 회전.. ***
            int[][] newBoard = new int[N][N];
            for (int x = 0; x < N; x++) {
                for (int y = 0; y < N; y++) {
                    newBoard[x][y] = board[y][N-1-x];
                }
            }
            board = newBoard;

            // 6. 중력 또 작용
            gravity();

        }
        // 6. 점수합 리턴
        System.out.println(ans);
    }

    private static void gravity() {
        for (int c = 0; c < N; c++) { // 열 순회
            ArrayDeque<Integer> stack = new ArrayDeque<>();
            int black = 0;
            for (int r = 0; r < N; r++) {
                if (board[r][c] >= 0) { // 무지개블록, 일반블록이면
                    stack.push(board[r][c]);
                    board[r][c] = -2;
                } else if (r > black && board[r][c] == -1) { // 첫번째 행이 아닌 상황에서 검은 블록이면
                    black = r; // 검은 블록 행 저장
                    while (!stack.isEmpty()) {
                        board[black - 1][c] = stack.pop();
                        black--;
                    }
                }
            }

            int idx = N-1;
            while(!stack.isEmpty()) { // if문이 아니라 while..
                board[idx--][c] = stack.pop();
            }
        }
    }

    private static void removeBlocks(boolean[][] vis) {
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                if (vis[r][c]) {
                    board[r][c] = -2; // 빈칸 표시 (0은 무지개블록임)
                }
            }
        }
    }

    private static void bfs(int r, int c) { // 블록 그룹 찾기용 bfs
        for (int i = 0; i < N; i++) { // 매 bfs마다 경로탐색용 방문배열 갱신
            Arrays.fill(visInBfs[i], false);
        }
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{r, c}); // 시작 일반블록 큐에 넣기
        visOutBfs[r][c] = true; // 그룹에 쓰인 일반 블록 기록용
        visInBfs[r][c] = true; // 경로 탐색용

        int cnt = 1; // 블록 그룹의 블록 수
        int rbCnt = 0; // 무지개 블록 수
        int color = board[r][c];
        List<int[]> zzang = new ArrayList<>(); // 대표자(짱)이 될 수 있는 후보 리스트
        zzang.add(new int[]{r, c}); // ** 첫번째 블록도 짱 후보 리스트에 넣어야 함!!

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int curX = cur[0];
            int curY = cur[1];
            for (int i = 0; i < 4; i++) {
                int nx = curX + dix[i];
                int ny = curY + diy[i];
                if (nx < 0 || nx >= N || ny < 0 || ny >= N || visInBfs[nx][ny]) {
                    continue;
                }

                if (board[nx][ny] < 0) {
                    continue; // 검은 블록이거나 빈칸일 경우 넘어가기
                }

                if (board[nx][ny] != color && board[nx][ny] != 0) {
                    continue;
                }

                if (board[nx][ny] == 0) { // 무지개 블록이면
                    rbCnt++; // 갯수 세기
                } else if (board[nx][ny] > 0) { // 일반 블록이면 (빈칸이 있으므로, else if로 명시)
                    zzang.add(new int[]{nx, ny}); // 짱 후보 리스트에 넣기
                }

                queue.offer(new int[]{nx, ny});
                cnt++;

                visInBfs[nx][ny] = true; // 경로탐색용 방문배열에 기록
                if (board[nx][ny] > 0) { // 일반블록이면 이미 그룹이 지어진 블록이므로 visOutBfs 기록
                    visOutBfs[nx][ny] = true;
                }
            }
        }
        if (cnt >= 2) { //***
            // bfs 이후에 대표자 찾기
            Collections.sort(zzang, new Comparator<int[]>() { // 대표자 찾기 위해 정렬 (행, 열 오름차순)
                @Override
                public int compare(int[] o1, int[] o2) {
                    if (o1[0] == o2[0]) {
                        return o1[1] - o2[1];
                    }
                    return o1[0] - o2[0];
                }
            });
            int[] z = zzang.get(0); // 선택된 대표자
            zzangList.add(new ZzangInfo(cnt, rbCnt, z[0], z[1]));
        }
    }

    // 각 블록 그룹별 선택된 대표자의 정보 객체
    static class ZzangInfo implements Comparable<ZzangInfo> {

        int cnt; // 해당 블록 그룹 블록 수
        int rbCnt; // 무지개 블록 수
        int zzangX; // 대표자 행
        int zzangY; // 대표자 열

        public ZzangInfo(int cnt, int rbCnt, int zzangX, int zzangY) {
            this.cnt = cnt;
            this.rbCnt = rbCnt;
            this.zzangX = zzangX;
            this.zzangY = zzangY;
        }

        @Override // 찍어보기용
        public String toString() {
            return "ZzangInfo{" +
                "cnt=" + cnt +
                ", rbCnt=" + rbCnt +
                ", zzangX=" + zzangX +
                ", zzangY=" + zzangY +
                '}';
        }

        //(cnt 내림차순, rbCnt 내림차순, 기준블록 r,c 내림차순 *** 어휴...
        @Override
        public int compareTo(ZzangInfo o) {
            if (this.cnt == o.cnt) {
                if (this.rbCnt == o.rbCnt) {
                    if (this.zzangX == o.zzangX) {
                        return o.zzangY - this.zzangY;
                    }
                    return o.zzangX - this.zzangX;
                }
                return o.rbCnt - this.rbCnt;
            }
            return o.cnt - this.cnt;
        }
    }

}