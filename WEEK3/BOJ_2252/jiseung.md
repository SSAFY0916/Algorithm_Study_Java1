![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%202252&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **πProblem**

> BOJ 2252 μ€ μΈμ°κΈ°
> 

# π»**Code**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {

	static class Pair implements Comparable<Pair> { // νμμ λ²νΈμ μ΄ νμλ³΄λ€ μμ μμΌνλ νμμ μλ₯Ό μ μ₯νλ ν΄λμ€
		int x, y;

		public Pair(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public int compareTo(Pair o) { // μ΄ νμλ³΄λ€ μμ μμΌνλ νμ μ κΈ°μ€ μ λ ¬
			return this.y==o.y ? Integer.compare(this.x, o.x) : Integer.compare(this.y, o.y);
		}
		
	}
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		List<List<Integer>> priorities = new ArrayList<>(); // iλ²μ§Έ λ¦¬μ€νΈμλ νμ λ²νΈκ° iμΈ νμλ³΄λ€ λ€μ μμΌνλ νμλ€μ λ²νΈκ° μ μ₯λμ΄ μμ
		List<List<Integer>> reversedPriorities = new ArrayList<>(); // iλ²μ§Έ λ¦¬μ€νΈμλ νμ λ²νΈκ° iμΈ νμλ³΄λ€ μμ μμΌνλ νμλ€μ λ²νΈκ° μ μ₯λμ΄ μμ
		for(int i=0; i<=n; i++) {
			priorities.add(new ArrayList<>());
			reversedPriorities.add(new ArrayList<>());
		}
		for(int i=0; i<m; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			priorities.get(y).add(x);	
			reversedPriorities.get(x).add(y);
		}
		int[] counts = new int[n+1];					// νμ λ²νΈκ° iμΈ νμλ³΄λ€ μμ μμΌνλ νμμ μλ₯Ό μ μ₯νλ λ°°μ΄ => counts[i]κ° 0μ΄ λμ΄μΌ μμ μμΌνλ νμλ€μ΄ μμ΄μ§κ³  νμ λ²νΈκ° iμΈ νμμ΄ μ€ μ μλ€.
		PriorityQueue<Pair> pq = new PriorityQueue<>();	// νμ λ²νΈμ μ΄ νμλ³΄λ€ μμ μμΌ νλ νμμ μλ₯Ό μ μ₯νλ ν΄λμ€μ μ°μ μμ ν	
		for(int i=1; i<=n; i++) {
			counts[i] = priorities.get(i).size();
			pq.add(new Pair(i, counts[i]));
		}
		boolean[] visited = new boolean[n+1];			// νμ λ²νΈκ° iμΈ νμμ΄ μ€μ μ°λμ§ μ¬λΆλ₯Ό μ μ₯νλ λ°°μ΄
		int[] answer = new int[n];						// νμ¬ μ€μ μμλ νμλ€μ λ²νΈ
		int count = 0;									// νμ¬ μ€μ μμλ νμλ€μ μ
		while(count < n) {								// nλͺμ νμλ€μ΄ λͺ¨λ μ€μ μ€ λκΉμ§
			Pair pair = pq.poll();
			if(pair.y != 0) {							// μμμμΌ νλ νμλ€μ΄ λͺ¨λ μμ§ μμλλ° μ€μ μλ €κ³  ν  λ
				System.out.println("!");				// νμλ€μ΄ μ€μ μ€ μ μλ κ²½μ°λ§ μλ ₯μΌλ‘ λ€μ΄μμ μ€νλμ§λ μμ
				break;
			}
			if(visited[pair.x]) {						// μ΄λ―Έ μ€μ μ  νμμ κ±΄λλ
				continue;
			}
			answer[count++] = pair.x;					// μ€μ μκ³  μ€ μ  νμμ μ¦κ°
			visited[pair.x] = true;						// μ€μ μμλ€κ³  κ°±μ 
			for(Integer i : reversedPriorities.get(pair.x)) {	// λλ³΄λ€ λ€μ μμΌ νλ νμλ€μ λ€μ μ°μ μμ νμ λ£μ
				pq.add(new Pair(i, --counts[i]));				// λ΄κ° μ€μ μ°μΌλ―λ‘ countsλ₯Ό νλμ© μ€μ¬μ μ°μ μμ νμ λ£μ
			}
		}
		for(int i : answer) {
			bw.write(i + " ");
		}
		bw.flush();
		bw.close();
		br.close();
	}
}
```

# **πDescription**

> μμ μκ³  λ€μ μκ³  ν·κ°λ Έλ€.
μ€μ μκΈ° μν΄ νμν νμμ μλ₯Ό κΈ°μ€μΌλ‘ μ°μ μμ νλ₯Ό λμ΄μ νμν νμμ΄ μ μ μμλλ‘ μ€μ μΈμ λ€.
μ€μ μλ©΄ λ€λ₯Έ νμλ€μ νμν νμμ μκ° μ€μ΄λ€νλ λ€μ νμν νμ μλ₯Ό κ΅¬ν΄μ μ°μ μμ νμ μ§μ΄λ£κ² νλ€.
> 

# **πRelated Issues**

> μ°μ μμ νμ μ§μ΄λ£λ νμμ΄ μ°μ μμ νμμ λΉ μ Έμ μ€μ μλ νμλ³΄λ€ λ§μ κ² κ°μλ° μ€νμ μ λλ€.
> 

# **πResource**

| Memory | Time |
| --- | --- |
| 55664KB | 632ms |