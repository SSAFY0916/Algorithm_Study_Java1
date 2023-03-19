![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:FF658D,100:FFCB32&text=BOJ%201062&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 1062 가르침](https://www.acmicpc.net/problem/1062)

<br>
<br>

# **💻Code**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    static int K, N, result;
    static int[] vocaFlag;

    static List<Character> nominee = new ArrayList<Character>();

    public static void main(String[] args) throws Exception {

        StringTokenizer st = new StringTokenizer(br.readLine(), " ");

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        boolean[][] alphabet = new boolean[N]['z' + 1];
        String[] voca = new String[N];
        vocaFlag = new int[N];

        int flag = 0;

        // a, n, t, i, c 는 필수적으로 선택해야함
        flag = flag | 1 << 'a' - 'a';
        flag = flag | 1 << 'n' - 'a';
        flag = flag | 1 << 't' - 'a';
        flag = flag | 1 << 'i' - 'a';
        flag = flag | 1 << 'c' - 'a';

        for (int i = 0; i < N; i++) {
            voca[i] = br.readLine();
            for (int j = 0; j < voca[i].length(); j++) {
                //각 단어마다 필요한 알파벳 추가
                vocaFlag[i] = vocaFlag[i] | 1 << voca[i].charAt(j) - 'a';
            }
        }

        if (K < 5) {    // a, n ,t, i ,c 선택 못하는 경우
            result = 0;
        } else {
            // 조합 돌면서 알파벳 K-5개 선택
            combination(0, flag, 0);
        }

        bw.write(Integer.toString(result));

        bw.close();

    }

    static void combination(int count, int flag, int start) {   // 알파벳 26개 중 count 만큼 선택
        if (count == K - 5) {
            int c = 0;
            for (int i = 0; i < N; i++) {
                if ((vocaFlag[i] & flag) == vocaFlag[i]) {  // 선택 된 알파벳들 중에 단어가 포함되어있는 경우
                    c++;
                }
            }
            result = Math.max(result, c);
            return;
        }

        for (int i = start; i < 26; i++) {
            if ((flag & 1 << i) != 0) continue;
            combination(count + 1, flag | 1 << i, i + 1);
        }
    }
}

```

<br>
<br>

# **🔑Description**

> a, n, t, i, c는 필수적으로 들어가야하므로 알파벳 중에서 K-5개를 선택할때마다 몇개를 읽을 수 있는지 카운트했다.\
> K가 5보다 작은수로 들어오는 경우에는 아무 단어도 읽을수 없으므로 바로 0이 출력되게 하였다.

<br>
<br>

# **📑Related Issues**

> 처음에는 남극 단어에 있는 알파벳들 안에서만 고르게 하려했는데 이게 더 복잡해지는것 같고 위 방법대로 해도 시간초과 안날 것 같아서 이렇게 바꿨다...!\
> 덕분에 비트마스킹도 연습하고 좋은듯...??!\
> 첨에 제출했을때 한번 틀렸는데 K < 5 인 경우에도 일단 단어 입력을 받고 출력해야하는데 K까지만 입력받고 바로 0 출력시켜서 틀렸당\
> 근데 이거 문제가 진짜 넘 슬프다.....8ㅅ8

<br>
<br>

# **🕛Resource**

| Memory  | Time  |
| ------- | ----- |
| 78180KB | 268ms |
