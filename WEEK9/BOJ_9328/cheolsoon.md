![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%209328&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> BOJ 9328 열쇠

# 💻**Code**

```java
import java.util.*;
import java.io.*;

public class Main_9328 {
	/*
	 * 상근이
	 * 문서를 훔친다
	 * 문 - 열쇠 필요, 일부열쇠 가지고 있다
	 * 일부열쇠 바닥에 있다 상하좌우 움직인다
	 * 훔칠 수 있는 문서 최대개수
	 * '.'는 빈 공간을 나타낸다.
		'*'는 벽을 나타내며, 상근이는 벽을 통과할 수 없다.
		'$'는 상근이가 훔쳐야하는 문서이다.
		알파벳 대문자는 문을 나타낸다.
		알파벳 소문자는 열쇠를 나타내며, 그 문자의 대문자인 모든 문을 열 수 있다.
		마지막 줄에는 상근이가 이미 가지고 있는 열쇠가 공백없이 주어진다. 
		만약, 열쇠를 하나도 가지고 있지 않는 경우에는 "0"이 주어진다.		
	 * */
	static int n, m, score;
	static char graph[][];
	static List<int[]> gate[];
	static int key;
	static boolean visit[][];
	static int dx[] = {-1,1,0,0};
	static int dy[] = {0,0,-1,1};
	static ArrayDeque<int []> q;
	
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(in.readLine());
		for (int testCase=1; testCase<=T; testCase++) {
			st = new StringTokenizer(in.readLine());
			n = Integer.parseInt(st.nextToken());
			m = Integer.parseInt(st.nextToken());
			graph = new char[n+2][m+2];
			key = 0; // 열쇠 초기화
			gate = new ArrayList[27]; // 게이트를 발견했지만 방문하지 못한 게이트 담음.
			for(int i=0;i<27;i++) {
				gate[i] = new ArrayList<int[]>();
			}
            // 테두리 만들어준다.(side가 벽이 아닌 모든 곳을 자유롭게 이동 가능하도록)
			visit = new boolean[n+2][m+2]; 
			score = 0;
			q = new ArrayDeque<>();
			// 테두리 초기화
			for(int i=0;i<n+2;i++) {
				for(int j=0;j<m+2;j++) {					
					graph[i][j] = '.';
				}
			}
			
			for(int i=1;i<=n;i++) {
				String temp = in.readLine();
				for(int j=1;j<=m;j++) {
					graph[i][j] = temp.charAt(j-1);
				}
			}
			
			// 초기 열쇠 등록
			String keyList = in.readLine();
			if(!keyList.equals("0")) {
				for(int i=0;i<keyList.length();i++) {
					int k = keyList.charAt(i)-'a';
					key= key|(1<<k);
				}
			}
			// BFS
			q.offer(new int[] {0,0});
			while(!q.isEmpty()) {
				int temp[] = q.pollFirst();
				int x=temp[0]; int y=temp[1];				
				int nx, ny;
				for(int i=0;i<4;i++) {
					nx = x+dx[i];
					ny = y+dy[i];
					if(nx<0 || nx>=n+2 || ny<0 || ny>=m+2) continue;
					if(graph[nx][ny] == '*') continue;
					if(visit[nx][ny]) continue;
					// 문일때
					if(graph[nx][ny] >= 'A' && graph[nx][ny] <= 'Z'){
						int need = graph[nx][ny] - 'A';
						if((key&(1<<need)) > 0) {
							graph[nx][ny] = '.';
						}else {
							gate[need].add(new int[] {nx,ny});
							continue;
						}
					}
					
					// 열쇠 획득
					if(graph[nx][ny] >= 'a' && graph[nx][ny] <= 'z'){
						int k = graph[nx][ny] - 'a';
						key = key|(1<<k);
						graph[nx][ny] = '.';
						if(gate[k].size() > 0) {
							for(int g=0;g<gate[k].size();g++) {
								int tmp[] = gate[k].get(g);
								visit[tmp[0]][tmp[1]] = true;
								graph[tmp[0]][tmp[1]] = '.';
								q.offer(new int[] {tmp[0], tmp[1]});
							}							
						}
					}
					// 점수 증가
					if(graph[nx][ny] == '$') score++;
					// 이동 처리
					q.offer(new int[] {nx,ny});
					visit[nx][ny] = true;
				}
			}			
			sb.append(score+"\n");
		}
		System.out.println(sb);
	}
}

```

# **🔑Description**

> - 달이차오른다 처럼 비트 마스킹으로 방문 처리해주려 했지만 -> 메모리 초과 오류 발생했다. 열쇠 갯수가 최대 'a' - 'z' 26개라서 
> - Map의 가장자리 부분이 빈공간이거나, 열쇠거나, 문이라면 모두 자유롭게 갈 수 있다 -> 입력에서 해당 공간 방문처리하고 넣어주려고 했는데 조건이 까다로워서 전체 크기를 위아래 양옆 + 1씩 빈공간을 넣어줬다.
> - 문을 방문했을때 열쇠가 없을 경우 - 저장해주었다가 나중에 열쇠를 얻었을 때 텔레포트 할 수 있도록 해주었다.
> - 열쇠 관리는 비트마스킹으로 처리했다. boolean [26] 으로 해도 상관은 없을 것 같다.

# **📑Related Issues**

>열쇠문제. 너무 즐겁다

# **🕛Resource**

| Memory    | Time    |
| --------- | ------- |
| 20692`KB` | 204`ms` |