import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

	static int N, inning, now, score, max;

	static boolean[] visited;
	static int[] player;
	static int[][] result;

	public static void main(String[] args) throws Exception {

		visited = new boolean[8];
		player = new int[9];
		player[3] = 0;  // 4번 타자는 1번 선수로 고정

		N = Integer.parseInt(br.readLine());

		result = new int[N][9]; // N경기 동안의 결과값 저장

		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < 9; j++) {
				result[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		permutation(0); // 순열 생성하고 경기 시뮬 돌리기

		bw.write(Integer.toString(max));
		bw.close();
	}

	static void permutation(int count) {
		if (count == 8) {
			now = 0;    // 현재 타자번호
			inning = 0; // 이닝 수
			score = 0;  // 점수
			while (inning != N) {  
				baseball();
				inning++;
			}

			max = Math.max(max, score);
			return;
		}

		for (int i = 0; i < 8; i++) {
			if (visited[i])
				continue;

			visited[i] = true;
			if (count >= 3) //1번타자가 4번에 고정 되어있음
				player[count + 1] = i + 1;
			else
				player[count] = i + 1;
			permutation(count + 1);
			visited[i] = false;
		}
	}

	static void baseball() {
		int out = 0;

		int[] roo = new int[4]; //0: 홈, 1: 1루, 2: 2루, 3: 3루

		while (out != 3) {
			int r = result[inning][player[now]];

			if (r == 0) {   //아웃
				out++;
			} else {
				roo[0] = 1; // 홈에 나옴
				for (int i = 4 - r; i < 4; i++) {
					if (roo[i] == 1) {    //루에 있는 선수들 중 홈으로 올 수 있는 선수들 만큼 점수 획득
						score++;
						roo[i] = 0;
					}
				}

				for (int i = 4 - r - 1; i >= 0; i--) {  //루에 남아있는 선수들 다음 루로 옮기기
					roo[i + r] = roo[i];
					roo[i] = 0;
				}
			}

			now = (now + 1) % 9;    //다음 플레이어
		}
	}

}
