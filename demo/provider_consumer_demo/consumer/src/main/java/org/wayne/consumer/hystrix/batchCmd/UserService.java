package org.wayne.consumer.hystrix.batchCmd;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.wayne.demo.User;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @Description: 请求合并-查询条件是同一个字段
 * @author: LinWeiQi
 */
@Service
public class UserService {

    @Resource//byName注入 这里name=restTemplate
    RestTemplate restTemplate;

    /**
     User[]不写List是精髓,List的话返回的List里面存的是Map泛型不方便转成List<User>
     StringUtils.join(p1,p2) 根据p2分割p1返回数组
     数组转成集合 Arrays.asList()
     */
    public List<User> getUserByIds(List<Integer> ids){
        User[] users = restTemplate.getForObject("http://provide/user/{1}", User[].class, StringUtils.join(ids, ","));
        System.out.println("user: "+users);
        return Arrays.asList(users);
    }


}
