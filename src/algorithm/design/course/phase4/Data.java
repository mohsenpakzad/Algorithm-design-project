package algorithm.design.course.phase4;

import java.util.List;
import java.util.Set;

class Data {

    public Data(Cave startCave, Set<Cave> treasureCaves) {
        this.startCave = startCave;
        this.treasureCaves = treasureCaves;
    }

    public Cave startCave;
    public Set<Cave> treasureCaves;
}
