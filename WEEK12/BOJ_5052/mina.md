![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:A066F9&text=BOJ%205052&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 5052 전화번호 목록](https://www.acmicpc.net/problem/5052)

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

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));


    public static void main(String[] args) throws Exception {
        List<String>[] phone = new ArrayList[11]; // 전화번호부 길이가 n인 전화번호를 phone[n] 리스트에 저장
        for (int i = 0; i < 11; i++) {
            phone[i] = new ArrayList<>();
        }

        int t = Integer.parseInt(br.readLine());

        while (t-- > 0) {
            for (int i = 0; i < 11; i++) {
                phone[i].clear();
            }

            int N = Integer.parseInt(br.readLine());

            for (int i = 0; i < N; i++) {
                String s = br.readLine();
                phone[s.length()].add(s); // 전화번호부 길이가 n인 전화번호를 phone[n] 리스트에 저장
            }

            boolean flag = false;

            for (int i = 1; i < 11; i++) {  // 길이가 i인 전화번호들이 i보다 긴 전화번호들에 포함되어있는지 확인
                for (int j = 0; j < phone[i].size(); j++) {
                    for (int k = i + 1; k < 11; k++) {
                        for (int l = 0; l < phone[k].size(); l++) {
                            int idx = phone[k].get(l).indexOf(phone[i].get(j));
                            if (idx == 0) { // index가 0이면 접두어인 경우 - 전화번호 목록에 일관성 없음
                                flag = true;
                                break;
                            }
                        }
                        if (flag)
                            break;
                    }
                    if (flag)
                        break;
                }
                if (flag)
                    break;
            }

            if (flag)   // 전화번호 목록에 일관성 없음
                System.out.println("NO");
            else   // 전화번호 목록에 일관성 있음
                System.out.println("YES");
        }


        bw.flush();
        bw.close();
        br.close();
    }
}

```

<br>
<br>

# **🔑Description**

> 어떤 전화번호가 다른 전화번호에 있는지 전부 확인했다.

<br>
<br>

# **📑Related Issues**

> 53라인 조건을 `if (idx != -1)` 이라고 써서 틀렸었다(당당)\
> 포함되어 있는지를 확인하는게 아니고 접두어인지 확인해야 하는거였는데ㅎㅁㅎ...

<br>
<br>

# **🕛Resource**

| Memory  | Time   |
| ------- | ------ |
| 27176KB | 2296ms |
