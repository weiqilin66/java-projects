package org.wayne.common.init;

import cn.hutool.log.StaticLog;
import cn.hutool.setting.Setting;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Map;
import java.util.Set;

/**
 * @Description:全局配置类 ApplicationContextInitializer接口: 用于在spring容器**刷新之前**初始化Spring
 * @author: lwq
 */
public class HtConfigSetting implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    public HtConfigSetting() {
        StaticLog.info("configSetting实例化");
    }

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        initHost();
    }

    private void initHost() {
        Setting setting = new Setting("myHutool.setting");

        final Map configSetting = setting.getMap("config");
        StaticLog.info("初始化configSetting:{}", configSetting);
        final Set<Map.Entry> set = configSetting.entrySet();
        set.parallelStream().forEach(entry -> System.setProperty(entry.getKey().toString(), String.valueOf(entry.getValue())));
    }
}
