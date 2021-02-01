package algorithm.design.course.phase3;

import algorithm.design.course.helper.Index;
import algorithm.design.course.helper.Utils;

import java.util.*;

public class P3 {

    private static Scanner scanner;

    public static void main(String[] args) {


        scanner = Utils.getFileScanner("TestCases/C/nested-stronghold.in");
        //        scanner = new Scanner(System.in);

        int m = scanner.nextInt();
        int n = scanner.nextInt();

        int[][] graph = readInput(m, n);

        Index goal = new Index(scanner.nextInt(), scanner.nextInt());
        System.out.println(maxFlowMinimumCut(graph, 0, goal.i * n + goal.j + 1));
    }

    private static boolean bfs(int[][] graph, int s, int t, int[] parent) {

        boolean[] visited = new boolean[graph.length];
        for (int i = 0; i < graph.length; ++i) visited[i] = false;

        Queue<Integer> queue = new LinkedList<>();
        queue.add(s);
        visited[s] = true;
        parent[s] = -1;

        while (queue.size() != 0) {
            int u = queue.poll();

            for (int v = 0; v < graph.length; v++) {
                if (!visited[v] && graph[u][v] > 0) {
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }
        return visited[t];
    }

    private static int maxFlowMinimumCut(int[][] graph, int source, int sink) {

        int[][] copiedGraph = new int[graph.length][graph.length];

        for (int i = 0; i < graph.length; i++)
            System.arraycopy(graph[i], 0, copiedGraph[i], 0, graph.length);

        int[] parent = new int[graph.length];

        int maxFlow = 0;

        while (bfs(copiedGraph, source, sink, parent)) {

            int path_flow = Integer.MAX_VALUE;
            for (int i = sink; i != source; i = parent[i]) {
                int p = parent[i];
                path_flow = Math.min(path_flow, copiedGraph[p][i]);
            }

            for (int i = sink; i != source; i = parent[i]) {
                int p = parent[i];
                copiedGraph[p][i] -= path_flow;
                copiedGraph[i][p] += path_flow;
            }


            if (path_flow != Integer.MAX_VALUE) {
//                System.out.println("Removed " + path_flow);
                maxFlow += path_flow;
            }
        }
        return maxFlow;
    }

    private static int[][] readInput(int m, int n) {

        int numberOfNodes = m * n * 2 + 1;
        int[][] graph = new int[numberOfNodes][numberOfNodes];


        for (int j = 1; j <= n; j++) {
            graph[0][j] = Integer.MAX_VALUE; // i = 0
            graph[0][(m - 1) * n + j] = Integer.MAX_VALUE; // i = m
        }

        for (int i = 1; i < m; i++) {
            graph[0][i * n + 1] = Integer.MAX_VALUE;  // j = 0
            graph[0][i * n + n] = Integer.MAX_VALUE; // j = n
        }


        int currentInNode = 1;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int currentOutNode = m * n + currentInNode;
                graph[currentInNode][currentOutNode] = scanner.nextInt();

                getSuccessorOfIndex(new Index(i, j), m, n)
                        .forEach(index -> graph[currentOutNode][index.i * m + index.j + 1] = Integer.MAX_VALUE);

                currentInNode++;
            }
        }
        return graph;
    }

    private static List<Index> getSuccessorOfIndex(Index index, int m, int n) {
        List<Index> successors = new ArrayList<>();

        addValidIndex(successors, m, n, index.i - 1, index.j);
        addValidIndex(successors, m, n, index.i, index.j + 1);
        addValidIndex(successors, m, n, index.i + 1, index.j);
        addValidIndex(successors, m, n, index.i, index.j - 1);

        return successors;
    }

    private static void addValidIndex(List<Index> successors, int m, int n, int targetRow, int targetColumn) {
        if (targetRow >= 0 &&
                targetColumn >= 0 &&
                m > targetRow &&
                n > targetColumn) {
            successors.add(new Index(targetRow, targetColumn));
        }
    }
}
