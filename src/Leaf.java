import java.util.ArrayList;

public class Leaf {
    public int frequency = -1;
    public int otherPriority = -1;
    public Leaf child0, child1; // these should be nodes not Leafs
    public char n;
    public Leaf parent; // these should be nodes not Leafs
//    If the two nodes have the same frequency in the priority queue,
//    pick first the nodes with the smallest characters alphabetically.
//    space comes first,
//    then lower case letters,
//    then upper case letters,
//    then digits
//    special characters??
//    TODO Up to here setting up the Node Object class for the tree
    public Leaf (char n, int frequency, int otherPriority, Leaf parent, Leaf child0, Leaf child1) {
        this.n = n;
        this.frequency = frequency;
        this.otherPriority = otherPriority;
        this.parent = parent;
        this.child0 = child0;
        this.child1 = child1;
    }
}
//import java.util.ArrayList;
//
//public class APObject {
//    public final Node n;
//    public int depth;
//    public int reachBack;
//    public APObject parent;
//    public ArrayList<APObject> children;
//
//    public APObject(Node n, int depth, int reachBack, APObject parent, ArrayList<APObject> children) {
//        this.n = n;
//        this.depth = depth;
//        this.reachBack = reachBack;
//        this.parent = parent;
//        this.children = children;
//    }
//
//}