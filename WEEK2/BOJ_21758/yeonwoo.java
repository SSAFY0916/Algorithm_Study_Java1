package bj21758_꿀따끼;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.io.IOException;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) throws IOException{
        int n = Integer.parseInt(br.readLine());

        int[] arr =new int[n];
        int[] sum = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        int s = 0;
        int max = 0;
        int maxIdx = 0;
        for(int i=0;i<n;i++) {
            arr[i] = Integer.parseInt(st.nextToken());
            s+=arr[i];//총합에 현재값추가
            sum[i] = s;

            if(i!=0&&i!=n-1) {
                max = Math.max(max, arr[i]);
                maxIdx = i;
            }
        }

        int result = 0;

        //뒷꿀이 앞꿀이 --- 벌통
        for(int i=1;i<n-1;i++) {//뒷꿀이(꿀단지에서 더 먼 꿀벌)
            int back = s-sum[0]-arr[i];
            int front = s-sum[i];

            result = Math.max(result, back+front);

        }

        //벌통 --- 앞꿀이 뒷꿀이
        for(int i=n-2;i>0;i--) {
            int back = sum[n-2]-arr[i];//뒷꿀이위치까지구간합 - 뒷꿀이위치-앞꿀이위치
            int front = sum[i-1];

            result = Math.max(result, back+front);

        }

        int left = sum[maxIdx]-arr[0];//좌끝꿀이
        int right = s-sum[maxIdx-1]-arr[n-1];//우끝꿀이
        int lastCase = left+right;
        result = Math.max(result, lastCase);
        System.out.println(result);




    }

}
