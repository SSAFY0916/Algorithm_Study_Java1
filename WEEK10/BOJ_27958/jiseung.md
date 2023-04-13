![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2027958&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> BOJ 27958 사격 연습

# 💻**Code**

```java
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
```

# **🔑Description**

> 총알이 최대 5개, 쏠 수 있는 행의 개수가 최대 8개라서 8^5개의 총을 쏘는 행의 중복순열을 만들어서 시뮬레이션을 돌렸다\
> 총알의 개수만큼 총알을 쏠 행의 번호가 정해지면 표적을 만날때 까지 왼쪽에서 부터 오른쪽으로 탐색했다.\
> 보너스 표적을 만나면 점수를 얻고 표적을 삭제했고\
> 일반 표적을 만나면 체력을 줄이고 표적의 체력이 0 이하가 되면 삭제하고 기존의 체력이 4 이상이었으면 새로운 표적들을 만들었다.\
> 표적의 체력과 표적의 점수를 모두 가지고 있어야 해서 배열을 두 개 만들어 사용했다.

# **📑Related Issues**

> 중복순열을 perm라는 배열에 저장해두었는데 매번 perm[i] 하기 귀찮아서 foreach문 사용했다가 i가 필요해서 다시 그냥 for문으로 바꾸는 과정에서 perm[i]로 수정하지 않은 부분이 있어서 틀렸었다.

# **🕛Resource**

| Memory    | Time    |
| --------- | ------- |
| 69964`KB` | 284`ms` |
