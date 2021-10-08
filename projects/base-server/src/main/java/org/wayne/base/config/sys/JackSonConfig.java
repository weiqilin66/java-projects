package org.wayne.base.config.sys;

import org.springframework.context.annotation.Configuration;

/**
 * @Description: 修改配置springboot自动配置的jackSon !!使用此配置后所有的JSON转换都会进过这里,如果date无法被转换例如null会报错
 * @JsonIgnore 转出json或者接受json时候忽略这个字段     @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")来自定义更灵活
 * 也可以在前端控制返回的日期格式   value-format="yyyy-MM-dd"
 * @author: LinWeiQi
 */
@Configuration
public class JackSonConfig {
    /**
     * @Description 修改jackson 配置日期格式,可以注入objectMapper实现
     * @date 2020/1/16
     */
//    @Bean
//    MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
//
//        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        objectMapper.setDateFormat(new SimpleDateFormat("yyyy/MM/dd"));
//        converter.setObjectMapper(objectMapper);
//
//        return converter;
//    }


}
