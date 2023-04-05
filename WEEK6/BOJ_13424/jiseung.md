![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2013424&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> BOJ 13424 비밀 모임

# 💻**Code**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;
		int t = Integer.parseInt(br.readLine());
		while(t-- > 0) {
			st = new StringTokenizer(br.readLine());
			int n = Integer.parseInt(st.nextToken());
			int m = Integer.parseInt(st.nextToken());
			int[][] dists = new int[n+1][n+1]; // 정점들 간의 거리를 저장하는 배열
			for(int i=0; i<=n; i++) {
				Arrays.fill(dists[i], 100*1000); // 초기값을 (정점의 개수*최대 가중치)로 초기화, 정점 사이의 거리는 이 값보다는 항상 작으므로
				dists[i][i] = 0; // 자기 자신은 거리가 0
			}
			for(int i=0; i<m; i++) {
				st = new StringTokenizer(br.readLine());
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				int c = Integer.parseInt(st.nextToken());
				dists[a][b] = c; // 양방향 저장
				dists[b][a] = c;
			}
			int k = Integer.parseInt(br.readLine());
			int[] nums = new int[k]; // 비밀 모임 회원들의 번호 저장할 배열
			st = new StringTokenizer(br.readLine());
			for(int i=0; i<k; i++) {
				nums[i] = Integer.parseInt(st.nextToken());
			}
			for(int i=1; i<=n; i++) { // 플로이드와셜
				for(int start=1; start<=n; start++) {
					for(int end=1; end<=n; end++) {
						dists[start][end] = Math.min(dists[start][end], dists[start][i] + dists[i][end]);
					}
				}
			}
			int answer = 0;
			int minDist = Integer.MAX_VALUE;
			for(int i=1; i<=n; i++) {
				int sum = 0;
				for(int j=0; j<k; j++) {
					sum += dists[nums[j]][i]; // 비밀 모임 회원들의 i번호 방까지의 거리의 합
				}
				if(sum < minDist) { // 합이 기존의 최소값보다 작으면 갱신
					answer = i;
					minDist = sum;
				}
			}
			bw.write(answer + "\n");
		}
		bw.flush();
		bw.close();
		br.close();
	}

}
```

# **🔑Description**

> 문제가 길고 설명이 많아서 어려워보였는데 n이 최대 100이어사 바로 플로이드와셜 쓸 생각이 났다.\

# **📑Related Issues**

> 플로이드 와셜 쓸 때마다 반복문 인덱스 순서가 헷갈린다.

# **🕛Resource**

| Memory    | Time    |
| --------- | ------- |
| 34044`KB` | 564`ms` |
