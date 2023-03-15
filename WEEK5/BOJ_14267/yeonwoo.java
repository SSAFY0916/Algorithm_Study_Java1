package daily.y_2023.m_03.d_09.bj_회사문화;
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int n,m;
    static ArrayList<Integer>[] arr;
    static int[] point;
    public static void main(String[] args) throws IOException{
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        arr = new ArrayList[n+1];
        for(int i=1;i<=n;i++){
            arr[i] = new ArrayList<>();
        }

        point = new int[n+1];
        st = new StringTokenizer(br.readLine());
        st.nextToken();
        for(int i=2;i<=n;i++){
            arr[Integer.parseInt(st.nextToken())].add(i);
        }

        for(int i=0;i<m;i++){
            st = new StringTokenizer(br.readLine());
            int one = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            point[one]+=w;
        }
        // cal(2);
        for(int i=2;i<=n;i++){
            cal(i);
        }
        printPoint();
    }
    static void cal(int one){
        ArrayList<Integer> cur = arr[one];
        for(int i=0;i<cur.size();i++){
            point[cur.get(i)]+=point[one];
        }

    }
    static void printPoint(){
        for(int i=1;i<=n;i++){
            System.out.print(point[i]+ " ");
        }
    }
}
