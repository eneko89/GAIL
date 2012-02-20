/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gail;

import java.awt.*;
import javax.swing.JComponent;

/**
 *
 * @author eneko
 */
public class Node extends JComponent {
    
    private String label;
    
    public Node() {      
    }

    public Node(String label) {
        this.label = label;
    }

    public Node(int diameter) {
        setSize(diameter, diameter);
    }
     
    public Node(int diameter, String label) {
        setSize(diameter, diameter);
        this.label = label;
    }
    
    public Point getCenter() {
        return new Point(getWidth()/2, getHeight()/2);
    }
    
    public float getRadius() {
        return getWidth()/2;
    }
    
    public void setLabel(String label) {
        this.label = label;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(getBackground());
        g2.fillOval(1, 1, getWidth()-3, getHeight()-3);
        g2.setColor(getForeground());
        g2.drawOval(1, 1, getWidth()-3, getHeight()-3);
        if (label != null) {
            FontMetrics metrics = g2.getFontMetrics(getFont());
            int hgt = getHeight();
            int adv = metrics.stringWidth(label);
            Dimension textBoxSize = new Dimension(adv, hgt);
            //TODO fix the drawing position of the text
            g2.drawString(label, (getWidth() - textBoxSize.width) / 2,
                                  (getHeight() / 2) + 4);
        }
    }

    /**
     * Sets the size of the Node.
     * 
     * As the node must be a circle, width parameter will set the diameter of
     * the node and height parameter will be ignored.
     * 
     * @param width
     * @param height 
     */
    @Override
    public final void setSize(int width, int height) {
        super.setSize(width, width);
    }
    
    /**
     * Sets the size of the Node.
     * 
     * As the node must be a circle, width parameter will set the diameter of
     * the node and height parameter will be ignored.
     * 
     * @param d 
     */
    @Override
    public final void setSize(Dimension d) {
        super.setSize(new Dimension(d.width, d.height));
    }
    
    /**
     * Sets the size of the Node.
     * 
     * @param diameter 
     */
    public final void setSize(int diameter) {
        this.setSize(diameter, diameter);
    }
    
}