import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

// 04 03 13 18
// 04 03 13 35
public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        int[] dr = {-1, 0, 1, 0};
        int[] dc = {0, -1, 0, 1};
        int t = Integer.parseInt(br.readLine());
        while(t-- > 0) {
            st = new StringTokenizer(br.readLine());
            int h = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            char[][] board = new char[h+2][w+2]; // 따로 출발 지점이 정해져 있지 않고 빌딩 밖에서 들어오기 때문에 가장자리에 한 줄씩 추가하여 선언함
            for (int i = 1; i <= h; i++) {
                System.arraycopy(br.readLine().toCharArray(), 0, board[i], 1, w); // 1~w 까지 입력 받기
            }

            boolean[] keys = new boolean[26]; // 열쇠 소유 여부를 저장하는 배열
            for (char key : br.readLine().toCharArray()) {
                if(key == '0') break; // 가진 열쇠가 없으면 break
                keys[key - 'a'] = true;
            }

            Queue<int[]> q = new ArrayDeque<>(); // 현재 위치를 저장하는 큐
            q.add(new int[]{0, 0});
            boolean[][] visited = new boolean[h + 2][w + 2]; // 방문여부를 저장하는 배열
            List<int[]>[] locked = new ArrayList[26]; // 각 알파벳 별로 잠겨있어서 이동하지 못한 위치를 저장하는 리스트
            for (int i = 0; i < 26; i++) {
                locked[i] = new ArrayList<>();
            }
            int count = 0; // 얻은 문서의 개수

            while (!q.isEmpty()) {
                int r = q.peek()[0];
                int c = q.poll()[1];
                if(visited[r][c]) { // 거리를 구하는 것이 아니라 방문 가능 여부를 탐색하기 때문에 이미 방문했으면 건너뜀
                    continue;
                }
                if(board[r][c] == '$') { // 문서획득
                    count++;
                }
                visited[r][c] = true; // 방문 여부 갱신

                for (int i = 0; i < 4; i++) {
                    int newr = r + dr[i];
                    int newc = c + dc[i];
                    if (newr < 0 || newr >= h + 2 || newc < 0 || newc >= w + 2) { // 격자 밖
                        continue;
                    }
                    if(board[newr][newc] == '*') { // 벽
                        continue;
                    }
                    if('a' <= board[newr][newc] && board[newr][newc] <= 'z') { // 열쇠
                        keys[board[newr][newc] - 'a'] = true; // 열쇠 획득
                        q.add(new int[]{newr, newc});
                        q.addAll(locked[board[newr][newc] - 'a']); // locked에 있는 해당 열쇠에 대한 위치들에 모두 이동가능하므로 큐에 넣음
                    }else if('A' <= board[newr][newc] && board[newr][newc] <= 'Z') { // 문
                        if(keys[board[newr][newc] - 'A']) { // 열쇠가 있으면 이동
                            q.add(new int[]{newr, newc});
                        }else {
                            locked[board[newr][newc] - 'A'].add(new int[]{newr, newc}); // 아직 열쇠가 없어서 이동하지 못하므로 locked에 추가
                        }
                    }else { // 빈칸 혹은 문서
                        q.add(new int[]{newr, newc});
                    }
                }
            }
            bw.write(count + "\n");
        }
        bw.flush();
        bw.close();
        br.close();
    }
}