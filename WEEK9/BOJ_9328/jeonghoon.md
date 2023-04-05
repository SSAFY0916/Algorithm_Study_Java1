![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:FF658D,100:FFCB32&text=BOJ%209328&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 9328 열쇠](https://www.acmicpc.net/problem/9328)

<br>
<br>

# **💻Code**

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_9328 {
    private static int rowLen;
    private static int colLen;
    private static char[][] board;
    private static List<List<Pos>> doors;
    private static int[][] dir = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    private static int key;
    private static int result;

    static class Pos {
        int row;
        int col;

        public Pos(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int tc = Integer.parseInt(br.readLine());
        while (tc-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            rowLen = Integer.parseInt(st.nextToken());
            colLen = Integer.parseInt(st.nextToken());
            board = new char[rowLen][colLen];
            key = 0;
            doors = new ArrayList<>();
            result = 0;

            for (int i = 0; i < 26; i++) {
                doors.add(new ArrayList<>());
            }

            for (int i = 0; i < rowLen; i++) {
                String str = br.readLine();
                board[i] = str.toCharArray();
            }

            String str = br.readLine();
            if (!str.equals("0")) {
                for (int i = 0; i < str.length(); i++) {
                    key |= (1 << (str.charAt(i) - 'a'));
                }
            }

            bfs();

            sb.append(result).append("\n");
        }
        System.out.println(sb.toString());
    }

    private static void bfs() {
        Queue<Pos> q = new ArrayDeque<>();
        boolean[][] visited = new boolean[rowLen][colLen];

        /**
         * 가장자리에 있는 문자들 중 이동할 수 있는 곳을 Queue에 삽입
         * 
         * 문인 경우에는 이동할 수 없다고 우선 가정 하고 추후 key를 획득하였을 때
         * key 보유 여부에 따라서 Queue에 삽입
         *
         *  .    : 빈 공간
         *         이동할 수 있는 곳이므로 visited를 true로 만들고 Queue에 삽입
         *  $    : 훔쳐야 하는 문서
         *         이동할 수 있는 곳이므로 visited를 true로 만들고 Queue에 삽입
         *         또한 문서를 훔칠 수 있는 곳이기 때문에 result 값을 1 증가
         *  a~z  : 열쇠를 획득할 수 있는 곳
         *         이동할 수 있는 곳이므로 visited를 true로 만들고 Queue에 삽입
         *         또한 key를 획득하였기 때문에 해당 bit를 On
         *  A~Z  : 문
         *         key가 있다면 이동할 수 있기 때문에 visited를 true로 만드는 것 까지는 동일
         *         하지만 아직 BFS로 탐색을 진행할 수 있을지에 대한 여부는 불분명
         *         따라서 doors를 저장하는 2차원 리스트에 자신을 열 수 있는 key의 인덱스에 좌표를 추가
         */
        for (int i = 0; i < colLen; i++) {
            if (board[0][i] == '.') {
                visited[0][i] = true;
                q.add(new Pos(0, i));
            } else if (board[0][i] == '$') {
                result++;
                visited[0][i] = true;
                q.add(new Pos(0, i));
            } else if (board[0][i] >= 'a' && board[0][i] <= 'z') {
                key |= (1 << (board[0][i] - 'a'));
                visited[0][i] = true;
                q.add(new Pos(0, i));
            } else if (board[0][i] >= 'A' && board[0][i] <= 'Z') {
                visited[0][i] = true;
                doors.get(board[0][i] - 'A').add(new Pos(0, i));
            }

            if (board[rowLen - 1][i] == '.') {
                visited[rowLen - 1][i] = true;
                q.add(new Pos(rowLen - 1, i));
            } else if (board[rowLen - 1][i] == '$') {
                result++;
                visited[rowLen - 1][i] = true;
                q.add(new Pos(rowLen - 1, i));
            } else if (board[rowLen - 1][i] >= 'a' && board[rowLen - 1][i] <= 'z') {
                key |= (1 << (board[rowLen - 1][i] - 'a'));
                visited[rowLen - 1][i] = true;
                q.add(new Pos(rowLen - 1, i));
            } else if (board[rowLen - 1][i] >= 'A' && board[rowLen - 1][i] <= 'Z') {
                visited[rowLen - 1][i] = true;
                doors.get(board[rowLen - 1][i] - 'A').add(new Pos(rowLen - 1, i));
            }
        }

        for (int i = 1; i < rowLen - 1; i++) {
            if (board[i][0] == '.') {
                visited[i][0] = true;
                q.add(new Pos(i, 0));
            } else if (board[i][0] == '$') {
                result++;
                visited[i][0] = true;
                q.add(new Pos(i, 0));
            } else if (board[i][0] >= 'a' && board[i][0] <= 'z') {
                key |= (1 << (board[i][0] - 'a'));
                visited[0][i] = true;
                q.add(new Pos(i, 0));
            } else if (board[i][0] >= 'A' && board[i][0] <= 'Z') {
                visited[i][0] = true;
                doors.get(board[i][0] - 'A').add(new Pos(i, 0));
            }

            if (board[i][colLen - 1] == '.') {
                visited[i][colLen - 1] = true;
                q.add(new Pos(i, colLen - 1));
            } else if (board[i][colLen - 1] == '$') {
                result++;
                visited[i][colLen - 1] = true;
                q.add(new Pos(i, colLen - 1));
            } else if (board[i][colLen - 1] >= 'a' && board[i][colLen - 1] <= 'z') {
                key |= (1 << (board[i][colLen - 1] - 'a'));
                visited[i][colLen - 1] = true;
                q.add(new Pos(i, colLen - 1));
            } else if (board[i][colLen - 1] >= 'A' && board[i][colLen - 1] <= 'Z') {
                visited[i][colLen - 1] = true;
                doors.get(board[i][colLen - 1] - 'A').add(new Pos(i, colLen - 1));
            }
        }

        /**
         * 보유하고 있는 key인지 확인하고 해당 key를 보유하고 있다면
         * 해당 key를 통해서 열 수 있는 door들을 모두 Queue에 삽입
         * 삽입한 door들은 clear()함수를 통해서 list에서 삭제
         */
        for (int i = 0; i < 26; i++) {
            if ((key & (1 << i)) == (1 << i)) {
                for (int j = 0; j < doors.get(i).size(); j++) {
                    q.add(doors.get(i).get(j));
                }
                doors.get(i).clear();
            }
        }

        /**
         * BFS 탐색
         * 가장자리 문자들을 확인할 때와 마찬가지로 동작
         */
        while (!q.isEmpty()) {
            Pos tmp = q.poll();
            int curRow = tmp.row;
            int curCol = tmp.col;
            for (int i = 0; i < 4; i++) {
                int nextRow = curRow + dir[i][0];
                int nextCol = curCol + dir[i][1];

                if (!check(nextRow, nextCol)) continue;
                if (visited[nextRow][nextCol]) continue;
                if (board[nextRow][nextCol] == '*') continue;

                if (board[nextRow][nextCol] == '.') {
                    visited[nextRow][nextCol] = true;
                    q.add(new Pos(nextRow, nextCol));
                } else if (board[nextRow][nextCol] == '$') {
                    result++;
                    visited[nextRow][nextCol] = true;
                    q.add(new Pos(nextRow, nextCol));
                } else if (board[nextRow][nextCol] >= 'a' && board[nextRow][nextCol] <= 'z') {
                    key |= (1 << (board[nextRow][nextCol] - 'a'));
                    visited[nextRow][nextCol] = true;
                    q.add(new Pos(nextRow, nextCol));
                } else if (board[nextRow][nextCol] >= 'A' && board[nextRow][nextCol] <= 'Z') {
                    visited[nextRow][nextCol] = true;
                    doors.get(board[nextRow][nextCol] - 'A').add(new Pos(nextRow, nextCol));
                }
            }

            for (int i = 0; i < 26; i++) {
                if ((key & (1 << i)) == (1 << i)) {
                    for (int j = 0; j < doors.get(i).size(); j++) {
                        q.add(doors.get(i).get(j));
                    }
                    doors.get(i).clear();
                }
            }
        }
    }

    private static boolean check(int row, int col) {
        return row >= 0 && row < rowLen && col >= 0 && col < colLen;
    }
}

```

<br>
<br>

# **🔑Description**

> 기본적인 탐색 방법은 BFS를 사용하였습니다.
> 하지만 Queue만 사용하는 기본적인 BFS와 달리 door들을 저장하는 다른 2차원 리스트를 하나 만들어, 탐색 중 만나는 door들은 이 리스트에 저장해 두고, door를 열 수 있는 key를 보유하고 있을 경우에 door들을 Queue에 넣어주는 방식으로 설계하였습니다.

<br>
<br>

# **📑Related Issues**

> 지난 번 풀었던 “달이 차오른다, 가자.” 문제와 비슷하게 3차원 visited 배열을 이용해서 풀려고 생각하였으나, 이 문제는 key의 종류가 26개이기 때문에 최대 boolean[2^26][100][100]의 크기의 visited 배열이 생성되어, 메모리 초과가 발생합니다.
> 위와 같은 문제를 해결하기 위해서 다른 방법을 생각해 내는 과정이 어려웠습니다.
> 코드가 길어지다 보니, 반복문 안에서 인덱스 번호를 잘못 입력하여, 틀리는 경우도 발생을 했습니다.

<br>
<br>

# **🕛Resource**

| Memory   | Time   |
| -------- | ------ |
| 22404KB  | 248ms  |
