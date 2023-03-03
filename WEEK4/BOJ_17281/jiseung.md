
![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2017281&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> BOJ 17281 ⚾
> 

# 💻**Code**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {

	static int n, answer;
	// 이닝별 선수들의 결과를 저장하는 배열
	static int[][] results;
	// 순열로 정할 타순을 저장하는 배열
	static int[] perm;
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;
		n = Integer.parseInt(br.readLine());
		results = new int[n][9];
		for(int i=0; i<n; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<9; j++) {
				results[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		perm = new int[9]; // 순열로 9명의 타순을 정해서 저장해줄 배열
		perm[3] = 0; // 4번 타자는 1번 선수
		answer = 0;
		permutation(0, 0);
		bw.write(answer + "\n");
		bw.flush();
		bw.close();
		br.close();
	}
	
	static void permutation(int count, int flag) {
		if(count == 3) { // 4번 타자는 이미 정했으니까 다음으로 넘어감
			permutation(count+1, flag);
			return;
		}
		if(count == 9) { // 다 정했으면 점수를 계산
			answer = Math.max(answer, play());
			return;
		}
		for(int i=1; i<9; i++) {
			if((flag & (1<<i)) != 0) continue; // 타순에 이미 포함되면 건너뜀
			perm[count] = i;
			permutation(count+1, flag | (1<<i));
		}
	}
	
	// 정해진 타순대로 이닝을 반복하면서 점수를 계산하는 메소드
	static int play() {
		int score = 0; // 점수
		int cur = 0; // 현재 타석에 있는 선수
		for(int i=0; i<n; i++) {
			boolean first = false, second = false, third = false; // 1, 2, 3루에 주자가 있는지 저장하는 변수
			int out = 0; // 현재 이닝의 아웃카운트
			while(out < 3) { // 3아웃되면 반복 종료
				switch(results[i][perm[cur]]) {
				case 0: // 아웃일 때
					out++;
					break;
				case 1: // 1루타일 때
					if(third) score++;
					third = second;
					second = first;
					first = true;
					break;
				case 2: // 2루타일 때
					if(third) score++;
					if(second) score++;
					third = first;
					second = true;
					first = false;
					break;
				case 3: // 3루타일 때
					if(third) score++;
					if(second) score++;
					if(first) score++;
					third = true;
					second = false;
					first = false;
					break;
				case 4: // 홈런일 때
					if(third) score++;
					if(second) score++;
					if(first) score++;
					score++;
					third = false;
					second = false;
					first = false;
					break;
				}
				cur = (cur+1)%9; // 다음 타자
			}
		}
		return score;
	}
}

```

# **🔑Description**

> 순열로 타선을 정하고 이닝을 반복하면서 점수를 계산했다.<br>
> 처음에는 루상의 주자들을 저장할 때 큐를 선언하고 3개의 원소들을 넣어두고 유지하는 방법으로 했었다.<br>
> 만약에 2루타면 큐에서 2개의 원소를 빼고 다시 2개를 넣는 방식으로 했었는데 시간초과였다.


# **📑Related Issues**

> 비트마스킹을 활용하면서 & 연산의 결과를 0과 비교하는 부분을 자꾸 까먹었다.<br>
> 자바는 왜 int boolean 서로 안 친할까

# **🕛Resource**

| Memory | Time |
| --- | --- |
| 14472KB | 132ms |