![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2020166&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> BOJ 20166 문자열 지옥에 빠진 호석

# 💻**Code**

```java
import java.io.*;
import java.util.*;

public class Main {

    static int n, m, k;
    static char[][] board; // 알파벳을 저장하는 격자
    static int[] dr = {-1, -1, -1, 0, 1, 1, 1, 0};
    static int[] dc = {-1, 0, 1, 1, 1, 0, -1, -1};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        board = new char[n][m];
        for (int i = 0; i < n; i++) {
            board[i] = br.readLine().toCharArray();
        }
        for (int i = 0; i < k; i++) {
            bw.write(simulate(br.readLine()) + "\n");
        }
        bw.flush();
        bw.close();
        br.close();
    }

    // 문자열을 입력받아 가능한 경우의 수를 반환하는 메소드
    static int simulate(String word) {
        int[][] counts = new int[n][m]; // 현재 위치에서 끝나는 경우의 수를 저장하는 배열
        Queue<int[]> q = new ArrayDeque<>(); // 가능한 (행, 열)을 저장할 큐
        for (int i = 0; i < n; i++) { // 입력받은 문자열에서 첫 글자와 동일한 알파벳을 격자에서 찾아서 counts와 q에 반영
            for (int j = 0; j < m; j++) {
                if (word.charAt(0) == board[i][j]) {
                    counts[i][j] = 1;
                    q.add(new int[]{i, j});
                }
            }
        }

        for (int i = 1; i < word.length(); i++) { // 0번째는 위에서 했으므로 1부터 문자열의 끝까지 반복
            int[][] newCounts = new int[n][m]; // 문자열의 현재 알파벳에 대한 경우의 수를 저장할 배열
            Queue<int[]> newQ = new ArrayDeque<>(); // 새로운 위치를 저장할 큐
            char ch = word.charAt(i); // 탐색할 알파벳

            while (!q.isEmpty()) {
                int r = q.peek()[0];
                int c = q.poll()[1];
                for (int j = 0; j < 8; j++) { // 8방 탐색
                    int newr = (r+dr[j] + n*2) % n; // 새로운 위치를 격자가 환형이므로 나누기를 통해 계산
                    int newc = (c+dc[j] + m*2) % m;
                    if (board[newr][newc] == ch) { // 원하는 알파벳이면
                        if (newCounts[newr][newc] == 0) { // 이번 탐색에서 처음 온 위치면 큐에 추가
                            newQ.add(new int[]{newr, newc});
                        }
                        newCounts[newr][newc] += counts[r][c]; // 경우의 수 증가
                    }
                }
            }
            q = newQ; // 큐 변경
            counts = newCounts; // 경우의 수 변경
        }

        int answer = 0;
        for (int i = 0; i < n; i++) {
            answer += Arrays.stream(counts[i]).sum(); // 각 경우의 수 총합 계산
        }
        return answer;
    }
}
```

# **🔑Description**

> 문자열의 길이만큼 가능한 위치마다 8방 탐색을 했다\
> 가능한 위치와 해당 위치에서의 경우의 수를 저장해서 탐색한다\
> 또한 격자가 환형이므로 행의 길이와 열의 길이와 나누었다.\
> 문자열의 길이가 최대 5까지 갈 수 있어서 행\*2나 열\*2를 더하고 나누었다. 

# **📑Related Issues**

> 처음에는 n*3 X m*3 짜리 배열을 선언하고 환형을 구현하려다가 나누기를 이용했다\
> newCounts와 newQ의 선언하는 위치가 헷갈렸다

# **🕛Resource**

| Memory    | Time    |
| --------- | ------- |
| 45228`KB` | 512`ms` |
