import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));


    public static void main(String[] args) throws  Exception {

        int N = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        Map<String, Integer> map = new HashMap<String, Integer>();  // 아이템 이름으로 노드 번호 얻어오기
        List<String> node = new ArrayList<>();  // 노드 번호로 아이템 이름 얻어오기
        List<ArrayList<Integer>> nodelist = new ArrayList<>();  // 인접 노드 리스트
        List<Integer> edgeCount = new ArrayList<>();    // 연결된 간선 개수 저장

        // 0번째 데이터 추가
        nodelist.add(new ArrayList<>());
        edgeCount.add(0);
        node.add("");

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            String A = st.nextToken();
            String B = st.nextToken();

            if(!map.containsKey(A)){    // 아이템 처음 들어온 경우 노드 번호 부여
                map.put(A,map.size()+1);
                nodelist.add(new ArrayList<>());
                node.add(A);
                edgeCount.add(0);
            }
            if(!map.containsKey(B)){    // 아이템 처음 들어온 경우 노드 번호 부여
                map.put(B, map.size()+1);
                nodelist.add(new ArrayList<>());
                node.add(B);
                edgeCount.add(0);
            }

            int a = map.get(A);
            int b = map.get(B);

            nodelist.get(a).add(b); // 인접 노드 리스트에 추가
            edgeCount.set(b, edgeCount.get(b) + 1); // 연결된 간선 개수 증가
        }

        PriorityQueue<String> queue = new PriorityQueue<>();    // 아이템 사전 순으로 정렬
        List<String> list = new ArrayList<>();  // 현재 살 수 있는 아이템 저장

        int count = edgeCount.size() - 1;   // 남은 아이템 개수

        // 위상 정렬
        for (int i = 1; i < edgeCount.size(); i++) {
            if(edgeCount.get(i) == 0){  // 아이템을 살 수 있는 경우
                list.add(node.get(i));
            }
        }

        while (!list.isEmpty()) {

            queue.addAll(list); // 현재 살 수 있는 아이템들 사전순으로 정렬
            list.clear();

            while (!queue.isEmpty()) {
                String s = queue.poll();    // 아이템 하나씩 구매
                count--;
                sb.append(s).append("\n");
                int n = map.get(s);
                for (int i = 0; i < nodelist.get(n).size(); i++) {
                    int m = nodelist.get(n).get(i);
                    edgeCount.set(m, edgeCount.get(m) - 1);  // 구매한 아이템의 간선 제거

                    if (edgeCount.get(m) == 0) {    // 다음 아이템 구매할 수 있으면 리스트에 추가
                        list.add(node.get(m));
                    }
                }
                nodelist.get(n).clear();
            }
        }

        if(count == 0)  // 아이템 전부 구매한 경우
            bw.write(sb.toString());
        else    // 아이템을 전부 구매하지 못한 경우
            bw.write(Integer.toString(-1));

        bw.flush();
        bw.close();
        br.close();
    }


}