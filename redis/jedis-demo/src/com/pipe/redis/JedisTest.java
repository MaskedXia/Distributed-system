package com.pipe.redis;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

public class JedisTest {
    private Jedis jedis;

    @BeforeEach
    void JedisTest() {
        //jedis = new Jedis("192.168.80.128", 6379);
        jedis = JedisPoolTest.getJedis(); //
        jedis.auth("123456");
        jedis.select(0);
    }

    @Test
    void testConnect() throws Exception {
        String res = jedis.set("name", "pipe");
        System.out.println("result: " + res);

        String name = jedis.get("name");
        System.out.println("name = " + name);
    }

    @Test
    void testConnect2() throws Exception {
        jedis.hset("user1:1", "name", "pipe");
        jedis.hset("user1:1", "age", "100");
        System.out.println("user1:1 name=" + jedis.hget("user1:1", "name"));

    }

    @AfterEach
    void tearDown() throws Exception {
        if (jedis != null) {
            jedis.close();
        }
    }
}
