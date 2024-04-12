package com.chenhai.dubbo.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.chenhai.model.domain.UserInfo;

public interface BlackListApi {

    /**
     * 分页查询黑名单列表
     * @param userId
     * @param page
     * @param size
     * @return
     */
    IPage<UserInfo> findByUserId(Long userId, int page, int size);
}
