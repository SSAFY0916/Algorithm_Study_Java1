import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        TreeSet<Integer> sights = new TreeSet<>(); // 명소들을 저장하는 트리셋
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            if (st.nextToken().equals("1")) { // 명소면 인덱스를 셋에 추가
                sights.add(i);
            }
        }
        
        int location = 0;
        while (q-- > 0) {
            st = new StringTokenizer(br.readLine());
            int type = Integer.parseInt(st.nextToken());
            if (type == 1) { // 명소 지정, 명소 해제
                int i = Integer.parseInt(st.nextToken()) - 1;
                if (sights.contains(i)) {
                    sights.remove(i);
                } else {
                    sights.add(i);
                }
            } else if (type == 2) { // 이동
                int x = Integer.parseInt(st.nextToken());
                location += x;
                location %= n;
            } else { // 명소와의 거리 구하기
                if (sights.isEmpty()) { // 명소가 없으면
                    bw.write("-1\n");
                } else if (sights.contains(location)) { // 도현이가 있는 장소가 명소일 때
                    bw.write("0\n");
                } else {
                    Integer sight = sights.higher(location);
                    if (sight == null) { // 도현이가 있는 위치부터 n번 구역까지 명소가 없음
                        bw.write((sights.first()+n-location) + "\n");
                    } else { 
                        bw.write((sight-location) + "\n");
                    }
                }
            }
        }
        bw.flush();
        bw.close();
        br.close();
    }
}