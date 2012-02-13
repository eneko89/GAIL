/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gail;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import javax.swing.JComponent;

/**
 *
 * @author eneko
 */
public class Node extends JComponent {

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawOval(getX(), getY(), getWidth(), getHeight());
        g2.drawString("1", getX() + 5, getY() + 5);
    }

}
