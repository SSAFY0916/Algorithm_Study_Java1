import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    static int N, M;
    static char[][] office;

    static boolean[][] visited;
    static boolean[] key;

    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};


    public static void main(String[] args) throws Exception {

        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        for (int t = 0; t < T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            key = new boolean[26];  // 키 획득 여부 저장

            List<Pair> entrance = new ArrayList<>();    // 입구 모음

            office = new char[N][M];    // 평면도
            visited = new boolean[N][M];    // 방문 check

            for (int i = 0; i < N; i++) {
                office[i] = br.readLine().toCharArray();
                for (int j = 0; j < M; j++) {
                    if ((i == 0 || j == 0 || i == N - 1 || j == M - 1) && office[i][j] != '*') {    // 빌딩 입구로 쓸 수 있는 곳
                        entrance.add(new Pair(i, j));
                    }
                }
            }
            String input = br.readLine();
            if (!input.equals("0")) {   // 처음 갖고 있는 키
                for (int i = 0; i < input.length(); i++) {
                    key[input.charAt(i)-'a'] = true;
                }
            }

            sb.append(bfs(entrance)).append("\n");
        }
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    static void initVisited() {
        for (int i = 0; i < N; i++) {
            Arrays.fill(visited[i], false);
        }
    }

    static int bfs(List<Pair> entrance) {   // bfs 탐색

        int result = 0; // 훔친 문서 개수
        boolean flag = true;

        while (flag) {  // 열쇠를 새로 주웠을 때 열 수 있는 문이 있는지 확인하기 위하여 입구마다 bfs탐색 -> 새로 주운 열쇠가 없는 경우 break
            flag = false;

            for (Pair etr : entrance) { // 입구마다 bfs 탐색
                Queue<Pair> queue = new ArrayDeque<>();
                initVisited();

                if (office[etr.x][etr.y] == '$') {  // 입구가 서류
                    office[etr.x][etr.y] = '.'; // 서류 줍줍
                    result++;
                } else if (office[etr.x][etr.y] >= 'a' && office[etr.x][etr.y] <= 'z') {    // 입구가 열쇠
                    if (!key[office[etr.x][etr.y] - 'a']) {    // 나한테 없던 열쇠
                        key[office[etr.x][etr.y] - 'a'] = true;
                        flag = true;
                    }
                    office[etr.x][etr.y] = '.'; // 열쇠 줍줍
                } else if (office[etr.x][etr.y] >= 'A' && office[etr.x][etr.y] <= 'Z') {    // 입구가 문
                    if (key[office[etr.x][etr.y] - 'A']) {  // 열쇠 있음
                        office[etr.x][etr.y] = '.'; // 문 열기
                    } else { // 열쇠 없음
                        continue;
                    }
                }

                visited[etr.x][etr.y] = true;
                queue.offer(etr);

                // bfs 탐색
                while (!queue.isEmpty()) {
                    int x = queue.peek().x;
                    int y = queue.poll().y;

                    for (int j = 0; j < 4; j++) {
                        int nx = x + dx[j];
                        int ny = y + dy[j];

                        if (nx < 0 || nx >= N || ny < 0 || ny >= M || office[nx][ny] == '*' || visited[nx][ny])
                            continue;

                        if (office[nx][ny] == '$') {    // 서류 줍줍
                            office[nx][ny] = '.';
                            result++;
                        } else if (office[nx][ny] >= 'a' && office[nx][ny] <= 'z') {    // 열쇠
                            if (!key[office[nx][ny] - 'a']) {    // 나한테 없던 열쇠
                                key[office[nx][ny] - 'a'] = true;
                                flag = true;
                            }
                            office[nx][ny] = '.';   // 열쇠 줍줍
                        } else if (office[nx][ny] >= 'A' && office[nx][ny] <= 'Z') {    // 문
                            if (key[office[nx][ny] - 'A']) {    // 열쇠 있음
                                office[nx][ny] = '.';   // 문 열기
                            } else {    // 열쇠 없음
                                continue;
                            }
                        }

                        visited[nx][ny] = true;
                        queue.offer(new Pair(nx, ny));

                    }
                }
            }
        }
        return result;
    }

    static class Pair {
        int x, y;

        Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}

