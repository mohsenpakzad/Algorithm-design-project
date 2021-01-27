package algorithm.design.course;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.stream.Collectors;

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

        while (true) {
            m = scanner.nextInt();
            n = scanner.nextInt();
            k = scanner.nextInt();

            if (m == 0 || n == 0) break;

            FireMap map = readInput(m, n);
            Index lastFired = lastFired(map, k);

            System.out.printf("%d %d\n", lastFired.i, lastFired.j);
        }
    }

    private static FireMap readInput(int m, int n) {

        FireMap map = new FireMap();
        map.rowLength = m;
        map.columnLength = n;

        for (int i = 0; i < m; i++) {
            String line = scanner.next();
            for (int j = 0; j < n; j++) {
                char roomStatus = line.charAt(j);
                switch (roomStatus) {
                    case 'f':
                        map.toFireIndices.add(new Index(i, j));
                        break;
                    case '-':
                        map.unfiredIndices.add(new Index(i, j));
                        break;
                }
            }
        }
        return map;
    }

    private static Index lastFired(FireMap map, int k) {

        //---
        //-f-
        //---
        while (!map.toFireIndices.isEmpty()) {

            Index toFireIndex = map.toFireIndices.poll();

            fire(map, toFireIndex.i + 1, toFireIndex.j + 1);

            fire(map, toFireIndex.i + 1, toFireIndex.j);
            fire(map, toFireIndex.i, toFireIndex.j + 1);

            fire(map, toFireIndex.i + 1, toFireIndex.j - 1);
            fire(map, toFireIndex.i - 1, toFireIndex.j + 1);

            fire(map, toFireIndex.i, toFireIndex.j - 1);
            fire(map, toFireIndex.i - 1, toFireIndex.j);

            fire(map, toFireIndex.i - 1, toFireIndex.j - 1);


            if (map.unfiredIndices.size() == 1) {
                return map.unfiredIndices.poll();
            } else {
                map.unfiredIndices = map.unfiredIndices.stream()
                        .filter(index -> !index.equals(toFireIndex))
                        .collect(Collectors.toCollection(LinkedList::new));
                if (map.unfiredIndices.size() == 1)
                    return map.unfiredIndices.poll();
            }
        }
        return map.unfiredIndices.peek();
    }

    private static void fire(FireMap map, int toFireRow, int toFireColumn) {
        if (toFireRow >= 0 &&
                toFireColumn >= 0 &&
                map.rowLength > toFireRow &&
                map.columnLength > toFireColumn)
            map.toFireIndices.add(new Index(toFireRow, toFireColumn));
    }
}

class FireMap {
    public Queue<Index> toFireIndices = new LinkedList<>();
    public Queue<Index> unfiredIndices = new LinkedList<>();
    public int rowLength, columnLength;
}