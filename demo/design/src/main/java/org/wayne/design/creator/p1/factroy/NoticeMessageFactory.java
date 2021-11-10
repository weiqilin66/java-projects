package org.wayne.design.creator.p1.factroy;

import org.wayne.design.creator.p1.entity.NoticeMessage;
import org.wayne.design.creator.p1.entity.Message;

/**
 * @Description:   工厂实现类 对应一个接口实现类
 * @author: LinWeiQi
 */
public class NoticeMessageFactory extends MessageFactory {
    @Override
    public Message createMsg() {
        return new NoticeMessage();
    }
}
