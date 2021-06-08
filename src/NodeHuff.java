public class NodeHuff {
    int frequency = -1;
    int orderPriority = -1;
    char n;
    Boolean isLeaf = true;
    NodeHuff childLeft, childRight; // oh wait these should be binary codes
    NodeHuff parent; //

//    the Node Object class for the forest to create the Huffman tree
    public NodeHuff(char n, int frequency, int orderPriority, NodeHuff parent, NodeHuff childLeft, NodeHuff childRight, boolean isLeaf) {
        this.n = n;
        this.frequency = frequency;
        this.orderPriority = orderPriority;
        this.parent = parent;
        this.childLeft = childLeft;
        this.childRight = childRight;
        this.isLeaf = isLeaf;
    }
    public int getFrequency(){
        return frequency;
    }
    public int getOrderPriority(){
        return orderPriority;
    }
}
