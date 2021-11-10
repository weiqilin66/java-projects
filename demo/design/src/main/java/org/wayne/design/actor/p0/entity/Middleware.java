package org.wayne.design.actor.p0.entity;

public abstract class Middleware {
    // 下一个处理者
    private Middleware next;

    /**
     * 核心方法 绑定下一个执处理者
     * 并返回下一个处理者,使构造时候可以循环调用
     */
    public Middleware linkWith(Middleware next) {
        this.next = next;
        return next;
    }

    /**
     * 子类实现具体业务
     */
    public abstract boolean check(String email, String password);

    /**
     * 执行下一个处理者的check方法
     */
    protected boolean checkNext(String email, String password) {
        if (next == null) {
            return true;
        }
        return next.check(email, password);
    }
}
