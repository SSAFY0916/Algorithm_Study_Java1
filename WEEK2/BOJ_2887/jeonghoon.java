import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    static int num;
    static int[] parent;

    static class Planet implements Comparable<Planet> {
        int idx;
        int pos;

        public Planet(int idx, int pos) {
            this.idx = idx;
            this.pos = pos;
        }

        @Override
        public int compareTo(Planet o) {
            if (this.pos == o.pos)
                return this.idx - o.idx;
            return this.pos - o.pos;
        }

    }

    static class Node implements Comparable<Node> {
        int from;
        int to;
        int length;

        public Node(int from, int to, int length) {
            this.from = from;
            this.to = to;
            this.length = length;
        }

        @Override
        public int compareTo(Node o) {
            // TODO Auto-generated method stub
            if (this.length == o.length) {
                if (this.from == o.from)
                    return this.to - o.to;
                return this.from - o.from;
            }
            return this.length - o.length;
        }

    }

    static void merge(int x, int y) {
        x = find(x);
        y = find(y);

        if (x == y)
            return;
        parent[y] = x;
    }

    static int find(int x) {
        if (x == parent[x])
            return x;
        return parent[x] = find(parent[x]);
    }

    static boolean isUnion(int x, int y) {
        x = find(x);
        y = find(y);
        if (x == y)
            return true;
        return false;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        num = Integer.parseInt(br.readLine());
        parent = new int[num];
        for (int i = 0; i < num; i++) {
            parent[i] = i;
        }

        List<Planet> listX = new ArrayList<>();
        List<Planet> listY = new ArrayList<>();
        List<Planet> listZ = new ArrayList<>();

        List<Node> listNode = new ArrayList<>();

        for (int i = 0; i < num; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int z = Integer.parseInt(st.nextToken());

            listX.add(new Planet(i, x));
            listY.add(new Planet(i, y));
            listZ.add(new Planet(i, z));
        }

        Collections.sort(listX);
        Collections.sort(listY);
        Collections.sort(listZ);

        for (int i = 1; i < num; i++) {
            listNode.add(new Node(listX.get(i - 1).idx, listX.get(i).idx, listX.get(i).pos - listX.get(i - 1).pos));
            listNode.add(new Node(listY.get(i - 1).idx, listY.get(i).idx, listY.get(i).pos - listY.get(i - 1).pos));
            listNode.add(new Node(listZ.get(i - 1).idx, listZ.get(i).idx, listZ.get(i).pos - listZ.get(i - 1).pos));
        }

        Collections.sort(listNode);

        int length = 0;

        for (int i = 0; i < listNode.size(); i++) {
            Node n = listNode.get(i);
            if (!isUnion(n.from, n.to)) {
                merge(Math.min(n.from, n.to), Math.max(n.from, n.to));
                length += n.length;
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append(length);
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

}
