import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

	static int N, M, H, result;

	static boolean[][] ladder;

	static int[] dx = { 0, 0 };
	static int[] dy = { -1, 1 };

	static boolean flag;

	public static void main(String[] args) throws IOException {

		StringTokenizer st = new StringTokenizer(br.readLine(), " ");

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		H = Integer.parseInt(st.nextToken());

		ladder = new boolean[H][N - 1]; // 사다리를 놓을 수 있는 칸의 배열

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");

			int a = Integer.parseInt(st.nextToken()) - 1;
			int b = Integer.parseInt(st.nextToken()) - 1;

			ladder[a][b] = true;
		}

		flag = checkResult(); // 사다리 놓기 전에 조건 만족하는지 체크

		if (!flag) // 사다리 놔야함
			recur(0); // 사다리 놓기

		if (flag) // 조건 충족되는 경우
			bw.write(Integer.toString(result));
		else
			bw.write("-1");

		bw.close();
	}

	static void recur(int count) {

		if (count == 3) { // 사다리 3개까지만 놓음
			return;
		}

		for (int i = 0; i < H; i++) {
			for (int j = 0; j < N - 1; j++) {
				if (!ladder[i][j] && checkAround(i, j)) { // 사다리 놓을 수 있는 자리인지 확인

					ladder[i][j] = true; // 사다리 놓기

					if (checkResult()) { // 결과 충족하는지 확인
						flag = true;

						// 필요한 사다리 개수 넣어줌(이미 찾은적이 있으면 둘 중 최솟값으로 넣어줌)
						result = (result == 0) ? count + 1 : Math.min(result, count + 1);
					}

					recur(count + 1); // 다음 사다리 놓기

					ladder[i][j] = false; // 놓은 사다리 빼기
				}
			}
		}

	}

	// 인풋 좌표에 사다리를 놓을 수 있는지 체크 - 인풋 좌표 좌우에 사다리가 있는지 확인
	static boolean checkAround(int x, int y) {

		for (int i = 0; i < 2; i++) {
			int rx = x + dx[i];
			int ry = y + dy[i];

			if (rx < 0 || rx >= H || ry < 0 || ry >= N - 1)
				continue;

			if (ladder[rx][ry]) // 양쪽에 사다리가 있음
				return false;
		}

		return true;
	}

	// i번째로 시작해서 i번째로 끝나는지 확인
	static boolean checkResult() {

		for (int i = 0; i < N; i++) {
			int line = i;
			for (int j = 0; j < H; j++) {
				if (line == N - 1) { // 맨 오른쪽 줄
					if (ladder[j][line - 1]) { // 사다리 있으면 한칸 왼쪽으로 옮김
						line--;
					}
				} else if (line == 0) { // 맨 왼쪽 줄
					if (ladder[j][line]) { // 사다리 있으면 한칸 오른쪽으로 옮김
						line++;
					}
				} else { // 중간 사다리 - 사다리 있는 쪽으로 옮김
					if (ladder[j][line]) {
						line++;
					} else if (ladder[j][line - 1]) {
						line--;
					}
				}
			}
			if (line != i)
				return false;
		}

		return true;
	}
}
