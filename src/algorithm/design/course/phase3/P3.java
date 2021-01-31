package algorithm.design.course.phase3;

import algorithm.design.course.helper.Index;
import algorithm.design.course.helper.Utils;

import java.util.*;

public class P3 {

    private static Scanner scanner;

    public static void main(String[] args) {


        scanner = Utils.getFileScanner("TestCases/C/sample.in");
        //        scanner = new Scanner(System.in);

        int m = scanner.nextInt();
        int n = scanner.nextInt();

        int[][] graph = readInput(m, n);

        Index goal = new Index(scanner.nextInt(), scanner.nextInt());
        System.out.println(maxFlowMinimumCut(graph, 0, goal.i * n + goal.j + 1));
    }


    private static boolean bfs(int[][] rGraph, int s, int t, int[] parent) {

        boolean[] visited = new boolean[rGraph.length];

        Queue<Integer> q = new LinkedList<Integer>();
        q.add(s);
        visited[s] = true;
        parent[s] = -1;

        while (!q.isEmpty()) {
            int v = q.poll();
            for (int i = 0; i < rGraph.length; i++) {
                if (rGraph[v][i] > 0 && !visited[i]) {
                    q.offer(i);
                    visited[i] = true;
                    parent[i] = v;
                }
            }
        }
        return visited[t];
    }

    private static void dfs(int[][] rGraph, int s, boolean[] visited) {
        visited[s] = true;
        for (int i = 0; i < rGraph.length; i++) {
            if (rGraph[s][i] > 0 && !visited[i]) {
                dfs(rGraph, i, visited);
            }
        }
    }

    private static int maxFlowMinimumCut(int[][] graph, int s, int t) {

        int u, v;

        int[][] graphCopy = new int[graph.length][graph.length];
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph.length; j++) {
                graphCopy[i][j] = graph[i][j];
            }
        }

        int[] parent = new int[graph.length];

        while (bfs(graphCopy, s, t, parent)) {

            int pathFlow = Integer.MAX_VALUE;
            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                pathFlow = Math.min(pathFlow, graphCopy[u][v]);
            }

            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                graphCopy[u][v] = graphCopy[u][v] - pathFlow;
                graphCopy[v][u] = graphCopy[v][u] + pathFlow;
            }
        }

        boolean[] isVisited = new boolean[graph.length];
        dfs(graphCopy, s, isVisited);

        int res = 0;

        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph.length; j++) {
                if (graph[i][j] > 0 && isVisited[i] && !isVisited[j]) {
                    System.out.println(i + " - " + j);
                    if (i != 0) res += graph[i][j];
                }
            }
        }

        return res;
    }

    private static int[][] readInput(int m, int n) {

        int numberOfNodes = m * n * 2 + 1;
        int[][] graph = new int[numberOfNodes][numberOfNodes];


        for (int i = 1; i <= m * n; i++) {
            graph[0][i] = Integer.MAX_VALUE;
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
