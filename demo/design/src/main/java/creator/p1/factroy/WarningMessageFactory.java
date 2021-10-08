package creator.p1.factroy;

import creator.p1.entity.WaringMessage;
import creator.p1.entity.Message;

/**
 * @Description:   工厂实现类 对应一个接口实现类WaringMessage
 * @author: LinWeiQi
 */
public class WarningMessageFactory extends MessageFactory {
    @Override
    public Message createMsg() {
        return new WaringMessage();
    }
}
