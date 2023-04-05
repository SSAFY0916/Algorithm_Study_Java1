![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2014890&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> BOJ 14890 경사로

# 💻**Code**

```java
import java.io.*;
import java.util.*;

public class Main_14890 {
	static int N,graph[][],L, result;
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		st = new StringTokenizer(in.readLine());
		N = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());
		graph = new int[N][N];
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(in.readLine());
			for(int j=0;j<N;j++) {
				graph[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		for(int i=0;i<N;i++) {
			// 행방향 확인
			if(check(graph[i])) result++;
			int temp[] = new int[N];
			for(int j=0;j<N;j++) {
				temp[j] = graph[j][i];
			}
			// 열방향 확인
			if(check(temp)) result++;
		}
		
		System.out.println(result);
		
		
	}
	private static boolean check(int[] temp) {
		int before = temp[0];
		int count=1;
		boolean isLower = false;
		int need = 0;
		
		for(int i=1;i<N;i++) {
			if(before == temp[i]) {// 같을 때
				count++;
				if(isLower) {// 낮아졌을때
					if(count >= L) {
						need--;
						isLower=false;
						count = 0;
					}
				}
			}else {
				need++;
				if(before == temp[i]-1) {//높아졌을때
					if(isLower) {
						return false; // 이미 낮아진게 해결이 안되어 있을 때
					}
					if(count >= L) {// 경사로 설치 가능할 때
						need--;
						count = 1;
					}else {
						return false;
					}
				}else if(before == temp[i]+1) {//낮아졌을때
					if(isLower) {
						return false;// 기존에 낮아진게 해결이 안되어 있을 때						
					}else {
						isLower = true;
						count = 1;
						if(count >= L) {// 바로 경사로 세울 수 있으면
							count = 0;
							need--;
							isLower = false;
						}
					}
				}else {// 차이가 1 이상 일 때
					return false;
				}
			}
			before = temp[i];
		}
		if(need!=0) return false;
		return true;
	}
}

```

# **🔑Description**

> 까다로운 조건을 모두 만족하도록 구현하는 문제였다. 조건들이 너무 까다로웠다..
>
> count로 이전까지 같은 값의 개수를 저장했다.
>
> - 올라가는 방향
>   - 이전까지 같은 값의 개수가 L 만족 -> 경사로 가능.
> - 내려가는 방향
>   - 지금부터 같은 값의 개수가 L이 되는 순간 -> 경사로 세우기
> - 같은 높이
> - 높이가 2이상 차이

# **📑Related Issues**

>맞왜틀 하지말고, 예제부터 잘 살펴보자, 문제는 내 코드안에 있다...
>
>내려가는 곳에서 그 곳에 바로 경사로를 세울 수 있다면 세워야 함을 상당히 늦게 발견했다.

# **🕛Resource**

| Memory    | Time    |
| --------- | ------- |
| 14980`KB` | 148`ms` |