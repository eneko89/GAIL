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
