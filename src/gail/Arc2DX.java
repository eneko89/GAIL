/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gail;

import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;

/**
 *
 * @author eneko
 */
public class Arc2DX extends Arc2D.Double {
    
    public static Point2D.Double getCircleCenter(Point2D.Double a,
                                                 Point2D.Double b,
                                                 Point2D.Double c) {
        double ax = a.getX();
        double ay = a.getY();
        double bx = b.getX();
        double by = b.getY();
        double cx = c.getX();
        double cy = c.getY();

        double A = bx - ax;
        double B = by - ay;
        double C = cx - ax;
        double D = cy - ay;

        double E = A * (ax + bx) + B * (ay + by);
        double F = C * (ax + cx) + D * (ay + cy);

        double G = 2 * (A * (cy - by) - B * (cx - bx));
        if (G == 0.0)
            return null; // a, b, c must be collinear

        double px = (D * E - B * F) / G;
        double py = (A * F - C * E) / G;
        return new Point2D.Double(px, py);
    }

    public static double makeAnglePositive(double angleDegrees) {
        double ret = angleDegrees;
        if (angleDegrees < 0) {
            ret = 360 + angleDegrees;
        }
        return ret;
    }

    public static double getNearestAnglePhase(double limitDegrees,
                                              double sourceDegrees,
                                              int dir) {
        double value = sourceDegrees;
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

    public static Arc2D makeArc(Point2D.Double s, Point2D.Double mid, Point2D.Double e) {
        Point2D.Double c = getCircleCenter(s, mid, e);
        double radius = c.distance(s);

        double startAngle = makeAnglePositive(Math.toDegrees(-Math.atan2(s.y - c.y,
                                                                      s.x - c.x)));
        double midAngle = makeAnglePositive(Math.toDegrees(-Math.atan2(mid.y - c.y,
                                                                    mid.x - c.x)));
        double endAngle = makeAnglePositive(Math.toDegrees(-Math.atan2(e.y - c.y,
                                                                    e.x - c.x)));

        // Now compute the phase-adjusted angles begining from startAngle, moving 
        // positive and negative.
        double midDecreasing = getNearestAnglePhase(startAngle, midAngle, -1);
        double midIncreasing = getNearestAnglePhase(startAngle, midAngle, 1);
        double endDecreasing = getNearestAnglePhase(midDecreasing, endAngle, -1);
        double endIncreasing = getNearestAnglePhase(midIncreasing, endAngle, 1);

        // Each path from start -> mid -> end is technically, but one will wrap
        // around the entire circle, which isn't what we want. Pick the one that
        // with the smaller angular change.
        double extent;
        if (Math.abs(endDecreasing - startAngle)
                < Math.abs(endIncreasing - startAngle)) {
            extent = endDecreasing - startAngle;
        } else {
            extent = endIncreasing - startAngle;
        }

        return new Arc2D.Double(c.x - radius, c.y - radius, radius * 2,
                                radius * 2, startAngle, extent, Arc2D.OPEN);
    }

}
