import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Main_9663_NQueen {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static boolean[] isused1 = new boolean[50]; // 같은 열
	static boolean[] isused2 = new boolean[50]; // 대각선 /
	static boolean[] isused3 = new boolean[50]; // 대각선 \
	static int n;
	static int cnt = 0;


	public static void recursion(int cur){
		if (cur == n){
			cnt ++;
			return;
		}
		for (int i = 0; i < n; i++) {
			if (isused1[i] || isused2[i + cur] || isused3[cur - i + n-1]){
				continue;
			}
			isused1[i] = true;
			isused2[i + cur] = true;
			isused3[cur - i + n-1] = true;
			recursion(cur + 1);
			isused1[i] = false;
			isused2[i + cur] = false;
			isused3[cur - i + n-1] = false;
		}
	}

	public static void main(String[] args) throws IOException {
		n = Integer.parseInt(br.readLine());
		recursion(0);
		System.out.println(cnt);
	}
}