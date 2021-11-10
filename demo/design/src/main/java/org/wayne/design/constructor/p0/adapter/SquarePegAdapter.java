package org.wayne.design.constructor.p0.adapter;

import org.wayne.design.constructor.p0.entity.RoundPeg;
import org.wayne.design.constructor.p0.entity.SquarePeg;

/**
 * 方钉适配器
 * 1. 继承目标参数类型
 */
public class SquarePegAdapter extends RoundPeg {

    private SquarePeg peg;

    /**
     * 构造入参方钉
     * @param peg
     */
    public SquarePegAdapter(SquarePeg peg) {
        this.peg = peg;
    }

    /**
     * 重写半径方法
     * why: 因为RoundHole的fit方法判断的是半径调用的getRadius()
     * @return
     */
    @Override
    public double getRadius() {
        double result;
        // Calculate a minimum circle radius, which can fit this peg.
        result = (Math.sqrt(Math.pow((peg.getWidth() / 2), 2) * 2));
        return result;
    }
}
