package algorithm.design.course.phase1;

import algorithm.design.course.helper.Index;
import algorithm.design.course.helper.Utils;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class P1 {

    private static Scanner scanner;

    public static void main(String[] args) {

//        long startTime = System.currentTimeMillis();


        scanner = Utils.getFileScanner("TestCases/A/a.in");


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

//        long stopTime = System.currentTimeMillis();
//        System.out.println("Total time:" + (stopTime - startTime));
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
        Collections.reverse((List<?>) map.toFireIndices);
        return map;
    }

    private static Index lastFired(FireMap map, int k) {


        while (!map.toFireIndices.isEmpty()) {

            if (map.unfiredIndices.size() == 1) {
                return map.unfiredIndices.peek();
            }

            Index toFireIndex = map.toFireIndices.poll();

            //---
            //-f-
            //---
            fire(map, toFireIndex.i + 1, toFireIndex.j + 1);
            fire(map, toFireIndex.i + 1, toFireIndex.j);
            fire(map, toFireIndex.i + 1, toFireIndex.j - 1);


            fire(map, toFireIndex.i, toFireIndex.j + 1);
            fire(map, toFireIndex.i, toFireIndex.j - 1);


            fire(map, toFireIndex.i - 1, toFireIndex.j + 1);
            fire(map, toFireIndex.i - 1, toFireIndex.j);
            fire(map, toFireIndex.i - 1, toFireIndex.j - 1);


            map.unfiredIndices = map.unfiredIndices.stream()
                    .filter(index -> !index.equals(toFireIndex))
                    .collect(Collectors.toCollection(LinkedList::new));
        }
        return map.unfiredIndices.peek();
    }

    private static void fire(FireMap map, int toFireRow, int toFireColumn) {
        if (toFireRow >= 0 &&
                toFireColumn >= 0 &&
                map.rowLength > toFireRow &&
                map.columnLength > toFireColumn) {
            Index toAdd = new Index(toFireRow, toFireColumn);
            if (map.unfiredIndices.stream().anyMatch(index -> index.equals(toAdd)) &&
                    map.toFireIndices.stream().noneMatch(index -> index.equals(toAdd)))
                map.toFireIndices.add(toAdd);
        }
    }
}

