import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main{
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int N;//학생 수
    static int[] map;//코딩실력
    static long cnt = 0;//답

    public static void main(String[] args) throws IOException{
        N = Integer.parseInt(br.readLine());
        map = new int[N];
        st = new StringTokenizer(br.readLine());
        for(int i=0;i<N;i++){
            map[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(map);

        for(int i=0;i<N;i++){
            //i번째 수를 가장 작은 수로 놓고 탐색

            if(map[i] > 0) break;//가장 작은 수가 양수이면 안됨
            int num = map[i]; //가장 작은 수 == 가작수
            //가작수 + 좌수 + 우수 == 0이어야함
            int left = i+1;//좌수
            int right = N-1;//우수

            while(left<right){//왜 <=이 아니지??
                int sum = num + map[left] + map[right];//코딩실력합

                if(sum == 0){
                    //합이 0인경우

                    //??
                    int l = 1;//좌수와 같은 수 몇개인지 찾는용
                    int r = 1;//우수와 ''  ''  ''     ''

                    if(map[left] == map[right]){
                        //map[left] == map[right]? ==>  둘 사이의 수(둘을 포함)에서 2개뽑는 조합

                        int n = right-left+1;//둘을 포함하여 둘 사이의 수의 갯수
                        cnt += nC2(n);//nC2 구함
                        break;
                    }

                    while(map[left] == map[left+1]){
                        l++;
                        left++;
                    }

                    while(map[right]==map[right-1]){
                        r++;
                        right--;
                    }
                    cnt += l*r;//l과 r 짝 경우의 수
                }

                //이거 else로하면 시간 늘어나는이유 파악
                if(sum>0){
                    //합이 0보다 크다

                    right --;//젤큰수 인덱스 --
                }else{
                    //합이 0보다 작다


                    left ++;//중간수(좌수) 인덱스 ++;
                }
            }

        }
        System.out.println(cnt);
    }

    //nc2
    public static long nC2(int n){
        return n * (n-1) / 2;
    }


}