package org.wayne.proxy.static_demo;

/**
 * @Description: 静态代理
 * 1 真实对象 你
 * 2 代理对象 婚介公司
 *
 * 静态代理 只能执行固定类的方法
 * 动态代理= invoke方法动态执行类的方法
 *
 * @author: lwq
 */
public class StaticProxyDemo {

    interface Marry {
        void marry(String name);
    }

    // 你结婚
    class You implements Marry{

        @Override
        public void marry(String name) {
            System.out.println(name+"结婚了");
        }
    }


    // 婚介公司
    class MarryHelp {
        private Marry object;
        public MarryHelp(Marry o){
            this.object = o;
        }
        public void marry(String name) {
            System.out.println("婚前忙");
            this.object.marry(name);
            System.out.println("婚后忙");
        }
    }
}
