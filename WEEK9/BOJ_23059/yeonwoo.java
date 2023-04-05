import java.io.*;
import java.util.*;

/* Topological Sort

#topological sort 재료
- 인접리스트로 만든 그래프
    Map<String,ArrayList<String>>
- 노드별 차수
    Map<String,Integer>

#문제에만 특별히 적용되는 부분
- 동일위상 노드에 대해 사전순정렬
    queue 대신 priorityQueue로 극복
        조건1. 위상 오름차순
        조건2. String 오름차순


1.
 */

public class Main{
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static HashMap<String,ArrayList<String>> map = new HashMap<>();
    static HashMap<String,Integer> indegree = new HashMap<>();

    static int N;

    public static void main(String[] args) throws IOException{
        N = Integer.parseInt(br.readLine());//관계 갯수

        //그래프, inner차수 계산
        for(int i=0;i<N;i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            String lowItem = st.nextToken();
            String highItem = st.nextToken();

            //그래프
            map.putIfAbsent(lowItem,new ArrayList<>());
            map.putIfAbsent(highItem,new ArrayList<>());
            map.get(lowItem).add(highItem);

            //차수
            indegree.putIfAbsent(lowItem,0);
            indegree.putIfAbsent(highItem,0);

            //상위템 차수 증가
            indegree.put(highItem,indegree.get(highItem)+1);
        }

        //시작노드 입력
        topologicalSort();

    }
    static void topologicalSort(){
        Queue<Node> q = new ArrayDeque<>();//BFS를 위한 큐
        PriorityQueue<Node> result = new PriorityQueue<>();//결과를 저장할 우선순위 큐

        //초기 진입차수가 0인 노드를 큐에 삽입
        for(String key:indegree.keySet()){
            int curDegree = indegree.get(key);
            if(curDegree==0) q.offer(new Node(key,0));
        }

        while(!q.isEmpty()){
            Node cur = q.poll();
            result.offer(cur);//Node cur에는 priority가 반영되어있음

            //cur의 인접 노드 차수 감소, 0이면 큐에 삽입
            for(String key:map.get(cur.key)){
                indegree.put(key,indegree.get(key)-1);
                if(indegree.get(key)==0){
                    q.offer(new Node(key,cur.priority+1));//출력우선순위를 여기서 올려준다
                }

            }
        }

        if(result.size() != map.size()){
            System.out.println(-1);
            return;
        }
        StringBuilder sb = new StringBuilder();
        while(!result.isEmpty()){
            sb.append(result.poll().key+"\n");
        }
        System.out.println(sb.toString());
    }

    static class Node implements Comparable<Node>{
        String key;
        int priority;

        public Node(String key, int degree){
            this.key = key;
            this.priority = degree;
        }

        public int compareTo(Node o){
            if(priority==o.priority){
                return key.compareTo(o.key);
            }
            return priority-o.priority;
        }
    }
}