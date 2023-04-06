import java.io.*;
import java.util.*;

/*
위상정렬
    싸이클이 없고, 유향그래프

인접리스트로 그래프 표현
정점별 innerDegreeTable 생성

q에 innerDegree가 0인 애들 추가
bfs탐색 -
    cur의 인접 정점에 대하여 innerDegree를 1씩 줄이고, 0이면 q에 넣는다
 */
public class Main{
    static int N;
    static HashMap<String, ArrayList<String>> map = new HashMap<>();
    static HashMap<String,Integer> innerDegree = new HashMap<>();

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static void main(String[] args) throws IOException{
        StringTokenizer st;
        N = Integer.parseInt(br.readLine());
        for(int i=0;i<N;i++){
            st = new StringTokenizer(br.readLine()," ");
            String low = st.nextToken();//하위템
            String high = st.nextToken();//상위템

            //그래프에 없으면 넣는다
            map.putIfAbsent(low,new ArrayList<>());
            map.putIfAbsent(high,new ArrayList<>());
            //하위템의 인접 정점으로 상위템 등록
            map.get(low).add(high);


            //innerDegree setting
            innerDegree.putIfAbsent(low,0);
            innerDegree.putIfAbsent(high,0);
            //상위템의 innerDegree ++
            int curHighInnerDegree = innerDegree.get(high);
            innerDegree.put(high,curHighInnerDegree+1);
        }

        bfs();
    }
    static void bfs() throws IOException{
        Queue<Item> q = new ArrayDeque<>();//bfs용 큐
        PriorityQueue<Item> pq = new PriorityQueue<>();//출력우선순위 고려

        //애초에 innerDegree가 0인애들을 q에 넣는다
        for(String itemName:innerDegree.keySet()){
            int curItemInnerDegree = innerDegree.get(itemName);
            if(curItemInnerDegree==0){
                q.offer(new Item(itemName,curItemInnerDegree));
            }
        }

        while(!q.isEmpty()){
            Item cur = q.poll();
            pq.offer(cur);//출력용 pq에 넣기

            for(String nextItemName:map.get(cur.name)){
                //cur의 인접정점 탐색

                int nextItemInnerDegree = innerDegree.get(nextItemName);
                //차수 1씩 줄인다
                innerDegree.put(nextItemName,nextItemInnerDegree-1);
                //0이면 q에 넣는다
                if(innerDegree.get(nextItemName)==0){
                    q.offer(new Item(nextItemName,cur.priority+1));
                }
            }
        }
        /*사이클이 있다면 사이클 시작지점에서 degree가 0이되지 않는 문제 발생한다
        사이클 시작지점의 innerDegree가 2번 계산되기 때문이다
        그 결과 q에 정점 공급이 끊기고
        그 결과 pq의 크기가 map보다 작아지게된다(bfs가 멈추므로).
         */
        if(pq.size()<map.size()){
            System.out.println(-1);
            return;
        }
        
        //출력
        while(!pq.isEmpty()){
            bw.write(pq.poll().name+"\n");
        }
        bw.close();//close하면 자동 flush됨
    }

    static class Item implements Comparable<Item>{
        String name;//아이템명
        int priority;//출력우선순위
        public Item(String name, int priority){
            this.name = name;
            this.priority = priority;
        }

        public int compareTo(Item o){
            //기본적으로 출력우선순위 순
            if(priority==o.priority){
                //출력우선순위가 같으면 이름 기준 오름차순
                return name.compareTo(o.name);
            }
            
            return priority-o.priority;
        }

    }

}