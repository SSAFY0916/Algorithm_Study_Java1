![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2015486&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 15486 퇴사 2](https://www.acmicpc.net/problem/15486)

<br>
<br>

# **💻Code**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

	public static void main(String[] args) throws Exception {

		int N = Integer.parseInt(br.readLine());

		int[] time = new int[N + 1]; // 상담에 소요되는 날짜
		int[] salary = new int[N + 1]; // 그 상담을 끝냈을 때 받는 돈
		int[] dp = new int[N + 51]; // 마지막날(N) + 최대 상담일 수(50)

		for (int i = 1; i <= N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			time[i] = Integer.parseInt(st.nextToken());
			salary[i] = Integer.parseInt(st.nextToken());
		}

		for (int i = 1; i <= N; i++) {

			// 3가지 중 최댓값 찾음
			// 1. 이미 계산 된 dp[i + time[i] - 1] (이전에 2, 3으로 이미 계산된 값이 들어있을 경우)
			// 2. i + time[i]까지 상담안함 : 전날까지 번돈(dp[i + time[i] - 2])) 그대로 유지
			// 3. i + time[i]까지 상담함 : i번째 날까지 번 돈의 최댓값에 그 날 할 수 있는 상담해서 돈 벌기(dp[i - 1] +
			// salary[i]))
			dp[i + time[i] - 1] = Math.max(Math.max(dp[i + time[i] - 1], dp[i + time[i] - 2]), dp[i - 1] + salary[i]);

			// 전날까지 번돈 vs 오늘까지 번돈 중 최댓값
			dp[i] = Math.max(dp[i - 1], dp[i]);
		}

		bw.write(Integer.toString(dp[N]));

		bw.close();
	}

}
```

<br>
<br>

# **🔑Description**

> 현재까지 번 돈과 지금 할 수 있는 상담으로 번 돈을 합하는 식으로 dp값을 구하려고 했다.\
> 전날 번 돈과 다른 날짜에서 계산된 돈까지 비교해서 최댓값을 dp[i]에 넣어줬다.

<br>
<br>

# **📑Related Issues**

> 미래의 dp값을 계산하는게 좀 복잡했다....\
> i번째 날부터 time[i]동안 상담하면 돈을 번 날짜를 i + time[i] 으로 해야할지 i + time[i] - 1로 해야할지 헷갈렸다.\
> 그냥 상담 끝난 날 그 돈 받는거임!!! i + time[i] - 1 이 맞음

<br>
<br>

# **🕛Resource**

| Memory    | Time   | Implementation Time |
| --------- | ------ | ------------------- |
| 313708 KB | 656 ms | 40 Minutes          |
