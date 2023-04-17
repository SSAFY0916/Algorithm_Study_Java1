import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    static int N;
    static int[][] arr, arrCopy;

    public static void main(String[] args) throws Exception {
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        int R = Integer.parseInt(st.nextToken());
        int length = (int) Math.pow(2, N);
        arr = new int[length][length];
        arrCopy = new int[length][length];

        for (int i = 0; i < length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < length; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 0; i < R; i++) {
            st = new StringTokenizer(br.readLine());
            int k = Integer.parseInt(st.nextToken());
            int l = Integer.parseInt(st.nextToken());

            switch (k) {    // k에 따라 연산 수행
                case 1:
                    cal1(l);
                    break;
                case 2:
                    cal2(l);
                    break;
                case 3:
                    cal3(l);
                    break;
                case 4:
                    cal4(l);
                    break;
                case 5:
                    cal5(l);
                    break;
                case 6:
                    cal6(l);
                    break;
                case 7:
                    cal7(l);
                    break;
                case 8:
                    cal8(l);
                    break;
            }
        }

        bw.write(printArray());

        bw.flush();
        bw.close();
        br.close();
    }

    static String printArray() {    //arr to String

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                sb.append(arr[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    static void saveArray() {   //arrCopy를 arr에 옮겨놓기
        for (int i = 0; i < arr.length; i++) {
            arr[i] = Arrays.copyOf(arrCopy[i], arr.length);
        }
    }

    static void cal1(int l) {   // 1번 연산 - 각 부분 배열안에서 상하 반전
        int length = (int) Math.pow(2, l);
        for (int i = 0; i < arr.length; i += length) {
            for (int j = 0; j < arr.length; j += length) {
                for (int k = 0; k < length; k++) {
                    for (int m = 0; m < length; m++) {
                        arrCopy[i + length - k - 1][m + j] = arr[k + i][m + j]; // 행 index만 조정
                    }
                }
            }
        }

        saveArray();
    }

    static void cal2(int l) {   // 2번 연산 - 각 부분 배열안에서 좌우 반전
        int length = (int) Math.pow(2, l);
        for (int i = 0; i < arr.length; i += length) {
            for (int j = 0; j < arr.length; j += length) {
                for (int k = 0; k < length; k++) {
                    for (int m = 0; m < length; m++) {
                        arrCopy[k + i][j + length - m - 1] = arr[k + i][m + j]; // 열 index만 조정
                    }
                }
            }
        }

        saveArray();
    }

    static void cal3(int l) {   // 3번 연산 - 각 부분 배열안에서 오른쪽으로 90도 회전
        int length = (int) Math.pow(2, l);
        for (int i = 0; i < arr.length; i += length) {
            for (int j = 0; j < arr.length; j += length) {
                for (int k = 0; k < length; k++) {
                    for (int m = 0; m < length; m++) {
                        int a = (k + i) % length;
                        int b = (m + j) % length;
                        arrCopy[b + i][length - a - 1 + j] = arr[k + i][m + j];
                    }
                }
            }
        }
        saveArray();
    }

    static void cal4(int l) {      // 4번 연산 - 각 부분 배열안에서 왼쪽으로 90도 회전
        int length = (int) Math.pow(2, l);
        for (int i = 0; i < arr.length; i += length) {
            for (int j = 0; j < arr.length; j += length) {
                for (int k = 0; k < length; k++) {
                    for (int m = 0; m < length; m++) {
                        int a = (k + i) % length;
                        int b = (m + j) % length;
                        arrCopy[length - b - 1 + i][a + j] = arr[k + i][m + j];
                    }
                }
            }
        }
        saveArray();
    }

    static void cal5(int l) {   // 5번 연산 - 부분 배열을 한칸으로 보고 모든 칸 상하 반전
        int length = (int) Math.pow(2, l);
        for (int i = 0; i < arr.length; i += length) {
            for (int j = 0; j < arr.length; j += length) {
                for (int k = 0; k < length; k++) {
                    for (int m = 0; m < length; m++) {
                        arrCopy[arr.length - length + k - i][m + j] = arr[k + i][m + j];
                    }
                }
            }
        }

        saveArray();
    }

    static void cal6(int l) {   // 6번 연산 - 부분 배열을 한칸으로 보고 모든 칸 좌우 반전
        int length = (int) Math.pow(2, l);
        for (int i = 0; i < arr.length; i += length) {
            for (int j = 0; j < arr.length; j += length) {
                for (int k = 0; k < length; k++) {
                    for (int m = 0; m < length; m++) {
                        arrCopy[k + i][arr.length - length + m - j] = arr[k + i][m + j];
                    }
                }
            }
        }

        saveArray();
    }

    static void cal7(int l) {   // 7번 연산 - 부분 배열을 한칸으로 보고 모든 칸 오른쪽으로 90도 회전
        int length = (int) Math.pow(2, l);
        for (int i = 0; i < arr.length; i += length) {
            for (int j = 0; j < arr.length; j += length) {
                for (int k = 0; k < length; k++) {
                    for (int m = 0; m < length; m++) {
                        int a = i / length;
                        int b = j / length;
                        arrCopy[k + j][arr.length - length - i + m] = arr[k + i][m + j];
                    }
                }
            }
        }

        saveArray();
    }

    static void cal8(int l) {   // 8번 연산 - 부분 배열을 한칸으로 보고 모든 칸 왼쪽으로 90도 회전
        int length = (int) Math.pow(2, l);
        for (int i = 0; i < arr.length; i += length) {
            for (int j = 0; j < arr.length; j += length) {
                for (int k = 0; k < length; k++) {
                    for (int m = 0; m < length; m++) {
                        int a = i / length;
                        int b = j / length;
                        arrCopy[arr.length - length - j + k][m + i] = arr[k + i][m + j];
                    }
                }
            }
        }

        saveArray();
    }


}
