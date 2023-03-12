import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class dajeong {

    static ArrayList<Integer> arrayList;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[] input = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            input[i] = Integer.parseInt(st.nextToken());
        }

        arrayList = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            int target = input[i];
            int lastIdx = arrayList.size()-1;
            if (arrayList.size()==0 || arrayList.get(lastIdx) < target) {
                arrayList.add(target);
            } else { // 타겟이 마지막값보다 작거나 같을경우, 이분탐색으로 대체할 위치 찾기 (타겟값보다 큰 값중 가장 가까운 값)
                int lt = 0;
                int rt = lastIdx;
                int mid = (lt+rt)/2;

                while(lt<rt) {
                    mid = (lt+rt)/2;
                    int midValue = arrayList.get(mid);

                    if (midValue == target) {
                        break;
                    } else if (midValue > target) {
                        rt = mid;
                    } else {
                        lt = mid+1;
                    }
                }
                mid = (lt+rt)/2; // ***
                arrayList.set(mid, target);

            }
        }
        System.out.println(arrayList.size());

    }

    private static void print() {
        for (int i = 0; i < arrayList.size(); i++) {
            System.out.print(arrayList.get(i)+" ");
        }
        System.out.println();
    }
}
