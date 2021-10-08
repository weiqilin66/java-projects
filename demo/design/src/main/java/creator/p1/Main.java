package creator.p1;

import creator.p1.factroy.NoticeMessageFactory;
import creator.p1.factroy.WarningMessageFactory;
import creator.p1.factroy.MessageFactory;

/**
 * @Description: 实现对象解耦
 * @author: LinWeiQi
 */
public class Main {
    // 声明工程
     static MessageFactory messageFactory;

    // 返回想要的对象工程
    public static void doMain(String type) {
        if ("waring".equalsIgnoreCase(type)) {
            messageFactory = new WarningMessageFactory();
        } else {
            messageFactory = new NoticeMessageFactory();
        }
        //执行核心逻辑
        messageFactory.buss();

    }

    public static void main(String[] args) {
        doMain("1");
    }


}
