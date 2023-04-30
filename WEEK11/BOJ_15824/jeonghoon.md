![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:FF658D,100:FFCB32&text=BOJ%2015824&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 15824 너 봄에는 캡사이신이 맛있단다](https://www.acmicpc.net/problem/15824)

<br>
<br>

# **💻Code**

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_15824 {

    static final int MOD_NUM = 1_000_000_007;

    // 기존에 사용된 pow 값을 따로 저장해 둠으로써 연산 속도 상승 효과 기대
    static long[] powArr = new long[300_001];

    // 분할 정복을 이용한 거듭제곱 함수 (지수의 밑은 무조건 2 이므로 매개변수로 받지 않음)
    public static long pow(int n) {
        if (powArr[n] != 0)
            return powArr[n];
        if (n % 2 == 1) {
            long tmp = pow((n - 1) / 2);
            return powArr[n] = 2 * tmp * tmp % MOD_NUM;
        }
        long tmp = pow(n / 2);
        return powArr[n] = pow(n / 2) * pow(n / 2) % MOD_NUM;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int num = Integer.parseInt(br.readLine());
        int[] numArr = new int[num];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < num; i++) {
            numArr[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(numArr);

        powArr[0] = 1;
        powArr[1] = 2;

        long sum = 0;

        /**
         * 현재 Index의 값이 최솟값으로 만들어 지는 부분집합의 갯수 : min_i
         * 현재 Index의 값이 최댓값으로 만들어 지는 부분집합의 갯수 : max_i
         * S_i : max_i - min_i 라고 할 때
         * 0부터 n - 1까지 모든 S_i를 더해주면 O(N) 시간동안 고통지수의 합을 구할 수 있음
         */
        for (int i = 0; i < num; i++) {
            sum += numArr[i] * (pow(i) - pow(num - i - 1) + MOD_NUM) % MOD_NUM;
            sum %= MOD_NUM;
        }

        System.out.println(sum);
    }

}
```

<br>
<br>

# **🔑Description**

> 현재 Index의 값이 최솟값으로 만들어 지는 부분집합의 갯수 : min_i
> 현재 Index의 값이 최댓값으로 만들어 지는 부분집합의 갯수 : max_i
> S_i : max_i - min_i 라고 할 때
> 0부터 n - 1까지 모든 S_i를 더해주면 O(N) 시간동안 고통지수의 합을 구할 수 있음

<br>
<br>

# **📑Related Issues**

> 처음에 O(N*N) 으로 부분집합을 구하는 방법 밖에 생각 나지 않아서 너무너무 슬펐지만 나중에 오랫동안 생각하다가 위의 식이 떠올라서 행복했습니다.

<br>
<br>

# **🕛Resource**

| Memory  | Time  |
| ------- | ----- |
| 58904KB | 820ms |
