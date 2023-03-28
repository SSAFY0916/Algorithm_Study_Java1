![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%201300&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> BOJ 1300 K번째 수

# 💻**Code**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        long n = Integer.parseInt(br.readLine());
        long k = Integer.parseInt(br.readLine());

        // 1부터 n*n 사이를 이분탐색
        long left = 1;
        long right = n*n;
        while(left < right) {
            long mid = (left + right) / 2;
            long count = 0; // A 에 있는 mid 이하 숫자들의 개수
            for (int i = 1; i <= n; i++) {
                if (mid < i) break; // 모두 mid 보다 클테니까 그만
                count += Math.min(n, mid / i); // i 번째 열에서 mid 이하의 숫자의 개수
            }
            if(count < k) { // 원하는 숫자가 더 크다. 원하는 숫자보다 적다.
                left = mid+1;
            }else { // 원하는 숫자가 더 작다. 원하는 숫자보다 많다.
                right = mid;
            }
        }

        bw.write(left + "\n");
        bw.flush();
        bw.close();
        br.close();
    }
}
```

# **🔑Description**

> 1부터 숫자를 1씩 증가해가면서 A 배열에 등장하는 개수를 구해서 총 개수가 k가 될 때까지 반복하려 했다.\
> 등장하는 개수는 약수의 개수와 같다고 생각했는데 A 배열에 없는 경우가 있을 수 있어서 하나하나 직접 나누어 떨어지는지 보면서 개수를 세야 했다.\
> 시간이 오래 걸려서 고민했는데 모르겠어서 겁쟁이의 쉼터를 들렸다.\
> A 에는 1부터 n\*n까지의 수가 올 수 있고 1부터 n\*n까지 이분 탐색한다.\
> 이분탐색은 각 열에서 현재 탐색하는 숫자보다 작은 수의 개수를 새고 총 개수를 k와 비교하여 다음 탐색 구간을 조정했다.\
> 이분탐색하는 구간이 n*n이고 탐색마다 n개의 열을 계산하니까 `nlog(n*n) = nlogn` 정도 걸린 것 같다.

# **📑Related Issues**

> lower랑 upper랑 헷갈린다\
> 이분탐색인걸 생각하기 힘들다

# **🕛Resource**

| Memory    | Time    |
| --------- | ------- |
| 14372`KB` | 172`ms` |
