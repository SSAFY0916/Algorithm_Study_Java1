import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class Main {

    static long[] nums; // 입력받은 수들을 저장하는 배열
    static long[] tree; // 세그먼트 트리를 저장하는 배열
    static long mod = 1_000_000_007; // 나누는 수

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        nums = new long[n];
        for (int i = 0; i < n; i++) {
            nums[i] = Integer.parseInt(br.readLine());
        }

        tree = new long[n * 4]; // 입력받은 수의 길이의 4배 짜리 배열을 트리로 저장
        init(0, n-1, 1);

        for (int i = 0; i < m + k; i++) {
            st = new StringTokenizer(br.readLine());
            if(st.nextToken().equals("1")) {
                update(0, n - 1, 1, Integer.parseInt(st.nextToken()) - 1, Long.parseLong(st.nextToken()));
            }else {
                long answer = query(0, n - 1, 1, Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1);
                bw.write(answer + "\n");
            }
        }
        bw.flush();
        bw.close();
        br.close();
    }

    // 세그먼트 트리를 초기화하는 메소드
    static long init(int start, int end, int node) { // 트리에서 node번째 인덱스는 start~end의 구간에 대한 초기화를 한다.
        if(start == end) { // start~end 구간이 start~start구간일 때
            return tree[node] = nums[start]; //해당 인덱스에서의 값을 트리의 node번재 인덱스에 저장
        }
        int mid = (start + end) / 2; // 현재 다루는 구간을 두 구간으로 나누기 위한 구간의 중간지점
        return tree[node] = init(start, mid, node * 2) * init(mid+1, end, node * 2 + 1) % mod; // 왼쪽 구간과 오른쪽 구간으로 분할하고 각각의 값을 곱한 값을 현재 인덱스의 값으로 한다.
    }

    // 세그먼트 트리를 갱신하는 메소드
    static long update(int start, int end, int node, int index, long value) { // 트리에서 node번째 인덱스는 start~end구간을 다루고 index번째 수를 value로 바꾼다.
        if(index < start || end < index) { // index번째 수는 현재 구간에 포함되지 않는다.
            return tree[node]; // 갱신할 필요 없으니까 현재 값 리턴
        }
        if(start == end) { // start~end 구간이 start~start구간일 때, 구간에 포함되지 않는 경우는 다 위에 있는 조건문에서 걸렀으므로 index~index 구간이다.
            return tree[node] = value; // 값 갱신
        }
        int mid = (start + end) / 2;
        return tree[node] = update(start, mid, node * 2, index, value) * update(mid+1, end, node*2+1, index, value) % mod;
    }

    // 세그먼트 트리에서 원하는 구간의 값을 가져오는 메소드
    static long query(int start, int end, int node, int left, int right) { // 트리에서 node번째 인덱스는 start~end구간을 다루고 left~right 구간을 구한다.
        if(right < start || end < left) { // 현재 구간은 원하는 구간에 포함되지 않는다.
            return 1;
        }
        if(left <= start && end <= right) { // 현재 구간이 원하는 구간 전체를 포함한다.
            return tree[node]; // 현재값 반환
        }
        int mid = (start + end) / 2;
        return query(start, mid, node*2, left, right) * query(mid+1, end, node*2+1, left, right) % mod; // 두 구간으로 나누어 각각 값을 구하고 곱한다.
    }
}