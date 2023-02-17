import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int houseNum;
    static int wifiNum;
    static int[] housePos;

    public static int binarySearch(int distance) {
        int cnt = 1;
        int lastPos = housePos[0];

        for (int i = 1; i < houseNum; i++) {
            int pos = housePos[i];

            if (pos - lastPos >= distance) {
                cnt++;
                lastPos = pos;
            }
        }

        return cnt;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        houseNum = Integer.parseInt(st.nextToken());
        wifiNum = Integer.parseInt(st.nextToken());
        housePos = new int[houseNum];

        for (int i = 0; i < houseNum; i++) {
            housePos[i] = Integer.parseInt(br.readLine());
        }

        Arrays.sort(housePos);

        int low = 1;
        int high = housePos[houseNum - 1] - housePos[0] + 1;

        while (low < high) {
            int mid = (low + high) / 2;

            if (binarySearch(mid) < wifiNum) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        System.out.println(low - 1);
    }

}
