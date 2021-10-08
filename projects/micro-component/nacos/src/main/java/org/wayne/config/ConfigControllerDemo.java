package org.wayne.config;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description:
 * @author: lwq
 */
@Controller
@RequestMapping("config")
public class ConfigControllerDemo {

    // 通过 Nacos 的 @NacosValue 注解设置属性值
    @NacosValue(value = "${useLocalCache:false}", autoRefreshed = true)
    private boolean useLocalCache;

    @GetMapping(value = "/get")
    @ResponseBody
    public boolean get() {
        return useLocalCache;
    }
}
