![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2012015&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 문제번호 문제이름](https://www.acmicpc.net/problem/12015)

<br>
<br>

# **Code**

```java
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
```

<br>
<br>

# **🔑Description**

> 주어진 수열이 있을 때 새롭게 입력받은 값에 따라서 부분 수열에 추가하는 방법을 달리 했습니다.
    > 입력 받은 값이 현재 부분 수열의 가장 큰 값보다 클 경우
        > 부분 수열의 맨 뒤에 새롭게 추가해 주기
    > 입력 받은 값이 현재 부분 수열의 가장 큰 값보다 작거나 같은 경우
        > 부분 수열 내에서 이분 탐색(Binary Search)를 이용해 현재 입력 받은 값과 가장 근접한 큰 수 (같은 수 포함)를 고른 후 해당 위치를 입력받은 값으로 교체

<br>
<br>

# **📑Related Issues**

> LinkedList(가변 배열)를 사용하여 값을 추가, 교체 등을 하려고 하였으나 해당 자료구조 사용 시 자료구조 이외의 다른 코드들은 동일하지만 시간 초과가 발생하였습니다.
> 자료구조의 문제라고는 생각하지 않고 이분 탐색 함수를 잘못 작성하였는지 등의 고민을 하였지만 문제라고는 생각할 수 없었고, 자료구조를 정적 배열로 변환한 이후 통과하였습니다.

<br>
<br>

# **🕛Resource**

| Memory | Time   |
| ------ | ------ |
| 96108KB | 544ms |