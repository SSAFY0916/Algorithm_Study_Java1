![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%20N&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **๐Problem**

> [BOJ 17471 ๊ฒ๋ฆฌ๋งจ๋๋ง](https://www.acmicpc.net/problem/17471)

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
1. ๋ถ๋ถ์งํฉ์ ์ด์ฉํ์ฌ a์ ๊ฑฐ๊ตฌ ๋์ ๋ฝ๋๋ค
    - a์ ๊ฑฐ๊ตฌ ์ธ์์ boolean isAreaA[]์ ์ฒดํฌ
2. A์ B ์ ๊ฑฐ๊ตฌ ๋ชจ๋ ์ฐ์์ธ๊ฐ ํ์ธ
3. a์ b ์ ๊ฑฐ๊ตฌ ์ธ๊ตฌํฉ ์ฐจ์ด ๊ณ์ฐํ์ฌ ๊ฒฐ๊ณผ์ ๋น๊ต
 */
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    static int N;
    static int[] population;//์ธ๊ตฌ
    static boolean[] isAreaA;//true = A์ ๊ฑฐ๊ตฌ ์์ญ 
    static ArrayList<Integer>[] link;//์ธ์ ๋์๋ฆฌ์คํธ

    static int minGap = Integer.MAX_VALUE;//๋ต

    public static void main(String[] args) throws IOException{
        N = Integer.parseInt(br.readLine());//๋์

        //์ธ๊ตฌ ๋ฑ๋ก
        population = new int[N+1];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i=1;i<=N;i++){
            population[i] = Integer.parseInt(st.nextToken());
        }

        //๋์๊ฐ ์ฐ๊ฒฐ ๋น๋ฆฌ์คํธ ์์ฑ
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
        select(1);//๋ถ๋ถ์งํฉ

        System.out.println(minGap==Integer.MAX_VALUE?-1:minGap);
    }
    //๋ถ๋ถ์งํฉ์ ์ด์ฉํ A์ ๊ฑฐ๊ตฌ์ ํฌํจ๋  ๋์ ์ ์ 
    private static void select(int depth){
        if(depth==N+1){//N๊ฐ์ ๋์์ ๋ํ์ฌ A์ ๊ฑฐ๊ตฌ๋ก ํ ์ง ๋ง์ง ์ ํ์
            if(check()){//๋์ผ ์ ๊ฑฐ๊ตฌ ๋ชจ๋ ์ธ์ ํ์๋๊ฐ ํ์ธํ๋ ํจ์
                int sumA =0;//a์ ๊ฑฐ๊ตฌ์ํฉ
                int sumB = 0;//b์ ๊ฑฐ๊ตฌ์ํฉ

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
        //์ฌ์ฉ
        isAreaA[depth] = true;
        select(depth+1);
        //๋น์ฌ์ฉ
        isAreaA[depth] = false;

        select(depth+1);
    }
    //๋์ผ ์ ๊ฑฐ๊ตฌ๋ ์ธ์ ํ ๋์๋ผ๋ฆฌ์ธ๊ฐ
    static boolean check(){

        //a์ ๊ฑฐ๊ตฌ ์ขํ 1๊ฐ ์ฐพ๊ธฐ(bfs ์์์ฉ)
        int aStart = -1;
        for(int i=1;i<=N;i++){
            if(isAreaA[i]) {
                aStart = i;
                break;
            }
        }
        //b์ ๊ฑฐ๊ตฌ ์ขํ 1๊ฐ ์ฐพ๊ธฐ(bfs ์์์ฉ)
        int bStart = -1;
        for(int i=1;i<=N;i++){
            if(!isAreaA[i]){
                bStart = i;
                break;
            }
        }
        //ํ ์ ๊ฑฐ๊ตฌ๋ง ์๋ ๊ฒฝ์ฐ {์ฌ์ฉ๋ถ๊ฐ}ํ์ 
        if(aStart==-1 || bStart==-1) return false;

        //bfs ์ค๋น
        boolean[] visited = new boolean[N+1];
        Queue<Integer> q = new ArrayDeque<>();

        //bfs์์
        /*
        aStart, bStart๋ก๋ถํฐ ๊ฐ๊ฐ bfs ํ ๋ฒ์ฉ ๋๋ฆฝ๋๋ค.
        ์ ๊ฑฐ๊ตฌ๋ 2๊ฐ์ ๊ฐ ์ ๊ฑฐ๊ตฌ๋ ๋ชจ๋ ์ธ์ ํ์์ผ๋
        2ํ์ bfs๋ก visited๋ฐฐ์ด์ ๋ชจ๋ true์ํ๊ฐ ๋์ด์ผ ํฉ๋๋ค.
        */
        //a์ฒดํฌ
        q.offer(aStart);
        visited[aStart] = true;
        while(!q.isEmpty()){
            int curCity = q.poll();

            for(int nextCity:link[curCity]){
                if(visited[nextCity]) continue; //๋ฐฉ๋ฌธ์ด๊ณผ
                if(!isAreaA[nextCity]) continue;//A์ํ ์๋

                visited[nextCity] = true;
                q.offer(nextCity);
            }
        }

        //b์ฒดํฌ
        q.offer(bStart);
        visited[bStart] = true;
        while(!q.isEmpty()){
            int curCity = q.poll();

            for(int nextCity:link[curCity]){
                if(visited[nextCity]) continue;
                if(isAreaA[nextCity]) continue;//B์ํ ์๋

                visited[nextCity] = true;
                q.offer(nextCity);
            }
        }

        //2ํ์ bfs๋ก ๋ชจ๋  visited๋ฅผ true๋ก ์ฑ์ ๋ค๋ฉด
        //๊ฐ ์ ๊ฑฐ๊ตฌ์ ๋์๋ค์ ์๋ก ์ด์ํฉ๋๋ค.
        for(int i=1;i<=N;i++){
            if(visited[i] == false) return false;
        }
        return true;

    }
}
```

<br>
<br>

# **๐Description**
> ๋ถ๋ถ์งํฉ์ ์ด์ฉํ์ฌ ์ ๊ฑฐ๊ตฌ๋ฅผ ๋๋ด์ต๋๋ค(a์ ๊ฑฐ๊ตฌ๋ boolean[] isAreaA์ trueํ๊ธฐ)
> ๋ถ๋ถ์งํฉ์ด ์์ฑ๋๋ฉด checkํจ์๋ฅผ ์ด์ฉํด ์ฌ์ฉ ๊ฐ๋ฅํ ์ ๊ฑฐ๊ตฌ์ธ์ง ํ๋จํ์ต๋๋ค
> checkํจ์๋ bfs2๋ฒ์ผ๋ก ๋ชจ๋  ๋์ ๋ฒํธ๋ฅผ ์ํ ๊ฐ๋ฅํ์ง ํ๋ณํฉ๋๋ค
> (1๋ฒ์ isAreaA[i] = true๋ง ์ํ, 1๋ฒ์ !isAreaA[i]==false๋ง ์ํ)
> check๋ฅผ ์ด์ฉํด ๋์ผ ์ ๊ฑฐ๊ตฌ๋ผ๋ฆฌ ์ธ์ ํ์๋ ํ๋จ๋๋ฉด
> ์ต์  ์ธ๊ตฌ์ ์ฐจ์ด๋ฅผ ๊ตฌํฉ๋๋ค

<br>
<br>

# **๐Related Issues**

> ์ฒ์์ ์๋ ฅ ๋ฐ๋๊ฒ ์ด๋ ค์ ๊ณ 
> ์๊ฐ์ด๊ณผ์ ๋ํ ๋ถ๋ด ๋๋ฌธ์ ์๋๊ฐ ์ด๋ ค์ด ๋ฌธ์ ๊ฐ ์์์ต๋๋ค
> 

<br>
<br>

# **๐Resource**

| Memory  | Time  | Implementation Time |
| ------- | ----- | ------------------- |
| 15288KB | 144ms | 2 Hour 00 Minutes   |
