import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {

    static int n, answer;
    // 각 열에서 퀸을 놓는 행 인덱스를 저장
    static int[] rows;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        n = Integer.parseInt(br.readLine());
        rows = new int[n];
        backtracking(0, 0, 0, 0);

        bw.write(answer + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    // 현재까지 놓은 퀸의 개수, 퀸이 놓인 행인덱스들, 퀸이 놓인 대각선1(\)들, /
    static void backtracking(int count, int rowflag, int diagflag1, int diagflag2) {
        if(count == n) { // n개의 퀸을 놓았다.
            answer++;
            return;
        }

        for (int i = 0; i < n; i++) {
            if((rowflag & (1<<i)) != 0) { // i 번째 행엔 이미 퀸이 있으면 건너뜀
                continue;
            }
            if((diagflag1 & (1<<(i+count))) != 0) { // 같은 대각선에 이미 퀸이 있으면 건너뜀
                continue;
            }
            if((diagflag2 & (1<<(n-1-count+i))) != 0) {
                continue;
            }

            rows[count] = i;
            backtracking(count+1, rowflag | (1<<i), diagflag1 | (1<<(i+count)), diagflag2 | (1<<(n-1-count+i)));
        }
    }
}