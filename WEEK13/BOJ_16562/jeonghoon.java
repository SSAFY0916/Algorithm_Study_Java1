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
