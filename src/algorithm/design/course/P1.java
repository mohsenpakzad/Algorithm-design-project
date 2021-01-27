package algorithm.design.course;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class P1 {

    private static Scanner scanner;

    public static void main(String[] args) {

        File inputFile = new File("TestCases/A/sample.in");
        try {
            scanner = new Scanner(inputFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int m, n, k;

        while(true) {
            m = scanner.nextInt();
            n = scanner.nextInt();
            k = scanner.nextInt();

            if (m == 0 || n == 0) break;

            char[][] map = readInput(m, n);
            Index lastFired = lastFired(map, k);

            System.out.printf("%d %d\n", lastFired.i, lastFired.j);
        }
    }

    private static char[][] readInput(int m, int n) {

        char[][] map = new char[m][n];

        for (int i = 0; i < m; i++) {
            String line = scanner.next();
            for (int j = 0; j < n; j++) {
                map[i][j] = line.charAt(j);
            }
        }
        return map;
    }

    private static Index lastFired(char[][] map, int k) {

        // findAllFiredPlaces -> list all of fs and its indexes
        // make 8 near rooms in fire
        // check if any rooms unfired exists
        // if no rooms exits, return first element of last state unfired room

        List<Index> unfiredIndices = findForTarget(map, '-');

        while (true) {

            //---
            //-f-
            //---
            for (Index firedRoom : findForTarget(map, 'f')) {
                fire(map, firedRoom.i - 1, firedRoom.j - 1);
                fire(map, firedRoom.i - 1, firedRoom.j);
                fire(map, firedRoom.i - 1, firedRoom.j + 1);

                fire(map, firedRoom.i, firedRoom.j + 1);
                fire(map, firedRoom.i + 1, firedRoom.j + 1);

                fire(map, firedRoom.i + 1, firedRoom.j);
                fire(map, firedRoom.i + 1, firedRoom.j - 1);

                fire(map, firedRoom.i, firedRoom.j - 1);
            }

            List<Index> newUnfiredIndices = findForTarget(map, '-');
            if (newUnfiredIndices.size() > 0) {
                unfiredIndices = newUnfiredIndices;
            } else {
                return unfiredIndices.get(0);
            }
        }
    }

    private static List<Index> findForTarget(char[][] map, char target) {
        List<Index> indices = new LinkedList<>();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == target) indices.add(new Index(i, j));
            }
        }
        return indices;
    }

    private static void fire(char[][] map, int toFireRow, int toFireColumn) {
        if (toFireRow >= 0 &&
                toFireColumn >= 0 &&
                map.length > toFireRow &&
                map[toFireRow].length > toFireColumn &&
                map[toFireRow][toFireColumn] != 'f')
            map[toFireRow][toFireColumn] = 'f';
    }
}
