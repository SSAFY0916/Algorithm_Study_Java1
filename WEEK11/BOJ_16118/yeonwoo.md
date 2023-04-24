![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:FF658D,100:FFCB32&text=BOJ%2016118&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 16118 달빛 여우](https://www.acmicpc.net/problem/16118)

<br>
<br>

# **💻Code**

```java
import java.io.*;
import java.util.*;
/*
여우다익 늑대다익으로
각 동물의 1번정점으로부터 다른 모든 정점까지의 거리를 구한다
(음간 없으므로 벨만포드x 다익o)

2번부터 끝 정점까지 두 동물의 최소거리를 비교하여 여우가 더 짧으면 res++


늑대다익의 경우 각 정점별로 홀수로왔을 때, 짝수로 왔을 때를 나누어 저장한다
    거리를 메모이제이션하는 배열을 이차원 배열로 선언하여 [odd][dist]형식으로 저장한다
        odd가 1이면 홀수이동으로 도착한 경우의 거리를 저장한다
        odd가 2이면 짝수이동으로 도착한 경우의 거리를 저장한다
    간선 클래스에도 odd iv를 두어 거리 갱신 시점에 odd인지 even인지 따진다
 */
public class Main{
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int N;//정점
    static int M;//간선
    static List<Edge>[] map;//맵

    static int[] distFox;//여우거리
    static int[][]distWolf;//늑대거리

    static class Edge implements Comparable<Edge>{
        int e,w;//종료,가중
        int odd;//울프 홀수거리면 1

        //여우용
        public Edge(int e, int w){
            this.e = e;
            this.w = w;
        }

        //늑대용
        public Edge(int e, int w, int odd){
            this.e = e;
            this.w = w;
            this.odd = odd;
        }

        @Override
        public int compareTo(Edge o){
            return this.w - o.w;
        }
    }

    public static void main(String[] args) throws IOException{
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        //지도
        map = new ArrayList[N+1];
        for(int i=1;i<=N;i++){
            map[i] = new ArrayList<>();//노드별 간선리스트 초기화
        }

        distFox = new int[N+1]; //1인덱스 시작
        distWolf = new int[2][N+1];//1 홀수진입, 0 짝수진입

        Arrays.fill(distFox,Integer.MAX_VALUE);
        Arrays.fill(distWolf[0],Integer.MAX_VALUE);
        Arrays.fill(distWolf[1],Integer.MAX_VALUE);

        for(int i=0;i<M;i++){
            st = new StringTokenizer(br.readLine());
            int s=  Integer.parseInt(st.nextToken());
            int e=  Integer.parseInt(st.nextToken());
            int w=  Integer.parseInt(st.nextToken())*2;// 소수가 나오지 않도록 2배화

            map[s].add(new Edge(e,w));
            map[e].add(new Edge(s,w));
        }

        dijkWolf();
        dijkFox();


        int cnt = 0;
        for(int i=2;i<=N;i++){
            int dW = Math.min(distWolf[0][i], distWolf[1][i]);
            int dF = distFox[i];
//            System.out.println("dW = " + dW);
//            System.out.println("dF = " + dF);

            if(dW>dF) cnt++;
        }
        System.out.println(cnt);
    }
    static void dijkWolf(){
        //다익 초기재료
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        pq.offer(new Edge(1,0,0));//짝수로 들어온 취급
        distWolf[0][1] = 0;

        while(!pq.isEmpty()){
            Edge cur = pq.poll();

            if(distWolf[cur.odd][cur.e]<cur.w) continue;

            for(Edge nEdge:map[cur.e]){
                //다음 가중치 판단
                int nWeight = cur.w;
                if(cur.odd==0){//기존이 짝수거리면
                    nWeight+= nEdge.w/2;//이번에홀수이동 => 이속 두배
                }else{
                    nWeight+= nEdge.w*2;
                }

                //다음 홀짝판단
                int nOdd = cur.odd==1?0:1;//이번홀?다음짝:다음홀


                if(distWolf[nOdd][nEdge.e] >nWeight){
                    distWolf[nOdd][nEdge.e] =nWeight;
                    pq.offer(new Edge(nEdge.e,nWeight,nOdd));
                }
            }
        }
    }

    static void dijkFox(){
        //다익 초기잴
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        pq.offer(new Edge(1,0));
        distFox[1] = 0;

        while(!pq.isEmpty()){
            Edge cur = pq.poll();//현재간선
            if(distFox[cur.e]<cur.w) continue;//기존 e까지 거리가, 현 간선써서 e까지 가는 거리보다 짧을시
            for(Edge nEdge:map[cur.e]){
                if(distFox[nEdge.e]>cur.w+nEdge.w){//dist[nextE] > 현재정점->인접정점 가중치
                    distFox[nEdge.e]=cur.w+nEdge.w;
                    pq.offer(new Edge(nEdge.e,cur.w+nEdge.w));
                }
            }
        }
    }




}
```

<br>
<br>

# **🔑Description**

> ```
> /*
> 여우다익 늑대다익으로
> 각 동물의 1번정점으로부터 다른 모든 정점까지의 거리를 구한다
> (음간 없으므로 벨만포드x 다익o)
> 
> 2번부터 끝 정점까지 두 동물의 최소거리를 비교하여 여우가 더 짧으면 res++
> 
> 
> 늑대다익의 경우 각 정점별로 홀수로왔을 때, 짝수로 왔을 때를 나누어 저장한다
>     거리를 메모이제이션하는 배열을 이차원 배열로 선언하여 [odd][dist]형식으로 저장한다
>         odd가 1이면 홀수이동으로 도착한 경우의 거리를 저장한다
>         odd가 2이면 짝수이동으로 도착한 경우의 거리를 저장한다
>     간선 클래스에도 odd iv를 두어 거리 갱신 시점에 odd인지 even인지 따진다
>  */
> ```
>
> 

<br>
<br>

# **📑Related Issues**

> 나누기2 했을 때 소수가 나오는 경우에 대한 처리
>
> ​	가중치*=2
>
> 홀수번째 이동일 때 홀수 이동의 최소거리가 보장되는게 맞나 싶은 마음
>
> ​	홀수로 오는 모든 경우의 최솟값
>
> ​	짝수로 오는 모든 경우의 최솟값
>
> ​		이라고 생각하니까 이해가 되어씀

<br>
<br>

# **🕛Resource**

| Memory  | Time  |
| ------- | ----- |
| 64228KB | 856ms |
