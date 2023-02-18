![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2021758&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 21758 꿀 따기](https://www.acmicpc.net/problem/21758)

<br>
<br>

# **Code**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {

	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) throws Exception {
		int N = Integer.parseInt(br.readLine());
		int[] honey = new int[N];   //꿀 저장
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		int total = 0;
		int min = 1000000000;   //최댓값
		int max = 0;    //최솟값

		for (int i = 0; i < N; i++) {
			honey[i] = Integer.parseInt(st.nextToken());
			total += honey[i];
			if (honey[i] > max)
				max = honey[i];
			if (honey[i] < min)
				min = honey[i];
		}

        // 꿀을 최대로 딸 수 있는 경우
        //-> 통이 왼쪽 끝에 있거나 오른쪽 끝에 있거나 가운데(양끝이 아닌 곳)에 있음

        // 1. 벌통이 양 끝에 없는 경우
        // -> 양 끝이 아닌 값 중 최댓값인 자리에 벌통이 있고 벌이 양 끝에서 출발해야 가장 많은 꿀을 딸 수 있음
		int center = total - honey[0] - honey[N - 1] + max;


        // 2. 벌통이 오른쪽 끝에 있는 경우
		int right = 2 * total - honey[0];   //한마리를 왼쪽 끝에 뒀을때 모든 꿀의 합
		int rightSum = 0;   //누적합
		int rightMax = 0;   //최댓값


		for (int i = 1; i < N; i++) {
            //다른 한마리를 맨 왼쪽에서 오른쪽으로 이동시키면서 이 벌이 지나가지 못하는 꿀들을 누적하여 합함
            //전체 꿀에서 누적 합을 뺐을 때 최댓값 구하기
			rightSum += honey[i - 1];
			int temp = right - rightSum - 2 * honey[i];
			if (rightMax < temp) {
				rightMax = temp;
			}
		}

        // 3. 벌통이 왼쪽 끝에 있는 경우
		int left = 2 * total - honey[N - 1];   //한마리를 오른쪽 끝에 뒀을때 모든 꿀의 합
		int leftSum = 0;    //누적합
		int leftMax = 0;    //최댓값

		for (int i = N - 2; i >= 0; i--) {
             //다른 한마리를 맨 오른쪽에서 왼쪽으로 이동시키면서 이 벌이 지나가지 못하는 꿀들을 누적하여 합함
            //전체 꿀에서 누적 합을 뺐을 때 최댓값 구하기
			leftSum += honey[i + 1];
			int temp = left - leftSum - 2 * honey[i];
			if (leftMax < temp) {
				leftMax = temp;
			}
		}

        // 3가지 경우 중 최대값 출력
		bw.write(Math.max(Math.max(center, rightMax), leftMax) + "");
		bw.close();
	}
}

```

<br>
<br>

# **🔑Description**

> 최대로 꿀을 딸 수 있으려면 벌들 동선이 최대한 겹치거나 (벌통이 양 끝 중 하나에 위치함)\
> 칸의 길이가 짧은 경우에는 벌통이 가운데 어디쯤에 있을 수 있음\
> 이 세 가지 경우를 계산했음 벌통이 양 끝에 위치하는 경우는 sliding window로 누적합 구하는 방식 사용함

<br>
<br>

# **📑Related Issues**

> 벌통을 두는 경우를 왼쪽, 오른쪽 둘 다 구하지 않고 양 끝 값 중에 더 큰 값이 있는 쪽으로 하나만 골라서 구한다음에 center랑 비교할 수도 같은데 나중에 시간나면 해보기....ㅎㅎ

<br>
<br>

# **🕛Resource**

| Memory  | Time  |
| ------- | ----- |
| 26360KB | 240ms |
