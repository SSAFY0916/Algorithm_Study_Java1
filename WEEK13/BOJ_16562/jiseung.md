![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2016562&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> BOJ 16562 친구비

# 💻**Code**

```java
import java.io.*;
import java.util.*;
import java.util.stream.IntStream;

public class Main {

    static int[] fees, parents;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        fees = new int[n]; // 친구비를 저장하는 배열
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            fees[i] = Integer.parseInt(st.nextToken());
        }
        parents = new int[n]; // 유니온 파인드에서 사용할 조상 노드번호를 저장하는 배열
        for (int i = 0; i < n; i++) {
            parents[i] = i;
        }
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) -1;
            int b = Integer.parseInt(st.nextToken()) -1;
            if (a > b) { // 무조건 a <= b하게 swap
                int temp = a;
                a = b;
                b = temp;
            }
            if(!isUnion(a, b)) { // 같은 유니온이 아니면
                unify(a, b); // 유니온으로 만들기
            }
        }
        int answer = IntStream.range(0, n)
                .filter(i -> parents[i] == i) // 루트 노드만
                .map(i -> fees[i]) // 친구비
                .sum(); // 더하기

        if (answer <= k)
            bw.write(answer + "\n");
        else
            bw.write("Oh no"); // 돈 부족
        bw.flush();
        bw.close();
        br.close();
    }

    static int getRoot(int a) { // 루트 노드 찾기
        if (parents[a] == a) {
            return a;
        }
        return parents[a] = getRoot(parents[a]); // 찾으면서 최대한 조상으로 갱신
    }

    static boolean isUnion(int a, int b) { // 루트 노드가 같은지 비교해서 같은 유니온인지 비교
        return getRoot(a) == getRoot(b);
    }

    static void unify(int a, int b) {
        fees[getRoot(a)] = Math.min(fees[getRoot(a)], fees[getRoot(b)]); // 친구비를 a와 b 중에서 더 작은 값으로 변경
        parents[getRoot(b)] = getRoot(a); // b를 a에 연결
    }
}
```

# **🔑Description**

> 친구의 친구도 친구이므로 친구들끼리는 유니온으로 연결하고 각 유니온에서 최소 친구비를 계산했다\
> 친구비는 유니온을 연결할 때 둘 중 더 작은 값을 루트노드에 저장하고 루트노드들의 친구비를 합쳐서 계산한다

# **📑Related Issues**

> 유니온 파인드하면서 친구비를 계산하는 방법이 어려웠다\
> 스트림도 사용해봤다

# **🕛Resource**

| Memory    | Time    |
| --------- | ------- |
| 19576`KB` | 260`ms` |
