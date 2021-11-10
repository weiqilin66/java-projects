package org.wayne.design.creator.p2.factory;

import org.wayne.design.creator.p2.entity.IChair;
import org.wayne.design.creator.p2.entity.ISofa;

/**
 * @Description: 家具工厂
 * @author: lwq
 */
public interface FurnitureFactory {
    IChair createChair();
    ISofa createSofa();
}
