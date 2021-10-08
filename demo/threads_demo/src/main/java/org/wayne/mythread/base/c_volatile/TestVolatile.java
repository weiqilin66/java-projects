package org.wayne.mythread.base.c_volatile;


import org.wayne.mythread.base.d_synchronized.block.MyObject;

/**
 * @Description: volatile(不稳定)指示JVM每次都到主存中读取变量
 * 在 JDK1.2 之前，Java的内存模型实现总是从主存（即共享内存）读取变量，是不需要进行特别的注意的。
 * 而在当前的 Java 内存模型下，线程可以把变量保存本地内存（比如机器的寄存器）中，而不是直接在主存中进行读写。
 * 这就可能造成一个线程在主存中修改了一个变量的值，而另外一个线程还继续使用它在寄存器中的变量值的拷贝，造成数据的不一致
 * 要解决这个问题，就需要把变量声明为 volatile，这就指示 JVM，这个变量是不稳定的，每次使用它都到主存中进行读取
 * @author: LinWeiQi
 */
public class TestVolatile {
    volatile int a;
    int b;
    // volatile关键字只能用于变量
    // synchronized关键字可以修饰方法以及代码块。
    synchronized public void t1(){

    }
    public void t2(MyObject object){
        synchronized(this){

        }
        synchronized (object){

        }
    }

}
/**
 JVM会尽力保证内存的可见性，即便这个变量没有加同步关键字。换句话说，只要CPU有时间，JVM会尽力去保证变量值的更新。
 这种与volatile关键字的不同在于，volatile关键字会强制的保证线程的可见性。而不加这个关键字，JVM也会尽力去保证可见性，
 但是如果CPU一直有其他的事情在处理，它也没办法。最开始的代码，一直处于死循环中，CPU处于一直占用的状态，这个时候CPU没有时间，
 JVM也不能强制要求CPU分点时间去取最新的变量值。而加了输出或者sleep语句之后，CPU就有可能有时间去保证内存的可见性，
 */
/**
 synchronized关键字和volatile关键字比较
 volatile关键字是线程同步的轻量级实现，所以volatile性能肯定比synchronized关键字要好。
 但是volatile关键字只能用于变量而synchronized关键字可以修饰方法以及代码块。
 synchronized关键字在JavaSE1.6之后进行了主要包括为了减少获得锁和释放锁带来的性能消耗而引入的偏向锁和轻量级锁以及其它各种优化之后执行效率有了显著提升，
 实际开发中使用synchronized关键字还是更多一些。
 多线程访问volatile关键字不会发生阻塞，而synchronized关键字可能会发生阻塞
 volatile关键字能保证数据的可见性，但不能保证数据的原子性。synchronized关键字两者都能保证。
 volatile关键字用于解决变量在多个线程之间的可见性，而ynchronized关键字解决的是多个线程之间访问资源的同步性。

 */