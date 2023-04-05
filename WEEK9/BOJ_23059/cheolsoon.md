![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2023059 &fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> BOJ 23059 리그 오브 레게노

# 💻**Code**

```java
import java.io.*;
import java.util.*;

public class Main {
	static class Node implements Comparable<Node>{
		String name;
		int depth;

		public Node(String name, int depth) {
			this.name = name;
			this.depth = depth;
		}
		@Override
		public int compareTo(Node o) {
			if(o.depth == this.depth) {
				// 사전순 정렬
				return this.name.compareTo(o.name);
			}
			return this.depth - o.depth;
		}
	}
	static int N;
	static HashMap<String, ArrayList<String>> adjList;
	static HashMap<String, Integer> inDegree;
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		StringBuilder sb = new StringBuilder();
		
		N = Integer.parseInt(in.readLine());
		adjList = new HashMap<>();
		inDegree = new HashMap<>();
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(in.readLine());
			String from = st.nextToken();
			String to = st.nextToken();
			adjList.putIfAbsent(from, new ArrayList<>());
			adjList.putIfAbsent(to, new ArrayList<>());
			adjList.get(from).add(to);
			// 차수++
			inDegree.putIfAbsent(from, 0);
			inDegree.putIfAbsent(to, 0);
			inDegree.replace(to, inDegree.get(to)+1);
		}
		
		PriorityQueue<Node> answerQ = topologySort();
		if(answerQ.size() == inDegree.size()) {
			while(!answerQ.isEmpty()) {
				sb.append(answerQ.poll().name+"\n");
			}
			System.out.println(sb);
		}else {
			System.out.println("-1");
		}
	}
	
	static PriorityQueue<Node> topologySort(){
		PriorityQueue<Node> orderQ = new PriorityQueue<>();
		ArrayDeque<Node> queue = new ArrayDeque<>();
		int priority = 0;
		
		for(String item: adjList.keySet()) {
			if(inDegree.get(item) == 0) {
				queue.offer(new Node(item, priority));
			}
		}
		
		while(!queue.isEmpty()) {
			Node curr = queue.pollFirst();
			orderQ.add(curr);
			
			for(int i=0;i<adjList.get(curr.name).size(); i++) {
				String item = adjList.get(curr.name).get(i);
				// 차수 -1이 0이 되면 위상정렬 결과값에 넣어준다. 넣을때 같은 depth(우선순위)이면, 사전순정렬. 
				inDegree.replace(item, inDegree.get(item)-1);
				if(inDegree.get(item) == 0) {
					queue.add(new Node(item, curr.depth+1));
				}
			}
		}
		return orderQ;
	}
}

```

# **🔑Description**

> 	static HashMap<String, ArrayList<String>> adjList;
> 	static HashMap<String, Integer> inDegree;
>
> 처음에는 HashMap<Integer, String> itemMap 
>
> 각 아이템에 대한 id를 저장하는 Map을 만들고 그 id로 위상정렬한 결과를 출력하려했는데, 출력할 때 사전순으로 구매하는 것이 불가능했다.
>
> String 자체를 인접리스트로 만들고, String에 따른 inDegree를 만들어주는 방법을 찾았다.
>
> 기존까지 map에서 put만 사용했는데, putIfAbsent() -> 값이 없을때만 추가해주는 것이 너무 편리하다.
>
> 

# **📑Related Issues**

>

# **🕛Resource**

| Memory     | Time     |
| ---------- | -------- |
| 192832`KB` | 2592`ms` |