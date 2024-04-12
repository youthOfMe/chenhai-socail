package com.chenhai.dubbo.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chenhai.dubbo.mappers.UserMapper;
import com.chenhai.model.domain.User;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

@DubboService
public class UserApiImpl implements UserApi {

    @Autowired
    private UserMapper userMapper;

    public User findByMobile(String mobile) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", mobile);
        return userMapper.selectOne(queryWrapper);
    }

    public Long save(User user) {
        userMapper.insert(user);
        return user.getId();
    }
}
