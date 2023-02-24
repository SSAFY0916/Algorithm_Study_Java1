![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%202252&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 문제번호 문제이름](https://www.acmicpc.net/problem/2252)

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
	// 2차원 ArrayList를 이용하여 graph 구현
	static ArrayList<ArrayList<Integer>> graph;
	// 앞에 서야하는 사람의 수를 저장하는 배열
	static int[] connectNum;
	static boolean[] visited;
	static StringBuilder sb = new StringBuilder();
	static Queue<Integer> q = new ArrayDeque<>();

	public static void pushQueue() {
		for (int i = 1; i <= peopleNum; i++) {
			// 아직 줄에 서 있지 않고, 앞에 서야하는 사람이 남아있지 않는 경우에 큐에 넣어줌
			if (!visited[i] && connectNum[i] == 0) {
				q.add(i);
				visited[i] = true;
			}
		}
	}

	public static void solve() {
		// 큐에 들어있는 모든 사람을 줄에 세우기
		while (!q.isEmpty()) {
			int pNum = q.poll();
			sb.append(pNum).append(" ");
			// 큐에서 꺼낸 사람의 뒤에 서는 사람들의 앞에 서야하는 사람의 수를 1씩 감소시켜줌
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

			// graph[front]에 뒤에 서는 사람(back) 들을 추가해줌
			graph.get(front).add(back);
			// back의 앞에 서야하는 사람의 수 증가
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

# **🔑Description**

> 그래프를 이용해서 앞에 서는 사람과 뒤에 서는 사람을 연결해 주었습니다.
> 각 사람별로 앞에 서야하는 사람의 수를 저장해둔 뒤 앞에 서야하는 사람이 더 이상 남아있지 않을 경우 줄에 세우는 방식으로 구현하였습니다.

<br>
<br>

# **📑Related Issues**

> 앞에 서야하는 사람의 수가 남아있지 않는 경우에 세우는 방식을 생각해내기 전, 그래프 탐색을 시작할 때 시작 노드를 어떻게 잡아야 할지 생각하기가 어려웠습니다.
> 자바로 2차원 가변배열을 만드는 것을 사용해 본 적이 없어서 2차원 ArrayList를 생성하는 방법이 어려웠습니다.

<br>
<br>

# **🕛Resource**

| Memory | Time   |
| ------ | ------ |
| 47452KB | 4612ms |