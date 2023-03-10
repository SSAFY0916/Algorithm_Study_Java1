![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%20N&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **๐Problem**

> [BOJ 15684 ์ฌ๋ค๋ฆฌ ์กฐ์](https://www.acmicpc.net/problem/15684)

<br>
<br>

# **Code**

```java
package d230302.bj_์ฌ๋ค๋ฆฌ;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
/*Time
๊ตฌ์ 10m
5:35~5:45

๊ตฌํ 2h45m
5:45~6:30
9:00~11:00
 */

//๋ฉ๋ชจ๋ฆฌ์ ์๊ฐ
// 18312KB, 2828ms

/*๊ตฌ์
์ฌ๋ค๋ฆฌ ์กฐ์

์ฌ๋ค๋ฆฌ ํํ
1์ธ๋ฑ์ค 2์ฐจ์๋ฐฐ์ด H+1,N+1
M = ์ด๋ฏธ์๋์ฐ๊ฒฐ
true์ true๋ก ์ฐ๊ฒฐ ํ์ธ

์ฌ๋ค๋ฆฌ ์๋ฎฌ
์๋๋ก์ด๋
ํธ๋ฃจ๋ฉด ์์ผ๋ก ์ด๋

์ฌ๋ค๋ฆฌ ์ถ๊ฐ
1,1 ~ n,m-1๊น์ง ์ถ๊ฐ ๊ฐ๋ฅ, ๋จ์ผ๋ฉด r,c์ r,c+1 ํธ๋ฃจ์ฒ๋ฆฌ
์ค๋ณต์์ด

 */

/*ISSUE

issue0์์-------------------------
1๊ณผ 2๊ฐ ์ฐ๊ฒฐ๋์ด์์์
1 2 3 4
t t f f
ํ์์ผ๋ก ํ๊ธฐํ ๊ฒฐ๊ณผ

1-2์ฐ๊ฒฐ์ด ์๊ณ , 3-4 ์ฐ๊ฒฐ์ด ์์ ๋
1 2 3 4
t t t t๊ฐ ๋์ด์
3์์ ์ถ๋ฐํ๋ฉด 3->2 ์ด๋์ด ๊ฐ๋ฅํ ๋ฒ๊ทธ๊ฐ ๋ฐ์ํจ

์ด๊ฒฐ์ ์ข์ธก์๋ง ํ๊ธฐ๋ฅผ ํ๋ ๋ฐฉ์์ผ๋ก ํด๊ฒฐํจ
ex) 1๊ณผ 2๊ฐ ์ฐ๊ฒฐ๋์ด์๋ค๋ฉด
1 2 3 4
t f f f
issue0์ข๋ฃ-------------------------



issue1์์-------------------------
dfs๋๊น ํ ๊ฐ์ง์์ 3์์ ์ฐพ๊ณ 
๊ทธ ๋ค์ ๊ฐ์ง์์ 1์์ ์ฐพ์ ์ ์์๋๋ฐ
result!=์ด๊ธฐ๊ฐ์ด๋ฉด ๊ฐ์ง์น๋ ๋ก์ง ๋ฃ์์
if result!=-1 then return์
if result<=depth then return์ผ๋ก ์์ ํ์ฌ ํด๊ฒฐํ์์
issue1์ข๋ฃ-------------------------
 */

public class BJ_15684_์ฌ๋ค๋ฆฌ_์กฐ์ {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N; // C
    static int M; //์ฐ๊ฒฐ ์
    static int H; //R
    static boolean[][] ladder;
    static boolean[][] visited;

    static int result = Integer.MAX_VALUE;
    static int simulate(int sc){
        int sr = 1;

        int nr = sr;
        int nc = sc;
        while(isIn(nr,nc)){
            if(ladder[nr][nc]){//์ ๋ฐ๊ฒฌ
                nc++;
            }else if(isIn(nr,nc-1)){
                if(ladder[nr][nc-1]){
                    nc--;
                }
            }
            nr++;
        }
//        System.out.printf("%d -> %d\n",sc,nc);
        return nc;

    }
    static boolean isIn(int r, int c){
        return r>=1 && r<=H && c>=0 && c<=N;
    }

    static boolean iIsi(){
        for(int i=1;i<=N;i++){
            if(simulate(i)!=i)return false;
        }
        return true;
    }
    static void dfs(int depth){
//        printArr(ladder);
        if(result<=depth) return;
        if(iIsi()) {
            result = depth;
            return;
        }
        if(depth==3){
            return;
        }

        for(int r=1;r<=H;r++){
            for(int c=1;c<N;c++){
                if(ladder[r][c]) continue;//๋ชป๋ - ์กด์ฌํ๋ ์ 
                if(ladder[r][c-1]) continue;// ๋ชป๋ - ์ข์ธก์์์ด์
                if(visited[r][c]) continue;
                //์ฌ๋ค๋ฆฌ ๋๊ธฐ
                ladder[r][c] = true;
                visited[r][c] = true;
                dfs(depth+1);
                ladder[r][c] = false;
                visited[r][c]=false;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());//C
        M = Integer.parseInt(st.nextToken());//์ฐ๊ฒฐ
        H = Integer.parseInt(st.nextToken());//R
        //์ฌ๋ค๋ฆฌ ์์ฑ
        ladder = new boolean[H+1][N+1];
        visited = new boolean[H+1][N+1];

        for(int i=0;i<M;i++){
            //st๋ก ์จ๋ ๋ฉ๋ชจ๋ฆฌ ์๋์ค
            StringTokenizer edge = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(edge.nextToken());
            int b = Integer.parseInt(edge.nextToken());
            ladder[a][b] = true;


        }
//         printArr(ladder);

//
//         simulate(1);
//         for(int i=1;i<=N;i++){
//             System.out.println(i+" "+simulate(i));
//         }
        dfs(0);
        System.out.println(result==Integer.MAX_VALUE?-1:result);

    }



    //๋๋ฒ๊น ํจ์
    static void printArr(boolean[][] map){
        System.out.println();
        for(int i=0;i<map.length;i++){
            for(int j=0;j<map[0].length;j++){
                System.out.print(map[i][j]?"1 ":"0 ");
            }
            System.out.println();
        }
        System.out.println();
    }
    //
}
```

<br>
<br>

# **๐Description**
- ์ด์ฐจ์ boolean ๋ฐฐ์ด๋ก ์ฌ๋ค๋ฆฌ๋ฅผ ํํํ์์ต๋๋ค.
- ์ด๊ฒฐ์ด ์กด์ฌํ๋ค๋ฉด ์ฐ๊ฒฐ๋์ด์๋ ์  ์ค ์ข์ธก ์ ์ true ํ๊ธฐ๋ฅผ ํ์์ต๋๋ค.
- ๋ฐฑํธ๋ํน์ ํตํด ์ถ๊ฐํ  ์ฌ๋ค๋ฆฌ๋ฅผ ์ ํํ์์ต๋๋ค
  - ๊ฐ์ง์น๊ธฐ์กฐ๊ฑด
    1. ์ถ๊ฐ๋ ์ฌ๋ค๋ฆฌ๊ฐ ๊ธฐ์กด ์ต์๊ฐ ์ด์์
    2. ํ์ฌ ์ฌ๋ค๋ฆฌ๊ฐ ๋ฌธ์  ์กฐ๊ฑด์ ์ถฉ์กฑํ  ๊ฒฝ์ฐ
       - ์กฐ๊ฑด ์ถฉ์กฑ ํ์ธ ๋ฐฉ๋ฒ
         1. ๋จ์ ์๋ฎฌ๋ ์ด์์ผ๋ก ํ์ธํ์์ต๋๋ค.


<br>
<br>

# **๐Related Issues**

> ```
> issue0์์-------------------------
> 1๊ณผ 2๊ฐ ์ฐ๊ฒฐ๋์ด์์์
> 1 2 3 4
> t t f f
> ํ์์ผ๋ก ํ๊ธฐํ ๊ฒฐ๊ณผ
> 
> 1-2์ฐ๊ฒฐ์ด ์๊ณ , 3-4 ์ฐ๊ฒฐ์ด ์์ ๋
> 1 2 3 4
> t t t t๊ฐ ๋์ด์
> 3์์ ์ถ๋ฐํ๋ฉด 3->2 ์ด๋์ด ๊ฐ๋ฅํ ๋ฒ๊ทธ๊ฐ ๋ฐ์ํจ
> 
> ์ด๊ฒฐ์ ์ข์ธก์๋ง ํ๊ธฐ๋ฅผ ํ๋ ๋ฐฉ์์ผ๋ก ํด๊ฒฐํจ
> ex) 1๊ณผ 2๊ฐ ์ฐ๊ฒฐ๋์ด์๋ค๋ฉด
> 1 2 3 4
> t f f f
> issue0์ข๋ฃ-------------------------
> 
> 
> 
> issue1์์-------------------------
> dfs๋๊น ํ ๊ฐ์ง์์ 3์์ ์ฐพ๊ณ 
> ๊ทธ ๋ค์ ๊ฐ์ง์์ 1์์ ์ฐพ์ ์ ์์๋๋ฐ
> result!=์ด๊ธฐ๊ฐ์ด๋ฉด ๊ฐ์ง์น๋ ๋ก์ง ๋ฃ์์
> if result!=-1 then return์
> if result<=depth then return์ผ๋ก ์์ ํ์ฌ ํด๊ฒฐํ์์
> issue1์ข๋ฃ-------------------------
> 
> 
> issue2์์-------------------------
> ๊ฐ์ง๊ฐ ๋๋๋ฉด visited๋ฅผ ํ์ด์คฌ์ด์ผ ํ๋๋ฐ
> visited๋ฅผ ํด์ ํ์ด์ผ ํ๋๋ฐ ํ์ง ์์์
> dfs ์ฝ๋๋ฅผ ํ์์ ๋ค๋ฅด๊ฒ ์ง๊ฒ ๋๋๋ฐ
> ๊ทธ๋ฌ๋ฉด์ ๊ตฌ์กฐ ์ดํด๊ฐ ์ ๋์ง ์์๋๋ฏ
> 
> ํ์ ์ง๋ ๊ตฌ์กฐ๋ก(ํ๋ผ๋ฏธํฐ๋ก r๊ณผ c๋ฅผ ์ฃผ๋ ํํ) ์ฌ๊ตฌ์ฑ ํด๋ณด๋ ค ํ์ผ๋
> ์ฝ์ง ์์๋ค
> issue2์ข๋ฃ-------------------------
> ```

<br>
<br>

# **๐Resource**

| Memory  | Time   | Implementation Time |
| ------- | ------ | ------------------- |
| 18312KB | 2828ms | 2h 45m              |
