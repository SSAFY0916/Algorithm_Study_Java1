![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2015684&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **๐Problem**

> [BOJ ๋ฌธ์ ๋ฒํธ ๋ฌธ์ ์ด๋ฆ](https://www.acmicpc.net/problem/15684)

<br>
<br>

# **Code**

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_15684 {

    static int colNum, rowNum, possNum;
    static int res = -1;

    // ์ฌ๋ค๋ฆฌํ๊ธฐ ๊ฒ์ ํจ์
    static void ladder(int num, int[][] move) {

        // ๋ชจ๋  ์ธ๋ก์ ๋ค์ ๋ํด์ ์งํ
        for (int i = 1; i <= colNum; i++) {
            // ํ์ฌ ์์ํ ์ธ๋ก์  ๊ฐ
            int curCol = i;
            // ํ์์ค์ธ ๋์ด
            int curRow = 0;

            // ์ด ๊ฐ๋ก์  ๊ธธ์ด๊น์ง ์๋๋ก ๋ด๋ ค๊ฐ๋ฉฐ ํ์ ์งํ
            while (curRow <= possNum) {
                // ๋ง์ฝ move๋ฐฐ์ด์ ๊ฐ์ด ์์ ๊ฒฝ์ฐ ํด๋น ๊ฐ์ผ๋ก ์ด๋ํด์ผํจ
                if (move[curRow][curCol] != 0) {
                    curCol = move[curRow][curCol];
                }
                // ์๋๋ก ํ์นธ ์ด๋
                curRow++;
            }
            // ๋๊น์ง ๋ด๋ ค๊ฐ๋๋ฐ ํ์์ ์์ํ ์ธ๋ก์ ๊ณผ ์ข๋ฃํ ์ธ๋ก์ ์ด ๋ค๋ฅด๋ค๋ฉด ๋ถ๊ฐ๋ฅ
            if (i != curCol)
                return;
        }

        // ์ฌ๊ธฐ๊น์ง ์ ๋์ฐฉํ๋ค๋ฉด ๋ชจ๋  ์ธ๋ก์ ์ด ์๊ธฐ ์ธ๋ก์  ๋ฒํธ์ ๋์ฐฉ
        // ๋ฐ๋ผ์ ํ์ฌ ์ถ๊ฐํ ๊ฐ๋ก์ ์ ๊ฐ์๋ฅผ res ๋ณ์์ ์ ์ฅ
        res = num;
    }

    // ์ฌ๋ค๋ฆฌ๋ฅผ ์ถ๊ฐํด๋ณด๋ ํจ์
    static void addLadder(int cnt, int target, int[][] move) {
        // ํ์ฌ ๋ชฉํํ๋ ๊ฐฏ์๋งํผ ์ฌ๋ค๋ฆฌ๋ฅผ ์ถ๊ฐํ๋ค๋ฉด ์ฌ๋ค๋ฆฌ๊ฒ์ ์์
        if (cnt == target) {
            ladder(target, move);
            return;
        }
        // ์ฌ๋ค๋ฆฌ๋ฅผ ์ถ๊ฐํ ์๋ก์ด ์ฌ๋ค๋ฆฌ ๋ฐฐ์ด
        int[][] copyMove = new int[possNum + 1][colNum + 1];

        for (int i = 0; i <= possNum; i++) {
            copyMove[i] = Arrays.copyOf(move[i], colNum + 1);
        }

        // (possNum ๋์ด์ colNum, colNum + 1์ ์๋ ๊ฐ๋ก์  ์ถ๊ฐ)
        for (int i = 1; i <= possNum; i++) {
            for (int j = 1; j < colNum; j++) {
                // ์ถ๊ฐํ๊ธฐ ์ํด์๋ colNum์ ์์น์ colNum + 1 ์์น์ ๋ชจ๋ ๊ฐ๋ก์ ์ด ์๋ ์ํ์ฌ์ผ ํจ
                if (copyMove[i][j] == 0 && copyMove[i][j + 1] == 0) {
                    copyMove[i][j] = j + 1;
                    copyMove[i][j + 1] = j;
                    addLadder(cnt + 1, target, copyMove);
                    copyMove[i][j] = 0;
                    copyMove[i][j + 1] = 0;
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        colNum = Integer.parseInt(st.nextToken()); // ์ธ๋ก์  ๊ฐ์
        rowNum = Integer.parseInt(st.nextToken()); // ๊ฐ๋ก์  ๊ฐ์
        possNum = Integer.parseInt(st.nextToken()); // ๋์ ์ ์๋ ๊ฐ๋ก์  ์์น

        int[][] move = new int[possNum + 1][colNum + 1];

        // ์๋ ฅ๋ฐ์ ๊ฐ๋ก์ ๋ค์ move (์ฌ๋ค๋ฆฌ ๋ฐฐ์ด)์ ์ถ๊ฐ
        for (int i = 0; i < rowNum; i++) {
            StringTokenizer tmp = new StringTokenizer(br.readLine());
            int row = Integer.parseInt(tmp.nextToken());
            int col = Integer.parseInt(tmp.nextToken());
            move[row][col] = col + 1;
            move[row][col + 1] = col;
        }

        // ์ด 3๊ฐ๋ฅผ ์ถ๊ฐํ๋ ๊ฒ ๊น์ง ์๋ฎฌ๋ ์ด์
        for (int i = 0; i <= 3; i++) {
            addLadder(0, i, move);
            // res๊ฐ์ด ๋ณํ๊ฐ ์๋ค๋ฉด i์ธ๋ก์ ์ด ๋ชจ๋ i์ธ๋ก์ ์์ ์ข๋ฃ๋ ๊ฒฝ์ฐ๊ฐ ์๋ ๊ฒ
            if (res != -1)
                break;
        }

        System.out.println(res);
    }

}
```

<br>
<br>

# **๐Description**

> ์ฌ๋ค๋ฆฌ๋ฅผ ์ถ๊ฐํ์ง ์๋ ๊ฒฝ์ฐ๋ถํฐ 3๊ฐ๊น์ง ์ถ๊ฐํ๋ ๊ฒฝ์ฐ๊น์ง ์ฌ๋ค๋ฆฌ ๊ฒ์์ ์คํํด๋ณด๋ฉฐ ์์ ๊ฒฝ์ฐ ์ฆ์ ํ๋ก๊ทธ๋จ์ด ์ข๋ฃ๋๋๋ก ์์ฑํ์์ต๋๋ค.
> ๋ถ๋ถ์งํฉ์ ๊ตฌํ๋ addLadder ํจ์๋ฅผ ์ฌ์ฉํ์ฌ ํ์ํ ๊ฐ์๋งํผ ์ฌ๋ค๋ฆฌ๋ฅผ ์ถ๊ฐํ๊ณ , ๋ชจ๋ ์ถ๊ฐํ ๊ฒฝ์ฐ์ ์ฌ๋ค๋ฆฌ๊ฒ์์ ์คํํ๋๋ก ์ค๊ณํ์์ต๋๋ค.

<br>
<br>

# **๐Related Issues**

> ์ฒ์์ ๋ฌธ์ ๋ฅผ ์ ๋๋ก ํ์ธํ์ง ์๊ณ , ์ฌ๋ค๋ฆฌ๋ฅผ ์ถ๊ฐํ๋ ๋ชจ๋  ๊ฒฝ์ฐ๋ฅผ ๊ณ์ฐํ๋ฉด 2^(300 * 9)์ ํ์๋งํผ ํ์ํ์ฌ์ผ ํ๋ค๊ณ  ์๊ฐํ์ฌ, ๋ค๋ฅธ ๋ฐฉ๋ฒ์ ์ฐพ๋๋ผ ์๊ฐ๋ณด๋ค ๋ง์ ์๊ฐ์ ์๋ชจํ์ต๋๋ค.

<br>
<br>

# **๐Resource**

| Memory | Time   |
| ------ | ------ |
| 256620KB | 1384ms |