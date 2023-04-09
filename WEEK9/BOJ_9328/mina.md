![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:FF658D,100:FFCB32&text=BOJ%209328&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 9328 열쇠](https://www.acmicpc.net/problem/9328)

<br>
<br>

# **💻Code**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    static int N, M;
    static char[][] office;

    static boolean[][] visited;
    static boolean[] key;

    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};


    public static void main(String[] args) throws Exception {

        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        for (int t = 0; t < T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            key = new boolean[26];  // 키 획득 여부 저장

            List<Pair> entrance = new ArrayList<>();    // 입구 모음

            office = new char[N][M];    // 평면도
            visited = new boolean[N][M];    // 방문 check

            for (int i = 0; i < N; i++) {
                office[i] = br.readLine().toCharArray();
                for (int j = 0; j < M; j++) {
                    if ((i == 0 || j == 0 || i == N - 1 || j == M - 1) && office[i][j] != '*') {    // 빌딩 입구로 쓸 수 있는 곳
                        entrance.add(new Pair(i, j));
                    }
                }
            }
            String input = br.readLine();
            if (!input.equals("0")) {   // 처음 갖고 있는 키
                for (int i = 0; i < input.length(); i++) {
                    key[input.charAt(i)-'a'] = true;
                }
            }

            sb.append(bfs(entrance)).append("\n");
        }
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    static void initVisited() {
        for (int i = 0; i < N; i++) {
            Arrays.fill(visited[i], false);
        }
    }

    static int bfs(List<Pair> entrance) {   // bfs 탐색

        int result = 0; // 훔친 문서 개수
        boolean flag = true;

        while (flag) {  // 열쇠를 새로 주웠을 때 열 수 있는 문이 있는지 확인하기 위하여 입구마다 bfs탐색 -> 새로 주운 열쇠가 없는 경우 break
            flag = false;

            for (Pair etr : entrance) { // 입구마다 bfs 탐색
                Queue<Pair> queue = new ArrayDeque<>();
                initVisited();

                if (office[etr.x][etr.y] == '$') {  // 입구가 서류
                    office[etr.x][etr.y] = '.'; // 서류 줍줍
                    result++;
                } else if (office[etr.x][etr.y] >= 'a' && office[etr.x][etr.y] <= 'z') {    // 입구가 열쇠
                    if (!key[office[etr.x][etr.y] - 'a']) {    // 나한테 없던 열쇠
                        key[office[etr.x][etr.y] - 'a'] = true;
                        flag = true;
                    }
                    office[etr.x][etr.y] = '.'; // 열쇠 줍줍
                } else if (office[etr.x][etr.y] >= 'A' && office[etr.x][etr.y] <= 'Z') {    // 입구가 문
                    if (key[office[etr.x][etr.y] - 'A']) {  // 열쇠 있음
                        office[etr.x][etr.y] = '.'; // 문 열기
                    } else { // 열쇠 없음
                        continue;
                    }
                }

                visited[etr.x][etr.y] = true;
                queue.offer(etr);

                // bfs 탐색
                while (!queue.isEmpty()) {
                    int x = queue.peek().x;
                    int y = queue.poll().y;

                    for (int j = 0; j < 4; j++) {
                        int nx = x + dx[j];
                        int ny = y + dy[j];

                        if (nx < 0 || nx >= N || ny < 0 || ny >= M || office[nx][ny] == '*' || visited[nx][ny])
                            continue;

                        if (office[nx][ny] == '$') {    // 서류 줍줍
                            office[nx][ny] = '.';
                            result++;
                        } else if (office[nx][ny] >= 'a' && office[nx][ny] <= 'z') {    // 열쇠
                            if (!key[office[nx][ny] - 'a']) {    // 나한테 없던 열쇠
                                key[office[nx][ny] - 'a'] = true;
                                flag = true;
                            }
                            office[nx][ny] = '.';   // 열쇠 줍줍
                        } else if (office[nx][ny] >= 'A' && office[nx][ny] <= 'Z') {    // 문
                            if (key[office[nx][ny] - 'A']) {    // 열쇠 있음
                                office[nx][ny] = '.';   // 문 열기
                            } else {    // 열쇠 없음
                                continue;
                            }
                        }

                        visited[nx][ny] = true;
                        queue.offer(new Pair(nx, ny));

                    }
                }
            }
        }
        return result;
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

> 빌딩 테두리에서 벽이 아닌 부분에서 들어올 수 있다길래 테두리에서 벽이 아닌 위치들을 전부 입구로 생각하고 리스트에 넣었다.\
> 리스트에 있는 모든 입구에서 bfs탐색을 하는데 탐색 중 나한테 없던 열쇠를 주우면 다시 모든 입구에서 bfs탐색을 하도록 했다.\
> 왜냐하면 열쇠 새로 주우면 열 수 있는 문이 있을 수도 있으니까...!\
> 그래서 이 일련의 행위들을 새로 주운 열쇠가 없을 때까지 반복하였다.

<br>
<br>

# **📑Related Issues**

> 문제를 읽었을 때 어떻게 풀어야할지 감이 잡히지 않아서 일단 생각나는 방법을 먼저 구현했다.\
> 심지어 이 방법 시간복잡도 계산하기 머리아파서 일단 냅다 구현함 ㅎㅁㅎ...\
> 최단거리 구하는 것도 아니고 들어올 수 있는 입구도 여러개라 새로운 열쇠 주우면 입구로 돌려보내서 다시 탐색하게 했다.\
> 열쇠 저장할 때도 [달이 차오른다, 가자.](https://www.acmicpc.net/problem/1194) 의 기억땜에 무의식적으로 열쇠를 비트마스킹으로 저장했다.\
> 근데 키의 종류가 26가지라 비트마스킹으론 못할 것 같아서 그냥 boolean 배열에 담아놨다.\
> 사실 시간초과 날 줄 알았는데 어떻게 어떻게 통과는 됐다.\
> 근데 별로 좋지 못한 방법으로 푼 것 같아서 좀 슬프다 (›´-`‹ )

<br>
<br>

# **🕛Resource**

| Memory   | Time  |
| -------- | ----- |
| 297260KB | 968ms |
