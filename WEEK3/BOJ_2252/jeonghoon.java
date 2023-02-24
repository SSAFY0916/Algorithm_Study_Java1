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
