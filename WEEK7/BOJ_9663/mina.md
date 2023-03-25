![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:FF658D,100:FFCB32&text=BOJ%209663&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 9663 N-Queen](https://www.acmicpc.net/problem/9663)

<br>
<br>

# **💻Code**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.List;

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    static int N, result;

    static Pair[] queen;

    static List<Pair> list = new LinkedList<Pair>();

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(br.readLine());

        queen = new Pair[N];

        for (int i = 0; i < N; i++) {
            queen[i] = new Pair(0, 0);
        }

        select(0, 0);  //(0,0)에 1개를 놓으면서 시작

        bw.write(Integer.toString(result));
        bw.close();
    }

    static void select(int count, int start) {
        if (count == N) {   // 서로 공격할 수 없게 놓는 경우
            result++;
            return;
        }
        for (int i = start; i < start + N; i++) {
            if (check(i / N, i % N, count)) {   // 놓을 수 있는지 check

                // 퀸 놓기
                queen[count].x = i / N;
                queen[count].y = i % N;

                // 다음 퀸을 다음 행의 0번째 열에 놓음
                select(count + 1, i - i % N + N);
            }
        }

    }

    static boolean check(int x1, int y1, int length) {

        for (int i = 0; i < length; i++) {
            int x2 = queen[i].x;
            int y2 = queen[i].y;

            int dx = Math.abs(x1 - x2);
            int dy = Math.abs(y1 - y2);

            // 같은 행, 열, 대각선에 놓여진 경우
            if (dx == 0 || dy == 0 || dx == dy) {
                return false;
            }
        }

        return true;
    }

    static class Pair {
        int x, y;

        Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "Pair{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }
}

```

<br>
<br>

# **🔑Description**

> 0,0 에서부터 1개를 놓으면서 다음 퀸을 놓을 수 있는지 없는지 체크했다.\
> 하나를 놓을 수 있는 경우만 그 다음 퀸을 놓을 수 있도록 백트래킹 방식을 사용하였다.

<br>
<br>

# **📑Related Issues**

> 코드 짜고 로컬에서 돌리는데 N=14 일때 시간초과 나는 것 같아서 계속 고민했다.\
> 근데 방법을 더 모르겠어서 제출했더니 통과 됐다 ㅎㅅㅎ\
> 다른 사람들은 수행시간이 나보다 적게 나온걸 보니 뭔가 더 좋은 방법이 있긴 한듯...?

<br>
<br>

# **🕛Resource**

| Memory  | Time   |
| ------- | ------ |
| 12980KB | 8780ms |
