![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%201253&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 1253_좋다](https://www.acmicpc.net/problem/15824)

<br>
<br>

# **Code**

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            list.add(Integer.parseInt(st.nextToken()));
        }
        Collections.sort(list);
        int cnt = 0;
        for (int i = 0; i < N - 2; i++) {
            for (int j = i+1; j < N - 1; j++) {
                int sum = list.get(i) + list.get(j);
                int target = -sum;

                // lowerBound
                int s = j+1;
                int e = N;
                while(s<e) {
                    int mid = (s+e)/2;
                    if (list.get(mid) >= target) {
                        e = mid;
                    } else {
                        s = mid+1;
                    }
                }
                int lowerIdx = s;

                // upperBound
                s = j+1;
                e = N;
                while(s<e) {
                    int mid = (s+e)/2;
                    if (list.get(mid) > target) {
                        e = mid;
                    } else {
                        s = mid+1;
                    }
                }
                int upperIdx = s;

                if (upperIdx == lowerIdx) continue;

                cnt+= (upperIdx-lowerIdx);
            }
        }
        System.out.println(cnt);
    }
}

```

<br>
<br>

# **🔑Description**


<aside>
💡 설계 아이디어
    
    왜 틀리지?
    upperbound, lowerbound!
    
</aside>

<br>
<br>

# **📑Related Issues**

> Related Issues
<aside>

    왜 틀릴까

</aside>

<br>
<br>

# **🕛Resource**

| Memory | Time  | Implementation Time |
| -- |-------|---------------------|
| KB | ms | 2 Hour + a |
