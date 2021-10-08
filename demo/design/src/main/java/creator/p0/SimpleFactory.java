package creator.p0;

/**
 * @Description: 简单工厂是引入工厂方法或抽象工厂模式时的一个中间步骤。
 * 没有子类
 * @author: lwq
 */
public class SimpleFactory {
    public static String create(String type) throws Exception {
        switch (type) {
            case "user": return "new User()";
            case "customer": return "new Customer()";
            case "admin": return "new Admin()";
            default:
                throw new Exception("传递的用户类型错误。");
        }
    }
}
