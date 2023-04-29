![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%203151&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> BOJ 3151 합이 0

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
        List<Integer> nums = new ArrayList<>(); // 중복을 제거한 숫자들을 저장하는 리스트
        int[] counts = new int[20001]; // 숫자들의 개수를 저장하는 배열
        int offset = 10000; // -10000~10000 범위의 숫자들을 저장하기 위한 숫자
        for (int i = 0; i < n; i++) {
            int num = Integer.parseInt(st.nextToken());
            if (++counts[num+offset] == 1) { // 처음 등장할 때만 nums에 저장하여 중복을 제거
                nums.add(num);
            }
        }
        Collections.sort(nums); // 정렬
        
        long answer = 0L; // 가능한 경우의 수
        int num1 = 0, num2 = 0, num3 = 0;
        for (int i = 0; i < nums.size(); i++) {
            num1 = nums.get(i);
            for (int j = i; j < nums.size(); j++) {
                num2 = nums.get(j);
                if (num1 + num2 < -offset) { // 두 숫자의 합이 -10000보다 작으면 숫자는 최대 10000이라 합을 0으로 만들수 없음
                    continue;
                }
                if (num1 + num2 > offset) { // 두 숫자의 합이 10000보다 크면 숫자는 최소 -10000이라 합을 0으로 만들 수 없고 이후에도 모두 10000을 넘으므로 break
                    break;
                }
                num3 = -num1 -num2;
                if (num2 > num3) { // num1 <= num2 <= num3하게 해서 중복을 제거
                    break;
                }
                if (num1 == num2 && num2 == num3) { // 모두 같은 수일 때
                    answer += (long) counts[num1 + offset] * (counts[num2 + offset]-1) * (counts[num3 + offset]-2) / 6;
                } else if (num1 == num2 || num2 == num3) { // 2개가 같은 수일 때
                    answer += (long) counts[num1 + offset] * (counts[num2 + offset]-1) * counts[num3 + offset] / 2;
                } else if (num1 == num3) {
                    answer += (long) counts[num1 + offset] * counts[num2 + offset] * (counts[num3 + offset]-1) / 2;
                } else { // 모두 다른 수일 때
                    answer += (long) counts[num1 + offset] * counts[num2 + offset] * counts[num3 + offset];
                }
            }
        }
        bw.write(answer + "\n");
        bw.close();
        br.close();
    }
}
```

# **🔑Description**

> 숫자들을 리스트에 중복을 제거하여 저장하고 배열에 경우의 수 계산을 위한 각 숫자의 개수를 저장한다.\
> 투 포인터로 숫자 두 개를 뽑고 합이 0이 되게하는 숫자를 찾아서 경우의 수를 구한다.\
> 이때 합이 0이 불가능한 경우들은 넘어가고 같은 숫자들이 있는지 검사한다.\
> 뽑는 순서대로 대소를 유지하여 같은 경우를 여러번 선택하지 않도록 했다.\

# **📑Related Issues**

> 첫 번째 뽑는 숫자와 두 번째 뽑는 숫자는 정렬된 리스트에서 인덱스별로 가져옴으로써 나중에 뽑는 수가 더 크도록 했는데\
> 세 번째 숫자를 뽑을 때 합이 0이 되도록 하는 조건에만 신경쓰다가 세 번째 숫자를 가장 크게 하지 않아\
> `(-5, 2, 3)`, `(-5, 3, 2)`와 같이 같은 조합을 중복해서 계산했었다.

# **🕛Resource**

| Memory    | Time    |
| --------- | ------- |
| 16700`KB` | 340`ms` |
