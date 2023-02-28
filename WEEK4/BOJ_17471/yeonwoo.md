![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%20N&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 17471 게리맨더링](https://www.acmicpc.net/problem/17471)

<br>
<br>

# **Code**

```java
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Queue;
import java.util.ArrayDeque;
/*
1. 부분집합을 이용하여 a선거구 도시 뽑는다
    - a선거구 인원은 boolean isAreaA[]에 체크
2. A와 B 선거구 모두 연속인가 확인
3. a와 b 선거구 인구합 차이 계산하여 결과와 비교
 */
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    static int N;
    static int[] population;//인구
    static boolean[] isAreaA;//true = A선거구 영역 
    static ArrayList<Integer>[] link;//인접도시리스트

    static int minGap = Integer.MAX_VALUE;//답

    public static void main(String[] args) throws IOException{
        N = Integer.parseInt(br.readLine());//도시

        //인구 등록
        population = new int[N+1];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i=1;i<=N;i++){
            population[i] = Integer.parseInt(st.nextToken());
        }

        //도시간 연결 빈리스트 생성
        link = new ArrayList[N+1];
        for(int i=1;i<=N;i++){
            link[i] = new ArrayList<>();

            st = new StringTokenizer(br.readLine());
            int size = Integer.parseInt(st.nextToken());

            for(int j=0;j<size;j++){
                link[i].add(Integer.parseInt(st.nextToken()));
            }
        }
        isAreaA = new boolean[N+1];
        select(1);//부분집합

        System.out.println(minGap==Integer.MAX_VALUE?-1:minGap);
    }
    //부분집합을 이용한 A선거구에 포함될 도시 선정
    private static void select(int depth){
        if(depth==N+1){//N개의 도시에 대하여 A선거구로 할지 말지 정했음
            if(check()){//동일 선거구 모두 인접하였는가 확인하는 함수
                int sumA =0;//a선거구의합
                int sumB = 0;//b선거구의합

                for(int i=1;i<=N;i++){
                    if(isAreaA[i]){
                        sumA += population[i];
                    }else{
                        sumB += population[i];
                    }
                }

                minGap = Math.min(minGap, Math.abs(sumA-sumB));
            }

            return;
        }
        //사용
        isAreaA[depth] = true;
        select(depth+1);
        //비사용
        isAreaA[depth] = false;

        select(depth+1);
    }
    //동일 선거구는 인접한 도시끼리인가
    static boolean check(){

        //a선거구 좌표 1개 찾기(bfs 시작용)
        int aStart = -1;
        for(int i=1;i<=N;i++){
            if(isAreaA[i]) {
                aStart = i;
                break;
            }
        }
        //b선거구 좌표 1개 찾기(bfs 시작용)
        int bStart = -1;
        for(int i=1;i<=N;i++){
            if(!isAreaA[i]){
                bStart = i;
                break;
            }
        }
        //한 선거구만 있는 경우 {사용불가}판정
        if(aStart==-1 || bStart==-1) return false;

        //bfs 준비
        boolean[] visited = new boolean[N+1];
        Queue<Integer> q = new ArrayDeque<>();

        //bfs시작
        /*
        aStart, bStart로부터 각각 bfs 한 번씩 돌립니다.
        선거구는 2개에 각 선거구는 모두 인접하였으니
        2회의 bfs로 visited배열은 모두 true상태가 되어야 합니다.
        */
        //a체크
        q.offer(aStart);
        visited[aStart] = true;
        while(!q.isEmpty()){
            int curCity = q.poll();

            for(int nextCity:link[curCity]){
                if(visited[nextCity]) continue; //방문초과
                if(!isAreaA[nextCity]) continue;//A영토아님

                visited[nextCity] = true;
                q.offer(nextCity);
            }
        }

        //b체크
        q.offer(bStart);
        visited[bStart] = true;
        while(!q.isEmpty()){
            int curCity = q.poll();

            for(int nextCity:link[curCity]){
                if(visited[nextCity]) continue;
                if(isAreaA[nextCity]) continue;//B영토아님

                visited[nextCity] = true;
                q.offer(nextCity);
            }
        }

        //2회의 bfs로 모든 visited를 true로 채웠다면
        //각 선거구의 도시들은 서로 이웃합니다.
        for(int i=1;i<=N;i++){
            if(visited[i] == false) return false;
        }
        return true;

    }
}
```

<br>
<br>

# **🔑Description**
> 부분집합을 이용하여 선거구를 나눴습니다(a선거구는 boolean[] isAreaA에 true표기)
> 부분집합이 완성되면 check함수를 이용해 사용 가능한 선거구인지 판단했습니다
> check함수는 bfs2번으로 모든 도시 번호를 순회 가능한지 판별합니다
> (1번은 isAreaA[i] = true만 순회, 1번은 !isAreaA[i]==false만 순회)
> check를 이용해 동일 선거구끼리 인접하였나 판단되면
> 최저 인구수 차이를 구합니다

<br>
<br>

# **📑Related Issues**

> 처음에 입력 받는게 어려웠고
> 시간초과에 대한 부담 때문에 시도가 어려운 문제가 있었습니다
> 

<br>
<br>

# **🕛Resource**

| Memory  | Time  | Implementation Time |
| ------- | ----- | ------------------- |
| 15288KB | 144ms | 2 Hour 00 Minutes   |
