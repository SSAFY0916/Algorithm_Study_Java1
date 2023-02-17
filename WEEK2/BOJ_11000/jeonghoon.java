import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_11000 {
	
	static class Lecture {
		int startTime;
		int endTime;
		
		public Lecture(int startTime, int endTime) {
			this.startTime = startTime;
			this.endTime = endTime;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int num = Integer.parseInt(br.readLine());
		
		Queue<Lecture> lectures = new PriorityQueue<>(
				(l1, l2) -> {
					if (l1.startTime == l2.startTime)
						return l1.endTime - l2.endTime;
					return l1.startTime - l2.startTime;
				});
		
		for (int i = 0; i < num; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			lectures.add(new Lecture(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
		}
		
		Queue<Lecture> rooms = new PriorityQueue<>(
				(l1, l2) ->  {
					if (l1.endTime == l2.endTime)
						return l1.startTime - l2.startTime;
					return l1.endTime - l2.endTime;
				});
		
		while (!lectures.isEmpty()) {
			Lecture start = lectures.poll();
			Lecture using = rooms.peek();
			
			if (rooms.isEmpty() || start.startTime >= using.endTime) {
				rooms.poll();
			}
			rooms.add(start);
		}
		
		System.out.println(rooms.size());
	}

}
