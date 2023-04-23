![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2013460&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 13460 구슬탈출2](https://www.acmicpc.net/problem/13460)

<br>
<br>

# **Code**

```java
import java.io.*;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main{
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int R,C;
    static char[][] map;
    static int[] dr = {-1,0,1,0};
    static int[] dc = {0,-1,0,1};

    /* Ball
        볼의 정보(위치, 색깔)
        볼의 이동을 담당
     */

    /* BallPair
        볼 조합 관리
        이동방향에 따른 볼 순서 정립
     */
    static class Ball{
        int r,c;//위치
        boolean isRed;//빨간공인지
        public Ball(int r,int c,boolean isRed){
            this.r = r;
            this.c = c;
            this.isRed = isRed;
        }
        //d방향으로 이동
        public Ball move(int d){
            for(int i=1; ; i++){//조건식 생략 == 무조건 true
                int nr = r + dr[d]*i;
                int nc = c + dc[d]*i;

                if(map[nr][nc]=='O'){
                    //구멍에 도달하면 리턴

                    return new Ball(nr,nc,isRed);
                }else if(map[nr][nc]=='#'){
                    //벽에 도달했으면 한칸 전으로 가서 리턴

                    Ball newBall = new Ball(nr-dr[d],nc-dc[d],isRed);

                    //다른공이 오지 못하도록 임시 벽 설치(해제는 외부에서 해줌)
                    map[newBall.r][newBall.c] = '#';
                    return newBall;
                }
            }
        }
    }

    static class BallPair{
        Ball red, blue;//어느 공이 빨강인지
        Ball[] ordered = new Ball[2];//방향에 따른 이동 순서

        //생성자 --
        //oredered는 여기서 안 하고 이동할 때마다 reOrder 메소드로 지정
        public BallPair(Ball red, Ball blue){
            this.red = red;
            this.blue = blue;
        }

        //이동할 방향에 따른
        public void reOrder(int d){
            if(d==0){
                //위로 이동

                ordered[0] = red.r<blue.r?red:blue;//r이 작은애가 0번요소
            }else if(d==1){
                //좌로이동

                ordered[0] = red.c<blue.c?red:blue;//c가 작은애가 0번요소
            }else if(d==2){
                //하로이동

                ordered[0] = red.r>blue.r?red:blue;//r이 큰 애가 0번요소
            }else if(d==3){
                //우로이동

                ordered[0] = red.c>blue.c?red:blue;//c가 큰 애가 우선 요소
            }

            ordered[1] = ordered[0]==red? blue: red;//1번요소는 0번요소의 반대
        }
    }

    public static void main(String[] args) throws IOException{
        st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        map = new char[R][C];

        Ball red = null;
        Ball blue = null;//초기빨강 파랑
        //맵세팅
        for(int r=0;r<R;r++){
            String row = br.readLine();
            for(int c=0;c<C;c++){
                map[r][c] = row.charAt(c);

                if(map[r][c]=='R'){
                    //빨강

                    red = new Ball(r,c,true);
                }else if(map[r][c]=='B'){
                    //파랑

                    blue = new Ball(r,c,false);
                }
            }
        }
        int res = bfs(red,blue);
        System.out.println(res);
    }

    static int bfs(Ball red, Ball blue){
        Queue<BallPair> q = new ArrayDeque<>();
        boolean[][][][] visited = new boolean[R][C][R][C];

        q.offer(new BallPair(red,blue));
        visited[red.r][red.c][blue.r][blue.c] = true;

        int turn = 0;
        int size;
        while(!q.isEmpty()){
            size = q.size();
            turn++;
            if(turn==11) return -1;
            while(size-->0){
                BallPair cur = q.poll();

                //사방탐색
                for(int i=0;i<4;i++){
                    //방향에 따른 선이동 공 지정
                    cur.reOrder(i);
                    //선공 이동 결과
                    Ball front = cur.ordered[0].move(i);
                    //후공 이동 결과
                    Ball back = cur.ordered[1].move(i);


                    //공 이동 벽 제거
                    if(map[front.r][front.c]=='#'){
                        map[front.r][front.c]='.';
                    }
                    if(map[back.r][back.c]=='#'){
                        map[back.r][back.c]='.';
                    }

                    //선공 후공중 뭐가 빨공 파공인지 확인
                    Ball redOne = front.isRed?front:back;//선공이 빨공이면 선공
                    Ball blueOne = redOne==back?front:back;//빨공이 후공이면 선공

                    //####공 이동 결과에 대한 판단 시작####
                    if(map[blueOne.r][blueOne.c]=='O'){
                        //파공빠짐
                        continue;
                    }else if(map[redOne.r][redOne.c]=='O'){
                        //빨공만 빠짐

                        return turn;
                    }else{
                        //둘 다 안 빠짐

                        // 방문초과
                        if(visited[redOne.r][redOne.c][blueOne.r][blueOne.c]) continue;
                        //방문체크
                        visited[redOne.r][redOne.c][blueOne.r][blueOne.c] = true;
                        q.offer(new BallPair(redOne,blueOne));
                    }
                    //####공 이동 결과에 대한 판단 종료####

                }
            }
        }
        return -1;
    }
}
```

<br>
<br>

# **🔑Description**
- 

- ```
  /* Ball class
  볼 위치, 색
  이동
      어짜피 벽으로 둘러쌓여있으니까 범위초과 체크 안 함
      이동이 끝나면 도착자리에 임시 벽 세워둠(다른 공과 겹치지 않기 위함)
          이 벽은 클래스 외부에서 이동을 마치고 제거한다
   */
  
  /*BallPair class
      기본적인 역할은 BFS용 Queue에 두 볼의 좌표를 담기 위함
      (일반 bfs에서 int[2] 넣듯이)
  
      reorder()
      두 공을 move()할 때,
          이동 방향에 따라 선공, 후공을 정하는 역할 수행
   */
  
  /* BFS
  등장하는 개념 총 2개
  1. 선공 후공 -- 움직이는 순서에 따른 공 분류
  2. 빨공 파공 -- 색깔에 따른 공 분류
  3. BallPair -- bfs에서 정점의 좌표 담는 int[2]의 역할 수행
              -- 4방탐색시에 선공, 후공 고르는 역할 수행
  
  q에 BallPair(시작빨공,시작파공) 넣기
  사차원 boolean배열로 visit 체크
  
  
  ##4방탐색 시작##
  이동방향에 따라 선공 후공을 정한다
  [선공 -> 후공] 순서대로 move()
  선공 = 선공.move()
  후공 = 후공.move()
  
  각 공의 위치에 존재하는 가벽 제거
  (제거하기 전에 가벽이 있는지 확인해야함)
  (왜냐면 구멍일 수도 있어서임)
  
  
  
  선공 후공을 빨공 파공으로 분류한다(Ball 객체에 isRed 있음)
  빠진 공이 있다면 승리, 패배를 분류한다
      승리시 리턴
      패배시 continue
  
  
  
  빠진 공이 없다면 방문초과여부 체크하고 큐에 담는다
  - 방문체크는 두 공의 R,C 좌표를 담는 4차원 boolean 배열 사용
  - 큐에 담는건 BallPair에 Ball객체 두개 담아서 사용
  ##4방탐색 종료##
   */
  ```

<br>
<br>

# **📑Related Issues**

> 
>
> 

<br>
<br>

# **🕛Resource**

| Memory  | Time  | Implementation Time |
| ------- | ----- | ------------------- |
| 14344KB | ms136 |                     |
