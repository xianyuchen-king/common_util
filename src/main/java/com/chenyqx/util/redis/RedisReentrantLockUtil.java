package com.chenyqx.util.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName : RedisReentrantLockUtil
 * @Description :
 * @Author : chenyqx
 * @Date: 2021-05-10 13:57
 */
@Component
public class RedisReentrantLockUtil {

    @Autowired
    JedisConfig jedisConfig;

    private static JedisPool JedisPool = JedisConfig.getRedisPool();

    /**
     * 释放锁脚本
     */
    private static final String UNLOCK_LUA;
    /**分布式线程的唯一标识，使用本机IP+UUID形式，释放锁后会删除，防止线程池方式的重入锁*/
    private static ThreadLocal<Map<String,Integer>> reentrantCount = new ThreadLocal<>();

    /*
     * 释放锁脚本，原子操作
     */
    static {
        StringBuilder sb = new StringBuilder();
        sb.append("if redis.call(\"get\",KEYS[1]) == ARGV[1] ");
        sb.append("then ");
        sb.append("    return redis.call(\"del\",KEYS[1]) ");
        sb.append("else ");
        sb.append("    return 0 ");
        sb.append("end ");
        UNLOCK_LUA = sb.toString();
        reentrantCount.set(new HashMap<>());
    }



    /**Redis成功响应码*/
    private static final String OK = "OK";

    private static final int TTL = 60*10;

    public boolean tryGetLock(String key, String val){
        return tryGetLock(key, val, TTL);
    }

    public static boolean tryGetLock(String key, String requestId, int expire){
        boolean result = false;
        Map<String,Integer> map = reentrantCount.get();
        if(map.containsKey(key)){
            int count = map.get(key);
            map.put(key,++count);
            reentrantCount.set(map);
            result = true;
        }else {
            Jedis jedis = JedisPool.getResource();
            SetParams setParams = new SetParams();
            setParams.nx();
            setParams.ex(expire);
            String res = jedis.set(key, requestId, setParams);
            if(OK.equals(res)){
                result = true;
                map.put(key,1);
            }
        }
        return result;
    }

    public static boolean releaseLock(String key, String requestId) {
        int count = reentrantCount.get().get(key);
        if(count > 1){
            count--;
            return true;
        }else {
            Jedis jedis = JedisPool.getResource();
            Object res = jedis.eval(UNLOCK_LUA, 1, key, requestId);
            if("1".equals(res.toString())){
                return true;
            }else {
                return false;
            }
        }
    }

}
