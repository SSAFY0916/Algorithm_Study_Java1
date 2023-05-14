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
