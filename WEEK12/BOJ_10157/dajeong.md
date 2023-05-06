![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2010157&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 10157_자리배정](https://www.acmicpc.net/problem/10157)

<br>
<br>

# **Code**

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int C = Integer.parseInt(st.nextToken());
        int R = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(br.readLine());

        int[][] seats = new int[R][C];

        int[] dix = {-1, 0, 1, 0};
        int[] diy = {0, 1, 0, -1};

        int x =R-1;
        int y = 0;
        int dir = 0;
        if (K > R * C) {
            System.out.println(0);
            return;
        }
        for (int i = 1; i <= C * R; i++) {
            seats[x][y] = i;
            if (i == K) {
                System.out.printf("%d %d%n", y+1, R-x);
                return;
            }
            int nx = x + dix[dir];
            int ny = y + diy[dir];
            if (nx < 0 || nx >= R || ny < 0 || ny >= C|| seats[nx][ny] !=0) {
                dir = (dir + 1)%4;
                nx = x + dix[dir];
                ny = y + diy[dir];
            }
            x = nx;
            y = ny;

        }
        System.out.println(0);

    }


}
```

<br>
<br>

# **🔑Description**


<aside>
💡 설계 아이디어
    
    - 상우하좌 순서로 탐색하면서 해당하는 행, 열 번호 출력하기
    - 행, 열이 거꾸로 되어있는 점 주의
    
</aside>

<br>
<br>

# **📑Related Issues**

> Related Issues
<aside>

    - 일반 구현 문제
    - 다만 출력하는 부분에서 반대로 적는 실수가 있었다.

</aside>

<br>
<br>

# **🕛Resource**

| Memory | Time  | Implementation Time |
| -- |-------|---------------------|
| 18320KB | 160ms |  |
