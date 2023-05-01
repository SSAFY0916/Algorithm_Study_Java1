![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%205052&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> BOJ 5052 전화번호 목록

# 💻**Code**

```java
import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int t = Integer.parseInt(br.readLine());
        while(t-- > 0) {
            Set<String> prefixes = new HashSet<>(); // 전화번호들을 저장하는 셋
            int n = Integer.parseInt(br.readLine());
            String[] nums = new String[n]; // 전화번호들을 저장하는 배열
            for (int i = 0; i < n; i++) {
                nums[i] = br.readLine();
                prefixes.add(nums[i]);
            }
            String answer = "YES\n";
            for (int i = 0; i < n; i++) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < nums[i].length() - 1; j++) {
                    sb.append(nums[i].charAt(j)); // 앞에서부터 한 숫자씩 추가
                    if (prefixes.contains(sb.toString())) { // 현재까지의 접두어가 셋에 전화번호로 저장되어 있으면 일관성 X
                        answer = "NO\n";
                        break;
                    }
                }
            }
            bw.write(answer);
        }
        bw.flush();
        bw.close();
        br.close();
    }
}

```

# **🔑Description**

> 전화번호들을 셋에 저장해 두고\
> 다시 전화번호들의 접두어가 저장된 셋에 있는지 찾았다

# **📑Related Issues**

> 먼저 전화번호를 다 저장해두고 접두어들이 저장된 전화번호랑 겹치는지 검사했어야했는데\
> 하나씩 저장하고 동시에 찾아서 자꾸 틀렸었다.\
> 트라이로도 풀수있을거같다

# **🕛Resource**

| Memory     | Time    |
| ---------- | ------- |
| 116688`KB` | 660`ms` |
