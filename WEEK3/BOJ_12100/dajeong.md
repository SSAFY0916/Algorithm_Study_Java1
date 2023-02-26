![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2012100&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

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
    for (int i = 0; i < (1 << (2 * 5)); i++) { //1024 // 5번에 대해 각각 회전 방향을 정해줌. ("감시" 문제와 동일한 방법)
      // 각 방향 경우의 수에 따라 시뮬레이션 할 배열 복사
      for (int r = 0; r < N; r++) {
        copyBoard[r] = Arrays.copyOf(board[r], board[r].length);
      }
      int tmp = i;
      for (int t = 0; t < 5; t++) {
        int dir = tmp % 4;
        tmp /= 4;
        shift(dir); // dir= 0,1,2,3 (좌상우하 == 0도, 90도, 180도, 270도 돌리기)
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

  private static void shift(int dir) { // 한 행을 기울여서 미는 작업
    while (dir-- > 0) {
      rotate(); // 배열을 시계방향으로 90도 돌림
    }
    for (int r = 0; r < N; r++) {
      int[] shiftArr = new int[N]; // 값을 민 결과를 저장할 새 배열
      int idx = 0; // 포인터 역할

      for (int i = 0; i < copyBoard[r].length; i++) {
        if (copyBoard[r][i] == 0) continue; // 원 배열의 값이 0이 아닐 때만 밀기 가능

        if (shiftArr[idx] == 0) {
          shiftArr[idx] = copyBoard[r][i]; // 새 배열에 값이 없으면 원 배열 값 추가
        } else { // 새 배열에 값이 있을 경우 비교
          if (shiftArr[idx] == copyBoard[r][i]) { // 이미 들어간 값과 같으면, 합쳐주기
            shiftArr[idx++] *= 2;
          } else { // 다르면 별도로 저장
            shiftArr[++idx] = copyBoard[r][i];
          }
        }
      }
      for (int c = 0; c < N; c++) {
        copyBoard[r][c] = shiftArr[c];
      }
    }
  }

  private static void rotate() { // 배열을 90도씩 회전하는 함수 (상하좌우 미는 동작을 각각 안만들고, 배열을 돌린 후 밀게끔 구현)
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

# **🔑Description**

> 설계 시간: 2~3일..

> 구현 시간: 1hr
<aside>
💡 설계 아이디어

1. 게임판 상하좌우 기울이기
  - 결론: 상하좌우 방향에 따라 배열을 돌려주고, 배열 값들을 밀어주기.


  - 1-1. 방향이 정해졌다고 가정하고 (왼쪽), **배열 값들을 미는 것**에 집중

    - **행만 미는** 함수 구현 후 각 행별로 적용 (쪼개서 생각하고 합치기)

   - 1-2. 상하좌우 방향 → 1-1에서 왼쪽 방향으로 민 배열을 **90도씩 돌리면** 됨! (기하학적 관점)


2. 5번 각 횟수마다 상하좌우 방향 경우의 수 정하기 (”감시”문제와 동일한 방법!)
- BOJ 15683 감시 문제(Week2 스터디 문제)와 동일하게 4진법 경우의 수 사용
</aside>

<br>
<br>

# **📑Related Issues**

> Related Issues
<aside>

- 이 문제가 정말 처음에 너무 어려워서 며칠을 고민했는데, 풀고 나서 보니 생각보다~~는? 나름 괜찮았다. 비록 다른 사람 풀이 참고했지만.. 중간중간 멈추고 혼자서 구현해볼 때 나와 아이디어가 같은 부분이 있어서 신기했다.
- 사실 시뮬레이션은 설계한 방향은 맞는데, 구현하는 부분에서 많이 막히는 것 같다. 이 부분은 연습이 많이 필요할 것 같다.
- 문제를 하나하나씩 쪼개서 합치는 관점이 중요하다. 내가 생각한 것보다 더 잘게 쪼개자.
- 다소 간단한 기하학적인 관점이 코드 작성을 좀 더 쉽게 해주는 것이 인상깊었다.
  - 배열 회전 rotate 함수
  - 배열 회전할 때 r,c값이 달라지면 좀 더 전처리를 해주어야 할텐데, 이 방법도 더 생각해보기!
</aside>

<br>
<br>

# **🕛Resource**

| Memory | Time   | Implementation Time |
| ------ | ------ |---------------------|
| 59688KB | 320ms | 3 Hour 0 Minutes    |
