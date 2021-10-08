package creator.p2.config;

import creator.p2.Application;
import creator.p2.factory.FashionFurniturefactory;
import creator.p2.factory.FurnitureFactory;
import creator.p2.factory.OldFurnitureFactory;

/**
 * @Description: 配置类 让客户端使用某一类工厂
 * @author: lwq
 */
public class ApplicationConfig {

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
