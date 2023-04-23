import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int[] bullets;//�Ѿ˵���
    static Target[][] board;//��
    static int N;//��ũ��
    static int K;//�Ѿ� ��
    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, 1, 0, -1};
    static int result = 0;

    public static void main(String[] args) throws IOException {

        input();//��ǲ����

        //����, �Ѿ˹�ȣ
        dfs(0, 0);

        System.out.println(result);
    }

    private static void dfs(int point, int bulletNumber) {
        if (bulletNumber >= K) { // k ��° �Ѿ˱��� �� ���� ��
            result = Math.max(result, point);
            return;
        }

        for (int i = 0; i < N; i++) { // N ��° �࿡�� �i�� ��
            for (int j = 0; j < N; j++) { // Ÿ���� �ִ� ������ N ��° ������ Ž��

                if (board[i][j].curLife > 0) { // i,j�� Ÿ���� ���� ��

                    if (board[i][j].curLife >= 10) {
                        // ���ʽ� �� ��
                        //�ܼ��� ����� �б�����ϰ� ����
                        
                        int before = board[i][j].curLife;//��������
                        board[i][j].curLife = 0;//Ÿ������
                        dfs(point + board[i][j].initialLife, bulletNumber + 1);//����Ʈ�߰�,�Ѿ��ε����߰�
                        board[i][j].curLife = before;//����
                        break;

                    } else if (board[i][j].curLife <= bullets[bulletNumber]) { 
                        // Ÿ���� �Ѿ˿� �°� ���� ��
                        //4���غ��� 0�ƴϸ� [��,ĭ,¥,��] ���� ������ �� IDX�� DIR�� ���缭 ����
                        
                        Target[] isCreated = new Target[4]; // ��濡 ���� ���ο� Ÿ�� ��ġ
                        int createTargetPoint = board[i][j].initialLife / 4;//4��

                        if (createTargetPoint >= 1) {//4���� 0�ƴ�
                            for (int k = 0; k < 4; k++) { // ��濡 ���� �� �ִ� �� Ȯ��
                                //�б� ���⼭ ����°� �ƴ�(Ÿ�� ������ 4�濡 '����'�� ����)
                                int createX = i + dx[k];//NR
                                int createY = j + dy[k];//NC

                                //�������鼭 �ش�ĭ ������0 == �� Ÿ�� ���� �� ����
                                if (isPossible(createX, createY)) {
                                    isCreated[k] = board[createX][createY];//K��������� ����
                                    //K���� MAP�� �� �� ����
                                    board[createX][createY] = new Target(createTargetPoint);
                                }
                            }
                            
                            //�б����
                            int before = board[i][j].curLife;//����ĭ ����������
                            board[i][j].curLife = 0;//����ĭ Ÿ�� ����
                            dfs(point + board[i][j].initialLife, bulletNumber + 1);//�б����
                            board[i][j].curLife = before;//����ĭ����
                            for (int k = 0; k < 4; k++) {
                                //isCreated[d]�� ��ü ������ �� Ÿ�� �����Ѱ�
                                if (isCreated[k] != null) {
                                    //�� Ÿ�� ���������� ����
                                    board[i + dx[k]][j + dy[k]] = isCreated[k];
                                }
                            }
                            break;

                        } else {
                            //����ĭ 4���ϴϱ� 0
                            //�ܼ� �б� ����
                            int before = board[i][j].curLife;
                            board[i][j].curLife = 0;
                            dfs(point + board[i][j].initialLife, bulletNumber + 1);
                            board[i][j].curLife = before; // �ѹ�
                            break;

                        }

                    } else {                                            
                        // �Ѿ� �������� ���ڶ� ��
                        board[i][j].curLife -= bullets[bulletNumber];
                        dfs(point, bulletNumber + 1);
                        board[i][j].curLife += bullets[bulletNumber];
                        break;

                    }
                }
            }
        }


    }

    //�� ���� ���̰� Ÿ���ǰ� 0�̴� == Ź���� ����
    private static boolean isPossible(int x, int y) {
        return x >= 0 && y >= 0 && x < N && y < N && board[x][y].curLife == 0;
    }

    private static void input() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(bf.readLine());
        K = Integer.parseInt(bf.readLine());
        board = new Target[N][N];
        bullets = new int[K];

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(bf.readLine());
            for (int j = 0; j < N; j++) {
                int point = Integer.parseInt(st.nextToken());
                board[i][j] = new Target(point);
            }
        }

        StringTokenizer st = new StringTokenizer(bf.readLine());
        for (int i = 0; i < K; i++) {
            bullets[i] = Integer.parseInt(st.nextToken());
        }
    }


    private static class Target {

        int initialLife;
        int curLife;

        public Target(int initialLife) {
            this.initialLife = initialLife;
            this.curLife = initialLife;
        }
    }
}