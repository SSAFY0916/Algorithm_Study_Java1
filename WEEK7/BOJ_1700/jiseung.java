import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        // 입력되는 전기용품의 번호를 순서대로 저장
        int[] nums = new int[k];
        // 전기용품마다 nums에서의 인덱스를 저장, 추후에 희생할 전기용품을 고를 때 사용
        Queue<Integer>[] orders = new Queue[k+1];
        for (int i = 0; i < k + 1; i++) {
            orders[i] = new ArrayDeque<>();
        }
        for(int i=0; i<k; i++) {
            nums[i] = Integer.parseInt(st.nextToken());
            orders[nums[i]].add(i);
        }
        int answer = 0; // 전기용품을 뽑은 횟수
        int countConnected = 0; // 현재 멀티탭에 꽂은 전기용품의 수
        boolean[] multitap = new boolean[k+1]; // 멀티탭에 꽂혀있는지 여부
        for(int i=0; i<k; i++) {
            orders[nums[i]].poll(); // 현재 인덱스를 제거
            if(multitap[nums[i]]) { // 이미 멀티탭에 꽂혀있으면 건너뜀
                continue;
            }
            if(countConnected < n) { // 아직 멀티탭에 꽂을 칸이 남으면 꽂음
                multitap[nums[i]] = true;
                countConnected++;
                continue;
            }

            int nextIndex = i; // 현재 멀티탭에 꽂혀있는 전기용품 중에서 가장 나중에 다시 사용하는 전기용품의 nums에서의 인덱스
            int num = -1; // 그 전기용품의 번호
            for (int j = 0; j < k; j++) {
                if (!multitap[nums[j]]) { // 멀티탭에 꽂혀있는 전기용품이면 건너뜀
                    continue;
                }
                if(orders[nums[j]].isEmpty()) { // 다시는 사용하지 않을 전기용품이 있으면 그 전기용품을 희생
                    num = nums[j];
                    break;
                }
                if(nextIndex < orders[nums[j]].peek()) { // 가장 나중 인덱스를 갱신
                    nextIndex = orders[nums[j]].peek();
                    num = nums[j];
                }
            }

            answer++; // 뽑은 횟수 증가
            multitap[nums[i]] = true; // 현재 전기용품 꽂음
            multitap[num] = false; // 가장 나중에 사용할 전기용품 희생
        }

        bw.write(answer + "\n");
        bw.flush();
        bw.close();
        br.close();
    }
}