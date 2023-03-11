import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

	static int k;
	static int[] parents, scores;
	static Node[] tree;
	static class Node { // 직원을 저장하는 클래스
		int index;
		List<Integer> children; // 직속 부하들

		public Node(int index) {
			this.index = index;
			this.children = new ArrayList<>();
		}
	}
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());

		st = new StringTokenizer(br.readLine());
		// 각 직원들의 직속 상사를 저장하는 배열
		parents = new int[n+1];
		for(int i=1; i<=n; i++) {
			parents[i] = Integer.parseInt(st.nextToken());
		}
		// 회사의 직원들을 저장하는 배열
		tree = new Node[n+1];
		for(int i=1; i<=n; i++) {
			tree[i] = new Node(i);
		}
		// 직속 부하들 추가
		for(int i=2; i<=n; i++) {
			tree[parents[i]].children.add(i);
		}
		// 각 직원들의 칭찬을 얼마나 받았는지 저장하는 배열
		scores = new int[n+1];
		for(int i=0; i<m; i++) {
			st = new StringTokenizer(br.readLine());
			int j = Integer.parseInt(st.nextToken());
			int score = Integer.parseInt(st.nextToken());
			scores[j] += score;
		}

		// 회사의 직원들을 사장부터 bfs로 탐색
		Queue<Integer> q = new ArrayDeque<>();
		q.add(1);
		while(!q.isEmpty()) {
			int cur = q.poll();
			for(Integer next : tree[cur].children) {
				scores[next] += scores[cur]; // 직속 부하들에게 나의 점수를 더해줌
				q.add(next);
			}
		}
		for(int i=1; i<=n; i++) {
			bw.write(scores[i] + " ");
		}
		bw.flush();
		bw.close();
		br.close();
	}
}