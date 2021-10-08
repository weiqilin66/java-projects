package org.wayne.provide.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wayne.demo.User;

/**
 * @Description:
 * @author: LinWeiQi
 */
@Controller
public class RegisterController {
    //重定向接口
    @PostMapping("/register")
    public String register(User user){
        //写死http://provide/ 否则自动前缀自动拼接http://本机标识 consumer那边就无法从注册中心获取provide信息了
        return "redirect:http://provide/loginPage?username=" + user.getUsername();
    }

    @RequestMapping("/loginPage")
    @ResponseBody
    public String loginPage(String username){
        System.out.println("进入loginPage");
        return "login:"+username;
    }
}
