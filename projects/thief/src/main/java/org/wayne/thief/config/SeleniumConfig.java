package org.wayne.thief.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * @Description:
 * @author: lwq
 */
@Configuration
@SuppressWarnings("all")
public class SeleniumConfig {

    String driverPath = "D:\\chromedriver_new.exe";

    @Bean
    ChromeOptions tbOptions() {
        final ChromeOptions options = baseConfig();
        // 不加载图片
        options.addArguments("blink-settings=imagesEnabled=false");
        // 配置参数禁止显示 Chrome正在受到自动软件的控制
        options.addArguments("--disable-infobars");
        // 浏览器不提供可视化页面
//        options.addArguments("--headless");
        // 屏蔽webdriver特征
        options.addArguments("--disable-blink-features");
        options.addArguments("--disable-blink-features=AutomationControlled");
        return options;
    }

    @Bean
    ChromeOptions baseOptions(){
        return baseConfig();
    }

    ChromeOptions baseConfig() {
        System.setProperty("webdriver.chrome.driver", driverPath);
        ChromeOptions options = new ChromeOptions();
        // 设置为开发者模式，防止被各大网站识别出来使用了Selenium  window.navigator.webdriver检测
        // 高版本无法使用
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        return options;
    }


}
