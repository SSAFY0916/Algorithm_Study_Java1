![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:FF658D,100:FFCB32&text=BOJ%2027985&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 27985 사격 연습](https://www.acmicpc.net/problem/27985)

<br>
<br>

# **💻Code**

```java
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

```

<br>
<br>

# **🔑Description**

> 행을 선택해서 젤 왼쪽에 있는 표적을 맞추는 함수를 짜고 이 함수를 재귀호출했다.\
> 같은 행을 여러번 선택할 수 있으므로 중복조합 짜듯이 짰다.\
> 그 행에 표적이 없는 경우는 선택하지 못하고 다음 행으로 넘어가야하는데 이때 행을 효율적으로 선택하기 위하여 각 행이 갖고있는 표적 수를 저장하는 targetCount를 만들어서 사용하였다.\
> 보드판을 다 검사하지 않고 targetCount가 1 이상일때 선택하도록 하였다.

<br>
<br>

# **📑Related Issues**

> 표적 없앴을때 초기체력을 점수로 가져와야하는데 체력 까이는 배열..? 에서는 초기체력을 확인 할 수 없었다.\
> 또 표적 맞추면 /4 로 나눠서 초기체력이 생겨서 아주.. 아주 고민햇다\
> 결국 초기체력 확인용으로 배열 하나 더 만들어서 썼다ㅎㅅㅎ\
> 메모리 많이 썼을까봐 걱정했는데 많이 안 쓴 것 같아서 다행쓰

<br>
<br>

# **🕛Resource**

| Memory  | Time  |
| ------- | ----- |
| 53364KB | 188ms |
