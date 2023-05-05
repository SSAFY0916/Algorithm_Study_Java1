import java.io.*;
import java.util.*;

public class Main {

    static int[] nums; // 수들을 저장하는 배열
    static long[] tree; // 세그먼트 트리
    static int n, q;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        q = Integer.parseInt(st.nextToken());
        nums = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            nums[i] = Integer.parseInt(st.nextToken());
        }
        tree = new long[n*4]; // 세그먼트 트리를 수의 개수 * 4로 초기화
        init(0, n - 1, 1);
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            if (y < x) { // x가 구간의 왼쪽 끝, y가 구간의 오른쪽 끝이 되도록 swap
                int temp = x;
                x = y;
                y = temp;
            }
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            bw.write(query(0, n-1, 1, x-1, y-1) + "\n"); // x-1 ~ y-1까지의 합 구하기
            update(0, n-1, 1, a-1, b); // a-1번째 수를 b로 변경
        }
        bw.flush();
        bw.close();
        br.close();
    }

    // 트리를 초기화하는 메소드, tree[node]는 start번째 수부터 end번째 수까지의 합이다
    static long init(int start, int end, int node) {
        if (start == end) { // 구간이 하나의 숫자만을 포함할 때
            return tree[node] = nums[start]; // 해당 숫자를 tree[node]에 대입
        }
        int mid = (start + end) / 2; // 현재 다루는 구간을 두 구간으로 나누기 위한 구간의 중간지점
        return tree[node] = init(start, mid, node * 2) + init(mid + 1, end, node * 2 + 1); // 왼쪽 구간과 오른쪽 구간으로 분할하고 각각의 값을 더한 값을 현재 값으로 한다
    }

    // index번째 수를 value로 갱신하는 메소드
    static long update(int start, int end, int node, int index, int value) {
        if (index < start || end < index) { // index번째 수가 현재 구간(start~end)에 포함되지 않으면
            return tree[node]; // 갱신할 필요 없으니까 현재 값 리턴
        }
        if (start == end) { // start~end 구간이 하나의 수만 포함할 때, 구간에 포함되지 않는 경우는 다 위에 있는 조건문에서 걸렀으므로 현재 구간에 있는 수는 갱신할 수이다
            return tree[node] = value;
        }
        int mid = (start + end) / 2;
        return tree[node] = update(start, mid, node * 2, index, value) + update(mid + 1, end, node * 2 + 1, index, value);
    }

    // left부터 right까지의 합을 구하는 메소드
    static long query(int start, int end, int node, int left, int right) {
        if (right < start || end < left) { // 현재 구간은 원하는 구간에 포함되지 않으면
            return 0; // 더해도 결과가 변하지 않는 0 반환
        }
        if (left <= start && end <= right) { // 원하는 구간이 현재 구간 전체를 포함하면
            return tree[node]; // 현재 값 반환
        }
        int mid = (start + end) / 2;
        return query(start, mid, node * 2, left, right) + query(mid + 1, end, node * 2 + 1, left, right);
    }
}