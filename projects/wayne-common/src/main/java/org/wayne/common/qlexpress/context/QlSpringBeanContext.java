package org.wayne.common.qlexpress.context;

import com.ql.util.express.ExpressRunner;
import com.ql.util.express.IExpressContext;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @TODO:
 * @author: lwq
 */
public class QlSpringBeanContext extends HashMap<String, Object> implements IExpressContext<String, Object> {

    private ApplicationContext applicationContext;

    public QlSpringBeanContext(Map<String, Object> map, ApplicationContext applicationContext) {
        super(map);
        this.applicationContext = applicationContext;
    }

    /**
     * 根据key从容器里面获取对象
     */
    public Object get(String key) {
        Object object = super.get(key);
        try {
            if (object == null && this.applicationContext != null
                    && this.applicationContext.containsBean(key)) {

                object = this.applicationContext.getBean(key);
            }
        } catch (Exception e) {
            throw new RuntimeException("表达式容器获取对象失败", e);
        }
        return object;
    }

    /**
     * 把key-value放到容器里面去
     *
     * @param key
     * @param value
     */
    public Object put(String key, Object value) {
        return super.put(key, value);
    }
}

