import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        int n = Integer.parseInt(br.readLine());
        int[][] coords = new int[n][2]; // 편의시설들의 좌표
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            coords[i][0] = Integer.parseInt(st.nextToken());
            coords[i][1] = Integer.parseInt(st.nextToken());
        }
        int min = 16_0000_0001; // 한 편의시설과 다른 편의시설과의 거리 중 최대값의 최소값
        int minIndex = -1;
        for (int i = 0; i < n; i++) {
            int max = 0; // i번째 편의시설과 다른 편의시설과의 거리 중 최대값
            for (int j = 0; j < n; j++) {
                int dx = coords[i][0] - coords[j][0];
                int dy = coords[i][1] - coords[j][1];
                max = Math.max(max, dx * dx + dy * dy);
            }
            if (min > max) { // 최대값들 중에서 최소값 찾기
                min = max;
                minIndex = i;
            }
        }
        bw.write(coords[minIndex][0] + " " + coords[minIndex][1]);
        bw.flush();
        bw.close();
        br.close();
    }
}
