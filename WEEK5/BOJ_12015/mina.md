![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2012015&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 12015 가장 긴 증가하는 부분 수열 2](https://www.acmicpc.net/problem/12015)

<br>
<br>

# **💻Code**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

	public static void main(String[] args) throws Exception {
		int N = Integer.parseInt(br.readLine());
		int[] A = new int[N + 1];
		int[] dp = new int[N + 1];
		List<Integer> list = new ArrayList<Integer>();
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");

		for (int i = 1; i < N + 1; i++) {
			A[i] = Integer.parseInt(st.nextToken());
		}

		dp[1] = 1;
		list.add(A[1]);

		for (int i = 2; i < N + 1; i++) {
			if (list.get(list.size() - 1) < A[i]) { // 수열 맨 뒤에 붙일 수 있으면 붙임
				list.add(A[i]);
			} else { // 못붙이는 경우 교환할 수 있는 값 찾기 - lowerbound
				int start = 0;
				int end = list.size();
				int middle = -1;

				while (start < end) {
					middle = (start + end) / 2;

					if (list.get(middle) == A[i]) { // 같은 숫자가 있는 경우
						start = middle;
						break;
					} else if (list.get(middle) < A[i]) {
						start = middle + 1;
					} else {
						end = middle;
					}
				}
				list.set(start, A[i]);
			}
		}

		bw.write(Integer.toString(list.size()));
		bw.close();

	}
}

```

<br>
<br>

# **🔑Description**

> 숫자를 하나씩 뽑아서 수열을 만든다.\
> 증가해야하므로 현재까지 만들어진 수열 맨 뒤에있는 숫자를 확인해서 붙일 수 있으면 붙인다.\
> 못 붙이면 수열 안에서 교환할 수 있는 값을 찾아 교환한다.\

<br>
<br>

# **📑Related Issues**

> 첨에는 교환하지 않고 전부 추가해주는 방식이었다.\
> 인풋이 10 20 30 40 1 2 3 이렇게 있으면 리스트가 1 2 3 10 20 30 40 이렇게 만들어짐...\
> 물론 이게 수열은 아님 저 숫자랑 저 숫자를 가진 인덱스도 같이 저장해서 쓸려고함\
> 근데 리스트를 저렇게 만들다보면 list.add(i, n) 이렇게 쓰게되는데 이게 O(n)이라 시간초과 났다...\
> 사실 오랫동안 고민해봐도 모르겠어서 블로그에서 살짝 읽어봤는데 교환할수 있는 값을 찾으라그래서 보자마자 lowerbound로 교환할수있는거 찾아서 교환하는 방식 썼다..ㅇ<-< 넘 힘들고 졸려

<br>
<br>

# **🕛Resource**

| Memory   | Time  | Implementation Time |
| -------- | ----- | ------------------- |
| 153268KB | 624ms | 5 Hours             |
