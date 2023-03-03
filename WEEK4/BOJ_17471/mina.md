![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2017471&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 17471 게리맨더링](https://www.acmicpc.net/problem/17471)

<br>
<br>

# **💻Code**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	static int N, min, C;

	static int[] population;

	static boolean[] vistiedDFS, vistiedBFS;

	static List<ArrayList<Integer>> nodeList;

	public static void main(String[] args) throws Exception {

		N = Integer.parseInt(br.readLine());

		population = new int[N + 1];
		vistiedDFS = new boolean[N + 1];
		vistiedBFS = new boolean[N + 1];

		int total = 0;

		StringTokenizer st = new StringTokenizer(br.readLine(), " ");

		nodeList = new ArrayList<ArrayList<Integer>>();
		nodeList.add(new ArrayList<Integer>());
		for (int i = 1; i < N + 1; i++) {
			population[i] = Integer.parseInt(st.nextToken());
			total += population[i];
			nodeList.add(new ArrayList<Integer>());
		}

		min = total;

		for (int i = 1; i < N + 1; i++) {
			st = new StringTokenizer(br.readLine(), " ");

			int n = Integer.parseInt(st.nextToken());
			for (int j = 0; j < n; j++) {
				nodeList.get(i).add(Integer.parseInt(st.nextToken()));	// 인접 노드 리스트
			}
		}

		dfs(1);	// 1번 인덱스부터 채우기

		if (min == total) {	// 두 선거구로 나눌 수 없음
			bw.write(Integer.toString(-1));
		} else {
			bw.write(Integer.toString(min));
		}

		bw.close();
	}

	static void dfs(int count) {

		if (count == N + 1) {\
			Arrays.fill(vistiedBFS, false);	// 방문 배열 초기화

			int A = 0, B = 0;	// 두 선거구 인구 수

			for (int j = 1; j <= N; j++) {
				if (vistiedDFS[j]) {	//true로 선택된 지역들을 bfs 돌면서 인구수 얻어옴
					A = bfs(j);
					break;
				}
			}

			for (int j = 1; j <= N; j++) {
				if (!vistiedDFS[j]) {	//false로 선택된 지역들을 bfs 돌면서 인구수 얻어옴
					B = bfs(j);
					break;
				}
			}
			boolean possible = true;

			for (int j = 1; j <= N; j++) {
				if (!vistiedBFS[j]) {	//방문 안 한 지역이 있으면 두 선거구로 나뉘어 지지 않은 것
					possible = false;
					break;
				}
			}

			if (possible) {	//두 선거구로 나뉘어 진 경우에만 둘 차이의 최솟값 계산
				min = Math.min(min, Math.abs(A - B));
			}
			return;
		}

		// dfs 돌면서 선거구를 true / false로 나눔
		vistiedDFS[count] = true;
		dfs(count + 1);
		vistiedDFS[count] = false;
		dfs(count + 1);

	}
	static int bfs(int n) {	// n번 지역부터	bfs
		Queue<Integer> queue = new LinkedList<Integer>();
		vistiedBFS[n] = true;
		queue.offer(n);

		int count = population[n];
		while (!queue.isEmpty()) {
			int cur = queue.poll();

			for (int i = 0; i < nodeList.get(cur).size(); i++) {
				int next = nodeList.get(cur).get(i);

				//입력으로 받은 지역이랑 같은 상태인 지역만 큐에 넣음
				if (vistiedBFS[next] || vistiedDFS[next] != vistiedDFS[cur])
					continue;

				count += population[next];	// 인구 수 더해주기
				vistiedBFS[next] = true;
				queue.offer(next);
			}
		}
		return count;	//총 인구 수 리턴

	}

}

```

<br>
<br>

# **🔑Description**

> n개의 지역을 두 구역으로 나누는 모든 경우를 부분집합 구하듯이 구했다.\
> 나뉘어진 지역안에서 bfs를 2번 돌면서 각 구역에 대한 총 인구 수를 가져왔다.\
> 그 후에 bfs로 방문 안한 지역이 있다면 두 구역으로 나뉘어 지지 않은 것으로 판단했다.

<br>
<br>

# **📑Related Issues**

> 간선 이용해서 두 구역으로 나누어지는 경우로만 부분집합을 만들어 보려고 했었는데 뭔가 잘 안돼서 결국 모든 경우의 수를 탐색했다....

<br>
<br>

# **🕛Resource**

| Memory   | Time   | Implementation Time |
| -------- | ------ | ------------------- |
| 13420 KB | 100 ms | 1 Hour 30 Minutes   |
