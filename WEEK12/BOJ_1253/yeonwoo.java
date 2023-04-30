
import java.util.*;
import java.io.*;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N;
    static int[] numbers;
    static StringTokenizer st;
    public static void main(String[] args) throws IOException{
        N = Integer.parseInt(br.readLine());
        numbers = new int[N];
        st = new StringTokenizer(br.readLine());
        for(int i =0 ; i < N ; i++){
            numbers[i] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(numbers);//투포인터 사용할 것이라 정렬

        int result = 0;
        for(int i = 0 ; i < N ; i++){
            int left = 0;
            int right = N-1;    // 음수값이 있으므로 i까지 돌지 않고 전체 다 돌아야함
            while(true){
                //i자신의 위치면 적절히 조정
                if(left == i) left++;//왼쪽부텅 왔으니 우측으로한칸
                else if(right == i) right--;//우측부터왔으니 좌측으로 한칸

                // 결과를 찾을 수 없다.
                if(left >= right) break;//같은 수 두 번 쓰면 반칙이라 >가 아니라 >=임

                // 정렬이 되어 있으므로, 합이 더 크다면 더 작은 수와 더해주어야 하니까 왼쪽으로 움직이는 right 값을 변경
                if(numbers[left] + numbers[right] > numbers[i]) right--;
                else if(numbers[left] + numbers[right] < numbers[i]) left++;
                else{      // 좋다!
                    result++;
                    break;
                }
            }
        }
        System.out.println(result);
    }

}