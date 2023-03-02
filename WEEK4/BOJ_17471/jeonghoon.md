![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2017471&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 문제번호 문제이름](https://www.acmicpc.net/problem/17471)

<br>
<br>

# **Code**

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;

public class BOJ_17471 {
    // 구역의 개수
    static int cityNum;
    // 구역별 인구 수 저장 배열
    static int[] populations;
    // 구역들의 연결 여부를 저장하는 배열
    static boolean[][] connection;
    // 유니온 파인드를 사용하기 위한 배열
    static int[] parent;
    // 부분집합을 계산할 때 사용하는 배열
    static boolean[] selected;
    // 최소가 되는 결과값
    static int min = 0x7fffffff;

    static class Pair {
        int city1;
        int city2;

        public Pair(int city1, int city2) {
            this.city1 = city1;
            this.city2 = city2;
        }
    }

    static int find(int x) {
        if (parent[x] == x)
            return x;
        return parent[x] = find(parent[x]);
    }

    static boolean isUnion(int x, int y) {
        if (find(x) == find(y))
            return true;
        return false;
    }

    static void merge(int x, int y) {
        x = find(x);
        y = find(y);

        if (x < y)
            parent[y] = x;
        else
            parent[x] = y;
    }

    // 구역들이 하나로 연결되어 있는지 확인하는 함수
    static boolean canMerge(List<Integer> list) {
        Set<Integer> set = new HashSet<>();
        set.add(list.get(0));
        Queue<Integer> q = new ArrayDeque<>();
        q.add(list.get(0));
        boolean[] visited = new boolean[cityNum];
        visited[list.get(0)] = true;

        // bfs를 이용하여 탐색
        while (!q.isEmpty()) {
            int tmp = q.poll();
            for (int i = 0; i < cityNum; i++) {
                // i가 현재 구역과 연결되어 있으며, list에 i가 있는 경우 set에 넣어줌
                if (!visited[i] && connection[tmp][i] && list.contains(i)) {
                    visited[i] = true;
                    set.add(i);
                    q.add(i);
                }
            }
        }

        // list의 크기와, set의 크기가 같다면 모든 구역들이 연결되어 있음
        if (list.size() == set.size())
            return true;
        return false;
    }

    // 부분집합을 구하는 함수
    static void subSet(int cnt) {
        if (cnt == cityNum) {
            List<Integer> list1 = new LinkedList<>();
            List<Integer> list2 = new LinkedList<>();

            // 선택 된 구역들은 list1에
            // 그렇지 않은 구역들은 list2에 저장
            for (int i = 0; i < cityNum; i++) {
                if (selected[i])
                    list1.add(i);
                else
                    list2.add(i);
            }

            // 두 구역 중 하나라도 size가 0이라면, 구역이 2개로 나뉘어지지 않은 경우이므로 종료
            if (list1.size() == 0 || list2.size() == 0)
                return;

            // list1과 list2가 각각 모든 구역들이 연결되어 있다면
            // 두 list의 인구 수를 계산 한 후에 인구 수의 차를 구함
            if (canMerge(list1) && canMerge(list2)) {
                int list1Sum = 0;
                for (int i = 0; i < list1.size(); i++) {
                    list1Sum += populations[list1.get(i)];
                }
                int list2Sum = 0;
                for (int i = 0; i < list2.size(); i++) {
                    list2Sum += populations[list2.get(i)];
                }
                min = Math.min(min, Math.abs(list1Sum - list2Sum));
            }
            return;
        }
        // cnt가 cityNum과 동일해 질 때까지 재귀적으로 호출하며 부분집합을 구하기
        selected[cnt] = true;
        subSet(cnt + 1);
        selected[cnt] = false;
        subSet(cnt + 1);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        cityNum = Integer.parseInt(br.readLine());
        populations = new int[cityNum];

        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 0; i < cityNum; i++) {
            populations[i] = Integer.parseInt(st.nextToken());
        }

        connection = new boolean[cityNum][cityNum];
        parent = new int[cityNum];
        selected = new boolean[cityNum];

        for (int i = 0; i < cityNum; i++)
            parent[i] = i;

        // 첫 입력을 받을 때 구역을 나누기 전 총 몇개의 구역으로 구별되어 있는지 확인하기 위해 유니온 파인드를 사용
        for (int i = 0; i < cityNum; i++) {
            StringTokenizer tmp = new StringTokenizer(br.readLine());
            int connNum = Integer.parseInt(tmp.nextToken());
            for (int j = 0; j < connNum; j++) {
                int neighbor = Integer.parseInt(tmp.nextToken());
                connection[i][neighbor - 1] = true;
                if (!isUnion(i, neighbor - 1))
                    merge(i, neighbor - 1);
            }
        }

        // 유니온 파인드가 끝난 이후 모든 구역들이 부모 구역을 가리키도록
        // 모든 구역들에 대해서 find() 함수 실행
        for (int i = 0; i < cityNum; i++) {
            find(i);
        }

        // 나뉘어져 있는 구역의 개수를 세기
        int flag = 0;
        // 각 구역들을 순회하며 부모 구역의 flag를 켜줌
        // 이 flag를 사용하면 켜진 flag의 개수 == 나뉘어진 구역의 개수
        for (int i = 0; i < cityNum; i++) {
            flag = (flag) | 1 << parent[i];
        }

        // 나뉘어진 구역의 개수를 계산
        int areaNum = 0;
        for (int i = 0; i < cityNum; i++) {
            if ((flag & 1 << i) == (1 << i))
                areaNum++;
        }

        // 나뉘어진 구역이 2보다 크다면, 2개의 구역으로 나눌 수 없는 경우
        if (areaNum > 2)
            System.out.println(-1);
        // 나뉘어진 구역이 2개라면 이 방법 이외에 다른 방법으로는 구역을 나눌 수 없음
        // 이 두 개의 구역의 인구차를 출력
        else if (areaNum == 2) {
            int[] tmp = new int[cityNum];
            for (int i = 0; i < cityNum; i++) {
                tmp[parent[i]] += populations[i];
            }
            int[] popSum = new int[2];
            for (int i = 0; i < cityNum; i++) {
                if (tmp[i] != 0 && popSum[0] == 0) {
                    popSum[0] = tmp[i];
                } else if (tmp[i] != 0) {
                    popSum[1] = tmp[i];
                }
            }
            System.out.println(Math.abs(popSum[1] - popSum[0]));
            // 나뉘어진 구역이 한개인 경우
        } else {
            // 구역을 두개로 나누기 위해 부분집합을 구하기
            subSet(0);
            if (min != 0x7fffffff)
                System.out.println(min);
            else
                System.out.println(-1);
        }
    }

}
```

<br>
<br>

# **🔑Description**

> 구역을 부분집합을 이용하여 나눈 이후, 구역의 인구수를 계산, 차를 구하는 방식으로 문제를 해결하였습니다.
> 유니온 파인드를 이용해서 현재 선택된 구역들이 모두 연결되어 있는지 확인하였습니다.

<br>
<br>

# **📑Related Issues**

> 두 구역을 처음부터 어떻게 연결되어 있지 않는 방식으로 쪼갤 수 있을지 생각하였습니다. 하지만 이 방식을 사용하지 않고, 부분집합으로 우선 선거구를 나눈 후 선거구 안의 구역들이 모두 연결되어 있는지 확인하는 방법으로 풀이하였습니다.

<br>
<br>

# **🕛Resource**

| Memory | Time   |
| ------ | ------ |
| 15996KB | 164ms |