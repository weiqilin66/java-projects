package org.wayne.redis.main;

import java.util.List;

/**
 * @Description:
 * @author: lwq
 */
public class RedisService {
    /**
     *     java redis list只能存储list<String> , 存储List<T> 将T 转换成String
     */
    /*
    List<HashMap<String, String>> sqls;
    ObjectMapper mapper = new ObjectMapper();
    final String gkConfig = JedisUtil.getString("gk_config");
		if (gkConfig!=null) {
        sqls = mapper.readValue(gkConfig, List.class);  //关键代码
    }else {
        sqls = dao.getSqlConfig();
        final String res = mapper.writeValueAsString(sqls); //关键代码
        JedisUtil.getJc().set("gk_config",res);
    }

    */
}
