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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Point2D;
import javax.swing.WindowConstants;

/**
 *
 * @author eneko
 */
public class GraphDemo extends javax.swing.JFrame {

    // Drag start point for the "E" node
    private Point dragStart;

    /**
     * Creates an ExampleFrame
     */
    public GraphDemo() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(400, 400));
        // Create a MathGraph
        MathGraph graph = new MathGraph();

        // Set is as this frame's content pane
        this.setContentPane(graph);

        // Create some nodes
        Node a = new Node(30, "A");
        Node b = new Node(30, "B");

        // Add them to the graph in the desired position
        graph.add(a, new Point(5, 5));
        graph.add(b, new Point(50, 78));

        // Establish a directed link between them
        graph.directedLink(a, b);

        // And so on :)
        Node c = new Node(50, "C");
        graph.add(c, new Point(80, 33));

        // This is a regular link (is not directed, it hasn't got an arrow)
        graph.link(b, c);
        graph.directedLink(c, a);
        Node d = new Node(50, "D");
        graph.add(d, new Point(80, 170));
        graph.directedLink(d, c);
        graph.directedLink(b, d);
        Node e = new Node(70, "E");
        graph.add(e, new Point(150, 50));
        graph.link(c, e);

        // Now we enable dragging for the nodes
        makeDraggable(a);
        makeDraggable(b);
        makeDraggable(c);
        makeDraggable(d);
        makeDraggable(e);

    }

    private void makeDraggable(Node n) {
        n.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                dragStart = e.getPoint();
            }
        });
        n.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Component dragTarget = (Component)e.getSource();
                Point location = dragTarget.getLocation();
                dragTarget.setLocation(location.x + e.getX() - dragStart.x,
                              location.y + e.getY() - dragStart.y);
                repaint();
            }
        });
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new GraphDemo().setVisible(true);
            }
        });
    }

}
