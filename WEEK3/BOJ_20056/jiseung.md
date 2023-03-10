
![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2020056&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **πProblem**

> BOJ 20056 λ§λ²μ¬ μμ΄μ νμ΄μ΄λ³Ό
> 

# π»**Code**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

	static int n, m, k, answer;
	static int[] dr = {-1, -1, 0, 1, 1, 1, 0, -1}; 		// κ° λ°©ν₯μ λν κ°λ€
	static int[] dc = {0, 1, 1, 1, 0, -1, -1, -1};
	static List<Fireball> fireballs; 					// νμ΄μ΄λ³Όλ€μ μ μ₯νλ λ¦¬μ€νΈ, μμλ λ±ν μκ΄ μμ
	static class Fireball {								// νμ΄μ΄λ³Ό ν΄λμ€
		int r, c, m, s, d;
		public Fireball(int r, int c, int m, int s, int d) {
			this.r = r;
			this.c = c;
			this.m = m;
			this.s = s;
			this.d = d;
		}
		
	}
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		fireballs = new ArrayList<>();
		for(int i=0; i<m; i++) {
			st = new StringTokenizer(br.readLine());
			Fireball fireball = new Fireball(
					Integer.parseInt(st.nextToken())-1,		// 0index κΈ°λ°μΌλ‘ λ³κ²½
					Integer.parseInt(st.nextToken())-1,	
					Integer.parseInt(st.nextToken()),
					Integer.parseInt(st.nextToken()),
					Integer.parseInt(st.nextToken())
			);
			fireballs.add(fireball);
		}
		while(k-- > 0) {
			for(Fireball fireball : fireballs) {
				fireball.r += dr[fireball.d] * fireball.s;
				fireball.r %= n;
				if(fireball.r < 0) fireball.r += n;			// μλ ₯λ§νΌ κ° λ κ²©μλ₯Ό λ²μ΄λλ©΄ λλμμ€λλ‘ νμ§λ§ μμ μΈλ±μ€λ‘ κ°λ©΄ λ°λμͺ½ λμ μ°κ²°ν΄μ£Όλ κ² μ²λΌ nμ λν΄μ€ 
				fireball.c += dc[fireball.d] * fireball.s;
				fireball.c %= n;
				if(fireball.c < 0) fireball.c += n;
			}
			Collections.sort(fireballs, (Fireball o1, Fireball o2) ->	// νκ³Ό μ΄μ κΈ°μ€μΌλ‘ μ λ ¬ν¨μΌλ‘μ¨ κ°μ μμΉμ μλ νμ΄μ΄λ³Όμ μ°Ύμ μ μλλ‘ ν¨
					o1.r == o2.r ? Integer.compare(o1.c, o2.c) : Integer.compare(o1.r, o2.r)
			);
			fireballs.add(new Fireball(-1,-1,0,0,0));		// μ΄μ μ νμ΄μ΄λ³Όκ³Ό μμΉλ₯Ό λΉκ΅νλ©΄μ λ£μ΄μ£Όλλ° λ§μ§λ§ νμ΄μ΄λ³Όμ λμ΄μ λΉκ΅ν  νμ΄μ΄λ³Όμ΄ μμ΄ μλ€μ΄κ° μ μκΈ°λλ¬Έμ μμΉκ° κ°μ μ μλ λλ―Έ νμ΄μ΄λ³Όμ λ£μ΄μ€
			int prevR = fireballs.get(0).r, prevC = fireballs.get(0).c, index = 0;
			List<Fireball> newFireballs = new ArrayList<>();// μλ‘μ΄ νμ΄μ΄λ³Ό λ¦¬μ€νΈ
			for(int i=1; i<fireballs.size(); i++) {
				if(prevR == fireballs.get(i).r && prevC == fireballs.get(i).c) {
					continue;								// μ΄μ  νμ΄μ΄λ³Όκ³Ό μμΉκ° κ°μΌλ©΄ κ±΄λ λ
				}else {
					if(index+1 == i) {						// μ΄μ  νμ΄μ΄λ³Όκ³Ό μμΉκ° λ€λ₯΄κ³  μ΄μ  νμ΄μ΄λ³Όμ νΌμ μμΌλ©΄ κ·Έλ₯ λ¦¬μ€νΈμ μΆκ°
						newFireballs.add(fireballs.get(index));
					}else {									// μ΄μ  νμ΄μ΄λ³Όμ μ¬λ¬ νμ΄μ΄λ³Όλ€κ³Ό κ°μ΄ μμ
						int d = fireballs.get(index).d;		// νμ΄μ΄λ³Όλ€μ λ°©ν₯μ μ μ₯
						int mSum = 0, sSum = 0;				// μ§λκ³Ό μλ ₯μ ν©μ μ μ₯ν  λ³μ
						for(int j=index; j<i; j++) {
							mSum += fireballs.get(j).m;
							sSum += fireballs.get(j).s;
							if(d != -1 && d%2 != fireballs.get(j).d%2) {
								d = -1;						// νμ΄μ΄λ³Όλ€μ λ°©ν₯μ λΉκ΅ν΄ νμ§μ΄ λ€λ₯΄λ©΄ -1λ‘ λ°κΏμ€
							}
						}
						if(mSum/5 == 0)  {					// νμ΄μ΄λ³Ό μλ©Έ
							prevR = fireballs.get(i).r;		// μ΄μ  νμ΄μ΄λ³Ό μμΉλ₯Ό νμ¬ νμ΄μ΄λ³Ό μμΉλ‘ κ°±μ 
							prevC = fireballs.get(i).c;
							index = i;
							continue;
						}
						int[] directions = new int[4];
						if(d == -1) {						// λ°©ν₯μ΄ μλ‘ λ€λ¦
							directions = new int[] {1,3,5,7};
						}else {								// λ°©ν₯μ΄ μλ‘ κ°μ
							directions = new int[] {0,2,4,6};
						}
						for(int j=0; j<4; j++) {			// μλ‘μ΄ νμ΄μ΄λ³Ό λ¦¬μ€νΈμ μΆκ°
							newFireballs.add(new Fireball(prevR, prevC, mSum/5, sSum/(i-index), directions[j]));
						}
					}
					prevR = fireballs.get(i).r;				// μ΄μ  νμ΄μ΄λ³Ό μμΉλ₯Ό νμ¬ νμ΄μ΄λ³Ό μμΉλ‘ κ°±μ 
					prevC = fireballs.get(i).c;
					index = i;
				}
			}
			fireballs = newFireballs;						// κΈ°μ‘΄ νμ΄μ΄λ³Ό λ¦¬μ€νΈλ₯Ό μλ‘μ΄ νμ΄μ΄λ³Ό λ¦¬μ€νΈλ‘ λ³κ²½ 
		}
		for(Fireball fireball : fireballs) {
			answer += fireball.m;
		}
		bw.write(answer + "\n");
		bw.flush();
		bw.close();
		br.close();
	}
}
```

# **πDescription**

> νμ΄μ΄λ³Όλ€μ μ μ₯ν  μλ£κ΅¬μ‘°λ₯Ό μ νλκ² κΉλ€λ‘μ λ€.
νμ΄μ΄λ³Ό λ¦¬μ€νΈλ₯Ό λκ³  2μ°¨μ κ²©μμ μΉΈλ§λ€ νμ΄μ΄λ³Ό κ°μλ μΈλ±μ€λ₯Ό μ μ₯νλ λ°©λ²λ μκ°νμκ³ , 2μ°¨μ κ²©μμ νμ΄μ΄λ³Ό κ°μ²΄λ₯Ό μ μ₯ν΄ 3μ°¨μ λ¦¬μ€νΈλ₯Ό λ§λλ λ°©λ²μ΄λ μ°μ μμ νλ‘ κ°μ μμΉμ μλ νμ΄μ΄λ³Όμ μ°Ύλ λ°©λ²λ μκ°νμλ€.
νμ§λ§ 2μ°¨μ κ²©μλ₯Ό λλ©΄ νμ΄μ΄λ³Όμ μμ§μΌ λ μμ§μΈ νμ΄μ΄λ³Όμ  μΈλ±μ€λ₯Ό λ°λΌ λ μμ§μ΄κ² λκ³  μ΄λ₯Ό λ°©μ§νλκ² μ΄λ €μ λ€.
κ·Έλμ νμ΄μ΄λ³Ό λ¦¬μ€νΈλ§ λκ³  μλ‘μ΄ λ¦¬μ€νΈλ₯Ό λ§λ€μ΄ μ μ₯νλ€κ° λ°κΎΈλ λ°©λ²μ μ¬μ©νλ€.
> 

# **πRelated Issues**

> λ¬Έμ λ₯Ό μ΄μ§ λ μμΈν μ½μμΌλ©΄ μ’μμ κ² κ°μλ€.
4λ°©ν₯μ λνμ¬ κ°κ°μ μ½λλ€μ λ μ¬μ¬μ©ν  μ μκ² κ΅¬ννμΌλ©΄ λ μ’μμ κ² κ°λ€.
> 

# **πResource**

| Memory | Time |
| --- | --- |
| 33880KB | 544ms |