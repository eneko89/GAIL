/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gail;

import gail.animations.Animation;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.HashMap;
import javax.swing.JComponent;

/**
 *
 * @author eneko
 */
public class GridElement extends JComponent {

    private HashMap<String, Animation> actions;
    private Point positionOnGrid = new Point(0, 0);
    private BufferedImage backgroundImage = null;
    private BufferedImage scaledImage = null;
    private float opacity = 1;

    public GridElement() {
        actions = new HashMap<String, Animation>();
        setVisible(true);
    }

    public GridElement(BufferedImage backgroundImage) {
        this();
        this.backgroundImage = scaledImage = backgroundImage;
    }
    
    /**
     * Defines an action for this GridElement.
     * 
     * Each action name must be unique.
     * 
     * @param actionName
     * @param animation
     */
    public void defineAction(String name, Animation animation) {
        actions.put(name, animation);
    }
    
    /**
     * Removes an action from this GridElement.
     * 
     * @param name 
     */
    public void removeAction(String name) {
        actions.remove(name);
    }
    
    /**
     * Retrieve the animation assigned to the action name given as parameter.
     *
     * @param actionName
     * @return Animation or null if the action is not defined
     */
    public Animation getActionAnimation(String actionName) {
        return actions.get(actionName);
    }
    
    /**
     * Retrieve all the animations assigned.
     * 
     * @return Collection<Animation> 
     */
    public Collection<Animation> getActionAnimations() {
        return actions.values();
    }
    
    /**
     * Get grid position of this GridElement.
     * 
     * @return 
     */
    public Point getPositionOnGrid() {
        return positionOnGrid;
    }
    
    /**
     * Set the position of this GridElement on the Grid.
     * 
     * Note that the row/columns start with 0. The first is (0,0), so a 2x2 
     * grid would have {(0,0)(0,1)(1,0)(1,1)} positions.
     * 
     * @param gridPosition 
     */
    public void setPositionOnGrid(Point gridPosition) {
        this.positionOnGrid = gridPosition;
    }
    
    /**
     * Returns the grid which this component is attached to.
     * 
     * @return null if not attached to any Grid
     */
    public Grid getGrid() {
        Container grid = this.getParent();
        if ((grid != null)&&(grid instanceof Grid))
            return (Grid)grid;
        else
            return null;
    }
    
    public boolean isPerformingAnimations() {
        Collection<Animation> animations = actions.values();
        for(Animation anim : animations){
            if (anim.isRunning() == true)
                return true;
        }
        return false;
    }
    
    public void pauseAnimations() {
        Collection<Animation> animations = actions.values();
        for(Animation anim : animations){
            if (anim.isRunning() == true)
                anim.pause();
        }
    }
    
    public void resumeAnimations() {
        Collection<Animation> animations = actions.values();
        for(Animation anim : animations){
            if (anim.isRunning() == true)
                anim.resume();
        }
    }
    
    public void cancelAnimations() {
        Collection<Animation> animations = actions.values();
        for(Animation anim : animations){
            if (anim.isRunning() == true)
                anim.cancel();
        }
    }

    public float getOpacity() {
        return opacity;
    }

    public void setOpacity(float opacity) {
        this.opacity = opacity;
        repaint();
    }

    @Override
    public void setSize(Dimension d) {
        super.setSize(d);
        if ((backgroundImage != null)&&(getWidth() > 0)&&(getHeight() > 0)) {
            int size = getWidth() > getHeight() ? getHeight() : getWidth();
            scaledImage = getScaledInstance(backgroundImage,
                                            size,
                                            size,
                                            RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        }
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        if ((backgroundImage != null)&&(getWidth() > 0)&&(getHeight() > 0)) {
            int size = getWidth() > getHeight() ? getHeight() : getWidth();
            scaledImage = getScaledInstance(backgroundImage,
                                            size,
                                            size,
                                            RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (scaledImage != null) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                                RenderingHints.VALUE_RENDER_QUALITY);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                                       opacity));
            int drawPosx = (this.getWidth() - scaledImage.getWidth()) / 2;
            int drawPosy = (this.getHeight() - scaledImage.getHeight()) / 2;
            g2.drawImage(scaledImage, drawPosx, drawPosy, null);
        }
    }

    /**
     * Convenience method that returns a scaled instance of the
     * provided {@code BufferedImage}.
     *
     * @param img the original image to be scaled
     * @param targetWidth the desired width of the scaled instance,
     *    in pixels
     * @param targetHeight the desired height of the scaled instance,
     *    in pixels
     * @param hint one of the rendering hints that corresponds to
     *    {@code RenderingHints.KEY_INTERPOLATION} (e.g.
     *    {@code RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR},
     *    {@code RenderingHints.VALUE_INTERPOLATION_BILINEAR},
     *    {@code RenderingHints.VALUE_INTERPOLATION_BICUBIC})
     * @return a scaled version of the original {@code BufferedImage}
     */
    private BufferedImage getScaledInstance(BufferedImage img,
                                           int targetWidth,
                                           int targetHeight,
                                           Object hint)
    {
        int type = (img.getTransparency() == Transparency.OPAQUE)
                                            ? BufferedImage.TYPE_INT_RGB
                                            : BufferedImage.TYPE_INT_ARGB;

        BufferedImage ret = (BufferedImage)img;

        BufferedImage tmp = new BufferedImage(targetWidth, targetHeight, type);
        Graphics2D g2 = tmp.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
        g2.drawImage(ret, 0, 0, targetWidth, targetHeight, null);
        g2.dispose();

        ret = tmp;

        return ret;

    }


}
