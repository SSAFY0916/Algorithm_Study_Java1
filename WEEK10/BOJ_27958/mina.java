import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    static int N, K, result;

    static int[][] target;
    static int[] bullet, targetCount;

    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(br.readLine());
        K = Integer.parseInt(br.readLine());
        target = new int[N][N]; // 보드 판
        bullet = new int[K];    // 갖고있는 총알
        targetCount = new int[N];   // 보드 판의 각 행에 들어았는 타겟 수
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                target[i][j] = Integer.parseInt(st.nextToken());
                if (target[i][j] != 0)  // 타겟 있는 좌표인 경우
                    targetCount[i]++;   // 타겟 count 증가
            }
        }

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < K; i++) {
            bullet[i] = Integer.parseInt(st.nextToken());
        }

        recur(0, 0, targetCount, target, target);

        bw.write(Integer.toString(result));
        bw.flush();
        bw.close();
        br.close();
    }

    static void recur(int count, int totalScore, int[] targetCountOrigin, int[][] heartOrigin, int[][] targetOrigin) {
        int score = totalScore;

        if (count == K) {
            result = Math.max(result, score);
            return;
        }

        // 점수판 복사본 만들어서 쓰기
        int[][] target = new int[N][N]; // 체력 확인용
        int[][] heart = new int[N][N];  // 초기 체력 확인용
        for (int i = 0; i < N; i++) {
            target[i] = Arrays.copyOf(targetOrigin[i], N);
            heart[i] = Arrays.copyOf(heartOrigin[i], N);
        }
        int[] targetCount = Arrays.copyOf(targetCountOrigin, N);    // 보드의 각 행이 갖고있는 표적 수

        for (int i = 0; i < N; i++) {
            if (targetCount[i] == 0) // 갖고있는 표적이 1개 이상인 행을 고름
                continue;

            for (int j = 0; j < N; j++) {  // 0번 열부터 표적 찾기
                if (target[i][j] != 0) {    // 표적 찾음
                    if (target[i][j] > 9) {  // 보너스 점수인 경우
                        score += heart[i][j];   // 점수 합산
                        target[i][j] = 0;   // 표적 맞춤
                        heart[i][j] = 0;
                        targetCount[i]--;   // 표적 맞췄으므로 count 감소
                    } else {
                        if (bullet[count] >= target[i][j]) {    // 공격력이 높아서 한번에 표적 없애는 경우
                            score += heart[i][j];   // 점수 합산
                            target[i][j] = 0; // 표적 맞춤
                            targetCount[i]--;   // 표적 맞췄으므로 count 감소

                            // 초기체력/4 사방에 퍼트리기 (0인곳에만)
                            if (heart[i][j] / 4 != 0) {
                                for (int k = 0; k < 4; k++) {
                                    int nx = i + dx[k];
                                    int ny = j + dy[k];

                                    if (nx < 0 || nx >= N || ny < 0 || ny >= N || target[nx][ny] != 0)
                                        continue;

                                    target[nx][ny] = heart[i][j] / 4;
                                    heart[nx][ny] = heart[i][j] / 4;    // 초기 체력 갱신
                                    targetCount[nx]++;  // 타겟 새로 생겼으므로 count 증가
                                }

                            }

                            heart[i][j] = 0; // 타겟 맞춤
                        } else {    // 공격력이 낮아서 표적 체력 남은 경우
                            target[i][j] -= bullet[count];
                        }
                    }

                    // 재귀돌면서 총알 발사하기
                    recur(count + 1, score, targetCount, heart, target);

                    // 배열 복사본 초기화
                    for (int k = 0; k < N; k++) {
                        target[k] = Arrays.copyOf(targetOrigin[k], N);
                        heart[k] = Arrays.copyOf(heartOrigin[k], N);
                    }
                    targetCount = Arrays.copyOf(targetCountOrigin, N);
                    score = totalScore;
                    break;
                }
            }
        }

    }

}


