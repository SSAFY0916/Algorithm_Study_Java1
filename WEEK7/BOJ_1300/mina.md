![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:FF658D,100:FFCB32&text=BOJ%201300&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 1300 K번째 수](https://www.acmicpc.net/problem/1300)

<br>
<br>

# **💻Code**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    static int N, K;

    public static void main(String[] args) throws Exception {

        N = Integer.parseInt(br.readLine());
        K = Integer.parseInt(br.readLine());

        long start = 1;
        long end = K;
        long result = 0;

        while (start <= end) {
            long middle = (start + end) / 2;
            long total = 0;

            // 1부터 N번째 열에서 각 열의 middle 이하의 숫자의 개수를 전부 합함
            for (int i = 1; i <= N; i++) {
                total += Math.min(middle / i, N);
            }

            if (total < K)  // 그 개수가 K개가 안될때 - 범위 옮기기
                start = middle + 1;
            else {
                end = middle - 1;
                result = middle;
            }
        }

        bw.write(Long.toString(result));

        bw.close();
    }

}

```

<br>
<br>

# **🔑Description**

> 1부터 N번째 열이 있을때 각 열마다 있는 middle 이하의 숫자의 개수들을 모두 더했다.\
> 그 숫자가 K와 같아질 때의 middle을 출력하였다.\
> 다르다면 구간을 옮겨가면서 K가 되는 순간을 찾았다.\
> 또 i번째 열은 i의 배수로 이루어져 있다는 점을 이용했다.\

<br>
<br>

# **📑Related Issues**

> 약수의 개수 이용해서 풀지 이분 탐색으로 생각해볼지 고민하다가 구현이 쉬워 보이는 전자로 했는데 로컬에서 돌렸을때 수행시간이 엄청 오래걸렸다...ㅎㅎ\
> 이분 탐색 맞는 것 같아서 종이에 표 그리면서 방법을 고민했다.\
> 28번째 라인 - total += Math.min(middle / i, N);를 원래는
> total += middle/i 로 했었다가 게시판 반례에서 걸렸당..(틀릴것 같아서 제출 못하고 게시판 반례부터 돌려봄)\
> 생각해보니까 1 to K에서 이분탐색 하는거라 middle > N 이 될수도 있어서 암튼 저렇게 하면 안된다는 걸 깨달음

<br>
<br>

# **🕛Resource**

| Memory  | Time  |
| ------- | ----- |
| 11736KB | 120ms |
