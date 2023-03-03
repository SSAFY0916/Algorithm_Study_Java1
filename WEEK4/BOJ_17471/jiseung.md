
![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2017471&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> BOJ 17471 게리맨더링
> 

# 💻**Code**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

	static int n;
	// 각 구역의 인구수를 저장하는 배열
	static int[] populations;
	// 각 구역 별로 인접한 구역을 저장하는 배열
	static int[][] graph;
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		n = Integer.parseInt(br.readLine());
		populations = new int[n];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i=0; i<n; i++) {
			populations[i] = Integer.parseInt(st.nextToken());
		}
		graph = new int[n][];
		for(int i=0; i<n; i++) {
			st = new StringTokenizer(br.readLine());
			graph[i] = new int[Integer.parseInt(st.nextToken())];
			for(int j=0; j<graph[i].length; j++) {
				graph[i][j] = Integer.parseInt(st.nextToken()) -1;
			}
		}
		int answer = Integer.MAX_VALUE;
		for(int i=1; i<(1<<n)-1; i++) { // 비트마스킹을 활용하여 두 선거구로 나눔
			int sum1 = 0; // 첫 번째 선거구의 인구수의 합
			int sum2 = 0; // 두 번째 선거구의 인구수의 합
			for(int j=0; j<n; j++) {
				if((i & (1<<j)) != 0) {
					sum1 += populations[j];
				}else {
					sum2 += populations[j];
				}
			}
			int diff = Math.abs(sum1 - sum2); // 두 선거구의 인구수의 차
			if(diff >= answer) { // 두 선거구의 인구수의 차가 현재 답보다 크거나 같으면 어차피 49번 줄에서 갱신하지 않기 때문에 건너뜀
				continue;
			}
			if(!check(i)) continue; // 두 선거구로 잘 나뉘었는지 확인
			answer = diff; // 더 작은 인구수로 갱신
		}
		bw.write((answer==Integer.MAX_VALUE ? -1 : answer) + "\n"); // 
		bw.flush();
		bw.close();
		br.close();
	}

	// bfs를 활용하여 각 선거구를 탐색하여 두 선거구로 잘 나뉘었는지 확인하는 메소드
	static boolean check(int flag) {
		boolean[] visited = new boolean[n]; // 각 구역에 대한 방문여부를 저장하는 배열
		Queue<Integer> q = new LinkedList<>();
		q.add(0); // 0번 구역 큐에 넣음
		for(int i=0; i<n; i++) {
			if(((flag & (1<<i)) != 0) != ((flag & 1) != 0)) { // 0번 구역과 다른 선거구를 큐에 넣고 break
				q.add(i);
				break;
			}
		}
		while(!q.isEmpty()) { // bfs
			int cur = q.poll();
			if(visited[cur]) {
				continue;
			}
			visited[cur] = true;
			for(int next : graph[cur]) { // 현재 구역과 인접한 구역들 중에서
				if(((flag & (1<<cur)) != 0) == ((flag & (1<<next)) != 0)) { // 현재 구역과 같은 선거구이면 큐에 넣음
					q.add(next);
				}
			}
		}
		for(int i=0; i<n; i++) {
			if(!visited[i]) { // 방문되지 않은 구역이 있다면 두 선거구로 잘 나뉘지 않은 것이므로 false 리턴
				return false;
			}
		}
		return true;
	}
}
```

# **🔑Description**

> n이 최대 10밖에 안돼서 바로 완전탐색할 생각을 했었다.
> 두 선거구로 분리한 다음 인구수부터 조사해서 이번 탐색이 성공해서 최소값을 갱신할 수 있는지 여부를 먼저 보아 조금이라도 연산을 줄여보려고 했다.
> 두 선거구로 잘 분리되었는지는 0번째 구역과 0번째 구역과 다른 선거구인 구역 이렇게 2개의 구역을 큐에 넣고 하나씩 꺼내면서 선거구역을 bfs로 탐색했다.
> bfs가 끝나고 방문하지 못한 구역이 하나라도 존재한다면 두 선거구로 잘 분리되지 않은 것으로 간주했다.

# **📑Related Issues**

> 비트마스킹을 활용하면서 & 연산의 결과를 0과 비교하는 부분을 자꾸 까먹었다.
> 

# **🕛Resource**

| Memory | Time |
| --- | --- |
| 14472KB | 132ms |