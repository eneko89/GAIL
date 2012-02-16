/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gail;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.LinkedList;
import javax.swing.JComponent;

/**
 *
 * @author eneko
 */
public class MathGraph extends JComponent {

    /**
     * List of nodes.
     */
    private LinkedList<Node> nodes;

    /**
     * List of edges.
     */
    private LinkedList<Edge> edges;
    
    /**
     * List of directed edges.
     */
    private LinkedList<Edge> directedEdges;

    /**
     * The transform used to rotate and place the arrowHead properly.
     */
    private AffineTransform transform = new AffineTransform();

    /**
     * The triangle shape (three point polygon) to draw an arrowHead.
     */
    private Polygon arrowHead = new Polygon();


    public MathGraph() {
        initArrowHead();
    }

    public void add(Node node) {
        add(node);
    }
    
    public void add(Node node, Point location) {
        node.setLocation(location);
        add(node);
    }
    
    public void link(Node n1, Node n2) {
        edges.add(new Edge(n1, n2));
    }
    
    public void directedLink(Node from, Node to) {
        directedEdges.add(new Edge(from, to));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                            RenderingHints.VALUE_RENDER_QUALITY);
        g2.setStroke(new BasicStroke(2.0f,
                            BasicStroke.CAP_ROUND,
                            BasicStroke.JOIN_ROUND));
        for(Edge e : edges) {
            Node n1 = e.getStartNode();
            Node n2 = e.getEndNode();
            g2.drawLine(n1.getX(), n1.getY(), n2.getX(), n2.getY());
        }
        Line2D.Float tempLine = new Line2D.Float();
        for(Edge e : directedEdges) {
            Node start = e.getStartNode();
            Node end = e.getEndNode();
            tempLine.setLine(start.getX(), start.getY(), end.getX(), end.getY());
            g2.draw(tempLine);
            drawArrowHead(g2, tempLine);
        }
    }

    private void initArrowHead() {
        arrowHead.addPoint( 0,5);
        arrowHead.addPoint( -5, -5);
        arrowHead.addPoint( 5,-5);
    }

    private void drawArrowHead(Graphics2D g2d, Line2D.Float line) {
        transform.setToIdentity();
        double angle = Math.atan2(line.y2-line.y1, line.x2-line.x1);
        transform.translate(line.x2, line.y2);
        transform.rotate((angle-Math.PI/2d));
        Graphics2D g = (Graphics2D) g2d.create();
        g.setTransform(transform);
        g.fill(arrowHead);
    }
    
}