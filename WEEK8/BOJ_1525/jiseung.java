import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        int start = 0; // 입력받은 표를 저장할 수, 왼쪽위부터 차례대로 저장
        for (int i = 0; i < 3; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 3; j++) {
                start = start * 10 + Integer.parseInt(st.nextToken());
            }
        }
        int answer = -1;
        int[] dr = {-1, 0, 1, 0};
        int[] dc = {0, -1, 0, 1};
        Map<Integer, Integer> dp = new HashMap<>(); // 각각의 경우에 대한 최소 이동횟수를 저장할 맵
        Queue<int[]> q = new ArrayDeque<>();
        q.add(new int[]{start, 0}); // 현재 표와 이동 횟수
        while (!q.isEmpty()) {
            int num = q.peek()[0];
            int dist = q.poll()[1];
            if(dp.containsKey(num) && dp.get(num) <= dist) { // 현재 표에 대해서 더 적은 이동횟수로 이동할 수 있다면 건너뜀
                continue;
            }
            if(num == 123456780) { // 원하는 모양이 되면 끝
                answer = dist;
                break;
            }
            dp.put(num, dist); // 최소 이동횟수 갱신
            int[][] board = num2arr(num); // 표를 저장해놓은 수를 다시 2차원 배열로 바꿈
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == 0) { // 0 옆의 수들만 움직일 수 있다
                        for (int k = 0; k < 4; k++) {
                            int newi = i + dr[k];
                            int newj = j + dc[k];
                            if(newi<0 || newi>=3 || newj<0 || newj>=3) {
                                continue;
                            }
                            board[i][j] = board[newi][newj]; // 옮기고
                            board[newi][newj] = 0;
                            q.add(new int[]{arr2num(board), dist + 1}); // 새로만든 표를 다시 수로 바꾸어 추가
                            board[newi][newj] = board[i][j]; // 다시 되돌려 놓음
                            board[i][j] = 0;
                        }
                    }
                }
            }
        }
        bw.write(answer + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    // 숫자를 2차원 표로 만드는 메소드
    static int[][] num2arr(int num) {
        int[][] arr = new int[3][3];
        for (int i = 2; i >= 0; i--) {
            for (int j = 2; j >= 0; j--) {
                arr[i][j] = num % 10;
                num /= 10;
            }
        }
        return arr;
    }

    // 2차원 표를 숫자로 만드는 메소드
    static int arr2num(int[][] arr) {
        int num = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                num = num * 10 + arr[i][j];
            }
        }
        return num;
    }
}