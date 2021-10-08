package creator.p2;

import creator.p2.entity.IChair;
import creator.p2.entity.ISofa;
import creator.p2.factory.FurnitureFactory;

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
    public void ability(){
        chair.sitOn();
        sofa.lieOn();
    }
}