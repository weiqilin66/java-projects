package org.wayne.common.qlexpress.context;

import com.ql.util.express.ExpressRunner;
import com.ql.util.express.IExpressContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @TODO:
 * @author: lwq
 */
@Slf4j
@Component
public class ExpressRunnerInit implements ApplicationContextAware {

    private ApplicationContext applicationContext;
    private ExpressRunner runner;

    @Override
    public void setApplicationContext(ApplicationContext context) {
        this.applicationContext = context;
        runner = new ExpressRunner();
    }

    public Object executeExpress(String expressStr, Map<String, Object> context) {
        IExpressContext<String, Object> expressContext = new QlSpringBeanContext(context, this.applicationContext);
        try {
            return runner.execute(expressStr, expressContext, null, true, false);
        } catch (Exception e) {
            log.error("qlExpress运行出错！", e);
        }
        return null;
    }
}

