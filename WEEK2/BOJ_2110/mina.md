![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%202110&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 2110 공유기 설치](https://www.acmicpc.net/problem/2110)

<br>
<br>

# **Code**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	static int[] wifi;
	static int N;
	static int C;

	public static void main(String[] args) throws Exception {

		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		wifi = new int[N];
		for (int i = 0; i < N; i++) {
			wifi[i] = Integer.parseInt(br.readLine());
		}
		Arrays.sort(wifi);

		int left = 0;
		int right = 1000000000;
		int result = 0;

        // 바이너리 서치로 제일 균일한 distance를 찾는다
		while (left <= right) {

			int middle = (left + right) / 2;

			if (checkDistance(middle)) {
                // 일단 저장하고 더 균일할 수 있는지 찾아본다
				result = middle;
				left = middle + 1;
			} else {
				right = middle - 1;
			}

		}

		bw.write(Integer.toString(result));

		bw.close();
	}

    //distance간격 마다 공유기를 뒀을때 C개가 나오는지 check
	static boolean checkDistance(int distance) {

		int prePoint = wifi[0];
		int count = 1;

		for (int i = 1; i < N; i++) {
			if (wifi[i] - prePoint >= distance) {
				count++;
				prePoint = wifi[i];
			}
		}


		return count >= C;
	}
}

```

<br>
<br>

# **🔑Description**

> 가장 인접한 두 공유기 사이의 거리를 최대로 하려면 최대한 비슷한 간격으로 놓아야 할 것 같았다.\
> 그래서 그 간격을 바이너리 서치로 찾았다 처음 범위는 0~1,000,000,000 였고 여기서 점점 줄여나갔다.\
> 줄여 나가는 방법은 그 간격으로 공유기를 놨을때 문제에서 준 공유기 개수보다 작으면 범위를 오른쪽으로 옮겼고 문제 공유기 개수보다 크면 범위를 왼쪽으로 옮겼다.\
> 문제 공유기 개수보다 크거나 같을때의 간격을 일단 저장해놓고 더 균일한 간격이 있는지 찾는 방식으로 했다.

<br>
<br>

# **📑Related Issues**

> 바이너리 서치라는 아이디어를 생각하면 크게 어렵지 않은 문제였다.\
> 그래도 좀 더 연습해보자옹

<br>
<br>

# **🕛Resource**

| Memory  | Time  |
| ------- | ----- |
| 28060KB | 276ms |
