import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    //이차원맵
    static int N;
    static char[][] map;
    //상좌하우
    static int[] dr = {-1,0,1,0};
    static int[] dc = {0,1,0,-1};

    static int doorCnt = 0;//2개를 찾아라
    static Door[] doorArr = new Door[2];
    static class Door{
        int r,c;
        public Door(int r,int c){
            this.r = r;
            this.c = c;
        }
    }

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(br.readLine());
        map = new char[N][N];
        for(int i=0;i<N;i++){
            map[i] = br.readLine().toCharArray();
            for(int j=0;doorCnt<2 && j<N;j++){
                if(map[i][j]=='#'){
                    doorArr[doorCnt++] = new Door(i,j);
                }
            }
        }


        int res = bfs();
        System.out.println(res);
    }

    private static int bfs() {
        //기본 bfs 재료
        Door sDoor = doorArr[0];//시작
        Door eDoor = doorArr[1];//끝
        if(sDoor==null || eDoor==null){
            throw new RuntimeException("sdfsdf");
        }
        Queue<Door> q = new ArrayDeque<>();//큐
        boolean[][] visited = new boolean[N][N];
        q.offer(sDoor);
        visited[sDoor.r][sDoor.c] = true;

        int turn = -1;
        while (!q.isEmpty()) {
            turn +=1;
            int size = q.size();
            for (int i = 0; i < size; i++) {
                Door cur = q.poll();
                int r = cur.r;
                int c = cur.c;

                //4방탐색
                for (int d=0;d<4;d++) {
                    int nr = r + dr[d];
                    int nc = c + dc[d];

                    //빛은 끝까지 간다
                    while (true) {
                         if(nr<0||nr>=N||nc<0||nc>=N) break;//범위초과

                        //값분기
                        char val = map[nr][nc];
                         if(val=='*') break;//벽초과
                        if(val=='!'){
                            //거울조우
                            if(visited[nr][nc]) break;//방문초과

                            visited[nr][nc] = true;
                            q.offer(new Door(nr,nc));
                        }else if(nr==eDoor.r && nc==eDoor.c){//끝문도달
                            return turn;
                        }
                        //빛은 끝까지 간다
                        nr+=dr[d];
                        nc+=dc[d];

                    }
                }
            }
        }

        return -1;
    }

}