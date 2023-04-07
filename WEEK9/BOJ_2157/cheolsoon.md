![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%202157&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> BOJ 2157 여행

# 💻**Code**

```java
import java.io.*;
import java.util.*;

public class Main_2157 {
	// 플로이드 워샬로 하려고 했으나 M개의 도시를 초과했는지 체크하기가 어려움.
	static int N,M,K, graph[][], dp[][];
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		st = new StringTokenizer(in.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		graph = new int[N+1][N+1];
		dp = new int[N+1][M+1];
		
		int from,to,cost;
		for(int i=0;i<K;i++) {
			st = new StringTokenizer(in.readLine());
			from = Integer.parseInt(st.nextToken());
			to = Integer.parseInt(st.nextToken());
			cost = Integer.parseInt(st.nextToken());
			if(from>=to) continue; // 역순 주행 X
			graph[from][to] = Math.max(cost,graph[from][to]);// 같은 경로라면 비용이 큰 것만
		}

		// i번도시까지, (1번,i번) 2번 지났을 때 최대 비용
		for(int i=1;i<=N;i++) {
			dp[i][2] = graph[1][i];
		}
		
		/*
		 * DP[현재 도시까지] [cnt개만큼 거쳤을 때] = 최대 비용
		 * curr - 현재도시
		 * cnt - 지나온 개수
		 * next - 다음도시
		 * */
		for(int curr=1;curr<N;curr++) {
			for(int cnt=1;cnt<M;cnt++) {// M개 이하의 도시를 지나는 여행을 계획
				for(int next=curr+1;next<=N;next++) {
					if(graph[curr][next] == 0 || dp[curr][cnt] == 0) continue;
					dp[next][cnt+1] = Math.max(dp[next][cnt+1], dp[curr][cnt]+graph[curr][next]);
				}
			}
		}
		int result = 0;
		for(int i=0;i<=M;i++) {
			result = Math.max(dp[N][i], result);
		}
		System.out.println(result);
	}
}

/*
 * N개 도시 동->서
 * 제일 동쪽 - 1번 서쪽 - N번
 * M개 이하 도시 여행 한다
 * 1->N 순서로 서쪽으로만 이동 가능
 * 증가하는 순으로만 이동 가능
 * 모든 도시 -> 다른 모든 도시 이동할 수 있는건 아니다
 * 맛있는 기내식만 먹으면서 이동한다. 
 * from to, cost -> cost의 합이 최대가 되도록
 */
```

# **🔑Description**

> 주어진 조건들을 지켜주도록 해야했습니다. 
>
> - 같은 경로가 있으면 비용 큰 것으로만 유지
> - 증가하는 순서로만 이동 가능 -> 거꾸로 가는 경로 추가하지 않음
> - 1번 도시에서  N번으로 가는데
>   - 몇 개를 거쳐왔는지 알 수 있어야 함.(M개를 넘으면 X)
>   - 다음 방문지는 항상 이전보다 커야 함.
>   - DP[1번에서 현재 방문지까지] [최대 cnt개 지역을 방문했을 때] - 최대 비용

# **📑Related Issues**

>간선 개수 K개인데 M개로 착각한 바보입니다.
>

# **🕛Resource**

| Memory    | Time    |
| --------- | ------- |
| 42688`KB` | 424`ms` |