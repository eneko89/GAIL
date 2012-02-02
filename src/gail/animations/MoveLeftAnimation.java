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
public class MoveLeftAnimation extends Animation {

    @Override
    public Point animate(GridElement ge) {
        animator = new Animator.Builder(timingSource)
                        .setInterpolator(new AccelerationInterpolator(0.1, 0.8))
                        .setDuration(600, TimeUnit.MILLISECONDS)
                        .build();
        Point finalLocation = new Point(ge.getX() - ge.getGrid().getCellWidth(),
                                                                     ge.getY());
        TimingTarget setter = PropertySetter.getTarget(ge, "location",
                                             ge.getLocation(), finalLocation);
        animator.addTarget(setter);
        animator.start();
        return new Point(ge.getPositionOnGrid().x - 1, ge.getPositionOnGrid().y);
    }

}
