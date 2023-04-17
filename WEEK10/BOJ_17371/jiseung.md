![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2017371&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> BOJ 17371 이사

# 💻**Code**

```java
import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        int n = Integer.parseInt(br.readLine());
        int[][] coords = new int[n][2]; // 편의시설들의 좌표
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            coords[i][0] = Integer.parseInt(st.nextToken());
            coords[i][1] = Integer.parseInt(st.nextToken());
        }
        int min = 16_0000_0001; // 한 편의시설과 다른 편의시설과의 거리 중 최대값의 최소값
        int minIndex = -1;
        for (int i = 0; i < n; i++) {
            int max = 0; // i번째 편의시설과 다른 편의시설과의 거리 중 최대값
            for (int j = 0; j < n; j++) {
                int dx = coords[i][0] - coords[j][0];
                int dy = coords[i][1] - coords[j][1];
                max = Math.max(max, dx * dx + dy * dy);
            }
            if (min > max) { // 최대값들 중에서 최소값 찾기
                min = max;
                minIndex = i;
            }
        }
        bw.write(coords[minIndex][0] + " " + coords[minIndex][1]);
        bw.flush();
        bw.close();
        br.close();
    }
}
```

# **🔑Description**

> 나누기 2해서 평균 구하는 것은 모든 경우에 대해서 연산하니까 생략하고\
> 대신 편의시설과의 거리를 비교했다.\
> 편의시설 2개를 잇는 직선 위에 있는 것이 거리를 최소로 만들 수 있는데\
> 직선 중에서 어디에 집을 둘까 고민하다가 좌표를 이미 알고 있는 편의시설에 두기로 했고 편의시설에 집을 두면서 최소 거리와 최대 거리를 확정할 수 있었다.\
> 그래서 편의시설끼리의 거리를 비교하고 한 편의시설에서 가장 먼 편의시설의 거리들이 가장 작은 편의시설을 탐색했다.

# **📑Related Issues**

> 편의시설에 집을 두는게 편한 방법인 건 알겠는데 항상 정답인 이유를 모르겠다.

# **🕛Resource**

| Memory    | Time    |
| --------- | ------- |
| 15048`KB` | 172`ms` |
