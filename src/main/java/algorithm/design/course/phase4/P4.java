package algorithm.design.course.phase4;

import algorithm.design.course.helper.Utils;

import java.util.*;
import java.util.stream.Collectors;

public class P4 {

    private static Scanner scanner;

    public static void main(String[] args) {

//        long startTime = System.currentTimeMillis();


        scanner = Utils.getFileScanner("TestCases/D/random.10000.in");


        int testsNumber = scanner.nextInt();
        for (int i = 0; i < testsNumber; i++) {

            Data data = readCavesAndTunnels();
            findMinDistance(data);

            int maxPossibleDistance = scanner.nextInt();

            System.out.println(findMaxTreasureWithPossibleDistance(data, maxPossibleDistance));
        }


//        long stopTime = System.currentTimeMillis();
//        System.out.println("Total time:" + (stopTime - startTime));
    }

    private static int findMaxTreasureWithPossibleDistance(Data data, int maxPossibleDistance) {

        Set<Integer> foundTreasuresForCompletePaths = new LinkedHashSet<>();
        Stack<DfsNode> stack = new Stack<>();

        for (Cave treasureCave : data.treasureCaves) {
            int shortestPathToTreasure = findDistance(data.minDistances, data.startCave, treasureCave);
            if (shortestPathToTreasure <= maxPossibleDistance) {
                stack.push(new DfsNode(null, data.startCave, treasureCave, shortestPathToTreasure));
            }
        }

        while (!stack.isEmpty()) {

            DfsNode popNode = stack.pop();

            int shortestPathFromStartAndBack = popNode.passedLength +
                    findDistance(data.minDistances, popNode.desCave, data.startCave);

            if (shortestPathFromStartAndBack <= maxPossibleDistance) {

                foundTreasuresForCompletePaths.add(popNode.coveredCaves.stream()
                        .mapToInt(value -> value.treasureNum)
                        .sum()
                );

                List<Cave> filteredNextTreasures = data.treasureCaves.stream()
                        .filter(cave -> !popNode.coveredCaves.contains(cave))
                        .collect(Collectors.toList());

                for (Cave nextTreasureCave : filteredNextTreasures) {
                    int shortestPathToNextTreasure = findDistance(data.minDistances, popNode.desCave, nextTreasureCave);
                    if (shortestPathToNextTreasure <= maxPossibleDistance) {
                        stack.push(new DfsNode(popNode, popNode.desCave, nextTreasureCave,
                                popNode.passedLength + shortestPathToNextTreasure));
                    }
                }
            }
        }

        return foundTreasuresForCompletePaths.stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(0);
    }

    private static int findDistance(List<Distance> distances, Cave first, Cave second) {
        return distances.stream()
                .filter(distance -> (distance.src == first && distance.dest == second) ||
                        (distance.src == second && distance.dest == first)
                ).findAny()
                .orElseThrow(() -> new RuntimeException("Path not calculated!"))
                .value;
    }

    private static int calculateShortestPathFromSourceToDest(Cave source, Cave dest) {

        PriorityQueue<UniformSearchNode> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o.coveredDistance));
        priorityQueue.add(new UniformSearchNode(source, 0));

        Set<Cave> goneCaves = new LinkedHashSet<>();

        while (!priorityQueue.isEmpty()) {

            UniformSearchNode polledNode = priorityQueue.poll();

            if (polledNode.cave == dest) return polledNode.coveredDistance;
            else if (!goneCaves.contains(polledNode.cave)) {
                polledNode.cave.tunnels.forEach(tunnel -> priorityQueue.add(
                        new UniformSearchNode(tunnel.destCave, polledNode.coveredDistance + tunnel.length)));

                goneCaves.add(polledNode.cave);
            }

        }
        return -1;
    }

    private static void findMinDistance(Data data) {

        Set<Cave> noWayFromSourceCaves = new LinkedHashSet<>();
        for (Cave treasureCave : data.treasureCaves) {
            int shortestPathToTreasure = calculateShortestPathFromSourceToDest(data.startCave, treasureCave);
            if (shortestPathToTreasure == -1) {
                noWayFromSourceCaves.add(treasureCave);
            } else {
                data.minDistances.add(new Distance(data.startCave, treasureCave, shortestPathToTreasure));
            }
        }
        data.treasureCaves.removeAll(noWayFromSourceCaves);

        for (int i = 0; i < data.treasureCaves.size(); i++) {
            for (int j = i; j < data.treasureCaves.size(); j++) {
                int shortestPathToTreasure = calculateShortestPathFromSourceToDest(data.treasureCaves.get(i),
                        data.treasureCaves.get(j));
                data.minDistances.add(new Distance(data.treasureCaves.get(i), data.treasureCaves.get(j),
                        shortestPathToTreasure));
            }
        }
    }

    private static Data readCavesAndTunnels() {

        int caveNumber = scanner.nextInt();
        int tunnelNumber = scanner.nextInt();

        List<Cave> caves = new ArrayList<>();
        Cave startCave = null;

        for (int i = 0; i < caveNumber; i++) {
            Cave cave = new Cave();
            caves.add(cave);
            if (i == 0) startCave = cave;
        }

        for (int i = 0; i < tunnelNumber; i++) {
            int sourceCave = scanner.nextInt();
            int destCave = scanner.nextInt();
            int length = scanner.nextInt();

            caves.get(sourceCave).tunnels.add(new Tunnel(caves.get(destCave), length));
            caves.get(destCave).tunnels.add(new Tunnel(caves.get(sourceCave), length));
        }

        List<Cave> treasureCaves = new ArrayList<>();
        int numberOfTreasures = scanner.nextInt();

        for (int i = 0; i < numberOfTreasures; i++) {

            int caveWithTreasure = scanner.nextInt();
            Cave treasureCave = caves.get(caveWithTreasure);
            treasureCave.treasureNum += 1;

            if (treasureCave.tunnels.size() > 0 &&
                    !treasureCaves.contains(treasureCave)) treasureCaves.add(treasureCave);
        }

        return new Data(startCave, treasureCaves);
    }
}
