import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int h1 = Integer.parseInt(st.nextToken());
        int w1 = Integer.parseInt(st.nextToken());
        char[][] coins1 = new char[h1+18][w1+18]; // 최대 10x10크기의 동전들이 등장하므로 상하좌우로 9칸씩 늘린 배열을 선언
        for (int i = 0; i < h1; i++) {
            String line = br.readLine();
            for (int j = 0; j < w1; j++) {
                coins1[i+9][j+9] = line.charAt(j); // 왼쪽과 위쪽에서 9칸 떨어진 곳에서부터 초기화
            }
        }
        st = new StringTokenizer(br.readLine());
        int h2 = Integer.parseInt(st.nextToken());
        int w2 = Integer.parseInt(st.nextToken());
        char[][] coins2 = new char[h2][w2];
        for (int i = 0; i < h2; i++) {
            coins2[i] = br.readLine().toCharArray();
        }

        int maxCount = 0;
        for (int i = 10 - h2; i < 9 + h1; i++) { // 새로운 모양의 가장 아래 행과 기존 모양의 가장 위 행이 겹칠 때부터 새로운 모양의 가장 위 행과 기존 모양의 가장 아래행이 겹칠 때까지
            for (int j = 10 - w2; j < 9 + w1; j++) { // 위와 동일하게 좌우로
                int count = 0;
                for (int k = 0; k < h2; k++) {
                    for (int l = 0; l < w2; l++) {
                        if (coins1[i+k][j+l] == 'O' && coins2[k][l] == 'O') { // 둘 다 동전이 있는 경우 세기
                            count++;
                        }
                    }
                }
                maxCount = Math.max(maxCount, count); // 가장 많이 겹치는 개수 갱신
            }
        }

        int totalCount = 0;
        for(char[] coins : coins2) {
            for(char coin : coins) {
                if(coin == 'O') { // 총 동전 개수 세기
                    totalCount++;
                }
            }
        }
        bw.write((totalCount-maxCount) + "\n"); // 총 동전 - 겹치는 동전
        bw.flush();
        bw.close();
        br.close();
    }
}