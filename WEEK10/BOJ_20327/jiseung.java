import java.io.*;
import java.util.*;

public class Main {
    
    static int N, power2N;
    static int[][] arr, temp;
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        int R = Integer.parseInt(st.nextToken());
        power2N = (int) Math.pow(2, N); // 전체 배열의 길이 2^n
        arr = new int[power2N][power2N];
        for (int i = 0; i < power2N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < power2N; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        temp = new int[power2N][power2N]; // 전체 배열과 크기는 같은 임시로 값을 저장해놓을 배열
        while (R-- > 0) {
            st = new StringTokenizer(br.readLine());
            int K = Integer.parseInt(st.nextToken());
            int L = Integer.parseInt(st.nextToken());
            operate(K, L);
        }
        for (int i = 0; i < power2N; i++) {
            for (int j = 0; j < power2N; j++) {
                bw.write(arr[i][j] + " ");
            }
            bw.write("\n");
        }
        bw.flush();
        bw.close();
        br.close();
    }

    static void operate(int K, int L) {
        int lenSubArr = (int) Math.pow(2, L); // 부분배열의 길이
        if (K <= 4) { // 부분 배열 내에서의 연산들
            if (lenSubArr == 1) return; // 부분배열의 길이가 1이면 아무 효과가 없음
            if (K == 1) { // 상하 반전
                for (int i = 0; i < power2N; i+=lenSubArr) { // 한 줄에서 부분 배열의 개수 만큼 반복
                    for (int j = 0; j < lenSubArr; j++) { // 부분 배열의 길이 만큼 반복
                        System.arraycopy(arr[i + lenSubArr - 1 - j], 0, temp[i+j], 0, power2N);
                    }
                }
            } else if (K == 2) { // 좌우 반전
                for (int i = 0; i < power2N; i+=lenSubArr) {
                    for (int j = 0; j < lenSubArr; j++) {
                        for (int k = 0; k < power2N; k++) {
                            temp[k][i+j] = arr[k][i + lenSubArr - 1 - j];
                        }
                    }
                }
            } else if (K == 3) { // 시계 방향 90도
                for (int i = 0; i < power2N; i+=lenSubArr) {
                    for (int j = 0; j < power2N; j+=lenSubArr) {
                        for (int k = 0; k < lenSubArr / 2; k++) {
                            for (int l = 0; l < lenSubArr - 1 - k * 2; l++) {
                                temp[i+k][j+lenSubArr-1-l-k] = arr[i+l+k][j+k];
                                temp[i+lenSubArr-1-l-k][j+lenSubArr-1-k] = arr[i+k][j+lenSubArr-1-l-k];
                                temp[i+lenSubArr-1-k][j+l+k] = arr[i+lenSubArr-1-l-k][j+lenSubArr-1-k];
                                temp[i+l+k][j+k] = arr[i+lenSubArr-1-k][j+l+k];
                            }
                        }
                    }
                }
            } else { // 반시계 방향 90도
                for (int m = 0; m < 3; m++) {
                    for (int i = 0; i < power2N; i+=lenSubArr) {
                        for (int j = 0; j < power2N; j+=lenSubArr) {
                            for (int k = 0; k < lenSubArr / 2; k++) {
                                for (int l = 0; l < lenSubArr - 1 - k * 2; l++) {
                                    temp[i+k][j+lenSubArr-1-l-k] = arr[i+l+k][j+k];
                                    temp[i+lenSubArr-1-l-k][j+lenSubArr-1-k] = arr[i+k][j+lenSubArr-1-l-k];
                                    temp[i+lenSubArr-1-k][j+l+k] = arr[i+lenSubArr-1-l-k][j+lenSubArr-1-k];
                                    temp[i+l+k][j+k] = arr[i+lenSubArr-1-k][j+l+k];
                                }
                            }
                        }
                    }
                    for (int i = 0; i < power2N; i++) {
                        arr[i] = Arrays.copyOf(temp[i], power2N);
                    }
                }
            }
        } else {
            if (lenSubArr == power2N) return; // 부분배열의 길이가 전체 배열과 같으면 아무런 효과가 없음
            if (K == 5) { // 상하 반전
                for (int i = 0; i < power2N / 2; i+=lenSubArr) {
                    for (int j = 0; j < lenSubArr; j++) {
                        System.arraycopy(arr[power2N-i-lenSubArr+j], 0, temp[i+j], 0, power2N);
                        System.arraycopy(arr[i+j], 0, temp[power2N-i-lenSubArr+j], 0, power2N);
                    }
                }
            } else if (K == 6) { // 좌우 반전
                for (int i = 0; i < power2N / 2; i+=lenSubArr) {
                    for (int j = 0; j < lenSubArr; j++) {
                        for (int k = 0; k < power2N; k++) {
                            temp[k][power2N - i - lenSubArr + j] = arr[k][i + j];
                            temp[k][i + j] = arr[k][power2N - i - lenSubArr + j];
                        }
                    }
                }
            } else if (K == 7) { // 시계 방향 90도
                for (int i = 0; i < power2N / 2; i+=lenSubArr) {
                    for (int j = 1; j <= (power2N - i * 2) / lenSubArr - 1; j++) {
                        for (int k = 0; k < lenSubArr; k++) {
                            for (int l = 0; l < lenSubArr; l++) {
                                temp[i + k][power2N - i - lenSubArr * j + l] = arr[i + lenSubArr * (j - 1) + k][i + l];
                                temp[power2N - i - lenSubArr * j + k][power2N - i - lenSubArr + l] = arr[i + k][power2N - i - lenSubArr * j + l];
                                temp[power2N - i - lenSubArr + k][i + lenSubArr * (j - 1) + l] = arr[power2N - i - lenSubArr * j + k][power2N - i - lenSubArr + l];
                                temp[i + lenSubArr * (j - 1) + k][i + l] = arr[power2N - i - lenSubArr + k][i + lenSubArr * (j - 1) + l];
                            }
                        }
                    }
                }
            } else { // 반시계 방향 90도
                for (int m = 0; m < 3; m++) {
                    for (int i = 0; i < power2N / 2; i+=lenSubArr) {
                        for (int j = 1; j <= (power2N - i * 2) / lenSubArr - 1; j++) {
                            for (int k = 0; k < lenSubArr; k++) {
                                for (int l = 0; l < lenSubArr; l++) {
                                    temp[i+k][power2N-i-lenSubArr*j+l] = arr[i+lenSubArr*(j-1)+k][i+l];
                                    temp[power2N-i-lenSubArr*j+k][power2N-i-lenSubArr+l] = arr[i+k][power2N-i-lenSubArr*j+l];
                                    temp[power2N-i-lenSubArr+k][i+lenSubArr*(j-1)+l] = arr[power2N-i-lenSubArr*j+k][power2N-i-lenSubArr+l];
                                    temp[i+lenSubArr*(j-1)+k][i+l] = arr[power2N-i-lenSubArr+k][i+lenSubArr*(j-1)+l];
                                }
                            }
                        }
                    }
                    for (int i = 0; i < power2N; i++) {
                        arr[i] = Arrays.copyOf(temp[i], power2N);
                    }
                }
            }
        }
        for (int i = 0; i < power2N; i++) {
            arr[i] = Arrays.copyOf(temp[i], power2N);
        }
    }
}