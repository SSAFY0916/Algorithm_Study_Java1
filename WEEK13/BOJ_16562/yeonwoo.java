import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer st;

    public static void main(String[] args) throws IOException{
        //unionFind 재료
        int[] parent;//부모
        int[] friendFee;//친구비(union 기준)

        //문제 인풋1 - 학생수, 관계 수, 가진 돈
        st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());//학생 수
        int M = Integer.parseInt(st.nextToken());//관계 수
        int k = Integer.parseInt(st.nextToken());//가진 돈

        //학생 수에 따른 변수 초기화(1인덱스 시작)
        parent = new int[N+1];
        friendFee = new int[N+1];

        //부모 초기화
        for(int i=1;i<=N;i++){
            parent[i] = i;
        }

        //친구비 초기화
        st = new StringTokenizer(br.readLine());
        for(int i=1;i<=N;i++){
            friendFee[i] = Integer.parseInt(st.nextToken());
        }

        for(int i=0;i<M;i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            union(a,b,friendFee, parent);//친구비를 기준으로 결합
        }

        //각 그룹의 대표(최소친구비)의 친구비 합산하여 최소 친구비 계산
        int sum = 0;
        for(int i=1;i<=N;i++){
            //그룹의 대표면 친구로 삼는다
            if(parent[i]==i) sum+=friendFee[i];
        }

        if(sum>k){
            bw.write("Oh no");
        }else{
            bw.write(Integer.toString(sum));
        }
        bw.close();//close할 떄 자동 flush됨
    }

    private static void union(int a, int b, int[] friendFee, int[] parent){
        //뿌리찾기
        int rootA = find(a, parent);
        int rootB = find(b, parent);

        if(rootA != rootB){
            //둘이 다른 그룹 ==> 결합이 필요하다

            //더 싼 쪽으로 결합
            if(friendFee[rootA] < friendFee[rootB]){
                //a의 그룹이 더 싸다

                //b그룹의 부모로 a그룹 등록
                parent[rootB] = parent[rootA];
            }else{
                //b그룹이 더 싸거나 같다

                //a그룹의 부모로 b그룹 등록
                parent[rootA] = parent[rootB];
            }
        }
    }

    private static int find(int a, int[] parent){
        if(a == parent[a]) return a;
        else return parent[a] = find(parent[a],parent);
    }

}