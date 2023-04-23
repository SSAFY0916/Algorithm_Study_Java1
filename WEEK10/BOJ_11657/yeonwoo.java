import java.io.*;
import java.util.StringTokenizer;
import java.util.ArrayList;

public class Main{
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer st;


    static int N; //정점 수
    static int M; //간선 수
    static ArrayList<Node>[] map;

    static long[] dist;//각 노드까지 거리
    static int INF = Integer.MAX_VALUE;

    static class Node{
        int end;
        int weight;

        public Node(int end, int weight){
            this.end = end;
            this.weight = weight;
        }
    }

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());//정점
        M = Integer.parseInt(st.nextToken());//간선

        //노드까지 거리
        dist = new long[N+1];
        for(int i=1;i<=N;i++){
            dist[i] = INF;
        }

        //인접리스트
        map = new ArrayList[N+1];//1번부터 N번
        for(int i=1;i<=N;i++){
            map[i] = new ArrayList<>();
        }

        //정점별 인접노드 배치
        for(int i=0;i<M;i++){
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());

            map[start].add(new Node(end,weight));
        }

        //벨만포도
        int startNode = 1;//이 문제에서는 1에서 출발로 고정
        boolean isCycle = bellmanFord(startNode);//음싸이클존재시 true

        //결과세팅
        StringBuilder sb = new StringBuilder();
        if(isCycle){
            sb.append("-1");
        }else{
            for(int i=1;i<=N;i++){
                if(i==startNode) continue;
                if(dist[i]==INF){
                    sb.append("-1\n");
                }else{
                    sb.append(dist[i]+"\n");////각 노드까지의 거리
                }

            }
        }
        bw.write(sb.toString());
        bw.close();//출력
    }

    static boolean bellmanFord(int s){
        //시작노드까지의 거리는 0
        dist[s] = 0;
        /*
        (a노드 -> b노드) 최대 n-1번의 길을 건넘
        그 이상 건넌다면 음사이클 존재를 나타냄
         */
        boolean change = false;
        for(int i=0;i<N-1;i++){

            change = false;
            //모든 정점 순회하면서 이어진 노드와의 거리 설정
            for(int j=1;j<=N;j++){
                long curDist = dist[j];
                if(curDist==INF) continue;//아직 닿지 않은 노드

                ArrayList<Node> nodeList = map[j];//j번노드의 간선들
                for(Node n:nodeList){
                    int end = n.end;//인접노드번호
                    int weight = n.weight;//이번 간선의 가중치


                    if(dist[end] > curDist + weight){
                        dist[end] = curDist + weight;
                        change = true;
                    }

                }
            }
            if(!change) break;//업뎃 없으면 더 돌아도 의미 x
        }
        //마지막 순회까지 change있었으면 함더 돌면서 음의 사이클 탐색
        if(change){
            //모든 정점 순회하면서 이어진 노드와의 거리 설정
            for(int j=1;j<=N;j++){
                long curDist = dist[j];
                if(curDist==INF) continue;//아직 닿지 않은 노드

                ArrayList<Node> nodeList = map[j];//j번노드의 간선들
                for(Node n:nodeList){
                    int end = n.end;//인접노드번호
                    int weight = n.weight;//이번 간선의 가중치


                    if(dist[end] > curDist + weight){
                        return true;//음의사이클 존재
                    }
                }
            }
        }
        return false;
    }
}