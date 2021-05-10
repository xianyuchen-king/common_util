package com.chenyqx.util.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @ClassName : JedisConf
 * @Description :
 * @Author : chenyqx
 * @Date: 2021-05-10 13:39
 */
@Configuration
@PropertySource("classpath:redis.properties")
public class JedisConfig {
    protected static final Logger logger = LoggerFactory.getLogger(JedisConfig.class);

    private static String host;

    private static Integer port;

    private static String password;

    private static Integer database;

    private static Integer maxActive;

    private static Integer maxIdle;

    private static Long maxWait;

    private static Integer minIdle;

    private static int maxTotal;

    @Value("${redis.host}")
    public void setHost(String host) {
        JedisConfig.host = host;
    }
    @Value("${redis.port}")
    public void setPort(Integer port) {
        JedisConfig.port = port;
    }
    @Value("${redis.password}")
    public void setPassword(String password) {
        JedisConfig.password = password;
    }
    @Value("${redis.database.index}")
    public void setDatabase(Integer database) {
        JedisConfig.database = database;
    }
    @Value("${redis.maxActive}")
    public void setMaxActive(Integer maxActive) {
        JedisConfig.maxActive = maxActive;
    }
    @Value("${redis.maxIdle}")
    public void setMaxIdle(Integer maxIdle) {
        JedisConfig.maxIdle = maxIdle;
    }
    @Value("${redis.maxWaitMillis}")
    public void setMaxWait(Long maxWait) {
        JedisConfig.maxWait = maxWait;
    }
    @Value("${redis.minIdle}")
    public void setMinIdle(Integer minIdle) {
        JedisConfig.minIdle = minIdle;
    }
    @Value("${redis.maxTotal}")
    public void setMaxTotal(int maxTotal) {
        JedisConfig.maxTotal = maxTotal;
    }

    public static JedisPool getRedisPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, 1000, null);
        logger.info("JedisPool注入成功！！");
        logger.info("redis地址：" + host + ":" + port);
        return jedisPool;
    }

}
