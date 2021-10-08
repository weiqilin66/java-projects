package org.wayne;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.wayne.common.init.HtConfigSetting;

/**
 * @Description: 二手卡
 * @author: lwq
 */
@SpringBootApplication
public class CardServer {
    public static void main(String[] args) {
        new HtConfigSetting();
        SpringApplication.run(CardServer.class, args);

    }
}
