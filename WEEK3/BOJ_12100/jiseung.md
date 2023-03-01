
![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2012100&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)


# **🔒Problem**

> BOJ 12100 2048 (Easy)
> 

# 💻**Code**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	static int[][] board;
	static int n, answer;
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;
		n = Integer.parseInt(br.readLine());
		board = new int[n][n];
		for(int i=0; i<n; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<n; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		answer = 0;
		backtracking(0);
		bw.write(answer + "\n");
		bw.close();
		br.close();
	}
	static void backtracking(int count) {
		if(count == 5) { 	// 5번 반복 완료
			for(int[] row : board) {
				for(int i : row) {
					answer = Math.max(answer, i);
				}
			}
			return;
		}
		
		int[][] newBoard = new int[n][n];	// board를 복사해두었다가 board를 원래 상태로 되돌릴때 사용
		for(int i=0; i<n; i++) {
			newBoard[i] = Arrays.copyOf(board[i], n);
		}
		for(int i=0; i<n; i++) { // right => left
			int index = -1;		// 0이 아닌 값이 있는 곳의 인덱스
			int value = -1;		// 그 인덱스에 있는 0이 아닌 값
			for(int j=0; j<n; j++) {
				if(board[i][j] != 0) {	// 0이면 건너 뜀
					if(value == board[i][j]) {	// 값이 같아서 합칠 수 있으면
						board[i][index] *= 2;	
						value = 0;				// 다음 숫자와 합쳐지지 않도록 0으로 설정 (어차피 0이면 건너뛰어서 value가 0이 되면 다음 숫자와 합쳐지지 않는다) 
						board[i][j] = 0;		// 합쳐진 후 숫자 제거
					}else {						// 값이 달라서 옮기기만 할 때
						value = board[i][j];	// 옮길 값 저장
						index++;				// 옮길 위치
						if(index != j) {		// 옮길 위치가 바로 현재 위치와 다르면
							board[i][index] = board[i][j];
							board[i][j] = 0;
						}
					}
				}
			}
		}
		backtracking(count+1);
		
		for(int i=0; i<n; i++) {
			board[i] = Arrays.copyOf(newBoard[i], n);
		}
		for(int i=0; i<n; i++) { // left => right
			int index = n;
			int value = -1;
			for(int j=n-1; j>=0; j--) {
				if(board[i][j] != 0) {
					if(value == board[i][j]) {
						board[i][index] *= 2;
						value = 0;
						board[i][j] = 0;
					}else {
						value = board[i][j];
						index--;
						if(index != j) {
							board[i][index] = board[i][j];
							board[i][j] = 0;
						}
					}
				}
			}
		}
		backtracking(count+1);
		
		for(int i=0; i<n; i++) {
			board[i] = Arrays.copyOf(newBoard[i], n);
		}
		for(int j=0; j<n; j++) { // bottom => top
			int index = -1;
			int value = -1;
			for(int i=0; i<n; i++) {
				if(board[i][j] != 0) {
					if(value == board[i][j]) {
						board[index][j] *= 2;
						value = 0;
						board[i][j] = 0;
					}else {
						value = board[i][j];
						index++;
						if(index != i) {
							board[index][j] = board[i][j];
							board[i][j] = 0;
						}
					}
				}
			}
		}
		backtracking(count+1);
		
		for(int i=0; i<n; i++) {
			board[i] = Arrays.copyOf(newBoard[i], n);
		}
		for(int j=0; j<n; j++) { // top => bottom
			int index = n;
			int value = -1;
			for(int i=n-1; i>=0; i--) {
				if(board[i][j] != 0) {
					if(value == board[i][j]) {
						board[index][j] *= 2;
						value = 0;
						board[i][j] = 0;
					}else {
						value = board[i][j];
						index--;
						if(index != i) {
							board[index][j] = board[i][j];
							board[i][j] = 0;
						}
					}
				}
			}
		}
		backtracking(count+1);
	}
}
```

# **🔑Description**

> 최대 5번만 이동시키면 되기 때문에 4방향을 중복순열로 정해두고 각 경우마다 가장 큰 블록을 찾는 방법으로 구현했다. 
최대 5번이지만 이동이 반복 될수록 숫자가 커질 수 있으므로 5번 모두 이동한 후에 가장 큰 블록을 찾았다.
0은 빈 칸이므로 각 방향에 대해 0 이 아닌 값을 찾아 놓고 다음 0 이 아닌 값과 같을 때와 다를 때로 나누어 구현했고 다르면 그냥 위치만 옮겼고 같으면 합쳐주고 빈 칸인 척 함으로서 합쳐진 칸이 다시 합쳐지진 않게 했다.
> 

# **📑Related Issues**

> 문제를 살짝 더 자세히 읽었으면 좋았을 것 같았다.
4방향에 대하여 각각의 코드들을 더 재사용할 수 있게 구현했으면 더 좋았을 것 같다.
> 

# **🕛Resource**

| Memory | Time |
| --- | --- |
| 20612KB | 184ms |