![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%202533&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 문제번호 문제이름](https://www.acmicpc.net/problem/2533)

<br>
<br>

# **Code**

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class BOJ_2533 {
    // 그래프 정보 인접리스트로 저장
    static List<List<Integer>> node = new ArrayList<>();
    // 얼리어답터인지 저장
    static boolean[] earlyAdapter;
    // 트리 탐색에 사용할 visited 배열
    static boolean[] visited;

    public static boolean findEarlyAdapter(int num) {
        // visited 배열을 사용하여 graph를 tree처럼 사용하게끔 함
        visited[num] = true;

        // flag가 true이면 자식 노드들이 모두 얼리 어답터
        // 자식 노드가 없는 경우도 포함
        boolean flag = true;
        for (int i = 0; i < node.get(num).size(); i++) {
            // 방문한 node이면 탐색을 진행하지 않음
            if (visited[node.get(num).get(i)])
                continue;
            // 인접 노드에 대해서 얼리어답터 여부 확인
            // 자식 노드중 하나라도 얼리 어답터가 아니라면 num 노드는 얼리어답터 이어야 함
            if (!findEarlyAdapter(node.get(num).get(i))) {
                flag = false;
            }
            visited[node.get(num).get(i)] = true;
        }

        // flag 값에 따라서 earlyAdapter 여부를 설정하고 함수 리턴
        if (!flag) {
            return earlyAdapter[num] = true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int edgeNum = Integer.parseInt(br.readLine());
        earlyAdapter = new boolean[edgeNum + 1];
        visited = new boolean[edgeNum + 1];

        for (int i = 0; i <= edgeNum; i++) {
            node.add(new ArrayList<>());
        }

        for (int i = 0; i < edgeNum - 1; i++) {
            // 양방향 그래프로 우선 인접리스트에 저장
            StringTokenizer st = new StringTokenizer(br.readLine());
            int parent = Integer.parseInt(st.nextToken());
            int child = Integer.parseInt(st.nextToken());
            node.get(parent).add(child);
            node.get(child).add(parent);
        }

        findEarlyAdapter(1);

        int cnt = 0;
        for (int i = 1; i <= edgeNum; i++) {
            if (earlyAdapter[i])
                cnt++;
        }

        System.out.println(cnt);
    }
}
```

<br>
<br>

# **🔑Description**

> 트리를 리프노드까지 탐색하여 리프노드들은 얼리어답터가 아닌 것으로 설정
> 노드의 자식 노드 중 하나라도 얼리어답터가 아니라면 현재 노드는 얼리어답터이도록 풀이

<br>
<br>

# **📑Related Issues**

> 트리를 리프 노드에서부터 위로 올라가면서 얼리어답터 여부를 설정하는 과정이 어려웠습니다.

<br>
<br>

# **🕛Resource**

| Memory   | Time   |
| -------- | ------ |
| 404164KB | 1884ms |
