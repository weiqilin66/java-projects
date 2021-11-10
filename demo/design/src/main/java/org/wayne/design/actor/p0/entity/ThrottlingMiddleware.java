package org.wayne.design.actor.p0.entity;

/**
 * 处理者1 检查请求数量
 */
public class ThrottlingMiddleware extends Middleware{

    private final int requestPerMinute;
    private int request;
    private long currentTime;

    public ThrottlingMiddleware(int requestPerMinute) {
        this.requestPerMinute = requestPerMinute;
        this.currentTime = System.currentTimeMillis();
    }

    /**
     * 实现父类抽象方法 行为模式具体行为
     */
    public boolean check(String email, String password) {
        if (System.currentTimeMillis() > currentTime + 60_000) {
            request = 0;
            currentTime = System.currentTimeMillis();
        }

        request++;

        if (request > requestPerMinute) {
            System.out.println("Request limit exceeded!");
            Thread.currentThread().stop();
        }
        // 通过交给下一个处理者执行
        return checkNext(email, password);
    }
}
