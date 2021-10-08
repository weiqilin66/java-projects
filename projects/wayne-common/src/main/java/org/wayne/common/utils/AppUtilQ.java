package org.wayne.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

/**
 * @Description: 工具类
 * ${}解析器
 * ApplicationContext上下文
 * 注册Bean
 * @author: lwq
 */
@Component
public class AppUtilQ implements EmbeddedValueResolverAware, ApplicationContextAware {
    public static StringValueResolver resolver;
    public static ApplicationContext context;
    public static BeanDefinitionRegistry registry;

    public String getRemoteValue(String key) {
        final String s = String.format("${%s}", key);
        return resolver.resolveStringValue(s);
    }

    public ApplicationContext getContext() {
        return context;
    }

    public void registerBean(String beanName, Object object,Class clazz,String property ) {
        BeanDefinitionBuilder definitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
        if (property!=null && property.length()!=0){
            definitionBuilder.addPropertyValue(property, object);
        }
        registry.registerBeanDefinition(beanName, definitionBuilder.getBeanDefinition());
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver stringValueResolver) {
        resolver = stringValueResolver;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
        registry = (BeanDefinitionRegistry) context.getParentBeanFactory();
    }
}
