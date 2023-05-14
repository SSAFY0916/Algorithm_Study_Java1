ㅍimport java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            list.add(Integer.parseInt(st.nextToken()));
        }
        Collections.sort(list);
        int cnt = 0;
        for (int i = 0; i < N - 2; i++) {
            for (int j = i+1; j < N - 1; j++) {
                int sum = list.get(i) + list.get(j);
                int target = -sum;

                // lowerBound
                int s = j+1;
                int e = N;
                while(s<e) {
                    int mid = (s+e)/2;
                    if (list.get(mid) >= target) {
                        e = mid;
                    } else {
                        s = mid+1;
                    }
                }
                int lowerIdx = s;

                // upperBound
                s = j+1;
                e = N;
                while(s<e) {
                    int mid = (s+e)/2;
                    if (list.get(mid) > target) {
                        e = mid;
                    } else {
                        s = mid+1;
                    }
                }
                int upperIdx = s;

                if (upperIdx == lowerIdx) continue;

                cnt+= (upperIdx-lowerIdx);
            }
        }
        System.out.println(cnt);
    }
}