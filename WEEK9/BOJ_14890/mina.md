![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:FF658D,100:FFCB32&text=BOJ%2014890&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 14890 경사로](https://www.acmicpc.net/problem/14890)

<br>
<br>

# **💻Code**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    static int N, L;
    static int[][] load, slide;

    public static void main(String[] args) throws Exception {

        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());

        load = new int[N][N];   // 지도
        slide = new int[N][N];  // 경사로 설치 확인

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                load[i][j] = Integer.parseInt(st.nextToken());  // 지도 입력 받기
            }
        }


        makeFlatHorizontal();   // 행에 경사로 놓기
        int result = checkHorizontal(); // 행에서 길의 개수 확인
        initSlide();    // slide 초기화
        makeFlatVertical(); // 열에 경사로 놓기
        result += checkVertical();  // 열에서 길의 개수 확인

        bw.write(Integer.toString(result));
        bw.flush();
        bw.close();
        br.close();
    }

    static void initSlide(){

        // 경사로 초기화
        for (int i = 0; i < N; i++) {
            Arrays.fill(slide[i], 0);
        }
    }

    static void makeFlatVertical(){ // 열에 경사로 놓기
        for (int i = 0; i < N; i++) {
            for (int j = 1; j < N; j++) {   // 윗칸이 높은곳 , 아랫칸이 낮은 곳일때 경사로 놓기
                if (slide[j][i] != 0)   // 경사로 이미 놓여진 곳
                    continue;

                if (load[j - 1][i] == load[j][i] + 1) { // 높이가 1 차이나서 경사로 놓아야 하는 경우
                    boolean flag = true;
                    for (int k = 0; k < L; k++) {
                        // 경사로 이미 놓여진 곳이나 경사로 길이만큼의 공간이 없는 경우 경사로 놓지 않음
                        if (j + k >= N || slide[j + k][i] != 0|| load[j][i] != load[j + k][i]) {
                            flag = false;
                            break;
                        }
                    }

                    if (flag) { // 경사로 놓기
                        for (int k = 0; k < L; k++) {
                            slide[j + k][i] = 1;
                        }
                    }
                }
            }

            for (int j = N - 2; j >= 0; j--) {  // 윗칸이 낮은 곳 , 아랫칸이 높은 곳일때 경사로 놓기
                if (slide[j][i] != 0)   // 경사로 이미 놓여진 곳
                    continue;

                if (load[j + 1][i] == load[j][i] + 1) { // 높이가 1 차이나서 경사로 놓아야 하는 경우
                    boolean flag = true;
                    for (int k = 0; k < L; k++) {
                        // 경사로 이미 놓여진 곳이나 경사로 길이만큼의 공간이 없는 경우 경사로 놓지 않음
                        if (j - k < 0 || slide[j - k][i]  != 0|| load[j][i] != load[j - k][i]) {
                            flag = false;
                            break;
                        }
                    }

                    if (flag) { // 경사로 놓기
                        for (int k = 0; k < L; k++) {
                            slide[j - k][i] = 2;
                        }
                    }
                }
            }
        }
    }

    static void makeFlatHorizontal(){   // 행에 경사로 놓기
        for (int i = 0; i < N; i++) {
            for (int j = 1; j < N; j++) {   // 왼쪽칸이 높은곳, 오른쪽 칸이 낮은 곳일때 경사로 놓기
                if (slide[i][j] != 0)   // 이미 경사로 놓여진 곳
                    continue;

                if (load[i][j - 1] == load[i][j] + 1) { // 높이가 1 차이나서 경사로 놓아야 하는 경우
                    boolean flag = true;
                    for (int k = 0; k < L; k++) {
                        // 경사로 이미 놓여진 곳이나 경사로 길이만큼의 공간이 없는 경우 경사로 놓지 않음
                        if (j + k >= N || slide[i][j + k] != 0 || load[i][j] != load[i][j + k]) {
                            flag = false;
                            break;
                        }
                    }

                    if (flag) { // 경사로 놓기
                        for (int k = 0; k < L; k++) {
                            slide[i][j + k] = 1;
                        }
                    }
                }
            }
            for (int j = N - 2; j >= 0; j--) {   // 왼쪽칸이 낮은 곳, 오른쪽 칸이 높은 곳일때 경사로 놓기
                if (slide[i][j] != 0)   // 이미 경사로 놓여진 곳
                    continue;

                if (load[i][j + 1] == load[i][j] + 1) { // 높이가 1 차이나서 경사로 놓아야 하는 경우
                    boolean flag = true;
                    for (int k = 0; k < L; k++) {
                        // 경사로 이미 놓여진 곳이나 경사로 길이만큼의 공간이 없는 경우 경사로 놓지 않음
                        if (j - k < 0 || slide[i][j - k] != 0 || load[i][j] != load[i][j - k]) {
                            flag = false;
                            break;
                        }
                    }

                    if (flag) { // 경사로 놓기
                        for (int k = 0; k < L; k++) {
                            slide[i][j - k] = 2;
                        }
                    }
                }
            }
        }
    }

    static int checkHorizontal(){   // 행에서 길이 몇개 있는지 확인
        int count = 0;
        for (int i = 0; i < N; i++) {
            boolean flag = true;
            for (int j = 0; j < N - 1; j++) {
                if (load[i][j] == load[i][j + 1]) { // 높이차이 없는 경우
                    continue;
                } else if (load[i][j] + 1 == load[i][j + 1] && slide[i][j] == 2) {  // 높이 차이 있지만 경사로 있는 경우
                    continue;
                } else if (load[i][j] == load[i][j + 1] + 1 && slide[i][j + 1] == 1) {  // 높이 차이 있지만 경사로 있는 경우
                    continue;
                } else {    // 높이차이 있는데 경사로 없는 경우 - 길이 아님
                    flag = false;
                    break;
                }
            }
            if(flag) {
                count++;
            }
        }
        return count;
    }

    static int checkVertical(){   // 열에서 길이 몇개 있는지 확인
        int count = 0;
        for (int i = 0; i < N; i++) {
            boolean flag = true;
            for (int j = 0; j < N - 1; j++) {
                if (load[j][i] == load[j + 1][i]) { // 높이차이 없는 경우
                    continue;
                } else if (load[j][i] + 1 == load[j + 1][i] && slide[j][i] == 2) {  // 높이 차이 있지만 경사로 있는 경우
                    continue;
                } else if (load[j][i] == load[j + 1][i] + 1 && slide[j + 1][i] == 1) {  // 높이 차이 있지만 경사로 있는 경우
                    continue;
                } else {    // 높이차이 있는데 경사로 없는 경우 - 길이 아님
                    flag = false;
                    break;
                }
            }
            if(flag) {
                count++;
            }
        }

        return count;
    }

}
```

<br>
<br>

# **🔑Description**

> 행/열에 경사로를 놓고 길인지 체크했다.\
> 경사로 놓는 방향이 두가지이므로 행에서는 왼쪽에서 오른쪽으로 가면서 놓고 오른쪽에서 왼쪽으로 가면서 놓았다.\
> 열에서도 위->아래 , 아래-> 위 이렇게 가면서 놓았다.\
> 체크할땐 평평하거나 높이 1 차이 나는데 맞는 방향의 경사로가 있으면 길인걸로 체크했다.

<br>
<br>

# **📑Related Issues**

> 놓은 경사로를 행이랑 열이 같이 쓰는 줄 알고 예제2를 이해 못하고 있었는데 페어가 열이랑 행에 놓는 경사로 따로 쓰는거라고 알려줬다.\
> 처음엔 경사로 방향 상관없이 경사로의 유무를 true/false 로 표기했는데 예제 돌리다가 방향 표기가 필요한걸 깨닫고 0/1/2 로 표기해서 사용했다.

<br>
<br>

# **🕛Resource**

| Memory  | Time  |
| ------- | ----- |
| 13148KB | 104ms |
