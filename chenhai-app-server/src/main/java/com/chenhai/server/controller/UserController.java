package com.chenhai.server.controller;

import com.chenhai.model.domain.UserInfo;
import com.chenhai.server.interceptor.UserHolder;
import com.chenhai.server.service.UserInfoService;
import com.tanhua.commons.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 保存用户信息
     *   UserInfo
     *   请求头中携带token
     */
    @PostMapping("/loginReginfo")
    public Result loginReginfo(@RequestBody UserInfo userInfo, @RequestHeader("Authorization") String token) {
        // 2. 向userinfo中设置用户ID
        userInfo.setId(UserHolder.getUserId());
        // 3. 调用service
        userInfoService.save(userInfo);
        return Result.success();
    }

    /**
     * 上传用户头像
     */
    @PostMapping("/loginReginfo/head")
    public Result head(MultipartFile headPhoto, @RequestHeader("Authorization") String token) throws IOException {
        // 2. 向userinfo中设置用户id
        // 3. 调用service
        userInfoService.updateHead(headPhoto, UserHolder.getUserId());
        return Result.success();
    }
}
