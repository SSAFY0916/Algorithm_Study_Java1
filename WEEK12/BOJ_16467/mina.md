![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:A066F9&text=BOJ%2016467&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 16467 병아리의 변신은 무죄](https://www.acmicpc.net/problem/16467)

<br>
<br>

# **💻Code**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    static long MOD = 100_000_007;

    public static void main(String[] args) throws Exception {

        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());
        while (T-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            int K = Integer.parseInt(st.nextToken());
            int N = Integer.parseInt(st.nextToken());


            if (K == 0) {   // 2의 N제곱을 MOD 연산함
                sb.append(power(N)).append("\n");
            } else if (N <= K) {    // K일이 안지나서 첫번째 알도 안태어남
                sb.append(1).append("\n");
            } else if (N == K + 1) {    // K일째라 첫번째 알만 태어남
                sb.append(2).append("\n");
            } else if (N == K + 2) {    // K+1일째라 병아리 2마리 태어남
                sb.append(3).append("\n");
            } else {
                int size = K + 2;
                int power = N - K - 2;

                // 2차원 행렬
                long[][] base = new long[size][size];

                base[size - 1][1] = 1;
                base[size - 1][size - 1] = 1;

                for (int i = 0; i < size - 1; i++) {
                    base[i][i + 1] = 1;
                }


                long[][] result = new long[size][size];

                result[size - 1][0] = 3;
                result[size - 2][0] = 2;

                for (int i = 0; i < size - 2; i++) {
                    result[i][0] = 1;
                }

                base = matrixPower(base, size, power);  // 행렬 거듭제곱
                result = matrixMultiplication(base, result, size);

                sb.append(result[size - 1][0]).append("\n");
            }
        }


        bw.write(sb.toString());

        bw.flush();
        bw.close();
        br.close();
    }

    static long power(long b) { // 2의 b제곱 MOD 연산
        long a = 2;
        long result = 1;
        while (b != 0) {
            if (b % 2 == 1) {
                result = (result * a) % MOD;
            }
            a = (a * a) % MOD;
            b /= 2;
        }
        return result;
    }

    static long[][] matrixMultiplication(long[][] a, long[][] b, int size) {    // 행렬 곱
        long[][] result = new long[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    long temp = (a[i][k] * b[k][j]) % MOD;
                    result[i][j] = (result[i][j] + temp) % MOD;
                }
            }
        }
        return result;
    }

    static long[][] matrixPower(long[][] base, int size, int power) {   // 행렬 거듭제곱
        long[][] result = new long[size][size];
        for (int i = 0; i < size; i++) {
            result[i][i] = 1;
        }
        while (power != 0) {
            if (power % 2 == 1) {
                result = matrixMultiplication(result, base, size);
            }
            base = matrixMultiplication(base, base, size);
            power /= 2;
        }
        return result;
    }
}

```

<br>
<br>

# **🔑Description**

> 행렬 거듭제곱을 이용하여 오른쪽 행렬의 차수를 줄여나갔다.\
> ppt 참고하기

<br>
<br>

# **📑Related Issues**

> 행렬 곱 헷갈려서 다시 찾아봤다....\
> 그리고 거듭제곱을 이용한다는걸 떠올리는게 좀 힘들었다\
> 피보나치처럼 생겼는데 O(n)보다 적게 걸릴수 있나?? 싶었음... 병아리 이즈 킬링 미...🐣ㅇ<-< \
> 비슷한 문제 -> https://www.acmicpc.net/problem/17272

<br>
<br>

# **🕛Resource**

| Memory  | Time  |
| ------- | ----- |
| 15212KB | 172ms |
