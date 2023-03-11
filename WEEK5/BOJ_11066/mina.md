![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2011066&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 11066 파일 합치기](https://www.acmicpc.net/problem/11066)

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

	static int[] sum;

	public static void main(String[] args) throws Exception {

		int t = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();

		while (t-- > 0) {
			int n = Integer.parseInt(br.readLine());
			int[] chapters = new int[n + 1];
			sum = new int[n + 1]; // 누적함 저장
			int[][] dp = new int[n + 1][n + 1]; // dp[i][j] = i부터 j까지 합치는데 드는 최소 비용
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			for (int i = 1; i < n + 1; i++) {
				chapters[i] = Integer.parseInt(st.nextToken());
				sum[i] = sum[i - 1] + chapters[i];
			}
			for (int i = 1; i < n; i++) {
				dp[i][i + 1] = chapters[i] + chapters[i + 1];
			}
			for (int i = 2; i < n + 1; i++) {
				for (int j = 1; i + j < n + 1; j++) {
					for (int k = j; k < i + j; k++) {

						// a to k 까지의 최소비용을 구한다고 하면
						// a to b + c to k
						// a to c + d to k
						// a to d + e to k
						// a to e + f to k
						// ...
						// a to i + j to k
						// 중 최솟값을 구함

						if (dp[j][i + j] == 0) { // j to i + j 까지의 첫 비용 -> (j to j+1) + (j+2 to i+j)
							dp[j][i + j] = dp[j][k] + dp[k + 1][i + j] + getSum(j, i + j); // getSum은 dp[j][k] + dp[k + 1][i + j] 합치는데 드는 비용
						} else { // 한칸씩 이동하면서 최솟값 구해주기
							dp[j][i + j] = Math.min(dp[j][i + j], dp[j][k] + dp[k + 1][i + j] + getSum(j, i + j));
						}
					}
				}
			}

			sb.append(dp[1][n]).append("\n");
		}
		bw.write(sb.toString());
		bw.close();
	}

	static int getSum(int start, int end) {
		if (start == 0) {
			return sum[end];
		}

		return sum[end] - sum[start - 1];
	}
}

```

<br>
<br>

# **🔑Description**

> 파일이 a부터 k까지 있다고 할때 a to k의 최소비용을 구하는 방법을 다음 중에서 구했다.\
> (a to b) + (c to k) + (a부터 k까지 합치는 비용)\
> (a to c) + (d to k) + (a부터 k까지 합치는 비용)\
> (a to d) + (e to k) + (a부터 k까지 합치는 비용)\
> (a to e) + (f to k) + (a부터 k까지 합치는 비용)\
> ...\
> (a to i) + (j to k) + (a부터 k까지 합치는 비용)\
> 이 중에서 최솟값을 dp[a to k]에 넣었다.\
> i to j 안에서 반복문 N번 돌 수있고 i 랑 j도 N번씩 도니까 O(N^3) 만큼 나오는 것 같다.

<br>
<br>

# **📑Related Issues**

> 설계하는데 4시간, 구현하는데 2시간 걸렸다...\
> 파일 a,b,c, ... , h 까지 써놓고\
> 그 안에서 각각 구간별로 최솟값이 될 수있는 계산식 다 쓴담에 거기서 규칙 찾아보려고 했다.\
> 인덱스 적는 구간(32~52라인)이 진짜 너무 헷갈렸다...ㅠㅠㅠㅠ\

<br>
<br>

# **🕛Resource**

| Memory  | Time  | Implementation Time |
| ------- | ----- | ------------------- |
| 27304KB | 732ms | 6 Hours             |
