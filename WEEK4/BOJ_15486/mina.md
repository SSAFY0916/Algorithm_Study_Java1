![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2015486&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **πProblem**

> [BOJ 15486 ν΄μ¬ 2](https://www.acmicpc.net/problem/15486)

<br>
<br>

# **π»Code**

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

		int[] time = new int[N + 1]; // μλ΄μ μμλλ λ μ§
		int[] salary = new int[N + 1]; // κ·Έ μλ΄μ λλμ λ λ°λ λ
		int[] dp = new int[N + 51]; // λ§μ§λ§λ (N) + μ΅λ μλ΄μΌ μ(50)

		for (int i = 1; i <= N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			time[i] = Integer.parseInt(st.nextToken());
			salary[i] = Integer.parseInt(st.nextToken());
		}

		for (int i = 1; i <= N; i++) {

			// 3κ°μ§ μ€ μ΅λκ° μ°Ύμ
			// 1. μ΄λ―Έ κ³μ° λ dp[i + time[i] - 1] (μ΄μ μ 2, 3μΌλ‘ μ΄λ―Έ κ³μ°λ κ°μ΄ λ€μ΄μμ κ²½μ°)
			// 2. i + time[i]κΉμ§ μλ΄μν¨ : μ λ κΉμ§ λ²λ(dp[i + time[i] - 2])) κ·Έλλ‘ μ μ§
			// 3. i + time[i]κΉμ§ μλ΄ν¨ : iλ²μ§Έ λ κΉμ§ λ² λμ μ΅λκ°μ κ·Έ λ  ν  μ μλ μλ΄ν΄μ λ λ²κΈ°(dp[i - 1] +
			// salary[i]))
			dp[i + time[i] - 1] = Math.max(Math.max(dp[i + time[i] - 1], dp[i + time[i] - 2]), dp[i - 1] + salary[i]);

			// μ λ κΉμ§ λ²λ vs μ€λκΉμ§ λ²λ μ€ μ΅λκ°
			dp[i] = Math.max(dp[i - 1], dp[i]);
		}

		bw.write(Integer.toString(dp[N]));

		bw.close();
	}

}
```

<br>
<br>

# **πDescription**

> νμ¬κΉμ§ λ² λκ³Ό μ§κΈ ν  μ μλ μλ΄μΌλ‘ λ² λμ ν©νλ μμΌλ‘ dpκ°μ κ΅¬νλ €κ³  νλ€.\
> μ λ  λ² λκ³Ό λ€λ₯Έ λ μ§μμ κ³μ°λ λκΉμ§ λΉκ΅ν΄μ μ΅λκ°μ dp[i]μ λ£μ΄μ€¬λ€.

<br>
<br>

# **πRelated Issues**

> λ―Έλμ dpκ°μ κ³μ°νλκ² μ’ λ³΅μ‘νλ€....\
> iλ²μ§Έ λ λΆν° time[i]λμ μλ΄νλ©΄ λμ λ² λ μ§λ₯Ό i + time[i] μΌλ‘ ν΄μΌν μ§ i + time[i] - 1λ‘ ν΄μΌν μ§ ν·κ°λ Έλ€.\
> κ·Έλ₯ μλ΄ λλ λ  κ·Έ λ λ°λκ±°μ!!! i + time[i] - 1 μ΄ λ§μ

<br>
<br>

# **πResource**

| Memory    | Time   | Implementation Time |
| --------- | ------ | ------------------- |
| 313708 KB | 656 ms | 40 Minutes          |
