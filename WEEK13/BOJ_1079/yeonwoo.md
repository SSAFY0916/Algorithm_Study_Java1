![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2EㄹFF,100:A1AAF9&text=BOJ%201079&fontColor=000000&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 1079 마피아](https://www.acmicpc.net/problem/1079)

<br>
<br>

# **💻Code**

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int N;//참가자 수
    static int[] guilty;//유죄 지수
    static int[][] R;
    static int enjin = 0;
    static int ans = 0;


    static boolean[] isLive;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        N = Integer.parseInt(br.readLine());//참가자수
        guilty = new int[N];//유죄지수
        R = new int[N][N];//반응
        isLive = new boolean[N];//생존자

        Arrays.fill(isLive, true);

        st = new StringTokenizer(br.readLine());
        //유죄지수 초기화
        for(int i = 0; i < N; i++) {
            guilty[i] = Integer.parseInt(st.nextToken());
        }

        //R 초기화
        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < N; j++) {
                R[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        //은진이 번호
        enjin = Integer.parseInt(br.readLine());
        play(N, 0);
        System.out.println(ans);
    }

    static void play(int cnt, int day) {
        if(!isLive[enjin] || cnt == 1) {//은진이가 죽었거나 은진이만 남았거나??
            ans = Math.max(ans, day);
            return;
        }

        if(cnt %2 == 0) {
            //짝수(밤)일 경우 랜덤으로 한명을 죽이다.
            for(int i = 0; i < N; i++) {
                if(!isLive[i] || i == enjin)//못죽임
                    continue;

                //guilty 바꾸기
                for(int j = 0; j < N; j++) {
                    if(!isLive[j])
                        continue;
                    guilty[j] += R[i][j];
                }

                //사망처리하고 분기생성
                isLive[i] = false;
                play(cnt-1, day+1);

                //복구
                isLive[i] = true;
                //guilty 복구
                for(int j = 0; j < N; j++) {
                    if(!isLive[j])
                        continue;
                    guilty[j] -= R[i][j];
                }
            }
        }else {
            int max = 0, idx = N-1;

            for(int i = 0; i < N; i++) {
                if(isLive[i] && max < guilty[i]) {//살아있는 사람 중 최악의 유조ㅣ지수
                    max = guilty[i];
                    idx = i;
                }
                //동점은 알아서 앞번호 걸리므로 패스
                // else if(isLive[i] && max == guilty[i]) {
                //     max = guilty[i];
                //     idx = Math.min(i, idx);
                // }
            }

            //최악유죄지수자 사망처리후 분기생성
            isLive[idx] = false;
            play(cnt-1, day);

            //오직 하나의 경우이므로 복구할필요x
            //ㄴㄴ 복구해야 다른 분기에 영향 안줌
            isLive[idx] = true;
        }
    }
}
```

<br>
<br>

# 🔑Description

```
//dfs
//모든 분기 생성 후에는 복구하여 다른 분기에 영향 주지 않도록 할 것
```

<br>
<br>

# **📑Related Issues**

> * ```
>               //오직 하나의 경우이므로 복구할필요x
>               //ㄴㄴ 복구해야 다른 분기에 영향 안줌
>               isLive[idx] = true;
>           
>   ```
>
>   

<br>
<br>

# **🕛Resource**

| Memory  | Time  |
| ------- | ----- |
| 23916KB | 636ms |
