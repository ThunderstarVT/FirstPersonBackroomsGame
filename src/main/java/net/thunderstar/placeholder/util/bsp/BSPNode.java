package net.thunderstar.placeholder.util.bsp;

import net.thunderstar.placeholder.objects.Sector;
import net.thunderstar.placeholder.objects.Wall;
import net.thunderstar.placeholder.util.Normal;

public class BSPNode {
    public BSPNode parent;
    public BSPNode[] children = new BSPNode[2];

    public Normal normal;

    public BSPNode() {

    }

    public BSPNode createChildrenWall(Wall[] walls) {
        return this;
    }

    public BSPNode createChildrenSectorFloor(Sector[] sectors) {
        return this;
    }

    public BSPNode createChildrenSectorCeiling(Sector[] sectors) {
        return this;
    }


    public BSPNode getChild(boolean b){
        if (b) {
            return children[1];
        }
        return children[0];
    }

    public boolean isLeaf() {
        if (children[0] == null && children[1] == null) {
            return true;
        }
        return false;
    }

    public boolean isRoot() {
        if (parent == null) {
            return true;
        }
        return false;
    }
}
