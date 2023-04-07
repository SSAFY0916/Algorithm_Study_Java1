![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%209328&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 9328 열쇠](https://www.acmicpc.net/problem/9328)

<br>
<br>

# **Code**

```java
import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    static int[] dr = {-1,0,1,0};
    static int[] dc = {0,-1,0,1};

    static char[][] map;//기지
    static boolean[] key;//알파벳 열쇠 26개
    //알파벳별 문위치
    static ArrayList<int[]>[] gates;//열지 못한 문

    static int T;//테케 수
    static int h,w;//맵의 높이, 너비

    public static void main(String[] args) throws IOException{
//        System.out.println((int)'a');
//        System.out.println((int)'z');
        T = Integer.parseInt(br.readLine());
        for(int tc=1;tc<=T;tc++){
            cal();
        }
        bw.flush();
        bw.close();
    }
    static void cal() throws IOException{
        key = new boolean[26];//열쇠 초기화

        //문 초기화
        gates = new ArrayList[26];
        for(int i=0;i<26;i++){
            gates[i] = new ArrayList<>();//
        }


        //높이너비
        StringTokenizer st = new StringTokenizer(br.readLine());
        h = Integer.parseInt(st.nextToken());
        w = Integer.parseInt(st.nextToken());

        //맵셋팅
        map = new char[h+2][w+2];//상하좌우 마진(기지 외부 표현)
        //테두리 빈칸으로 만드는용
        for(int i=0;i<map.length;i++){
            for(int j=0;j<map[0].length;j++){
                map[i][j] = '.';
            }
        }
        //사무실내부
        for(int i=1;i<=h;i++){
            char[] line = br.readLine().toCharArray();
            for(int j=1;j<=w;j++){
                map[i][j] = line[j-1];
            }
        }

        //보유중인열쇠
        String haveKey = br.readLine();
        if(!haveKey.equals("0")){//0이면 없는것
            char[] keyAlreadyHave = haveKey.toCharArray();

            for(int i=0;i<keyAlreadyHave.length;i++){
                key[keyAlreadyHave[i]-'a'] = true;
            }
        }


        //bfs
        int res = bfs();
        bw.write(Integer.toString(res)+"\n");
    }
    static int bfs(){
        int res = 0;
        //기본 BFS재료
        Queue<int[]> q = new ArrayDeque<>();
        boolean[][] visited = new boolean[h+2][w+2];
        q.offer(new int[]{0,0});//사무실 바깥 0,0에서 시작
        visited[0][0] = true;

        while(!q.isEmpty()){
            int[] cur = q.poll();
            int r = cur[0];
            int c = cur[1];

            //4방탐색
            for(int i=0;i<4;i++){
                int nr = r+dr[i];
                int nc = c+dc[i];

                if(nr<0||nr>=h+2||nc<0||nc>=w+2) continue;//범위초과
                if(visited[nr][nc]) continue;//방문초과
                if(map[nr][nc]=='*') continue;//벽 초과

                int elem = map[nr][nc];//현재값

                if(elem-'A'>=0 && elem-'A'<=25){
                    //문발견
                    if(key[elem-'A']){//열쇠있다
                        map[nr][nc] = '.';//빈공간화
                        visited[nr][nc] = true;
                        q.offer(new int[]{nr,nc});
                    }else{//열쇠없다
                        gates[elem-'A'].add(new int[]{nr,nc});//이알파벳 공간에 넣어둠
                    }
                }else if(elem-'a'>=0 && elem-'a'<=25){
                    //열쇠발견
                    key[elem-'a'] = true;//열쇠획득처리
                    visited[nr][nc]=true;//방문처리
                    q.offer(new int[] {nr,nc});
                    for(int[] posi:gates[elem-'a']){//찾은열쇠가 막고있는곳 오픈
                        int openR = posi[0];
                        int openC = posi[1];
                        map[openR][openC] = '.';
                        visited[openR][openC]=true;
                        q.offer(new int[]{openR,openC});
                    }
                }else if(elem=='$'){
                    //문서발견
                    res++;
                    visited[nr][nc] = true;
                    q.offer(new int[]{nr,nc});
                }else{
                    //빈공간
                    visited[nr][nc] = true;
                    q.offer(new int[]{nr,nc});
                }

            }
        }
        return res;
    }
}
```

<br>
<br>

# **🔑Description**
- ```
  열쇠처리하는 방법
  
  boolean[] key로 보유중인 열쇠 체크합니다
  
  ArrayList<int[]> odds로 가능성을 저장합니다
  
  bfs돌때
  
  대문자만나면
  
  열쇠있으면 q에 offer
  
  열쇠없으면 odds[알파벳idx]에 해당 좌표를 add해두비다
  
  소문자만나면
  
  key[알파벳] = true로 바꾸고
  
  odds[알파벳] 순회하면서 q에 offer
  ```
  
  

<br>
<br>

# **📑Related Issues**

> 

<br>
<br>

# **🕛Resource**

| Memory  | Time  | Implementation Time |
| ------- | ----- | ------------------- |
| 21264KB | 252ms |                     |



