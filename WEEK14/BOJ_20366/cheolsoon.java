import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	/*
	 * 눈덩이 i의 지름 hi
	 * 하나의 눈사람 두개 눈덩이
	 * 아래 눈덩이 >= 위 눈덩이
	 * 눈덩이 N개 중에 서로 다른 4개 골라서
	 * 2개의 눈사람 만든다
	 * 눈사람의 키는 두 눈사람 지름 합과 같다
	 * 눈사람의 키 차이가 작을수록 사이가 좋다
	 * 만들 수 있는 눈사람의 키 차이의 최솟값을 구해라
	 * 
	 * */
	static int N, snow[];
	static boolean used[];
	
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		N = Integer.parseInt(in.readLine());
		snow = new int[N];
		used = new boolean[N];
		
		st = new StringTokenizer(in.readLine());
		for(int i=0;i<N;i++) {
			snow[i] = Integer.parseInt(st.nextToken());
		}
		
		Arrays.sort(snow);
		
		int minDiff = Integer.MAX_VALUE;
		for(int i=0;i<N-1;i++) {
			used[i] = true;
			int snowman1, snowman2;
			for(int j=i+1;j<N;j++) {
				used[j] = true;
				snowman1 = snow[i] + snow[j];
				// 두개 뽑고 나머지 두개 뽑기
				int start = 0;
				int end = N-1;
				while(start<end) {
					if(start == i || start == j) {
						start++;
						continue;
					}
					if(end == i || end == j) {
						end--;
						continue;
					}
					
					snowman2 = snow[start] + snow[end];
					minDiff = Math.min(minDiff, Math.abs(snowman1 - snowman2));
					if(snowman1 > snowman2) {
						start++;
					}else {
						end--;
					}
				}
			}
		}
		
		System.out.println(minDiff);
	}

}
