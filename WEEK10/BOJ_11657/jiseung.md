![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2011657&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> BOJ 11657 타임머신

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
        int[][] edges = new int[m][3]; // 간선들의 리스트
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 3; j++) {
                edges[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int[] dists = new int[n+1];  // 각 노드까지의 거리 배열
        int inf = (n - 1) * 10000 + 1; // 최대 n-1번의 간선을 거칠 수 있으므로 거리의 절대값이 inf보다 클 수 없다
        Arrays.fill(dists, inf);
        dists[1] = 0; // 출발 노드는 거리를 0으로 함
        boolean flag = true;
        for (int i = 0; i < n - 1; i++) { // n-1번 반복
            for (int j = 0; j < m; j++) { // 모든 간선에 대하여 반복
                if (dists[edges[j][0]] == inf) { // 출발노드를 아직 방문한적이 없으면 건너뜀
                    continue;
                }
                dists[edges[j][1]] = Math.min(dists[edges[j][1]], dists[edges[j][0]] + edges[j][2]); // 이번 간선을 사용했을 때의 거리로 갱신
                if (dists[edges[j][1]] <= -inf) { // -inf보다 작거나 같아지면 음의 사이클이 있는 것이므로 flag 갱신
                    flag = false;
                }
            }
        }
        for (int j = 0; j < m; j++) { // 모든 간선에 대하여 한 번 더 반복해서 음의 사이클 조사
            if (dists[edges[j][0]] == inf) {// 출발노드를 아직 방문한적이 없으면 건너뜀
                continue;
            }
            if (dists[edges[j][1]] > dists[edges[j][0]] + edges[j][2]) { // 아직도 갱신이 가능하다면 음의 사이클이 존재
                flag = false;
                break;
            }
        }
        if (flag) { // 음의 사이클 존재X
            for (int i = 2; i <= n; i++) {
                if (dists[i] == inf) { // 도달 불가능
                    bw.write(-1 + "\n");
                }else { // 도달 가능
                    bw.write(dists[i] + "\n");
                }
            }
        }else { // 음의 사이클 존재
            bw.write(-1 + "\n");
        }
        bw.flush();
        bw.close();
        br.close();
    }
}
```

# **🔑Description**

> 문제를 읽고 음의 가중치가 있는 것, 음의 사이클을 조사해야 되는 것을 보고 벨만포드 알고리즘을 떠올렸다.\
> 노드의 개수 -1 번 동안 반복하면서 모든 간선에 대하여 이번 간선을 사용했을 때 비용이 더 작아지는지 검사했다.\
> 그리고 마지막으로 모든 간선에 대하여 한 번 더 비용이 작아지는지 검사해서 음의 사이클을 찾았다.

# **📑Related Issues**

> n개의 노드가 있을 때, 최단 거리 경로는 최대 n-1개의 간선을 포함하니까 n-1번 반복하고 n번째에서 음의 사이클을 검사해야되는데\
> n번 반복하고 n+1번째에서 음의 사이클을 검사했었다.\
> 또한 항상 더 작은 값만 저장해서 n-1번의 반복 안에서 이미 사이클이 발생해서 integer 음의 범위 밖으로 거리가 갱신되는 것을\
> 저장하지 못해서 음의 사이클을 찾지 못했었다.\
> 500 *6000 *10000도 long 범위니까 아예 dists배열을 long으로 선언하던가 n-1번의 반복안에서 범위밖의 값으로 넘어가는지 검사를 했었으면 더 좋았을 것 같다.\
> 비슷한 문제로 [웜홀](https://www.acmicpc.net/problem/1865)이 있다.

# **🕛Resource**

| Memory    | Time    |
| --------- | ------- |
| 19164`KB` | 280`ms` |
