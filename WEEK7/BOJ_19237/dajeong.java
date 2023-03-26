package simulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_19237_어른상어 {

    static int N, M, K, cnt;
    static int[][] board, newBoard;
    static int[] curDir;
    static int[][][] dirOrder;
    static int[] dix = {0, -1, 1, 0, 0};
    static int[] diy = {0, 0, 0, -1, 1};
    static int[][][] smell;
    static Queue<int[]> queue;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        board = new int[N][N];
        // 상어가 뿌리고 간 냄새 저장할 배열
        smell = new int[N][N][2];

        for (int r = 0; r < N; r++) {
            st = new StringTokenizer(br.readLine());
            for (int c = 0; c < N; c++) {
                board[r][c] = Integer.parseInt(st.nextToken());
                if (board[r][c] != 0) {
                    smell[r][c][0] = board[r][c];
                    smell[r][c][1] = K;
                }
            }
        }
        curDir = new int[M + 1]; // 상어들의 현재 위치
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i < M + 1; i++) {
            curDir[i] = Integer.parseInt(st.nextToken());
        }

        dirOrder = new int[M + 1][4][4];
        for (int i = 1; i < M + 1; i++) {
            for (int r = 0; r < 4; r++) {
                st = new StringTokenizer(br.readLine());
                for (int c = 0; c < 4; c++) {
                    dirOrder[i][r][c] = Integer.parseInt(st.nextToken());
                }
            }
        }

        cnt = M;
        int time = 0;
        // 한 번 이동 후 상어들 위치를 저장할 새로운 배열
        newBoard = new int[N][N];

        while (cnt > 1 && ++time <= 1000) {
            // 상어들 한꺼번에 큐에 넣고 bfs 수행
            queue = new ArrayDeque<>();
            for (int r = 0; r < N; r++) {
                for (int c = 0; c < N; c++) {
                    if (board[r][c] != 0) {
                        queue.offer(new int[]{board[r][c], r, c});
                    }
                }
            }
            bfs();

            // 상어들 각각 한 번 이동 후 냄새 배열 갱신해주기
            for (int r = 0; r < N; r++) {
                for (int c = 0; c < N; c++) {
                    // 가장 최근 이동한 위치에 냄새 뿌려주기
                    if (newBoard[r][c] != 0) {
                        smell[r][c] = new int[]{newBoard[r][c], K};
                    } else {
                        // 이미 뿌려놓은 냄새 -1
                        if (smell[r][c][1] != 0) {
                            smell[r][c][1]--;
                            // 만약 냄새 제한시간이 0이 되면 냄새 없애주기
                            if (smell[r][c][1] == 0) {
                                smell[r][c][0] = 0;
                                smell[r][c][1] = 0;
                            }
                        }
                    }
                }
            }

            board = newBoard;
            // *** Arrays.fill로 하니, board의 reference까지 함께 영향을 주는 문제가 생겼음. 연결고리를 끊고 새로 할당하기
            newBoard = new int[N][N];
        }
        // 1000초를 넘겼으면, -1 출력. 1000초 이하면 1번 상어가 남을 때까지의 시간 출력.
        if (time > 1000) {
            System.out.println(-1);
        } else {
            System.out.println(time);
        }
    }

    private static void bfs() {
        while (!queue.isEmpty()) {
            // 상하좌우 인접한 위치로 이동 가능하면 true, 이동 불가하면 false 체크할 플래그
            boolean flag = false;
            // 현재 상어 위치
            int[] curPos = queue.poll();
            // 현재 상어 번호
            int num = curPos[0];
            // 현재 상어 방향
            int currentDir = curDir[num];
            // 상어의 다음 방향
            int nd = 0;
            for (int i = 0; i < 4; i++) {
                nd = dirOrder[num][currentDir - 1][i];
                int nx = curPos[1] + dix[nd];
                int ny = curPos[2] + diy[nd];
                if (nx < 0 || nx >= N || ny < 0 || ny >= N) {
                    continue;
                }

                // 이동할 위치에 상어가 뿌리고 간 냄새가 없으면(이동할 위치가 있다면)
                if (smell[nx][ny][1] == 0) {
                    if (newBoard[nx][ny] == 0) {
                        newBoard[nx][ny] = num; // 이동한 위치에 다른 상어가 존재하지 않으면 이동
                        flag = true;
                        break;
                    } else if (newBoard[nx][ny] > num) { // 상어가 존재하면 더 값이 작은애가 잡아먹기
                        newBoard[nx][ny] = num;
                        cnt--; // 상어 수 -1
                        flag = true;
                        break;
                    } else { // 이미 이동한 상어가 값이 더 작으면, 오는 상어 잡아먹기
                        cnt--;
                        flag = true;
                        break;
                    }
                }
            }
            if (!flag) { // 이동할 곳이 없다면
                for (int i = 0; i < 4; i++) {
                    nd = dirOrder[num][currentDir - 1][i];
                    int nx = curPos[1] + dix[nd];
                    int ny = curPos[2] + diy[nd];
                    if (nx < 0 || nx >= N || ny < 0 || ny >= N) {
                        continue;
                    }
                    if (smell[nx][ny][0] == num) {
                        newBoard[nx][ny] = num;
                        break;
                    }
                }
            }
            curDir[num] = nd;
        }
    }


}
