package algorithm.design.course.phase4;

import java.util.ArrayList;
import java.util.List;

class Cave {

    public int id;
    public List<Tunnel> tunnels = new ArrayList<>();
    public int treasureNum = 0;

    public Cave(int id) {
        this.id = id;
    }
}
