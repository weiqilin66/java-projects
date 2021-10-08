package org.wayne.base;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.wayne.common.utils.RedisUtil;

/**
 * @Description:
 * @author: lwq
 */
@SpringBootTest(classes = {BaseApp.class})
@RunWith(SpringRunner.class)
public class SelTest {

    @Test
   public void t1(){
        RedisUtil.set("s","y");
        System.out.println(RedisUtil.get("s"));
    }

}
