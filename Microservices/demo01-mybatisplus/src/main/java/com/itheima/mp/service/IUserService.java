package com.itheima.mp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.query.UserQuery;

import java.util.List;

public interface IUserService extends IService<User> {

    void deductionBalance(Long id, Integer money);

    List<User> queryUsers(UserQuery query);
}
