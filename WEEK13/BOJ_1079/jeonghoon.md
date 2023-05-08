![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:FF658D,100:FFCB32&text=BOJ%201079&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 1079 마피아](https://www.acmicpc.net/problem/1079)

<br>
<br>

# **💻Code**

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_1079 {
    static int attendNum;
    static int[] crimes;
    static int[][] arr;
    static boolean[] alive;
    static int ejIdx;
    static int max = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        attendNum = Integer.parseInt(br.readLine());

        crimes = new int[attendNum];

        arr = new int[attendNum][attendNum];
        alive = new boolean[attendNum];
        Arrays.fill(alive, true);

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < attendNum; i++) {
            crimes[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 0; i < attendNum; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < attendNum; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        ejIdx = Integer.parseInt(br.readLine());

        play(attendNum, 0);

        System.out.println(max);
    }

    private static void play(int liveNum, int day) {
        // 은진이가 죽었거나, 한명만 살아 있을 때
        if (!alive[ejIdx] || liveNum == 1) {
            max = Math.max(max, day);
            return;
        }
        // 살아 있는 사람이 짝수이면 밤
        if (liveNum % 2 == 0) {
            // 모든 사람을 죽이는 경우를 탐색
            for (int i = 0; i < attendNum; i++) {
                if (!alive[i] || i == ejIdx)
                    continue;

                alive[i] = false;
                // 죽인 사람에 따라 범죄 지수 수정
                for (int j = 0; j < attendNum; j++) {
                    crimes[j] += arr[i][j];
                }
                play(liveNum - 1, day + 1);
                alive[i] = true;
                // 범죄 지수 복구
                for (int j = 0; j < attendNum; j++) {
                    crimes[j] -= arr[i][j];
                }
            }
        }
        // 살아 있는 사람이 홀수이면 아침
        else {
            int score = 0;
            int killIdx = 0;

            // 범죄 지수가 가장 높은 사람을 죽임
            for (int i = 0; i < attendNum; i++) {
                if (alive[i] && score < crimes[i]) {
                    score = crimes[i];
                    killIdx = i;
                }
            }
            alive[killIdx] = false;
            play(liveNum - 1, day);
            alive[killIdx] = true;
        }
    }
}

```

<br>
<br>

# **🔑Description**

> 살아 있는 사람의 수가 짝수인 경우와, 홀수인 경우 케이스를 나누어서 구현하면 쉽게 해결할 수 있었습니다.

<br>
<br>

# **📑Related Issues**

> 전역 변수를 사용하지 않고 함수를 들어갈 때마다 배열을 복사해서 사용하고자 하였으나, 메모리가 터지는 관계로 고쳤습니다.

<br>
<br>

# **🕛Resource**

| Memory  | Time  |
| ------- | ----- |
| 22080KB | 492ms |
