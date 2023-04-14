![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2016472&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> BOJ 16472 고냥이

# 💻**Code**

```java
import java.util.*;
import java.io.*;

public class Main {
	static int N;
	static int[] arr;
	static int[] check;
	// 투포인터
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
		int cnt = 1;
		int result = 0;
		check[arr[end]]++;
		while(true) {
			end++;
            // 끝 문자까지 봤다면 종료
			if(end >= arr.length) break;
			
			check[arr[end]]++;
			if(check[arr[end]] == 1) {
				cnt++;
			}
            // 정해진 개수를 초과했다면 앞을 줄이면서 개수를 줄여준다.
			while(cnt > N) {
				check[arr[front]]--;
				if(check[arr[front]] == 0) {
					cnt--;
				}
				front++;
			}
			result = Math.max(result, end-front+1);//최대 길이 갱신		
		}
		
		System.out.println(result);
	}
}

```

# **🔑Description**

> front 포인터와 end 포인터 투 포인터로 투 포인터 사이 문자열에 쓰인 종류가 N개를 초과하지 않도록 한다.
>
> end가 새로운게 들어오면 종류가 추가되었으므로, front를 뒤로 가면서 종류가 줄어들때까지 하면서 종류의 개수를 맞춰준다.

# **📑Related Issues**

> 
>

# **🕛Resource**

| Memory    | Time    |
| --------- | ------- |
| 16184`KB` | 156`ms` |