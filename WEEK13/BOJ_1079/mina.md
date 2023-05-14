![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:31BCFF,100:A066F9&text=BOJ%201079&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 1079 마피아](https://www.acmicpc.net/problem/1079)

<br>
<br>

# **💻Code**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    static int[] guilty;

    static int[][] R;

    static int mafia, N, ret;

    public static void main(String[] args) throws Exception {

        N = Integer.parseInt(br.readLine());
        guilty = new int[N];    // 유죄 지수
        R = new int[N][N];  // 참가자들 반응
        ret = 0;    // 정답

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            guilty[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                R[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        mafia = Integer.parseInt(br.readLine());    // 마피아 번호

        game(N, 0, 0);

        bw.write(Integer.toString(ret));

        bw.flush();
        bw.close();
        br.close();
    }

    static void game(int people, int dead, int night) {
        // people : 살아남은 사람 수, dead : 죽은 사람들 비트마스킹, night : 지나간 밤의 수

        if (people == 1) {  // 마피아만 살아남은 경우 - 제일 오랫동안 게임 함
            ret = Math.max(ret, night);
            return;
        }

        if (people % 2 == 0) { // 밤 - 순서대로 한명 고름, 유죄 지수 바뀜

            for (int i = 0; i < N; i++) {   // 순서대로 고르기

                if (i == mafia || (1 << i & dead) != 0) {   // 이미 죽은사람과 마피아는 고르지 않기
                    continue;
                }

                for (int j = 0; j < N; j++) {   // 유죄지수 바뀜
                    guilty[j] = guilty[j] + R[i][j];
                }

                game(people - 1, 1 << i | dead, night + 1); // i번째 사람 죽이고 재귀 호출

                for (int j = 0; j < N; j++) {   // 유죄지수 되돌리기
                    guilty[j] = guilty[j] - R[i][j];
                }
            }

        } else {    // 낮 - 유죄지수로 고름
            int criminal = Integer.MIN_VALUE;
            int idx = -1;
            for (int i = 0; i < N; i++) {
                if ((1 << i & dead) != 0)   // 이미 죽은사람은 고르지 않기
                    continue;

                if (criminal < guilty[i]) { // 유죄지수가 가장 큰 사람 찾기
                    criminal = guilty[i];
                    idx = i;
                }
            }

            if (mafia == idx) { // 마피아가 선택 된 경우 게임 종료
                ret = Math.max(ret, night);
                return;
            } else {    // 선택 된 시민 죽이고 재귀 호출
                game(people - 1, 1 << idx | dead, night);
            }
        }
    }

}

```

<br>
<br>

# **🔑Description**

> 밤에는 남은 사람들 중에 순서대로 골라서 죽이고\
> 낮에는 유죄지수가 가장 높은 사람을 찾아서 죽였다.

<br>
<br>

# **📑Related Issues**

> 재귀로 선택해서 죽이려면 유죄지수를 바꾸고 다시 돌려줘야하는데\
> 다시 돌려놓기 귀찮아서 매번 배열 새로 생성해서 썼더니 메모리 터졋다ㅎㅁㅎ\
> 결국ㄱ 유죄지수 바꾼담에 다시 돌려주는식으로 수정했다.\
> 또 죽은사람 표시를 유죄지수를 0으로 하는걸로 표시했었는데(유죄지수 범위랑 사람들 반응 보니까 0으로 해도 될것 같았음)\
> 시간초과나거나 자꾸 틀림ㅠㅡ 보니까 유죄지수를 원래로 되돌리는 과정에서 사람들 반응 다시 빼주고 0인것도 되돌리고 하다보니까 뭐가 꼬인것처럼 보였다.\
> 그냥 비트 하나 두고 거기서 죽은 사람들 번호 체크해주는걸로 바꿔서 맞음!

<br>
<br>

# **🕛Resource**

| Memory  | Time  |
| ------- | ----- |
| 16932KB | 392ms |
