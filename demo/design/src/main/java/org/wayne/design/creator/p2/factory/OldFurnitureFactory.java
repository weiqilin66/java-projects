package org.wayne.design.creator.p2.factory;

import org.wayne.design.creator.p2.entity.ChairOld;
import org.wayne.design.creator.p2.entity.IChair;
import org.wayne.design.creator.p2.entity.ISofa;
import org.wayne.design.creator.p2.entity.SofaOld;

/**
 * @Description: 返回一系列老旧沙发
 * @author: lwq
 */
public class OldFurnitureFactory implements FurnitureFactory{
    @Override
    public IChair createChair() {
        return new ChairOld();
    }

    @Override
    public ISofa createSofa() {
        return new SofaOld();
    }
}
