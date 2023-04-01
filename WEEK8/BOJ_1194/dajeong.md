![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%201194&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 1194 달이 차오른다, 가자](https://www.acmicpc.net/problem/1194)

<br>
<br>

# **Code**

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int N,M;
    static char[][][] map;
    static int[][][] dist;
    static boolean[][][] vis;
    static int[] dix = {-1,1, 0, 0};
    static int[] diy = {0, 0, -1, 1};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken()); // 미로 세로 크기 (행)
        M = Integer.parseInt(st.nextToken()); // 미로 가로 크기 (열)
        map = new char[N][M][(1<<6)+1]; // 열쇠 경우의 수에 따른 이동 가능한 맵 (멀티버스/차원확장)
        dist = new int[N][M][(1<<6)+1]; // bfs 이동 거리 저장 배열
        vis = new boolean[N][M][(1<<6)+1]; // bfs 방문 여부 체크 배열

        int[] curPos = new int[3];
        for (int r = 0; r < N; r++) {
            String str = br.readLine();
            for (int c = 0; c < M; c++) {
                map[r][c][0] = str.charAt(c);
                if (map[r][c][0] == '0') { // 현재 민식이 위치 저장
                    curPos[0] = r;
                    curPos[1] = c;
                    curPos[2] = 0; // 가진 열쇠 조합 상태
                }
            }
        }

        // 미로 탐색 후 도착지까지 가는데 걸린 최소 이동횟수 리턴
        // 미로 탐색 탐색할 경우 -1 리턴
        System.out.println(bfs(curPos));




    }

    private static int bfs(int[] initPos) {
        int res = -1; // 미로 탐색 실패할 경우, -1 리턴
        Queue<int[]> queue = new ArrayDeque<>();
        int initX = initPos[0];
        int initY = initPos[1];
        int initK = initPos[2];
        queue.offer(new int[]{initX, initY, initK});
        vis[initX][initY][initK] = true;
        dist[initX][initY][initK] = 1; // 맨 마지막에 dis 배열 값 -1한 결과 리턴

        while(!queue.isEmpty()) {
            int[] curP = queue.poll();
            int curX = curP[0];
            int curY = curP[1];
            int curK = curP[2];

            if (map[curX][curY][0] == '1') { // 도착지에 도착할 경우 거리 반환 후 종료
                return dist[curX][curY][curK] -1;
            }
            for (int i = 0; i < 4; i++) {
                int nx = dix[i] + curP[0];
                int ny = diy[i] + curP[1];
                int nk = curK;

                if (nx<0 || ny<0 || nx>=N || ny>=M || vis[nx][ny][nk]) continue;
                char target = map[nx][ny][0]; // key는 0으로 주어야 함!
                if (target == '#') continue; // 벽일 때는 못감

                if ('a' <= target && target <= 'f') { // 열쇠일 경우
                    nk = nk | (1<<(target-'a')); // 취득한 열쇠 플래그 세우기
                    if (!vis[nx][ny][nk]) {
                        queue.offer(new int[]{nx,ny,nk});
                        vis[nx][ny][nk] =true;
                        dist[nx][ny][nk] = dist[curX][curY][curK]+1;
                        continue;
                    }
                }
                if ('A' <= target && target <= 'F') { // 문을 만날 경우
                    if ((nk & (1<<(target-'A'))) != 0) { // 해당 문에 해당하는 키를 가지고 있을 경우
                        queue.offer(new int[]{nx,ny,nk});
                        vis[nx][ny][nk] =true;
                        dist[nx][ny][nk] = dist[curX][curY][curK]+1;

                    } else {
                        continue;
                    }
                }

                // '.', '0' 등 이동 가능한 경우 이동
                queue.offer(new int[]{nx,ny,nk});
                vis[nx][ny][nk] =true;
                dist[nx][ny][nk] = dist[curX][curY][curK] + 1;
            }
        }



        return res;
    }
}

```

<br>
<br>

# **🔑Description**

> 설계 시간: 2hr +a

> 구현 시간: 1hr 
<aside>
💡 설계 아이디어

    - 갖고 있을 수 있는 열쇠들의 조합을 하나의 상태로 관리 ⇒ **비트마스킹**
    - 열쇠를 얻을 때마다 비트마스킹 플래그 후 차원확장(다른 멀티버스로 이동)해서 bfs 수행
</aside>

<br>
<br>

# **📑Related Issues**

> Related Issues
<aside>

    개인적으로 인사이트가 많았던 문제! :)
    - **비트마스킹을 메모리, 시간 효율성, 가독성 외에 또 왜 쓰는지 확 와닿았다. 여러 상태 조합들을 하나로 표현할 수 있다. (int)**
    - 멀티버스(차원확장) bfs 문제가 확실히 바로 생각하기가 어렵다. 더 연습해야지.
    - 배열을 더 나눠보자. (실수 - 원본 map 배열, 차원확장해서 이동하는 배열을 따로 안두고 합쳤는데 그러다보니 탐색해서 값 가져올 때 실수한 부분이 있었다.)

    처음에는 일반적인 bfs (열쇠조합과 이동횟수를 객체로 저장)로 풀다가 무한루프로 미로를 빠져나오지 못하는 문제에 봉착했다.
    열쇠를 얻어야 하므로 방문표시 배열을 쓰지 않아야 한다고 생각했는데, 접근 방식 자체가 틀린걸 알게되었다.
    
    바뀐 열쇠 조합 상태에 따라 멀티버스(차원확장)로 이동해서 새로운 bfs를 해주면 방문표시를 하면서, 무한루프에 빠지지 않을 수 있었다.
    힌트를 얻기 전에 문제 풀면서 스스로 아, boolean 배열말고 비트마스킹으로 풀어? 라는 생각을 했었는데 차원확장을 생각하지 못해서 굳이 할 필요성을 못느껴서 일반적인 풀이로 풀다가 더 돌아왔다.

    설계에 많은 시간을 쓰게 되었는데, 그래도 고민하면서 힌트도 조금씩 얻고 내 스스로 문제를 푸니 굉장히 뿌듯했다.

    다만, 중복되는 코드가 꽤 많은데 이거 고치면 더 깔끔해질 것 같다. 근데 좀 귀찮고 시간 복잡도면에서 큰 차이 없을 것 같아 안고쳤다.
    

</aside>

<br>
<br>

# **🕛Resource**

| Memory | Time  | Implementation Time |
| -- |-------|---------------------|
| 17496KB | 152ms | 2 Hour  |
