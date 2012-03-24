/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gail.grid.animations;

import gail.grid.GridElement;
import java.awt.Point;
import java.util.concurrent.TimeUnit;
import org.jdesktop.core.animation.timing.Animator;
import org.jdesktop.core.animation.timing.PropertySetter;
import org.jdesktop.core.animation.timing.TimingTarget;
import org.jdesktop.core.animation.timing.interpolators.AccelerationInterpolator;

/**
 *
 * @author eneko
 */
public class AppearAnimation extends Animation {
    
    /**
     * Default constructor.
     * 
     * It will use a 600ms duration for the animation.
     */
    public AppearAnimation() {
        this.duration = 600;
    }
    
    /**
     * Constructor with custom animation duration.
     * 
     * @param duration the duration of the animation in milliseconds
     */
    public AppearAnimation(int duration) {
        this.duration = duration;
    }
    
    @Override
    public Point animate(GridElement ge) {
        ge.setVisible(true);
        animator = new Animator.Builder(timingSource)
                        .setInterpolator(new AccelerationInterpolator(0.1, 0.8))
                        .setDuration(duration, TimeUnit.MILLISECONDS)
                        .build();
        TimingTarget setter = PropertySetter.getTarget(ge, "opacity", 0f, 1f);
        animator.addTarget(setter);
        animator.start();
        return ge.getPositionOnGrid();
    }

}
