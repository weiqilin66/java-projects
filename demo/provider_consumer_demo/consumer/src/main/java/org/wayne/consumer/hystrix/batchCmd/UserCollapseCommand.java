package org.wayne.consumer.hystrix.batchCmd;


import com.netflix.hystrix.*;
import org.wayne.demo.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Description
 * @return 请求合并核心方法类
 * @param
 * @date   2020/7/7
 */
public class UserCollapseCommand extends HystrixCollapser<List<User>, User, Integer> {
    private UserService userService;
    private Integer id;

    public UserCollapseCommand(UserService userService, Integer id) {
        super(HystrixCollapser.Setter.withCollapserKey(HystrixCollapserKey.Factory.asKey("UserCollapseCommand"))
                .andCollapserPropertiesDefaults(HystrixCollapserProperties.Setter().withTimerDelayInMilliseconds(200)));
        //withTimerDelayInMilliseconds设置批处理的间隔时间200ms 超过200ms的不进这一批批量请求
        this.userService = userService;
        this.id = id;
    }

    /**
     * 请求参数
     *
     * @return
     */
    @Override
    public Integer getRequestArgument() {
        return id;
    }

    /**
     * 请求合并的方法
     * 合并请求参数,调用合并请求方法
     *
     * @param collection
     * @return
     */
    @Override
    protected HystrixCommand<List<User>> createCommand(Collection<CollapsedRequest<User, Integer>> collection) {
        List<Integer> ids = new ArrayList<>(collection.size());
        for (CollapsedRequest<User, Integer> userIntegerCollapsedRequest : collection) {
            ids.add(userIntegerCollapsedRequest.getArgument());//合并参数成集合
        }
        return new BatchCommand(ids, userService);//发起请求
    }

    /**
     * 请求结果分发
     *
     * @param users
     * @param collection
     */
    @Override
    protected void mapResponseToRequests(List<User> users, Collection<CollapsedRequest<User, Integer>> collection) {
        int count = 0;
        for (CollapsedRequest<User, Integer> request : collection) {
            request.setResponse(users.get(count++));//配置resp结果
        }
    }

    public static void main(String[] args) {
        int count = 0;
        int x=3;
        for (int i = 0; i < x; i++) {
            System.out.println(count++);// 0 1 2 本行代码运行完才++
        }
    }
}