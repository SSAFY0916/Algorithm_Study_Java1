import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_23059 {

    // 해당 아이템의 정보를 저장
    static class Info {
        // 아이템을 구매하기 위해서 더 구매해야 하는 아이템의 개수
        int needNum;
        // 이 아이템을 구매하였을 때 구매와 연관된 아이템을 저장
        List<String> nextItems;

        public Info() {
            needNum = 0;
            nextItems = new ArrayList<>();
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int inputNum = Integer.parseInt(br.readLine());

        // map
        Map<String, Info> map = new HashMap<>();

        for (int i = 0; i < inputNum; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            String baseItem = st.nextToken();
            String combItem = st.nextToken();

            // baseItem과 combItem이 map에 아직 저장되지 않은 경우 저장
            if (!map.containsKey(baseItem)) {
                map.put(baseItem, new Info());
            }
            if (!map.containsKey(combItem)) {
                map.put(combItem, new Info());
            }
            // baseItem의 경우 combItem을 nextItems 리스트에 저장
            map.get(baseItem).nextItems.add(combItem);
            // combItem은 baseItem을 구매하여야지만 구매할 수 있으므로 needNum수 1 증가
            map.get(combItem).needNum++;
        }

        // String을 사전 순으로 출력하기 위하여 PQ 사용
        Queue<String> pq = new PriorityQueue<>();
        // map에 저장되어 있는 아이템들 중 어떠한 아이템을 구매하지 않더라도 구매할 수 있는 아이템들을 PQ에 add
        for (String str : map.keySet()) {
            if (map.get(str).needNum == 0) {
                pq.add(str);
                // 이 아이템은 더 이상 출력되면 안되기 때문에 needNum을 -1로 설정
                map.get(str).needNum = -1;
            }
        }

        // 모든 아이템을 구매하였는지 판단하기 위한 변수
        int cnt = 0;

        // 현재 PQ에 있는 아이템들을 우선적으로 출력하기 위하여 2중 반복문 사용
        while (!pq.isEmpty()) {
            // 다음 번에 출력해야 하는 아이템들을 저장해 놓기 위한 임시 PQ
            Queue<String> tmpPq = new PriorityQueue<>();
            int size = pq.size();
            while (size --> 0) {
                String tmp = pq.poll();
                sb.append(tmp).append('\n');
                cnt++;
                // 출력한 아이템의 nextItems들의 needNum 1씩 감소
                for (int i = 0; i < map.get(tmp).nextItems.size(); i++) {
                    map.get(map.get(tmp).nextItems.get(i)).needNum -= 1;
                    // 이 때 nextItems의 needNum이 0이 되었다면 tmpPq에 넣은 뒤 needNum을 -1로 갱신
                    if (map.get(map.get(tmp).nextItems.get(i)).needNum == 0) {
                        tmpPq.add(map.get(tmp).nextItems.get(i));
                        map.get(map.get(tmp).nextItems.get(i)).needNum = -1;
                    }
                }
            }
            pq.addAll(tmpPq);
        }
        
        // 모든 아이템을 구매할 수 있는지 판단 후 출력
        System.out.println(cnt != map.size() ? -1 : sb.toString());
    }
}