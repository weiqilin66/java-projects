package org.wayne.consumer.resilience4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @author: LinWeiQi
 */
@RestController("resilience4jController")
public class HelloController {
    @Autowired
    MyService myService;

    @GetMapping("/retry1")
    public void retry1(){
        myService.retry1();
    }

    @GetMapping("/limit1")
    public void limit(){
        myService.reateLimit();
        myService.reateLimit();
        myService.reateLimit();
        myService.reateLimit();
    }
}
