![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:FF658D,100:FFCB32&text=BOJ%203151&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 3151 합이 0](https://www.acmicpc.net/problem/3151)

<br>
<br>

# **💻Code**

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_3151 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int num = Integer.parseInt(br.readLine());

        // 숫자 별로 나오는 개수를 저장
        int[] appear = new int[20001];
        int[] attends = new int[num];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < num; i++) {
            int skill = Integer.parseInt(st.nextToken());
            attends[i] = skill;
            appear[skill + 10000]++;
        }

        long cnt = 0;

        // N*N의 크기가 100_000_000 이기 때문에 브루트포스를 사용해도 문제가 없다고 판단
        // attends[i], attends[j] 가 모두 양수 / 음수 일때 반대 부호의 appear 만큼 더하는 방법을 사용
        for (int i = 0; i < num; i++) {
            for (int j = i + 1; j < num; j++) {
                if (attends[i] == 0 || attends[j] == 0)
                    continue;
                if (attends[i] > 0 && attends[j] < 0 || attends[i] < 0 && attends[j] > 0)
                    continue;

                int skillSum = attends[i] + attends[j];
                // skillSum의 반대 부호 수의 appear을 찾아야 하므로 부호를 반전시키고 -10000의 인덱스가 0 이므로 10000만큼 더해줌
                int tmp = skillSum * -1 + 10000;
                if (tmp >= 0 && tmp < 20001) {
                    cnt += appear[tmp];
                }
            }
        }

        // 점수 1개가 0일 경우 다른 두 부호의 수들의 곱
        long possCnt = 0;
        for (int i = 0; i < 10000; i++) {
            possCnt += appear[i] * appear[20000 - i];
        }
        possCnt *= appear[10000];

        cnt += possCnt;

        // 점수 3개가 0일 경우는 0이 출현하는 갯수 중 3개를 뽑는 조합의 수
        if (appear[10000] >= 3) {
            cnt += (appear[10000] * (appear[10000] - 1) * (appear[10000] - 2) / 6);
        }

        System.out.println(cnt);
    }
}

```

<br>
<br>

# **🔑Description**

> 경우의 수를 나눠서 브루트 포스로 풀이하였습니다.
> 1. 0이 1개도 섞이지 않은 경우 : 같은 부호의 두 수를 더하고, 그 반대되는 수의 출연 횟수
> 2. 0이 1개 있는 경우 : 절댓값이 같은 다른 부호의 두 수의 출연 횟수의 곱
> 3. 0이 3개 있는 경우 : 0의 출연 횟수 중 3개를 뽑는 조합의 수
> 4. 0이 2개 있는 경우는 존재할 수 없으므로 제외

<br>
<br>

# **📑Related Issues**

> 처음에 0의 갯수에 따라서 경우를 나누지 않아서 많이 고생했습니다.

<br>
<br>

# **🕛Resource**

| Memory  | Time  |
| ------- | ----- |
| 19768KB | 572ms |
