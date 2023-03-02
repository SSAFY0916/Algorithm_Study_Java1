![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2015486&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 문제번호 문제이름](https://www.acmicpc.net/problem/15486)

<br>
<br>

# **Code**

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_15486 {
    static int[] dp;
    static Consulting[] consultings;

    // 컨설팅에 걸리는 시간과 받을 수 있는 비용
    static class Consulting {
        int time;
        int price;

        public Consulting(int time, int price) {
            this.time = time;
            this.price = price;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int remainDate = Integer.parseInt(br.readLine());

        dp = new int[remainDate + 1];
        consultings = new Consulting[remainDate + 1];

        // 입력
        for (int i = 1; i <= remainDate; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int time = Integer.parseInt(st.nextToken());
            int price = Integer.parseInt(st.nextToken());
            consultings[i] = new Consulting(time, price);
            // 컨설팅이 끝나는 시점이 퇴사 이전이라면 끝나는 지점의 dp값 갱신
            if (i + time - 1 <= remainDate)
                // 현재 저장되어 있는 값과, 입력받은 컨설팅의 비용을 비교하여 갱신
                dp[i + time - 1] = Math.max(price, dp[i + time - 1]);
        }

        for (int i = 1; i <= remainDate; i++) {
            // 하루 전날까지의 dp값과 지금까지의 dp값을 비교하여 dp값 갱신
            dp[i] = Math.max(dp[i], dp[i - 1]);
            if (i + consultings[i].time - 1 <= remainDate) {
                // 현재 시작되는 컨설팅의 시간이 종료되는 시점의 값 갱신
                /***
                 * // 하루 전날까지의 dp값 + 현재 시작되는 컨설팅의 비용과
                 * // 현재 저장되어 있는 컨설팅이 시간이 종료되는 시점의 dp값을 비교
                 */
                dp[i + consultings[i].time - 1] = Math.max(dp[i - 1] + consultings[i].price,
                        dp[i + consultings[i].time - 1]);
            }
        }

        System.out.println(dp[remainDate]);
    }

}
```

<br>
<br>

# **🔑Description**

> 현재 인덱스의 컨설팅 시간과 비용을 이용하여, 끝나는 지점까지의 최대 비용을 구해 나가는 방식으로 문제를 풀었습니다.

<br>
<br>

# **📑Related Issues**

> dp를 이용한 문제풀이를 할 때 매번 현재 인덱스까지의 값을 갱신하는 방식으로 풀이를 하였지만, 이번 문제의 경우 현재 인덱스 값을 이용하여 다른 인덱스의 dp값을 갱신해 나가는 방식으로 풀었습니다. 이러한 방식으로 풀이를 해본 경험이 없어서 점화식을 세우는 것이 다소 어려웠습니다.

<br>
<br>

# **🕛Resource**

| Memory | Time   |
| ------ | ------ |
| 339004KB | 812ms |