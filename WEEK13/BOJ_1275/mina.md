![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:31BCFF,100:A066F9&text=BOJ%201275&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 1275 커피숍2](https://www.acmicpc.net/problem/1275)

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

    static long[] nums, tree;

    public static void main(String[] args) throws Exception {

        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        int N = Integer.parseInt(st.nextToken());
        int Q = Integer.parseInt(st.nextToken());

        nums = new long[N + 1]; // input number

        tree = new long[4 * N]; // 세그먼트 트리

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            nums[i] = Long.parseLong(st.nextToken());
        }

        init(1, 1, N);  // 세그먼트 트리 초기화

        for (int i = 0; i < Q; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            if (x > y) {    // x > y 인 경우 x < y로 만들기
                int temp = y;
                y = x;
                x = temp;
            }

            sb.append(query(1, 1, N, x, y)).append("\n");   // x, y 사이의 구간 합 구하기
            update(1, 1, N, a, b);  // a번째 숫자 b로 바꾸기
        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    static void init(int node, int start, int end) {
        if (start == end) { // 리프노드
            tree[node] = nums[start];
        } else {
            init(node * 2, start, (start + end) / 2);   // left child 값 구하기
            init(node * 2 + 1, (start + end) / 2 + 1, end); // right child 값 구하기
            tree[node] = tree[node * 2] + tree[node * 2 + 1];   // left child + right child
        }
    }

    static long query(int node, int start, int end, int left, int right) {
        if (left > end || right < start) {  // left~right가 아예 포함 안됨
            return 0;
        }
        if (left <= start && end <= right) {    // left~right가 포함됨
            return tree[node];
        }

        // 어느정도 걸쳐져 있음 -> 왼쪽/오른쪽 나눠서 구하기
        return query(node * 2, start, (start + end) / 2, left, right) + query(node * 2 + 1, (start + end) / 2 + 1, end, left, right);
    }

    static void update(int node, int start, int end, int index, long val) {
        if (index < start || index > end) { // index가 이 구간에 포함 안됨
            return;
        }
        if (start == end) { // 리프노드 갱신
            tree[node] = val;
            return;
        }

        // index가 이 구간에 포함 됨 -> 왼쪽/오른쪽 나눠서 업뎃하기
        update(node * 2, start, (start + end) / 2, index, val);
        update(node * 2 + 1, (start + end) / 2 + 1, end, index, val);
        tree[node] = tree[node * 2] + tree[node * 2 + 1];
    }


}

```

<br>
<br>

# **🔑Description**

> 세그먼트 트리로 풀었다

<br>
<br>

# **📑Related Issues**

> 구간 곱 구하기 이후에 세그먼트 트리 공부 햇는지 안했는지 확인하려고 낸 문제가 분명함\
> 나도 이제 세그트리 풀수잇다~~~ㅎㅁㅎ

<br>
<br>

# **🕛Resource**

| Memory  | Time  |
| ------- | ----- |
| 91304KB | 832ms |
