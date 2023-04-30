import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[] arr = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(arr);

        long cnt = 0;
        // 두수의 합 구하고 나머지 수가 리스트에 있는지 확인
        // 중복된 수의 수 구하기 (ub-lb)
        for (int i = 0; i < N - 2; ++i) {
            for (int j = i + 1; j < N - 1; ++j) {
                int key = -(arr[i] + arr[j]);
                int l = lowerBound(j + 1, arr, key);

                if (l == N || arr[l] != key) continue;

                int r = upperBound(j + 1, arr,  key);
                cnt += r - l;
            }
        }
        System.out.println(cnt);

    }
    private static int lowerBound(int st, int[] arr, int target) {
        int len = arr.length;
        int en = len;
        while(st<en) {
            int mid = (st+en)/2;
            if (arr[mid] < target) st = mid+1;
            else en = mid;
        }
        return st;

    }

    private static int upperBound(int st, int[] arr, int target) {
        int len = arr.length;
        int en = len;
        while(st<en) {
            int mid = (st+en)/2;
            if (arr[mid] <= target) st = mid+1;
            else en = mid;
        }
        return st;
    }
}