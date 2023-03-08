![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2011066&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> BOJ 11066 파일 합치기

# 💻**Code**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	static int k;
	static int[] nums, sums;
	static int[][] dp;
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		int t = Integer.parseInt(br.readLine());

		while(t-- > 0) {
			k = Integer.parseInt(br.readLine());
			// 각 챕터의 크기를 저장하는 배열
			nums = new int[k];
			// 각 챕터의 크기의 부분합을 저장하는 배열, i+1번째 인덱스에는 nums[0]부터 nums[i]까지의 합이 저장되어 있다.
			sums = new int[k+1];
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int i=0; i<k; i++) {
				nums[i] = Integer.parseInt(st.nextToken());
			}
			for(int i=1; i<=k; i++) {
				sums[i] = sums[i-1]+nums[i-1];
			}
			// d[i][j]에는 챕터i부터 챕터j까지 합치는 최소 비용을 저장
			dp = new int[k][k];
			for(int i=0; i<k; i++) {
				Arrays.fill(dp[i], Integer.MAX_VALUE); // 최대값으로 초기화
				dp[i][i] = 0; // 챕터 하나는 합치는데 비용이 들지 않는다.
			}
			for(int i=0; i<k; i++) { // 붙이는 챕터 수
				for(int j=0; j<k; j++) { // 붙일 챕터의 인덱스
					method(j, j+i); // 챕터를 붙이는 비용을 순서대로 탐색
				}
			}
			bw.write(dp[0][k-1] + "\n");
		}
		bw.flush();
		bw.close();
		br.close();
	}

	static void method(int start, int end) {
		if(end >= k) { // 입력받은 챕터 수보다 커지면 건너뜀
			return;
		}

		// [start][end]는 start부터 i까지 붙이는 비용 + (i+1)부터 end까지 붙이는 비용 + start에서 end까지의 부분합
		for(int i=start; i<end; i++) {
			dp[start][end] = Math.min(dp[start][end], dp[start][i] + dp[i+1][end] + sums[end+1] - sums[start]);
		}
	}
}
```

# **🔑Description**

> 챕터 순서대로 이어붙여서 책을 만들어야되는데 뒤죽박죽으로 이어붙였었다.\
> 처음에는 이웃한 챕터 중에서 합치는 비용이 가장 작은 곳을 찾아 하나로 합치는 방법으로 하는 그리디 방식으로 생각했었다.\
> 하지만 첫번째 예제처럼 이웃하면서 가장 작을 곳을 찾는 방식이 답이 아닐수 있다는 것을 알았다.\
> 이후 dp를 활용한 방법으로 해결했다.

# **📑Related Issues**

> 해결한 방식이 분할정복이라고 생각했었는데 백준에는 dp로 써있었다.\
> 알고리즘 이름이 뭔지 중요한건 아니지만 개념의 차이는 궁금했다.\
> 분할정복과 DP는 문제를 쪼개서 해결한다는 점에서는 같지만 분할정복은 부분문제들끼리 중복되지 않지만 DP는 부분문제들끼리 중복되기도 하고 상위 문제 해결에 재활용하고 분할정복은 memoization을 사용하지 않지만 DP는 memoization을 사용한다는 점이 다르다고 한다.\
> memoization여부로 구분 가능하다고 한다.

# **🕛Resource**

| Memory    | Time    |
| --------- | ------- |
| 34596`KB` | 736`ms` |
