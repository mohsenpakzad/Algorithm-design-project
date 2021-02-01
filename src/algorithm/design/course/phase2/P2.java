package algorithm.design.course.phase2;

import algorithm.design.course.helper.Index;
import algorithm.design.course.helper.Utils;

import java.util.*;

public class P2 {

    private static Scanner scanner;

    public static void main(String[] args) {

//        long startTime = System.currentTimeMillis();


        scanner = Utils.getFileScanner("TestCases/B/b.real.in");
        //        scanner = new Scanner(System.in);

        int m, n, k;

        while (true) {
            m = scanner.nextInt();
            n = scanner.nextInt();
            k = scanner.nextInt();

            if (m == 0 || n == 0) break;

            FireMap map = readInput(m, n);
            int leastTime = findLeastTimeToReachTarget(map, k);

            if (leastTime >= 1) System.out.println(leastTime);
            else System.out.println("Impossible");
        }


//        long stopTime = System.currentTimeMillis();
//        System.out.println("Total time:" + (stopTime - startTime));
    }

    private static FireMap readInput(int m, int n) {

        FireMap map = new FireMap(m, n);

        for (int i = 0; i < m; i++) {
            String line = scanner.next();
            for (int j = 0; j < n; j++) {
                char roomStatus = line.charAt(j);
                switch (roomStatus) {
                    case 'f':
                        map.toFireIndices.add(new Index(i, j));
                        break;
                    case 's':
                        map.source = new Index(i, j);
                        break;
                    case 't':
                        map.target = new Index(i, j);
                        break;
                }
            }
        }
        return map;
    }

    private static int findLeastTimeToReachTarget(FireMap map, int k) {

        int[][] timeMap = new int[map.m][map.n];
        for (int i = 0; i < map.m; i++) {
            for (int j = 0; j < map.n; j++) {
                timeMap[i][j] = Integer.MAX_VALUE;
            }
        }

        map.toFireIndices.forEach(toFireIndex -> bfsAndMinTime(timeMap, toFireIndex, map.m, map.n, k));

        return findAllAvailablePathFromSourceToTarget(timeMap, map.m, map.n, map.source, map.target, k);
    }

    private static void bfsAndMinTime(int[][] timeMap, Index toFireIndex, int m, int n, int k) {

        Set<Index> usedRooms = new HashSet<>();
        Queue<Index> queue = new LinkedList<>();

        queue.add(toFireIndex);
        timeMap[toFireIndex.i][toFireIndex.j] = 0;

        while (!queue.isEmpty()) {
            Index polledIndex = queue.poll();

            if (!usedRooms.contains(polledIndex)) {
                List<Index> successorOfIndex = getSuccessorOfFireIndex(polledIndex, m, n);
                for (Index index : successorOfIndex) {
                    timeMap[index.i][index.j] = Math.min(timeMap[index.i][index.j],
                            timeMap[polledIndex.i][polledIndex.j] + k);
                }
                queue.addAll(successorOfIndex);
                usedRooms.add(polledIndex);
            }
        }
    }

    private static List<Index> getSuccessorOfFireIndex(Index index, int m, int n) {
        List<Index> successors = new ArrayList<>();

        addValidIndex(successors, m, n, index.i - 1, index.j - 1);
        addValidIndex(successors, m, n, index.i - 1, index.j);
        addValidIndex(successors, m, n, index.i - 1, index.j + 1);

        addValidIndex(successors, m, n, index.i, index.j - 1);
        addValidIndex(successors, m, n, index.i, index.j + 1);

        addValidIndex(successors, m, n, index.i + 1, index.j - 1);
        addValidIndex(successors, m, n, index.i + 1, index.j);
        addValidIndex(successors, m, n, index.i + 1, index.j + 1);

        return successors;
    }

    private static List<Index> getSuccessorOfSourceIndex(Index index, int m, int n) {
        List<Index> successors = new ArrayList<>();

        addValidIndex(successors, m, n, index.i - 1, index.j);
        addValidIndex(successors, m, n, index.i, index.j + 1);
        addValidIndex(successors, m, n, index.i + 1, index.j);
        addValidIndex(successors, m, n, index.i, index.j - 1);

        return successors;
    }

    private static void addValidIndex(List<Index> successors, int m, int n, int toFireRow, int toFireColumn) {
        if (toFireRow >= 0 &&
                toFireColumn >= 0 &&
                m > toFireRow &&
                n > toFireColumn) {
            successors.add(new Index(toFireRow, toFireColumn));
        }
    }

    // using A* algorithm
    private static int findAllAvailablePathFromSourceToTarget(int[][] timeMap, int m, int n,
                                                              Index source, Index target, int k) {

        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(
                Comparator.comparingInt(o -> o.heuristic + o.currentTime)
        );

        priorityQueue.add(new Node(0, source, target));

        while (!priorityQueue.isEmpty()) {
            Node polledNode = priorityQueue.poll();

            if (polledNode.currentTime < timeMap[polledNode.index.i][polledNode.index.j]) {

                if (polledNode.index.equals(target)) {
                    return polledNode.currentTime;
                }

                getSuccessorOfSourceIndex(polledNode.index, m, n)
                        .forEach(index -> priorityQueue.add(new Node(polledNode.currentTime + 1, index,
                                target)));

            }
        }
        return 0;
    }
}
