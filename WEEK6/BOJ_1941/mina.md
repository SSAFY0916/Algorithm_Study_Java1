![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:FF658D,100:FFCB32&text=BOJ%201941&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 1941 소문난 칠공주](https://www.acmicpc.net/problem/1941)

<br>
<br>

# **💻Code**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static int K, N, result;
    static char[][] students;
    static boolean[][] selected, visited;
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};

    public static void main(String[] args) throws Exception {
        students = new char[5][5];  // 학생
        selected = new boolean[5][5];   // 학생 선택용
        visited = new boolean[5][5];    // bfs 방문 check
        for (int i = 0; i < 5; i++) {
            String s = br.readLine();
            for (int j = 0; j < 5; j++) {
                students[i][j] = s.charAt(j);
            }
        }

        // 5*5에서 7명의 학생을 선택
        combination(0, 0, 0, 0);

        bw.write(Integer.toString(result));

        bw.close();

    }

    static void combination(int nextX, int nextY, int count, int countY) {

        if (countY == 4) {  //임도연파가 4명 골라진 경우
            return;
        }

        if (count == 7) {   // 7명 선택됨
            if (bfs(nextX, nextY))  // 가로세로로 인접해있는지 bfs로 확인
                result++;
            return;
        }

        for (int j = nextY; j < 5; j++) {
            if (selected[nextX][j])
                continue;

            selected[nextX][j] = true;
            if (students[nextX][j] == 'Y')
                combination(nextX, j, count + 1, countY + 1);
            else
                combination(nextX, j, count + 1, countY);
            selected[nextX][j] = false;
        }

        for (int i = nextX + 1; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (selected[i][j])
                    continue;

                selected[i][j] = true;
                if (students[i][j] == 'Y')
                    combination(i, j, count + 1, countY + 1);
                else
                    combination(i, j, count + 1, countY);
                selected[i][j] = false;
            }
        }


    }

    static boolean bfs(int x, int y) {
        Queue<Pair> queue = new ArrayDeque<Pair>();
        for (int i = 0; i < 5; i++)
            Arrays.fill(visited[i], false);
        visited[x][y] = true;
        queue.offer(new Pair(x, y));

        int count = 6;  // 방문된 학생 수

        while (!queue.isEmpty()) {
            int curX = queue.peek().x;
            int curY = queue.poll().y;
            for (int i = 0; i < 4; i++) {
                int nextX = curX + dx[i];
                int nextY = curY + dy[i];
                if (nextX < 0 || nextX >= 5 || nextY < 0 || nextY >= 5 || !selected[nextX][nextY] || visited[nextX][nextY])
                    continue;
                visited[nextX][nextY] = true;
                queue.offer(new Pair(nextX, nextY));
                count--;
            }
        }

        //count가 0이 아니면 방문 못한 학생이 있었음(학생들이 인접해있지 않은 경우였음)
        return count == 0;
    }

    static class Pair {
        int x, y;

        Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}

```

<br>
<br>

# **🔑Description**

> 5\*5에서 7명의 학생을 선택하는 모든 경우의 수를 확인하였다.\
> 재귀 타면서 선택했는데 임도연이 4명 이상이 될때는 가지치기해줬다.\
> 7명을 전부 선택한 후에는 bfs로 그 7명이 서로 인접해있는지 확인했고 인접해있는 경우만 카운트했다.

<br>
<br>

# **📑Related Issues**

> 처음에 시간복잡도 계산하는데 뭔갈 잘못 계산했는지 Description에 나와있는 방법대로 하면 시간초과 날거라고 생각했다...\
> 며칠동안 고민하다가 다시 계산기 돌려봤는데? 시간초과 안날것 같아서 저대로 구현했더니 통과....ㅇㅁㅇ\
> 계산기를... 똑바로 쓰자.......

<br>
<br>

# **🕛Resource**

| Memory  | Time  |
| ------- | ----- |
| 78180KB | 268ms |
