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