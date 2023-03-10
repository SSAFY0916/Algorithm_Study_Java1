![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2021758&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **πProblem**

> [BOJ 21758 κΏ λ°κΈ°](https://www.acmicpc.net/problem/21758)

<br>
<br>

# π»**Code**

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
		int[] honey = new int[N];   //κΏ μ μ₯
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		int total = 0;
		int min = 1000000000;   //μ΅λκ°
		int max = 0;    //μ΅μκ°

		for (int i = 0; i < N; i++) {
			honey[i] = Integer.parseInt(st.nextToken());
			total += honey[i];
			if (honey[i] > max)
				max = honey[i];
			if (honey[i] < min)
				min = honey[i];
		}

        // κΏμ μ΅λλ‘ λΈ μ μλ κ²½μ°
        //-> ν΅μ΄ μΌμͺ½ λμ μκ±°λ μ€λ₯Έμͺ½ λμ μκ±°λ κ°μ΄λ°(μλμ΄ μλ κ³³)μ μμ

        // 1. λ²ν΅μ΄ μ λμ μλ κ²½μ°
        // -> μ λμ΄ μλ κ° μ€ μ΅λκ°μΈ μλ¦¬μ λ²ν΅μ΄ μκ³  λ²μ΄ μ λμμ μΆλ°ν΄μΌ κ°μ₯ λ§μ κΏμ λΈ μ μμ
		int center = total - honey[0] - honey[N - 1] + max;


        // 2. λ²ν΅μ΄ μ€λ₯Έμͺ½ λμ μλ κ²½μ°
		int right = 2 * total - honey[0];   //νλ§λ¦¬λ₯Ό μΌμͺ½ λμ λμλ λͺ¨λ  κΏμ ν©
		int rightSum = 0;   //λμ ν©
		int rightMax = 0;   //μ΅λκ°


		for (int i = 1; i < N; i++) {
            //λ€λ₯Έ νλ§λ¦¬λ₯Ό λ§¨ μΌμͺ½μμ μ€λ₯Έμͺ½μΌλ‘ μ΄λμν€λ©΄μ μ΄ λ²μ΄ μ§λκ°μ§ λͺ»νλ κΏλ€μ λμ νμ¬ ν©ν¨
            //μ μ²΄ κΏμμ λμ  ν©μ λΊμ λ μ΅λκ° κ΅¬νκΈ°
			rightSum += honey[i - 1];
			int temp = right - rightSum - 2 * honey[i];
			if (rightMax < temp) {
				rightMax = temp;
			}
		}

        // 3. λ²ν΅μ΄ μΌμͺ½ λμ μλ κ²½μ°
		int left = 2 * total - honey[N - 1];   //νλ§λ¦¬λ₯Ό μ€λ₯Έμͺ½ λμ λμλ λͺ¨λ  κΏμ ν©
		int leftSum = 0;    //λμ ν©
		int leftMax = 0;    //μ΅λκ°

		for (int i = N - 2; i >= 0; i--) {
             //λ€λ₯Έ νλ§λ¦¬λ₯Ό λ§¨ μ€λ₯Έμͺ½μμ μΌμͺ½μΌλ‘ μ΄λμν€λ©΄μ μ΄ λ²μ΄ μ§λκ°μ§ λͺ»νλ κΏλ€μ λμ νμ¬ ν©ν¨
            //μ μ²΄ κΏμμ λμ  ν©μ λΊμ λ μ΅λκ° κ΅¬νκΈ°
			leftSum += honey[i + 1];
			int temp = left - leftSum - 2 * honey[i];
			if (leftMax < temp) {
				leftMax = temp;
			}
		}

        // 3κ°μ§ κ²½μ° μ€ μ΅λκ° μΆλ ₯
		bw.write(Math.max(Math.max(center, rightMax), leftMax) + "");
		bw.close();
	}
}

```

<br>
<br>

# **πDescription**

> μ΅λλ‘ κΏμ λΈ μ μμΌλ €λ©΄ λ²λ€ λμ μ΄ μ΅λν κ²ΉμΉκ±°λ (λ²ν΅μ΄ μ λ μ€ νλμ μμΉν¨)\
> μΉΈμ κΈΈμ΄κ° μ§§μ κ²½μ°μλ λ²ν΅μ΄ κ°μ΄λ° μ΄λμ―€μ μμ μ μμ\
> μ΄ μΈ κ°μ§ κ²½μ°λ₯Ό κ³μ°νμ λ²ν΅μ΄ μ λμ μμΉνλ κ²½μ°λ sliding windowλ‘ λμ ν© κ΅¬νλ λ°©μ μ¬μ©ν¨

<br>
<br>

# **πRelated Issues**

> λ²ν΅μ λλ κ²½μ°λ₯Ό μΌμͺ½, μ€λ₯Έμͺ½ λ λ€ κ΅¬νμ§ μκ³  μ λ κ° μ€μ λ ν° κ°μ΄ μλ μͺ½μΌλ‘ νλλ§ κ³¨λΌμ κ΅¬νλ€μμ centerλ λΉκ΅ν  μλ κ°μλ° λμ€μ μκ°λλ©΄ ν΄λ³΄κΈ°....γγ

<br>
<br>

# **πResource**

| Memory  | Time  |
| ------- | ----- |
| 26360KB | 240ms |
