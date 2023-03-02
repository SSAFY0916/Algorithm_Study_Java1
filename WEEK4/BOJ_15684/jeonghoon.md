![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2015684&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 문제번호 문제이름](https://www.acmicpc.net/problem/15684)

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

    // 사다리타기 게임 함수
    static void ladder(int num, int[][] move) {

        // 모든 세로선들에 대해서 진행
        for (int i = 1; i <= colNum; i++) {
            // 현재 시작한 세로선 값
            int curCol = i;
            // 탐색중인 높이
            int curRow = 0;

            // 총 가로선 길이까지 아래로 내려가며 탐색 진행
            while (curRow <= possNum) {
                // 만약 move배열에 값이 있을 경우 해당 값으로 이동해야함
                if (move[curRow][curCol] != 0) {
                    curCol = move[curRow][curCol];
                }
                // 아래로 한칸 이동
                curRow++;
            }
            // 끝까지 내려갔는데 탐색을 시작한 세로선과 종료한 세로선이 다르다면 불가능
            if (i != curCol)
                return;
        }

        // 여기까지 잘 도착했다면 모든 세로선이 자기 세로선 번호에 도착
        // 따라서 현재 추가한 가로선의 개수를 res 변수에 저장
        res = num;
    }

    // 사다리를 추가해보는 함수
    static void addLadder(int cnt, int target, int[][] move) {
        // 현재 목표하는 갯수만큼 사다리를 추가했다면 사다리게임 시작
        if (cnt == target) {
            ladder(target, move);
            return;
        }
        // 사다리를 추가한 새로운 사다리 배열
        int[][] copyMove = new int[possNum + 1][colNum + 1];

        for (int i = 0; i <= possNum; i++) {
            copyMove[i] = Arrays.copyOf(move[i], colNum + 1);
        }

        // (possNum 높이에 colNum, colNum + 1을 잇는 가로선 추가)
        for (int i = 1; i <= possNum; i++) {
            for (int j = 1; j < colNum; j++) {
                // 추가하기 위해서는 colNum의 위치와 colNum + 1 위치에 모두 가로선이 없는 상태여야 함
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

        colNum = Integer.parseInt(st.nextToken()); // 세로선 개수
        rowNum = Integer.parseInt(st.nextToken()); // 가로선 개수
        possNum = Integer.parseInt(st.nextToken()); // 놓을 수 있는 가로선 위치

        int[][] move = new int[possNum + 1][colNum + 1];

        // 입력받은 가로선들을 move (사다리 배열)에 추가
        for (int i = 0; i < rowNum; i++) {
            StringTokenizer tmp = new StringTokenizer(br.readLine());
            int row = Integer.parseInt(tmp.nextToken());
            int col = Integer.parseInt(tmp.nextToken());
            move[row][col] = col + 1;
            move[row][col + 1] = col;
        }

        // 총 3개를 추가하는 것 까지 시뮬레이션
        for (int i = 0; i <= 3; i++) {
            addLadder(0, i, move);
            // res값이 변화가 있다면 i세로선이 모두 i세로선에서 종료된 경우가 있는 것
            if (res != -1)
                break;
        }

        System.out.println(res);
    }

}
```

<br>
<br>

# **🔑Description**

> 사다리를 추가하지 않는 경우부터 3개까지 추가하는 경우까지 사다리 게임을 실행해보며 있을 경우 즉시 프로그램이 종료되도록 작성하였습니다.
> 부분집합을 구하는 addLadder 함수를 사용하여 필요한 개수만큼 사다리를 추가하고, 모두 추가한 경우에 사다리게임을 실행하도록 설계하였습니다.

<br>
<br>

# **📑Related Issues**

> 처음에 문제를 제대로 확인하지 않고, 사다리를 추가하는 모든 경우를 계산하면 2^(300 * 9)의 횟수만큼 탐색하여야 한다고 생각하여, 다른 방법을 찾느라 생각보다 많은 시간을 소모했습니다.

<br>
<br>

# **🕛Resource**

| Memory | Time   |
| ------ | ------ |
| 256620KB | 1384ms |