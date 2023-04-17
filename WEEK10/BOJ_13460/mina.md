![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:FF658D,100:FFCB32&text=BOJ%2013460&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 13460 구슬 탈출 2](https://www.acmicpc.net/problem/13460)

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

    static int N, M, result;

    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};

    static char[][] board;

    public static void main(String[] args) throws Exception {

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        board = new char[N][M];
        result = 20;
        Pair red = null, blue = null;

        for (int i = 0; i < N; i++) {
            board[i] = br.readLine().toCharArray(); // 보드 입력
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (board[i][j] == 'R') {   // R이 들어있는 위치
                    board[i][j] = '.';
                    red = new Pair(i, j);
                } else if (board[i][j] == 'B') {    // B이 들어있는 위치
                    board[i][j] = '.';
                    blue = new Pair(i, j);
                }
            }
        }

        recur(0, red, blue);

        if (result == 20)
            result = -1;

        bw.write(Integer.toString(result));
        bw.flush();
        bw.close();
        br.close();
    }

    static void recur(int count, Pair red, Pair blue) {
        if (count == 10) {  // 최대 10회까지만 기울임
            return;
        }

        for (int i = 0; i < 4; i++) {   // 4방향으로 기울이기
            Pair r = new Pair(red.x, red.y); // 빨간 공 복사
            Pair b = new Pair(blue.x, blue.y);  //파란 공 복사
            int n = tilt(r, b, i);   // i 방향으로 공 기울이기
            if (n == -1) {
                // 파란 공이 빠진 경우 - 실패
                continue;
            } else if (n == 1) {
                // 빨간 공이 빠진 경우 - 성공
                result = Math.min(count + 1, result);
                return;
            } else if (n == 0) {
                // 계속 기울이기
                recur(count + 1, r, b);
            }
        }
    }

    static int tilt(Pair red, Pair blue, int d) {

        // ball1 기울이고 ball2 기울임
        Pair ball1 = null, ball2 = null;

        // 공 기울일 순서 결정
        if (d == 0) { // 오른쪽으로 기울이기 -> y좌표 더 큰 애부터 기울이기
            if (red.y < blue.y) {   // 파란공 y좌표가 더 큼
                ball1 = blue;
                ball2 = red;
            } else {
                ball1 = red;
                ball2 = blue;
            }
        } else if (d == 1) { // 아래쪽으로 기울이기 ->  x좌표 더 큰 애부터 기울이기
            if (red.x < blue.x) {   // 파란공 x좌표가 더 큼
                ball1 = blue;
                ball2 = red;
            } else {
                ball1 = red;
                ball2 = blue;
            }
        } else if (d == 2) { // 왼쪽으로 기울이기 -> y좌표 더 작은 애부터 기울이기
            if (red.y > blue.y) {   // 파란공 y좌표가 더 작음
                ball1 = blue;
                ball2 = red;
            } else {
                ball1 = red;
                ball2 = blue;
            }
        } else if (d == 3) { // 위쪽으로 기울이기 -> x좌표 더 작은 애부터 기울이기
            if (red.x > blue.x) {   // 파란공 x좌표가 더 작음
                ball1 = blue;
                ball2 = red;
            } else {
                ball1 = red;
                ball2 = blue;
            }
        }

        // 첫번째 공 기울이기
        int x = ball1.x;
        int y = ball1.y;
        while (true) {
            int nx = x + dx[d];
            int ny = y + dy[d];
            if (board[nx][ny] == '.') { // 이동 가능
                x = nx;
                y = ny;
            } else if (board[nx][ny] == '#') { // 막힘
                break;
            } else if (board[nx][ny] == 'O') { // 구멍에 빠짐
                x = nx;
                y = ny;
                break;
            }
        }

        // 이동한 위치 갱신
        ball1.x = x;
        ball1.y = y;

        // 두번째 공 기울이기
        x = ball2.x;
        y = ball2.y;
        while (true) {
            int nx = x + dx[d];
            int ny = y + dy[d];
            if (board[nx][ny] == 'O') { // 구멍에 빠짐
                x = nx;
                y = ny;
                break;
            } else if (nx == ball1.x && ny == ball1.y || board[nx][ny] == '#') { // 다른 구슬 or 장애물이 있어서 막힘
                break;
            } else if (board[nx][ny] == '.') { // 이동 가능
                x = nx;
                y = ny;
            }
        }

        // 이동한 위치 갱신
        ball2.x = x;
        ball2.y = y;

        if (board[blue.x][blue.y] == 'O') { // 파란 공이 빠진 경우 - 실패
            return -1;
        } else if (board[red.x][red.y] == 'O') {    // 빨간 공이 빠진 경우 - 성공
            return 1;
        } else {    // 아무도 안빠짐 - continue
            return 0;
        }
    }

    static class Pair {
        int x, y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}

```

<br>
<br>

# **🔑Description**

> 공을 보드 배열안에 넣어놓지 않고 Pair로 따로 빼서 좌표로만 관리하였다.\
> 중복조합 하듯이 재귀함수로 4방향을 선택하도록 호출하였고 호출할때마다 그 방향으로 공을 기울였다.\

<br>
<br>

# **📑Related Issues**

> 처음에는 공을 보드 배열 좌표 안에 넣어놓고 2중 for문으로 기울이려고 했으나 쉽지 않아보였다. 글구 이걸 또 4방향으로 구현하는것도 힘들어 보였다(한 방향만 만든 다음에 배열돌리기로 어떻게 해보려했지만 이것도 힘들것 같아서 포기)\
> 기울였을 때 영향 받는게 공 2개밖에 없어서 보드 배열에서 빼고 따로 공 2개의 좌표만 이동시켰다.\
> tilt 함수에서 빨간공, 파란공을 ball1, ball2로 상황에 맞게 지정해서 순서대로 공을 기울였다.\
> 처음에는 공 기울이는 로직(130 ~ 172)이 모든 if문 안에 들어있었고 ball1, ball2로 나누지도 않았다 그냥 상황에 맞게 빨간공/파란공을 순서 다르게 해서 굴려줌\
> 즉 저 로직이 tilt 안에 8번 들어있었다는 소리ㅇㅁㅇ\
> 리팩토링 할까말까 고민했는데 이거 읽을 사람들 생각하니 미안해서 리팩토링 했다\
> 근데 리팩토링 했더니 수행 시간도 절반인가 줄어들었다 굳b

<br>
<br>

# **🕛Resource**

| Memory  | Time  |
| ------- | ----- |
| 78244KB | 240ms |
