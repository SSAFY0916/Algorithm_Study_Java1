package bfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_1194_달이차오른다가자 {
    static int N,M;
    static char[][][] map;
    static int[][][] dist;
    static boolean[][][] vis;
    static int[] dix = {-1,1, 0, 0};
    static int[] diy = {0, 0, -1, 1};
    static final int maxKeys = 6;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken()); // 미로 세로 크기 (행)
        M = Integer.parseInt(st.nextToken()); // 미로 가로 크기 (열)
        map = new char[N][M][1<<maxKeys]; // 열쇠 경우의 수에 따른 이동 가능한 맵 (멀티버스/차원확장)
        dist = new int[N][M][1<<maxKeys]; // bfs 이동 거리 저장 배열
        vis = new boolean[N][M][1<<maxKeys]; // bfs 방문 여부 체크 배열

        int[] curPos = new int[3];
        for (int r = 0; r < N; r++) {
            String str = br.readLine();
            for (int c = 0; c < M; c++) {
                map[r][c][0] = str.charAt(c);
                if (map[r][c][0] == '0') { // 현재 민식이 위치 저장
                    curPos[0] = r;
                    curPos[1] = c;
                    curPos[2] = 0; // 가진 열쇠 조합 상태
                }
            }
        }

        // 미로 탐색 후 도착지까지 가는데 걸린 최소 이동횟수 리턴
        // 미로 탐색 탐색할 경우 -1 리턴
        System.out.println(bfs(curPos));
    }

    private static int bfs(int[] initPos) {
        int res = -1; // 미로 탐색 실패할 경우, -1 리턴
        Queue<int[]> queue = new ArrayDeque<>();
        int initX = initPos[0];
        int initY = initPos[1];
        int initK = initPos[2];
        queue.offer(new int[]{initX, initY, initK});
        vis[initX][initY][initK] = true;
        dist[initX][initY][initK] = 1; // 맨 마지막에 dis 배열 값 -1한 결과 리턴

        while(!queue.isEmpty()) {
            int[] curP = queue.poll();
            int curX = curP[0];
            int curY = curP[1];
            int curK = curP[2];

            if (map[curX][curY][0] == '1') { // 도착지에 도착할 경우 거리 반환 후 종료
                return dist[curX][curY][curK] -1;
            }
            for (int i = 0; i < 4; i++) {
                int nx = dix[i] + curP[0];
                int ny = diy[i] + curP[1];
                int nk = curK;

                if (nx<0 || ny<0 || nx>=N || ny>=M || vis[nx][ny][nk]) continue;
                char target = map[nx][ny][0]; // ** 실수) key는 0으로 주어야 함! (타겟 탐색은 새 차원에서 하면 안된다. / 탐색 배열이랑 차원배열 자체를 나눴으면 실수 안했을듯?)
                if (target == '#') continue; // 벽일 때는 못감

                if ('a' <= target && target <= 'f') { // 열쇠일 경우
                    nk = nk | (1<<(target-'a')); // 취득한 열쇠 플래그 세우기

                } else if ('A' <= target && target <= 'F') { // 문을 만날 경우
                    // 해당 문에 해당하는 키를 가지고 있지 않을 경우 문을 열 수 없음
                    if ((nk & (1<<(target-'A'))) == 0)  continue;
                }

                // 멀티버스 bfs 수행
                queue.offer(new int[]{nx,ny,nk});
                vis[nx][ny][nk] =true;
                dist[nx][ny][nk] = dist[curX][curY][curK] + 1;
            }
        }
        return res;
    }
}
