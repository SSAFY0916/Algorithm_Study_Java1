![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%2017281&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **πProblem**

> [BOJ 17281 βΎ](https://www.acmicpc.net/problem/17281)

<br>
<br>

# **Code**

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    /*
    9λͺμΌλ‘ μ΄λ£¨μ΄μ§ λ νμ΄ κ³΅κ²©κ³Ό μλΉλ₯Ό λ²κ°μ νλ κ²μ
    μ΄ Nμ΄λ λμ κ²μμ μ§ν
    κ²½κΈ°κ° μμνκΈ° μ κΉμ§ νμ(νμκ° νμμ μλ μμ)μ μ ν΄μΌ νκ³ , κ²½κΈ° μ€μλ νμμ λ³κ²½ν  μ μλ€.
    ν μ΄λμ 3μμμ΄ λ°μνλ©΄ μ΄λμ΄ μ’λ£ -> λ νμ΄ κ³΅κ²©κ³Ό μλΉλ₯Ό μλ‘ λ°κΎΌλ€.
    9λ² νμκΉμ§ κ³΅μ μ³€λλ° 3μμμ΄ λ°μνμ§ μμ μνλ©΄ μ΄λμ λλμ§ μκ³ , 1λ² νμκ° λ€μ νμμ μ λ€.
    νμμ μ΄λμ΄ λ³κ²½λμ΄λ μμλ₯Ό μ μ§ν΄μΌ νλ€.

    κ°μ₯ λ§μ λμ μ μ»μ μ μλ νμ μ νκΈ° -> "μ΅λ λμ " μΆλ ₯
     */
    static int N; // μ΄λ μ
    static int[][] gameResult; // κ° μ΄λ λ³λ‘ νμ λ²νΈλ³ κ²μ κ²°κ³Ό μ μ₯ λ°°μ΄
    static int[] position; // νμ λ²νΈλ³ μμΉ (0:μμμ  1: 1λ£¨ 2: 2λ£¨ 3: 3λ£¨ 4: ν)
    static int maxScore; // μ λ΅ (μ΅λ λμ  μ μ)
    static int[] order; // νμ λ°°μ΄

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        gameResult = new int[N][9]; // νμ λ²νΈ 0λ²λΆν° μμ.
        position = new int[9];
        StringTokenizer st;
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 9; j++) {
                gameResult[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        maxScore = 0;
        solution();
    }

    private static void solution() {
        order = new int[9]; // νμ μμ κ²½μ°μ μ μ μ₯
        makeOrder(0, 0);
        System.out.println(maxScore);
    }

    private static void makeOrder(int cnt, int flag) {
        if (cnt == 4) { // 1λ² νμκ° 4λ²μ§Έ μμκ° μλ κ²½μ° λ¦¬ν΄ν΄μ μμ λ€μ μ νκΈ°
            if (order[3] != 0) {
                return;
            }
        }
        if (cnt == 9) { // 9λ²μ§ΈκΉμ§ μμ μ νμΌλ©΄ νμ μ νκΈ° μλ£ -> κ²μ μμ
            int score = gameStart(order);
            maxScore = Math.max(maxScore, score);
            return;
        }
        for (int i = 0; i < 9; i++) {
            if ((flag & (1 << i)) != 0) {
                continue; // if ((i&flag)!=0) continue; <- μ΄κ±° μλ!
            }
            order[cnt] = i;
            makeOrder(cnt + 1, flag | (1 << i));
        }
    }

    private static int gameStart(int[] order) {
        int scoreSum = 0; // μ΄λλ³ μ μ ν© μ μ₯ λ³μ
        int cur = 0; // νμ μ μ₯ν  ν¬μΈν°
        for (int n = 0; n < N; n++) { // μ΄λ μ
            Arrays.fill(position, 0); // κ²μ μ¬μμμ μμΉ 0μΌλ‘ μ¬μ‘°μ 
            int outCnt = 0; // μ΄λλ³ μμ μ
            while (outCnt < 3) {
                cur %= 9;
                int curNum = order[cur++]; // νμ¬ νμ λ²νΈ
                int curRes = gameResult[n][curNum]; // νμ¬ νμμ κ²°κ³Ό
                if (curRes == 0) { // μμ
                    outCnt++;
                } else if (curRes == 1) { // μν
                    go(1, curNum);
                } else if (curRes == 2) { // 2λ£¨ν
                    go(2, curNum);
                } else if (curRes == 3) { // 3λ£¨ν
                    go(3, curNum);
                } else { // νλ°!
                    go(4, curNum);
                }
                // λμ  νμ + μμΉ μ¬μ‘°μ  (νμλ€ νλ² μ΄λμ΄ λλ ν κ³μ ν  μ μλλ‘ ν¨)
                for (int i = 0; i < 9; i++) {
                    if (position[i] >= 4) { // μμΉκ° 4 μ΄μμ΄λ©΄(νμΌλ‘ λμ°©) λμ  ν μμΉλ₯Ό μμμ μΌλ‘ μ¬μ‘°μ 
                        scoreSum++;
                        position[i] = 0;
                    }
                }
            }
        }
        return scoreSum;
    }

    private static void go(int cnt, int curNum) { // μ μμ λ°λΌ μμΉ μ΄λμν€λ ν¨μ
        for (int p = 0; p < 9; p++) { // νμ¬ νμλ₯Ό μ μΈν νμλ€ μ΄λμν€κΈ°
            if (position[p] == 0) {
                continue; // μμ§ μ΄λνμ§ μμ νμλ€μ μ΄λx
            }
            position[p] += cnt;
        }
        position[curNum] += cnt; // νμ¬ νμλ μ΄λμν€κΈ°
    }

}
```

<br>
<br>

# **πDescription**

> μ€κ³ μκ°: 16min

> κ΅¬ν μκ°: 1hr
<aside>
π‘ μ€κ³ μμ΄λμ΄

    1. 4λ²μ§Έκ° 1λ² νμμ΄λλ‘νλ μμ΄ κ΅¬νκΈ° (λΉνΈμ°μ° μ¬μ©)
    2. κ° μμ΄ κ²½μ°μ μλ³λ‘ Nλ² κ²μ μ§ν, κ° κ²½μ°μ μμ μ΄ λμ  μ μ€ μ΅λκ°(μ λ΅) μ μ₯.
    3. κ° κ²μμ΄ λλλ νμμ΄ μ μ§λ  μ μλλ‘ νμ¬ νμ λ²νΈλ₯Ό μ μ₯ν  cur ν¬μΈν° μ¬μ©
    4. κ° κ²μ μ§νν  λλ§λ€ νμλ³ poisition, μμμ κ°±μ .
    5. μμ μκ° 3μ΄νμΌ λκΉμ§ κ²μ κ³μ μ§ννλ©΄μ, νμλ³ κ²μ κ²°κ³Ό λ°°μ΄μ νμΈνλ©° κ²μμ μ°Έμ¬νκ³  μλ (1~4λ£¨ μ£Όμ + νμ¬ νμ)μ μμΉ μ΄λ
    6. μμΉ μ΄λ ν νμΌλ‘ λμμ¨ νμλ€ νμΈ ν λμ  μ ++

</aside>

<br>
<br>

# **πRelated Issues**

> Related Issues
<aside>

- μ€κ°μ€κ° print νλκΉ νμ€ν λλ²κΉ μκ°μ΄ μ€μ΄λ€μλ€.
- μ€κ³λ₯Ό κΌΌκΌΌν ν νΈμΈλ°, 16λΆ?μ λμ λλ¬λ€.
- κ·Έλ°λ° μ΄κ±°λ₯Ό κ΅¬νν  λ κ±°μ 55λΆ μ λ κ±Έλ Έλ€.. μ€κ³κ° μλͺ»λκ² μλμλλ°λ!
- κ΅¬ν μκ°μ΄ ν­μ λ§μ΄ κ±Έλ¦¬λ κ² κ°λ€ γ γ .. μ΄κ±΄ λ§μ΄ μ°μ΅ν΄μΌκ² μ§? νΉν λλ²κΉ.......!!
- κ·Έλλ λ°μ νκ² λ§μ΄ λκ»΄μ§λ€ γγ
</aside>

<br>
<br>

# **πResource**

| Memory | Time  | Implementation Time |
| -- |-------|---------------------|
| 26540KB | 804ms | 1 Hour 15 Minutes   |
