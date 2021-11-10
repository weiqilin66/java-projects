package org.wayne.design.constructor.p0.entity;

/**
 * 圆钉
 */
public class RoundPeg {
    /**半径**/
    private double radius;

    public RoundPeg() {}

    public RoundPeg(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }
}
