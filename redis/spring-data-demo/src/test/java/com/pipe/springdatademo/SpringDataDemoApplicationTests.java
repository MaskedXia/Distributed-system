package com.pipe.springdatademo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pipe.springdatademo.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Map;

@SpringBootTest
class SpringDataDemoApplicationTests {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testRedis() {
        redisTemplate.opsForValue().set("testKey2", "testValue2");
        System.out.println(redisTemplate.opsForValue().get("testKey2"));
    }

    @Test
    public void testRedis2() {
        redisTemplate.opsForValue().set("user:100", new User(12L, "pipe", 100));
        System.out.println(redisTemplate.opsForValue().get("user:100"));
    }


    // StringRedisTemplate 推荐
    @Test
    public void testRedis3() {
        stringRedisTemplate.opsForValue().set("testKey3", "testValue3");
        System.out.println(stringRedisTemplate.opsForValue().get("testKey3"));
    }

    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testRedis4() throws JsonProcessingException {
        User user = new User(13L, "tom", 110);
        String json = mapper.writeValueAsString(user); //手动序列化
        stringRedisTemplate.opsForValue().set("user:101", json);
        String jsonUser = stringRedisTemplate.opsForValue().get("user:101");
        User user2 = mapper.readValue(jsonUser, User.class);
        System.out.println(user2);
    }

    //hashset
    @Test
    public void testRedis5() {
        stringRedisTemplate.opsForHash().put("user:103", "name", "pipe");
        stringRedisTemplate.opsForHash().put("user:103", "age", "123");
        Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries("user:103");
        System.out.println(entries);
    }

}
