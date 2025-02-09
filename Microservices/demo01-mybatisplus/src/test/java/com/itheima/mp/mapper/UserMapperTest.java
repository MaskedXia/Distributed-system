package com.itheima.mp.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.itheima.mp.domain.po.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;


@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserMapperPlus userMapperPlus;

    @Test
    void testInsert() {
        User user = new User();
        user.setId(5L);
        user.setUsername("Lucy");
        user.setPassword("123");
        user.setPhone("18688990011");
        user.setBalance(200);
        user.setInfo("{\"age\": 24, \"intro\": \"英文老师\", \"gender\": \"female\"}");
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
//        userMapper.saveUser(user);
        userMapperPlus.insert(user);
    }

    @Test
    void testSelectById() {
//        User user = userMapper.queryUserById(5L);
        User user = userMapperPlus.selectById(5L);
        System.out.println("user = " + user);
    }


    @Test
    void testQueryByIds() {
//        List<User> users = userMapper.queryUserByIds(List.of(1L, 2L, 3L, 4L));
        List<User> users = userMapperPlus.selectBatchIds(List.of(1L, 2L, 3L, 4L));
        users.forEach(System.out::println);
    }

    @Test
    void testUpdateById() {
        User user = new User();
        user.setId(5L);
        user.setBalance(20000);
//        userMapper.updateUser(user);
        userMapperPlus.updateById(user);
    }

    @Test
    void testDeleteUser() {
//        userMapper.deleteUser(5L);
        userMapperPlus.deleteById(5L);
    }

    @Test
    void testWithWapper() {
        QueryWrapper<User> userQueryWapper = new QueryWrapper<User>()
                .select("id", "username", "info", "balance")
                .like("username", "o")
                .ge("balance", "1000");
        List<User> users = userMapperPlus.selectList(userQueryWapper);
        users.forEach(System.out::println);
    }

    @Test
    void testWithWapper2() {
        List<Long> ids = List.of(1L, 2L, 4L);
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<User>()
                .setSql("balance = balance - 100")
                .in("id", ids);

        userMapperPlus.update(userUpdateWrapper);
    }

    @Test
    void testWithWapper3() {
        LambdaQueryWrapper<User> userQueryWapper = new LambdaQueryWrapper<User>()
                .select(User::getId)
                .like(User::getUsername, "o")
                .ge(User::getBalance, "1000");
        List<User> users = userMapperPlus.selectList(userQueryWapper);
        users.forEach(System.out::println);
    }
}