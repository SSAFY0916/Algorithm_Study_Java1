![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:FF658D,100:FFCB32&text=BOJ%2017371&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 17371 이사](https://www.acmicpc.net/problem/17371)

<br>
<br>

# **💻Code**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));


    public static void main(String[] args) throws Exception {

        int N = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();

        List<Pair> store = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            store.add(new Pair(x, y));
        }

        int min = Integer.MAX_VALUE, minIndex = 0;  // 최소 거리와 최소 거리를 가지는 편의시설 index

        for (int i = 0; i < N; i++) {
            int max = -1, maxIndex = 0; // 최대 거리, 최대 거리를 가지는 편의시설 index
            for (int j = 0; j < N; j++) {

                if (i == j) // 똑같은 편의시설 간의 거리는 구하지 않음
                    continue;

                // 편의시설 간 거리 구하기
                int d = getDistance(store.get(i).x, store.get(i).y, store.get(j).x, store.get(j).y);

                if (max < d) {  // 거리가 최대인 경우 저장
                    max = d;
                    maxIndex = i;
                }

            }

            if (min > max) {    // 거리가 최대가 되는 편의시설 간의 거리가 최소인 경우 저장
                min = max;
                minIndex = maxIndex;
            }
        }

        sb.append(store.get(minIndex).x).append(" ").append(store.get(minIndex).y);
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    static int getDistance(int x1, int y1, int x2, int y2) {
        return (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
    }

    static class Pair {
        int x, y;

        Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}

```

<br>
<br>

# **🔑Description**

> 하나의 정확한 좌표를 구하는 게 아니라서 편의시설 위에 집이 있다고 생각하고 풀었다.\
> 모든 편의시설에 대해 다른 모든 편의시설 간의 최대 거리를 구하고 그 최대거리가 최소가 되는 편의시설을 찾았다.\

<br>
<br>

# **📑Related Issues**

> 이분탐색인가 했는데 좌표를 이분탐색으로 구할 수 있나 싶기도 했고 빨리 풀고 자고싶어서 카테고리 열어봄 -> 그리디라고햇다\
> 조합 구하는 함수로 편의시설간 거리를 미리 다 구하고 리스트에 넣어서 쓰려고 했으나 어디 편의점간의 거리를 구한건지도 정보가 필요할 것 같고 편의시설을 2개씩만 골라서 구하면 되므로 그냥 이중for문안에서 해결했다.\
> 테케 답 때문에 첨에는 double형으로도 선언했는데 sqrt 굳이 쓸 필요 없고 2로 나눌 필요도 없을 것 같아서 int로 바꿨다\
> 근데 minIndex랑 maxIndex -1로 초기화 시키고 풀었는데 ArrayIndexOutOfBounds 에러 났다.\
> 의심 가는 부분이 저기 뿐이라 0으로 바꿔봤는데 통과 됐다\
> 뭐지.. ? 헐 근데 이거 쓰면서 본건데 편의시설 개수가 (1 ≤ N ≤ 10^3) 이라서 그런 것 같다.\
> 둘다 -1로 초기화 하면 N=1일때 처리가 안되어서 0으로 초기화 해줘야한다.

<br>
<br>

# **🕛Resource**

| Memory  | Time  |
| ------- | ----- |
| 12788KB | 148ms |
