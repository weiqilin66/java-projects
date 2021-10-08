package org.wayne.mythread.base.d_synchronized.block;

/**
 * @Description: 对象(同步)监视器 随意类
 * syn方法中无需指定监视器 因为其监视的是this对象本身,即class类
 * 执行过程
 * 1. 第1个线程访问,锁定同步监视器,执行其中的代码
 * 2. 第2个线程访问,发现同步监视器被锁定, 无法访问
 * 3. 第1个线程执行完毕,第2个线程发现同步监视器未被锁,锁定并访问
 * @author: LinWeiQi
 */
public class MyObject{
}


/** java4种块
 * 1方法块(方法体) 解决变量作用域快速释放内存
 * 构造块(类下面的{}) 与构造器效果一样,初始化对象信息的
 * 静态块(构造块加static) 只加载一次 初始化类的 先于构造块执行
 * 同步块( syn(obj){} ) 解决同步安全
 */
