package org.wayne.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands.GeoLocation;
import org.springframework.data.redis.connection.RedisGeoCommands.GeoRadiusCommandArgs;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
public class RedisUtil {
    /**
     * zset 中如果不存在则增加
     */
    private static final String ZSET_ADDNX_SCRIPT = "return redis.call('ZADD',KEYS[1],'NX',ARGV[1],ARGV[2])";

    private final RedisScript<Boolean> zsetAddNxScript;

    public RedisUtil() {
        this.zsetAddNxScript = new DefaultRedisScript<>(ZSET_ADDNX_SCRIPT, Boolean.class);
    }

    private static RedisTemplate<String, Object> redisTemplate;

    private static void setRedisTemplate(RedisTemplate<String, Object> redisTemplate2) {
        redisTemplate = redisTemplate2;
    }
    //=============================common============================

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return
     */
    public static boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            log.error("【RedisUtil】指定缓存失效时间异常：" + e.getMessage());
            return false;
        }
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public static long getExpire(String key) {
        long expireTime = 0;
        try {
            expireTime = redisTemplate.getExpire(key, TimeUnit.SECONDS);
        } catch (Exception ex) {
            log.error("【RedisUtil】根据key 获取过期时间异常：" + ex.getMessage());
        }
        return expireTime;
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public static boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error("【RedisUtil】判断key是否存在异常：" + e.getMessage());
            return false;
        }
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public static void del(String... key) {
        try {
            if (key != null && key.length > 0) {
                if (key.length == 1) {
                    redisTemplate.delete(key[0]);
                } else {
                    redisTemplate.delete(CollectionUtils.arrayToList(key));
                }
            }
        } catch (Exception e) {
            log.error("【RedisUtil】删除缓存：" + e.getMessage());
        }
    }

    /**
     * 重命名
     *
     * @param
     */
    public static void rename(String oldKey, String newKey) {
        try {
            redisTemplate.rename(oldKey, newKey);
        } catch (Exception e) {
            log.error("【RedisUtil】重命名：" + e.getMessage());
        }
    }

    //============================String=============================

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public static Object get(String key) {
        Object value = null;
        try {
            value = key == null ? null : redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.error("【RedisUtil】普通缓存获取：" + e.getMessage());
        }
        return value;
    }

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public static String getString(String key) {
        Object objcet = null;
        String value = null;
        try {
            objcet = key == null ? null : redisTemplate.opsForValue().get(key);
            value = objcet == null ? "" : String.valueOf(objcet);
        } catch (Exception e) {
            log.error("【RedisUtil】普通缓存获取：" + e.getMessage());
        }
        return value;
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public static boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error("【RedisUtil】普通缓存获取：" + e.getMessage());
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public static boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            log.error("【RedisUtil】普通缓存放入并设置时间：" + e.getMessage());
            return false;
        }
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return
     */
    public static long incr(String key, long delta) {
        long value = 0;
        try {
            if (delta < 0) {
                throw new RuntimeException("递增因子必须大于0");
            }
            value = redisTemplate.opsForValue().increment(key, delta);
        } catch (Exception e) {
            log.error("【RedisUtil】递增：" + e.getMessage());
        }
        return value;
    }

    /**
     * 获取n位数的缓存值
     *
     * @param key
     * @param lenNum
     * @return
     * @throws Exception
     */
    public static String getSeqByKey(String key, String lenNum) throws Exception {
        return String.format("%0" + lenNum + "d", increase(key));
    }

    /**
     * 获取对应key值，并+1
     *
     * @param key
     * @return
     * @throws Exception
     */
    private static long increase(String key) throws Exception {
        boolean exit = hasKey(key);
        if (!exit) {
            set(key, "0");
            Calendar c = Calendar.getInstance();
            c.set(c.get(1), c.get(2), c.get(5) + 1, 0, 0, 0);
            Long timeout = (c.getTime().getTime() - System.currentTimeMillis()) / 1000L;
            expire(key, timeout.intValue());
        }
        return incr(key, 1L);
    }


    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return
     */
    public static long decr(String key, long delta) {
        long value = 0;
        try {
            if (delta < 0) {
                throw new RuntimeException("递减因子必须大于0");
            }
            value = redisTemplate.opsForValue().increment(key, -delta);
        } catch (Exception e) {
            log.error("【RedisUtil】递减：" + e.getMessage());
        }
        return value;
    }

    //================================Map=================================

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public static Object hget(String key, String item) {
        Object value = null;
        try {
            value = redisTemplate.opsForHash().get(key, item);
        } catch (Exception e) {
            log.error("【RedisUtil】HashGet：" + e.getMessage());
        }
        return value;
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public static Map<Object, Object> hmget(String key) {
        Map<Object, Object> map = new HashMap<>();
        try {
            map = redisTemplate.opsForHash().entries(key);
        } catch (Exception e) {
            log.error("【RedisUtil】获取hashKey对应的所有键值：" + e.getMessage());
        }
        return map;
    }

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public static boolean hmset(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            log.error("【RedisUtil】HashSet：" + e.getMessage());
            return false;
        }
    }

    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public static boolean hmset(String key, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("【RedisUtil】HashSet：" + e.getMessage());
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public static boolean hset(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            log.error("【RedisUtil】HashSet：" + e.getMessage());
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒)  注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public static boolean hset(String key, String item, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("【RedisUtil】向一张hash表中放入数据,如果不存在将创建异常：" + e.getMessage());
            return false;
        }
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public static void hdel(String key, Object... item) {
        try {
            redisTemplate.opsForHash().delete(key, item);
        } catch (Exception e) {
            log.error("【RedisUtil】删除hash表中的值异常：" + e.getMessage());
        }
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public static boolean hHasKey(String key, String item) {
        boolean result = false;
        try {
            result = redisTemplate.opsForHash().hasKey(key, item);
        } catch (Exception e) {
            log.error("【RedisUtil】删除hash表中的值异常：" + e.getMessage());
        }
        return result;
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return
     */
    public static double hincr(String key, String item, double by) {
        double result = 0;
        try {
            result = redisTemplate.opsForHash().increment(key, item, by);
        } catch (Exception e) {
            log.error("【RedisUtil】hash递增 如果不存在,就会创建一个 并把新增后的值返回异常：" + e.getMessage());
        }
        return result;
    }

    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     * @return
     */
    public static double hdecr(String key, String item, double by) {
        double result = 0;
        try {
            result = redisTemplate.opsForHash().increment(key, item, -by);
        } catch (Exception e) {
            log.error("【RedisUtil】hash递减异常：" + e.getMessage());
        }
        return result;
    }

    //============================set=============================

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return
     */
    public static Set<Object> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            log.error("【RedisUtil】根据key获取Set中的所有值：" + e.getMessage());
            return null;
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public static boolean sHasKey(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            log.error("【RedisUtil】根据value从一个set中查询,是否存在：" + e.getMessage());
            return false;
        }
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public static long sSet(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            log.error("【RedisUtil】将数据放入set缓存：" + e.getMessage());
            return 0;
        }
    }

    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public static long sSetAndTime(String key, long time, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0) expire(key, time);
            return count;
        } catch (Exception e) {
            log.error("【RedisUtil】将set数据放入缓存：" + e.getMessage());
            return 0;
        }
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return
     */
    public static long sGetSetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            log.error("【RedisUtil】获取set缓存的长度：" + e.getMessage());
            return 0;
        }
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public static long setRemove(String key, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            log.error("【RedisUtil】移除值为value的：" + e.getMessage());
            return 0;
        }
    }
    //===============================list=================================

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束  0 到 -1代表所有值
     * @return
     */
    public static List<Object> lGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            log.error("【RedisUtil】获取list缓存的内容：" + e.getMessage());
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return
     */
    public static long lGetListSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            log.error("【RedisUtil】获取list缓存的长度：" + e.getMessage());
            return 0;
        }
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public static Object lGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            log.error("【RedisUtil】通过索引 获取list中的值：" + e.getMessage());
            return null;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param value 时间(秒)
     * @return
     */
    public static boolean lSet(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            log.error("【RedisUtil】将list放入缓存：" + e.getMessage());
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public static boolean lSet(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("【RedisUtil】将list放入缓存：" + e.getMessage());
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public static boolean lSet(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            log.error("【RedisUtil】将list放入缓存：" + e.getMessage());
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public static boolean lSet(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("【RedisUtil】将list放入缓存：" + e.getMessage());
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return
     */
    public static boolean lUpdateIndex(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            log.error("【RedisUtil】根据索引修改list中的某条数据：" + e.getMessage());
            return false;
        }
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public static long lRemove(String key, long count, Object value) {
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            log.error("【RedisUtil】根据索引修改list中的某条数据：" + e.getMessage());
            return 0;
        }
    }

    /**
     * 根据某个初始值进行递增
     *
     * @param key
     * @param initialValue
     * @return
     */
    public static long generate(String key, long initialValue) {
        long value = 0;
        try {
            RedisAtomicLong current = new RedisAtomicLong(key, redisTemplate.getConnectionFactory(), initialValue);
            value = current.getAndIncrement();
        } catch (Exception e) {
            log.error("【RedisUtil】根据索引修改list中的某条数据：" + e.getMessage());
        }
        return value;
    }


    /**------------------zSet相关操作--------------------------------*/

    /**
     * 添加元素,有序集合是按照元素的score值由小到大排列
     *
     * @param key
     * @param value
     * @param score
     * @return
     */
    public static Boolean zAdd(String key, String value, double score) {
        return redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * 如果不存在则添加元素 返回true ， 存在返回false
     *
     * @param key
     * @param value
     * @param score
     * @return
     */
    public Boolean zAddNx(String key, String value, double score) {
        try {
            Boolean success = redisTemplate.execute(zsetAddNxScript, Collections.singletonList(key), score, value);
            boolean result = Boolean.TRUE.equals(success);
            return result;
        } catch (Exception e) {
            log.error("异常", e);
            log.error("【RedisUtil】 zAddNx：" + e.getMessage());
        }
        return false;
    }

    /**
     * @param key
     * @param values
     * @return
     */
    public static Long zAdd(String key, Set<TypedTuple<Object>> values) {
        return redisTemplate.opsForZSet().add(key, values);
    }

    /**
     * @param key
     * @param values
     * @return
     */
    public static Long zRemove(String key, Object... values) {
        return redisTemplate.opsForZSet().remove(key, values);
    }

    /**
     * 增加元素的score值，并返回增加后的值
     *
     * @param key
     * @param value
     * @param delta
     * @return
     */
    public static Double zIncrementScore(String key, String value, double delta) {
        return redisTemplate.opsForZSet().incrementScore(key, value, delta);
    }

    /**
     * 返回元素在集合的排名,有序集合是按照元素的score值由小到大排列
     *
     * @param key
     * @param value
     * @return 0表示第一位
     */
    public static Long zRank(String key, Object value) {
        return redisTemplate.opsForZSet().rank(key, value);
    }

    /**
     * 返回元素在集合的排名,按元素的score值由大到小排列
     *
     * @param key
     * @param value
     * @return
     */
    public static Long zReverseRank(String key, Object value) {
        return redisTemplate.opsForZSet().reverseRank(key, value);
    }

    /**
     * 获取集合的元素, 从小到大排序
     *
     * @param key
     * @param start 开始位置
     * @param end   结束位置, -1查询所有
     * @return
     */
    public static Set<Object> zRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    /**
     * 获取集合元素, 并且把score值也获取
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static Set<TypedTuple<Object>> zRangeWithScores(String key, long start,
                                                           long end) {
        return redisTemplate.opsForZSet().rangeWithScores(key, start, end);
    }

    /**
     * 根据Score值查询集合元素
     *
     * @param key
     * @param min 最小值
     * @param max 最大值
     * @return
     */
    public static Set<Object> zRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().rangeByScore(key, min, max);
    }

    /**
     * 根据Score值查询集合元素, 从小到大排序
     *
     * @param key
     * @param min 最小值
     * @param max 最大值
     * @return
     */
    public static Set<TypedTuple<Object>> zRangeByScoreWithScores(String key,
                                                                  double min, double max) {
        return redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max);
    }

    /**
     * @param key
     * @param min
     * @param max
     * @param start
     * @param end
     * @return
     */
    public static Set<TypedTuple<Object>> zRangeByScoreWithScores(String key,
                                                                  double min, double max, long start, long end) {
        return redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max,
                start, end);
    }

    /**
     * 获取集合的元素, 从大到小排序
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static Set<Object> zReverseRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().reverseRange(key, start, end);
    }

    /**
     * 获取集合的元素, 从大到小排序, 并返回score值
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static Set<TypedTuple<Object>> zReverseRangeWithScores(String key,
                                                                  long start, long end) {
        return redisTemplate.opsForZSet().reverseRangeWithScores(key, start,
                end);
    }

    /**
     * 根据Score值查询集合元素, 从大到小排序
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public static Set<Object> zReverseRangeByScore(String key, double min,
                                                   double max) {
        return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max);
    }

    /**
     * 根据Score值查询集合元素, 从大到小排序
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public static Set<TypedTuple<Object>> zReverseRangeByScoreWithScores(
            String key, double min, double max) {
        return redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key,
                min, max);
    }

    /**
     * @param key
     * @param min
     * @param max
     * @param start
     * @param end
     * @return
     */
    public static Set<Object> zReverseRangeByScore(String key, double min,
                                                   double max, long start, long end) {
        return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max,
                start, end);
    }

    /**
     * 根据score值获取集合元素数量
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public static Long zCount(String key, double min, double max) {
        return redisTemplate.opsForZSet().count(key, min, max);
    }

    /**
     * 获取集合大小
     *
     * @param key
     * @return
     */
    public static Long zSize(String key) {
        return redisTemplate.opsForZSet().size(key);
    }

    /**
     * 获取集合大小
     *
     * @param key
     * @return
     */
    public static Long zZCard(String key) {
        return redisTemplate.opsForZSet().zCard(key);
    }

    /**
     * 获取集合中value元素的score值
     *
     * @param key
     * @param value
     * @return
     */
    public static Double zScore(String key, Object value) {
        return redisTemplate.opsForZSet().score(key, value);
    }

    /**
     * 移除指定索引位置的成员
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static Long zRemoveRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().removeRange(key, start, end);
    }

    /**
     * 根据指定的score值的范围来移除成员
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public static Long zRemoveRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().removeRangeByScore(key, min, max);
    }

    /**
     * 获取key和otherKey的并集并存储在destKey中
     *
     * @param key
     * @param otherKey
     * @param destKey
     * @return
     */
    public static Long zUnionAndStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForZSet().unionAndStore(key, otherKey, destKey);
    }

    /**
     * @param key
     * @param otherKeys
     * @param destKey
     * @return
     */
    public static Long zUnionAndStore(String key, Collection<String> otherKeys,
                                      String destKey) {
        return redisTemplate.opsForZSet()
                .unionAndStore(key, otherKeys, destKey);
    }

    /**
     * 交集
     *
     * @param key
     * @param otherKey
     * @param destKey
     * @return
     */
    public static Long zIntersectAndStore(String key, String otherKey,
                                          String destKey) {
        return redisTemplate.opsForZSet().intersectAndStore(key, otherKey,
                destKey);
    }

    /**
     * 交集
     *
     * @param key
     * @param otherKeys
     * @param destKey
     * @return
     */
    public static Long zIntersectAndStore(String key, Collection<String> otherKeys,
                                          String destKey) {
        return redisTemplate.opsForZSet().intersectAndStore(key, otherKeys,
                destKey);
    }


    /**
     * @Title: geoAdd
     * @Description: TODO(添加geo)
     * @param key key
     * @param point 经纬度
     * @param member 成员
     * @return Long 返回影响的行
     */
	/*public static Long geoAdd(String key, Point point, String member) {
		return redisTemplate.opsForGeo().geoAdd(key, point, member);
	}*/

    /**
     * geoAdd	添加 geo
     *
     * @param key
     * @param point
     * @param member
     * @return
     */
    public static Long geoAdd(String key, Point point, Object member) {
        return redisTemplate.opsForGeo().add(key, point, member);
    }

    /**
     * @Title: geoRemove
     * @Description: TODO(删除成员)
     * @param key
     * @param members 成员
     * @return Long 返回影响的行
     */
	/*public static Long geoRemove(String key, String... members) {
		return redisTemplate.opsForGeo().geoRemove(key, members);
	}*/

    /**
     * geoRemove 删除成员
     *
     * @param key
     * @param members
     * @return
     */
    public static Long geoRemove(String key, Object... members) {
        return redisTemplate.opsForGeo().remove(key, members);
    }

    /**
     * @Title: geoPos
     * @Description: TODO(查询地址的经纬度)
     * @param key key
     * @param members 成员
     * @return List<Point>
     */
	/*public static List<Point> geoPos(String key, String... members) {
		return redisTemplate.opsForGeo().geoPos(key, members);
	}*/

    /**
     * 查询地址经纬度
     *
     * @param key
     * @param members
     * @return
     */
    public static List<Point> geoPos(String key, Object... members) {
        return redisTemplate.opsForGeo().position(key, members);
    }

    /**
     * zrange key start end withscores
     *
     * @param key
     * @return
     */
    public static Set<TypedTuple<Object>> geoPosAll(String key) {
        return redisTemplate.opsForZSet().rangeWithScores(key, 0, -1);
    }

    /**
     * zrange key start end
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static Set<Object> geoRange(String key, Long start, Long end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    /**
     * geoRadius key longitude latitude radius m|km|ft|mi [WITHCOORD] [WITHDIST] [WITHHASH] [COUNT count] [ASC|DESC] [STORE key] [STOREDIST key]
     *
     * @param key
     * @param circle
     * @return
     */
    public static GeoResults<GeoLocation<Object>> geoRadius(String key, Circle circle, GeoRadiusCommandArgs args) {
        return redisTemplate.opsForGeo().radius(key, circle, args);
    }

    /**
     * @param key
     * @param member
     * @param distance
     * @return
     */
    public static GeoResults<GeoLocation<Object>> geoRadius(String key, Object member, Distance distance, GeoRadiusCommandArgs args) {
        return redisTemplate.opsForGeo().radius(key, member, distance, args);
    }

    /**
     * @param key
     * @param members
     * @return List<String>
     * @Title: geoHash
     * @Description: TODO(查询位置的geohash)
     */
    public static List<String> geoHash(String key, String... members) {
        return redisTemplate.opsForGeo().geoHash(key, members);
    }

    /**
     * @Title: geoDist
     * @Description: TODO(查询2位置距离)
     * @param key
     *            key
     * @param member1
     *            成员1
     * @param member2
     *            成员2
     * @param metric
     *            单位
     * @return Double 距离
     */
	/*public static Double geoDist(String key, String member1, String member2, Metric metric) {
		return redisTemplate.opsForGeo().geoDist(key, member1, member2, metric).getValue();
	}*/

    /**
     * geodist key member1 member2 [unit]
     *
     * @param key
     * @param member1
     * @param member2
     * @param metric
     * @return
     */
    public static Distance geoDist(String key, Object member1, Object member2, Metric metric) {
        return redisTemplate.opsForGeo().distance(key, member1, member2, metric);
    }

    /**
     * 加锁
     * @param key
     * @param value 当前时间+超时时间
     * @return
     */
    public static boolean lock(String key, String value,long time) {

        if(redisTemplate.opsForValue().setIfAbsent(key, value,time,TimeUnit.SECONDS)) {//相当于SETNX，setIfAbsent方法设置了为true,没有设置为false
            return true;
        }
        /*//假设currentValue=A   接下来并发进来的两个线程的value都是B  其中一个线程拿到锁,除非从始至终所有都是在并发（实际上这中情况是不存在的），只要开始时有数据有先后顺序，则分布式锁就不会出现“多卖”的现象
        String currentValue = String.valueOf(redisTemplate.opsForValue().get(key));
        //如果锁过期  解决死锁
        if (!StringUtils.isEmpty(currentValue)
                && Long.parseLong(currentValue) < System.currentTimeMillis()) {
            //获取上一个锁的时间，锁过期后，GETSET将原来的锁替换成新锁
            String oldValue = String.valueOf(redisTemplate.opsForValue().getAndSet(key, value));
            if (!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)) {
                return true;
            }
        }*/

        return false;//拿到锁的就有执行权力，拿不到的只有重新再来，重新再来只得是让用户手动继续抢单
    }

    /**
     * 解锁
     * @param key
     */
    public static void unlock(String key) {
        try {
            //String currentValue = String.valueOf(redisTemplate.opsForValue().get(key));
            //if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value)) {
            redisTemplate.opsForValue().getOperations().delete(key);
            //}
        }catch (Exception e) {
            log.error("删除key异常",e);
        }
    }


}
