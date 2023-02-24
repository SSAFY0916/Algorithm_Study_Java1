![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2020056&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 문제번호 문제이름](https://www.acmicpc.net/problem/20056)

<br>
<br>

# **Code**

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_20056 {
    static int mapSize;
    static int fireNum;
    static int cmdNum;
    // 8방향에 대해서 이동하게 되는 index값을 저장한 배열
    static int[][] dirArr = { { -1, 0 }, { -1, 1 }, { 0, 1 }, { 1, 1 }, { 1, 0 }, { 1, -1 }, { 0, -1 }, { -1, -1 } };

    // 현재 map에 살아있는 FireBall을 담고 있는 ArrayList
    static List<FireBall> fList = new ArrayList<>();

    // FireBall의 정보를 저장할 클래스
    static class FireBall implements Comparable<FireBall> {
        int row;
        int col;
        int mass;
        int dir;
        int speed;

        public FireBall(int row, int col, int mass, int dir, int speed) {
            this.row = row;
            this.col = col;
            this.mass = mass;
            this.dir = dir;
            this.speed = speed;
        }

        // FireBall을 row의 오름차순 (같다면 col의 오름차순)으로 정렬하기 위해 Overriding
        @Override
        public int compareTo(FireBall o) {
            if (this.row == o.row)
                return this.col - o.col;
            return this.row - o.row;
        }
    }

    // 같은 위치에 있는 FireBall이 있을 때 수행되는 함수
    // FireBall에는 같은 위치에 있는 FireBall이 담긴 Queue가 parameter로 넘어옴
    public static void merge(Queue<FireBall> q) {
        // Queue의 size가 1이라면 같은 위치에 있는 FireBall이 없으므로 다시 List에 넣어주고 Return;
        if (q.size() == 1) {
            while (!q.isEmpty()) {
                fList.add(q.poll());
            }
            return;
        }

        // Queue의 size가 1 이상이라면 담겨있는 FireBall들을 병합 후 분리
        FireBall tmp = q.poll();
        int massSum = tmp.mass;
        int speedSum = tmp.speed;
        int size = q.size() + 1;
        int dir = tmp.dir % 2;
        boolean flag = true;

        while (!q.isEmpty()) {
            tmp = q.poll();
            massSum += tmp.mass;
            speedSum += tmp.speed;
            if (dir != tmp.dir % 2)
                flag = false;
        }

        massSum /= 5;
        speedSum /= size;

        if (massSum != 0) {
            for (int i = 0; i < 4; i++) {
                if (flag) {
                    dir = i * 2;
                } else {
                    dir = i * 2 + 1;
                }
                fList.add(new FireBall(tmp.row, tmp.col, massSum, dir, speedSum));
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // Input
        mapSize = Integer.parseInt(st.nextToken());
        fireNum = Integer.parseInt(st.nextToken());
        cmdNum = Integer.parseInt(st.nextToken());

        for (int i = 0; i < fireNum; i++) {
            st = new StringTokenizer(br.readLine());
            int row = Integer.parseInt(st.nextToken()) - 1;
            int col = Integer.parseInt(st.nextToken()) - 1;
            int mass = Integer.parseInt(st.nextToken());
            int speed = Integer.parseInt(st.nextToken());
            int dir = Integer.parseInt(st.nextToken());
            fList.add(new FireBall(row, col, mass, dir, speed));
        }

        // 같은 위치에 있는 FireBall을 쉽게 찾을 수 있도록 List 정렬
        Collections.sort(fList);

        // 명령의 횟수만큼 반복
        for (int cmd = 0; cmd < cmdNum; cmd++) {
            // 모든 FireBall에 대하여 이동 명령 수행
            for (int i = 0; i < fList.size(); i++) {
                fList.get(i).row = (fList.get(i).row + dirArr[fList.get(i).dir][0] * fList.get(i).speed) % mapSize;
                if (fList.get(i).row < 0)
                    fList.get(i).row += mapSize;
                fList.get(i).col = (fList.get(i).col + dirArr[fList.get(i).dir][1] * fList.get(i).speed) % mapSize;
                if (fList.get(i).col < 0)
                    fList.get(i).col += mapSize;
            }

            // 이동된 FireBall을 재정렬
            Collections.sort(fList);

            // 중간에 fList.remove()를 수행하기 때문에 top-down 방식으로 loop
            for (int i = fList.size() - 1; i >= 0; i--) {
                // 같은 위치에 있는 FireBall을 담기 위한 Queue
                Queue<FireBall> q = new ArrayDeque<>();
                // 현재 index의 FireBall을 Queue에 넣어줌
                FireBall tmp = fList.get(i);
                q.add(tmp);
                // merge함수에서 다시 병합이 있을 경우 특정 동작 수행 후 다시 fList에 넣어주므로 remove
                // 병합이 없을 때는 그대로 넣어주도록 merge함수 작성
                fList.remove(i);
                // index를 하나씩 앞으로 가면서 같은 위치에 있는 FireBall을 탐색 후 Queue에 add
                while ((i - 1 >= 0) && tmp.row == fList.get(i - 1).row && tmp.col == fList.get(i - 1).col) {
                    --i;
                    q.add(fList.get(i));
                    fList.remove(i);
                }
                merge(q);
            }
        }

        int result = 0;

        // 남아 있는 FireBall의 질량을 구하기
        for (int i = 0; i < fList.size(); i++) {
            result += fList.get(i).mass;
        }

        System.out.println(result);
    }

}
```

<br>
<br>

# **🔑Description**

> 같은 위치에 있는 파이어볼을 Queue에 넣어준 후 Queue에 여러 개의 파이어볼이 들어가 있는 경우 병합 후 분리하는 방식으로 동작하도록 작성하였습니다.

<br>
<br>

# **📑Related Issues**

> For Loop에 사용되는 index 변수를 loop문 안에서 특정 조건일때 감소하도록 작성하였는데, 이 방식이 익숙하지 않아서 작성하면서 올바르게 작성했는지 계속해서 두려움이 있었던 것 같습니다.

<br>
<br>

# **🕛Resource**

| Memory | Time   |
| ------ | ------ |
| 47660KB | 788ms |