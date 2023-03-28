![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:FF658D,100:FFCB32&text=BOJ%2019237&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 19237 어른 상어](https://www.acmicpc.net/problem/19237)

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

    static int N, M, K, total;

    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};

    static Marking[][] space;

    static Shark[] sharks;

    public static void main(String[] args) throws Exception {
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        total = M;
        space = new Marking[N][N];
        sharks = new Shark[M + 1];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < N; j++) {
                int n = Integer.parseInt(st.nextToken());
                if (n != 0) {
                    sharks[n] = new Shark(n, i, j);
                    space[i][j] = new Marking(n);
                } else {
                    space[i][j] = new Marking();
                }
            }
        }

        st = new StringTokenizer(br.readLine(), " ");
        for (int i = 1; i < M + 1; i++) {
            sharks[i].direction = Integer.parseInt(st.nextToken()) - 1;
        }

        for (int i = 1; i < M + 1; i++) {
            for (int j = 0; j < 4; j++) {
                st = new StringTokenizer(br.readLine(), " ");
                for (int k = 0; k < 4; k++) {
                    sharks[i].priority[j][k] = Integer.parseInt(st.nextToken()) - 1;
                }
            }
        }

        int t = 0;
        while (total != 1 && t++ < 1000) {

            // 이동 방향 결졍
            direct();

            // 냄새 1씩 감소
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    space[i][j].removeMarking();
                }
            }

            // 한꺼번에 이동
            move();
        }

        if(t == 1001)
            bw.write("-1");
        else
            bw.write(Integer.toString(t));

        bw.close();

    }

    static void move(){
        for (int i = 1; i < M + 1; i++) {
            if(sharks[i].live){
                int x = sharks[i].x;
                int y = sharks[i].y;
                if(space[x][y].quantity == K){
                    //나보다 번호가 작은 상어가 이미 들어와있음
                    sharks[i].live = false; //쥬금
                    total--;    // 상어 수 감소
                } else {
                    space[x][y].sharkNum = sharks[i].num;   // 해당 공간을 차지한 상어 num set
                    space[x][y].quantity = K;   // 영역표시
                }
            }
        }
    }

    static void direct() {
        for (int i = 1; i < M + 1; i++) {
            if (sharks[i].live) {   // 살아있는 상어들만 이동방향 찾음
                // 빈 칸 찾기
                int x = sharks[i].x;
                int y = sharks[i].y;
                int d = sharks[i].direction;
                int[] priority = sharks[i].priority[d];
                for (int j = 0; j < 4; j++) {
                    int nx = x + dx[priority[j]];
                    int ny = y + dy[priority[j]];

                    if (nx < 0 || nx >= N || ny < 0 || ny >= N || space[nx][ny].sharkNum != 0)
                        continue;

                    sharks[i].x = nx;
                    sharks[i].y = ny;
                    sharks[i].direction = priority[j];

                    break;
                }
                if (x == sharks[i].x && y == sharks[i].y) {
                    // 이동 못했으면 자기 냄새 있는 칸으로 이동
                    for (int j = 0; j < 4; j++) {
                        int nx = x + dx[priority[j]];
                        int ny = y + dy[priority[j]];

                        if (nx < 0 || nx >= N || ny < 0 || ny >= N || space[nx][ny].sharkNum != sharks[i].num)
                            continue;

                        sharks[i].x = nx;
                        sharks[i].y = ny;
                        sharks[i].direction = priority[j];
                        // 이동
                        break;
                    }
                }

            }
        }
    }

    static  void print(){
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(space[i][j].quantity+" ");
            }
            System.out.println();
        }
    }

    static class Shark {    // 상어 객체
        int num, x, y, direction;   // 상어 번호, 위치, 방향
        int[][] priority;   // 방향의 우선순위

        boolean live; // 상어 생존 여부

        Shark(int num, int x, int y) {
            this.num = num;
            this.x = x;
            this.y = y;
            this.direction = -1;
            this.priority = new int[4][4];
            this.live = true;
        }
    }

    static class Marking {  // 공간 객체
        int sharkNum, quantity; //공간을 차지한 상어 num, 그 공간에 남아있는 냄새의 양

        Marking() {
            this.sharkNum = 0;
            this.quantity = 0;
        }

        Marking(int sharkNum) {
            this.sharkNum = sharkNum;
            this.quantity = K;
        }

        void removeMarking() {  // 냄새 1씩 감소
            if (--quantity <= 0) {
                this.sharkNum = 0;
                this.quantity = 0;
            }
        }

    }
}


```

<br>
<br>

# **🔑Description**

> 상어 객체와 공간 1칸에 대한 객체를 만들어서 사용했다.\
> 상어들의 이동방향을 결정하고 각 공간에 대한 냄새를 1씩 감소시킨 후에 모든 상어를 그 방향으로 이동시켰다.\
> 이 루틴을 1번 상어만 남거나 1000초가 지날때 까지 반복하였다.

<br>
<br>

# **📑Related Issues**

> 처음에 방향정하고 이동시키는걸 한번에 하려다가 뭔가 이상해서 분리시켰다 ...!\
> 그리고 냄새 감소시키는것도 반복문 안에서 젤 첨으로 했는데 그러다보니까 방향 정하는게 좀 이상해져서 방향 정하고 냄새 감소시킨후에 한번에 이동시키는 걸로 결정했다.\

<br>
<br>

# **🕛Resource**

| Memory  | Time  |
| ------- | ----- |
| 13712KB | 136ms |
