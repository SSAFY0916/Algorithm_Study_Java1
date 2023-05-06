![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:FF658D,100:FFCB32&text=BOJ%2016562&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 16562 친구비](https://www.acmicpc.net/problem/16562)

<br>
<br>

# **💻Code**

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_16562 {
    static int sNum, rNum, money;
    static int[] fMoney;
    static int[] parents;

    public static int find(int a) {
        if (a == parents[a])
            return a;
        return parents[a] = find(parents[a]);
    }

    public static void union(int a, int b) {
        a = find(a);
        b = find(b);
        if (a != b) {
            if (fMoney[a] > fMoney[b])
                parents[a] = b;
            else
                parents[b] = a;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        sNum = Integer.parseInt(st.nextToken());
        rNum = Integer.parseInt(st.nextToken());
        money = Integer.parseInt(st.nextToken());

        fMoney = new int[sNum + 1];
        parents = new int[sNum + 1];

        // 0의 친구비(나 자신)는 무한대로 갱신
        fMoney[0] = Integer.MAX_VALUE;
        for (int i = 1; i <= sNum; i++) {
            parents[i] = i;
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= sNum; i++) {
            fMoney[i] = Integer.parseInt(st.nextToken());
        }

        // 입력받은 친구들을 Union 함수를 통해서 묶어줌
        for (int i = 0; i < rNum; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            union(a, b);
        }

        int res = 0;
        // 모든 학생들에 대해서 union find 진행
        for (int i = 1; i <= sNum; i++) {
            // Union 되어있지 않다면 친구비를 입금
            if (find(i) != find(0)) {
                res += fMoney[find(i)];
                union(i, 0);
            }
        }

        // 가진 돈으로 친구비를 모두 낼 수 있다면 출력
        if (money >= res)
            System.out.println(res);
        // 그렇지 않다면 친구를 모두 사귈 수 없는 경우
        else
            System.out.println("Oh no");
    }
}

```

<br>
<br>

# **🔑Description**

> 유니온 파인드의 기준을 작은 인덱스가 아닌, 친구비가 싼 비용이 부모가 되도록 머지하는 방식을 사용해서 풀이하였습니다.

<br>
<br>

# **📑Related Issues**

>

<br>
<br>

# **🕛Resource**

| Memory  | Time  |
| ------- | ----- |
| 18672KB | 220ms |
