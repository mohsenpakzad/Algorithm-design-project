package algorithm.design.course.phase2;

import algorithm.design.course.helper.Index;

public class Node {

    public int currentTime;
    public Index index;
    public double heuristic;

    public Node(int currentTime, Index index, Index target) {
        this.currentTime = currentTime;
        this.index = index;
        this.heuristic = Math.abs(index.i - target.i) + Math.abs(index.j - target.j);
    }
}
