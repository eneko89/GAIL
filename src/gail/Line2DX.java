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

package gail;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 *
 * @author eneko
 */
public class Line2DX extends Line2D.Float {

    public Line2DX() {
        super();
    }

    public Line2DX(Point2D p1, Point2D p2) {
        super(p1, p2);
    }
    
    public Line2DX(float x1, float y1, float x2, float y2) {
        super(x1, y1, x2, y2);
    }
    
    /**
     * Gets the X coordinate of the line matching the given Y coordinate.
     * 
     * It shlouldn't be used when the line is vertical or horizontal.
     * 
     * With horizontal lines, it will return positive or negative infinity
     * when the given Y is not defined, and NaN if the given Y coordinate is
     * where the horizontal line is located.
     * 
     * With vertical lines it will allways return positive or negative infinity.
     * 
     * @param y the Y coordinate
     * @return the X coordinate matching Y coordinate
     */
    public float getX(float y) {
        float slope = getSlope();
        return (y - y1 + (slope * x1)) / slope;
    }
    
    /**
     * Gets the Y coordinate of the line matching the given X coordinate.
     * 
     * It shlouldn't be used when the line is vertical or horizontal.
     * 
     * With horizontal lines it will return the Y parameter where the horizontal
     * line is located
     *
     * With vertical lines, the result is hardly predictable; it could be NaN,
     * negative infinity or positive infinity.
     * 
     * @param x
     * @return 
     */
    public float getY(float x) {
        float slope = getSlope();
        return (slope * x) - (slope * x1) + y1;
    }

    public float getSlope() {
        return (y2 - y1) / (x2 - x1);
    }
    
    public Point2D.Float getMidPoint() {
        return new Point2D.Float((x1+x2)/2, (y1+y2)/2);
    }

    public Line2DX getPerpendUpwardsFrom(Point2D.Float p, float length) {
        float invSlope= (float) -Math.pow(getSlope(), -1);
        Line2DX perpendLine = null;
        if (java.lang.Float.isInfinite(invSlope)) {
            perpendLine = new Line2DX(p.x, p.y, p.x,
                                      x1 > x2 ? p.y + 10 : p.y - 10);
        } else if (invSlope < 0) {
            float x = x1 > x2 ? p.x - 10 : p.x + 10;
            float y = (float) (invSlope * (x - p.x) + p.y);
            perpendLine = new Line2DX(p.x, p.y, x, y);
        } else if (invSlope > 0) {
            float x = x1 > x2 ? p.x + 10 : p.x - 10;
            float y = (float) (invSlope * (x - p.x) + p.y);
            perpendLine = new Line2DX(p.x, p.y, x, y);
        } else if (invSlope == 0){
            perpendLine = new Line2DX(p.x, p.y,
                                      y1 > y2 ? p.x - 10 : p.x + 10,
                                                                p.y);
        }
        perpendLine.setLine(perpendLine.getP1(),
                            perpendLine.getPointAtDistance(length));
        return perpendLine;
    }
    
    public Line2DX getPerpendDownwardsFrom(Point2D.Float p, float length) {
        float invSlope= (float) -Math.pow(getSlope(), -1);
        Line2DX perpendLine = null;
        if (java.lang.Float.isInfinite(invSlope)) {
            perpendLine = new Line2DX(p.x, p.y, p.x,
                                      x1 > x2 ? p.y - 10 : p.y + 10);
        } else if (invSlope < 0) {
            float x = x1 > x2 ? p.x + 10 : p.x - 10;
            float y = (float) (invSlope * (x - p.x) + p.y);
            perpendLine = new Line2DX(p.x, p.y, x, y);
        } else if (invSlope > 0) {
            float x = x1 > x2 ? p.x - 10 : p.x + 10;
            float y = (float) (invSlope * (x - p.x) + p.y);
            perpendLine = new Line2DX(p.x, p.y, x, y);
        } else if (invSlope == 0){
            perpendLine = new Line2DX(p.x, p.y,
                                      y1 > y2 ? p.x + 10 : p.x - 10,
                                                                p.y);
        }
        perpendLine.setLine(perpendLine.getP1(),
                            perpendLine.getPointAtDistance(length));
        return perpendLine;
    }

    /**
     * Gets the point on this segment at the given distance from the start
     * point (P1).
     * 
     * @param distance distance from p1
     * @return the point at the distance as parameter
     */
    public Point2D.Float getPointAtDistance(float distance) {
        if (isHorizontal()) {
            if (x1 > x2)
                distance = -distance;
            return new Point2D.Float(x1 + distance, y1);
        }
        if (isVertical()) {
            if (y1 > y2)
                distance = -distance;
            return new Point2D.Float(x1, y1 + distance);
        }
        if (x1 > x2)
            distance = -distance;
        float angle = (float) Math.atan(getSlope());
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
     * @return true if this line is horizontal (slope = 0), false if not
     */
    public boolean isHorizontal() {
        return y1 == y2;
    }
    
    /**
     * @return true if this line is vertical (slope = infinity), false if not
     */
    public boolean isVertical() {
        return x1 == x2;
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