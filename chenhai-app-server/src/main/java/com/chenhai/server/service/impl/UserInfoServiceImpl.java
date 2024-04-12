package com.chenhai.server.service.impl;

import com.chenhai.autoconfig.template.AipFaceTemplate;
import com.chenhai.autoconfig.template.OssTemplate;
import com.chenhai.dubbo.api.UserInfoApi;
import com.chenhai.model.domain.UserInfo;
import com.chenhai.model.vo.ErrorResult;
import com.chenhai.model.vo.UserInfoVo;
import com.chenhai.server.exception.BusinessException;
import com.chenhai.server.service.UserInfoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @DubboReference
    private UserInfoApi userInfoApi;

    @Autowired
    private OssTemplate ossTemplate;

    @Autowired
    private AipFaceTemplate aipFaceTemplate;

    /**
     * 保存用户信息数据
     * @param userInfo
     */
    public void save(UserInfo userInfo) {
        userInfoApi.save(userInfo);
    }

    /**
     * 更新用户头像
     * @param headPhoto
     * @param id
     */
    public void updateHead(MultipartFile headPhoto, Long id) throws IOException {
        // 1. 奖图片上传到阿里云oss
        String imgUrl = ossTemplate.upload(headPhoto.getOriginalFilename(), headPhoto.getInputStream());
        System.out.println(imgUrl);
        // 2. 调用百度晕判断是否包含人脸
        boolean detect = aipFaceTemplate.detect(imgUrl);
        // 2.1 如果不包含人脸, 抛出异常
        if (!detect) {
            throw new BusinessException(ErrorResult.faceError());
        } else {
            // 2.2 包含人脸, 调用API更新
            UserInfo userInfo = new UserInfo();
            userInfo.setId(id);
            userInfo.setAvatar(imgUrl);
            userInfoApi.update(userInfo);
        }

    }

    /**
     * 根据id查询
     * @param userId
     * @return
     */
    public UserInfoVo findById(Long userId) {
        UserInfo userInfo = userInfoApi.findById(userId);
        UserInfoVo vo = new UserInfoVo();

        BeanUtils.copyProperties(userInfo, vo); // copy同名同类型的属性

        if (userInfo.getAge() != null) {
            vo.setAge(userInfo.getAge().toString());
        }

        return vo;
    }

    /**
     * 更新用户数据
     * @param userInfo
     */
    public void update(UserInfo userInfo) {
        userInfoApi.update(userInfo);
    }
}
