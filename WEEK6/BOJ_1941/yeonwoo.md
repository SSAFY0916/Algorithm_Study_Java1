![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%20N&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 1941 소문난 칠공주](https://www.acmicpc.net/problem/1941)

<br>
<br>

# **Code**

```java
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
```

<br>
<br>

# **🔑Description**
- 이차원 배열의 좌표값을 1차원 배열에 순서대로(행끝까지가면 다음행의 순) 배치하였습니다

- 7공주를 뽑았습니다

- BFS를 이용하여 7공주간 인접했는지 확인하였습니다

  

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
| 73328KB | 444ms |                     |
