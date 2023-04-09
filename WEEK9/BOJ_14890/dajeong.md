![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2014890&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 14890_경사로](https://www.acmicpc.net/problem/14890)

<br>
<br>

# **Code**

* 로직 수정 후 두번째 풀이
```java
// 두번째 풀이 (로직 수정)
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    static int N, L, cnt;
    static int[][] board;
    static boolean[] vis;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken()); // 행 수
        L = Integer.parseInt(st.nextToken()); // 경사로를 설치하는데 필요한 길의 수
        vis = new boolean[N];
        board = new int[N][N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        checkPath();
        board = rotate(board); // 90도 반시계 회전
        checkPath();
        System.out.println(cnt); // 가능한 길의 수(정답) 출력

    }

    private static void checkPath() {
        for (int i = 0; i < N; i++) {
            Arrays.fill(vis, false);
            int[] road = board[i];

            int p1 = 0;
            loop:
            while (p1 < N - 1) {
                int p2 = p1 + 1;
                if (Math.abs(road[p2]- road[p1]) >=2) break; // 차이가 2 이상일 경우 불가능 (종료)
                if (road[p2] == road[p1]+1) { // 올라가는 경사일 경우
                    int height = road[p1]; // 확인할 경사로 높이
                    for (int j = 0; j < L; j++) {
                        // 범위를 넘어서거나, 이미 경사로를 놓은적 있거나, 필요한 경사로 높이가 아닌 경우 불가능 (종료)
                        if (p1-j<0 || vis[p1-j] || road[p1-j] != height) break loop;
                    }
                    // 가능할 경우 올라가는 경사로 놓기
                    for (int j = 0; j < L; j++) {
                        vis[p1-j] = true;
                    }

                } else if (road[p2] == road[p1]-1) { // 내려가는 경사일 경우
                    int height = road[p1]-1; // 확인할 경사로 높이
                    for (int j = 1; j <= L; j++) {
                        // 범위를 넘어서거나, 필요한 경사로 높이가 아닌 경우 불가능(종료)
                        if (p1+j >= N || road[p1+j] != height) break loop;
                    }
                    // 내려가는 경사로 놓기
                    for (int j = 1; j <= L; j++) {
                        vis[p1+j] = true;
                    }
                    // p2를 경사로 끝까지 이동시키기
                    p2 = p1 + L;
                }
                p1 = p2;

            }

            if (p1 == N-1) cnt++; // p1 포인터가 끝까지 도달했을 경우 가능한 길이므로 cnt +1
        }
    }

    private static int[][] rotate(int[][] board) {
        int[][] newBoard = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                newBoard[i][j] = board[j][N - 1 - i];
            }
        }
        return newBoard;
    }

}


```



* 첫번 째 풀이
```java
// 첫번째 풀이
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int N,L, ans;
    static int[][] board;
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());
        board = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        ans = 0;
        search(board); // 행 탐색
        search(rotate()); // 90도 반시계 회전 후 다시 탐색 (열->행)
        System.out.println(ans);

    }
    private static void search(int[][] board) {
        // 행 탐색 
        for (int r = 0; r < N; r++) {
            int p1 = 0, p2 =0;
            int bigCnt = 0, curCnt = 1, smallCnt = 0;
            for (int c = 0; c < N; c++) {
                // 종료조건1) p1 포인터가 끝까지 올 경우 가능한 길로 채택. 카운트 후 다음 길 탐색.
                if(p1 == N-1) {
                    ans++;
                    break;
                }
                // 종료조건2) 종료되지 않은채 p2포인터만 끝까지 왔을 경우 불가능한 길. 종료
                if(p2 == N-1) break;

                // p2 한칸 이동
                p2++;

                // p1, p2 포인터별 지형 높이
                int p1Val = board[r][p1];
                int p2Val = board[r][p2];

                // p1, p2 지형 차이가 2이상일 경우 불가능하므로 종료.
                if(Math.abs(p1Val-p2Val)>1) break;
                else { // 경사로가 1이하로 차이날 경우 가능한 길인지 탐색
                    if(p1Val < p2Val) { // +1 지형을 만났을 경우 bigCnt+1
                        bigCnt++;
                    } else if (p1Val == p2Val) { // 같은 높이의 지형을 만났을 경우
                        if(bigCnt > 0 && curCnt < L) break; // 혹시나 그 전에 더 높은 지형을 만났었고,경사로가 만들어질 수 없으면 종료 (e.g 343)
                        if(smallCnt > 0 && smallCnt < L) break; // 혹시나 그 전에 더 낮은 지형을 만났었고 경사로가 만들어질 수 없으면 종료 (e.g 323)
                        p1 = p2; // p1을 이동
                        curCnt++; // 현재 지형 카운트 +1
                        continue;
                    } else { // -1 지형을 만났을 경우 smallCnt+1
                        smallCnt++;
                    }

                    if(smallCnt == L) { // 내려가는 경사로 길이가 L이므로 경사로 설치 가능
                        p1 += L; // p1을 내려가는 경사로 끝으로 옮기기
                        p2 = p1;  // p2를 p1 자리로 옮기기
                        smallCnt = 0; // smallCnt, bigCnt, curCnt 갱신
                        bigCnt = 0;
                        curCnt=0;
                    } else if(bigCnt >0) { // p2: 더 큰 지형을 만났을 경우
                        if(curCnt < L) break; // 올라가는 경사로가 불가능하면(경사로 길이만큼 현재 지형이 없을 경우) 종료
                        else { // 올라가는 경사로가 가능할 경우 
                            p1 += 1; // p1을 p2 자리로 옮기기
                            p2 = p1; // p2를 p1 자리로 옮기기
                            smallCnt = 0; // smallCnt, bigCnt, curCnt 갱신
                            bigCnt = 0;
                            curCnt=1;
                        }
                    }
                }
            }
        }
    }
    private static int[][] rotate() {
        int[][] newBoard = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                newBoard[i][j] = board[j][N-1-i];
            }
        }
        return newBoard;
    }

}

```


<br>
<br>

# **🔑Description**

> 설계 시간: 1hr

> 구현 시간: 2hr+a
<aside>
💡 설계 아이디어

        <수정 후 풀이>
            1. 길을 통과할 수 있는지는 한 쪽 방향에서만 확인하면 된다.
            2. 한 방향으로만 이동하면서 경사로를 놓으면서 끝에 도착할 수 있는지 확인한다
            3. 내려가는 경사일 때는 앞으로 L칸 만큼 같은 높이인지 확인하면 된다.
            4. 올라가는 경사일 때는 현재 칸부터 뒤로 L칸 만큼 같은 높이이고 이전에 경사로를 설치한 곳이 없는지 확인하면 된다.
        
        <첫번째 풀이>
          - p1(기준점), p2(기준점을 중심으로 이동) 포인터 두기
          - p1, p2 지형 차이가 1이하일 경우에만 가능한 길인지 탐색
          - p1 포인터가 행의 끝까지 올 경우(모든 경우 탐색했는데 불가능해서 종료되지 않은 경우) 가능한 길로 카운트
          - p1, p2 지형 차이에 따라서 동작 수행
              - p1<p2
                  - bigCnt++
              - p1==p2
                  - 중간에 323, 343식으로 불가능한 경우가 있는지 확인
                  - curCnt++
              - p1>p2
                  - smallCnt++
          - 올라가는 경사로가 필요할 경우(bigCnt>0)
              - curCnt 확인 후 경사로 가능한지 확인.
              - p1, p2 이동. cnt 갱신
          - 내려가는 경사로가 가능할 경우(smallCnt==L)
              - p1, p2 이동. cnt 갱신.

</aside>

<br>
<br>

# **📑Related Issues**

> Related Issues
<aside>

    - 내려가는 경사로, 올라가는 경사로를 나눠서 생각하지 못해 처음 설계에서 조건등이 덕지덕지 붙게 되었다. ㅠ
    - 다른 사람 풀이를 보니 올라가거나 내려가는 칸을 만날 경우, 현재 칸부터 경사로 범위까지 체크하고 방문표시를 했던데 이 방식이 남에게 설명하기에도 더 좋고 이해가 잘 되는 방법인 것 같다. 
    - 그리고 처음에 행, 열을 따로따로 수행했는데 90도 회전하는 방식으로 하면 불필요한 코드를 줄일 수 있을 것 같아서 고쳤다.

</aside>

<br>
<br>

# **🕛Resource**

| Memory | Time  | Implementation Time |
| -- |-------|---------------------|
| 15084KB | 148ms | 2 Hour  |
