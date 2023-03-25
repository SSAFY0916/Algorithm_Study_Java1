import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    static int N;

    static int[][] bamboo, depth; // 대나무, 깊이 depth
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};


    public static void main(String[] args) throws Exception {

        N = Integer.parseInt(br.readLine());

        bamboo = new int[N][N];
        depth = new int[N][N];

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < N; j++) {
                bamboo[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (depth[i][j] == 0)  // depth 값이 없는 경우만 dfs로 계산
                    dfs(i, j);
            }
        }

        int max = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                max = Math.max(depth[i][j], max);
            }
        }


        bw.write(Integer.toString(max));
        bw.close();
    }

    static int dfs(int x, int y) {
        if (depth[x][y] != 0) {    // 이미 저장된 depth값이 있는 경우 그대로 리턴
            return depth[x][y];
        }

        boolean flag = false;
        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            if (nx < 0 || nx >= N || ny < 0 || ny >= N || bamboo[x][y] >= bamboo[nx][ny])
                continue;

            flag = true;

            // 그 위치로부터 나오는 트리의 depth 중 가장 큰 값을 저장
            depth[x][y] = Math.max(depth[x][y], dfs(nx, ny) + 1);
        }

        if (!flag) {    // 넘어갈 수 있는 칸이 없는 경우 - 리프 노드
            return depth[x][y] = 1;
        }

        return depth[x][y];
    }

}
