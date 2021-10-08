package org.wayne.common.init;

import cn.hutool.log.StaticLog;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.wayne.common.init.condition.YxEnvCondition;

/**
 * @Description:
 * @author: lwq
 */
@Configuration
@EnableConfigurationProperties(WayneProperties.class)
@Conditional(YxEnvCondition.class)
public class WayneConfig {

    public WayneConfig() {
        StaticLog.info("wayne动态配置初始化...");
    }
}
