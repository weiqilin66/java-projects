package creator.p1.entity;

/**
 * @Description: 消息接口
 * @author: LinWeiQi
 */
public interface Message {
    String content="";
    // 模拟核心方法
    void init();
    // 消息基本方法
    void buildContent();
}
