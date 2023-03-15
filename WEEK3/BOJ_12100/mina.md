![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2012100&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 12100 2048 (Easy)](https://www.acmicpc.net/problem/12100)

<br>
<br>

# **Code**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	static int N, max;

	static int[][] board, boardCopy;
	static int[] selected;

	public static void main(String[] args) throws Exception {

		N = Integer.parseInt(br.readLine());

		board = new int[N][N];
		boardCopy = new int[N][N];
		selected = new int[5];
		max = 0;

		StringTokenizer st = null;

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		combination(0);

		bw.write(Integer.toString(max));

		bw.close();
	}

	// 4가지 방향 5개로 중복조합
	static void combination(int count) throws IOException {

		if (count == 5) {
			moveNumber();
			return;
		}

		for (int i = 0; i < 4; i++) {
			selected[count] = i;

			combination(count + 1);
		}
	}

	static void moveNumber() throws IOException {

		for (int i = 0; i < N; i++) {
			boardCopy[i] = Arrays.copyOf(board[i], N);
		}

		for (int i = 0; i < 5; i++) {
			if (selected[i] == 0) {// 오른쪽으로 밀기
				for (int j = 0; j < N; j++) {
					for (int k = N - 2; k >= 0; k--) { // right to left, top to bottom 순서로 숫자 체크하면서 밀기
						int idx = k + 1; // 벽이나 그 다음 숫자 찾는 index
						while (boardCopy[j][k] != 0) {
							if (boardCopy[j][k] == boardCopy[j][idx]) { // 겹칠 수 있음
								boardCopy[j][idx] = -2 * boardCopy[j][idx]; // 이미 겹친건 음수로 표시해서 여러번 못겹치게 하기
								boardCopy[j][k] = 0;
								break;
							} else if (boardCopy[j][idx] != 0) { // 다른 숫자라 겹칠 수 없음 -> 그 숫자 직전까지 밀기
								int temp = boardCopy[j][k];
								boardCopy[j][k] = 0;
								boardCopy[j][idx - 1] = temp;
								break;
							}
							if (idx == N - 1) { // 벽에 도달 -> 벽끝까지 숫자 밀기
								boardCopy[j][idx] = boardCopy[j][k];
								boardCopy[j][k] = 0;
								break;
							}
							idx++;
						}

					}
				}

			} else if (selected[i] == 1) { // 아래쪽으로 밀기
				for (int j = N - 2; j >= 0; j--) {
					for (int k = 0; k < N; k++) { // left to right, bottom to top 순서로 숫자 체크하면서 밀기
						int idx = j + 1; // 벽이나 그 다음 숫자 찾는 index
						while (boardCopy[j][k] != 0) {
							if (boardCopy[idx][k] == boardCopy[j][k]) { // 겹칠 수 있음
								boardCopy[idx][k] = -2 * boardCopy[idx][k]; // 이미 겹친건 음수로 표시해서 여러번 못겹치게 하기
								boardCopy[j][k] = 0;
								break;
							} else if (boardCopy[idx][k] != 0) { // 다른 숫자라 겹칠 수 없음 -> 그 숫자 직전까지 밀기
								int temp = boardCopy[j][k];
								boardCopy[j][k] = 0;
								boardCopy[idx - 1][k] = temp;
								break;
							}

							if (idx == N - 1) { // 벽에 도달 -> 벽끝까지 숫자 밀기
								boardCopy[idx][k] = boardCopy[j][k];
								boardCopy[j][k] = 0;
								break;
							}
							idx++;
						}
					}
				}
			} else if (selected[i] == 2) {// 왼쪽으로 밀기
				for (int j = 0; j < N; j++) {
					for (int k = 1; k < N; k++) { // left to right, top to bottom 순서로 숫자 체크하면서 밀기
						int idx = k - 1; // 벽이나 그 다음 숫자 찾는 index
						while (boardCopy[j][k] != 0) {
							if (boardCopy[j][k] == boardCopy[j][idx]) { // 겹칠 수 있음
								boardCopy[j][idx] = -2 * boardCopy[j][idx]; // 이미 겹친건 음수로 표시해서 여러번 못겹치게 하기
								boardCopy[j][k] = 0;
								break;
							} else if (boardCopy[j][idx] != 0) { // 다른 숫자라 겹칠 수 없음 -> 그 숫자 직전까지 밀기
								int temp = boardCopy[j][k];
								boardCopy[j][k] = 0;
								boardCopy[j][idx + 1] = temp;
								break;
							}

							if (idx == 0) { // 벽에 도달 -> 벽끝까지 숫자 밀기
								boardCopy[j][idx] = boardCopy[j][k];
								boardCopy[j][k] = 0;
								break;
							}

							idx--;
						}
					}
				}
			} else if (selected[i] == 3) {// 위쪽으로 밀기
				for (int j = 1; j < N; j++) {
					for (int k = 0; k < N; k++) { // left to right, top to bottom 순서로 숫자 체크하면서 밀기
						int idx = j - 1; // 벽이나 그 다음 숫자 찾는 index
						while (boardCopy[j][k] != 0) {
							if (boardCopy[idx][k] == boardCopy[j][k]) { // 겹칠 수 있음
								boardCopy[idx][k] = -2 * boardCopy[idx][k]; // 이미 겹친건 음수로 표시해서 여러번 못겹치게 하기
								boardCopy[j][k] = 0;
								break;
							} else if (boardCopy[idx][k] != 0) { // 다른 숫자라 겹칠 수 없음 -> 그 숫자 직전까지 밀기
								int temp = boardCopy[j][k];
								boardCopy[j][k] = 0;
								boardCopy[idx + 1][k] = temp;
								break;
							}

							if (idx == 0) { // 벽을 도달 -> 벽끝까지 숫자 밀기
								boardCopy[idx][k] = boardCopy[j][k];
								boardCopy[j][k] = 0;
								break;
							}

							idx--;
						}
					}
				}
			}

			for (int j = 0; j < N; j++) {
				for (int k = 0; k < N; k++) {
					// 음수로 표시한거 돌려놓기
					boardCopy[j][k] = Math.abs(boardCopy[j][k]);
				}
			}
		}

		count();
	}

	static void count() { // 최댓값 찾기
		for (int j = 0; j < N; j++) {
			for (int k = 0; k < N; k++) {
				max = Math.max(max, boardCopy[j][k]);
			}
		}
	}

}
```

<br>
<br>

# **🔑Description**

> 동서남북을 중복조합으로 5개 조합하여 가능한 경우의 수에 대해 모두 탐색하였다.\
> 블럭을 한쪽으로 밀때 밀어야하는 쪽 블럭부터 검사해서 밀었다. 오른쪽으로 밀어야 하면 오른쪽 블럭부터 왼쪽 순서로 검사했다.\
> 해당 위치의 블럭에서 벽을 만나거나 다른 블럭을 만나기 전까지 쭉 탐색했다.\
> 벽을 만나면 그 벽앞으로 블럭 옮기고 다른 블럭 만나면 겹치거나 그 블럭 앞으로 옮겼다.\
> 이렇게 5번 밀고 난 후에 최댓값을 찾았다.

<br>
<br>

# **📑Related Issues**

> 완탐으로 풀어서 그런가 다른 사람들보다 시간이 오래걸린다. 이걸 줄일 수 있는 방법이 도대체 뭔지 모르겠음.\
> 0 8 4 4 인 상황에서 오른쪽으로 밀면 0 0 8 8 이 되어야하는데 자꾸 0 0 0 16 으로 나왔다.\
> 0 8 4 4 -> 0 8 0 8 -> 0 0 0 16 이렇게 겹쳐진건데 4 4 -> 8은 이미 겹쳐진거라 다른 8이랑 겹칠 수 없다.\
> 이미 겹쳐진 수를 어떻게 표시할 지 고민하다가 마이너스붙여놓고 전체 이동 끝난담에 다시 절댓값으로 넣어줬다. 이게 뭔가 문제인것 같기도 함...

<br>
<br>

# **🕛Resource**

| Memory  | Time  | Implementation Time |
| ------- | ----- | ------------------- |
| 23552KB | 604ms | 4 Hour              |
