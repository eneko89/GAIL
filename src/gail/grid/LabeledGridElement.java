/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gail.grid;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 *
 * @author eneko
 */
public class LabeledGridElement extends GridElement {
    
    private JLabel label = new JLabel();

    public LabeledGridElement(String string) {
        addLabel(string);
    }
    
    public LabeledGridElement(String string, BufferedImage backgroundImage) {
        super(backgroundImage);
        addLabel(string);
    }
    
    public void setText(String string) {
        label.setText(string);
    }
    
    public String getText() {
        return label.getText();
    }
    
    public void setFontSize(float f) {
        label.setFont(label.getFont().deriveFont(f));
    }
    
    private void addLabel(String string) {
        label.setText(string);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                label.setSize(getSize());
            }
        });
        label.setLocation(0,0);
        label.setVisible(true);
        add(label);
    }
    
}
