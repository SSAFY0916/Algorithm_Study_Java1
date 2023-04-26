![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2010157&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> BOJ 10157 자리배정

# 💻**Code**

```java
import java.util.*;
import java.io.*;

public class Main_10157 {
	static int r,c; // 반시계로 돌린다고 생각
	static int graph[][];
	// 하 우 상 좌
	static int dx[] = {1,0,-1,0};
	static int dy[] = {0,1,0,-1};
	
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		st = new StringTokenizer(in.readLine());
		c = Integer.parseInt(st.nextToken());
		r = Integer.parseInt(st.nextToken());
		graph = new int[r][c];
		int target = Integer.parseInt(in.readLine());
		if(target > r*c) {// 범위 초과하면 계산할 수 없음
			System.out.println(0);
			return;
		}
		int x=0, y=0;
		int dir = 0;
		int curr = 1;
		int nx, ny;
		while(true) {
			if(curr == target) {// 목표하는 값의 좌표를 찾았다면 출력
				System.out.println((y+1)+" "+(x+1));
				return;
			}
			
			graph[x][y] = curr++;
			nx = x+dx[dir];
			ny = y+dy[dir];
			// 범위 벗어나거나 값이 이미 있다면 방향전환
			if(nx<0 || nx>=r || ny<0 || ny>=c || graph[nx][ny] > 0) {
				dir = (dir+1)%4;
			}
			x+=dx[dir];
			y+=dy[dir];
		}
	}
}

```

# **🔑Description**

> Row Coloum을 주는 것이 헷갈리게 주었지만 제가 보기에 편한 방향으로 바꾸어 생각했습니다.
>
> 왼쪽 아래가 (1,1) 오른쪽 위가 (coloum, row) 이고 시계방향으로 돌면서 자리를 배정하는 것에서
>
> => 왼쪽 위(0,0) 오른쪽 아래(row-1,coloum-1)이고 반시계방향으로 돌면서 자리를 배정해주었습니다. 

# **📑Related Issues**

> 바깥쪽에서부터 채워주는 달팽이 문제로 
>
> 제때 올바른 방향으로 바꾸어주기만하면 간단하게 구현이 가능했습니다. 

# **🕛Resource**

| Memory    | Time    |
| --------- | ------- |
| 17948`KB` | 152`ms` |