package org.wayne.design.creator.p2.factory;

import org.wayne.design.creator.p2.entity.ChairFashion;
import org.wayne.design.creator.p2.entity.IChair;
import org.wayne.design.creator.p2.entity.ISofa;
import org.wayne.design.creator.p2.entity.SofaFashion;

/**
 * @Description: 返回一系列
 * @author: lwq
 */
public class FashionFurniturefactory implements FurnitureFactory{
    @Override
    public IChair createChair() {
        return new ChairFashion();
    }

    @Override
    public ISofa createSofa() {
        return new SofaFashion();
    }
}
