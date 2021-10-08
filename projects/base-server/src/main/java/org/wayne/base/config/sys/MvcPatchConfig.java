package org.wayne.base.config.sys;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description: 添加url前缀
 * @author: lwq
 */
@Configuration
public class MvcPatchConfig implements WebMvcConfigurer {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix("/api",c -> c.isAnnotationPresent(RestController.class));
    }

}
