import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int c = Integer.parseInt(st.nextToken());
        int r = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(br.readLine());
        if (k > r * c) { // 공연장에 등장할 수 없는 번호이면 0 출력
            bw.write(0 + "\n");
        } else {
            int x = 0, y = 0, d = 0; // 시작 위치는 (0, 0), 시작 방향은 아래 방향
            int[][] board = new int[r][c]; // 방문한 위치를 판별하기 위한 배열
            int[] dx = {1, 0, -1, 0}; // 하우상좌
            int[] dy = {0, 1, 0, -1};
            while (--k > 0) { // k-1 번 반복
                board[x][y] = k; // 방문 처리
                x += dx[d]; // 기존 방향으로 이동
                y += dy[d];
                if (x < 0 || x >= r || y < 0 || y >= c || board[x][y] != 0) { // 공연장을 벗어나거나 이미 방문한 위치면 방향을 바꿈
                    x -= dx[d];
                    y -= dy[d];
                    d = (d + 1) % 4;
                    x += dx[d];
                    y += dy[d];
                }
            }
            bw.write(++y + " " + ++x);
        }
        bw.close();
        br.close();
    }
}