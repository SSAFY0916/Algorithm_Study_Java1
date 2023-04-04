package res;

import java.io.*;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/*
크루스칼
가중치 내림차순 간선 뽑
간선의 s와 e를 잇는다
sv와 ev가 이어지면 현재 간선의 weight가 답이다
*/


public class Main{
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static class Edge implements Comparable<Edge>{
        int s,e,w;//출발 도착 가중

        public Edge(int s, int e, int w){
            this.s = s;
            this.e = e;
            this.w = w;
        }

        //가중치 기준 내림차순
        public int compareTo(Edge o){
            return o.w-w;
        }
    }
    static int N;//노드 수
    static int M;//간선 수
    static int sv,ev;//시작노드번호 도착노드번호

    //유니온파인드
    static int[] parent;

    public static void main(String[] args) throws IOException{
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());//node
        M = Integer.parseInt(st.nextToken());//edge

        //크루스칼 재료준비
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        for(int i=0;i<M;i++){
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());

            Edge edge = new Edge(s,e,w);
            pq.offer(edge);
        }

        //유니온파인드 재료
        parent = new int[N+1];//노드는 1번부터 시작하는 문제
        //일단 자기자신을 부모로 등록
        for(int i=1;i<=N;i++){
            parent[i] = i;
        }

        //시작노드 도착노드
        st = new StringTokenizer(br.readLine());
        sv = Integer.parseInt(st.nextToken());
        ev = Integer.parseInt(st.nextToken());

        int res = kruskal(pq);
        System.out.println(res);

    }
    static int kruskal(PriorityQueue<Edge> pq){
        while(!pq.isEmpty()){
            Edge cur = pq.poll();
            int curs = cur.s;
            int cure = cur.e;
            int curw = cur.w;

            unionFind(curs,cure);
            if(find(sv)==find(ev)){
                return curw;
            }
        }

        return -1;//올 일 없음
    }

    static void unionFind(int numA, int numB){
        int pA = find(numA);
        int pB = find(numB);
        //둘이 동일그룹이면 리턴
        if(pA ==pB) return;

        union(pA,pB);
    }

    static int find(int num){
        //num과 parent[num]이 같으면 리턴
        if(num==parent[num]) return num;
        return parent[num] = find(parent[num]);
    }

    static void union(int pA,int pB){
        if(pA<pB){
            parent[pB] = pA;
        }else{
            parent[pA] = pB;
        }
    }



}