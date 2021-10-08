package org.wayne.common.init;

import cn.hutool.log.StaticLog;
import cn.hutool.setting.Setting;

import java.util.Map;
import java.util.Set;

/**
 * @Description:
 * @author: lwq
 */
public class HtConfigSetting {
    Setting setting = null;

    public HtConfigSetting() {
        try {
            setting = new Setting("myHutool.setting");
        } catch (Exception e) {
            StaticLog.info("初始化configSetting异常",e);
            return;
        }
        initHost(setting);
    }

    private void initHost(Setting setting) {

        final Map configSetting = setting.getMap("config");
        StaticLog.info("初始化configSetting:{}",configSetting);
        final Set<Map.Entry> set = configSetting.entrySet();
        set.parallelStream().forEach(entry->System.setProperty(entry.getKey().toString(),String.valueOf(entry.getValue())));
    }

    public static void main(String[] args) {
        new HtConfigSetting();
    }
}
