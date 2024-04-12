package com.chenhai.dubbo.api;

import com.chenhai.model.domain.UserInfo;

public interface UserInfoApi {

    public void save(UserInfo userInfo);

    public void update(UserInfo userInfo);

    /**
     * 根据id查询
     * @param userId
     * @return
     */
    UserInfo findById(Long userId);
}
