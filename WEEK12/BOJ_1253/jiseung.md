![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%201253&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> BOJ 1253 좋다

# 💻**Code**

```java
import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        List<Integer> nums = new ArrayList<>(); // 중복을 제거한 수들을 저장하는 리스트
        Map<Integer, Integer> counts = new HashMap<>(); // 각 숫자들이 등장하는 횟수 (수, 등장 회숫)
        Map<Integer, Integer> answer = new HashMap<>(); // 각 숫자들 중 좋은 수의 개수 (수, 개수)
        for (int i = 0; i < n; i++) {
            int num = Integer.parseInt(st.nextToken());
            if (counts.getOrDefault(num, 0) == 0) { // 처음 등장하는 수
                nums.add(num);
                counts.put(num, 1);
                answer.put(num, 0);
            } else { // 이미 등장한 중복되는 수
                counts.put(num, counts.get(num)+1);
            }
        }
        Collections.sort(nums); // 중복을 제거한 수들을 정렬
        for (int i = 0; i < nums.size(); i++) {
            int num1 = nums.get(i);
            for (int j = i; j < nums.size(); j++) {
                int num2 = nums.get(j);
                if (num1 == num2 && counts.get(num1) < 2) { // 수가 하나 밖에 없어서 두 번 뽑을 수 없으면 건너뜀
                    continue;
                }
                if (num1 == 0 && num2 == 0) { // 0 + 0 = 0 일 때
                    if (counts.get(0) > 2) // 0이 2개 초과면 모든 0을 만들 수 있고 아니면 다 못 만듦
                        answer.put(0, counts.get(0));
                } else if (num1 == 0) { // 0 + num2 = num2일 때
                    if (counts.get(num2) > 1) // num2가 1개 초과일 때만 만들 수 있다
                        answer.put(num2, counts.get(num2));
                } else if (num2 == 0) { // num1 + 0 = num1
                    if (counts.get(num1) > 1) // num2가 1개 초과일 때만 만들 수 있다
                        answer.put(num1, counts.get(num1));
                } else { // num1 + num2 = num 일 때
                    answer.put(num1 + num2, counts.getOrDefault(num1 + num2, 0)); // (num1 + num2)의 개수만큼 만들 수 있다
                }
            }
        }

        bw.write(answer.values().stream().mapToInt(v -> v).sum() + "\n"); // answer의 모든 밸류의 합을 출력
        bw.close();
        br.close();
    }
}
```

# **🔑Description**

> 처음엔 num1 + num2을 lower, upper bound로 개수를 찾는 방법을 사용 했엇는데 시간초과가 났었다.\
> 저번주 합이 0 문제 처럼 숫자들과 숫자들의 등장횟수를 저장해놓고 찾는 방법으로 바꿨다.\
> 숫자 값의 범위가 넓어서 배열로 만들지 못 할 것 같아서 맵을 사용했다.\
> 숫자 2개를 뽑아서 숫자들을 합친 값과 동일한 수들을 저장했다.

# **📑Related Issues**

> 숫자의 범위가 넓고 음수도 있고 0도 있어서 헷갈렸다\
> 다른 수들로 만든 값이어야만 하는게 헷갈렸다

# **🕛Resource**

| Memory     | Time     |
| ---------- | -------- |
| 179536`KB` | 1040`ms` |
