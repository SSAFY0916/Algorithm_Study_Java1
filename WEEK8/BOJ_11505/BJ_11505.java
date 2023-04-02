//package daily.bj_11505;
//설명
//https://gom20.tistory.com/200
//코드

import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static int MOD = 1000000007;
    static long[] arr, tree;

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        arr = new long[N + 1];
        for (int i = 1; i <= N; i++) {
            arr[i] = Long.parseLong(br.readLine());
        }

//		int k = (int) Math.ceil(Math.log(N) / Math.log(2)) + 1;
//		int size = (int) Math.pow(2, k);
//
//		tree = new long[size];

        tree = new long[N * 4];

        init(1, N, 1);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < M + K; i++) {
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            long c = Long.parseLong(st.nextToken());

            if (a == 1) {
                arr[b] = c;
                update(1, N, 1, b, c);
            } else if (a == 2) {
                sb.append(mul(1, N, 1, b, (int) c) + "\n");
            }
        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    // start: 시작 인덱스, end: 끝 인덱스
    public static long init(int start, int end, int node) {
        if (start == end) {
            return tree[node] = arr[start];
        }

        int mid = (start + end) / 2;

        // 재귀적으로 두 부분으로 나눈 뒤에 그 합을 자기 자신으로 함.
        return tree[node] = (init(start, mid, node * 2) * init(mid + 1, end, node * 2 + 1)) % MOD;
    }

    // start: 시작 인덱스, end: 끝 인덱스
    // left, right: 구간 합을 구하고자 하는 범위
    public static long mul(int start, int end, int node, int left, int right) {
        // 범위 밖에 있는 경우
        if (left > end || right < start) {
            return 1;
        }

        // 범위 안에 있는 경우
        if (left <= start && end <= right) {
            return tree[node];
        }

        // 그렇지 않다면, 두 부분으로 나누어 합을 구하기
        int mid = (start + end) / 2;
        return (mul(start, mid, node * 2, left, right) * mul(mid + 1, end, node * 2 + 1, left, right)) % MOD;
    }

    // start: 시작 인덱스, end: 끝 인덱스
    // idx: 구간 합을 수정하고자 하는 노드
    // dif: 수정할 값
    public static long update(int start, int end, int node, int idx, long val) {
        // arr[idx]를 x라 하자.
        // tree에서 값이 x인 인덱스를 target이라고 할 때,
        // target과 연결된 가지 부분을 전체 갱신해야 함.

        // 범위 밖에 있는 경우
        if (idx < start || idx > end) {
            return tree[node];
        }

        // 리프 노드 업데이트
        if (start == end) {
            return tree[node] = val;
        }

        int mid = (start + end) / 2;
        return tree[node] = (update(start, mid, node * 2, idx, val) * update(mid + 1, end, node * 2 + 1, idx, val))
                % MOD;
    }

}