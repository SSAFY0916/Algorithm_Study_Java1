![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%203151&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 3151_합이0](https://www.acmicpc.net/problem/3151)

<br>
<br>

# **Code**

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[] arr = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(arr);

        long cnt = 0;
        // 두수의 합 구하고 나머지 수가 리스트에 있는지 확인
        // 중복된 수의 수 구하기 (ub-lb)
        for (int i = 0; i < N - 2; ++i) {
            for (int j = i + 1; j < N - 1; ++j) {
                int key = -(arr[i] + arr[j]);
                int l = lowerBound(j + 1, arr, key);

                if (l == N || arr[l] != key) continue;

                int r = upperBound(j + 1, arr,  key);
                cnt += r - l;
            }
        }
        System.out.println(cnt);

    }
    private static int lowerBound(int st, int[] arr, int target) {
        int len = arr.length;
        int en = len;
        while(st<en) {
            int mid = (st+en)/2;
            if (arr[mid] < target) st = mid+1;
            else en = mid;
        }
        return st;

    }

    private static int upperBound(int st, int[] arr, int target) {
        int len = arr.length;
        int en = len;
        while(st<en) {
            int mid = (st+en)/2;
            if (arr[mid] <= target) st = mid+1;
            else en = mid;
        }
        return st;
    }
}
```

<br>
<br>

# **🔑Description**

> 설계 시간: 30min

> 구현 시간: 1hr+a
<aside>
💡 설계 아이디어
    
    '세 수의 합' 문제와 '숫자카드' 응용 문제
    두 수의 합을 먼저 구한 뒤, 그 값의 음수값이 리스트 안에 들어있는지 확인하는 이분탐색 수행!
    이 때, 같은 값이 여러 개 있을 수 있으므로 upper bound - lower bound로 중복 갯수를 처리해준다. 
    

</aside>

<br>
<br>

# **📑Related Issues**

> Related Issues
<aside>

    - 이분탐색이 어렵긴한데 이 문제 풀기 위해서 여러가지 이분탐색 유형을 정리해봤더니 유형이 패턴화 되어있는 것 같다.
    - 그래도 여전히 어려움

</aside>

<br>
<br>

# **🕛Resource**

| Memory | Time  | Implementation Time |
| -- |-------|---------------------|
| 16988KB | 2496ms | 1 Hour  |
