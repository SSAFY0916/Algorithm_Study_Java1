package daily.y_2023.m_03.d_27.bj_1525;
import java.io.*;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;
import java.util.HashMap;
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int[] dr = {0,1,0,-1};
    static int[] dc = {1,0,-1,0};
    static String correct = "123456780";
    static HashMap<String,Integer> map = new HashMap<>();
    static StringTokenizer st;
    public static void main(String[] args) throws IOException{
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<3;i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<3;j++){
                sb.append(st.nextToken());
            }
        }
        String start = sb.toString();
        map.put(start,0);
        System.out.println(bfs(start));
    }

    static int bfs(String start){
        Queue<String> q= new ArrayDeque<>();
        q.offer(start);
        while(!q.isEmpty()){
            String cur = q.poll();
            int move = map.get(cur);//이동수
            int empty = cur.indexOf('0');//빈칸위치
            int r = empty/3;
            int c = empty%3;
            
            if(cur.equals(correct)){
                return move;
            }
            
            for(int i=0;i<4;i++){
                int nr = r+dr[i];
                int nc = c+dc[i];
                if(nr<0 || nr>=3 || nc<0 ||nc>=3) continue;//범위초과

                int nextPosition = nr*3+nc;//nrnc의 위치를 1차원화
                char ch = cur.charAt(nextPosition);//0과 바꿀 수

                String next = cur.replace(ch,'t');
                next = next.replace('0',ch);
                next = next.replace('t','0');

                if(!map.containsKey(next)) {//비방문
                    q.add(next);
                    map.put(next, move + 1);
                }

            }
        }
        return -1;
    }
}
