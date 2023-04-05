![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2021609&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 21609 상어 중학교](https://www.acmicpc.net/problem/21609)

<br>
<br>

# **Code**

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    static int[][] board;
    static boolean[][] visOutBfs, visInBfs;
    static int N, M;
    static int[] dix = {-1, 1, 0, 0}; // 상하좌우
    static int[] diy = {0, 0, -1, 1};
    static List<ZzangInfo> zzangList;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken()); // 한변 길이
        M = Integer.parseInt(st.nextToken()); // 일반블록 색상 갯수 -> 아오 이거 열 아니라구ㅠ

        // 입력
        board = new int[N][N];
        visOutBfs = new boolean[N][N]; // 블록 그룹 찾을 때 쓰는 방문 배열 (무지개 블록 x)
        visInBfs = new boolean[N][N]; // bfs 내에서 인접한 블록 찾을 때 사용할 방문배열 (무지개 블록 포함)

        // 입력
        for (int r = 0; r < N; r++) {
            st = new StringTokenizer(br.readLine());
            for (int c = 0; c < N; c++) { // M으로 잘못 적어서 계속 틀렸다..
                board[r][c] = Integer.parseInt(st.nextToken());
            }
        }

        int ans = 0; // 점수합 (정답)
        while (true) { // 오토플레이 반복문
            // 1. 블록 그룹 탐색 (bfs)
            zzangList = new ArrayList<>(); // 탐색할 때마다 블록그룹별 대표자 리스트 갱신
            for (int r = 0; r < N; r++) { // 블록 그룹 찾기용 방문 배열 갱신
                Arrays.fill(visOutBfs[r], false);
            }

            for (int r = 0; r < N; r++) {
                for (int c = 0; c < N; c++) {
                    if (board[r][c] > 0
                        && !visOutBfs[r][c]) { // 블록그룹 탐색하지 않은 일반 블록일 때 bfs로 블록 그룹 탐색
                        bfs(r, c);
                    }
                }
            }

            //2. 제거할 블록 그룹 찾기
            if (zzangList.isEmpty()) {
                break; // 전체 블록 그룹들 중 대표자(블록 수 많은 그룹)의 블록 수가 0이면 오토플레이 종료
            }
            // 정렬
            Collections.sort(zzangList);

            ZzangInfo target = zzangList.get(0); // 지울 블록 그룹 정함(정렬된 첫번째 원소)

            // 3. 블록그룹 수(cnt) 제곱만큼 점수(ans) 획득, board int[][]에서 지우기
            ans += target.cnt * target.cnt;

            bfs(target.zzangX, target.zzangY); // 대표자 블록을 이용해서 다시 bfs 시행 -> 지울 블록 찾기
            // 사실 지울 블록을 찾는 bfs에서는 대표자를 다시 찾을 필요 없는 등 필요없는 연산이 좀 있어서 따로 boolean flag를 두려고 했으나, 일반화 시키는 과정에서 괜히 꼬이는 바람에 그냥 하나로 씀
            removeBlocks(visInBfs); // bfs 돌린 결과 기록한 방문배열을 이용해서 타겟 블록 그룹 블록들 다 제거하기

            //     4. 중력 작용
            gravity();
            //    *** 5. 보드 반시계 방향 회전.. ***
            int[][] newBoard = new int[N][N];
            for (int x = 0; x < N; x++) {
                for (int y = 0; y < N; y++) {
                    newBoard[x][y] = board[y][N-1-x];
                }
            }
            board = newBoard;

            // 6. 중력 또 작용
            gravity();

        }
        // 6. 점수합 리턴
        System.out.println(ans);
    }

    private static void gravity() {
        for (int c = 0; c < N; c++) { // 열 순회
            ArrayDeque<Integer> stack = new ArrayDeque<>();
            int black = 0;
            for (int r = 0; r < N; r++) {
                if (board[r][c] >= 0) { // 무지개블록, 일반블록이면
                    stack.push(board[r][c]);
                    board[r][c] = -2;
                } else if (r > black && board[r][c] == -1) { // 첫번째 행이 아닌 상황에서 검은 블록이면
                    black = r; // 검은 블록 행 저장
                    while (!stack.isEmpty()) {
                        board[black - 1][c] = stack.pop();
                        black--;
                    }
                }
            }

            int idx = N-1;
            while(!stack.isEmpty()) { // if문이 아니라 while..
                board[idx--][c] = stack.pop();
            }
        }
    }

    private static void removeBlocks(boolean[][] vis) {
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                if (vis[r][c]) {
                    board[r][c] = -2; // 빈칸 표시 (0은 무지개블록임)
                }
            }
        }
    }

    private static void bfs(int r, int c) { // 블록 그룹 찾기용 bfs
        for (int i = 0; i < N; i++) { // 매 bfs마다 경로탐색용 방문배열 갱신
            Arrays.fill(visInBfs[i], false);
        }
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{r, c}); // 시작 일반블록 큐에 넣기
        visOutBfs[r][c] = true; // 그룹에 쓰인 일반 블록 기록용
        visInBfs[r][c] = true; // 경로 탐색용

        int cnt = 1; // 블록 그룹의 블록 수
        int rbCnt = 0; // 무지개 블록 수
        int color = board[r][c];
        List<int[]> zzang = new ArrayList<>(); // 대표자(짱)이 될 수 있는 후보 리스트
        zzang.add(new int[]{r, c}); // ** 첫번째 블록도 짱 후보 리스트에 넣어야 함!!

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int curX = cur[0];
            int curY = cur[1];
            for (int i = 0; i < 4; i++) {
                int nx = curX + dix[i];
                int ny = curY + diy[i];
                if (nx < 0 || nx >= N || ny < 0 || ny >= N || visInBfs[nx][ny]) {
                    continue;
                }

                if (board[nx][ny] < 0) {
                    continue; // 검은 블록이거나 빈칸일 경우 넘어가기
                }

                if (board[nx][ny] != color && board[nx][ny] != 0) {
                    continue;
                }

                if (board[nx][ny] == 0) { // 무지개 블록이면
                    rbCnt++; // 갯수 세기
                } else if (board[nx][ny] > 0) { // 일반 블록이면 (빈칸이 있으므로, else if로 명시)
                    zzang.add(new int[]{nx, ny}); // 짱 후보 리스트에 넣기
                }

                queue.offer(new int[]{nx, ny});
                cnt++;

                visInBfs[nx][ny] = true; // 경로탐색용 방문배열에 기록
                if (board[nx][ny] > 0) { // 일반블록이면 이미 그룹이 지어진 블록이므로 visOutBfs 기록
                    visOutBfs[nx][ny] = true;
                }
            }
        }
        if (cnt >= 2) { //***
            // bfs 이후에 대표자 찾기
            Collections.sort(zzang, new Comparator<int[]>() { // 대표자 찾기 위해 정렬 (행, 열 오름차순)
                @Override
                public int compare(int[] o1, int[] o2) {
                    if (o1[0] == o2[0]) {
                        return o1[1] - o2[1];
                    }
                    return o1[0] - o2[0];
                }
            });
            int[] z = zzang.get(0); // 선택된 대표자
            zzangList.add(new ZzangInfo(cnt, rbCnt, z[0], z[1]));
        }
    }

    // 각 블록 그룹별 선택된 대표자의 정보 객체
    static class ZzangInfo implements Comparable<ZzangInfo> {

        int cnt; // 해당 블록 그룹 블록 수
        int rbCnt; // 무지개 블록 수
        int zzangX; // 대표자 행
        int zzangY; // 대표자 열

        public ZzangInfo(int cnt, int rbCnt, int zzangX, int zzangY) {
            this.cnt = cnt;
            this.rbCnt = rbCnt;
            this.zzangX = zzangX;
            this.zzangY = zzangY;
        }

        @Override // 찍어보기용
        public String toString() {
            return "ZzangInfo{" +
                "cnt=" + cnt +
                ", rbCnt=" + rbCnt +
                ", zzangX=" + zzangX +
                ", zzangY=" + zzangY +
                '}';
        }

        //(cnt 내림차순, rbCnt 내림차순, 기준블록 r,c 내림차순 *** 어휴...
        @Override
        public int compareTo(ZzangInfo o) {
            if (this.cnt == o.cnt) {
                if (this.rbCnt == o.rbCnt) {
                    if (this.zzangX == o.zzangX) {
                        return o.zzangY - this.zzangY;
                    }
                    return o.zzangX - this.zzangX;
                }
                return o.rbCnt - this.rbCnt;
            }
            return o.cnt - this.cnt;
        }
    }

}
```

<br>
<br>

# **🔑Description**

> 설계 시간: 30min

> 구현 시간: 3hr + a
<aside>
💡 설계 아이디어
    - 특별한 로직 없음 하라는 대로 해줌..

    검은색 블록은 -1,
    무지개 블록은 0
    일반 블록 m 이하의 자연수 (1이상 m이하)

    블록 그룹
    대표자: 일반블록(0,-1 아님) 중 행 작은순, 같으면 열 작은순
    일반블록 최소 1개 - 모두 숫자 같아야 함 (색)
    검은색블록 (-1) 있으면 안됨
    무지개블록 0 갯수 상관 없음

    int ans = 점수합
    <오토플레이 진행> - 블록 그룹이 존재하는 동안 반복
    board int[][]
    1. 블록그룹 탐색 (bfs)
    -> 위의 블록 그룹 조건에 안맞으면 세지 않기
    -> 블록그룹 칸 수 (cnt), 무지개 블록 수(rbCnt), 대표자 블록(int[]{r,c}) 구한다.=> 객체 zzang list에 저장 (cnt, rbCnt, 대표자 블록(int[]{r,c})

    2. 제거할 블록 그룹 찾기 ( 그룹에 속한 블록의 개수는 2보다 크거나 같아야 함!!!!)
    *** -> zzang list 정렬 (cnt 내림차순, rbCnt 내림차순, 기준블록 r,c 내림차순 ***
    -> 블록 그룹 최대 칸수가 0일 때 오토플레이 종료.
    3. 블록그룹 수(cnt) 제곱만큼 점수(ans) 획득, board int[][]에서 지우기
    4. 중력 작용
        - stack 사용 (벽돌깨기 라이브 방송 방식 사용 - 다른 방법도 확인)
    *** 5. 보드 반시계 방향 회전.. ***
    6. 중력 또 한다.
    <오토플레이 종료>
    7. 점수합 리턴
</aside>

<br>
<br>

# **📑Related Issues**

> Related Issues
<aside>

- 너무 고생한 문제..
- 사실 로직이 어렵다기보다는 구현에서 너무 귀찮고,, 실수 때문에 다시 빡셌다..
- 중간중간에 프린트 찍어보면서 틀린거 계속 고쳐나갔다.
- 실수 및 인사이트
    - N,M이 행과 열인줄 착각함. 이런 낚시에 아직도 놀아나다니..
    - 블록 그룹의 블록 수가 2이상인데, 해당 조건을 넣지 않아서 더 많은 점수합이 나왔었다. (1개인 블록도 그룹으로 세기 때문). 문제 조건을 다 기억 못할거면 코드에 적어두고 중간중간 맞는지 아닌지 프린트 찍거나 문제 다시 확인하자. (나를 믿지 말자..)
    - 일반화 하다가 꼬일거면, 그냥 따로따로 구현하자!
        - bfs 일반화 해보려고 괜히 boolean 더 쓰려다가 머릿속에서 꼬여서 그냥 bfs 하나로 퉁쳤다. 시간 복잡도에 큰 영향 없으면 퉁치는 것도 좋은 것 같다
        - 방문배열도 하나로 퉁치려다가, bfs 내에서 경로 (무지개 블록 포함) 탐색용 방문배열과 bfs 밖에서 사용할 일반 블록용 방문배열을 따로 두는게 안꼬여서 다시 구현을 하였다.
    - 중력 구현에서 시간 좀 잡힘..
        - stack으로 구현해보았는데 방해물 -1이 열에서 나오지 않았을 때에도 출력해주어야 하는데 못해줌
        - stack이 비어있지 않을 때 기준으로 while문 돌아야 하는데 if문을 돌았다.
</aside>

<br>
<br>

# **🕛Resource**

| Memory | Time  | Implementation Time |
| -- |-------|---------------------|
| 22680KB | 208ms | 3 Hour 30 Minutes   |
