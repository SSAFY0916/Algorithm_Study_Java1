import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;
		int n = Integer.parseInt(br.readLine());
		List<int[]> classes = new ArrayList<>(); // 수업의 시작시간과 종료시간을 저장하는 리스트
		for(int i=0; i<n; i++) {
			st = new StringTokenizer(br.readLine());
			int[] temp = new int[2];
			temp[0] = Integer.parseInt(st.nextToken());
			temp[1] = Integer.parseInt(st.nextToken());
			classes.add(temp);
		}
		Collections.sort(classes, (int[] o1, int[] o2) -> { // 수업들을 시작시간 기준으로 정렬
			return Integer.compare(o1[0], o2[0]);
		});
		PriorityQueue<Integer> schedule = new PriorityQueue<>(); // 강의실별 수업 종료시간을 저장하는 우선순위 큐
		schedule.add(0); // 우선순위 큐 초기화
		for(int[] temp : classes) {
			if(schedule.peek() <= temp[0]) { // 현재 수업 시작시간보다 먼저 종료된 강의실이 있다면
				schedule.poll(); // 해당 강의실 제거
			}
			schedule.add(temp[1]); // 현재 수업 종료시간을 강의실에 추가
		}
		bw.write(schedule.size() + "\n"); // 강의실 개수 출력
		bw.flush();
		bw.close();
		br.close();
	}
}