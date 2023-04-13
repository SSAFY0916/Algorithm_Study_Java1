![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2013460&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> BOJ 13460 구슬 탈출 2

# 💻**Code**

```java
import java.io.*;
import java.util.*;

public class Main {

    static int n, m, answer;
    static char[][] board;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, -1, 0, 1};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        board = new char[n][m]; // 보드 판
        int start = 0; // 초기 공들의 위치를 저장
        for (int i = 0; i < n; i++) {
            board[i] = br.readLine().toCharArray();
            for (int j = 0; j < m; j++) {
                if (board[i][j] == 'R') { // 빨간 공
                    start += i*11*11*11 + j*11*11;
                    board[i][j] = '.'; // 빈 공간으로 바꿈
                }
                if (board[i][j] == 'B') { // 파란 공
                    start += i*11 + j;
                    board[i][j] = '.'; // 빈 공간으로 바꿈
                }
            }
        }

        Set<Integer> visited = new HashSet<>(); // 공들의 위치 별로 방문 여부를 판별하기 위한 셋
        Queue<Integer> q = new ArrayDeque<>(); // 가능한 공들의 위치를 저장하는 큐
        q.add(start); // 초기 상태 추가
        visited.add(start); // 초기 상태 방문 처리
        int answer = 0; // 굴리는 횟수
        int[] red, blue; // 공들의 좌표를 저장하는 배열
        boolean redOut, blueOut; // 공이 보드판을 빠져나갔는지 여부
        loop:
        while (++answer <= 10) { // 최대 10번까지만 굴림
            Queue<Integer> newQ = new ArrayDeque<>(); // 새로운 큐를 만들어 굴리는 횟수 별 가능한 공들의 위치를 저장
            while (!q.isEmpty()) {
                for (int i = 0; i < 4; i++) { // 4방향으로 굴리기
                    red = getRedCoord(q.peek()); // 빨간 공의 위치 가져오기
                    blue = getBlueCoord(q.peek()); // 파란공의 위치 가져오기

                    redOut = false;
                    blueOut = false;
                    while (0 <= red[0]+dr[i] && red[0]+dr[i] < n && 0 <= red[1]+dc[i] && red[1]+dc[i] < m && (red[0]+dr[i] != blue[0] || red[1]+dc[i] != blue[1]) && board[red[0]+dr[i]][red[1]+dc[i]] != '#') { // 빨간 공 굴리기
                        red[0]+=dr[i];
                        red[1]+=dc[i];
                        if (board[red[0]][red[1]] == 'O') {
                            redOut = true;
                            red[0] = -1;
                            break;
                        }
                    }
                    while (0 <= blue[0]+dr[i] && blue[0]+dr[i] < n && 0 <= blue[1]+dc[i] && blue[1]+dc[i] < m && (blue[0]+dr[i] != red[0] || blue[1]+dc[i] != red[1]) && board[blue[0]+dr[i]][blue[1]+dc[i]] != '#') { // 파란 공 굴리기
                        blue[0]+=dr[i];
                        blue[1]+=dc[i];
                        if (board[blue[0]][blue[1]] == 'O') {
                            blueOut = true;
                            blue[0] = -1;
                            break;
                        }
                    }
                    while (0 <= red[0]+dr[i] && red[0]+dr[i] < n && 0 <= red[1]+dc[i] && red[1]+dc[i] < m && (red[0]+dr[i] != blue[0] || red[1]+dc[i] != blue[1]) && board[red[0]+dr[i]][red[1]+dc[i]] != '#') { // 빨간 공 굴리기
                        red[0]+=dr[i];
                        red[1]+=dc[i];
                        if (board[red[0]][red[1]] == 'O') {
                            redOut = true;
                            break;
                        }
                    }

                    if (redOut && !blueOut) { // 빨간 공만 빠짐
                        break loop;
                    } else if (!blueOut) { // 둘다 안 빠짐
                        int cur = makeInteger(red, blue);
                        if (visited.add(cur)) { // 방문 여부 체크
                            newQ.add(makeInteger(red, blue)); // 새로운 큐에 저장
                        }
                    }
                }
                q.poll(); // 이번 위치로 4방향 탐색 했으니까 큐에서 제거
            }
            q = newQ; // 새로운 큐와 바꾸기
        }
        bw.write((answer>10 ? -1 : answer) + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    // 공들의 위치를 표현하는 int에서 빨간 공의 위치를 알아오는 메소드
    static int[] getRedCoord(int x) {
        return new int[]{x / (11 * 11 * 11), x % (11 * 11 * 11) / (11 * 11)};
    }

    // 공들의 위치를 표현하는 int에서 파란 공의 위치를 알아오는 메소드
    static int[] getBlueCoord(int x) {
        return new int[]{x % (11 * 11) / 11, x % 11};
    }

    // 공들의 위치를 int로 표현하는 메소드
    static int makeInteger(int[] red, int[] blue) {
        return red[0]*11*11*11 + red[1]*11*11 + blue[0]*11 + blue[1];
    }
}
```

# **🔑Description**

> 보드 배열에서의 상황은 공들의 위치만 변하니까 공들의 위치를 int로 표현하고 보드엔 벽과 구멍, 빈 공간만 표현하도록 했다.\
> 공들의 위치를 int로 표현할 때는 보드의 행과 열이 최대 길이가 10이므로 `빨간 공의 행 좌표 * 11^3 + 빨간 공의 열 좌표 * 11^2 + 파란 공의 행 좌표 * 11 + 파란공의 열 좌표`로 표현 했다.\
> 큐에는 초기 공들의 좌표를 넣고 최대 10번 상하좌우 방향으로 굴렸다.\
> 중복된 경우에 대한 연산을 피하기 위해 탐색한 보드를 set으로 관리했다.\
> 큐에 있는 모든 경우에 대해 탐색하고 다음 가능한 경우에 대해 새로운 큐로 교체하는 방법으로 탐색했다.\
> 상하좌우 방향에 대해 공들이 겹쳐있을 수는 없고 어느 공이 굴리는 방향에 있는지에 따라 결과가 다르므로\
> 하나의 공을 먼저 움직이고 다른 공을 움직인 다음 다시 처음에 움직인 공을 한번더 움직이는 방식으로 구현했다.\
> 지금은 빨간공, 파란 공 순서 대로 굴린 다음 먼저 움직인 빨간공 다시 한번 굴렸다.

# **📑Related Issues**

> 이 문제를 3번째 푸는 건데 풀면서 아쉬웠던 점들이 있었다\
> 상하좌우 방향에 대하여 각기다른 반복문을 만들어서 코드가 길어질 때도 있었고\
> 모든 가능한 경우의 수만큼 보드 배열을 큐에 넣고 빼서 메모리를 많이 사용할 때도 있었다.\
> 보드 배열 대신 공들의 위치를 int로 만들어 셋에서 관리하는 방식으로 같은 경우를 방문하지 않게 하고\
> dr, dc를 사용하여 상하좌우 방향에 대하여 하나의 코드를 반복문에서 반복하는 방식으로 해결했다.

# **🕛Resource**

| Memory    | Time    |
| --------- | ------- |
| 14264`KB` | 132`ms` |
