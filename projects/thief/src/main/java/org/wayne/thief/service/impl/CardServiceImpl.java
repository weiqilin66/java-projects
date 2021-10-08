package org.wayne.thief.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wayne.thief.service.ICardService;
import org.wayne.common.utils.DateUtilQ;
import org.wayne.common.utils.ReUtilQ;
import org.wayne.api.entity.Auctions;
import org.wayne.api.entity.Card;
import org.wayne.api.entity.JsonRoot;
import org.wayne.thief.mapper.GoodsMapper;
import org.wayne.thief.mapper.CardMapper;
import org.wayne.thief.util.AppUtil;
import org.wayne.common.utils.FileUtilQ;

import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * @Description: 爬取淘宝信息
 * @author: lwq
 */
@Service
public class CardServiceImpl implements ICardService {

    public static final Logger log = LoggerFactory.getLogger(CardServiceImpl.class);
    final int millis15 = 10000 + new Random().nextInt(5) * 10000;
    @Autowired
    CardMapper mapper;
    @Autowired
    GoodsMapper goodsMapper;

    Card card;

    @Override
    public void crawl() throws InterruptedException {
        ChromeDriver driver = AppUtil.getTbChromeDriver();
        String url = "https://s.taobao.com/";
        driver.get(url);
        // 窗口最大化
        driver.manage().window().maximize();
        final WebElement searchInput = driver.findElementByXPath("//input[@name='q']");
        searchInput.sendKeys("see you");
        final WebElement searchBtn = driver.findElementByXPath("//button");
        Thread.sleep(1000);
        searchBtn.click();
        doCrawl(driver);
    }

    public void doCrawl(WebDriver driver) throws InterruptedException {
        // 是否登录
        for (; ; ) {
            if (driver.getCurrentUrl().startsWith("https://login")) {
                log.info("未登录,休眠15s");
                Thread.sleep(15000);
            } else {
                break;
            }
        }
        log.info("------ 登录成功 ------");
        // 查询数据
        List<Card> list = mapper.selectList(new QueryWrapper<>());
        for (Card card : list) {
            this.card = card;
            fetchData(card);
        }
    }

    /**
     * 检索数据,获取网页source
     */
    private void fetchData(Card card) throws InterruptedException {
        ChromeDriver driver = AppUtil.getTbChromeDriver();
        final WebElement searchInput = driver.findElementByXPath("//input[@name='q']");
        final WebElement searchBtn = driver.findElementByXPath("//button");
        searchInput.clear();
        searchInput.sendKeys(card.getCardName());
        searchBtn.click();
        Thread.sleep(millis15);
        // 下一页按钮
        final List<WebElement> nextPageBtns = driver.findElementsByXPath("//div[@class='pager']//ul[@class='items']//li/a[@class='link']/span[@class='icon icon-btn-next-2-disable']");
        String gPageConfig = crawlBySellSort();
        json2info(gPageConfig);
        if (nextPageBtns == null || nextPageBtns.isEmpty()) {
            log.info("只有一页");
            return;
        }
        for (int i = 0; i < nextPageBtns.size(); i++) {
            nextPageBtns.get(0).click();
            gPageConfig = crawlBySellSort();
            json2info(gPageConfig);
        }
    }

    /**
     * 按销量爬取
     */
    String crawlBySellSort() throws InterruptedException {
        ChromeDriver driver = AppUtil.getTbChromeDriver();
        final List<WebElement> sortBtns = driver.findElementsByXPath("//li/a[@data-key='sort']");
        // 销量排序
        final WebElement sellSortBtn = sortBtns.get(1);
        sellSortBtn.click();
        Thread.sleep(millis15);
        // 下拉底部 加载元素
        driver.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        Thread.sleep(2000);
        // 取js
        final String pageSource = driver.getPageSource();
        return ReUtilQ.findAll(pageSource, "g_page_config = (.*?)}};");
    }

    /**
     * 数据
     */
    private void json2info(String json) {
        // 清洗
        final String all = json.replaceAll("g_page_config = ", "").replaceAll("}};", "");
        final String data = ReUtilQ.findAll(all, "\"data\":.*?\"bottomsearch\":");
        final String resData = data.replace("\"data\":", "").replace("},\"bottomsearch\":", "");
        JsonRoot jsonRoot;
        try {
            final JSONObject jsonObject = JSONObject.parseObject(resData);
            jsonRoot = JSONObject.toJavaObject(jsonObject, JsonRoot.class);
        } catch (Exception e) {
            log.error("json数据转实体失败", e);
            try {

                FileUtilQ.fileWriterMethod("D://" + DateUtilQ.getCurrentDate() + "//json.text", json);
            } catch (IOException ioException) {
                log.error("json数据转存文件失败", e);
            }
            return;
        }
        //存储
        final List<Auctions> auctionsList = jsonRoot.getAuctions();
        goodsMapper.batchInsert(auctionsList, card, DateUtilQ.getCurrentDate(), DateUtilQ.getCurrentTime());
    }


}
