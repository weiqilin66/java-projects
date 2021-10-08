package creator.p1.entity;

/**
 * @Description: 模拟接口的实现类2
 * @author: LinWeiQi
 */
public class NoticeMessage implements Message {
    @Override
    public void init() {
        System.out.println("notice...init");
    }

    @Override
    public void buildContent() {
        init();
    }
}
