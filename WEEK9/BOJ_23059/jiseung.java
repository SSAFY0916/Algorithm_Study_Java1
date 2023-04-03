import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

// 04 03 11 07
// 04 03 11 47
public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        int n = Integer.parseInt(br.readLine());
        int[] beforeCount = new int[n*2]; // 아이디가 i인 아이템을 사기 위해 필요한 선행아이템의 수를 저장하는 배열, 아이템간의 관계가 n개니까 아이템이 최대 2n개 등장
        List<Integer>[] afterItems = new ArrayList[n*2]; // 아이디가 i인 아이템을 사면 살 수 있는 아이템의 아이디를 저장하는 리스트
        for (int i = 0; i < n*2; i++) {
            afterItems[i] = new ArrayList<>();
        }
        String[] id2name = new String[n*2]; // 아이디가 i인 아이템의 이름
        Map<String, Integer> name2id = new HashMap<>(); // 아이템의 이름을 키로 아이디를 밸류로 하는 맵
        int count = -1; // 아이템 개수
        for(int i=0; i<n; i++) {
            st = new StringTokenizer(br.readLine());
            String name1 = st.nextToken();
            String name2 = st.nextToken();
            if(!name2id.containsKey(name1)) { // 처음 등장하는 아이템이면 등록시키기
                name2id.put(name1, ++count);
                id2name[count] = name1;
            }
            if(!name2id.containsKey(name2)) {
                name2id.put(name2, ++count);
                id2name[count] = name2;
            }
            beforeCount[name2id.get(name2)]++; // 선행 아이템수 1 증가
            afterItems[name2id.get(name1)].add(name2id.get(name2)); // 다음 아이템 추가
        }

        StringBuilder sb = new StringBuilder();
        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.comparing((Integer o) -> id2name[o])); // 이름순으로 정렬하여 아이디를 저장하는 우선순위 큐
        for (int i = 0; i < n * 2; i++) {
            if (id2name[i] != null && beforeCount[i] == 0) { // 등장한 아이템이고 선행아이템이 없는 아이템을 우선순위큐에 추가
                pq.add(i);
            }
        }
        while(true) { // 살 수 있는 아이템을 한번에 구매하기 위한 while문
            if(pq.isEmpty()) break; // 더이상 살 아이템이 없으면 break

            PriorityQueue<Integer> newPQ = new PriorityQueue<>(Comparator.comparing((Integer o) -> id2name[o])); //한번에 구매하면서 새롭게 살 수 있어진 아이템을 저장할 우선순위큐
            while (!pq.isEmpty()) {
                int cur = pq.poll();
                count--; // 모든 아이템 구매 가능여부 판단을 위해 1 감소
                beforeCount[cur] = -1;
                sb.append(id2name[cur]).append("\n");
                for (Integer id : afterItems[cur]) {
                    beforeCount[id]--;
                    if (beforeCount[id] == 0) { // 새롭게 살수 있어지면 우선순위 큐에 추가
                        newPQ.add(id);
                    }
                }
            }
            pq = newPQ; // 우선순위 큐 변경
        }

        if(count != -1) { // count의 초기값인 -1이 아니면 모든 아이템을 구매하지 못했으므로 -1 출력
            bw.write(-1 + "\n");
        }else {
            bw.write(sb.toString());
        }
        bw.flush();
        bw.close();
        br.close();
    }
}