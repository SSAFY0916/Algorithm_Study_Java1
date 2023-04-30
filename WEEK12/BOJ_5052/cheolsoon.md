![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%205052&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> BOJ 5052 전화번호 목록

# 💻**Code**

```java
import java.io.*;
import java.util.*;

// 방법 1: Map
public class Main_5052 {
	static int N;
	
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(in.readLine());
		for(int tc=1;tc<=T;tc++) {
			N = Integer.parseInt(in.readLine());
			String num[] = new String[N];
			Map<String, Integer> map = new HashMap<>();
			
			for(int i=0;i<N;i++) {
				num[i] = in.readLine();
				map.put(num[i], 1);
			}
			
			if(isConsistent(num, map)) {
				sb.append("YES\n");
			}else {
				sb.append("NO\n");
			}
		}
		
		System.out.println(sb);
		in.close();
	}

	private static boolean isConsistent(String[] num, Map<String, Integer> map) {
		for(int i=0;i<N;i++) {
			for(int j=1;j<num[i].length();j++) {
				// 한 문자의 접두어(0~j)가 해당 문자열인 값이 있는지 확인
				if(map.containsKey(num[i].substring(0, j))) {
					return false;
				}
			}
		}
		return true;
	}
}
// 방법2: string.startsWith
public class Main {
	static int N;
	
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(in.readLine());
		for(int tc=1;tc<=T;tc++) {
			N = Integer.parseInt(in.readLine());
			String num[] = new String[N];
			
			for(int i=0;i<N;i++) {
				num[i] = in.readLine();
			}
			
			// 문자열을 오름차순으로 정렬한다. 
			Arrays.sort(num);
			if(isConsistent(num)) {
				sb.append("YES\n");
			}else {
				sb.append("NO\n");
			}
		}
		
		System.out.println(sb);
		in.close();
	}

	private static boolean isConsistent(String[] num) {
		for(int i=0;i<N-1;i++) {
			// 이전 문자열이 다음 숫자의 앞부분, 시작부분에 포함되는지 확인한다.
			if(num[i+1].startsWith(num[i])) {
				return false;
			}
		}
		return true;
	}
}
import java.io.*;
import java.util.*;

// 방법 3: Trie 자료구조
public class Main_5052 {
	static class TrieNode {
		boolean isEndWord;
		TrieNode[] children = new TrieNode[10];
		
		public TrieNode() {
			isEndWord = false;
			children = new TrieNode[10];
			for(int i=0;i<=9;i++) {
				children[i] = null;
			}
		}
	}
	
	static TrieNode root;//root 노드 설정
	static int N;
	
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(in.readLine());
		for(int tc=1;tc<=T;tc++) {
			N = Integer.parseInt(in.readLine());
			root = new TrieNode();
			String num[] = new String[N];
			
			for(int i=0;i<N;i++) {
				num[i] = in.readLine();
				insert(num[i]);
			}

			for(int i=0;i<N;i++) {
				if(!available(num[i])) {
					sb.append("NO\n");
					break;
				}
				if(i==N-1) {
					sb.append("YES\n");
				}
			}
		}
		
		System.out.println(sb);
		in.close();
	}
	
	private static void insert(String str) {
		TrieNode curr = root;
		int length = str.length();
		int level;
		int index;
		
		for(level=0;level<length;level++) {
			index = str.charAt(level) - '0';
			if(curr.children[index] == null) {
				curr.children[index] = new TrieNode();				
			}
			curr = curr.children[index];
		}
		curr.isEndWord = true;
	}
	
	private static boolean available(String str) {
		TrieNode curr = root;
		int length = str.length();
		int level;
		int index;
		
		for(level=0;level<length;level++) {
			index = str.charAt(level) - '0';
			if(curr.isEndWord)
				return false;
			curr = curr.children[index];
		}
		return true;
	}
}


```

# **🔑Description**

> 1. Map에 저장하고 각 문자열들을 하나씩 확인하면서 접두사와 일치하는 값이 있는지 확인한다. 
>
> 2. String 정렬을 활용하여 
>
>    911 91155 993 91222 99333 을 오름차순 정렬하면
>
>    911 91155 91222 993 99333  과 같이 되는데 이렇게되면 바로 앞의 문자열과 뒤의 문자열을 비교해서 앞의 문자열을 포함하는지 확인하면된다. 
>
> 3. Trie 자료구조를 활용하여 부분 문자열이 일치하는, 접두사가 일치하는 문자열을 확인할 수 있다.

# **📑Related Issues**

> 처음에는 Trie 자료구조가 면접에 기출되는 질문, 알고리즘 종류이기 때문에 제출했지만
>
> Trie 외에도 풀 수 있는 방법이 많아서 다양한 방법으로 푸셨을 것 같습니다.

# **🕛Resource**

| Memory            | Time    |
| ----------------- | ------- |
| [String]34712`KB` | 676`ms` |
| [Map]100120`KB`   | 608`ms` |
| [Trie]166348`KB`  | 468`ms` |