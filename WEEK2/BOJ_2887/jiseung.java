import java.io.*;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {

    static class Edge implements Comparable<Edge> { // 행성 간의 터널을 저장하는 클래스
        int left, right, dist;

        public Edge(int left, int right, int dist) {
            this.left = left;
            this.right = right;
            this.dist = dist;
        }

        @Override
        public int compareTo(Edge edge) {
            return Integer.compare(dist, edge.dist);
        }
      
    }
    
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st;
        int[][] planets = new int[n][4];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            planets[i][0] = Integer.parseInt(st.nextToken());
            planets[i][1] = Integer.parseInt(st.nextToken());
            planets[i][2] = Integer.parseInt(st.nextToken());
            planets[i][3] = i;
        }
        PriorityQueue<Edge> pq = new PriorityQueue<>(); // 비용 기준 정렬
        Arrays.sort(planets, (int[] o1, int[] o2) -> { // x좌표 기준 정렬
						return Integer.compare(o1[0], o2[0]);
				});
        for(int i=1; i<n; i++) {
	        	pq.add(new Edge(planets[i-1][3], planets[i][3], planets[i][0] - planets[i-1][0]));
        }
        Arrays.sort(planets, (int[] o1, int[] o2) -> { // y좌표 기준 정렬
						return Integer.compare(o1[1], o2[1]);
				});
		    for(int i=1; i<n; i++) {
			    	pq.add(new Edge(planets[i-1][3], planets[i][3], planets[i][1] - planets[i-1][1]));
		    }
		    Arrays.sort(planets, (int[] o1, int[] o2) -> { // z좌표 기준 정렬
						return Integer.compare(o1[2], o2[2]);
				});
				for(int i=1; i<n; i++) {
						pq.add(new Edge(planets[i-1][3], planets[i][3], planets[i][2] - planets[i-1][2]));
				}
        int[] union = new int[n]; // 유니온 파인드에 사용할 배열
        for(int i=0; i<n; i++) {
	        	union[i] = i; // 초기엔 자기 자신을 저장
        }
        int answer = 0;
        for(int i=0; i<n-1; i++) { // 크러스컬
            Edge edge;
            int left_root = -1;
            int right_root = -1;
            while(true) { // pq에서 서로 다른 트리를 연결하는 터널을 찾아올 때까지 반복
                edge = pq.poll();
                left_root = edge.left;
                right_root = edge.right;
                while(left_root != union[left_root]) {
	                	union[left_root] = union[union[left_root]]; // 부모가 아닌 조상을 저장하도록 함
	                	left_root = union[left_root];
                }
                while(right_root != union[right_root]) {
	                	union[right_root] = union[union[right_root]];
	                	right_root = union[right_root];
                }
                if(left_root != right_root) break; // 서로 다른 트리에 속하면 break
            }
            union[left_root] = right_root; // 두 트리를 연결
            answer += edge.dist;
        }
        System.out.println(answer);
    }
}
