![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:FF658D,100:FFCB32&text=BOJ%2011657&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 11657 타임머신](https://www.acmicpc.net/problem/11657)

<br>
<br>

# **💻Code**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    static final long INF = 100000000L;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static int N;
    static long[] distance;
    static List<Bus> edges;

    public static void main(String[] args) throws Exception {
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();
        N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        edges = new ArrayList<>();
        distance = new long[N + 1];
        for (int i = 0; i < N + 1; i++) {
            distance[i] = INF;  // 최단거리 초기화
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            long c = Long.parseLong(st.nextToken());

            edges.add(new Bus(a, b, c));  //경로 추가
        }

        if (bellmanFord()) {    // 사이클 없는 경우
            for (int i = 2; i < N + 1; i++) {
                if (distance[i] == INF) //i번 도시까지 경로가 존재하지 않음
                    distance[i] = -1L;
                sb.append(distance[i]).append("\n");
            }
        } else {    // 사이클 있는 경우
            sb.append(-1);
        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();

    }

    static boolean bellmanFord() {

        distance[1] = 0;  // 1번 도시 기준으로 최단경로 찾기

        for (int i = 1; i < N + 1; i++) {
            for (int j = 0; j < edges.size(); j++) {
                int start = edges.get(j).start;  // 경유 도시
                int end = edges.get(j).end;   // 도착 도시
                long time = edges.get(j).time;  // 시간 비용

                if (distance[start] != INF && distance[start] + time < distance[end]) {
                    distance[end] = distance[start] + time;
                    if (i == N) { // N번째에도 위 조건문 통과하면 사이클 생긴 것임
                        return false;
                    }
                }
            }
        }

        // 사이클 없음
        return true;
    }

    static class Bus {
        int start, end;
        long time;

        public Bus(int start, int end, long time) {
            this.start = start;
            this.end = end;
            this.time = time;
        }
    }
}

```

<br>
<br>

# **🔑Description**

> 음수 간선이 있어서 사이클까지 확인해야하는 문제라 벨만포드 알고리즘으로 풀었다.

<br>
<br>

# **📑Related Issues**

> 저번주엔 다익풀고 이번주엔 벨만포드 풀어서 넘 좋다ㅎㅁㅎ
> 근데 벨만포드로 푸는거 맞는데 자꾸 출력초과라고 해서 짜증낫음ㅡㅡ\
> 문제 조건이 도시가 500개, 간선이 최대 6000개, 비용 범위가 -10,000 이상 10,000 이하라 500 _ 6000 _ -10000 = -30000000000 가 나와서 int 범위를 넘을 수도 있게 된다고 내 페어가 말해줬다.\
> result는 그냥 long으로 선언하는 습관 들여도 좋을듯...

<br>
<br>

# **🕛Resource**

| Memory  | Time  |
| ------- | ----- |
| 17088KB | 276ms |
