package com.itheima.mp.controller;

import cn.hutool.core.bean.BeanUtil;
import com.itheima.mp.domain.dto.UserFormDTO;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.query.UserQuery;
import com.itheima.mp.domain.vo.UserVO;
import com.itheima.mp.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "用户管理接口")
@RequestMapping("/u1")
@RestController
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private IUserService userService;

    @ApiOperation(value = "新增用户接口")
    @PostMapping
    public void saveUser(@RequestBody UserFormDTO userDao) {
        User user = BeanUtil.copyProperties(userDao, User.class);
        userService.save(user);
    }

    @ApiOperation(value = "删除用户接口")
    @DeleteMapping ("{id}")
    public void saveUser(@ApiParam("用户id") @PathVariable("id") Long id) {
        userService.removeById(id);
    }

    @ApiOperation(value = "修改用户接口")
    @PutMapping
    public void updateUser(@RequestBody UserFormDTO userDao) {
        User user = BeanUtil.copyProperties(userDao, User.class);
        userService.updateById(user);
    }

    @ApiOperation(value = "查询单个用户接口")
    @GetMapping ("{id}")
    public UserVO getUser(@ApiParam("用户id") @PathVariable("id") Long id) {
        User user = userService.getById(id);
        return BeanUtil.copyProperties(user, UserVO.class);
    }

    @ApiOperation(value = "查询所有用户接口")
    @GetMapping ()
    public List<UserVO> getUser(@ApiParam("用户id集合") @RequestParam("ids") List<Long> ids) {
        List<User> users = userService.listByIds(ids);
        return BeanUtil.copyToList(users, UserVO.class);
    }

    @ApiOperation(value = "扣减用户余额")
    @PutMapping("/{id}/deduction/{money}")
    public void deductionBalance(
            @ApiParam("用户id") @PathVariable("id") Long id,
            @ApiParam("扣减金额") @PathVariable("money") Integer money) {
        userService.deductionBalance(id, money);
    }

    @ApiOperation(value = "复杂条件查询用户接口")
    @GetMapping ("/list")
    public List<UserVO> queryUsers(UserQuery query) {
        List<User> users = userService.queryUsers(query);
        return BeanUtil.copyToList(users, UserVO.class);
    }
}
