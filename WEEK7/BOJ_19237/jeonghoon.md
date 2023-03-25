![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:FF658D,100:FFCB32&text=BOJ%2019237&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 19237 어른 상어](https://www.acmicpc.net/problem/19237)

<br>
<br>

# **💻Code**

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    public enum DIRECTION {
        ZERO, UP, DOWN, LEFT, RIGHT
    }

    static Smell[][] board;
    static int size;
    static int sharkNum;
    static int smellTime;
    static DIRECTION[][][] movePriority;
    static Shark[] sharks;
    static int[][] dir = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static int remainShark;

    static class Smell {
        int owner;
        int life;

        public Smell(int owner, int life) {
            this.owner = owner;
            this.life = life;
        }
    }

    static class Shark {
        boolean state;
        DIRECTION curDir;
        int curPosY;
        int curPosX;

        public Shark(boolean state, DIRECTION curDir, int curPosY, int curPosX) {
            this.state = state;
            this.curDir = curDir;
            this.curPosY = curPosY;
            this.curPosX = curPosX;
        }
    }

    public static DIRECTION numToDir(int num) {
        switch (num) {
            case 1:
                return DIRECTION.UP;
            case 2:
                return DIRECTION.DOWN;
            case 3:
                return DIRECTION.LEFT;
            case 4:
                return DIRECTION.RIGHT;
        }
        return DIRECTION.ZERO;
    }

    public static boolean check(int row, int col) {
        return row >= 0 && row < size && col >= 0 && col < size;
    }

    public static void move() {
        for (int i = 1; i <= sharkNum; i++) {
            if (!sharks[i].state) continue;
            boolean moveSuccess = false;
            for (int j = 0; j < 4; j++) {
                int nextRow = sharks[i].curPosY + dir[movePriority[i][sharks[i].curDir.ordinal() - 1][j].ordinal() - 1][0];
                int nextCol = sharks[i].curPosX + dir[movePriority[i][sharks[i].curDir.ordinal() - 1][j].ordinal() - 1][1];
                if (check(nextRow, nextCol) && board[nextRow][nextCol].owner == 0) {
                    sharks[i].curPosY = nextRow;
                    sharks[i].curPosX = nextCol;
                    sharks[i].curDir = movePriority[i][sharks[i].curDir.ordinal() - 1][j];
                    moveSuccess = true;
                    break;
                }
            }
            if (sharks[i].state && !moveSuccess) {
                for (int j = 0; j < 4; j++) {
                    int nextRow = sharks[i].curPosY + dir[movePriority[i][sharks[i].curDir.ordinal() - 1][j].ordinal() - 1][0];
                    int nextCol = sharks[i].curPosX + dir[movePriority[i][sharks[i].curDir.ordinal() - 1][j].ordinal() - 1][1];
                    if (check(nextRow, nextCol) && board[nextRow][nextCol].owner == i) {
                        sharks[i].curPosY = nextRow;
                        sharks[i].curPosX = nextCol;
                        sharks[i].curDir = movePriority[i][sharks[i].curDir.ordinal() - 1][j];
                        break;
                    }
                }
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j].life > 1) {
                    board[i][j].life--;
                } else if (board[i][j].life == 1) {
                    board[i][j].life = 0;
                    board[i][j].owner = 0;
                }
            }
        }

        for (int i = 1; i <= sharkNum; i++) {
            if (!sharks[i].state)
                continue;
            if (board[sharks[i].curPosY][sharks[i].curPosX].owner == 0 ||
                    board[sharks[i].curPosY][sharks[i].curPosX].owner == i) {
                board[sharks[i].curPosY][sharks[i].curPosX].owner = i;
                board[sharks[i].curPosY][sharks[i].curPosX].life = smellTime;
            } else {
                sharks[i].state = false;
                remainShark--;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        size = Integer.parseInt(st.nextToken());
        board = new Smell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = new Smell(0, 0);
            }
        }
        sharkNum = Integer.parseInt(st.nextToken());
        movePriority = new DIRECTION[sharkNum + 1][4][4];
        sharks = new Shark[sharkNum + 1];
        remainShark = sharkNum;
        smellTime = Integer.parseInt(st.nextToken());

        for (int i = 0; i < size; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < size; j++) {
                int tmp = Integer.parseInt(st.nextToken());
                if (tmp != 0) {
                    sharks[tmp] = new Shark(true, DIRECTION.ZERO, i, j);
                    board[i][j].life = smellTime;
                    board[i][j].owner = tmp;
                }
            }
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= sharkNum; i++) {
            int tmp = Integer.parseInt(st.nextToken());
            sharks[i].curDir = numToDir(tmp);
        }

        for (int idx = 1; idx <= sharkNum; idx++) {
            for (int priority = 0; priority < 4; priority++) {
                st = new StringTokenizer(br.readLine());
                for (int i = 0; i < 4; i++) {
                    int tmp = Integer.parseInt(st.nextToken());
                    movePriority[idx][priority][i] = numToDir(tmp);
                }
            }
        }

        int time = 0;
        while (time <= 1000) {
            time++;
            move();
            if (remainShark == 1) {
                break;
            }
        }

        if (time <= 1000)
            System.out.println(time);
        else
            System.out.println(-1);
    }
}
```

<br>
<br>

# **🔑Description**

> 시뮬레이션의 순서대로 상어가 자기 자리에 냄새를 뿌리고 이동한 후 겹치는 자리의 상어를 한마리씩 내쫓는 방식으로 문제대로 풀이했습니다

<br>
<br>

# **📑Related Issues**

> ENUM을 사용해보고 싶어서 풀다가 머리가 터지는줄 알았습니다...ㅎ

<br>
<br>

# **🕛Resource**

| Memory   | Time   |
| -------- | ------ |
| 17240KB  | 192ms  |
