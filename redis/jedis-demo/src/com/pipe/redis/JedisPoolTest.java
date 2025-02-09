package com.pipe.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolTest {

    //连接池
    private static final JedisPool jedisPool;

    static{
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(8);  //最大连接
        poolConfig.setMaxIdle(8);
        poolConfig.setMinIdle(0);  //空闲最大最小连接
        poolConfig.setMaxWaitMillis(1000);
        jedisPool = new JedisPool(poolConfig, "192.168.80.128",
                6379, 1000, "123456");

    }

    public static Jedis getJedis(){
        return jedisPool.getResource();
    }
}
