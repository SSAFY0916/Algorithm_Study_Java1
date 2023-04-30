import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Main {
    static List<List<Road>> list = new ArrayList<>();
    final static long MAX_VALUE = Long.MAX_VALUE;

    private static class Road implements Comparable<Road> {
        int dst;
        long cost;

        public Road(int dst, long cost) {
            this.dst = dst;
            this.cost = cost;
        }

        // PQ 사용을 위한 compareTo overriding
        @Override
        public int compareTo(Road o) {
            if (this.cost < o.cost) {
                return -1;
            } else {
                return 1;
            }
        }
    }
    
    private static class WolfRoad implements Comparable<WolfRoad> {
        int dst;
        long cost;
        boolean flag;

        public WolfRoad(int dst, long cost, boolean flag) {
            this.dst = dst;
            this.cost = cost;
            this.flag = flag;
        }

        // PQ 사용을 위한 compareTo overriding
        @Override
        public int compareTo(WolfRoad o) {
            if (this.cost < o.cost) {
                return -1;
            } else {
                return 1;
            }
        }
    }

    // 입출력 시간을 단축시키기 위한 다른 InputReader 클래스 생성
    private static class InputReader {
        private InputStream stream;
        private byte[] buf = new byte[1024];
        private int curChar;
        private int numChars;
        private SpaceCharFilter filter;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        public int read() {
            if (numChars == -1) {
                throw new InputMismatchException();
            }
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buf);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (numChars <= 0) {
                    return -1;
                }
            }
            return buf[curChar++];
        }

        public int readInt() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }
            int res = 0;
            do {
                if (c < '0' || c > '9') {
                    throw new InputMismatchException();
                }
                res *= 10;
                res += c - '0';
                c = read();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public boolean isSpaceChar(int c) {
            if (filter != null) {
                return filter.isSpaceChar(c);
            }
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        public interface SpaceCharFilter {
            public boolean isSpaceChar(int ch);
        }
    }

    public static void main(String[] args) throws IOException {
        InputReader in = new InputReader(System.in);

        int stubNum = in.readInt();
        int roadNum = in.readInt();

        for (int i = 0; i <= stubNum; i++) {
            list.add(new ArrayList<>());
        }

        for (int i = 0; i < roadNum; i++) {
            int stub1 = in.readInt();
            int stub2 = in.readInt();
            long cost = in.readInt();

            list.get(stub1).add(new Road(stub2, cost));
            list.get(stub2).add(new Road(stub1, cost));
        }

        System.out.println(dijkstra(1));
    }

    // 다익스트라 알고리즘
    private static int dijkstra(int start) {
        long[] foxDist = new long[list.size()];
        long[][] wolfDist = new long[2][list.size()];

        Arrays.fill(foxDist, MAX_VALUE);
        Arrays.fill(wolfDist[0], MAX_VALUE);
        Arrays.fill(wolfDist[1], MAX_VALUE);

        Queue<Road> fQueue = new PriorityQueue<>();
        Queue<WolfRoad> wQueue = new PriorityQueue<>();


        fQueue.add(new Road(start, 0));
        wQueue.add(new WolfRoad(start, 0, false));

        foxDist[start] = 0;
        wolfDist[0][start] = 0;

        // 일반적인 다익스트라 알고리즘
        while (!fQueue.isEmpty()) {
            Road tmp = fQueue.poll();
            int curPos = tmp.dst;
            long curCost = tmp.cost;

            if (foxDist[curPos] < curCost) continue;
            for (Road r : list.get(curPos)) {
                int nextPos = r.dst;
                long nextCost = curCost + r.cost * 2;

                if (nextCost < foxDist[nextPos]) {
                    foxDist[nextPos] = nextCost;
                    fQueue.add(new Road(nextPos, nextCost));
                }
            }
        }

        // 다익스트라 알고리즘을 빨리 가는 경우와 느리게 가는경우 두 가지로 구분하여 사용
        while (!wQueue.isEmpty()) {
            WolfRoad tmp = wQueue.poll();
            int curPos = tmp.dst;
            long curCost = tmp.cost;
            boolean curFlag = tmp.flag;
            boolean nextFlag = !curFlag;

            if (wolfDist[curFlag ? 1 : 0][curPos] < curCost) continue;
            for (Road r : list.get(curPos)) {
                int nextPos = r.dst;
                long nextCost = nextFlag ? r.cost : r.cost * 4;
                nextCost += curCost;

                if (nextCost < wolfDist[nextFlag ? 1 : 0][nextPos]) {
                    wolfDist[nextFlag ? 1 : 0][nextPos] = nextCost;
                    wQueue.add(new WolfRoad(nextPos, nextCost, nextFlag));
                }
            }
        }

        int cnt = 0;

        for (int i = 1; i < list.size(); i++) {
            if (foxDist[i] < Math.min(wolfDist[0][i], wolfDist[1][i]))
                cnt++;
        }

        return cnt;
    }
}
