![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:FF658D,100:FFCB32&text=BOJ%2027921&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 27921 동전 퍼즐](https://www.acmicpc.net/problem/27921)

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

    static int h1, w1, h2, w2;

    public static void main(String[] args) throws Exception {


        StringTokenizer st = new StringTokenizer(br.readLine(), " ");

        h1 = Integer.parseInt(st.nextToken());  // 첫번째 배치 height
        w1 = Integer.parseInt(st.nextToken());  // 첫번째 배치 width

        char[][] origin = new char[h1][w1]; // 첫번째 배치

        char[][] board1 = new char[30][30]; // 첫번째 배치 놓을 보드
        char[][] board2;    // 두번째 배치 놓을 보드

        int coin = 0;   // 첫번째 배치의 동전 개수

        for (int i = 0; i < h1; i++) {
            String input = br.readLine();
            for (int j = 0; j < w1; j++) {
                origin[i][j] = input.charAt(j);
                if (origin[i][j] == 'O')
                    coin++;
            }
        }

        st = new StringTokenizer(br.readLine(), " ");

        h2 = Integer.parseInt(st.nextToken());  // 두번째 배치 height
        w2 = Integer.parseInt(st.nextToken());  // 두번째 배치 width

        char[][] dest = new char[h2][w2];   // 두번째 배치

        for (int i = 0; i < h2; i++) {
            String input = br.readLine();
            for (int j = 0; j < w2; j++) {
                dest[i][j] = input.charAt(j);
            }
        }

        // 보드1에 첫번째 배치 놓기
        for (int i = 0; i < h1; i++) {
            for (int j = 0; j < w1; j++) {
                board1[h2 + i - 1][w2 + j - 1] = origin[i][j];
            }
        }

        int max = 0;

        // 두번째 배치를 보드2에 놓고 보드1과 겹치는 최대 동전수 찾기
        // 두번째 배치를 보드1의 오른쪽-아래의 1칸부터 겹치게 놓는다
        // 왼쪽-위로 두번째 배치 이동하면서 놓음
        for (int i = h1 + h2 - 2; i >= 0; i--) {
            for (int j = w1 + w2 - 2; j >= 0; j--) {
                board2 = copy(dest, i, j);  // 보드2에 두번째 배치 놓기
                max = Math.max(max, check(board1, board2));
            }
        }

        bw.write(Integer.toString(coin - max));
        bw.close();
        br.close();
    }

    static char[][] copy(char[][] dest, int row, int col) {
        // row, col 위치에 두번째 배치 놓고 리턴

        char[][] board = new char[30][30];
        for (int i = 0; i < h2; i++) {
            for (int j = 0; j < w2; j++) {
                board[row + i][col + j] = dest[i][j];
            }
        }
        return board;
    }

    static int check(char[][] origin, char[][] dest) {
        int count = 0;

        // 보드1과 보드2에서 겹치는 동전 개수 리턴
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                if (origin[i][j] == 'O' && dest[i][j] == 'O')
                    count++;
            }
        }
        return count;
    }
}
```

<br>
<br>

# **🔑Description**

> 넓은 보드(30x30)에 첫번째 배치를 놓았다.\
> 또 다른보드에도 두번째 배치를 놓고 둘이 겹치는 동전의 개수를 count하여 최대가 되는 경우를 구해 총 동전 수에서 뺐다.
> 첫번째 배치의 보드는 고정시키고 두번째 보드는 왼쪽아래부터 오른쪽위까지 이동시키면서 둘이 비교했다.
>
> 대충 이런식..?
>
> 여기서부터
> | &nbsp; |&nbsp; | &nbsp;|
> | ------- | ---- | ----- |
> |&nbsp;| 첫번째 보드 | &nbsp;|
> |&nbsp;| &nbsp; | 두번째보드|
>
> 이렇게 될때까지 두번째 보드 옮겨가면서 비교함
> | 두번째보드 | &nbsp; | &nbsp; |
> | ---------- | ----------- | ------ |
> | &nbsp; | 첫번째 보드 | &nbsp; |
> | &nbsp; | &nbsp; | &nbsp; |

<br>
<br>

# **📑Related Issues**

> 넓은 보드 쓰지 않고 배치 배열 안에서 인덱스 멋있게 써서 해결하려고 했는데 머리아파서 그냥 넓은 보드 썼다ㅎㅁㅎ\
> 배치의 최대 크기가 10x10 이길래 이렇게 한건데 혹시 많이 커지면,...\ 다른 방법을 생각해야할듯..?

<br>
<br>

# **🕛Resource**

| Memory  | Time |
| ------- | ---- |
| 12592KB | 92ms |
