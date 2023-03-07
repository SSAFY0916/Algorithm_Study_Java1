![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2014267&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 문제번호 문제이름](https://www.acmicpc.net/problem/14267)

<br>
<br>

# **Code**

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class BOJ_14267 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int peopleNum = Integer.parseInt(st.nextToken());
        int compNum = Integer.parseInt(st.nextToken());

        // 부하 직원을 저장하기 위한 2차원 가변 배열 초기화
        List<List<Integer>> subordinate = new ArrayList<>();
        for (int i = 0; i <= peopleNum; i++) {
            subordinate.add(new ArrayList<>());
        }

        st = new StringTokenizer(br.readLine());
        // 사장은 상사가 없으므로 한개의 토큰 날리기
        st.nextToken();

        // 2번부터 입력받은 상사의 가변배열 위치에 본인의 번호를 추가
        for (int i = 2; i <= peopleNum; i++) {
            int superior = Integer.parseInt(st.nextToken());
            subordinate.get(superior).add(i);
        }

        int[] dp = new int[peopleNum + 1];

        // 상사로부터 받은 칭찬을 저장하기
        for (int i = 0; i < compNum; i++) {
            st = new StringTokenizer(br.readLine());
            int num = Integer.parseInt(st.nextToken());
            int amount = Integer.parseInt(st.nextToken());

            dp[num] += amount;
        }

        // 상사로부터 자신의 직속 부하들에게 자신이 지금까지 받은 칭찬을 내리칭찬
        for (int i = 1; i <= peopleNum; i++) {
            for (int tmp : subordinate.get(i)) {
                dp[tmp] += dp[i];
            }
        }

        for (int i = 1; i <= peopleNum; i++) {
            System.out.print(dp[i] + " ");
        }
    }

}
```

<br>
<br>

# **🔑Description**

> 2차원 리스트를 이용하여 각 직원들 번호의 리스트에 직속 부하들의 번호를 저장하였습니다.
> dp[i] = dp[i] + dp[i - 1] 의 점화식을 사용하여 내가 기존에 상사로 부터 받은 칭찬 + 지금까지 상사가 받은 칭찬을 더해가는 방식을 사용하여 풀이하였습니다.

<br>
<br>

# **📑Related Issues**

> 처음에는 상사가 부하의 번호를 저장하는 방법이 아닌 부하가 상사의 번호를 저장해 두는 방식을 사용하고자 하였으나, 편향트리의 모양으로 관계도가 그려지는 경우에 시간초과가 나올 것 같아서 다른 방법으로 풀이하였습니다.

<br>
<br>

# **🕛Resource**

| Memory | Time   |
| ------ | ------ |
| 77376KB | 1536ms |