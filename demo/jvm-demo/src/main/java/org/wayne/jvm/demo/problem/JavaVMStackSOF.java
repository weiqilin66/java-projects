package org.wayne.jvm.demo.problem;

/**
 * 模拟虚拟机栈本地方法栈oom
 * VM Args：-Xss128k   栈容量只能由-Xss参数来设定
 */
public class JavaVMStackSOF {
    // 虚拟机栈包含局部变量表[存放了编译期可知的八大基本数据类型 ,对象引用]
    private int stackLength = 1;

    public void stackLeak() {
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) throws Throwable {
        JavaVMStackSOF oom = new JavaVMStackSOF();
        try {
            oom.stackLeak();
        } catch (Throwable e) {
            System.out.println("stack length:" + oom.stackLength);
            throw e;
        }
    }
}