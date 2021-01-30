package algorithm.design.course.phase4;

class UniformSearchNode {

    public UniformSearchNode(Cave cave, int coveredDistance) {
        this.cave = cave;
        this.coveredDistance = coveredDistance;
    }

    public Cave cave;
    int coveredDistance;
}
