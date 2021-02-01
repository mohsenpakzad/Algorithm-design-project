package algorithm.design.course.phase4;

import java.util.LinkedHashSet;
import java.util.Set;

class DfsNode {

    public Set<Cave> coveredCaves = new LinkedHashSet<>();
    public Cave startCave;
    public Cave desCave;
    public int passedLength = 0;

    public DfsNode(DfsNode parent, Cave startCave, Cave desCave, int passedLength) {

        if (parent != null) {
            this.coveredCaves.addAll(parent.coveredCaves);
        }

        this.coveredCaves.add(startCave);
        this.coveredCaves.add(desCave);

        this.startCave = startCave;
        this.desCave = desCave;
        this.passedLength = passedLength;
    }
}
