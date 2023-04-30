import java.util.*;
import java.io.*;

public class Main_1253 {
	static int N, arr[];
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		N = Integer.parseInt(in.readLine());
		arr = new int[N];
		st = new StringTokenizer(in.readLine());
		for(int i=0;i<N;i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		Arrays.sort(arr);
		/*
		 * 어떤 수가 다른 수 두개 합으로 나타낼 수 있다면 좋다
		 * */
		int like = 0;// 좋아
		for(int curr=0;curr<N;curr++){
			/* 투 포인터로 
			 * 목표하는 값보다 작으면 left++
			 * 목표하는 값보다 크면 	right++ 
			 * */
			int left = 0;
			int right = N-1;
			while(left < right) {
				if(left == curr) left++;
				else if(right == curr) right--;
				else if(arr[curr] < arr[left] + arr[right]) right--;
				else if(arr[curr] > arr[left] + arr[right]) left++;
				else if(arr[curr] == arr[left]+arr[right]) {
					like++;
					break;
				}
			}
		}
		System.out.println(like);
		/* 
		 * 수의 위치가 다르면 값이 같아도 다른 수이다.
		 * -> 이거 때문에 처음에는 
		 * 2를 만들기 위해 1 1도 되는지 알았는데 
		 * 그게 아니라 확인해야하는 값이 1,2,3,3,4 이렇게 있을 때
		 * 3 3이 각각 다른 값이라는 의미인 것을 깨달았다.
		 * */
	}

}
