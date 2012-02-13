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
     * Although directed graphs are not still supported, the concept of a
     * startNode and endNode makes it possible to implement it in the future.
     */
    private Node startNode;
    private Node endNode;
    
    public Edge(Node startNode, Node endNode) {
        this.startNode = startNode;
        this.endNode = endNode;
    }
    
}
