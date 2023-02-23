package bfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.StringTokenizer;

class Bridge {
    int row,col;

    public Bridge(int row, int col) {
        this.row = row;
        this.col = col;
    }
}
public class Main_2146_다리만들기_2 {

    static int[][] map; // 원본 데이터
    static int[][] dis; // 거리 배열
    static int[][] landNumMap; // 섬 번호를 저장할 배열
    static boolean[][] vis; // 방문 배열
    static int[] dix = {-1, 0, 1, 0}; //상우하좌
    static int[] diy = {0, 1, 0, -1};
    static int N;
    static ArrayDeque<Bridge> queue = new ArrayDeque<>(); // 다리를 놓는 bfs를 수행할 큐
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        map = new int[N][N];
        landNumMap = new int[N][N];
        dis = new int[N][N];
        for (int row = 0; row < N; row++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int col = 0; col < N; col++) {
                map[row][col] = Integer.parseInt(st.nextToken());
            }
        }

        vis = new boolean[N][N];
        int cnt = 0;
        // 아직 방문하지 않은 섬이면 섬번호 체크 bfs 실행 -> 동시에 섬에 가장 인접한 섬 위치 큐에 넣기
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                if (map[row][col] == 1 && !vis[row][col]) {
                    cnt++;
                    bfs(row,col, cnt);
                }
            }
        }

        vis = new boolean[N][N]; // 방문 배열 갱신
        int ans = Integer.MAX_VALUE; // 정답 (최소 다리 갯수)
        while(!queue.isEmpty()) {
            Bridge cur = queue.poll();
            for (int i = 0; i < 4; i++) {
                int nx = cur.row + dix[i];
                int ny = cur.col + diy[i];
                if (nx <0 || nx >= N || ny < 0 || ny >= N) continue;
                if (!vis[nx][ny] && landNumMap[nx][ny] == 0) { // 바다를 만날 경우
                    dis[nx][ny] = dis[cur.row][cur.col] + 1; // 최단 거리 기록
                    landNumMap[nx][ny] = landNumMap[cur.row][cur.col]; // 어느 섬에서 온 다리인지 표시
                    queue.offer(new Bridge(nx,ny)); // Bridge 객체 저장
                    vis[nx][ny] = true;
                } else if (dis[nx][ny] > 0 || landNumMap[nx][ny] >= 1) { // 이미 방문한 바다 혹은 섬에 다다를경우
                    if (landNumMap[nx][ny] != landNumMap[cur.row][cur.col]) { // 다른 섬일 때
                        ans = Math.min(ans, dis[nx][ny] + dis[cur.row][cur.col]); // 최솟값 비교 후 답에 저장
                    }
                }
            }
        }

        System.out.println(ans);
    }

    // landNumMap 이용하기
    // 바다가 아니라 바다에 인접한 섬을 큐에 넣기
    private static void bfs(int row, int col, int cnt) {
        ArrayDeque<Bridge> checkNumQueue = new ArrayDeque<>();
        checkNumQueue.offer(new Bridge(row, col));
        vis[row][col] = true;
        landNumMap[row][col] = cnt;

        while(!checkNumQueue.isEmpty()) {
            Bridge cur = checkNumQueue.poll();
            for (int i = 0; i < 4; i++) {
                int nx = cur.row + dix[i];
                int ny = cur.col + diy[i];
                if (nx <0 || nx >= N || ny < 0 || ny >= N || vis[nx][ny]) continue;
                if (map[nx][ny] == 0) { // 섬에 인접한 바다일 경우 현재 위치를 큐에 넣어준다.
                    queue.offer(new Bridge(cur.row, cur.col)); //  섬을 넣어주라고 위에 적었으면서 바다 넣어서 실수함..ㅠㅠ
                } else { // 섬일 경우 섬 번호 체크
                    checkNumQueue.offer(new Bridge(nx,ny));
                    vis[nx][ny] = true;
                    landNumMap[nx][ny] = cnt;
                }
            }
        }

    }

}