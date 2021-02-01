package algorithm.design.course.phase2;

import algorithm.design.course.helper.Index;

import java.util.LinkedList;
import java.util.Queue;

class FireMap {

    public Queue<Index> toFireIndices = new LinkedList<>();
    public int m, n;
    public Index source;
    public Index target;

    public FireMap(int m, int n) {
        this.m = m;
        this.n = n;
    }
}
