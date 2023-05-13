![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%201275&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 1275_커피숍2](https://www.acmicpc.net/problem/1275)

<br>
<br>

# **Code**

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Main {
    static long[] tree;
    static int n;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();
        n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        int treeHeight = (int) Math.ceil(Math.log(n) / Math.log(2));
        int treeSize = (int) Math.pow(2, treeHeight + 1);
        tree = new long[treeSize];
        long[] arr = new long[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        init(arr, 1, 0, n - 1);

        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken()) - 1;
            int y = Integer.parseInt(st.nextToken()) - 1;
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken());
            if (x > y) {
                int temp = x;
                x = y;
                y = temp;
            }
            sb.append(sum(1, 0, n - 1, x, y)).append('\n');
            update(1, 0, n - 1, a, b - arr[a]);
            arr[a] = b;
        }
        System.out.println(sb);
    }

    private static long init(long[] arr, int node, int start, int end) {
        if (start == end) {
            return tree[node] = arr[start];
        }
        int mid = (start + end) / 2;
        return tree[node] = init(arr, node * 2, start, mid) + init(arr, node * 2 + 1, mid + 1, end);
    }

    private static long sum(int node, int start, int end, int left, int right) {
        if (right < start || end < left) {
            return 0;
        }
        if (left <= start && end <= right) {
            return tree[node];
        }
        int mid = (start + end) / 2;
        return sum(node * 2, start, mid, left, right) + sum(node * 2 + 1, mid + 1, end, left, right);
    }

    private static void update(int node, int start, int end, int idx, long diff) {
        if (idx < start || end < idx) {
            return;
        }
        tree[node] += diff;
        if (start != end) {
            int mid = (start + end) / 2;
            update(node * 2, start, mid, idx, diff);
            update(node * 2 + 1, mid + 1, end, idx, diff);
        }
    }
}

```

<br>
<br>

# **🔑Description**


<aside>
💡 설계 아이디어
    
    세그먼트 트리!
    - 아직도 처음부터 끝까지 안보고 코드 치기는 어려운 것 같다.. 기억이 안나서 구현 방식 코드를 조금 찾아봤다 

</aside>

<br>
<br>

# **📑Related Issues**

> Related Issues
<aside>

    세그먼트 트리 기본문제인 것 같은데.. 함정과 실수 때문에 여러번 틀렸다.
    1. 문제 <노트>를 잘 보자. == 문제를 잘 읽자. 
        - x,y가 당연히 오름차순으로 주어지는줄 알았다.
    2. int, long 때문에 틀렸었다.. 
        - 근데 배열 값은 int 범위라고 문제에서 주어졌는데 왜 long으로 해야 맞는건지는 아직도 모르겠다.

</aside>

<br>
<br>

# **🕛Resource**

| Memory | Time  | Implementation Time |
| -- |-------|---------------------|
| 78116KB | 852s |  |
