package algorithm.design.course.phase1;

import algorithm.design.course.helper.Index;

import java.util.LinkedList;
import java.util.Queue;

class FireMap {
    public Queue<Index> toFireIndices = new LinkedList<>();
    public Queue<Index> unfiredIndices = new LinkedList<>();
    public int rowLength, columnLength;
}
