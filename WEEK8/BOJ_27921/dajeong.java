import java.io.*;
import java.util.*;

public class Main_27921_동전퍼즐 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int totalCoin = 0;
		
		int h1 = Integer.parseInt(st.nextToken());
		int w1 = Integer.parseInt(st.nextToken());
		char[][] arr1 = new char[h1][w1];
		for (int i=0; i<h1; i++) {
			String s = br.readLine();
			for (int j=0; j<w1; j++) {
				arr1[i][j] = s.charAt(j);
				if (arr1[i][j] == 'O') totalCoin++;
			}
		}
		
		st = new StringTokenizer(br.readLine());
		int h2 = Integer.parseInt(st.nextToken());
		int w2 = Integer.parseInt(st.nextToken());
		char[][] arr2 = new char[h2][w2];
		for (int i=0; i<h2; i++) {
			String s = br.readLine();
			for (int j=0; j<w2; j++) {
				arr2[i][j] = s.charAt(j);
			}
		}
		
		// 동전 겹치는 개수
		int dupCoin = 0;
		
		// i, j는 arr2에 대한 arr1의 offset
		for (int i=-h1+1; i<h2; i++) {
			for (int j=-w1+1; j<w2; j++) {
				
				int temp = 0;
				for (int x=0; x<h2; x++) {
					for (int y=0; y<w2; y++) {
						// arr2의 x,y와 arr1의 x-i, y-j 비교하기
						
						// out of index
						if (x-i < 0 || x-i >= h1 || y-j < 0 || y-j >= w1) continue;
						// arr1의 오프셋, arr2에 모두 동전이 있는 경우 세기 
						if (arr1[x-i][y-j] == 'O' && arr2[x][y] == 'O') temp++;
					}ㅈ
				}
				
				if (dupCoin < temp) dupCoin = temp;
			}
		}
		
		System.out.println(totalCoin - dupCoin);
		
	}
}