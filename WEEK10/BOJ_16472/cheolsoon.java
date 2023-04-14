import java.util.*;
import java.io.*;

public class Main {
	static int N;
	static int[] arr;
	static int[] check;
	
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		N = Integer.parseInt(in.readLine());
		
		char[] temp = in.readLine().toCharArray();
		arr = new int[temp.length];
		check = new int[26];
		for(int i=0;i<temp.length;i++) {
			arr[i] = temp[i] - 'a';
		}
				
		int front = 0;
		int end = 0;
		int cnt = 0;
		int result = 0;
		int length = 1;
		if(N == 1) {
			System.out.println(1);
			return;
		}
		while(true) {
			if(end == arr.length-1) break;
			if(cnt == N) { //만약 알파벳 종류가 최대일 때
				if(check[arr[end]] == 0) {// 새로운게 들어왔을 때
					while(front<=end) {
						check[arr[front]]--; // 맨앞에 삭제
						length--;
						// 종류가 줄어들었을 때
						if(check[arr[front]] == 0) break;	
						else front++;
					}// 종류가 줄어들 때까지 front -> 뒤로 보내줌.
				}
			}else { // 알파벳 종류가 최대가 아닐 때
				// 새로운 게 들어왔을 때
				if(check[arr[end]] == 0) cnt++;
			}
			length++;
			check[arr[end]]++;
			result = Math.max(length, result);
			end++;
		}
		System.out.println(result);
	}
}
