import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

// 그리디적인 관점으로 그냥 큐로 구현했었을 때 시간초과 났었음.
// -> 우선순위 큐 이용
public class Main_11000 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st;
        List<int[]> lecture = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            lecture.add(
                new int[]{Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())});
        }
        // 가능한 최대한 많은 강의를 한 강의실에 배정
        // -> 시작 시간이 빠른 순서로 정렬. 만약, 시작 시간이 같을 경우 종료 시간이 빠른 순으로 정렬.
        Collections.sort(lecture, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] == o2[0] ? o1[1] - o2[1] : o1[0] - o2[0];
            }
        });

        /*
         * 우선순위 큐 - 종료시간만 오름차순으로 정렬
         * 위에서 이미 정렬을 했지만 종료시간만 따로 우선순위 큐로 관리하는 이유?
         * 1. 최소 강의실 배정 -> 한 강의실에 가장 많은 강의를 배정 -> 현재 강의 종료시간을 다음 강의 시작
         * 2. 정렬 우선 기준이 시작시간이므로, 시작시간이 더 빠라서 더 늦는 종료시간인데도 선순위가 가능하기 때문
         */

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.offer(lecture.get(0)[1]); // 가장 처음 강의만 넣기

        for (int i = 1; i < n; i++) {
            if (pq.peek() <= lecture.get(i)[0]) {
                pq.poll();
            }
            pq.offer(lecture.get(i)[1]);
        }

        // 최종적으로 우선순위 큐에 남은 강의들이 가능한 강의실의 개수! (그 다음 가능한 강의가 없어서 마지막까지 poll 안됨)
        System.out.println(pq.size());
    }
}
