/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gail.animations;

import gail.GridElement;
import gail.GridElement;
import gail.GridElement;
import java.awt.Point;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.core.animation.timing.Animator;
import org.jdesktop.swing.animation.timing.sources.SwingTimerTimingSource;

/**
 *
 * @author eneko
 */
public abstract class Animation {

    protected Animator animator = null;

    /** Timing source of the animations */
    protected static final SwingTimerTimingSource timingSource =
                                                  new SwingTimerTimingSource();
    
    static {
        timingSource.init();
    }

    /**
     * @return true if the animation is running, false if not.
     */
    public boolean isRunning() {
        return animator != null ? animator.isRunning() : false;
    }

    /**
     * Pauses the animation until resume() is called.
     */
    public void pause() {
        animator.pause();
    }

    /**
     * Resumes a previously paused animation.
     */
    public void resume() {
        animator.resume();
    }

    /**
     * Cancels a running animation.
     */
    public void cancel() {
        animator.stop();
    }
    
    public void await() {
        try {
            animator.await();
        } catch (InterruptedException ex) {
            Logger.getLogger(Animation.class.getName()).log(Level.SEVERE, null,
                                                                            ex);
        }
    }
    
    /**
     * Create a new animator and start it.
     * 
     * Should use provided timingSource and return inmediately.
     * 
     * @param ge the animation target
     * @return the final position of the element on the grid
     */
    public abstract Point animate(GridElement ge);

    /**
     * This function is called when the animation ends.
     *
     * Override it if needed to perfom an action on animation end.
     *
     */
    protected void animationEnded() {

    }

}
