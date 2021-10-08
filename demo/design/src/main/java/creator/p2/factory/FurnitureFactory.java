package creator.p2.factory;

import creator.p2.entity.IChair;
import creator.p2.entity.ISofa;

/**
 * @Description: 家具工厂
 * @author: lwq
 */
public interface FurnitureFactory {
    IChair createChair();
    ISofa createSofa();
}
