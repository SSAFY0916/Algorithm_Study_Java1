import java.io.*;
import java.util.*;

public class Main {

    static int n, k, answer;
    static int[] perm, powers;
    static int[][] board;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, -1, 0, 1};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        n = Integer.parseInt(br.readLine());
        k = Integer.parseInt(br.readLine());
        board = new int[n][n]; // 보드 판
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        powers = new int[k]; // 총알의 공격력
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < k; i++) {
            powers[i] = Integer.parseInt(st.nextToken());
        }

        perm = new int[k];
        permutation(0);
        bw.write(answer + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    // k개의 총알을 어느 행으로 쏠지 결정하는 중복순열
    static void permutation(int count) {
        if (count == k) {
            answer = Math.max(answer, simulate());
            return;
        }
        for (int i = 0; i < n; i++) {
            perm[count] = i;
            permutation(count + 1);
        }
    }

    static int simulate() {
        int ret = 0; // 점수
        int[][] temp = new int[n][n]; // 보드판을 복사해서 표적들의 체력을 저장
        int[][] scores = new int[n][n]; // 보드판을 복사해서 표적들의 점수를 저장
        for (int i = 0; i < n; i++) {
            temp[i] = Arrays.copyOf(board[i], n);
            scores[i] = Arrays.copyOf(board[i], n);
        }
        for (int i = 0; i < k; i++) { // i번째 총알은 perm[i] 행에 발사
            for (int j = 0; j < n; j++) {
                if (temp[perm[i]][j] <= 0) { // 빈칸이면 건너뜀
                    continue;
                }
                if (temp[perm[i]][j] >= 10) { // 보너스 표적
                    ret += scores[perm[i]][j]; // 점수 획득
                    temp[perm[i]][j] = 0; // 표적 삭제
                    scores[perm[i]][j] = 0; // 점수 삭제
                }else { // 일반 표적
                    temp[perm[i]][j] -= powers[i]; // 총알의 공격력만큼 표적의 체력 감소
                    if (temp[perm[i]][j] <= 0) { // 표적이 사라짐
                        ret += scores[perm[i]][j]; // 점수 획득
                        if (scores[perm[i]][j] >= 4) { // 기존의 표적의 체력이 4 이상이라 새로운 표적이 생성될 때
                            for (int l = 0; l < 4; l++) {
                                int newr = perm[i] + dr[l];
                                int newc = j + dc[l];
                                if (newr < 0 || newr >= n || newc < 0 || newc >= n) {
                                    continue;
                                }
                                if (temp[newr][newc] > 0) {
                                    continue;
                                }
                                temp[newr][newc] = scores[perm[i]][j] / 4; // 새로운 표적 생성
                                scores[newr][newc] = scores[perm[i]][j] / 4; // 새로운 점수 지정
                            }
                        }
                        temp[perm[i]][j] = 0; // 표적 삭제
                        scores[perm[i]][j] = 0; // 점수 삭제
                    }
                }
                break; // 이번 행에서 총알이 표적을 만났으니까 break하고 다음 총알로 넘어가기
            }
        }
        return ret;
    }
}