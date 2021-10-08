package org.wayne.consumer.hystrix.batchCmd;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.wayne.demo.User;

import java.util.List;

/**
 * @Description:  合并请求-请求命令写法
 * @author: LinWeiQi
 */
public class BatchCommand extends HystrixCommand<List<User>> {

    private List<Integer> ids;
    private UserService userService;


    protected BatchCommand(List<Integer> ids,UserService userService) {
        super(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("batchCmd")));
        this.ids = ids;
        this.userService =userService;
    }

    //功能方法主体
    @Override
    protected List<User> run() throws Exception {
        return userService.getUserByIds(ids);
    }
}
