import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    static int N, L, cnt;
    static int[][] board;
    static boolean[] vis;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken()); // 행 수
        L = Integer.parseInt(st.nextToken()); // 경사로를 설치하는데 필요한 길의 수
        vis = new boolean[N];
        board = new int[N][N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        checkPath();
        board = rotate(board); // 90도 반시계 회전
        checkPath();
        System.out.println(cnt); // 가능한 길의 수(정답) 출력

    }

    private static void checkPath() {
        for (int i = 0; i < N; i++) {
            Arrays.fill(vis, false);
            int[] road = board[i];

            int p1 = 0;
            loop:
            while (p1 < N - 1) {
                int p2 = p1 + 1;
                if (Math.abs(road[p2]- road[p1]) >=2) break; // 차이가 2 이상일 경우 불가능 (종료)
                if (road[p2] == road[p1]+1) { // 올라가는 경사일 경우
                    int height = road[p1]; // 확인할 경사로 높이
                    for (int j = 0; j < L; j++) {
                        // 범위를 넘어서거나, 이미 경사로를 놓은적 있거나, 필요한 경사로 높이가 아닌 경우 불가능 (종료)
                        if (p1-j<0 || vis[p1-j] || road[p1-j] != height) break loop;
                    }
                    // 가능할 경우 올라가는 경사로 놓기
                    for (int j = 0; j < L; j++) {
                        vis[p1-j] = true;
                    }

                } else if (road[p2] == road[p1]-1) { // 내려가는 경사일 경우
                    int height = road[p1]-1; // 확인할 경사로 높이
                    for (int j = 1; j <= L; j++) {
                        // 범위를 넘어서거나, 필요한 경사로 높이가 아닌 경우 불가능(종료)
                        if (p1+j >= N || road[p1+j] != height) break loop;
                    }
                    // 내려가는 경사로 놓기
                    for (int j = 1; j <= L; j++) {
                        vis[p1+j] = true;
                    }
                    // p2를 경사로 끝까지 이동시키기
                    p2 = p1 + L;
                }
                p1 = p2;

            }

            if (p1 == N-1) cnt++; // p1 포인터가 끝까지 도달했을 경우 가능한 길이므로 cnt +1
        }
    }

    private static int[][] rotate(int[][] board) {
        int[][] newBoard = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                newBoard[i][j] = board[j][N - 1 - i];
            }
        }
        return newBoard;
    }

}
