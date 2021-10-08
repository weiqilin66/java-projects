package org.wayne.thief.util;

import lombok.Data;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

/**
 * @Description: 实现Aware接口且被Spring扫描到
 * @author: lwq
 */
@Component
public class AppUtil implements EmbeddedValueResolverAware, ApplicationContextAware {
    private static ChromeDriver chromeDriver;
    static ApplicationContext context;
    private static StringValueResolver resolver;

    public synchronized static ChromeDriver getTbChromeDriver(){
        if (chromeDriver==null) {
            final ChromeOptions chromeOptions = context.getBean("tbOptions", ChromeOptions.class);
            chromeDriver =  new ChromeDriver(chromeOptions);
        }
        return chromeDriver;
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver stringValueResolver) {
        resolver = stringValueResolver;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context=applicationContext;
    }
}
