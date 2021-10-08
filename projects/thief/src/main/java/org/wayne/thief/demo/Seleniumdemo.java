package org.wayne.thief.demo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * @Description:
 * @author: lwq
 */
public class Seleniumdemo {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "D:\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("http://www.baidu.com");

        String title = driver.getTitle();
        System.out.printf(title);

        driver.close();
    }
}

