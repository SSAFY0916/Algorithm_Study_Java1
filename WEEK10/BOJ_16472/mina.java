import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    static int N;

    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws Exception {

        N = Integer.parseInt(br.readLine());
        int count = 0;  // 선택한 알파벳 개수
        char[] meow = br.readLine().toCharArray();  // 고양이 문자열
        int[] letter = new int[26]; // 알파벳 수 count

        int start = 0, end = 0, flag = 0, result = 0;
        while (end != meow.length) {
            if ((flag & 1 << meow[end] - 'a') == 0) { // 고양이 알파벳이 flag에 포함 안되어있음
                if (count < N) {  // 자리 있어서 포함할 수 있음
                    count++;
                    flag |= 1 << meow[end] - 'a';
                } else { // 자리가 없어서 이전에 선택한거 중 하나 버리기

                    // start 부터 올라오면서 선택한 문자열에서 문자 하나씩 버림
                    while (true) {
                        letter[meow[start] - 'a']--;    // 문자 버림
                        if (letter[meow[start] - 'a'] == 0) {   // 문자열 안에 없어서 flag에서 제외 시킬 수 있음
                            flag -= (1 << meow[start] - 'a');   //flag에서 제외
                            flag |= 1 << meow[end] - 'a';
                            start++;
                            break;
                        }
                        start++;
                    }
                }
            }
            letter[meow[end] - 'a']++;  //새로운 문자 count 증가
            end++;  // end index 증가
            result = Math.max(result, end - start); // 문자열 길이 계산
        }

        bw.write(Integer.toString(result));
        bw.flush();
        bw.close();
        br.close();
    }

}

