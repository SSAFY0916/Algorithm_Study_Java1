![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2011000&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **πProblem**

> [BOJ 11000 κ°μμ€ λ°°μ ](https://www.acmicpc.net/problem/11000)

<br>
<br>

# π»**Code**

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

	// κ°μμ€ λ°°μ 
	public static void main(String[] args) throws Exception {

		int N = Integer.parseInt(br.readLine());
		StringTokenizer st = null;
		List<Schedule> list = new ArrayList<Schedule>();

    //κ°μμ€ λλλ μκ° λΉκ΅νλ μ©λλ‘ μΈ priority queue
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

    //μμμκ°, λλλμκ° μμλ‘ μ λ ¬
		Collections.sort(list, (Schedule s1, Schedule s2) -> {
			if (s1.startTime == s2.startTime) {
				return s1.endTime - s2.endTime;
			}
			return s1.startTime - s2.startTime;
		});

		pq.offer(list.get(0));

		for (int i = 1; i < N; i++) {
			pq.offer(list.get(i));

      //λ§¨ λ§μ§λ§μ λ£μ μμμκ°μ΄ κ°μ₯ λΉ¨λ¦¬ λλλ μμμκ°λ³΄λ€ ν¬κ±°λ κ°μΌλ©΄ μ΄μ΄μ ν  μ μμΌλ―λ‘ priority queueμμ μ κ±°ν΄μ€
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

# **πDescription**

> μμμκ°μ endTimeμ κΈ°μ€μΌλ‘ priority queueμμ head(μ μΌ λΉ¨λ¦¬ λλλ μκ°) κ°μ Έμμ κ·Έ μμμκ°μμ μ΄μ΄μ ν  μ μλμ§ μλμ§ νμΈνλ€.\
> μ΄μ΄μ μμν  μ μμΌλ©΄ κ·Έ μμμ λΉΌκ³  λ·νμ μμμ queueμ μ§μ΄λ£λλ€.
> λ°λΌμ queueμ λ¨μμλ μμμ κ°μκ° νμν κ°μμ€μ κ°μκ° λλ€.

<br>
<br>

# **πRelated Issues**

> priority queueλ₯Ό μ μ νκ² μ΄μ©ν΄μ κ·Έλ¦¬λλ₯Ό νμμ΄μΌ νλλ° μ§μ§ μκ°μ΄ λλ¬΄ λλ¬΄ μλ¬λ€\
> Descriptionμ μλ μμ΄λμ΄λ‘ κ΅¬ν νμλ€κ° Priority queue μ λ ¬ν  λ μ€μλ‘ startTime, endTime λ λ€ κ³ λ €νμ¬ μ λ ¬ν΄λ²λ €μ μ΄κ²λ λ μ½μ§νλ€.... endTimeμΌλ‘λ§ μ λ ¬νλ©΄ λ¨ λ°λ³΄λ°λ³΄λ°λ³΄\
> κ΄λ ¨ λ¬Έμ  μ°Ύμμ λ μ°μ΅νμ...

<br>
<br>

# **πResource**

| Memory  | Time  |
| ------- | ----- |
| 75460KB | 768ms |
