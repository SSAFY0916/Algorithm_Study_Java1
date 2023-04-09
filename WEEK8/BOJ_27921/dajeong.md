![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2027921&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 27921 동전퍼즐](https://www.acmicpc.net/problem/27921)

<br>
<br>

# **Code**

```java
import java.io.*;
import java.util.*;

public class Main_27921_동전퍼즐 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int totalCoin = 0;

        int h1 = Integer.parseInt(st.nextToken());
        int w1 = Integer.parseInt(st.nextToken());
        char[][] arr1 = new char[h1][w1];
        for (int i=0; i<h1; i++) {
            String s = br.readLine();
            for (int j=0; j<w1; j++) {
                arr1[i][j] = s.charAt(j);
                if (arr1[i][j] == 'O') totalCoin++;
            }
        }

        st = new StringTokenizer(br.readLine());
        int h2 = Integer.parseInt(st.nextToken());
        int w2 = Integer.parseInt(st.nextToken());
        char[][] arr2 = new char[h2][w2];
        for (int i=0; i<h2; i++) {
            String s = br.readLine();
            for (int j=0; j<w2; j++) {
                arr2[i][j] = s.charAt(j);
            }
        }

        // 동전 겹치는 개수
        int dupCoin = 0;

        // i, j는 arr2에 대한 arr1의 offset
        for (int i=-h1+1; i<h2; i++) {
            for (int j=-w1+1; j<w2; j++) {

                int temp = 0;
                for (int x=0; x<h2; x++) {
                    for (int y=0; y<w2; y++) {
                        // arr2의 x,y와 arr1의 x-i, y-j 비교하기

                        if (x-i < 0 || x-i >= h1 || y-j < 0 || y-j >= w1) continue;
                        // arr1의 오프셋, arr2에 모두 동전이 있는 경우 세기 
                        if (arr1[x-i][y-j] == 'O' && arr2[x][y] == 'O') temp++;
                    }
                }

                if (dupCoin < temp) dupCoin = temp;
            }
        }

        System.out.println(totalCoin - dupCoin);

    }
}
```

<br>
<br>

# **🔑Description**

> 설계 시간: 20min

> 구현 시간: 1hr+a
<aside>
💡 설계 아이디어

    두 맵을 움직여서 겹치는 최대 동전 개수를 세서 전체 동전 개수에서 빼면 됨
    for문을 사용하여 arr1과 arr2의 겹치는 범위를 찾기. arr1의 범위를 arr2에 대한 offset(i,j)만큼 이동시키며 비교
    arr2 배열 내의 모든 동전 위치를 확인하면서, arr1 배열 내에서 offset에 따라 겹치는 위치에 있는 동전의 개수를 확인
    temp 값을 기존 dupCoin 값과 비교하여 더 큰 값을 dupCoin에 저장
    모든 반복문이 종료되면, totalCoin에서 dupCoin 값을 빼고, 그 값을 출력

</aside>

<br>
<br>

# **📑Related Issues**

> Related Issues
<aside>

    로직은 생각을 했는데, 배열 인덱스 처리와 구현하기가 어려웠다 ㅠㅠ
    배열 탐색이 약한 것 같다.
    배열을 더 늘려서 시뮬레이션 하듯이 푸는 방식도 해봐야겠다..
    

</aside>

<br>
<br>

# **🕛Resource**

| Memory | Time  | Implementation Time |
| -- |-------|---------------------|
| 14228KB | 128ms | 2 Hour  |
