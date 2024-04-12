package com.chenhai.dubbo.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chenhai.dubbo.mappers.BlackListMapper;
import com.chenhai.dubbo.mappers.UserInfoMapper;
import com.chenhai.model.domain.UserInfo;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

@Service
public class BlackListApiImpl implements BlackListApi {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Autowired
    private BlackListMapper blackListMapper;

    /**
     * 分页查询黑名单列表
     * @param userId
     * @param page
     * @param size
     * @return
     */
    public IPage<UserInfo> findByUserId(Long userId, int page, int size) {
        // 1. 构建分页参数对象Page
        Page pages = new Page(page, size);
        // 2. 调用方法分页(自定义编写 分页参数Page sql条件参数)
        return userInfoMapper.findBlackList(pages, userId);
    }
}
