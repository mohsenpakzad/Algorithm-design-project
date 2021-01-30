package algorithm.design.course.phase4;

class UniformSearchNode {

    public Cave cave;
    int coveredDistance;
    public UniformSearchNode(Cave cave, int coveredDistance) {
        this.cave = cave;
        this.coveredDistance = coveredDistance;
    }
}
