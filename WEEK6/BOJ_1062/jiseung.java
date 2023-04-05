import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {

	static int n, k, answer;
	static boolean[][] words;
	static boolean[] comb;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		words = new boolean[n][26]; // 각 단어마다 필요한 알파벳의 유무를 저장
		for(int i=0; i<n; i++) {
			String word = br.readLine();
			for(int j=4; j<word.length()-4; j++) {
				words[i][word.charAt(j) - 'a'] = true;
			}
		}
		if(k >= 5) { // 필수 알파벳 5개는 배울 수 있어야 탐색
			comb = new boolean[26];
			comb['a'-'a'] = true; // 필수 알파벳 5개는 무조건 배움
			comb['n'-'a'] = true;
			comb['t'-'a'] = true;
			comb['i'-'a'] = true;
			comb['c'-'a'] = true;
			combination(0, 0);
		}
		bw.write(answer + "\n");
		bw.flush();
		bw.close();
		br.close();
	}

	// 가르칠 알파벳의 조합을 만듦
	static void combination(int count, int start) {
		if(count == k-5) { // 필수 알파벳 5개 배웠으니까 k-5개 배우면 그만
			simulate();
			return;
		}
		for(int i=start; i<26; i++) { // a부터 z까지
			if(i == 'a'-'a' || i == 'n'-'a' || i == 't'-'a' || i == 'i'-'a' || i == 'c'-'a') {
				continue;
			}
			comb[i] = true; // 배우고
			combination(count+1, i+1);
			comb[i] = false; // 다시 되돌림
		}
	}

	static void simulate() {
		int count = 0;
		for(int i=0; i<n; i++) {
			boolean flag = true;
			for(int j=0; j<26; j++) {
				if(words[i][j] && !comb[j]) { // 단어에는 있는데 가르칠 알파벳 조합에는 없다
					flag = false;
					break;
				}
			}
			if(flag) {
				count++;
			}
		}
		answer = Math.max(answer, count);
	}
}