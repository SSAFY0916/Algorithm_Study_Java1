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
