package org.wayne.consumer.openfeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wayne.demo.User;

import javax.annotation.Resource;

/**
 * @Description: 调用测试
 * @author: LinWeiQi
 */
@RestController
public class HelloController {

    @Autowired
    FeignService feignService;

    @GetMapping("/feignHello")
    public String hello(){
        return feignService.hello();
    }
    @GetMapping("/feignHello2")
    public String hello2(){
        return feignService.hello2("wayne");
    }

    @GetMapping("/feignHello3")
    public User user1(){
        return feignService.user1("wayne","123");
    }
    @GetMapping("/feignHello4")
    public User user2(){
        User user = new User();
        user.setId(111);
        return feignService.user2(user);

    }
    @GetMapping("/feignHello5")
    public String user3(){
        return feignService.del(3);

    }
    @GetMapping("/to")
    public String timeout(int ms){
        final String s = feignService.testTOut(ms);
        System.out.println(s);
        return s;
    }
}
