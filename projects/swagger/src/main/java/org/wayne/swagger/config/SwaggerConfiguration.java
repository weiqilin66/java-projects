package org.wayne.swagger.config;


import org.springframework.boot.SpringBootVersion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @Description:
 * @author: lwq
 */
@Configuration
public class SwaggerConfiguration implements WebMvcConfigurer {

    @Bean
    Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                // 定义是否开启swagger，false为关闭，可以通过变量控制
                .enable(true)
                .pathMapping("/")

                // 将api的元信息设置为包含在json ResourceListing响应中。
                .apiInfo(apiInfo());
    }

    /**
     * API 页面上半部分展示信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("帅奇的 Api Doc")
                .description("在我入职保安的那天，队长问我：你知道你要保护谁嘛？\n" +
                        "\n" +
                        "我嘴上说的是业主，心里却是你。")
//                .contact(new Contact("lighter", null, "123456@gmail.com"))
                .version("Application Version: 1.0.0")
                .build();
    }



}
