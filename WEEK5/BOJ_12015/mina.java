import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

	public static void main(String[] args) throws Exception {
		int N = Integer.parseInt(br.readLine());
		int[] A = new int[N + 1];
		int[] dp = new int[N + 1];
		List<Integer> list = new ArrayList<Integer>();
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");

		for (int i = 1; i < N + 1; i++) {
			A[i] = Integer.parseInt(st.nextToken());
		}

		dp[1] = 1;
		list.add(A[1]);

		for (int i = 2; i < N + 1; i++) {
			if (list.get(list.size() - 1) < A[i]) { // 수열 맨 뒤에 붙일 수 있으면 붙임
				list.add(A[i]);
			} else { // 못붙이는 경우 교환할 수 있는 값 찾기 - lowerbound
				int start = 0;
				int end = list.size();
				int middle = -1;

				while (start < end) {
					middle = (start + end) / 2;

					if (list.get(middle) == A[i]) { // 같은 숫자가 있는 경우
						start = middle;
						break;
					} else if (list.get(middle) < A[i]) {
						start = middle + 1;
					} else {
						end = middle;
					}
				}
				list.set(start, A[i]);
			}
		}

		bw.write(Integer.toString(list.size()));
		bw.close();

	}
}
