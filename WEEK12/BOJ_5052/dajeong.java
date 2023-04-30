package aaa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());
        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine());
            String[] phoneNumbers = new String[n];
            for (int i = 0; i < n; i++) {
                phoneNumbers[i] = br.readLine();
            }
            Arrays.sort(phoneNumbers);
            boolean consistent = true;
            for (int i = 0; i < n - 1; i++) {
                if (phoneNumbers[i + 1].startsWith(phoneNumbers[i])) {
                    consistent = false;
                    break;
                }
            }
            if (consistent) {
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }
        }
        br.close();
    }
}
