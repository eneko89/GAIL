/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gail.graph;

import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;

/**
 *
 * @author eneko
 */
public class Arc2DX extends Arc2D.Float {
    
    public static Point2D.Float getCircleCenter(Point2D.Float a,
                                                 Point2D.Float b,
                                                 Point2D.Float c) {
        float ax = a.x;
        float ay = a.y;
        float bx = b.x;
        float by = b.y;
        float cx = c.x;
        float cy = c.y;

        float A = bx - ax;
        float B = by - ay;
        float C = cx - ax;
        float D = cy - ay;

        float E = A * (ax + bx) + B * (ay + by);
        float F = C * (ax + cx) + D * (ay + cy);

        float G = 2 * (A * (cy - by) - B * (cx - bx));
        if (G == 0.0)
            return null; // a, b, c must be collinear

        float px = (D * E - B * F) / G;
        float py = (A * F - C * E) / G;
        return new Point2D.Float(px, py);
    }

    public static float makeAnglePositive(float angleDegrees) {
        float ret = angleDegrees;
        if (angleDegrees < 0) {
            ret = 360 + angleDegrees;
        }
        return ret;
    }

    public static float getNearestAnglePhase(float limitDegrees,
                                              float sourceDegrees,
                                              int dir) {
        float value = sourceDegrees;
        if (dir > 0) {
            while (value < limitDegrees) {
            value += 360.0;
            }
        } else if (dir < 0) {
            while (value > limitDegrees) {
            value -= 360.0;
            }
        }
        return value;
    }

    public static Arc2D makeArc(Point2D.Float s,
                                Point2D.Float mid,
                                Point2D.Float e) {
        Point2D.Float c = getCircleCenter(s, mid, e);
        float radius = (float) c.distance(s);

        float startAngle = makeAnglePositive((float)Math.toDegrees(-Math.atan2(
                                                        s.y - c.y, s.x - c.x)));
        float midAngle = makeAnglePositive((float)Math.toDegrees(-Math.atan2(
                                                    mid.y - c.y, mid.x - c.x)));
        float endAngle = makeAnglePositive((float)Math.toDegrees(-Math.atan2(
                                                        e.y - c.y, e.x - c.x)));

        // Now compute the phase-adjusted angles begining from startAngle, moving 
        // positive and negative.
        float midDecreasing = getNearestAnglePhase(startAngle, midAngle, -1);
        float midIncreasing = getNearestAnglePhase(startAngle, midAngle, 1);
        float endDecreasing = getNearestAnglePhase(midDecreasing, endAngle, -1);
        float endIncreasing = getNearestAnglePhase(midIncreasing, endAngle, 1);

        // Each path from start -> mid -> end is technically, but one will wrap
        // around the entire circle, which isn't what we want. Pick the one that
        // with the smaller angular change.
        float extent;
        if (Math.abs(endDecreasing - startAngle)
                < Math.abs(endIncreasing - startAngle)) {
            extent = endDecreasing - startAngle;
        } else {
            extent = endIncreasing - startAngle;
        }

        return new Arc2D.Float(c.x - radius, c.y - radius, radius * 2,
                                radius * 2, startAngle, extent, Arc2D.OPEN);
    }

}
