/*
 * This file is part of GAIL.
 *
 * GAIL is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * GAIL is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with GAIL.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright © 2011 Eneko Sanz Blanco <nkogear@gmail.com>
 *
 */

package gail;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
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
    private LinkedList<Node> nodes = new LinkedList<Node>();

    /**
     * List of edges.
     */
    private LinkedList<Edge> edges = new LinkedList<Edge>();
    
    /**
     * List of directed edges.
     */
    private LinkedList<Edge> directedEdges = new LinkedList<Edge>();

    /**
     * The transform used to rotate and place the arrowHead properly.
     */
    private AffineTransform transform = new AffineTransform();

    /**
     * The triangle shape (three point polygon) to draw an arrowHead.
     */
    private Polygon arrowHead;
    
    /**
     * Size of the arrow.
     * Should be allways positive.
     */
    private int arrowSize = 3;
    
    /**
     * Stroke of the graph nodes and edges.
     */
    private float lineStroke = 2f;

    public MathGraph() {
        createArrowHead();
    }

    public void add(Node node) {
        add((Component)node);
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
        g2.setStroke(new BasicStroke(lineStroke,
                            BasicStroke.CAP_ROUND,
                            BasicStroke.JOIN_ROUND));
        for(Edge e : edges) {
            Node startNode = e.getStartNode();
            Node endNode = e.getEndNode();
            Point startPoint = addPoints(startNode.getCenter(),
                                         startNode.getLocation());
            Point endPoint = addPoints(endNode.getCenter(),
                                       endNode.getLocation());
            g2.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
        }
        Line2DX tempLine = new Line2DX();
        for(Edge e : directedEdges) {
            Node startNode = e.getStartNode();
            Node endNode = e.getEndNode();
            Point startPoint = addPoints(startNode.getCenter(),
                                         startNode.getLocation());
            Point endPoint = addPoints(endNode.getCenter(),
                                       endNode.getLocation());
            tempLine.setLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
            Point.Float temp = tempLine.getPointAtDistance(
                                        tempLine.getDistanceTo(
                                                (Point2D.Float)tempLine.getP2())
                                                  - endNode.getRadius());
            tempLine.setLine(startPoint.x, startPoint.y, temp.x, temp.y);
            g2.draw(tempLine);
            drawArrowHead(g2, tempLine);
        }
        super.paintComponent(g);
    }
    
    private Point addPoints(Point p1, Point p2) {
        return new Point(p1.x + p2.x, p1.y + p2.y);
    }
    
    public void setArrowHeadSize(int size) {
        arrowSize = size;
        createArrowHead();
    }
    
    public void setLineStroke(float lineStroke) {
        this.lineStroke = lineStroke;
    }

    private void createArrowHead() {
        arrowHead = new Polygon();
        arrowHead.addPoint( 0,0);
        arrowHead.addPoint( -arrowSize, -arrowSize * 2);
        arrowHead.addPoint( arrowSize,-arrowSize * 2);
    }

    private void drawArrowHead(Graphics2D g2d, Line2D.Float line) {
        transform.setToIdentity();
        double angle = Math.atan2(line.y2-line.y1, line.x2-line.x1);
        transform.translate(line.x2, line.y2);
        transform.rotate((angle-Math.PI/2d));
        Graphics2D g = (Graphics2D) g2d.create();
        g.setTransform(transform);
        g.fill(arrowHead);
        g.draw(arrowHead);
    }
    
}