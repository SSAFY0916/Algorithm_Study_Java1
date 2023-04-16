import java.io.*;
import java.util.*;

public class Main {

    static int n, m, answer;
    static char[][] board;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, -1, 0, 1};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        board = new char[n][m]; // 보드 판
        int start = 0; // 초기 공들의 위치를 저장
        for (int i = 0; i < n; i++) {
            board[i] = br.readLine().toCharArray();
            for (int j = 0; j < m; j++) {
                if (board[i][j] == 'R') { // 빨간 공
                    start += i*11*11*11 + j*11*11;
                    board[i][j] = '.'; // 빈 공간으로 바꿈
                }
                if (board[i][j] == 'B') { // 파란 공
                    start += i*11 + j;
                    board[i][j] = '.'; // 빈 공간으로 바꿈
                }
            }
        }

        Set<Integer> visited = new HashSet<>(); // 공들의 위치 별로 방문 여부를 판별하기 위한 셋
        Queue<Integer> q = new ArrayDeque<>(); // 가능한 공들의 위치를 저장하는 큐
        q.add(start); // 초기 상태 추가
        visited.add(start); // 초기 상태 방문 처리
        int answer = 0; // 굴리는 횟수
        int[] red, blue; // 공들의 좌표를 저장하는 배열
        boolean redOut, blueOut; // 공이 보드판을 빠져나갔는지 여부
        loop:
        while (++answer <= 10) { // 최대 10번까지만 굴림
            Queue<Integer> newQ = new ArrayDeque<>(); // 새로운 큐를 만들어 굴리는 횟수 별 가능한 공들의 위치를 저장
            while (!q.isEmpty()) {
                for (int i = 0; i < 4; i++) { // 4방향으로 굴리기
                    red = getRedCoord(q.peek()); // 빨간 공의 위치 가져오기
                    blue = getBlueCoord(q.peek()); // 파란공의 위치 가져오기

                    redOut = false;
                    blueOut = false;
                    while (0 <= red[0]+dr[i] && red[0]+dr[i] < n && 0 <= red[1]+dc[i] && red[1]+dc[i] < m && (red[0]+dr[i] != blue[0] || red[1]+dc[i] != blue[1]) && board[red[0]+dr[i]][red[1]+dc[i]] != '#') { // 빨간 공 굴리기
                        red[0]+=dr[i];
                        red[1]+=dc[i];
                        if (board[red[0]][red[1]] == 'O') {
                            redOut = true;
                            red[0] = -1;
                            break;
                        }
                    }
                    while (0 <= blue[0]+dr[i] && blue[0]+dr[i] < n && 0 <= blue[1]+dc[i] && blue[1]+dc[i] < m && (blue[0]+dr[i] != red[0] || blue[1]+dc[i] != red[1]) && board[blue[0]+dr[i]][blue[1]+dc[i]] != '#') { // 파란 공 굴리기
                        blue[0]+=dr[i];
                        blue[1]+=dc[i];
                        if (board[blue[0]][blue[1]] == 'O') {
                            blueOut = true;
                            blue[0] = -1;
                            break;
                        }
                    }
                    while (0 <= red[0]+dr[i] && red[0]+dr[i] < n && 0 <= red[1]+dc[i] && red[1]+dc[i] < m && (red[0]+dr[i] != blue[0] || red[1]+dc[i] != blue[1]) && board[red[0]+dr[i]][red[1]+dc[i]] != '#') { // 빨간 공 굴리기
                        red[0]+=dr[i];
                        red[1]+=dc[i];
                        if (board[red[0]][red[1]] == 'O') {
                            redOut = true;
                            break;
                        }
                    }

                    if (redOut && !blueOut) { // 빨간 공만 빠짐
                        break loop;
                    } else if (!blueOut) { // 둘다 안 빠짐
                        int cur = makeInteger(red, blue);
                        if (visited.add(cur)) { // 방문 여부 체크
                            newQ.add(makeInteger(red, blue)); // 새로운 큐에 저장
                        }
                    }
                }
                q.poll(); // 이번 위치로 4방향 탐색 했으니까 큐에서 제거
            }
            q = newQ; // 새로운 큐와 바꾸기
        }
        bw.write((answer>10 ? -1 : answer) + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    // 공들의 위치를 표현하는 int에서 빨간 공의 위치를 알아오는 메소드
    static int[] getRedCoord(int x) {
        return new int[]{x / (11 * 11 * 11), x % (11 * 11 * 11) / (11 * 11)};
    }

    // 공들의 위치를 표현하는 int에서 파란 공의 위치를 알아오는 메소드
    static int[] getBlueCoord(int x) {
        return new int[]{x % (11 * 11) / 11, x % 11};
    }

    // 공들의 위치를 int로 표현하는 메소드
    static int makeInteger(int[] red, int[] blue) {
        return red[0]*11*11*11 + red[1]*11*11 + blue[0]*11 + blue[1];
    }
}