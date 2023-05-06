![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:31BCFF,100:A066F9&text=BOJ%2016562&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 16562 친구비](https://www.acmicpc.net/problem/16562)

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

    static int[] parent, price;

    public static void main(String[] args) throws Exception {

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        price = new int[N + 1]; // 친구 비용
        parent = new int[N + 1];    // 부모 노드 번호 저장
        boolean[] check = new boolean[N + 1];   // 돈 지불 상태 check

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            price[i] = Integer.parseInt(st.nextToken());
            parent[i] = i;
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            union(a, b);    // union 연산
        }

        for (int i = 1; i <= N; i++) {  // 모든 노드의 depth 1로 맞춰주기
            find(i);
        }

        int money = k;  // 가지고 있는 돈

        for (int i = 1; i <= N; i++) {
            if (check[parent[i]])   // i의 부모노드의 비용 이미 지불했으면 건너 뛰기
                continue;

            check[parent[i]] = true; // i의 부모노드의 비용 지불
            k -= price[parent[i]];  // 잔액

            if (k < 0)  // 잔액이 음수면 돈 못 낸것이므로 break;
                break;
        }

        if (k < 0)  // 모든 사람과 친구가 될 수 없음
            bw.write("Oh no");
        else
            bw.write(Integer.toString(money - k));  // 친구로 만드는데 드는 비용

        bw.flush();
        bw.close();
        br.close();
    }

    static void union(int a, int b) {   // union 함수

        int pa = find(a);
        int pb = find(b);

        if (pa == pb)
            return;

        if (price[pa] < price[pb])  // 비용 적은쪽이 부모가 되도록 함
            parent[pb] = pa;
        else if (price[pa] > price[pb])
            parent[pa] = pb;
        else {  // 비용 같으면 노드번호가 작은 쪽이 부모가 되도록 함
            if (pa < pb)
                parent[pb] = pa;
            else
                parent[pa] = pb;
        }
    }

    static int find(int a) {    // find 함수

        if (parent[a] == a)
            return a;

        return parent[a] = find(parent[a]);
    }

}

```

<br>
<br>

# **🔑Description**

> 친구의 친구는 친구라고 해서 한 그룹에서 제일 비용이 적은 사람을 친구로 만드는 방식으로 했다.\
> 그룹을 만드는 방식은 union-find를 사용했다.\
> 두 사람의 비용이 다르다면 비용이 적은쪽이, 비용이 같다면 노드번호가 적은쪽이 부모노드가 되도록 했다.

<br>
<br>

# **📑Related Issues**

> depth가 1을 초과하는 경우가 있어서 union 연산 후에 모든 노드에 대해 find를 돌려서 depth가 1 이하가 되도록 맞춰줬다.

<br>
<br>

# **🕛Resource**

| Memory  | Time  |
| ------- | ----- |
| 18476KB | 188ms |
