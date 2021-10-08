package org.wayne.common.init;

import io.swagger.annotations.Api;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Description: 搭配 @EnableConfigurationProperties(WayneProperties.class) 动态开启属性注入
 * @author: lwq
 */
@Api(tags = {"动态配置注入WayneProperties"})
@Data
@ConfigurationProperties(prefix = "wayne")
public class WayneProperties {
    private String host;
}
