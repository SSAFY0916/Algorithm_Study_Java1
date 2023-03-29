import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int holeNum = Integer.parseInt(st.nextToken());
        int useNum = Integer.parseInt(st.nextToken());

        int[] holeArr = new int[holeNum];
        int[] useOrder = new int[useNum];
        int res = 0;
        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < useNum; i++) {
            useOrder[i] = Integer.parseInt(st.nextToken());
        }

        hole:
        for (int i = 0; i < useNum; i++) {
            for (int j = 0; j < holeNum; j++) {
                if (holeArr[j] == useOrder[i]) {
                    continue hole;
                }
            }

            for (int j = 0; j < holeNum; j++) {
                if (holeArr[j] == 0) {
                    holeArr[j] = useOrder[i];
                    continue hole;
                }
            }

            int changeIdx = 0, lastIdx = 0;
            for (int j = 0; j < holeNum; j++) {
                int k = 0;
                for (k = i + 1; k < useNum; k++) {
                    if (holeArr[j] == useOrder[k])
                        break;
                }

                if (k > lastIdx) {
                    changeIdx = j;
                    lastIdx = k;
                }
            }
            res++;
            holeArr[changeIdx] = useOrder[i];
        }
        System.out.println(res);
    }
}