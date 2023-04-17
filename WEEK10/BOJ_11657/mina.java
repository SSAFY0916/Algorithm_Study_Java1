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
