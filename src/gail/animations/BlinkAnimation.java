/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gail.animations;

import gail.GridElement;
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
public class BlinkAnimation extends Animation {

    @Override
    public Point animate(final GridElement ge) {
                animator = new Animator.Builder(timingSource)
                        .setInterpolator(new AccelerationInterpolator(0.2, 0.1))
                        .setDuration(1500, TimeUnit.MILLISECONDS)
                        .build();
        TimingTarget setter = PropertySetter.getTarget(ge, "opacity",
                                                       1f, 0.6f, 1f);
        animator.addTarget(setter);
        animator.start();
        return ge.getPositionOnGrid();
    }
    
}
