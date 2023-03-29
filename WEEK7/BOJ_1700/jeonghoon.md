![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:FF658D,100:FFCB32&text=BOJ%201700&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 1700 멀티탭 스케줄링](https://www.acmicpc.net/problem/1700)

<br>
<br>

# **💻Code**

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int holeNum = Integer.parseInt(st.nextToken());
        int useNum = Integer.parseInt(st.nextToken());

        int[] holeArr = new int[holeNum];
        int[] useOrder = new int[useNum];
        int res = 0;
        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < useNum; i++) {
            useOrder[i] = Integer.parseInt(st.nextToken());
        }

        hole:
        for (int i = 0; i < useNum; i++) {
            for (int j = 0; j < holeNum; j++) {
                if (holeArr[j] == useOrder[i]) {
                    continue hole;
                }
            }

            for (int j = 0; j < holeNum; j++) {
                if (holeArr[j] == 0) {
                    holeArr[j] = useOrder[i];
                    continue hole;
                }
            }

            int changeIdx = 0, lastIdx = 0;
            for (int j = 0; j < holeNum; j++) {
                int k = 0;
                for (k = i + 1; k < useNum; k++) {
                    if (holeArr[j] == useOrder[k])
                        break;
                }

                if (k > lastIdx) {
                    changeIdx = j;
                    lastIdx = k;
                }
            }
            res++;
            holeArr[changeIdx] = useOrder[i];
        }
        System.out.println(res);
    }
}
```

<br>
<br>

# **🔑Description**

> 그리디 알고리즘을 적용하여 현재 기준으로 가장 마지막에 등장하는 전자제품을 우선적으로 멀티탭에서 뽑도록 하여 풀이하였습니다

<br>
<br>

# **📑Related Issues**

> 그리디 알고리즘을 풀 때마다 느끼는 건데 어떤 조건일 때 가장 최선의 해를 찾을 수 있는지 찾는게 어려운 것 같습니다...

<br>
<br>

# **🕛Resource**

| Memory   | Time   |
| -------- | ------ |
| 14184KB  | 120ms  |
