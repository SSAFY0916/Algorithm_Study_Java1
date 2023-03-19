![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%201062&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 1062 가르침](https://www.acmicpc.net/problem/1062)

<br>
<br>

# **Code**

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

// BOJ 1062 가르침
public class Main {

  static int N, K, ans;
  static boolean[] alphabet;
  static String[] wordList;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());
    N = Integer.parseInt(st.nextToken());
    K = Integer.parseInt(st.nextToken());
    wordList = new String[N];

    // 단어 저장
    for (int i = 0; i < N; i++) {
      wordList[i] = br.readLine();
    }

    // 가능한 알파벳 표시할 boolean 배열
    alphabet = new boolean[26];

    if (K < 5) { // 배울 수 있는 글자 수가 5 미만일 경우 단어를 읽을 수 없음
      System.out.println(0);
    } else {
      // K가 5이상일 경우 가르칠 글자 고르는 백트래킹 시행

      for (int i = 0; i < (1 << 26); i++) {
        // *** 알파벳 경우의 수 배열 갱신 후, 필수 글자 a, c, i, n, t true 표시
        Arrays.fill(alphabet, false);
        alphabet[0] = alphabet[2] = alphabet[8] = alphabet[13] = alphabet[19] = true;
        // 필수 글자를 포함하지 않는 경우의 수일 경우, 종료하기 위한 플래그
        boolean flag = true;
        // 선택된 비트 수가 K개가 아닐 경우 다음 경우의 수 확인
        if (Integer.bitCount(i) != K) {
          continue;
        }
        for (int k = 0; k < 26; k++) {
          // 필수 글자를 포합하지 않을 경우 다음 경우의 수 확인
          if (k == 0 || k == 2 || k == 8 || k == 13 || k == 19) {
            if ((i & (1 << k)) == 0) {
              flag = false;
              break;
            }
          } else {
            if ((i & (1 << k)) != 0) {
              alphabet[k] = true;
            }
          }
        }
        if (flag) { // 가능한 조합일 경우 읽을 수 있는 단어의 갯수 확인
          test(alphabet);
        }
      }
      System.out.println(ans);
    }
  }

  private static void test(boolean[] alphabet) {
    int cnt = 0; // 글자를 고르는 경우의 수 별로 읽을 수 있는 단어의 갯수
    for (int i = 0; i < N; i++) {
      String str = wordList[i];
      boolean flag = true;
      for (int s = 0; s < str.length(); s++) {
        char c = str.charAt(s);
        if (!alphabet[((int) c) - 97]) {
          flag = false;
          break;
        }
      }
      if (flag) {
        cnt++;
      }
    }
    ans = Math.max(ans, cnt); // 최댓값 갱신
  }
}
```

<br>
<br>

# **🔑Description**

> 설계 시간: 30min + a

> 구현 시간: 1hr
<aside>
💡 설계 아이디어

    - 조합으로 K개만큼 가르칠 알파벳을 고른다. 이를 알파벳 boolean 배열에 표시한다.
    - a,c,i,n,t의 경우 필수로 가르쳐야 단어를 읽을 수 있으므로 미리 디폴트로 알파벳 배열에 기록한다.
    - K가 5 미만일 때 필수로 가르쳐야할 글자도 가르칠 수 없으므로 0을 리턴한다. 5 이상일 때 조합(비트연산)을 통해 알파벳의 경우의 수를 고르고 읽을 수 있는 단어의 수를 구한다.
    - 읽을 수 있는 단어의 수가 최대인 값을 답으로 출력한다.

</aside>

<br>
<br>

# **📑Related Issues**

> Related Issues
<aside>

    - 처음에 백트래킹으로 풀었는데, 시간초과가 났다. 그래서 비트연산으로 풀었는데.. 다시 생각해보니 백트래킹에서 조합이 아니라 순열로 구해서 시간초과가 났던 것 같다. 
    - 꼭 비트연산으로 안풀어도 풀릴 것 같은데 알고리즘 분류에 왜 비트연산으로 기재되어 있는지 궁금하다. (스터디에서 물어봐야지)
    - 비트연산 조건처리가 오히려 복잡한 느낌이다. 
    - 시간이 1064ms가 나왔는데, 다른 풀이도 한번 찾아봐야겠다.
</aside>

<br>
<br>

# **🕛Resource**

| Memory | Time  | Implementation Time |
| -- |-------|---------------------|
| 17348KB | 1064ms | 2 Hour  |
