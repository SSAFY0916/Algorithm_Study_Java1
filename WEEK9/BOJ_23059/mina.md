![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:FF658D,100:FFCB32&text=BOJ%2023059&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 23059 리그 오브 레게노](https://www.acmicpc.net/problem/23059)

<br>
<br>

# **💻Code**

```java
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
```

<br>
<br>

# **🔑Description**

> 아이템 이름과 노드 번호를 매핑하는데에 HashMap과 ArrayList를 사용하였다.\
> 아이템을 구매할 때에는 위상 정렬을 사용하였다.\
> 사전 순으로 뽑기 위해 Priority Queue를 사용하였다.\
> 현재 구매할 수 있는 아이템을 모두 구매해야 하므로 구매할 수 있는 아이템을 List에 먼저 저장한 후에 PQ에 한번에 집어넣었다.

<br>
<br>

# **📑Related Issues**

> 아이템 이름-노드 번호 매핑하는게 조금 머리아팠다.\
> HashMap만으로 해보려다가 그냥 ArrayList도 추가해서 사용했다.\
> 그리고 위상정렬에서도 PQ에 바로바로 아이템을 집어넣었었는데 현재 구매할 수 있는 아이템을 한번에 구매해야 한다고 해서 List에 쌓아놓고 PQ에 한번에 집어 넣는 방식으로 변경했다.

<br>
<br>

# **🕛Resource**

| Memory   | Time   |
| -------- | ------ |
| 179480KB | 1968ms |
