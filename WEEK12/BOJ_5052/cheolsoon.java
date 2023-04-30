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

