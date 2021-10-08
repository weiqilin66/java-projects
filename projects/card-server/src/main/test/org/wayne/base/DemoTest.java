package org.wayne.base;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.wayne.card.CardServer;
import org.wayne.card.repository.mapper.GoodsMapper;
import org.wayne.common.init.HtConfigSetting;
import org.wayne.common.utils.AppUtilQ;
import org.wayne.common.utils.RedisUtil;

/**
 * @Description: springbootTest设置的Systerm.setProperty无效 使用@ContextConfiguration
 * @author: lwq
 */
@ContextConfiguration(initializers = HtConfigSetting.class)
@SpringBootTest(classes = {CardServer.class})
@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
public class DemoTest {

    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    AppUtilQ appUtilQ;
    @Before
    public void t2(){

    }
    @Test
   public void t1(){
        final String remoteValue = appUtilQ.getRemoteValue("wayne.host");
        final long l = goodsMapper.selectAll();
        System.out.println(l);
    }

}
