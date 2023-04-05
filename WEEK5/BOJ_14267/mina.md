![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2014267&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 14267 회사 문화 1](https://www.acmicpc.net/problem/14267)

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

	public static void main(String args[]) throws Exception {
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		StringBuilder sb = new StringBuilder();
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());

		int[] emp = new int[n + 1]; // 직속상사 번호 저장
		int[] compliment = new int[n + 1];   // n번 사원이 받은 칭찬
		int[] dp =  int[n + 1];

		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 1; i < n + 1; i++) {
			emp[i] = Integer.parseInt(st.nextToken());
		}

		for (int i = 0; i < m; i++) {new
			st = new StringTokenizer(br.readLine(), " ");
			int idx = Integer.parseInt(st.nextToken());
			int p = Integer.parseInt(st.nextToken());
			compliment[idx] += p;   // 같은 사원을 여러번 칭찬 할 수 있음
		}

		emp[1] = 0;

		for (int i = 2; i < n + 1; i++) {
			dp[i] = dp[emp[i]] + compliment[i]; // 내 상사가 받은 누적 칭찬 + 내가 받은 칭찬
		}

		for (int i = 1; i < n + 1; i++) {
			sb.append(dp[i]).append(" ");
		}
		bw.write(sb.toString());
		bw.close();

	}
}
```

<br>
<br>

# **🔑Description**

> 1. 한 사원이 칭찬을 받으면 그 사원의 직속 부하들이 연쇄적으로 내리 칭찬받음\
> 2. 직속 상사의 번호는 자신의 번호보다 작음\
>    이 두가지 조건을 보고 배열 트리를 만들어서 dp로 풀었다.\
>    자신의 부모 번호를 배열에 넣어줘서 그 배열에서 자신의 부모 정보를 가져오고 그 부모가 받은 칭찬 + 내가 받은 칭찬을 dp에 넣어줬다.

<br>
<br>

# **📑Related Issues**

> 사원들이 칭찬을 중복으로 받을 수 있는건지 모르겠어서 중복이 없다고 생각하고 풀어서 한번 틀렸다.\
> 사실 문제에 그런 말 없어서 중복이 있다고 생각하는게 맞긴함....ㅎㅅㅎ

<br>
<br>

# **🕛Resource**

| Memory  | Time  | Implementation Time |
| ------- | ----- | ------------------- |
| 53016KB | 472ms | 30 Minutes          |
