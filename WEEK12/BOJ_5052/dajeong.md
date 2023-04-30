![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%205052&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 5052_전화번호목록](https://www.acmicpc.net/problem/5052)

<br>
<br>

# **Code**

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());
        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine());
            String[] phoneNumbers = new String[n];
            for (int i = 0; i < n; i++) {
                phoneNumbers[i] = br.readLine();
            }
            Arrays.sort(phoneNumbers);
            boolean consistent = true;
            for (int i = 0; i < n - 1; i++) {
                if (phoneNumbers[i + 1].startsWith(phoneNumbers[i])) {
                    consistent = false;
                    break;
                }
            }
            if (consistent) {
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }
        }
        br.close();
    }
}

```

<br>
<br>

# **🔑Description**


<aside>
💡 설계 아이디어
    
    - String 배열에 전화번호들을 저장시킨 후 자릿수별 값이 오름차순으로 정렬되게끔 정렬
    - 인접한 두 전화번호 중 앞의 번호가 뒤의 번호의 접두사인지를 검사
    
</aside>

<br>
<br>

# **📑Related Issues**

> Related Issues
<aside>

    - 문제 분류에 트라이라고 적혀있던데.. 트라이를 모르는 상태에서 나름대로 문제를 풀었으나.. 트라이를 공부하는게 좋겠지..?

</aside>

<br>
<br>

# **🕛Resource**

| Memory | Time  | Implementation Time |
| -- |-------|---------------------|
| 34388KB | 568ms |  |
