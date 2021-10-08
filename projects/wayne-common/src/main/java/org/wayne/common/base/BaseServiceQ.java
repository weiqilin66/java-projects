package org.wayne.common.base;

import lombok.extern.slf4j.Slf4j;
import org.wayne.api.entity.BaseReq;
import org.wayne.api.entity.BaseResp;

/**
 * 不使用Aspect的方式切面
 *
 * @Description: 基础service
 * T 请求的泛型 K 响应的泛型
 * @author: lwq
 */
@Slf4j
public abstract class BaseServiceQ<T extends BaseReq, K extends BaseResp> {

//    @Autowired
//    JdbcTemplate jdbcTemplate;
//
//    public BaseServiceQ() {
//        // 子类实例化会默认调用父类无参构造,可封装实现
//    }
//
//    /**
//     * 实现业务逻辑
//     */
//    protected abstract K doExecute(T var1) throws Exception;
//
//    /**
//     * 请求入口
//     */
//    @PostMapping(value = {"/execute"}, produces = {"application/json;charset=UTF-8"})
//    public K execute(@Parameter(name = "req", description = "JSON字符串的请求报文", required = true) @RequestBody T req) {
//
//        K resp = null;
//        //幂等
//        try {
//            this.checkIdempotent(req);
//        } catch (Exception e) {
//            // 同一封装返回bean
//            resp = this.exceptionHandler(e);
//            return resp;
//        }
//        //目标逻辑
//        try {
//            resp = doExecute(req);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return resp;
//    }
//
//    /**
//     * 实现IidempotentService接口开启幂等校验
//     * fase 夹不上锁
//     */
//    private boolean checkIdempotent(T req) throws Exception {
//        boolean need = ClassUtils.getAllInterfacesForClassAsSet(req.getClass()).contains(Iidempotent.class);
//
//        if (!need) {
//            return true;
//        }
//        Iidempotent ide = (Iidempotent) req;
//        final String[] keys = ide.redisIdempotentKey();
//        final int expireTime = ide.expireTime();
//        final String uuid = UUID.randomUUID() + "";
//        if (keys != null && keys.length != 0) {
//            for (String key : keys) {
//                // 加盐
//                final String serviceId = req.getServiceId();
//                key = serviceId.concat("_").concat(key);
//                try {
//                    log.info("redis幂等锁key:{}", key);
//                    if (!RedisUtil.lock(key, uuid, expireTime)) {
//                        return false;
//                    }
//                } catch (Exception e) {
//                    log.error("redis加锁异常", e);
//                    throw new LockException("加锁异常", e);
//                }
//            }
//        }
//        log.info("redis幂等校验通过");
//        final Map<String, Map<String, Object>> tablesMap = ide.dbIdempotentKey();
//        if (tablesMap != null && tablesMap.size() != 0) {
//            for (Map.Entry<String, Map<String, Object>> entry : tablesMap.entrySet()) {
//                String tableName = entry.getKey();
//                Map<String, Object> clos = entry.getValue();
//                StringBuilder sb = new StringBuilder("SELECT 1 FROM ");
//                sb.append(tableName).append(" WHERE 1=1 ");
//                for (Map.Entry<String, Object> cloEntry : clos.entrySet()) {
//                    sb.append(cloEntry.getKey()).append("=");
//                    final Object value = cloEntry.getValue();
//                    if (value instanceof String) {
//                        sb.append("'").append(value).append("'");
//                    } else {
//                        sb.append(value);
//                    }
//                }
//                final String sql = sb.toString();
//                log.info("数据库幂等查询sql:{}", sql);
//                try {
//                    if (!jdbcTemplate.queryForList(sql).isEmpty()) {
//                        return false;
//                    }
//                } catch (Exception e) {
//                    throw new LockException("数据库校验异常", e);
//                }
//            }
//            log.info("数据库幂等校验通过");
//        }
//        return true;
//    }
//
//    private K exceptionHandler(Throwable e) {
//        return null;
//    }


}
