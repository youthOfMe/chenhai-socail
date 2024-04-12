package com.chenhai.dubbo.api;

import com.chenhai.dubbo.mappers.UserInfoMapper;
import com.chenhai.model.domain.UserInfo;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

@DubboService
public class UserInfoApiImpl implements UserInfoApi {

    @Resource
    private UserInfoMapper userInfoMapper;

    public void save(UserInfo userInfo) {
        userInfoMapper.insert(userInfo);
    }

    public void update(UserInfo userInfo) {
        userInfoMapper.updateById(userInfo);
    }

    /**
     * 根据ID查询
     * @param userId
     * @return
     */
    public UserInfo findById(Long userId) {
        return userInfoMapper.selectById(userId);
    }
}
