![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:FF658D,100:FFCB32&text=BOJ%2023059&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 23059 리그 오브 레게노](https://www.acmicpc.net/problem/23059)

<br>
<br>

# **💻Code**

```java
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
```

<br>
<br>

# **🔑Description**

> 더 이상 아이템을 구매하기 위해서 필요한 이전 아이템이 없을 경우 아이템을 구매할 수 있기 때문에 위상 정렬을 떠올려서 문제를 풀었습니다.
> Map에 이 아이템을 구매해야만 구매할 수 있는 아이템들을 nextItems List에, 이 아이템을 구매하기 위해 필요한 아이템들의 남은 개수를 needNum에 저장하여 needNum이 0이 되었을 때 해당 아이템의 nextItems List의 아이템들의 needNum을 1씩 감소시켜주고, 아이템을 출력하는 방식으로 문제를 풀이하였습니다.

<br>
<br>

# **📑Related Issues**

> 그래프의 Index를 Integer(숫자) 값이 아니라 String으로 사용해야 해서 Map을 통해서 Index를 나누려는 과정이 구현하기 다소 어려웠습니다.

<br>
<br>

# **🕛Resource**

| Memory   | Time   |
| -------- | ------ |
| 153904KB | 2688ms |
