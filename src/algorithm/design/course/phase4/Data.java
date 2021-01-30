package algorithm.design.course.phase4;

import java.util.List;
import java.util.Set;

class Data {

    public Data(List<Cave> caves, Cave startCave, Set<Cave> treasureCaves) {
        this.caves = caves;
        this.startCave = startCave;
        this.treasureCaves = treasureCaves;
    }

    public List<Cave> caves;
    public Cave startCave;
    public Set<Cave> treasureCaves;
}
