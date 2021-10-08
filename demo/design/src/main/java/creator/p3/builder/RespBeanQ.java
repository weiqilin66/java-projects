package creator.p3.builder;

/**
 * @Description: 响应类
 * @author: LinWeiQi
 */

public class RespBeanQ {
    private int status;
    private String message;
    private Object data;

    public static RespBeanQ build(){
        return new RespBeanQ();
    }
    public RespBeanQ() {
    }

    public RespBeanQ(int status, String message, Object obj) {
        this.status = status;

        this.message = message;
        this.data = obj;
    }
    public static RespBeanQ ok(String msg) {
        return new RespBeanQ(200, msg, null);
    }
    public static RespBeanQ ok() {
        return new RespBeanQ(200, null, null);
    }
    public static RespBeanQ ok(Object o) {
        return new RespBeanQ(200, null, o);
    }
    public static RespBeanQ error() {
        return new RespBeanQ(500, null, null);
    }
    public static RespBeanQ ok(String msg, Object o) {
        return new RespBeanQ(200, msg, o);
    }

    public static RespBeanQ error(String msg) {
        return new RespBeanQ(500, msg, null);
    }

    public static RespBeanQ error(String msg, Object o) {
        return new RespBeanQ(500, msg, o);
    }

    public int getStatus() {
        return status;
    }

    public RespBeanQ setStatus(int status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public RespBeanQ setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public RespBeanQ setData(Object data) {
        this.data = data;
        return this;
    }
}
