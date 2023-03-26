![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2010986&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> BOJ 10986 나머지 합

# 💻**Code**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		int[] nums = new int[n];
		st = new StringTokenizer(br.readLine());
		for(int i=0; i<n; i++) {
			nums[i] = Integer.parseInt(st.nextToken()) % m; // m으로 나눈 나머지를 저장
		}
		long[] dp = new long[m]; // i번째에는 m으로 나눴을 때 나머지가 i인 부분합들의 개수가 저장
		int index = 0; // 배열을 회전시켜야하는데 뺏다넣는 작업을 직접하면 오래 걸리니까 인덱스를 조절
		long answer = 0;
		for(int i=0; i<n; i++) { // [0, i], [1, i], ..., [i, i]의 부분합을 계산
			index = (index + m-nums[i]) % m; // 현재 숫자가 더해진다면 나머지들이 바뀌는걸 인덱스에 반영
			dp[(index+nums[i])%m]++; // i번째 숫자 하나만 속하는 부분합을 적용
			answer += dp[index]; // 개수 증가
		}

		bw.write(answer + "\n");
		bw.flush();
		bw.close();
		br.close();
	}
}
```

# **🔑Description**

> n^2가 걸리는 풀이만 생각하다가 입력이 너무 커서 이전의 결과를 저장했다가 반영하는 방법을 고민했다.\
> 앞에서부터 숫자 하나하나를 추가한다는 생각으로 구현했다.\
> 인덱스 조절하는 부분이 어려웠다.

# **📑Related Issues**

>

# **🕛Resource**

| Memory     | Time    |
| ---------- | ------- |
| 116752`KB` | 516`ms` |
