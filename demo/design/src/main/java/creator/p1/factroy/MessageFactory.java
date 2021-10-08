package creator.p1.factroy;

import creator.p1.entity.Message;

/**
 * 第一步
 * @Description: 抽象工程
 * 1. 负责创建产生对象
 * 2. 封装Message基础逻辑（主要）
 * @author: LinWeiQi
 */
public abstract class MessageFactory {


    //负责创建产生对象
    public abstract Message createMsg();

    //封装Message基础逻辑（主要）
    public void buss() {
        Message message = createMsg();
        message.init();
    }
}
