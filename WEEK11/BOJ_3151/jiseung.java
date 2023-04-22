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