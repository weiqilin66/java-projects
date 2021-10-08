package org.wayne.thief.service.impl;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wayne.thief.service.IDownloadPhotoService;
import org.wayne.common.utils.WhereAmIUtilQ;

import java.util.List;

/**
 * @Description:
 * @author: lwq
 */
@Service
public class DownloadPhotoServiceImpl implements IDownloadPhotoService {
    public static final Logger log = LoggerFactory.getLogger(DownloadPhotoServiceImpl.class);

    @Autowired
    ChromeOptions baseOptions;

    @Override
    public void crawl() throws InterruptedException {
        final ChromeDriver driver = new ChromeDriver(baseOptions);
        log.info("当前IP地址:"+ WhereAmIUtilQ.getIp4());
        final String url = "http://36.110.101.89:9910/yusp-app-admin-web/index.html?st=qinghai";
        driver.get(url);
        Thread.sleep(2000);
        driver.findElementById("username").sendKeys("xiamen");
        driver.findElementById("password").sendKeys("xiamen");
        while (true){
            final WebElement imageCode = driver.findElementById("imageCode");
            final String text = imageCode.getAttribute("value");
            if (text==null || text.length()<4) {
                log.info("手动输入验证码");
                Thread.sleep(5000);
            }else {
                break;
            }
        }
        driver.findElementById("submitBtn").click();
        Thread.sleep(2000);
        while (true){
            try {
                driver.findElementByXPath("//tr[@class='el-table__row']");
                break;
            } catch (Exception e) {
                log.warn("手动选择借据及还款计划表");
                Thread.sleep(10000);
            }
        }
        final List<WebElement> rows = driver.findElementsByXPath("//tr[@class='el-table__row']");
        // 记录数
        final int size = rows.size();
        for (int i = 1; i <= size; i++) {
            // 列class //td[@class='el-table_1_column_1']/div//div[1]
            final String colXpath = "//td[@class='el-table_1_column_" + i+"']/div//div[1]";
            final List<WebElement> clos = driver.findElementsByXPath(colXpath);
            for (WebElement clo : clos) {
                final String value = clo.getText();
                log.info(value);
            }


        }



        Thread.sleep(10000);
        driver.close();
    }
}
