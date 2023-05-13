import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    static int N;

    public static void main(String[] args) throws Exception {

        N = Integer.parseInt(br.readLine());

        int[] snowBall = new int[N];
        int ret = Integer.MAX_VALUE;

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            snowBall[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(snowBall);

        frozen:
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                for (int k = j + 1; k < N; k++) {
                    for (int l = k + 1; l < N; l++) {
                        int now = Math.min(Math.abs(snowBall[l] + snowBall[i] - snowBall[j] - snowBall[k]), Math.abs(snowBall[l] + snowBall[j] - snowBall[i] - snowBall[k]));


                        ret = Math.min(now, ret);
                        if (ret == 0)
                            break frozen;

                    }
                }
            }
        }

        bw.write(Integer.toString(ret));
        bw.flush();
        bw.close();
        br.close();
    }


}