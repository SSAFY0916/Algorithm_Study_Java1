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