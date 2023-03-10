![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2012100&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **๐Problem**

> [BOJ 12100 2048(Easy)](https://www.acmicpc.net/problem/12100)

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

  static int N;
  static int[][] board, copyBoard;
  static int max;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    N = Integer.parseInt(br.readLine());
    max = 0;
    board = new int[N][N];
    copyBoard = new int[N][N];

    for (int r = 0; r < N; r++) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      for (int c = 0; c < N; c++) {
        board[r][c] = Integer.parseInt(st.nextToken());
        max = Math.max(max, board[r][c]);
      }
    }
    execute();
  }

  private static void execute() {
    for (int i = 0; i < (1 << (2 * 5)); i++) { //1024 // 5๋ฒ์ ๋ํด ๊ฐ๊ฐ ํ์  ๋ฐฉํฅ์ ์ ํด์ค. ("๊ฐ์" ๋ฌธ์ ์ ๋์ผํ ๋ฐฉ๋ฒ)
      // ๊ฐ ๋ฐฉํฅ ๊ฒฝ์ฐ์ ์์ ๋ฐ๋ผ ์๋ฎฌ๋ ์ด์ ํ  ๋ฐฐ์ด ๋ณต์ฌ
      for (int r = 0; r < N; r++) {
        copyBoard[r] = Arrays.copyOf(board[r], board[r].length);
      }
      int tmp = i;
      for (int t = 0; t < 5; t++) {
        int dir = tmp % 4;
        tmp /= 4;
        shift(dir); // dir= 0,1,2,3 (์ข์์ฐํ == 0๋, 90๋, 180๋, 270๋ ๋๋ฆฌ๊ธฐ)
      }
      checkMaxVal();
    }
    System.out.println(max);
  }

  private static void checkMaxVal() {
    for (int r = 0; r < N; r++) {
      for (int c = 0; c < N; c++) {
        max = Math.max(max, copyBoard[r][c]);
      }
    }
  }

  private static void shift(int dir) { // ํ ํ์ ๊ธฐ์ธ์ฌ์ ๋ฏธ๋ ์์
    while (dir-- > 0) {
      rotate(); // ๋ฐฐ์ด์ ์๊ณ๋ฐฉํฅ์ผ๋ก 90๋ ๋๋ฆผ
    }
    for (int r = 0; r < N; r++) {
      int[] shiftArr = new int[N]; // ๊ฐ์ ๋ฏผ ๊ฒฐ๊ณผ๋ฅผ ์ ์ฅํ  ์ ๋ฐฐ์ด
      int idx = 0; // ํฌ์ธํฐ ์ญํ 

      for (int i = 0; i < copyBoard[r].length; i++) {
        if (copyBoard[r][i] == 0) continue; // ์ ๋ฐฐ์ด์ ๊ฐ์ด 0์ด ์๋ ๋๋ง ๋ฐ๊ธฐ ๊ฐ๋ฅ

        if (shiftArr[idx] == 0) {
          shiftArr[idx] = copyBoard[r][i]; // ์ ๋ฐฐ์ด์ ๊ฐ์ด ์์ผ๋ฉด ์ ๋ฐฐ์ด ๊ฐ ์ถ๊ฐ
        } else { // ์ ๋ฐฐ์ด์ ๊ฐ์ด ์์ ๊ฒฝ์ฐ ๋น๊ต
          if (shiftArr[idx] == copyBoard[r][i]) { // ์ด๋ฏธ ๋ค์ด๊ฐ ๊ฐ๊ณผ ๊ฐ์ผ๋ฉด, ํฉ์ณ์ฃผ๊ธฐ
            shiftArr[idx++] *= 2;
          } else { // ๋ค๋ฅด๋ฉด ๋ณ๋๋ก ์ ์ฅ
            shiftArr[++idx] = copyBoard[r][i];
          }
        }
      }
      for (int c = 0; c < N; c++) {
        copyBoard[r][c] = shiftArr[c];
      }
    }
  }

  private static void rotate() { // ๋ฐฐ์ด์ 90๋์ฉ ํ์ ํ๋ ํจ์ (์ํ์ข์ฐ ๋ฏธ๋ ๋์์ ๊ฐ๊ฐ ์๋ง๋ค๊ณ , ๋ฐฐ์ด์ ๋๋ฆฐ ํ ๋ฐ๊ฒ๋ ๊ตฌํ)
    int[][] tmp = new int[N][N];
    for (int r = 0; r < N; r++) {
      tmp[r] = Arrays.copyOf(copyBoard[r], copyBoard[r].length);
    }
    for (int r = 0; r < N; r++) {
      for (int c = 0; c < N; c++) {
        copyBoard[r][c] = tmp[N-1-c][r];
      }
    }

  }

}
```

<br>
<br>

# **๐Description**

> ์ค๊ณ ์๊ฐ: 2~3์ผ..

> ๊ตฌํ ์๊ฐ: 1hr
<aside>
๐ก ์ค๊ณ ์์ด๋์ด

1. ๊ฒ์ํ ์ํ์ข์ฐ ๊ธฐ์ธ์ด๊ธฐ
  - ๊ฒฐ๋ก : ์ํ์ข์ฐ ๋ฐฉํฅ์ ๋ฐ๋ผ ๋ฐฐ์ด์ ๋๋ ค์ฃผ๊ณ , ๋ฐฐ์ด ๊ฐ๋ค์ ๋ฐ์ด์ฃผ๊ธฐ.


  - 1-1. ๋ฐฉํฅ์ด ์ ํด์ก๋ค๊ณ  ๊ฐ์ ํ๊ณ  (์ผ์ชฝ), **๋ฐฐ์ด ๊ฐ๋ค์ ๋ฏธ๋ ๊ฒ**์ ์ง์ค

    - **ํ๋ง ๋ฏธ๋** ํจ์ ๊ตฌํ ํ ๊ฐ ํ๋ณ๋ก ์ ์ฉ (์ชผ๊ฐ์ ์๊ฐํ๊ณ  ํฉ์น๊ธฐ)

   - 1-2. ์ํ์ข์ฐ ๋ฐฉํฅ โ 1-1์์ ์ผ์ชฝ ๋ฐฉํฅ์ผ๋ก ๋ฏผ ๋ฐฐ์ด์ **90๋์ฉ ๋๋ฆฌ๋ฉด** ๋จ! (๊ธฐํํ์  ๊ด์ )


2. 5๋ฒ ๊ฐ ํ์๋ง๋ค ์ํ์ข์ฐ ๋ฐฉํฅ ๊ฒฝ์ฐ์ ์ ์ ํ๊ธฐ (โ๊ฐ์โ๋ฌธ์ ์ ๋์ผํ ๋ฐฉ๋ฒ!)
- BOJ 15683 ๊ฐ์ ๋ฌธ์ (Week2 ์คํฐ๋ ๋ฌธ์ )์ ๋์ผํ๊ฒ 4์ง๋ฒ ๊ฒฝ์ฐ์ ์ ์ฌ์ฉ
</aside>

<br>
<br>

# **๐Related Issues**

> Related Issues
<aside>

- ์ด ๋ฌธ์ ๊ฐ ์ ๋ง ์ฒ์์ ๋๋ฌด ์ด๋ ค์์ ๋ฉฐ์น ์ ๊ณ ๋ฏผํ๋๋ฐ, ํ๊ณ  ๋์ ๋ณด๋ ์๊ฐ๋ณด๋ค~~๋? ๋๋ฆ ๊ด์ฐฎ์๋ค. ๋น๋ก ๋ค๋ฅธ ์ฌ๋ ํ์ด ์ฐธ๊ณ ํ์ง๋ง.. ์ค๊ฐ์ค๊ฐ ๋ฉ์ถ๊ณ  ํผ์์ ๊ตฌํํด๋ณผ ๋ ๋์ ์์ด๋์ด๊ฐ ๊ฐ์ ๋ถ๋ถ์ด ์์ด์ ์ ๊ธฐํ๋ค.
- ์ฌ์ค ์๋ฎฌ๋ ์ด์์ ์ค๊ณํ ๋ฐฉํฅ์ ๋ง๋๋ฐ, ๊ตฌํํ๋ ๋ถ๋ถ์์ ๋ง์ด ๋งํ๋ ๊ฒ ๊ฐ๋ค. ์ด ๋ถ๋ถ์ ์ฐ์ต์ด ๋ง์ด ํ์ํ  ๊ฒ ๊ฐ๋ค.
- ๋ฌธ์ ๋ฅผ ํ๋ํ๋์ฉ ์ชผ๊ฐ์ ํฉ์น๋ ๊ด์ ์ด ์ค์ํ๋ค. ๋ด๊ฐ ์๊ฐํ ๊ฒ๋ณด๋ค ๋ ์๊ฒ ์ชผ๊ฐ์.
- ๋ค์ ๊ฐ๋จํ ๊ธฐํํ์ ์ธ ๊ด์ ์ด ์ฝ๋ ์์ฑ์ ์ข ๋ ์ฝ๊ฒ ํด์ฃผ๋ ๊ฒ์ด ์ธ์๊น์๋ค.
  - ๋ฐฐ์ด ํ์  rotate ํจ์
  - ๋ฐฐ์ด ํ์ ํ  ๋ r,c๊ฐ์ด ๋ฌ๋ผ์ง๋ฉด ์ข ๋ ์ ์ฒ๋ฆฌ๋ฅผ ํด์ฃผ์ด์ผ ํ ํ๋ฐ, ์ด ๋ฐฉ๋ฒ๋ ๋ ์๊ฐํด๋ณด๊ธฐ!
</aside>

<br>
<br>

# **๐Resource**

| Memory | Time   | Implementation Time |
| ------ | ------ |---------------------|
| 59688KB | 320ms | 3 Hour 0 Minutes    |
