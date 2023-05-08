import java.io.*;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * 기본 dfs 응용
 * max depth를 입력 받을 때 신의 문자열 중 최대 길이로 지정한다
 * hashmap을 통해 찾으면 계수해준다
 * dfs 써도 되는지 시간복잡도 판단하는 법 궁금하다
 */
public class Main{
    static HashMap<String, Integer> map = new HashMap<>();
    static int N,M,K;
    static int MAX_LENGTH;//최대 깊이

    //8방탐색
    static int[] dx = {-1,-1,0,1,1,1,0,-1};
    static int[] dy = {0,1,1,1,0,-1,-1,-1};

    static char[][] hell;//지도

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());//행
        M = Integer.parseInt(st.nextToken());//열
        K = Integer.parseInt(st.nextToken());//신이 좋아하는 문자열의 수
        //신의 key의 입력순서를 기억하기 위함
        String[] stringArray = new String[K];

        hell = new char[N][M];
        map = new HashMap<>();
        //최대 depth 저장용
        MAX_LENGTH = 0;

        //맵 세팅
        for(int i=0;i<N;i++){
            hell[i] = br.readLine().toCharArray();
        }

        for(int i=0;i<K;i++){
            String favoriteString = br.readLine();//신의 key
            MAX_LENGTH = Math.max(MAX_LENGTH, favoriteString.length());//최대뎁스
            map.put(favoriteString,0);
            stringArray[i] = favoriteString;
        }

        //각 좌표에서 실행해보기
        for(int i=0;i<N;i++){
            for(int j=0;j<M;j++){
                //기본으로 현위치의 문자 1개 들어있으니 기본 depth는 1임
                dfs(i,j,1,Character.toString(hell[i][j]));
            }
        }

        StringBuilder sb = new StringBuilder();

        for(String key:stringArray){
            sb.append(map.get(key)).append("\n");
        }

        System.out.println(sb);
    }

    private static void dfs(int x, int y, int depth,String result){
        if(map.containsKey(result)){
            //찾았다

            map.put(result, map.get(result)+1);
            //찾았다고 끝내지는 않는다. aa와 aac가 신의 문자일 수 있기때문
        }

        //최장길이만큼 문자열 넣었다
        //더이상 탐색 무의미
        if(depth==MAX_LENGTH) return;

        for(int dir=0;dir<8;dir++){
            int nx = x +dx[dir];
            int ny = y + dy[dir];

            //x축 환형보정
            if(nx<0){
                //위로초과

                nx = N-1;
            }else if(nx>=N){
                //아래로 초과

                nx=0;
            }

            //y축 환형보정
            if(ny<0){
                //좌로 초과

                ny = M-1;
            }else if(ny>=M){
                //우로 초과

                ny = 0;
            }

            dfs(nx,ny,depth+1,result+hell[nx][ny]);
        }
    }

}