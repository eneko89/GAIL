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

package gail.animations;

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
