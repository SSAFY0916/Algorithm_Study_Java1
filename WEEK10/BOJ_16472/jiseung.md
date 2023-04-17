![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2016472&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> BOJ 16472 고냥이

# 💻**Code**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        String str = br.readLine();
        int count = 0; // 현재 가지고 있는 알파벳들의 종류의 수
        int[] lastIndexes = new int[26]; // 각 알파벳 별로 마지막으로 등장한 인덱스 저장하는 배열
        int flag = 0; // 현재 가지고 있는 알파벳들의 종류
        int prev = 0; // n개의 알파벳을 가지는 부분문자열의 왼쪽 끝 인덱스
        int answer = 0;
        for (int i = 0; i < str.length(); i++) {
            int type = str.charAt(i) - 'a'; // i번째 글자
            if ((flag & (1<<type)) == 0) { // 현재 포함되지 않는 글자이면
                if (count < n) { // 아직 종류가 n개보다 적음
                    count++;
                } else { // 이미 n개의 종류가 있으면
                    int minIndex = -1;
                    int min = str.length() + 1;
                    for (int j = 0; j < 26; j++) { // 등장한지 가장 오래된 알파벳 찾기
                        if ((flag & (1 << j)) == 0) {
                            continue;
                        }
                        if (min > lastIndexes[j]) {
                            min = lastIndexes[j];
                            minIndex = j;
                        }
                    }
                    prev = min + 1; // 등장한지 가장 오래된 알파벳의 오른쪽 끝 + 1로 갱신
                    flag &= ~(1 << minIndex); // 오래된 알파벳 제거
                }
                flag |= 1 << type; // 현재 알파벳 추가
            }
            lastIndexes[type] = i; // 알파벳의 마지막 등장 인덱스 갱신
            answer = Math.max(answer, i - prev + 1); // [prev, i] 구간의 길이로 비교
        }
        bw.write(answer + "\n");
        bw.flush();
        bw.close();
        br.close();
    }
}
```

# **🔑Description**

> 문자열에서 부분문자열의 오른쪽 끝을 한 칸씩 이동하면서 부분문자열의 길이를 비교한다.\
> 부분문자열을 이동 시킬 때 부분문자열에 없는 알파벳이 들어오면\
> 아직 부분문자열에 원하는 종류만큼의 알파벳이 없다면 count를 증가시키고\
> 이미 원하는 종류만큼 알파벳이 있다면 현재 부분문자열에서 가장 오랫동안 등장하지 않는 알파벳을 찾아 제거했다.\
> 가장 오랫동안 등장하지 않는 알파벳을 찾기 위해 알파벳 마다 등장하는 인덱스를 저장해 두었다.

# **📑Related Issues**

> 가장 오랫동안 등장하지 않았던 알파벳을 제거한다는 아이디어를 생각하는 데 오래걸렸다.\
> 구현도 오래걸렸다.\
> 원하는 종류의 수만큼의 알파벳을 발견하고 나서야 답을 갱신해서 원하는 종류의 수보다 적은 종류로 이루어진 문자열이 입력으로 올때 0을 출력하게 해서 틀렸었다.

# **🕛Resource**

| Memory    | Time    |
| --------- | ------- |
| 15636`KB` | 168`ms` |
