/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gail;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 *
 * @author eneko
 */
public class Line2DX extends Line2D.Float {

    Line2DX() {
        super();
    }

    public Line2DX(Point2D p1, Point2D p2) {
        super(p1, p2);
    }
    
    public Line2DX(float x1, float y1, float x2, float y2) {
        super(x1, y1, x2, y2);
    }

    public float getX(float y) {
        float slope = getSlope();
        return (y - y1 + (slope * x1)) / slope;
    }

    public float getY(float x) {
        float slope = getSlope();
        return (slope * x) - (slope * x1) + y1;
    }

    public float getSlope() {
        return (y2 - y1) / (x2 - x1);
    }
    
    public Line2DX getPerpendicularAt(Point2D.Float p) {
        double invSlope= -Math.pow(getSlope(), -1);
        float x = 0;
        float y = (float) (invSlope * (x - p.x) + p.y);
        return new Line2DX((float)p.getX(), (float)p.getY(), x, y);
    }

    public Point2D.Float getPointAtDistance(float distance) {
        float slope = getSlope();
        if (slope == 0) {
            if (x1 > x2)
                distance = -distance;
            return new Point2D.Float(x1 + distance, y1);
        }
        if (java.lang.Float.isInfinite(slope)) {
            if (y1 > y2)
                distance = -distance;
            return new Point2D.Float(x1, y1 + distance);
        }
        if (x1 > x2)
            distance = -distance;
        float angle = (float) Math.atan(slope);
        float y = (float) (Math.sin(angle) * distance) + y1;
        float x = getX(y);
        return new Point.Float(x, y);
    }
    
    /**
     * Calculates the distance of the given point from the start point
     * of the segment (P1).
     * 
     * @param point
     * @return distance from P1 to the given point, allways positive
     */
    public float getDistanceTo(Point2D.Float point) {
        return (float) Math.sqrt(powOf2(point.x - x1) + powOf2(point.y - y1));
    }
    
    /**
     * Calculates the power of two.
     * 
     * @param number
     * @return power of two of the number
     */
    private float powOf2(float number) {
        return number * number;
    }
    
}