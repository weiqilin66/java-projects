package org.wayne.design.creator.p2;

import org.wayne.design.creator.p2.factory.FashionFurniturefactory;
import org.wayne.design.creator.p2.factory.FurnitureFactory;
import org.wayne.design.creator.p2.factory.OldFurnitureFactory;

/**
 * @Description: 配置类 让客户端使用某一类工厂
 * @author: lwq
 */
public class ApplicationConfigMain {

    public static Application newInstance(){
        Application app;
        FurnitureFactory factory;
        // 各种方式配置
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("mac")) {
            factory = new FashionFurniturefactory();
            app = new Application(factory);
        } else {
            factory = new OldFurnitureFactory();
            app = new Application(factory);
        }
        return app;
    }
}
