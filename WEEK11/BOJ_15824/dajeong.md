![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2015824&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 15824_너 봄에는 캡사이신이 맛있단다](https://www.acmicpc.net/problem/15824)

<br>
<br>

# **Code**

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/*
 * 틀린 풀이
 */
public class Main {

    static final int MOD = 1000000007;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            list.add(Integer.parseInt(st.nextToken()));
        }

        Collections.sort(list);

        // 2^n - 1을 메모이제이션할 배열 선언
        long[] pow = new long[N + 1];
        long t = 1;
        for (int i = 0; i < N; i++) {
            pow[i] = t - 1;
            t = (t << 1) % MOD;
        }

        int min = 0, max = 0;
        for (int i = 0; i < N; i++) {
            min += pow[i] * list.get(i);
            max += pow[i] * list.get(N - 1 - i);
            min %= MOD;
            max %= MOD;
        }

        long answer = max - min;
        if (answer < 0) {
            answer += MOD;
        }
        System.out.println(answer);
    }

}
```

<br>
<br>

# **🔑Description**

> 설계 시간: 1hr+a

> 구현 시간: 1hr+a
<aside>
💡 설계 아이디어
    
    - 어떤 조합을 선택하든, 최대값과 최소값만 알면 된다.
    - 숫자 후보를 정렬 후, 최댓값 혹은 최솟값을 선택한 후에 해당 최대/최소를 골랐을 때의 조합을 만든다. 
    - (2^(N-1)-1) (최대1-최소1) + (2^(N-2)-1) (최대2-최소2) + ...
    - 2^(N)-1을 매번 계산하지 않고 메모이제이션을 활용
    - 중간 연산 값에 나머지 연산을 매번 해주고, 마지막 최종 답이 음수일 경우를 고려해준다. 
    
</aside>

<br>
<br>

# **📑Related Issues**

> Related Issues
<aside>

    - 왜 틀렸는지도 모르겠고.. 그렇다고 이 풀이 버리고 분할 정복으로 푸는 것도 싫다.. 
    - 나랑 비슷하게 푼 사람이 있긴 하던데 내가 왜 틀렸는지 모르겠다. 맞왜틀!
    - 문제 제목부터 이해 안됨.. ㅋ

</aside>

<br>
<br>

# **🕛Resource**

| Memory | Time  | Implementation Time |
| -- |-------|---------------------|
| KB | ms | 2 Hour + a |
