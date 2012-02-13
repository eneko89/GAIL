/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gail;

import java.awt.Graphics;
import java.awt.Point;
import java.util.LinkedList;
import javax.swing.JComponent;

/**
 *
 * @author eneko
 */
public class MathGraph extends JComponent {
    
    private LinkedList<Node> nodes;
    private LinkedList<Edge> edges;

    public void add(Node node) {
        add(node);
    }
    
    public void add(Node node, Point location) {
        node.setLocation(location);
        add(node);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
    
}
