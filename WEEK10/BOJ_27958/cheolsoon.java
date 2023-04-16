import java.util.*;
import java.io.*;

public class Main {
    static int N,K, initMap[][], playMap[][], scoreMap[][];
    static int bullet[], currScore, maxScore;
    static int dx[] = {-1,1,0,0};
    static int dy[] = {0,0,-1,1};
    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = null;
        
        N = Integer.parseInt(in.readLine());
        K = Integer.parseInt(in.readLine());
        initMap = new int[N][N]; // 초기보드
        bullet = new int[K];//총알
        for(int i=0;i<N;i++) {
            st = new StringTokenizer(in.readLine());
            for(int j=0;j<N;j++) {
            	initMap[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        
        st = new StringTokenizer(in.readLine());
        for(int i=0;i<K;i++) {
            bullet[i] = Integer.parseInt(st.nextToken());
        }
        
        // 아무행, 모든 곳에서 총을 쏴본다.(위치 중복 가능)
        dfs(0, 0, new int [K]);
        
        System.out.println(maxScore);
    }
    private static void dfs(int idx, int cnt, int[] turn) {
    	
        if(cnt == K) {//사격 순서 행이 결정되었을때 사격 실시
        	currScore = 0;
        	/*
        	 * playMap: 여러 행을 바꿔가며, 사격을 하므로, 매 사격시 보드판 상황이 변동됨.
        	 * scoreMap: 목표물이 없어졌을때 초기 HP를 기억하고 있어야 함.
        	 * */
        	playMap = new int[N][N];
        	scoreMap = new int[N][N];
            for(int i=0;i<N;i++) {
            	playMap[i] = Arrays.copyOf(initMap[i], initMap[i].length);
            	scoreMap[i] = Arrays.copyOf(initMap[i], initMap[i].length);
            }
            for(int i=0;i<K;i++) {// 정해진 순서대로 사격 실시
                play(turn[i], bullet[i]);                
            }
            // 매 판 점수 갱신(고득점으로)
            maxScore = Math.max(maxScore, currScore);
            return;
        }
        
        // 현재 행을 선택
        for(int i=0;i<N;i++) {
            turn[idx] = i;
            dfs(idx+1, cnt+1, turn);
        }
    }
    private static void play(int row, int power) {
        for(int i=0;i<N;i++) {
            int hp = playMap[row][i];
            if(hp > 0) {
                if(hp >= 10) {// 이벤트 표적
                    currScore+=scoreMap[row][i];
                    playMap[row][i] = 0;
                    return; // 종료
                }else { // 일반 표적
                    if(hp <= power) {//점수를 얻을 수 있다면
                        currScore += scoreMap[row][i];
                        playMap[row][i] = 0;
                        // 점수 획득 후 상하좌우 새로운 표적 생성
                        make(row, i);
                        return;// 점수 획득 후 종료
                    }else {// 체력 감소
                    	playMap[row][i] -= power;
                        return;
                    }
                }
            }else{//쏠 수 있는 표적이 없으면 continue
                continue;
            }
        }
    }
    // 표적을 맞췄을 때 사방으로 새로운 표적 생성
    private static void make(int x, int y) {
        for(int i=0;i<4;i++) {
            int nx = x+dx[i];
            int ny = y+dy[i];
            if(nx <0 || nx>=N || ny<0 || ny>=N) continue;
            if(playMap[nx][ny] > 0) continue;
            playMap[nx][ny] = (int)scoreMap[x][y]/4;
            scoreMap[nx][ny] = (int)scoreMap[x][y]/4;
        }
    }
}
