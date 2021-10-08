package org.wayne.configclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @author: LinWeiQi
 */
@RestController
public class HelloController {

//    @Value("${wayne.paths}")
    String waynePaths;

    @Value("${password}")
    String password;

    @GetMapping("/hello")
    public String hello(){

        return waynePaths;
    }
    @GetMapping("/hello2")
    public String hello2(){

        return password;
    }
}
