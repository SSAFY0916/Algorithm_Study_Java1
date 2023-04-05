
![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2015684&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> BOJ 15684 사다리 조작
> 

# 💻**Code**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {
	
	static int n, m, h, answer;
	// 사다리가 있는지 저장하는 배열, (i,j)가 true면 j번째 가로 위치에 i번째 세로선에서 (i+1)번째 세로선으로 가는 선이 있다
	static boolean[][] lines;
	
	public static void main(String[] args) throws Exception {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    	StringTokenizer st = new StringTokenizer(br.readLine());
    	n = Integer.parseInt(st.nextToken());
    	m = Integer.parseInt(st.nextToken());
    	h = Integer.parseInt(st.nextToken());
    	lines = new boolean[h][n-1];
    	for(int i=0; i<m; i++) {
    		st = new StringTokenizer(br.readLine());
    		int a = Integer.parseInt(st.nextToken())-1;
    		int b = Integer.parseInt(st.nextToken())-1;
    		lines[a][b] = true;
    	}
    	
    	answer = 4;
    	for(int i=0; i<=3; i++) { // 사다리 0개 추가부터 3개 추가까지 탐색
    		backtracking(i, 0, 0, 0);
    		if(answer != 4) break; // 사다리 조작 성공했으므로 break
    	}
    	if(answer == 4) answer = -1; // 사다리 3개 추가로도 불가능
    	bw.write(answer + "\n");
    	bw.flush();
        bw.close();
        br.close();
    }
	
	// count개의 사다리를 추가하는 백트래킹 함수, 현재 cur개의 사다리가 추가 되었고 사다리는 r,c부터 추가 가능
	static void backtracking(int count, int cur, int r, int c) {
		if(count == cur) { // count개읠 사다리 추가 완료 
			if(simulate()) { // 사다리 시뮬
				answer = count; // count개의 사다리로도 가능하므로 갱신
			}
			return;
		}
		
		for(int j=c; j<n-1; j++) { // (r,c)부터 (r,n-2)까지 탐색
			if(lines[r][j]) { 
				continue;
			}
			lines[r][j] = true;
			backtracking(count, cur+1, r, j+1);
			lines[r][j] = false;
		}
		for(int i=r+1; i<h; i++) { // (r+1,0)부터 (h-1,n-2)까지 탐색
			for(int j=0; j<n-1; j++) {
				if(lines[i][j]) { // 이미 사다리가 있으면 건너뜀
					continue;
				}
				lines[i][j] = true;
				backtracking(count, cur+1, i, j+1); // 다음 백트래킹 함수 호출
				lines[i][j] = false;
			}
		}
	}
	// 모든 세로선에서 내려가서 자기 자신에 도착하는지 반환하는 메소드
	static boolean simulate() {
		for(int i=0; i<n; i++) {
			int cur = i; // 현재 세로선
			for(int j=0; j<h; j++) {
				if(cur-1>= 0 && lines[j][cur-1]) { // 왼쪽으로 이동
					cur = cur-1;
				}else if(cur<n-1 && lines[j][cur]) { // 오른쪽으로 이동
					cur = cur+1;
				}
			}
			if(cur != i) { // 자기 자신에 도착하지 않으면 false 리턴
				return false;
			}
		}
		return true; // 모든 세로선이 자기 자신에 도착했으므로 true 리턴
	}
}
```

# **🔑Description**

> 사다리 0개, 1개, 2개, 3개 추가하는 방법을 백트래킹을 활용한 조합으로 찾고<br>
> 사다리를 잘 내려오면 추가한 사다리 개수를 출력한다.<br>
> 사다리를 저장하는 배열의 인덱스를 정하는 방법이 어려웠다.<br>

# **📑Related Issues**

> 1년전의 나보다 성장했다.<br>

# **🕛Resource**

| Memory | Time |
| --- | --- |
| 19488KB | 436ms |