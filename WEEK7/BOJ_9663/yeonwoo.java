package daily.y_2023.m_03.bj_9663_nQueen;
import java.io.*;
import java.util.Arrays;


public class Main {
    static int[] arr;
    static int N;
    static int count = 0;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) throws IOException{
        N = Integer.parseInt(br.readLine());
        arr = new int[N];//idx=열, arr[idx] = 행

        nQueen(0);//0열부터 행지정
        System.out.println(count);
    }

    public static void nQueen(int idx){
        if(idx==N){//
            count ++;
            return;
        }

        for(int i=0;i<N;i++){//depth에
            arr[idx] = i;//depth에 행지정
            if(possible(idx)){
                nQueen(idx+1);//다음 idx의 행값 지정하러 ㄱㄱ
            }
        }
    }
    
    static boolean possible(int idx){
        for(int i=0;i<idx;i++){//idx까지 지정된상태이므로 idx전까지 확인
            if(arr[i]==arr[idx]){//행이 같은, 열이 존재함
                return false;
            }
            //대각 -- 열의차 == 행의차
            if(Math.abs(arr[i]-arr[idx])==Math.abs(i-idx)){
                return false;
            }
        }
        return true;
    }
}
