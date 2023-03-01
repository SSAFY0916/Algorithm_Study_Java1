import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {

	static class Pair implements Comparable<Pair> { // 학생의 번호와 이 학생보다 앞에 서야하는 학생의 수를 저장하는 클래스
		int x, y;

		public Pair(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public int compareTo(Pair o) { // 이 학생보다 앞에 서야하는 학생 수 기준 정렬
			return this.y==o.y ? Integer.compare(this.x, o.x) : Integer.compare(this.y, o.y);
		}
		
	}
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		List<List<Integer>> priorities = new ArrayList<>(); // i번째 리스트에는 학생 번호가 i인 학생보다 뒤에 서야하는 학생들의 번호가 저장되어 있음
		List<List<Integer>> reversedPriorities = new ArrayList<>(); // i번째 리스트에는 학생 번호가 i인 학생보다 앞에 서야하는 학생들의 번호가 저장되어 있음
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
		int[] counts = new int[n+1];					// 학생 번호가 i인 학생보다 앞에 서야하는 학생의 수를 저장하는 배열 => counts[i]가 0이 되어야 앞에 서야하는 학생들이 없어지고 학생 번호가 i인 학생이 설 수 있다.
		PriorityQueue<Pair> pq = new PriorityQueue<>();	// 학생 번호와 이 학생보다 앞에 서야 하는 학생의 수를 저장하는 클래스의 우선순위 큐	
		for(int i=1; i<=n; i++) {
			counts[i] = priorities.get(i).size();
			pq.add(new Pair(i, counts[i]));
		}
		boolean[] visited = new boolean[n+1];			// 학생 번호가 i인 학생이 줄에 섰는지 여부를 저장하는 배열
		int[] answer = new int[n];						// 현재 줄에 서있는 학생들의 번호
		int count = 0;									// 현재 줄에 서있는 학생들의 수
		while(count < n) {								// n명의 학생들이 모두 줄에 설 때까지
			Pair pair = pq.poll();
			if(pair.y != 0) {							// 앞에서야 하는 학생들이 모두 서지 않았는데 줄을 서려고 할 때
				System.out.println("!");				// 학생들이 줄을 설 수 있는 경우만 입력으로 들어와서 실행되지는 않음
				break;
			}
			if(visited[pair.x]) {						// 이미 줄을 선 학생은 건너뜀
				continue;
			}
			answer[count++] = pair.x;					// 줄에 서고 줄 선 학생수 증가
			visited[pair.x] = true;						// 줄에 서있다고 갱신
			for(Integer i : reversedPriorities.get(pair.x)) {	// 나보다 뒤에 와야 하는 학생들을 다시 우선순위 큐에 넣음
				pq.add(new Pair(i, --counts[i]));				// 내가 줄에 섰으므로 counts를 하나씩 줄여서 우선순위 큐에 넣음
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