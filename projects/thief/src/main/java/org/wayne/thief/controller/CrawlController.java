package org.wayne.thief.controller;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wayne.thief.service.impl.CardServiceImpl;
import org.wayne.thief.util.AppUtil;

/**
 * @Description:
 * @author: lwq
 */
@RestController
@RequestMapping("/thief")
public class CrawlController {

    @Autowired
    CardServiceImpl cardService;

    WebDriver driver = AppUtil.getTbChromeDriver();

    @RequestMapping("/")
    public void start() throws InterruptedException {
        cardService.crawl();
    }
    @RequestMapping("/reCrawl")
    public void reCrawl() throws InterruptedException {
        cardService.doCrawl(driver);;
    }
}
