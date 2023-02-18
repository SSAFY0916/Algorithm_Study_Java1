import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	static int[] wifi;
	static int N;
	static int C;

	public static void main(String[] args) throws Exception {

		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		wifi = new int[N];
		for (int i = 0; i < N; i++) {
			wifi[i] = Integer.parseInt(br.readLine());
		}
		Arrays.sort(wifi);

		int left = 0;
		int right = 1000000000;
		int result = 0;

        // 바이너리 서치로 제일 균일한 distance를 찾는다
		while (left <= right) {

			int middle = (left + right) / 2;

			if (checkDistance(middle)) {    
                // 일단 저장하고 더 균일할 수 있는지 찾아본다
				result = middle;
				left = middle + 1;
			} else {
				right = middle - 1;
			}

		}

		bw.write(Integer.toString(result));

		bw.close();
	}

    //distance간격 마다 공유기를 뒀을때 C개가 나오는지 check
	static boolean checkDistance(int distance) {

		int prePoint = wifi[0];
		int count = 1;

		for (int i = 1; i < N; i++) {
			if (wifi[i] - prePoint >= distance) {
				count++;
				prePoint = wifi[i];
			}
		}

    
		return count >= C;
	}
}
