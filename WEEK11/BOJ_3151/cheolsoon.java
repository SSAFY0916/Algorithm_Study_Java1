import java.util.*;
import java.io.*;

public class Main {
	/*
	 * 코딩실력 -10000~10000
	 * 세 팀원 코딩실력 합 0 이되는 팀을 만든다
	 * 합이 0이되는 팀을 몇개 만들 수 있는가 
	 * */
	static int N, student[];
    static long count;
	
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		N = Integer.parseInt(in.readLine());
		student = new int[N];
		st = new StringTokenizer(in.readLine());
		for(int i=0;i<N;i++) {
			int score = Integer.parseInt(st.nextToken());
			student[i] = score;
		}
		Arrays.sort(student);

        for(int curr=0;curr<N;curr++) { // curr번호를 무조건 선택한다고 하고
			if (student[curr] > 0) break;
			int left = curr+1; // left(curr+1), right(n-1)를 선택했을때
			int right = N-1;
			while(left < right) {
				int temp = student[left] + student[right] + student[curr];
				if(temp == 0) { // 세 수의 합이 0이라면 count++해준다. 
					if(student[left] == student[right]) { 
                    // left==4, right==4 인 경우 left~right까지 모두 4이기 때문에 
                    // 경우의 수는 (right-left-1)개 중에 2개를 뽑는 경우이다.
						int K = right-left+1;
						count+=(K*(K-1))/2;
						break;
					}					
					int l = 1;
					int r = 1;
                    // left값이 연속으로 같을 수 있는 경우를 계산.
					while((left+1<right) && (student[left] == student[left+1])) {
						left++;
						l++;					
					}
                    // right값이 연속으로 같을 수 있는 경우를 계산.
`					while((right-1>left) && (student[right] == student[right-1])) {
						right--;
						r++;
					}
					count+=l*r;
				}
				if(temp > 0) right--; // 합이 0보다 크면 right--
				else left++; // 합이 0보다 작으면 left++
			}
		}
		
		System.out.println(count);
	}
}

/*
 * 정렬 후
 * 1. 처음에는 양끝에를 포함하고 목표로하는 값이 있는지 이분탐색으로 찾도록 했다.
 * 양끝을 번갈아 하나씩 줄이면서 찾도록 했다. 
 * 2. 시작 0번 인덱스를 포함하고 1~N-1를 끝으로 하며, 셋의 합이 0보다 크면 right--, 합이 0보다 작으면 left++ 
 * -> -8, -2, -2, -2, 10, 10, 10, 10 같은 값이 있을 경우 방법을 생각해보면
 * -2위치 3가지 * 10의 위치 4가지 -> 12가지 존재
 * 3. count -> long으로 안해서 25에서 계속 틀림.
 * */