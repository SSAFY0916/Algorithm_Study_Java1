![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%202252&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 2252 줄 세우기](https://www.acmicpc.net/problem/2252)

<br>
<br>

# **💻Code**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	static List<ArrayList<Integer>> graph;  //인접노드 저장

	static int N, M, count;

	static int[] degree;    //노드 차수 기록

	Queue<Integer> q;

	public static void main(String[] args) throws Exception {
		graph = new ArrayList<ArrayList<Integer>>();

		StringTokenizer st = new StringTokenizer(br.readLine(), " ");

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		degree = new int[N + 1];

		for (int i = 0; i < N + 1; i++) {
			graph.add(new ArrayList<Integer>());
		}

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			graph.get(a).add(b);
			degree[b]++;
		}

		topologicalSort();

		bw.close();
	}

	static void topologicalSort() throws Exception {
		Queue<Integer> q = new LinkedList<Integer>();

		for (int i = 1; i <= N; i++) {
			if (degree[i] == 0) { // 차수 0이라 선택 가능
				q.offer(i);
			}
		}

		while (!q.isEmpty()) {
			int n = q.poll();   //선택 가능 - 그래프에서 제외
			bw.write(Integer.toString(n) + " ");

			for (int i = 0; i < graph.get(n).size(); i++) {
				int m = graph.get(n).get(i);
				degree[m]--;    //연결된 노드의 간선 끊기
				if (degree[m] == 0) {   //차수 0이면 그래프에서 제외 가능
					q.offer(m);
				}
			}

		}

	}

}
```

<br>
<br>

# **🔑Description**

> 문제를 읽고 위상 정렬이 생각났고 어렵지 않게 구현했다.\
> 큐에서 제거하는 순서대로 출력했다.

<br>
<br>

# **📑Related Issues**

> 사실 위상정렬 구현하는 방법이 정확하게 생각나지 않아서 기억나는대로 구현했더니 수행 시간이 2340ms가 나왔다.\
> 처음에 구현할땐 q에서 하나 뽑는 while문 안에다가 모든 노드의 차수가 0인지 확인하고 q에 집어넣는 걸 넣어놨었는데 짜면서도 이게 아니었던것 같은뎅..? 햇었고 진짜 그게 아니었음\
> \
> **모든 노드를 확인하는건 처음에만 해주고 나중엔 degree 줄어든 노드만 0인지 확인해서 큐에 집어넣으면 된다.**

<br>
<br>

# **🕛Resource**

| Memory  | Time  | Implementation Time |
| ------- | ----- | ------------------- |
| 50352KB | 452ms | 30 Minutes          |
