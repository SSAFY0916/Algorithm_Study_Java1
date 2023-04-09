![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%205972&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> BOJ 5972 택배 배송

# 💻**Code**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int inf = 50000*1000; // 최대 (n-1)*c 까지 비용이 나올 수 있으니까 n*c의 값으로 설정
        List<int[]>[] edges = new ArrayList[n+1]; // 간선을 저장할 리스트
        for (int i = 0; i < n + 1; i++) {
            edges[i] = new ArrayList<>();
        }
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            edges[a].add(new int[]{b, c}); // 양방향
            edges[b].add(new int[]{a, c});
        }

        int[] dists = new int[n+1]; // 각 노드까지 도달하는 비용을 저장할 배열
        Arrays.fill(dists, inf); // 아직 도달하지 못했다는 의미로 inf로 초기화
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(o -> o[1])); // 노드번호, 비용을 저장하는 우선순위 큐, 비용으로 정렬
        pq.add(new int[]{1, 0}); // 현서네 집에서 출발
        while (!pq.isEmpty()) {
            int cur = pq.peek()[0];
            int d = pq.poll()[1];
            if(dists[cur] != inf) { // 이미 방문했으면 더 적은 비용으로 도달했을 것이므로 건너뜀
                continue;
            }
            dists[cur] = d; // 비용 갱신
            if(cur == n) { // 찬홍이네 집이면 break
                break;
            }
            for (int[] next : edges[cur]) { // 현재 노드에서의 간선들
                if (dists[next[0]] == inf) { // 아직 도달하지 못한 노드들
                    pq.add(new int[]{next[0], next[1] + d}); // 도착노드와 현재노드까지의비용+간선의가중치를 우선순위큐에 저장
                }
            }
        }

        bw.write(dists[n] + "\n");
        bw.flush();
        bw.close();
        br.close();
    }
}
```

# **🔑Description**

> 다익스트라로 풀었다.\

# **📑Related Issues**

> 다익스트라 생각이 났다.

# **🕛Resource**

| Memory    | Time    |
| --------- | ------- |
| 39880`KB` | 448`ms` |
