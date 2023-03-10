
![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2017281&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **πProblem**

> BOJ 17281 βΎ
> 

# π»**Code**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {

	static int n, answer;
	// μ΄λλ³ μ μλ€μ κ²°κ³Όλ₯Ό μ μ₯νλ λ°°μ΄
	static int[][] results;
	// μμ΄λ‘ μ ν  νμμ μ μ₯νλ λ°°μ΄
	static int[] perm;
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;
		n = Integer.parseInt(br.readLine());
		results = new int[n][9];
		for(int i=0; i<n; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<9; j++) {
				results[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		perm = new int[9]; // μμ΄λ‘ 9λͺμ νμμ μ ν΄μ μ μ₯ν΄μ€ λ°°μ΄
		perm[3] = 0; // 4λ² νμλ 1λ² μ μ
		answer = 0;
		permutation(0, 0);
		bw.write(answer + "\n");
		bw.flush();
		bw.close();
		br.close();
	}
	
	static void permutation(int count, int flag) {
		if(count == 3) { // 4λ² νμλ μ΄λ―Έ μ νμΌλκΉ λ€μμΌλ‘ λμ΄κ°
			permutation(count+1, flag);
			return;
		}
		if(count == 9) { // λ€ μ νμΌλ©΄ μ μλ₯Ό κ³μ°
			answer = Math.max(answer, play());
			return;
		}
		for(int i=1; i<9; i++) {
			if((flag & (1<<i)) != 0) continue; // νμμ μ΄λ―Έ ν¬ν¨λλ©΄ κ±΄λλ
			perm[count] = i;
			permutation(count+1, flag | (1<<i));
		}
	}
	
	// μ ν΄μ§ νμλλ‘ μ΄λμ λ°λ³΅νλ©΄μ μ μλ₯Ό κ³μ°νλ λ©μλ
	static int play() {
		int score = 0; // μ μ
		int cur = 0; // νμ¬ νμμ μλ μ μ
		for(int i=0; i<n; i++) {
			boolean first = false, second = false, third = false; // 1, 2, 3λ£¨μ μ£Όμκ° μλμ§ μ μ₯νλ λ³μ
			int out = 0; // νμ¬ μ΄λμ μμμΉ΄μ΄νΈ
			while(out < 3) { // 3μμλλ©΄ λ°λ³΅ μ’λ£
				switch(results[i][perm[cur]]) {
				case 0: // μμμΌ λ
					out++;
					break;
				case 1: // 1λ£¨νμΌ λ
					if(third) score++;
					third = second;
					second = first;
					first = true;
					break;
				case 2: // 2λ£¨νμΌ λ
					if(third) score++;
					if(second) score++;
					third = first;
					second = true;
					first = false;
					break;
				case 3: // 3λ£¨νμΌ λ
					if(third) score++;
					if(second) score++;
					if(first) score++;
					third = true;
					second = false;
					first = false;
					break;
				case 4: // νλ°μΌ λ
					if(third) score++;
					if(second) score++;
					if(first) score++;
					score++;
					third = false;
					second = false;
					first = false;
					break;
				}
				cur = (cur+1)%9; // λ€μ νμ
			}
		}
		return score;
	}
}

```

# **πDescription**

> μμ΄λ‘ νμ μ μ νκ³  μ΄λμ λ°λ³΅νλ©΄μ μ μλ₯Ό κ³μ°νλ€.<br>
> μ²μμλ λ£¨μμ μ£Όμλ€μ μ μ₯ν  λ νλ₯Ό μ μΈνκ³  3κ°μ μμλ€μ λ£μ΄λκ³  μ μ§νλ λ°©λ²μΌλ‘ νμλ€.<br>
> λ§μ½μ 2λ£¨νλ©΄ νμμ 2κ°μ μμλ₯Ό λΉΌκ³  λ€μ 2κ°λ₯Ό λ£λ λ°©μμΌλ‘ νμλλ° μκ°μ΄κ³Όμλ€.


# **πRelated Issues**

> λΉνΈλ§μ€νΉμ νμ©νλ©΄μ & μ°μ°μ κ²°κ³Όλ₯Ό 0κ³Ό λΉκ΅νλ λΆλΆμ μκΎΈ κΉλ¨Ήμλ€.<br>
> μλ°λ μ int boolean μλ‘ μ μΉν κΉ

# **πResource**

| Memory | Time |
| --- | --- |
| 14472KB | 132ms |