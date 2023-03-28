![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2019237&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> BOJ 19237 어른 상어

# 💻**Code**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {

    static int n, m, k;
		// 격자 판에 상어의 번호를 저장
    static int[][] board;
		// 상어의 정보를 상어의 번호 순으로 저장 (행, 열, 번호, 방향)
    static int[][] sharks;
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};
		// 상어의 방향별 탐색 우선순위를 저장
    static int[][][] priorities;
		// 격자에 상어의 번호와 냄새의 양을 저장
    static int[][][] smells;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        board = new int[n][n];
        sharks = new int[m+1][4];
        sharks[0][0] = -1;
        for(int i=0; i<n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
                if(board[i][j] > 0) {
                    sharks[board[i][j]] = new int[]{i, j, board[i][j], 0};
                }
            }
        }
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= m; i++) {
            sharks[i][3] = Integer.parseInt(st.nextToken()) -1;
        }
        priorities = new int[m+1][4][4];
        for (int i = 1; i <= m; i++) {
            for (int j = 0; j < 4; j++) {
                st = new StringTokenizer(br.readLine());
                for (int l = 0; l < 4; l++) {
                    priorities[i][j][l] = Integer.parseInt(st.nextToken()) -1;
                }
            }
        }
        smells = new int[n][n][2];
        int answer = 0;
        while(++answer <= 1000) { // 1000초까지만 실행해보고 안되면 그만
            if(simulate())
                break;
        }
        bw.write(((answer==1001) ? -1 : answer) + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    static boolean simulate() {
        smell();
        move();
        return (countSharks() == 1); // 상어가 한 마리만 남으면 true 리턴
    }

    static void smell() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                smells[i][j][1]--; // 현재 남아있는 냄새 양 - 1
            }
        }

        for(int[] shark : sharks) {
            if(shark[0] == -1) { // 상어가 죽었으면 건너뜀
                continue;
            }
            smells[shark[0]][shark[1]] = new int[]{shark[2], k}; // 냄새 남기기
        }
    }

    static void move() {
        int[][] newBoard = new int[n][n]; // 격자를 새로 만들어 상어들의 새로운 위치에 저장
        for(int[] shark : sharks) {
            if(shark[0] == -1) { // 상어가 죽었으면 건너뜀
                continue;
            }
            int mine = -1; // 빈 칸이 없을 때 사용할 나의 냄새가 남아있는 방향 저장
            boolean moved = false; // 이동했는지 여부
            for(int i=0; i<4; i++) {
                int newr = shark[0] + dr[priorities[shark[2]][shark[3]][i]]; // 우선순위에 따라 새로운 좌표 계산
                int newc = shark[1] + dc[priorities[shark[2]][shark[3]][i]];
                if(newr<0 || newr>=n || newc<0 || newc>=n) { // 격자 벗어나면 건너뜀
                    continue;
                }
                if(smells[newr][newc][1] > 0) { // 냄새가 있으면 건너뜀
                    if(mine < 0 && smells[newr][newc][0] == shark[2]) { // 혹시 빈칸이 없을때 사용할 나의 냄새가 있는 방향 저장
                        mine = i;
                    }
                    continue;
                }
                moved = true; // 이동 true
                if(newBoard[newr][newc] > 0) { // 이미 나보다 작은 번호의 상어가 있으면 현재 상어를 죽임
                    shark[0] = -1;
                }else {
                    newBoard[newr][newc] = shark[2]; // 새로운 격자에 상어 위치시키기
                    sharks[shark[2]] = new int[]{newr, newc, shark[2], priorities[shark[2]][shark[3]][i]}; // 상어 정보 갱신
                }
                break;
            }
            if(!moved) { // 빈 칸이 없어서 못 움직였으면 mine을 사용해서 옮김
                int newr = shark[0] + dr[priorities[shark[2]][shark[3]][mine]];
                int newc = shark[1] + dc[priorities[shark[2]][shark[3]][mine]];
                if(newBoard[newr][newc] > 0) {
                    shark[0] = -1;
                }else {
                    newBoard[newr][newc] = shark[2];
                    sharks[shark[2]] = new int[]{newr, newc, shark[2], priorities[shark[2]][shark[3]][mine]};
                }
            }
        }

        board = newBoard; // 격자 변경
    }

    static int countSharks() {
        int count = 0;
        for (int i = 1; i <= m; i++) {
            if(sharks[i][0] >= 0) { // 살아있는 상어 카운트
                count++;
            }
        }
        return count;
    }
}
```

# **🔑Description**

> 상어가 우선순위 방향에 따라 빈 칸 혹은 나의 냄새가 남아있는 곳으로 이동하는 줄 알았는데\
> 빈 칸들 먼저 탐색하고 빈 칸이 없을 때 나의 냄새가 남아있는 곳으로 갈 수 있었다.\
> 일단 수정해서 맞은 다음에 코드 다시 작성했다.

# **📑Related Issues**

>

# **🕛Resource**

| Memory    | Time    |
| --------- | ------- |
| 21648`KB` | 188`ms` |
