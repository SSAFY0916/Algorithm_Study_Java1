![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2011000&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 11000 강의실 배정](https://www.acmicpc.net/problem/11000)

<br>
<br>

# 💻**Code**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

class Schedule {
	int startTime;
	int endTime;

	Schedule(int startTime, int endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}
}

public class Main {

	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	// 강의실 배정
	public static void main(String[] args) throws Exception {

		int N = Integer.parseInt(br.readLine());
		StringTokenizer st = null;
		List<Schedule> list = new ArrayList<Schedule>();

    //강의실 끝나는 시간 비교하는 용도로 쓸 priority queue
		PriorityQueue<Schedule> pq = new PriorityQueue<>(new Comparator<Schedule>() {
			@Override
			public int compare(Schedule s1, Schedule s2) {
				return s1.endTime - s2.endTime;
			}
		});

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			list.add(new Schedule(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
		}

    //시작시간, 끝나는시간 순서로 정렬
		Collections.sort(list, (Schedule s1, Schedule s2) -> {
			if (s1.startTime == s2.startTime) {
				return s1.endTime - s2.endTime;
			}
			return s1.startTime - s2.startTime;
		});

		pq.offer(list.get(0));

		for (int i = 1; i < N; i++) {
			pq.offer(list.get(i));

      //맨 마지막에 넣은 수업시간이 가장 빨리 끝나는 수업시간보다 크거나 같으면 이어서 할 수 있으므로 priority queue에서 제거해줌
			if (pq.peek().endTime <= list.get(i).startTime)
				pq.poll();
		}

		bw.write(pq.size() + "");

		bw.close();
	}

}

```

<br>
<br>

# **🔑Description**

> 수업시간의 endTime을 기준으로 priority queue에서 head(제일 빨리 끝나는 시간) 가져와서 그 수업시간에서 이어서 할 수 있는지 없는지 확인했다.\
> 이어서 수업할 수 있으면 그 수업을 빼고 뒷타임 수업을 queue에 집어넣는다.
> 따라서 queue에 남아있는 수업의 개수가 필요한 강의실의 개수가 된다.

<br>
<br>

# **📑Related Issues**

> priority queue를 적절하게 이용해서 그리디를 풀었어야 했는데 진짜 생각이 너무 너무 안났다 관련 문제 찾아서 더 풀어보쟈...

<br>
<br>

# **🕛Resource**

| Memory  | Time  |
| ------- | ----- |
| 75460KB | 768ms |
