![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2012100&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 문제번호 문제이름](https://www.acmicpc.net/problem/12100)

<br>
<br>

# **Code**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_12100 {
    static BufferedReader br;
    static BufferedWriter bw;
    // 입력 배열 저장
    static int[][] board;
    // board의 size
    static int size;
    // 만들 수 있는 최댓값을 저장하는 변수
    static int max = -1;

    // 위 키를 눌렀을 때의 동작
    public static int[][] onPressUp(int[][] board) {
        // 동작 후의 상태를 새롭게 저장하는 배열
        int[][] newBoard = new int[size][size];

        for (int i = 0; i < size; i++) {
            // 각 열별로 만들어지는 값을 새로운 1차원 배열에 저장
            int[] newLine = new int[size];
            // newLine에 저장되어야 하는 index
            int idx = 0;
            int curValue = -1;
            // 0행부터 size-1행까지 탐색
            for (int j = 0; j < size; j++) {
                // 0이면 다음 행을 검색하도록 함
                if (board[j][i] == 0)
                    continue;
                // curValue값이 -1일 때
                // 1. 아직 0 이외의 다른 값을 찾지 못했을 경우
                // 2. 이미 2개의 값이 합쳐져서 curValue값이 -1로 초기화 되었을 경우
                // 동작 : curValue값을 현재 행의 값으로 변경
                if (curValue == -1) {
                    curValue = board[j][i];
                    continue;
                }
                // curValue값이 현재 탐색중인 행의 값과 같을 경우
                // 두 개를 하나의 값으로 합치고 newLine에 넣어준 뒤 curValue 값을 -1로 초기화
                if (curValue == board[j][i]) {
                    newLine[idx++] = curValue * 2;
                    curValue = -1;
                    continue;
                }
                // curValue값이 현재 탐색중인 행의 값과 다를 경우
                // 두 값은 합쳐질 수 없으므로 newLine에 넣어주고 curValue 값을 현재 탐색 중인 행으로 변경
                if (curValue != board[j][i]) {
                    newLine[idx++] = curValue;
                    curValue = board[j][i];
                    continue;
                }
            }
            // 모든 탐색이 끝났을 때 curValue값이 -1이 아니라면 이 값도 newLine에 저장
            if (curValue != -1) {
                newLine[idx++] = curValue;
            }
            // newLine의 값들을 newBoard에 넣어줌
            for (int j = 0; j < size; j++) {
                newBoard[j][i] = newLine[j];
            }
        }
        // 동작을 수행한 이후의 배열을 반환
        return newBoard;
    }

    // onPressDown, onPressLeft, onPressRight 함수도 onPressUp 함수와 Index 탐색 순서 이외의 동작
    // 방식은 동일
    public static int[][] onPressDown(int[][] board) {
        int[][] newBoard = new int[size][size];

        for (int i = size - 1; i >= 0; i--) {
            int[] newLine = new int[size];
            int idx = size - 1;
            int curValue = -1;
            for (int j = size - 1; j >= 0; j--) {
                if (board[j][i] == 0)
                    continue;
                if (curValue == -1) {
                    curValue = board[j][i];
                    continue;
                }
                if (curValue == board[j][i]) {
                    newLine[idx--] = curValue * 2;
                    curValue = -1;
                    continue;
                }
                if (curValue != board[j][i]) {
                    newLine[idx--] = curValue;
                    curValue = board[j][i];
                    continue;
                }
            }
            if (curValue != -1) {
                newLine[idx--] = curValue;
            }
            for (int j = size - 1; j >= 0; j--) {
                newBoard[j][i] = newLine[j];
            }
        }

        return newBoard;
    }

    public static int[][] onPressLeft(int[][] board) {
        int[][] newBoard = new int[size][size];

        for (int i = 0; i < size; i++) {
            int[] newLine = new int[size];
            int idx = 0;
            int curValue = -1;
            for (int j = 0; j < size; j++) {
                if (board[i][j] == 0)
                    continue;
                if (curValue == -1) {
                    curValue = board[i][j];
                    continue;
                }
                if (curValue == board[i][j]) {
                    newLine[idx++] = curValue * 2;
                    curValue = -1;
                    continue;
                }
                if (curValue != board[i][j]) {
                    newLine[idx++] = curValue;
                    curValue = board[i][j];
                    continue;
                }
            }
            if (curValue != -1) {
                newLine[idx++] = curValue;
            }
            newBoard[i] = newLine;
        }

        return newBoard;
    }

    public static int[][] onPressRight(int[][] board) {
        int[][] newBoard = new int[size][size];

        for (int i = size - 1; i >= 0; i--) {
            int[] newLine = new int[size];
            int idx = size - 1;
            int curValue = -1;
            for (int j = size - 1; j >= 0; j--) {
                if (board[i][j] == 0)
                    continue;
                if (curValue == -1) {
                    curValue = board[i][j];
                    continue;
                }
                if (curValue == board[i][j]) {
                    newLine[idx--] = curValue * 2;
                    curValue = -1;
                    continue;
                }
                if (curValue != board[i][j]) {
                    newLine[idx--] = curValue;
                    curValue = board[i][j];
                    continue;
                }
            }
            if (curValue != -1) {
                newLine[idx--] = curValue;
            }
            newBoard[i] = newLine;
        }

        return newBoard;
    }

    // 동작 수행 전 배열과, 수행 이후 배열을 비교하여 동일한 배열이면 false를 반환
    // 이를 이용하여 동일한 배열에 대하여 다시 탐색하는 행위를 방지
    public static boolean check(int[][] b1, int[][] b2) {
        boolean flag = false;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (b1[i][j] != b2[i][j]) {
                    flag = true;
                    break;
                }
            }
            if (flag)
                break;
        }

        return flag;
    }

    // 배열을 전체 탐색하여 현재 배열에 있는 값 중 가장 큰 값을 찾기 위한 함수
    public static int maxValue(int[][] b) {
        int max = -1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                max = Math.max(max, b[i][j]);
            }
        }

        return max;
    }

    // 재귀적으로 동작하는 함수
    public static void recursive(int n, int[][] board) {
        // 5번 동작을 수행하였다면 (n==5) 현재 배열에서 최댓값을 찾은 후 함수 종료
        if (n == 5) {
            max = Math.max(max, maxValue(board));
            return;
        }

        // 기존 배열을 복사하여 사용
        int[][] newBoard = new int[size][size];
        for (int i = 0; i < size; i++) {
            newBoard[i] = Arrays.copyOf(board[i], size);
        }

        // Up, Down, Left, Right 동작들에 대해 각각 재귀적으로 동작
        int[][] tmp = onPressUp(newBoard);
        if (check(newBoard, tmp)) {
            recursive(n + 1, tmp);
        }

        tmp = onPressDown(newBoard);
        if (check(newBoard, tmp)) {
            recursive(n + 1, tmp);
        }

        tmp = onPressLeft(newBoard);
        if (check(newBoard, tmp)) {
            recursive(n + 1, tmp);
        }

        tmp = onPressRight(newBoard);
        if (check(newBoard, tmp)) {
            recursive(n + 1, tmp);
        }
    }

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));

        size = Integer.parseInt(br.readLine());
        board = new int[size][size];

        for (int i = 0; i < size; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < size; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        max = Math.max(max, maxValue(board));

        recursive(0, board);

        System.out.println(max);
    }

}

```

<br>
<br>

# **🔑Description**

> 각 방향키를 눌렀을 때 동작하는 함수들을 각각 구현해 주었습니다.
> 모든 경우의 수를 탐색하지만 함수 수행 후에 check() 함수를 이용하여 이전의 배열과 비교해 배열이 변하지 않은 경우에 더 이상 탐색을 진행하지 않도록 함으로써 실행시간을 단축시켰습니다.

<br>
<br>

# **📑Related Issues**

> 배열의 크기가 1인 케이스에서 max값을 초기화 해주지 않아서 이 문제를 발견하기 가지 오래 걸렸습니다.
> 전역 변수명과, 함수 영역에서 parameter로 넘기는 지역 변수명을 동일하게 사용하다 보니, parameter에서 실수한 오타(board -> boardd로 오타)로 인해 코드가 올바르게 동작하지 못하였고, 코드의 길이 또한 길다 보니 오타를 발견하기까지 많은 시간이 걸렸습니다.

<br>
<br>

# **🕛Resource**

| Memory | Time   |
| ------ | ------ |
| 21592KB | 200ms |