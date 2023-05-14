![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%201079&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> BOJ 1079 마피아

# 💻**Code**

```java
import java.io.*;
import java.util.*;

public class Main {

    static int n, num, answer;
    static int[][] reacts;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] scores = new int[n];
        for (int i = 0; i < n; i++) {
            scores[i] = Integer.parseInt(st.nextToken());
        }
        reacts = new int[n][n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                reacts[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        num = Integer.parseInt(br.readLine());

        int suspect = -1; // 낮에 죽을 사람
        if (n % 2 == 1) { // 홀수면 낮부터 시작
            for (int i = 0; i < n; i++) {
                if (suspect < 0 || scores[suspect] < scores[i]) { // 유죄지수가 높은 사람이 죽음
                    suspect = i;
                }
            }
        }
        permutation(1, 1<<suspect, scores);

        bw.write(answer + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    static void permutation(int count, int flag, int[] scores) {
        for (int i = 0; i < n; i++) { // 밤에 마피아가 죽일 사람 고르기
            if (i == num || (flag & (1 << i)) != 0) // 은진이거나 이미 죽은 사람은 건너뜀
                continue;

            for (int j = 0; j < n; j++) { // i번째 사람을 죽인 유죄 지수 반영하기
                scores[j] += reacts[i][j];
            }
            
            int suspect = -1; // 낮에 죽을 사람
            for (int j = 0; j < n; j++) {
                if (i == j || (flag & (1 << j)) != 0) // 직전 밤에 죽엇거나 이미 죽은 사람이면 건너 뜀
                    continue;
                if (suspect < 0 || scores[suspect] < scores[j]) { // 유죄지수가 높은 사람이 죽음
                    suspect = j;
                }
            }
            
            if (suspect == num) // 은진이가 죽으면 여기까지 탐색
                answer = Math.max(answer, count);
            else // 은진이가 살아있으면 한 턴 더 진행
                permutation(count + 1, flag | (1 << i) | (1 << suspect), scores);

            for (int j = 0; j < n; j++) { // 밤에 죽일 사람 다시 고르기 위해 유죄 지수 되돌리기
                scores[j] -= reacts[i][j];
            }
        }
    }
}

```

# **🔑Description**

> 사람 수가 홀수면 낮을 보낸다\
> 이후 은진이가 죽을 때까지 밤과 낮을 반복한다\
> 밤에 죽을 사람은 순열 구하는 것처럼 구하고 낮에 죽을 사람은 유죄 지수로 구한다\
> 이번 탐색의 결과가 다음 탐색의 결과게 영향을 끼치지 않도록 유지 죄수를 갱신하면 다시 되돌리는 부분도 추가했다\ 

# **📑Related Issues**

> 메모리 초과, 시간 초과, 틀렸습니다 다 봤다\
> 틀렸습니다는 사람이 홀수면 낮부터 시작하는 문제를 안 읽어서 였다\
> 메모리 초과는 매 탐색마다 유죄 지수나 사람들이 살아있는지 여부를 저장하는 배열을 만들어서 그런것 같다\
> 시간 초과는 먼저 순열을 구하고 구한 순열을 처음부터 반복하면서 시뮬레이션 하지 않고\
> 순열을 구하는 것과 시뮬레이션을 동시에 해서 중복되는 과정을 다음 탐색에서 활용할 수 있도록 했다\
> md 작성하면서 찾은 건데 사람 수가 짝수여서 처음에 낮을 보내지 않으면 suspect가 -1이라서 flag가 1<<-1 이 되는데 잘 작동하는게 신기하다

# **🕛Resource**

| Memory    | Time    |
| --------- | ------- |
| 22004`KB` | 644`ms` |
