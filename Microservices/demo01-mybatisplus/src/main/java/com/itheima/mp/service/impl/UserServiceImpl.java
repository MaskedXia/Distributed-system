package com.itheima.mp.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.query.UserQuery;
import com.itheima.mp.mapper.UserMapperPlus;
import com.itheima.mp.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapperPlus, User> implements IUserService {

    @Override
    public void deductionBalance(Long id, Integer money) {
        User user = getById(id);
        if (user == null || user.getStatus() == 2) {
            throw new RuntimeException("用户状态异常");
        }

        if (user.getBalance() < money) {
            throw new RuntimeException("用户余额不足");
        }

//        user.setBalance(user.getBalance() - money);
//        updateById(user);
        lambdaUpdate()
                .set(User::getBalance, user.getBalance() - money)
                .set(user.getBalance() - money == 0, User::getStatus, 2)
                .eq(User::getId, id)
                .update();
    }

    @Override
    public List<User> queryUsers(UserQuery query) {
        List<User> users = lambdaQuery()
                .like(query.getName() != null, User::getUsername, query.getName())
                .eq(query.getStatus() != null, User::getStatus, query.getStatus())
                .gt(query.getMinBalance() != null, User::getBalance, query.getMinBalance())
                .lt(query.getMaxBalance() != null, User::getBalance, query.getMaxBalance())
                .list();
        return users;

    }
}
