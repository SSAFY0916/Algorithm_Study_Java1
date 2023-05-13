import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        int[] nums = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            nums[i] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(nums); // 지름 정렬

        int[][] sums = new int[n*(n-1)/2][2];
        int index = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                sums[index++] = new int[]{nums[i] + nums[j], i, j}; // 지름의합, 지름1의 인덱스, 지름2의 인덱스
            }
        }
        Arrays.sort(sums, Comparator.comparingInt(o -> o[0])); // 지름의 합 기준으로 정렬
        int answer = Integer.MAX_VALUE; // 차이의 최솟값
        for (int i = 1; i < sums.length; i++) {
            int diff = sums[i][0] - sums[i - 1][0]; // 정렬된 연속된 지름의 합들의 차이
            if (diff >= answer) { // 최솟값보다 크면 건너뜀
                continue;
            }
            if (sums[i][1] != sums[i-1][1] && sums[i][1] != sums[i-1][2] && sums[i][2] != sums[i-1][1] && sums[i][2] != sums[i-1][2]) { // 선택된 4개의 눈덩이가 서로 다름
                answer = diff;
            }
        }

        bw.write(answer + "\n");
        bw.flush();
        bw.close();
        br.close();
    }
}