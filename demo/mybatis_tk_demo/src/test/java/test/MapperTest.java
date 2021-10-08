package test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.wayne.DemoApp;
import org.wayne.mapper.DemoMapper;

/**
 * @Description:
 * @author: lwq
 */
@SpringBootTest(classes = {DemoApp.class})
@RunWith(SpringRunner.class)
public class MapperTest {
    @Autowired
    DemoMapper demoMapper;

    @Test
    public void t1(){
//        System.out.println(demoMapper.getClass());
        System.out.println(demoMapper.selectAll().get(0).getAssetSide());
    }
}
