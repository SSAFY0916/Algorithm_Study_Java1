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

        int N = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();

        List<Pair> store = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            store.add(new Pair(x, y));
        }

        int min = Integer.MAX_VALUE, minIndex = 0;  // 최소 거리와 최소 거리를 가지는 편의시설 index

        for (int i = 0; i < N; i++) {
            int max = -1, maxIndex = 0; // 최대 거리, 최대 거리를 가지는 편의시설 index
            for (int j = 0; j < N; j++) {

                if (i == j) // 똑같은 편의시설 간의 거리는 구하지 않음
                    continue;

                // 편의시설 간 거리 구하기
                int d = getDistance(store.get(i).x, store.get(i).y, store.get(j).x, store.get(j).y);

                if (max < d) {  // 거리가 최대인 경우 저장
                    max = d;
                    maxIndex = i;
                }

            }

            if (min > max) {    // 거리가 최대가 되는 편의시설 간의 거리가 최소인 경우 저장
                min = max;
                minIndex = maxIndex;
            }
        }
        
        sb.append(store.get(minIndex).x).append(" ").append(store.get(minIndex).y);
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    static int getDistance(int x1, int y1, int x2, int y2) {
        return (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
    }

    static class Pair {
        int x, y;

        Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}

