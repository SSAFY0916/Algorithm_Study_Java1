import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    /*
    9명으로 이루어진 두 팀이 공격과 수비를 번갈아 하는 게임
    총 N이닝 동안 게임을 진행
    경기가 시작하기 전까지 타순(타자가 타석에 서는 순서)을 정해야 하고, 경기 중에는 타순을 변경할 수 없다.
    한 이닝에 3아웃이 발생하면 이닝이 종료 -> 두 팀이 공격과 수비를 서로 바꾼다.
    9번 타자까지 공을 쳤는데 3아웃이 발생하지 않은 상태면 이닝은 끝나지 않고, 1번 타자가 다시 타석에 선다.
    타순은 이닝이 변경되어도 순서를 유지해야 한다.

    가장 많은 득점을 얻을 수 있는 타순 정하기 -> "최대 득점" 출력
     */
    static int N; // 이닝 수
    static int[][] gameResult; // 각 이닝 별로 타자 번호별 게임 결과 저장 배열
    static int[] position; // 타자 번호별 위치 (0:시작점 1: 1루 2: 2루 3: 3루 4: 홈)
    static int maxScore; // 정답 (최대 득점 점수)
    static int[] order; // 타순 배열

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        gameResult = new int[N][9]; // 타자 번호 0번부터 시작.
        position = new int[9];
        StringTokenizer st;
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 9; j++) {
                gameResult[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        maxScore = 0;
        solution();
    }

    private static void solution() {
        order = new int[9]; // 타자 순서 경우의 수 저장
        makeOrder(0, 0);
        System.out.println(maxScore);
    }

    private static void makeOrder(int cnt, int flag) {
        if (cnt == 4) { // 1번 타자가 4번째 순서가 아닐 경우 리턴해서 순서 다시 정하기
            if (order[3] != 0) {
                return;
            }
        }
        if (cnt == 9) { // 9번째까지 순서 정했으면 타순 정하기 완료 -> 게임 시작
            int score = gameStart(order);
            maxScore = Math.max(maxScore, score);
            return;
        }
        for (int i = 0; i < 9; i++) {
            if ((flag & (1 << i)) != 0) {
                continue; // if ((i&flag)!=0) continue; <- 이거 아님!
            }
            order[cnt] = i;
            makeOrder(cnt + 1, flag | (1 << i));
        }
    }

    private static int gameStart(int[] order) {
        int scoreSum = 0; // 이닝별 점수 합 저장 변수
        int cur = 0; // 타순 저장할 포인터
        for (int n = 0; n < N; n++) { // 이닝 수
            Arrays.fill(position, 0); // 게임 재시작시 위치 0으로 재조정
            int outCnt = 0; // 이닝별 아웃 수
            while (outCnt < 3) {
                cur %= 9;
                int curNum = order[cur++]; // 현재 타자 번호
                int curRes = gameResult[n][curNum]; // 현재 타자의 결과
                if (curRes == 0) { // 아웃
                    outCnt++;
                } else if (curRes == 1) { // 안타
                    go(1, curNum);
                } else if (curRes == 2) { // 2루타
                    go(2, curNum);
                } else if (curRes == 3) { // 3루타
                    go(3, curNum);
                } else { // 홈런!
                    go(4, curNum);
                }
                // 득점 파악 + 위치 재조정 (타자들 한번 이동이 끝난 후 계속 할 수 있도록 함)
                for (int i = 0; i < 9; i++) {
                    if (position[i] >= 4) { // 위치가 4 이상이면(홈으로 도착) 득점 후 위치를 시작점으로 재조정
                        scoreSum++;
                        position[i] = 0;
                    }
                }
            }
        }
        return scoreSum;
    }

    private static void go(int cnt, int curNum) { // 점수에 따라 위치 이동시키는 함수
        for (int p = 0; p < 9; p++) { // 현재 타자를 제외한 타자들 이동시키기
            if (position[p] == 0) {
                continue; // 아직 이동하지 않은 타자들은 이동x
            }
            position[p] += cnt;
        }
        position[curNum] += cnt; // 현재 타자도 이동시키기
    }

}