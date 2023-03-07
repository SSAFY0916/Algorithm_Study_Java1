import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_12015 {

    // 바로 다음으로 큰 수(같은 수)의 Index를 찾기 위한 binarySearch 함수
    public static int find(int num, int maxIdx, int[] arr) {
        int start = 0;
        int end = maxIdx;

        while (start < end) {
            int mid = (start + end) / 2;
            if (num > arr[mid]) {
                start = mid + 1;
            } else {
                end = mid;
            }
        }

        return start;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int size = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());

        int[] arr = new int[size];

        for (int i = 0; i < size; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        /**
         * LinkedList 사용 시 시간 초과가 발생
         * 해결을 위해서 MAX_SIZE의 정적 배열을 사용
         */
        int[] list = new int[1000001];
        // 초기 값 배열에 넣어주기
        list[0] = arr[0];
        // 현재 마지막으로 입력 받은 곳의 Index
        int maxIdx = 0;

        for (int i = 1; i < size; i++) {
            // 현재 수열의 가장 큰 값보다 큰 수를 추가할 경우 맨 뒤에 추가
            if (list[maxIdx] < arr[i]) {
                list[++maxIdx] = arr[i];
                // 그렇지 않은 경우 binarySearch를 이용해 교체해야하는 index를 찾은 후 값 교체
            } else {
                list[find(arr[i], maxIdx, list)] = arr[i];
            }
        }

        // maxIdx는 size보다 1 작으므로 1을 더해서 출력
        System.out.println(maxIdx + 1);
    }

}
