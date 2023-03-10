
![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2012100&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)


# **๐Problem**

> BOJ 12100 2048 (Easy)
> 

# ๐ป**Code**

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
		if(count == 5) { 	// 5๋ฒ ๋ฐ๋ณต ์๋ฃ
			for(int[] row : board) {
				for(int i : row) {
					answer = Math.max(answer, i);
				}
			}
			return;
		}
		
		int[][] newBoard = new int[n][n];	// board๋ฅผ ๋ณต์ฌํด๋์๋ค๊ฐ board๋ฅผ ์๋ ์ํ๋ก ๋๋๋ฆด๋ ์ฌ์ฉ
		for(int i=0; i<n; i++) {
			newBoard[i] = Arrays.copyOf(board[i], n);
		}
		for(int i=0; i<n; i++) { // right => left
			int index = -1;		// 0์ด ์๋ ๊ฐ์ด ์๋ ๊ณณ์ ์ธ๋ฑ์ค
			int value = -1;		// ๊ทธ ์ธ๋ฑ์ค์ ์๋ 0์ด ์๋ ๊ฐ
			for(int j=0; j<n; j++) {
				if(board[i][j] != 0) {	// 0์ด๋ฉด ๊ฑด๋ ๋
					if(value == board[i][j]) {	// ๊ฐ์ด ๊ฐ์์ ํฉ์น  ์ ์์ผ๋ฉด
						board[i][index] *= 2;	
						value = 0;				// ๋ค์ ์ซ์์ ํฉ์ณ์ง์ง ์๋๋ก 0์ผ๋ก ์ค์  (์ด์ฐจํผ 0์ด๋ฉด ๊ฑด๋๋ฐ์ด์ value๊ฐ 0์ด ๋๋ฉด ๋ค์ ์ซ์์ ํฉ์ณ์ง์ง ์๋๋ค) 
						board[i][j] = 0;		// ํฉ์ณ์ง ํ ์ซ์ ์ ๊ฑฐ
					}else {						// ๊ฐ์ด ๋ฌ๋ผ์ ์ฎ๊ธฐ๊ธฐ๋ง ํ  ๋
						value = board[i][j];	// ์ฎ๊ธธ ๊ฐ ์ ์ฅ
						index++;				// ์ฎ๊ธธ ์์น
						if(index != j) {		// ์ฎ๊ธธ ์์น๊ฐ ๋ฐ๋ก ํ์ฌ ์์น์ ๋ค๋ฅด๋ฉด
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

# **๐Description**

> ์ต๋ 5๋ฒ๋ง ์ด๋์ํค๋ฉด ๋๊ธฐ ๋๋ฌธ์ 4๋ฐฉํฅ์ ์ค๋ณต์์ด๋ก ์ ํด๋๊ณ  ๊ฐ ๊ฒฝ์ฐ๋ง๋ค ๊ฐ์ฅ ํฐ ๋ธ๋ก์ ์ฐพ๋ ๋ฐฉ๋ฒ์ผ๋ก ๊ตฌํํ๋ค. 
์ต๋ 5๋ฒ์ด์ง๋ง ์ด๋์ด ๋ฐ๋ณต ๋ ์๋ก ์ซ์๊ฐ ์ปค์ง ์ ์์ผ๋ฏ๋ก 5๋ฒ ๋ชจ๋ ์ด๋ํ ํ์ ๊ฐ์ฅ ํฐ ๋ธ๋ก์ ์ฐพ์๋ค.
0์ ๋น ์นธ์ด๋ฏ๋ก ๊ฐ ๋ฐฉํฅ์ ๋ํด 0 ์ด ์๋ ๊ฐ์ ์ฐพ์ ๋๊ณ  ๋ค์ 0 ์ด ์๋ ๊ฐ๊ณผ ๊ฐ์ ๋์ ๋ค๋ฅผ ๋๋ก ๋๋์ด ๊ตฌํํ๊ณ  ๋ค๋ฅด๋ฉด ๊ทธ๋ฅ ์์น๋ง ์ฎ๊ฒผ๊ณ  ๊ฐ์ผ๋ฉด ํฉ์ณ์ฃผ๊ณ  ๋น ์นธ์ธ ์ฒ ํจ์ผ๋ก์ ํฉ์ณ์ง ์นธ์ด ๋ค์ ํฉ์ณ์ง์ง ์๊ฒ ํ๋ค.
> 

# **๐Related Issues**

> ๋ฌธ์ ๋ฅผ ์ด์ง ๋ ์์ธํ ์ฝ์์ผ๋ฉด ์ข์์ ๊ฒ ๊ฐ์๋ค.
4๋ฐฉํฅ์ ๋ํ์ฌ ๊ฐ๊ฐ์ ์ฝ๋๋ค์ ๋ ์ฌ์ฌ์ฉํ  ์ ์๊ฒ ๊ตฌํํ์ผ๋ฉด ๋ ์ข์์ ๊ฒ ๊ฐ๋ค.
> 

# **๐Resource**

| Memory | Time |
| --- | --- |
| 20612KB | 184ms |