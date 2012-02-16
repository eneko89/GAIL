/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gail;

/**
 *
 * @author eneko
 */
public class Edge {

    /**
     * The concept of an start and an end node is used to tell where
     * the directed edges should start and in which node they should end.
     */
    private Node startNode;
    private Node endNode;
    
    public Edge(Node startNode, Node endNode) {
        this.startNode = startNode;
        this.endNode = endNode;
    }

    public Node getStartNode() {
        return startNode;
    }
    
    public Node getEndNode() {
        return endNode;
    }
    
}
