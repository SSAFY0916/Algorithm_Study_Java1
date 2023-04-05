import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class Main {

    static BufferedReader br;
    static BufferedWriter bw;
    static StringTokenizer st;
    static int n, m, answer; // 행, 열, 점수의 최대값
    static int[][] board; // 격자에 있는 블록들의 종류를 저장하는 배열 => -2는 빈 칸
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, -1, 0, 1};

    static class Pair implements Comparable<Pair> { // 좌표를 저장하는 클래스
        int x, y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(Pair pair) { // 좌표 비교를 위한 오버라이딩, 파라미터로 받은 pair보다 행이 작거나, 행은 같은데 열이 작을 때 -1 리턴
            return (this.x == pair.x) ? Integer.compare(this.y, pair.y) : Integer.compare(this.x, pair.x);
        }
    }
    static class BlockGroup implements Comparable<BlockGroup> { // 블럭그룹을 저장하는 배열
        List<Pair> locations; // 블럭그룹의 블럭들의 좌표
        int countRainbow; // 무지개 블럭의 수
        Pair pair; // 기준블럭의 좌표

        public BlockGroup(int x, int y) {
            this.locations = new ArrayList<>();
            this.countRainbow = 0;
            this.pair = new Pair(x, y);
        }

        @Override
        public int compareTo(BlockGroup blockGroup) { // 블럭 개수, 무지개 블럭 수, 좌표 순서로 비교
            if (this.locations.size() == blockGroup.locations.size()) {
                if(this.countRainbow == blockGroup.countRainbow) {
                    return this.pair.compareTo(blockGroup.pair);
                }
                return Integer.compare(this.countRainbow, blockGroup.countRainbow);
            }
            return Integer.compare(this.locations.size(), blockGroup.locations.size());
        }
    }

    public static void main(String[] args) throws Exception{
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        init();

        solve();

        bw.write(answer + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    // 입력을 처리하는 메소드
    static void init() throws Exception {
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        board = new int[n][n];
        for(int i=0; i<n; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<n; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }

    static void solve() {
        while(true) {
            BlockGroup maxBlockGroup = findMaxBlockGroup();
            if(maxBlockGroup.locations.size() < 2) { // 가장 큰 블럭그룹의 크기가 2보다 작으면 그만
                break;
            }
            increaseScore(maxBlockGroup);
            removeMaxBlockGroup(maxBlockGroup);
            gravity();
            rotate();
            gravity();
        }
    }

    // 격자의 블럭들에서 가장 큰 블럭그룹을 리턴
    static BlockGroup findMaxBlockGroup() {
        boolean[][] visited = new boolean[n][n]; // 이미 블럭그룹을 찾은 블럭에 대해서 반복을 피하기 위한 방문여부 저장 배열
        BlockGroup maxBlockGroup = new BlockGroup(-1, -1); // 리턴 해줄 블럭그룹 초기화
        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {
                if(visited[i][j] || board[i][j] <= 0) { // 이미 방문했거나 검은 블럭이면 건너뜀
                    continue;
                }
                // 이번 bfs에서 반복을 피하기 위해 사용하는 방문 여부 저장 배열
                // visited만 운영하면 무지개 블럭을 여러 그룹에 넣을 수 없어서 두 개의 배열 사용
                boolean[][] visited2 = new boolean[n][n];
                Queue<Pair> q = new ArrayDeque<>();
                BlockGroup blockGroup = new BlockGroup(n, n); // 이번 bfs에서 만들 블럭그룹 초기화
                q.add(new Pair(i, j));
                while(!q.isEmpty()) { // bfs
                    Pair pair = q.poll();
                    if(visited2[pair.x][pair.y]) { // 이번 bfs에서 이미 방문한 블럭이면 건너뜀
                        continue;
                    }
                    blockGroup.locations.add(pair); // 블럭그룹에 넣기
                    if(board[pair.x][pair.y] == 0) {
                        blockGroup.countRainbow++; // 블럭그룹 무지개 블럭 수 증가
                    }else {
                        if(blockGroup.pair.compareTo(pair) > 0) {
                            blockGroup.pair = pair; // 블럭그룹의 기준블럭 변경
                        }
                        visited[pair.x][pair.y] = true; // 이미 블럭그룹에 사용된 일반 블럭이므로 방문 처리하고 이 블럭으로 블럭그룹 만들기를 시도하지 않는다
                    }
                    visited2[pair.x][pair.y] = true; // 이번 bfs에서의 방문 처리
                    for(int k=0; k<4; k++) {
                        int newx = pair.x + dr[k];
                        int newy = pair.y + dc[k];
                        if(newx<0 || newx>=n || newy<0 || newy>=n)
                            continue;
                        if (board[newx][newy] == board[i][j] || board[newx][newy] == 0) { // 같은색 블럭과 무지개 블럭만 큐에 넣음
                            q.add(new Pair(newx, newy));
                        }

                    }
                }
                if(blockGroup.locations.size() < 2) { // 크기가 2보다 작으면 블럭그룹이 아니다
                    continue;
                }
                if(maxBlockGroup.compareTo(blockGroup) < 0) { // 가장 큰 블럭그룹과 비교
                    maxBlockGroup = blockGroup;
                }
            }
        }
        return maxBlockGroup;
    }

    // 블럭그룹을 입력받아 블럭그룹의 블럭 개수의 제곱만큼 점수를 증가시킨다
    static void increaseScore(BlockGroup blockGroup) {
        answer += blockGroup.locations.size() * blockGroup.locations.size();
    }

    // 블럭그룹을 입력받아 블럭들을 제거한다
    static void removeMaxBlockGroup(BlockGroup blockGroup) {
        for(Pair pair : blockGroup.locations) {
            board[pair.x][pair.y] = -2; // -2로 변경
        }
    }

    // 검은색 블럭을 제외하고 다른 블럭이나 격자 끝까지 블럭들을 끌어내림
    static void gravity() {
        for (int j = 0; j < n; j++) { // 왼쪽에서 오른쪽으로
            int index = n-1; // 다음에 블럭을 끌어놓을 위치
            for (int i = n - 1; i >= 0; i--) { // 아래에서부터 위로
                if(board[i][j] == -2) { // 빈 칸이면 건너뜀
                    continue;
                }
                if(board[i][j] == -1) { // 검정 블럭이면 검정 블럭 위로 끌어내려지게 현재 위치보다 한 칸위로 index 변경
                    index = i-1;
                    continue;
                }
                if(index == i) { // 끌어내려봤자 지금 위치면 건너뜀
                    index--; // 다음 블럭은 이 블럭 위로
                    continue;
                }
                board[index--][j] = board[i][j]; // 끌어내림
                board[i][j] = -2; // 원래 블럭 위치는 빈 칸으로
            }
        }
    }

    // 격자를 시계 반대 방향으로 90도 회전시킴
    static void rotate() {
        int[][] newBoard = new int[n][n]; // 새로운 격자 배열 생성
        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {
                newBoard[n-1-j][i] = board[i][j];
            }
        }
        board = newBoard; // 기존의 격자를 새로운 격자로 변경
    }
}
