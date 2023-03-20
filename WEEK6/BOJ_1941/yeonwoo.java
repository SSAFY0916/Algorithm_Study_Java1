import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;

public class Main {
    static char[][] map = new char[5][5];
    static int[] dr = new int[]{-1,0,1,0};
    static int[] dc = new int[]{0,-1,0,1};
    static int[] combR = new int[25];
    static int[] combC = new int[25];

    static int ans = 0;

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) throws IOException {
        for(int i=0;i<5;i++){
            map[i] = br.readLine().toCharArray();
        }

        //좌표 미리 계산
        for(int i=0;i<25;i++){
            combR[i] = i/5;
            combC[i] = i%5;
        }

        combination(new int[7],0,0,7);
        System.out.println(ans);
    }


    static void combination(int[] comb, int idx, int depth, int left){
        if(left==0){
            bfs(comb);
            return;
        }

        if(depth==25) return;
        comb[idx]=depth;
        combination(comb,idx+1,depth+1,left-1);
        combination(comb,idx,depth+1,left);
    }

    static void bfs(int[] comb){
        Queue<Integer> q = new ArrayDeque<>();
        boolean[] visited = new boolean[7];

        visited[0] = true;
        q.offer(comb[0]);
        int cnt = 1;
        int sCnt = 0;//솜파

        while(!q.isEmpty()){
            int cur = q.poll();
            if(map[combR[cur]][combC[cur]]=='S') sCnt++;
            for(int i=0;i<4;i++){
                for(int next=1;next<7;next++){
                    if(visited[next]) continue;
                    if(combR[comb[next]]==combR[cur]+dr[i] && combC[comb[next]]==combC[cur]+dc[i]){
                        visited[next] = true;
                        q.offer(comb[next]);
                        cnt++;
                    }
                }
            }
        }

        if(cnt==7){
            if(sCnt>=4){
                ans++;
            }
        }

    }
}