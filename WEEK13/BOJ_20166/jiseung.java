import java.io.*;
import java.util.*;

public class Main {

    static int n, m, k;
    static char[][] board; // 알파벳을 저장하는 격자
    static int[] dr = {-1, -1, -1, 0, 1, 1, 1, 0};
    static int[] dc = {-1, 0, 1, 1, 1, 0, -1, -1};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        board = new char[n][m];
        for (int i = 0; i < n; i++) {
            board[i] = br.readLine().toCharArray();
        }
        for (int i = 0; i < k; i++) {
            bw.write(simulate(br.readLine()) + "\n");
        }
        bw.flush();
        bw.close();
        br.close();
    }

    // 문자열을 입력받아 가능한 경우의 수를 반환하는 메소드
    static int simulate(String word) {
        int[][] counts = new int[n][m]; // 현재 위치에서 끝나는 경우의 수를 저장하는 배열
        Queue<int[]> q = new ArrayDeque<>(); // 가능한 (행, 열)을 저장할 큐
        for (int i = 0; i < n; i++) { // 입력받은 문자열에서 첫 글자와 동일한 알파벳을 격자에서 찾아서 counts와 q에 반영
            for (int j = 0; j < m; j++) {
                if (word.charAt(0) == board[i][j]) {
                    counts[i][j] = 1;
                    q.add(new int[]{i, j});
                }
            }
        }

        for (int i = 1; i < word.length(); i++) { // 0번째는 위에서 했으므로 1부터 문자열의 끝까지 반복
            int[][] newCounts = new int[n][m]; // 문자열의 현재 알파벳에 대한 경우의 수를 저장할 배열
            Queue<int[]> newQ = new ArrayDeque<>(); // 새로운 위치를 저장할 큐
            char ch = word.charAt(i); // 탐색할 알파벳

            while (!q.isEmpty()) {
                int r = q.peek()[0];
                int c = q.poll()[1];
                for (int j = 0; j < 8; j++) { // 8방 탐색
                    int newr = (r+dr[j] + n*2) % n; // 새로운 위치를 격자가 환형이므로 나누기를 통해 계산
                    int newc = (c+dc[j] + m*2) % m;
                    if (board[newr][newc] == ch) { // 원하는 알파벳이면
                        if (newCounts[newr][newc] == 0) { // 이번 탐색에서 처음 온 위치면 큐에 추가
                            newQ.add(new int[]{newr, newc});
                        }
                        newCounts[newr][newc] += counts[r][c]; // 경우의 수 증가
                    }
                }
            }
            q = newQ; // 큐 변경
            counts = newCounts; // 경우의 수 변경
        }

        int answer = 0;
        for (int i = 0; i < n; i++) {
            answer += Arrays.stream(counts[i]).sum(); // 각 경우의 수 총합 계산
        }
        return answer;
    }
}