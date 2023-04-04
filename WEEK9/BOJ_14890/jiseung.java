import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class Main {

    static int n, l;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        l = Integer.parseInt(st.nextToken());
        int[][] board = new int[n][n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int answer = 0;
        for (int i = 0; i < n; i++) {
            if (isCrossable(board[i])) { // i번째 행에 대해서 지나갈 수 있는지 탐색
                answer++;
            }

            int[] col = new int[n];
            for (int j = 0; j < n; j++) {
                col[j] = board[j][i];
            }
            if (isCrossable(col)) { // i번째 열에 대해서 지나갈 수 있는지 탐색
                answer++;
            }
        }
        bw.write(answer + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    static boolean isCrossable(int[] arr) {
        int curHeight = arr[0], seqNum = 1; // 현재 높이(배열의 0번째 값)와 같은 높이로 연속된 횟수
        boolean[] used = new boolean[n]; // 경사로를 놓았는지 여부를 저장하는 배열
        for (int i = 1; i < n; i++) { // 0번째 인덱스를 왼쪽, n-1번째 인덱스를 오른쪽으로 생각하고 반복
            if (curHeight +1 == arr[i]) { // 높이가 1 크다, 왼쪽으로 경사로를 깔아야 된다
                if(seqNum < l) { // 현재 높이가 경사로의 길이만큼 이어지지 않았다
                    return false;
                }
                for (int j = 0; j < l; j++) { // 왼쪽으로 l칸만큼 경사로를 놓을 수 있는지 비교
                    if(used[i-1-j]) {
                        return false;
                    }
                }
                for (int j = 0; j < l; j++) { // 왼쪽으로 l칸만큼 경사로를 놓음
                    used[i-1-j] = true;
                }
                curHeight = arr[i]; // 현재 높이와 연속된 횟수를 갱신
                seqNum = 1;
            } else if (curHeight == arr[i] +1) { // 높이가 1 작다, 오른쪽으로 경사로를 깔아야 된다
                if(i+l-1 >= n) { // 오른쪽에 l-1개의 칸이 존재하긴 하는지 비교
                    return false;
                }
                for (int j = 1; j < l; j++) { // 오른쪽으로 l-1칸동안 같은 높이가 유지되는지 비교
                    if(arr[i+j] != arr[i]) {
                        return false;
                    }
                }
                for (int j = 0; j < l; j++) { // 현재 칸과 오른쪽으로 l-1칸에 경사로를 놓을 수 있는지 비교
                    if(used[i+j]) {
                        return false;
                    }
                }
                for (int j = 0; j < l; j++) { // 현재 칸과 오른쪽으로 l-1칸에 경사로 설치
                    used[i+j] = true;
                }

                curHeight = arr[i+l-1]; // 현재 높이와 연속된 횟수를 오른쪽으로 l-1칸(방금 놓은 경사로의 마지막 칸) 뒤로 이동
                seqNum = l;
                i += l-2;
            } else if (curHeight == arr[i]) { // 높이가 같다
                seqNum++;
            } else { // 높이가 2이상 차이 난다
                return false;
            }
        }
        return true;
    }
}