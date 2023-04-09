import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
// 너무 더럽게 푼 코드.. 다시 풀어봐야겠다
public class Main_14890_경사로 {
    static int N,L, ans;
    static int[][] board;
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());
        board = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        ans = 0;
        search(board); // 행 탐색
        search(rotate()); // 90도 반시계 회전 후 다시 탐색 (열->행)
        System.out.println(ans);

    }
    private static void search(int[][] board) {
        // 행 탐색
        for (int r = 0; r < N; r++) {
            int p1 = 0, p2 =0;
            int bigCnt = 0, curCnt = 1, smallCnt = 0;
            for (int c = 0; c < N; c++) {
                // 종료조건1) p1 포인터가 끝까지 올 경우 가능한 길로 채택. 카운트 후 다음 길 탐색.
                if(p1 == N-1) {
                    ans++;
                    break;
                }
                // 종료조건2) 종료되지 않은채 p2포인터만 끝까지 왔을 경우 불가능한 길. 종료
                if(p2 == N-1) break;

                // p2 한칸 이동
                p2++;

                // p1, p2 포인터별 지형 높이
                int p1Val = board[r][p1];
                int p2Val = board[r][p2];

                // p1, p2 지형 차이가 2이상일 경우 불가능하므로 종료.
                if(Math.abs(p1Val-p2Val)>1) break;
                else { // 경사로가 1이하로 차이날 경우 가능한 길인지 탐색
                    if(p1Val < p2Val) { // +1 지형을 만났을 경우 bigCnt+1
                        bigCnt++;
                    } else if (p1Val == p2Val) { // 같은 높이의 지형을 만났을 경우
                        if(bigCnt > 0 && curCnt < L) break; // 혹시나 그 전에 더 높은 지형을 만났었고,경사로가 만들어질 수 없으면 종료 (e.g 343)
                        if(smallCnt > 0 && smallCnt < L) break; // 혹시나 그 전에 더 낮은 지형을 만났었고 경사로가 만들어질 수 없으면 종료 (e.g 323)
                        p1 = p2; // p1을 이동
                        curCnt++; // 현재 지형 카운트 +1
                        continue;
                    } else { // -1 지형을 만났을 경우 smallCnt+1
                        smallCnt++;
                    }

                    if(smallCnt == L) { // 내려가는 경사로 길이가 L이므로 경사로 설치 가능
                        p1 += L; // p1을 내려가는 경사로 끝으로 옮기기
                        p2 = p1;  // p2를 p1 자리로 옮기기
                        smallCnt = 0; // smallCnt, bigCnt, curCnt 갱신
                        bigCnt = 0;
                        curCnt=0;
                    } else if(bigCnt >0) { // p2: 더 큰 지형을 만났을 경우
                        if(curCnt < L) break; // 올라가는 경사로가 불가능하면(경사로 길이만큼 현재 지형이 없을 경우) 종료
                        else { // 올라가는 경사로가 가능할 경우
                            p1 += 1; // p1을 p2 자리로 옮기기
                            p2 = p1; // p2를 p1 자리로 옮기기
                            smallCnt = 0; // smallCnt, bigCnt, curCnt 갱신
                            bigCnt = 0;
                            curCnt=1;
                        }
                    }
                }
            }
        }
    }
    private static int[][] rotate() {
        int[][] newBoard = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                newBoard[i][j] = board[j][N-1-i];
            }
        }
        return newBoard;
    }

}