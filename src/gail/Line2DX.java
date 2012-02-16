/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gail;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 *
 * @author eneko
 */
public class Line2DX extends Line2D.Float {

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
    
    public Line2DX getPerpendicularAt(Point2D p) {
        double invSlope= -Math.pow(getSlope(), -1);
        float x = 0;
        float y = (float) (invSlope * (x - p.getX()) + p.getY());
        return new Line2DX((float)p.getX(), (float)p.getY(), x, y);
    }

}