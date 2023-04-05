
![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2020056&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> BOJ 20056 마법사 상어와 파이어볼
> 

# 💻**Code**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

	static int n, m, k, answer;
	static int[] dr = {-1, -1, 0, 1, 1, 1, 0, -1}; 		// 각 방향에 대한 값들
	static int[] dc = {0, 1, 1, 1, 0, -1, -1, -1};
	static List<Fireball> fireballs; 					// 파이어볼들을 저장하는 리스트, 순서는 딱히 상관 없음
	static class Fireball {								// 파이어볼 클래스
		int r, c, m, s, d;
		public Fireball(int r, int c, int m, int s, int d) {
			this.r = r;
			this.c = c;
			this.m = m;
			this.s = s;
			this.d = d;
		}
		
	}
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		fireballs = new ArrayList<>();
		for(int i=0; i<m; i++) {
			st = new StringTokenizer(br.readLine());
			Fireball fireball = new Fireball(
					Integer.parseInt(st.nextToken())-1,		// 0index 기반으로 변경
					Integer.parseInt(st.nextToken())-1,	
					Integer.parseInt(st.nextToken()),
					Integer.parseInt(st.nextToken()),
					Integer.parseInt(st.nextToken())
			);
			fireballs.add(fireball);
		}
		while(k-- > 0) {
			for(Fireball fireball : fireballs) {
				fireball.r += dr[fireball.d] * fireball.s;
				fireball.r %= n;
				if(fireball.r < 0) fireball.r += n;			// 속력만큼 갈 때 격자를 벗어나면 되돌아오도록 하지만 음의 인덱스로 가면 반대쪽 끝을 연결해주는 것 처럼 n을 더해줌 
				fireball.c += dc[fireball.d] * fireball.s;
				fireball.c %= n;
				if(fireball.c < 0) fireball.c += n;
			}
			Collections.sort(fireballs, (Fireball o1, Fireball o2) ->	// 행과 열을 기준으로 정렬함으로써 같은 위치에 있는 파이어볼을 찾을 수 있도록 함
					o1.r == o2.r ? Integer.compare(o1.c, o2.c) : Integer.compare(o1.r, o2.r)
			);
			fireballs.add(new Fireball(-1,-1,0,0,0));		// 이전의 파이어볼과 위치를 비교하면서 넣어주는데 마지막 파이어볼은 더이상 비교할 파이어볼이 없어 안들어갈 수 있기때문에 위치가 같을 수 없는 더미 파이어볼을 넣어줌
			int prevR = fireballs.get(0).r, prevC = fireballs.get(0).c, index = 0;
			List<Fireball> newFireballs = new ArrayList<>();// 새로운 파이어볼 리스트
			for(int i=1; i<fireballs.size(); i++) {
				if(prevR == fireballs.get(i).r && prevC == fireballs.get(i).c) {
					continue;								// 이전 파이어볼과 위치가 같으면 건너 뜀
				}else {
					if(index+1 == i) {						// 이전 파이어볼과 위치가 다르고 이전 파이어볼은 혼자 있으면 그냥 리스트에 추가
						newFireballs.add(fireballs.get(index));
					}else {									// 이전 파이어볼은 여러 파이어볼들과 같이 있음
						int d = fireballs.get(index).d;		// 파이어볼들의 방향을 저장
						int mSum = 0, sSum = 0;				// 질량과 속력의 합을 저장할 변수
						for(int j=index; j<i; j++) {
							mSum += fireballs.get(j).m;
							sSum += fireballs.get(j).s;
							if(d != -1 && d%2 != fireballs.get(j).d%2) {
								d = -1;						// 파이어볼들의 방향을 비교해 홀짝이 다르면 -1로 바꿔줌
							}
						}
						if(mSum/5 == 0)  {					// 파이어볼 소멸
							prevR = fireballs.get(i).r;		// 이전 파이어볼 위치를 현재 파이어볼 위치로 갱신
							prevC = fireballs.get(i).c;
							index = i;
							continue;
						}
						int[] directions = new int[4];
						if(d == -1) {						// 방향이 서로 다름
							directions = new int[] {1,3,5,7};
						}else {								// 방향이 서로 같음
							directions = new int[] {0,2,4,6};
						}
						for(int j=0; j<4; j++) {			// 새로운 파이어볼 리스트에 추가
							newFireballs.add(new Fireball(prevR, prevC, mSum/5, sSum/(i-index), directions[j]));
						}
					}
					prevR = fireballs.get(i).r;				// 이전 파이어볼 위치를 현재 파이어볼 위치로 갱신
					prevC = fireballs.get(i).c;
					index = i;
				}
			}
			fireballs = newFireballs;						// 기존 파이어볼 리스트를 새로운 파이어볼 리스트로 변경 
		}
		for(Fireball fireball : fireballs) {
			answer += fireball.m;
		}
		bw.write(answer + "\n");
		bw.flush();
		bw.close();
		br.close();
	}
}
```

# **🔑Description**

> 파이어볼들을 저장할 자료구조를 정하는게 까다로웠다.
파이어볼 리스트를 두고 2차원 격자에 칸마다 파이어볼 개수나 인덱스를 저장하는 방법도 생각했었고, 2차원 격자에 파이어볼 객체를 저장해 3차원 리스트를 만드는 방법이나 우선순위 큐로 같은 위치에 있는 파이어볼을 찾는 방법도 생각했었다.
하지만 2차원 격자를 두면 파이어볼을 움직일 때 움직인 파이어볼을  인덱스를 따라 또 움직이게 되고 이를 방지하는게 어려웠다.
그래서 파이어볼 리스트만 두고 새로운 리스트를 만들어 저장했다가 바꾸는 방법을 사용했다.
> 

# **📑Related Issues**

> 문제를 살짝 더 자세히 읽었으면 좋았을 것 같았다.
4방향에 대하여 각각의 코드들을 더 재사용할 수 있게 구현했으면 더 좋았을 것 같다.
> 

# **🕛Resource**

| Memory | Time |
| --- | --- |
| 33880KB | 544ms |