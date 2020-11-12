import java.util.ArrayList;

/*
 * Node for game tree used in minimax
 */
class Node {
    private CongaBoard congaBoard;
    private Node parentNode;
    private ArrayList<Node> childNodes;
    private Node bestChildState;
    private int bestValue;

    Node (CongaBoard congaBoard, Node parentNode) {
        this.congaBoard = congaBoard;
        this.parentNode = parentNode;
        this.childNodes = new ArrayList<>();
        this.bestChildState = null;
        this.bestValue = Integer.MIN_VALUE;
    }

    public void addChild(Node node) {
        if (bestChildState == null) {
            this.bestChildState = node;
        }
        node.setParentNode(this);
        this.childNodes.add(node);
    }

    public CongaBoard getCongaBoard() {
        return congaBoard;
    }

    public void setCongaBoard(CongaBoard congaBoard) {
        this.congaBoard = congaBoard;
    }

    public Node getParentNode() {
        return parentNode;
    }

    public void setParentNode(Node parentNode) {
        this.parentNode = parentNode;
    }

    public ArrayList<Node> getChildNodes() {
        return childNodes;
    }

    public void setChildNodes(ArrayList<Node> childNodes) {
        this.childNodes = childNodes;
    }

    public Node getBestChildState() {
        return bestChildState;
    }

    public void setBestChildState(Node bestChildState) {
        this.bestChildState = bestChildState;
    }

    public int getBestValue() {
        return bestValue;
    }

    public void setBestValue(int bestValue) {
        this.bestValue = bestValue;
    }
}