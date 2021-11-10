package org.wayne.design.creator.p2;

import org.wayne.design.creator.p2.entity.IChair;
import org.wayne.design.creator.p2.entity.ISofa;
import org.wayne.design.creator.p2.factory.FurnitureFactory;

/**
 * @Description: 客户端代码
 * @author: lwq
 */
public class Application {
    private IChair chair;
    private ISofa sofa;

    public Application(FurnitureFactory factory) {
        chair = factory.createChair();
        sofa = factory.createSofa();
    }
    // 封装chair sofa的业务逻辑
    public void ability(){
        chair.sitOn();
        sofa.lieOn();
    }
}