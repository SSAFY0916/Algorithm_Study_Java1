import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

class CCTV {
	int x;  //cctv의 x좌표
	int y;  //cctv의 y좌표
	int n;  //cctv의 종류
	int d;  //cctv의 방향

	CCTV(int x, int y, int n) {
		this.x = x;
		this.y = y;
		this.n = n;
	}
}

public class Main {

	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	static int[][] office;
	static int[][] officeCopy;
	static boolean[] selected;
	static boolean flag;
	static int N, M, min;
	static int cctvCount;
	static int[] dx = { 0, 1, 0, -1 };
	static int[] dy = { 1, 0, -1, 0 };
	static List<CCTV> cctvList;

	public static void main(String[] args) throws Exception {
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		cctvCount = 0;
		cctvList = new ArrayList<>();
		min = N * M;
		office = new int[N][M];
		officeCopy = new int[N][M];

        //데이터 입력 받기
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < M; j++) {
				office[i][j] = Integer.parseInt(st.nextToken());
				if (office[i][j] >= 1 && office[i][j] <= 5) {//cctv인 경우 cctvlist에 추가
					cctvCount++;
					cctvList.add(new CCTV(i, j, office[i][j]));
				}
			}
		}
		test = new int[cctvCount];
		backtracking(0);
		bw.write(min + "\n");
		bw.close();
	}

    //1개의 cctv가 가질 수 있는 방향은 4개이므로(0,1,2,3) 백트래킹으로 중복조합 계산
	static void backtracking(int count) {
		if (count == cctvCount) {
            // 중복조합 하나 완성 될때마다 cctv 돌려보기
			workCCTV(cctvList.toArray(new CCTV[cctvCount]));
			return;
		}

		for (int i = 0; i < 4; i++) {
            
            //2번 cctv는 방향이 2개이므로 (0,1) 중에서만 선택
			if (cctvList.get(count).n == 2 && i > 1)
				continue;

			cctvList.get(count).d = i;
			backtracking(count + 1);
		}
	}

	static void workCCTV(CCTV[] cctvs) {
		for (int i = 0; i < N; i++) {
			officeCopy[i] = Arrays.copyOf(office[i], M);
		}

		for (int i = 0; i < cctvCount; i++) {
			CCTV cctv = cctvs[i];
			if (cctv.n == 1) {
                //상, 하, 좌, 우 중 한 방향으로만 감시
				goStraight(cctv.x, cctv.y, cctv.d);
			} else if (cctv.n == 2) {
				if (cctv.d == 0) {
                    //좌우 방향
					goStraight(cctv.x, cctv.y, 0);
					goStraight(cctv.x, cctv.y, 2);
				} else if (cctv.d == 1) {
                    //상하 방향
					goStraight(cctv.x, cctv.y, 1);
					goStraight(cctv.x, cctv.y, 3);
				}
			} else if (cctv.n == 3) {
				if (cctv.d == 0) {
                    //남동 방향 감시
					goStraight(cctv.x, cctv.y, 0);
					goStraight(cctv.x, cctv.y, 1);
				} else if (cctv.d == 1) {
                    //남서 방향 감시
					goStraight(cctv.x, cctv.y, 1);
					goStraight(cctv.x, cctv.y, 2);
				} else if (cctv.d == 2) {
                    //북서 방향 감시
					goStraight(cctv.x, cctv.y, 2);
					goStraight(cctv.x, cctv.y, 3);
				} else if (cctv.d == 3) {
                    //북동 방향 감시
					goStraight(cctv.x, cctv.y, 3);
					goStraight(cctv.x, cctv.y, 0);
				}
			} else if (cctv.n == 4) {
				if (cctv.d == 0) {
                    //오른쪽 방향 제외하고 감시
					goStraight(cctv.x, cctv.y, 1);
					goStraight(cctv.x, cctv.y, 2);
					goStraight(cctv.x, cctv.y, 3);
				} else if (cctv.d == 1) {
                    //아래쪽 방향 제외하고 감시
					goStraight(cctv.x, cctv.y, 0);
					goStraight(cctv.x, cctv.y, 2);
					goStraight(cctv.x, cctv.y, 3);
				} else if (cctv.d == 2) {
                    //왼쪽 방향 제외하고 감시
					goStraight(cctv.x, cctv.y, 0);
					goStraight(cctv.x, cctv.y, 1);
					goStraight(cctv.x, cctv.y, 3);
				} else if (cctv.d == 3) {
                    //위쪽 방향 제외하고 감시
					goStraight(cctv.x, cctv.y, 0);
					goStraight(cctv.x, cctv.y, 1);
					goStraight(cctv.x, cctv.y, 2);
				}
			} else if (cctv.n == 5) {
                //동서남북 전부 감시
				goStraight(cctv.x, cctv.y, 0);
				goStraight(cctv.x, cctv.y, 1);
				goStraight(cctv.x, cctv.y, 2);
				goStraight(cctv.x, cctv.y, 3);
			}
		}

		countBlindSpace();
	}

    // (x, y) 위치에서 direction 방향으로 벽을 만나거나 범위를 벗어나기 전까지 방문 check
	static void goStraight(int x, int y, int direction) {
		int curX = x;
		int curY = y;
		while (true) {
			int rx = curX + dx[direction];
			int ry = curY + dy[direction];
			if (rx < 0 || rx >= N || ry < 0 || ry >= M || officeCopy[rx][ry] == 6)
				break;
			if (officeCopy[rx][ry] == 0)
				officeCopy[rx][ry] = 7;
			curX = rx;
			curY = ry;
		}
	}

    //사각지대 count
	static void countBlindSpace() {
		int total = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (officeCopy[i][j] == 0)
					total++;
			}
		}
		if (min > total)
			min = total;
	}

}
