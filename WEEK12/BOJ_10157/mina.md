![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:A066F9&text=BOJ%2010157&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 10157 자리배정](https://www.acmicpc.net/problem/10157)

<br>
<br>

# **💻Code**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws Exception {

        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine());

        int C = Integer.parseInt(st.nextToken());
        int R = Integer.parseInt(st.nextToken());

        int K = Integer.parseInt(br.readLine());

        // 사방 탐색용
        int[] dx = {1, 0, -1, 0};
        int[] dy = {0, 1, 0, -1};

        // 자리
        boolean[][] seat = new boolean[R + 1][C + 1];

        if (C * R < K) {    // 자리 개수보다 사람이 더 많음
            sb.append(0);
        } else {
            int n = 1;  // 자리 번호
            int x = 1;  // x 좌표
            int y = 1;  // y 좌표
            int d = 0;  // 방향
            seat[x][y] = true;  // 첫 자리 배정
            while (n != K) {
                // 다음 자리
                int nx = x + dx[d];
                int ny = y + dy[d];

                if (nx < 1 || nx > R || ny < 1 || ny > C || seat[nx][ny]) { // 범위 벗어나거나 이미 앉은 자리

                    //방향 전환하고 새로운 자리 찾음
                    d = (d + 1) % 4;
                    nx = x + dx[d];
                    ny = y + dy[d];
                }

                seat[nx][ny] = true;
                x = nx;
                y = ny;
                n++;
            }
            sb.append(y).append(" ").append(x);
        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
}

```

<br>
<br>

# **🔑Description**

> `(1, 1)` 자리를 `seat[1][1]` 에 넣고 아래 -> 오른쪽 -> 위 -> 왼쪽 방향대로 돌면서 K개의 자리를 배정했다.

<br>
<br>

# **📑Related Issues**

> 문제 그림이랑 똑같이 하려고 `seat[R][1]` 에 `(1, 1)` 자리가 들어가도록 했었다.\
> 근데 나중에 좌표가 뭔가 헷갈려서 그냥 그림을 위아래로 뒤집었다고 치고 `seat[1][1]` 자리에 `(1, 1)` 자리가 들어가도록 수정했다.

<br>
<br>

# **🕛Resource**

| Memory  | Time  |
| ------- | ----- |
| 12944KB | 100ms |
