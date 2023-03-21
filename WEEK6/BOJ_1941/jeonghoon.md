![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%201941&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 문제번호 문제이름](https://www.acmicpc.net/problem/1941)

<br>
<br>

# **Code**

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;

public class BOJ_1941 {
    static char[][] arr = new char[5][5];
    static boolean[][] selected = new boolean[5][5];

    static int res = 0;

    static class Pair {
        int first;
        int second;

        public Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        for (int i = 0; i < 5; i++) {
            String tmp = br.readLine();
            arr[i] = tmp.toCharArray();
        }
        // 조합으로 7명의 학생 선택
        comb(0,0);
        System.out.println(res);
    }

    private static void comb(int cnt, int start) {
        if (cnt == 7) {
            // 선택이 완료되었다면 BFS를 이용하여 탐색
            bfs();
            return;
        }
        for (int i = start; i < 25; i++) {
            selected[i / 5][i % 5] = true;
            comb(cnt + 1, i + 1);
            selected[i / 5][i % 5] = false;
        }
    }

    private static void bfs() {
        Queue<Pair> q = new ArrayDeque<>();
        boolean[][] visited = new boolean[5][5];
        int[][] dir = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        boolean flag = false;

        // 선택된 학생들 중 가장 첫번째 학생을 선택한 뒤 Queue에 넣어줌
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (selected[i][j]) {
                    flag = true;
                    q.add(new Pair(i, j));
                    visited[i][j] = true;
                    break;
                }
            }
            if (flag)
                break;
        }

        // 이다솜파와 임도연파의 수
        int sCnt = 0;
        int yCnt = 0;

        // BFS를 수행하며 poll된 학생의 파에 따라서 각 Cnt 증가
        while (!q.isEmpty()) {
            Pair tmp = q.poll();
            if (arr[tmp.first][tmp.second] == 'Y') {
                yCnt++;
            } else if (arr[tmp.first][tmp.second] == 'S') {
                sCnt++;
            }
            for (int i = 0; i < 4; i++) {
                int nextRow = tmp.first + dir[i][0];
                int nextCol = tmp.second + dir[i][1];

                if (check(nextRow, nextCol) && !visited[nextRow][nextCol] && selected[nextRow][nextCol]) {
                    q.add(new Pair(nextRow, nextCol));
                    visited[nextRow][nextCol] = true;
                }
            }
        }

        // 두 파의 학생 수의 합이 7이면 모두 이어져 있고, 다솜파가 도연파보다 사람이 많다면 성공
        if (yCnt + sCnt == 7 && sCnt > yCnt)
            res++;
    }

    private static boolean check(int nextRow, int nextCol) {
        return nextRow >= 0 && nextRow < 5 && nextCol >= 0 && nextCol < 5;
    }
}
```

<br>
<br>

# **🔑Description**

> Combination을 이용해서 이어짐의 여부와 관계 없이 7명의 학생을 선정하였습니다.
> 이후 BFS를 사용하여 연결되어 있는지의 여부와, 다솜파의 학생이 도연파의 학생보다 많은지의 여부를 판단하여 결과값을 증가시켜주는 방법으로 풀이하였습니다.

<br>
<br>

# **📑Related Issues**

> 처음에 어떻게 이어져 있는 학생들만 뽑을지 계속 고민하다가, 브루트포스 방법으로 풀이를 하더라도 시간이 크게 소요되지 않는 것을 계산하고, 7명을 선정한 후 이어져있는지 여부를 판단하는 방법으로 풀이하게 되었습니다.

<br>
<br>

# **🕛Resource**

| Memory   | Time  |
| -------- | ----- |
| 220820KB | 600ms |
