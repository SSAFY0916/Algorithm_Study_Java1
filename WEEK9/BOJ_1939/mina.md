![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:FF658D,100:FFCB32&text=BOJ%201939&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 1939 중량제한](https://www.acmicpc.net/problem/1939)

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

    static List<Parcel>[] graph;
    static int N, M, max, min, result;

    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        min = 1000000000;

        graph = new ArrayList[N + 1];

        for (int i = 0; i <= N; i++) {
            graph[i] = new ArrayList<Parcel>(); // 그래프 입력 받기
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            // 양방향 그래프 입력
            graph[a].add(new Parcel(b, c));
            graph[b].add(new Parcel(a, c));
            max = Math.max(max, c); // 최소 weight
            min = Math.min(min, c); // 최대 weight
        }

        st = new StringTokenizer(br.readLine());
        int[] factory = {Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())};

        findMax(factory[0], factory[1]);

        bw.write(Integer.toString(result));
        bw.flush();
        bw.close();
        br.close();
    }

    static void findMax(int start, int end) {
        int low = min;
        int high = max;

        Queue<Integer> queue = new ArrayDeque<>();
        boolean[] visited = new boolean[N + 1];

        while (low <= high) {
            int mid = (low + high) / 2;
            queue.add(start);   // 출발지 넣어줌
            visited[start] = true;

            // end까지 가는데 mid만큼 들 수있는지 체크
            if (checkPossible(queue, visited, mid, end)) {  // mid 만큼 옮길 수 있음
                result = Math.max(result, mid);
                low = mid + 1;
            } else {    // 옮길 수 없음 - 범위 옮겨서 탐색
                high = mid - 1;
            }

            queue.clear();  // 초기화
            Arrays.fill(visited, false);
        }
    }


    static boolean checkPossible(Queue<Integer> q, boolean[] visited, int mid, int end) {   // end까지 가는데 mid만큼 들 수있는지 체크

        // BFS 탐색
        while (!q.isEmpty()) {
            int from = q.poll();

            for (Parcel p : graph[from]) {
                if (p.weight >= mid) {  // 경유지가 mid이상 통과 가능한 경우만
                    if (from == end) {    // 도착지에 도착
                        return true;
                    }

                    if (!visited[p.end]) {  // 방문 안한경우만 queue에 넣어서 확인하기
                        visited[p.end] = true;
                        q.add(p.end);
                    }
                }
            }
        }

        return false;
    }

    static class Parcel {
        int end;
        int weight;

        Parcel(int end, int weight) {
            this.end = end;
            this.weight = weight;
        }
    }
}
```

<br>
<br>

# **🔑Description**

> 이분탐색으로 들 수 있는 중량을 찾고 출발지부터 도착지까지 그 중량을 옮길 수 있는 경로가 나오는지 BFS로 확인했다.\

<br>
<br>

# **📑Related Issues**

> 일요일까지 못 풀것 같아서 결국 알고리즘 분류 봤다(...)\
> 근데 보고도 감이 바로 안잡혀서 약간 고민했다. 설마 이분탐색으로 들 수 있는 무게 구해보고 그걸 들 수 있는 경로가 있는지 보는건가...? 싶었는데 맞았다\
> 맨첨에 알고리즘 분류 보기 전에는 모든 경로를 찾고 거기서 들 수있는 무게를 찾아야하나 했는데 이건 어떻게 구현해야할지 진짜 모르겠고 좀 아닌 방법 같았음...\
> 근데 흠ㅁ 분류 보지말고 풀어볼걸 그랫나 싶은데 그랬음 9시까지 이거 못올렸을듯ㅠㅅㅠ

<br>
<br>

# **🕛Resource**

| Memory  | Time  |
| ------- | ----- |
| 58220KB | 576ms |
