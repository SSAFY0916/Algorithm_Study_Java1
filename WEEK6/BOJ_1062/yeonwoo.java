package daily.y_2023.m_03.d_18;

import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int N,K;
    static boolean[] alphabet = new boolean[26];//0~25

    static String[] wordList;

    static int max = Integer.MIN_VALUE;
    public static void main(String[] args) throws IOException{
        // System.out.println((int)'a'-97); -- 0
        // System.out.println((int)'z'-97); -- 25
        alphabet[(int)'a'-97]=true;
        alphabet[(int)'n'-97]=true;
        alphabet[(int)'t'-97]=true;
        alphabet[(int)'i'-97]=true;
        alphabet[(int)'c'-97]=true;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        wordList = new String[N];
        K = Integer.parseInt(st.nextToken());
        for(int i=0;i<N;i++){
            wordList[i] = br.readLine();
        }
        if(K<5){
            System.out.println(0);
        }else{
            K-=5;


            dfs(0,0);
            System.out.println(max);
        }

    }

    static void dfs(int idx, int depth){

        if(depth==K||idx==alphabet.length){
            // printAlpha();
            int able = checkWordList();//현재 알파벳 목록으로 가능한 단어 수 체크
            max = Math.max(max,able);
            return;
        }

        if(alphabet[idx]){
            dfs(idx+1,depth);
        }else{
            alphabet[idx]=true;
            dfs(idx+1,depth+1);
            alphabet[idx]=false;
            dfs(idx+1,depth);
        }

    }

    static int checkWordList(){
        int cnt= 0;
        for(String word:wordList){
            // System.out.println("word = " + word);
            cnt++;
            for(int i=0;i<word.length();i++){
                char cur = word.charAt(i);
                // System.out.printf("cur=%c ,%s\n",cur,alphabet[cur-97]);
                if(!alphabet[cur-97]){
                    cnt--;
                    break;
                }
            }
        }
        return cnt;
    }

    static void printAlpha(){
        for(int i=0;i<alphabet.length;i++){
            if(alphabet[i]){
                System.out.print((char)(i+97)+" ");
            }
        }
        System.out.println();
    }
}
