import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws Exception {

        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        List<Pair>[] tinder = new ArrayList[N]; // 인접 노드 리스트
        boolean[] visited = new boolean[N]; // 방문 check
        char[] university = new char[N];    // 남초/여초 대학 표시
        int[][] matrix = new int[N][N]; // 거리 저장용
        int[] distance = new int[N];    // 최소거리
        int count = N, result = 0;  // count: 방문 해야하는 남은 대학 수, result: 거리 합

        final int INF = 100000000;

        st = new StringTokenizer(br.readLine(), " ");

        for (int i = 0; i < N; i++) {
            university[i] = st.nextToken().charAt(0);
        }

        for (int i = 0; i < N; i++) {
            tinder[i] = new ArrayList<Pair>();
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken());

            matrix[a][b] = (matrix[a][b] == 0) ? c : Math.min(matrix[a][b], c);  // 대학교 사이에 간선이 2개면 그 중 최솟값으로만 저장
            matrix[b][a] = matrix[a][b];
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (matrix[i][j] == 0)
                    continue;

                tinder[i].add(new Pair(j, matrix[i][j]));
            }
        }

        for (int i = 0; i < N; i++) {
            distance[i] = INF;
        }

        // MST - 프림 알고리즘
        distance[0] = 0;    // 0번 대학 선택
        visited[0] = true;
        count--;
        for (int j = 0; j < tinder[0].size(); j++) {    // 최소거리 갱신
            if (university[tinder[0].get(j).n] != university[0])
                distance[tinder[0].get(j).n] = Math.min(distance[tinder[0].get(j).n], tinder[0].get(j).distance);
        }

        while (count != 0) {
            int idx = -1, minDistance = INF;

            for (int j = 0; j < N; j++) {
                if (!visited[j] && distance[j] < minDistance) { // 최소 거리인 다음 대학 찾기
                    idx = j;
                    minDistance = distance[j];
                }
            }

            if (idx == -1) {    // 다음 대학 못찾음 - 모든학교 연결 불가
                result = -1;
                break;
            }

            visited[idx] = true;    // 방문 check
            result += minDistance;  // 모든 거리 합

            count--;    // 남은 대학 수 감소

            for (int j = 0; j < tinder[idx].size(); j++) {  // 최소 거리 갱신
                if (university[tinder[idx].get(j).n] != university[idx])
                    distance[tinder[idx].get(j).n] = Math.min(distance[tinder[idx].get(j).n], tinder[idx].get(j).distance);
            }
        }

        bw.write(Integer.toString(result));
        bw.close();
    }

    static class Pair {
        int n, distance;

        Pair(int n, int distance) {
            this.n = n;
            this.distance = distance;
        }
    }
}