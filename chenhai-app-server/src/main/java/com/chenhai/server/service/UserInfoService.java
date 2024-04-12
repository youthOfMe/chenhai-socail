package com.chenhai.server.service;

import com.chenhai.model.domain.UserInfo;
import com.chenhai.model.vo.UserInfoVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserInfoService {
    /**
     * 保存用户信息数据
     * @param userInfo
     */
    void save(UserInfo userInfo);

    /**
     * 更新用户头像
     * @param headPhoto
     * @param id
     */
    void updateHead(MultipartFile headPhoto, Long id) throws IOException;

    /**
     * 根据id查询
     * @param userId
     * @return
     */
    UserInfoVo findById(Long userId);

    /**
     * 更新用户资料
     * @param userInfo
     */
    void update(UserInfo userInfo);
}
