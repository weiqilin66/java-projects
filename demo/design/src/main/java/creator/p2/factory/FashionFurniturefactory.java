package creator.p2.factory;

import creator.p2.entity.ChairFashion;
import creator.p2.entity.IChair;
import creator.p2.entity.ISofa;
import creator.p2.entity.SofaFashion;

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
