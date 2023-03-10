![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%202252&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **πProblem**

> [BOJ λ¬Έμ λ²νΈ λ¬Έμ μ΄λ¦](https://www.acmicpc.net/problem/2252)

<br>
<br>

# **Code**

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_2252 {

	static int peopleNum;
	static int inputNum;
	// 2μ°¨μ ArrayListλ₯Ό μ΄μ©νμ¬ graph κ΅¬ν
	static ArrayList<ArrayList<Integer>> graph;
	// μμ μμΌνλ μ¬λμ μλ₯Ό μ μ₯νλ λ°°μ΄
	static int[] connectNum;
	static boolean[] visited;
	static StringBuilder sb = new StringBuilder();
	static Queue<Integer> q = new ArrayDeque<>();

	public static void pushQueue() {
		for (int i = 1; i <= peopleNum; i++) {
			// μμ§ μ€μ μ μμ§ μκ³ , μμ μμΌνλ μ¬λμ΄ λ¨μμμ§ μλ κ²½μ°μ νμ λ£μ΄μ€
			if (!visited[i] && connectNum[i] == 0) {
				q.add(i);
				visited[i] = true;
			}
		}
	}

	public static void solve() {
		// νμ λ€μ΄μλ λͺ¨λ  μ¬λμ μ€μ μΈμ°κΈ°
		while (!q.isEmpty()) {
			int pNum = q.poll();
			sb.append(pNum).append(" ");
			// νμμ κΊΌλΈ μ¬λμ λ€μ μλ μ¬λλ€μ μμ μμΌνλ μ¬λμ μλ₯Ό 1μ© κ°μμμΌμ€
			for (int i = 0; i < graph.get(pNum).size(); i++) {
				connectNum[graph.get(pNum).get(i)]--;
			}

			pushQueue();
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		peopleNum = Integer.parseInt(st.nextToken());
		inputNum = Integer.parseInt(st.nextToken());
		connectNum = new int[peopleNum + 1];
		visited = new boolean[peopleNum + 1];

		graph = new ArrayList<>();
		for (int i = 0; i < peopleNum + 1; i++) {
			graph.add(new ArrayList<>());
		}

		for (int i = 0; i < inputNum; i++) {
			st = new StringTokenizer(br.readLine());
			int front = Integer.parseInt(st.nextToken());
			int back = Integer.parseInt(st.nextToken());

			// graph[front]μ λ€μ μλ μ¬λ(back) λ€μ μΆκ°ν΄μ€
			graph.get(front).add(back);
			// backμ μμ μμΌνλ μ¬λμ μ μ¦κ°
			connectNum[back]++;
		}

		pushQueue();
		solve();

		System.out.println(sb.toString());
	}

}

```

<br>
<br>

# **πDescription**

> κ·Έλνλ₯Ό μ΄μ©ν΄μ μμ μλ μ¬λκ³Ό λ€μ μλ μ¬λμ μ°κ²°ν΄ μ£Όμμ΅λλ€.
> κ° μ¬λλ³λ‘ μμ μμΌνλ μ¬λμ μλ₯Ό μ μ₯ν΄λ λ€ μμ μμΌνλ μ¬λμ΄ λ μ΄μ λ¨μμμ§ μμ κ²½μ° μ€μ μΈμ°λ λ°©μμΌλ‘ κ΅¬ννμμ΅λλ€.

<br>
<br>

# **πRelated Issues**

> μμ μμΌνλ μ¬λμ μκ° λ¨μμμ§ μλ κ²½μ°μ μΈμ°λ λ°©μμ μκ°ν΄λ΄κΈ° μ , κ·Έλν νμμ μμν  λ μμ λΈλλ₯Ό μ΄λ»κ² μ‘μμΌ ν μ§ μκ°νκΈ°κ° μ΄λ €μ μ΅λλ€.
> μλ°λ‘ 2μ°¨μ κ°λ³λ°°μ΄μ λ§λλ κ²μ μ¬μ©ν΄ λ³Έ μ μ΄ μμ΄μ 2μ°¨μ ArrayListλ₯Ό μμ±νλ λ°©λ²μ΄ μ΄λ €μ μ΅λλ€.

<br>
<br>

# **πResource**

| Memory | Time   |
| ------ | ------ |
| 47452KB | 4612ms |