![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:31BCFF,100:A066F9&text=BOJ%2020166&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 20166 문자열 지옥에 빠진 호석](https://www.acmicpc.net/problem/20166)

<br>
<br>

# **💻Code**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    static int N, M, K;

    static Set<String> set;

    static char[][] world;

    static String[] beloved;

    static int[] dx = {0, 1, 0, -1, -1, 1, -1, 1};
    static int[] dy = {1, 0, -1, 0, -1, -1, 1, 1};

    public static void main(String[] args) throws Exception {

        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        world = new char[N][M]; // 격자 세상
        beloved = new String[K];    // 신이 좋아하는 문자열

        Map<String, Integer> map = new HashMap<>(); // (key, value) = (문자열, 만들 수 있는 경우의 수)

        for (int i = 0; i < N; i++) {
            world[i] = br.readLine().toCharArray();
        }

        for (int i = 0; i < K; i++) {
            beloved[i] = br.readLine();
        }

        set = new HashSet<>();  // 문자열의 조합 저장
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < K; i++) {
            set.clear();
            if (map.get(beloved[i]) != null) {  // 문자열의 경우의 수를 구해놓음
                sb.append(map.get(beloved[i])).append("\n");    // 맵에서 경우의 수 가져옴
                continue;
            }

            // 문자열의 경우의 수를 구해야함
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < M; k++) {
                    recur(0, beloved[i], j, k, "");
                }
            }
            map.put(beloved[i], set.size());    // 맵에 저장
            sb.append(set.size()).append("\n");
        }

        bw.write(sb.toString());

        bw.flush();
        bw.close();
        br.close();
    }

    static void recur(int depth, String beloved, int x, int y, String ret) {
        if (world[x][y] == beloved.charAt(depth)) { // 선택한 좌표의 알파벳이 문자열의 depth위치의 알파벳이랑 일치함
            ret += Integer.toString(x * M + y) + ",";   // 좌표값의 인덱스값 ret에 이어 붙임
        } else {    // 선택한 알파벳이 문자열의 depth위치의 알파벳이랑 일치함
            return;
        }

        if (depth == beloved.length() - 1) {    // 문자열 끝까지 다 만듦
            set.add(ret);   // set에 추가
            return;
        }

        for (int i = 0; i < 8; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            // x좌표 환형 처리
            if (nx < 0)
                nx += N;
            else if (nx >= N)
                nx %= N;

            // y좌표 환형 처리
            if (ny < 0)
                ny += M;
            else if (ny >= M)
                ny %= M;

            recur(depth + 1, beloved, nx, ny, ret);
        }
    }

}

```

<br>
<br>

# **🔑Description**

> 모든 좌표에서 문자열을 만드는 recur를 실행했다.\
> 현재 좌표의 알파벳이 문자열의 현재 depth의 알파벳과 일치하면 그 알파벳을 선택했다.\
> 그 좌표의 index를 스트링에 이어붙여서 다 만들어지면 set에 넣어놔 같은 조합으로 만들지 않도록 했다.\
> 한 문자열에 대하여 경우의 수를 다 구하면 맵에 `(key, value) = (문자열, 만들 수 있는 경우의 수)` 로 저장해서 똑같은 문자열의 경우의 수를 구해야 할 경우에는 맵에서 바로 값을 가져오도록 했다.\

<br>
<br>

# **📑Related Issues**

> 제한 조건 중에 `신이 좋아하는 문자열은 중복될 수도 있다.`가 아주 눈에 띄엇지만... map을 쓰지 않고 모든 문자열에 대해 경우의수를 다 계산했었다.\
> 그대로 제출했더니 시간초과 나오길래 맵에 저장하도록 바로 수정했다...ㅎㅁㅎ

<br>
<br>

# **🕛Resource**

| Memory   | Time  |
| -------- | ----- |
| 146720KB | 696ms |
