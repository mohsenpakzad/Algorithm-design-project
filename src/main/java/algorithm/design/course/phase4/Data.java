package algorithm.design.course.phase4;

import java.util.ArrayList;
import java.util.List;

class Data {

    public Cave startCave;
    public List<Cave> treasureCaves;
    public List<Distance> minDistances = new ArrayList<>();

    public Data(Cave startCave, List<Cave> treasureCaves) {
        this.startCave = startCave;
        this.treasureCaves = treasureCaves;
    }
}
