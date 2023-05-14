import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    static int[][] field;
    static boolean[][] visited;

    static int[][] cows;

    static int N, K, R;

    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};

    static HashMap<Pair, Pair> map;

    static int ret;

    public static void main(String[] args) throws Exception {

        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());

        field = new int[N][N];
        visited = new boolean[N][N];

        cows = new int[K][2];

        map = new HashMap<>();

        for (int i = 0; i < R; i++) {
            st = new StringTokenizer(br.readLine());

            int r1 = Integer.parseInt(st.nextToken()) - 1;
            int c1 = Integer.parseInt(st.nextToken()) - 1;
            int r2 = Integer.parseInt(st.nextToken()) - 1;
            int c2 = Integer.parseInt(st.nextToken()) - 1;

            int dirx = r2 - r1;
            int diry = c2 - c1;

            if (diry == 1) {    // 맵에 다리 추가 - 양방향이라 2개 추가
                map.put(new Pair(r1, c1, 0), new Pair(r2, c2, 2));
                map.put(new Pair(r2, c2, 2), new Pair(r1, c1, 0));
            } else if (diry == -1) {
                map.put(new Pair(r1, c1, 2), new Pair(r2, c2, 0));
                map.put(new Pair(r2, c2, 0), new Pair(r1, c1, 2));
            } else if (dirx == -1) {
                map.put(new Pair(r1, c1, 3), new Pair(r2, c2, 1));
                map.put(new Pair(r2, c2, 1), new Pair(r1, c1, 3));
            } else if (dirx == 1) {
                map.put(new Pair(r1, c1, 1), new Pair(r2, c2, 3));
                map.put(new Pair(r2, c2, 3), new Pair(r1, c1, 1));
            }


        }

        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            cows[i][0] = Integer.parseInt(st.nextToken()) - 1;
            cows[i][1] = Integer.parseInt(st.nextToken()) - 1;
        }

        combination(0, 0, 0, new int[]{0, 0});

        System.out.println(ret);


        bw.flush();
        bw.close();
        br.close();
    }

    static void initVisited() {
        for (int i = 0; i < N; i++) {
            Arrays.fill(visited[i], false);
        }
    }

    static void combination(int count, int next, int flag, int[] selected) {        // 소 2마리 뽑기
        if (count == 2) {   // 2마리 뽑았으면 bfs 돌리기

            boolean temp = bfs(selected[0], selected[1]);
            
            if (!temp)
                ret++;
            return;
        }

        for (int i = next; i < K; i++) {
            if ((flag & 1 << i) != 0) {
                continue;
            }

            selected[count] = i;
            combination(count + 1, i + 1, flag | 1 << i, selected);
        }
    }

    static boolean bfs(int start, int end) {
        initVisited();
        Queue<Pair> queue = new ArrayDeque<>();
        queue.offer(new Pair(cows[start][0], cows[start][1]));
        visited[cows[start][0]][cows[start][1]] = true;

        while (!queue.isEmpty()) {
            int x = queue.peek().x;
            int y = queue.poll().y;

            if (x == cows[end][0] && y == cows[end][1])
                return true;

            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];

                if (nx < 0 || nx >= N || ny < 0 || ny >= N || visited[nx][ny]) {    // 범위 벗어나거나
                    continue;
                }

                if (new Pair(nx, ny, (i + 2) % 4).equals(map.get(new Pair(x, y, i)))) { // 다리 있으면 못지나감
//                    System.out.println("다리");
                    continue;
                }

//                System.out.println("다리x");

                queue.offer(new Pair(nx, ny));
                visited[nx][ny] = true;
            }

        }


        return false;
    }

    static class Pair {
        int x, y, d;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Pair(int x, int y, int d) {
            this.x = x;
            this.y = y;
            this.d = d;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair pair = (Pair) o;
            return x == pair.x && y == pair.y && d == pair.d;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, d);
        }

        @Override
        public String toString() {
            return "Pair{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }
}
