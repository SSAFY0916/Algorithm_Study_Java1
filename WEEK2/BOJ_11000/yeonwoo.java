package bj_11000_강의실배정;
import java.io.*;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.HashMap;
public class Main {

    static class Lecture implements Comparable<Lecture>{
        int s;
        int e;

        public Lecture(int s, int e) {
            this.s = s;
            this.e = e;
        }

        public int compareTo(Lecture o) {
            if(this.s < o.s)return -1;//뽑았을 때 나보다 빠른 시작 강의는 없다
            if(this.s==o.s)return -(this.e-o.e); //뽑은시점기준: 나와 같은 시작인 애들 중애는 내가 제일 빨리 끝난다
            return 1;
        }
    }


    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer st;

    static PriorityQueue<Lecture> pq = new PriorityQueue<>(); //강의들
    static PriorityQueue<Integer> rights = new PriorityQueue<>();//강의실들

    public static void main(String[] args) throws IOException{


        int n = Integer.parseInt(br.readLine());



        for(int i=0;i<n;i++) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());//시작시간
            int e = Integer.parseInt(st.nextToken());//끝시간

            pq.offer(new Lecture(s,e));//강의추가
        }



        rights.offer(Integer.MAX_VALUE);//강의실 최소 1개 쓰게 만드는 구문


        int count = 0;//결과
        while(!pq.isEmpty()) {
            Lecture curLec = pq.poll();//나보다빠른시작인애들은 다 들어있음
            int right = rights.peek();//나 이하 시작인 애들 중 가장 빨리 끝나는 강의

            if(curLec.s<right) {//가장 빨리끝나는강의보다 내가 빨리시작
                count ++;
            }else {//강의실 바통터치 가능
                rights.poll();//바통터치강의 제거
            }
            rights.offer(curLec.e);//강의끝나는시간추가

        }
        bw.write(Integer.toString(count));
        bw.close();
    }

}
