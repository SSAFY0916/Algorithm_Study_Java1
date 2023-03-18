![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%20N&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 13424 비밀모임](https://www.acmicpc.net/problem/13424)

<br>
<br>

# **Code**

```java
import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N;
    static int M;
    static int[][] dist;
    public static void main(String[] args) throws Exception{
        int tc= Integer.parseInt(br.readLine());
        for(int t=0;t<tc;t++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            dist = new int[N+1][N+1];
            for(int i=1;i<=N;i++){
                for(int j=1;j<=N;j++){
                    if(i==j)dist[i][j] = 0;
                    else dist[i][j] = 1000000;
                }
            }
            for(int i=0;i<M;i++){
                st = new StringTokenizer(br.readLine());
                int s = Integer.parseInt(st.nextToken());
                int e = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());
                dist[s][e] = c;
                dist[e][s] = c;
            }
            for(int i=1;i<=N;i++){
                for(int j=1;j<=N;j++){
                    for(int k=1;k<=N;k++){
                        dist[j][k] = Math.min(dist[j][k], dist[j][i] + dist[i][k]);
                    }
                }
            }
            int person = Integer.parseInt(br.readLine());
            int personInfo[] = new int[person];
            st = new StringTokenizer(br.readLine());
            for(int i=0;i<person;i++){
                personInfo[i] = Integer.parseInt(st.nextToken());
            }
            int min = 10000000;
            int ans = -1;
            for(int i=1;i<=N;i++){
                int tempLen = 0;
                for(int j=0;j<person;j++){
                    tempLen += dist[personInfo[j]][i];
                }
                if(tempLen < min){
                    min = tempLen;
                    ans = i;
                }
            }
            System.out.println(ans);
        }
    }

}
```

<br>
<br>

# **🔑Description**
- 

  


<br>
<br>

# **📑Related Issues**

> 

<br>
<br>

# **🕛Resource**

| Memory  | Time  | Implementation Time |
| ------- | ----- | ------------------- |
| 32412KB | 460ms |                     |
