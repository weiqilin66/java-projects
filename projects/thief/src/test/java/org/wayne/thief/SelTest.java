package org.wayne.thief;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.wayne.thief.service.ICardService;
import org.wayne.common.utils.DateUtilQ;
import org.wayne.api.entity.Auctions;
import org.wayne.api.entity.Card;
import org.wayne.thief.mapper.GoodsMapper;

import java.util.ArrayList;

/**
 * @Description:
 * @author: lwq
 */
@SpringBootTest(classes = {ThiefApp.class})
@RunWith(SpringRunner.class)
public class SelTest {

    @Autowired
    ICardService cardService;

    @Test
    public void t1() {
        System.out.println(1);
        try {
            cardService.crawl();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Autowired
    GoodsMapper goodsMapper;

    @Test
    public void t2(){
        final ArrayList<Auctions> list = new ArrayList<>();
        final Auctions auctions = new Auctions();
        auctions.setP4p(0);
        auctions.setP4pSameHeight(false);
        auctions.setNid("xx");
        auctions.setCategory("xx");
        auctions.setPid("1");
        auctions.setTitle("1");
        auctions.setRaw_title("荒野");
        auctions.setPic_url("http");
        auctions.setDetail_url("http");
        auctions.setView_price("88888");
        auctions.setView_fee("898989");
        auctions.setItem_loc("1");
        auctions.setView_sales("76666");
        auctions.setComment_count("1");
        auctions.setUser_id("1");
        auctions.setNick("pk");
        auctions.setHideIM(false);
        auctions.setHideNick(false);
        auctions.setComment_url("1");
        auctions.setShopLink("1");
        list.add(auctions);
        final Card card = new Card();
        card.setCardId(66);
        card.setPlatform("fd");
        card.setCardName("fd");

        final String currentDate = DateUtilQ.getCurrentDate();
        final String currentTime = DateUtilQ.getCurrentTime();
        System.out.println(currentDate);
        System.out.println(currentTime);
        goodsMapper.batchInsert(list, card, currentDate, currentTime);
    }

}
