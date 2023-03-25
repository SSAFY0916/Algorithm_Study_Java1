![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:FF658D,100:FFCB32&text=BOJ%201700&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 1300 멀티탭 스케줄링](https://www.acmicpc.net/problem/1700)

<br>
<br>

# **💻Code**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;


public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    static int N, K;

    public static void main(String[] args) throws Exception {

        StringTokenizer st = new StringTokenizer(br.readLine(), " ");

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        int[] electrics = new int[K + 1];   // 전자제품 순서
        boolean[] plug = new boolean[K + 1];    // 꽂혀있는 플러그 표시
        boolean[] check = new boolean[K + 1];   // 안쓰거나 제일 나중에 쓰는 플러그 찾을때 씀
        int plugCount = 0;  // 사용중인 콘센트 개수
        int result = 0; // 플러그 빼는 횟수

        st = new StringTokenizer(br.readLine(), " ");
        for (int i = 1; i <= K; i++) {
            electrics[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 1; i <= K; i++) {
            if (plug[electrics[i]]) // 이미 꽂혀있음
                continue;

            if (plugCount < N) {  // 빈자리가 있어서 꽂을 수 있음
                plug[electrics[i]] = true;
                plugCount++;
                continue;
            }

            int index = -1;
            Arrays.fill(check, false);
            for (int j = i + 1; j <= K; j++) {  // 꽂혀있는 플래그 중 앞으로 사용 안하는 플래그 찾기
                check[electrics[j]] = true; // 앞으로 사용 예정인 플러그 표시
            }\
            for (int j = 1; j <= K; j++) {
                if (plug[electrics[j]] && !check[electrics[j]]) {   //꽂혀있으면서 사용예정 아닌 플러그
                    index = electrics[j];
                    break;
                }
            }

            if (index != -1) {  // 안쓰는 플러그가 있음
                plug[index] = false;
                plug[electrics[i]] = true;
                result++;
                continue;
            }


            Arrays.fill(check, false);
            for (int j = i + 1; j <= K; j++) {  // 꽂혀있는 플래그 중 제일 나중에 사용하는 플래그 찾기
                if (!check[electrics[j]] && plug[electrics[j]]) {
                    check[electrics[j]] = true; // 하나의 플래그가 여러번 사용될 경우 그 중 먼저 쓰는 플래그를 기준으로 함
                    index = electrics[j];
                }
            }

            if (index != -1) {  // 제일 나중에 쓰는 플러그 찾음
                plug[index] = false;
                plug[electrics[i]] = true;
                result++;
                continue;
            }

            if (index == -1 && i == K)  // 맨 마지막 플러그
                result++;
        }

        bw.write(Integer.toString(result));

        bw.close();
    }

}

```

<br>
<br>

# **🔑Description**

> i번째 가전제품을 콘센트에 꽂아야할때 나올 수 있는 경우를 4가지로 나눴다.
>
> 1. i번째 가전제품이 이미 꽂혀있는 경우 - continue
> 2. i번째 가전제품이 안꽂혀있는데 빈 콘센트가 있는 경우 - 빈자리에 사용함
> 3. i번째 가전제품이 안꽂혀있는데 빈 콘센트가 있는 경우 - 앞으로 다시 사용 안될 가전제품 중 희생양 찾아서 그거 빼고 꽂기
> 4. i번째 가전제품이 안꽂혀있는데 빈 콘센트가 있는 경우 - 제일 나중에 사용될 가전제품을 희생양으로 골라서 그거 빼고 꽂기
>
> 맨 마지막 가전제품을 꽂아야 하는 경우에는 그냥 아무거나 빼고 꽂게 했다.

<br>
<br>

# **📑Related Issues**

> 그리디 인것 같았는데 넘 졸리고 10시까지 시간이 얼마 안남아서(...) 카테고리 확인했는데 그리디가 맞았다 정말 다행야...\
> 처음에는 50 라인에 있는 조건식을 49라인 for문 조건 안에 같이 넣어놨었다.\
> for문은 조건 틀리면 바로 빠져나오는데 왜 continue 되어서 계속 돌아갈거라고 생각한건지...?\
> 졸려서 정신이 나갓낭....

<br>
<br>

# **🕛Resource**

| Memory  | Time |
| ------- | ---- |
| 11644KB | 76ms |
