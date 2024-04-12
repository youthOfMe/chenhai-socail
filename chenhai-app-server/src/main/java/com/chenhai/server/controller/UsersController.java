package com.chenhai.server.controller;

import com.chenhai.model.domain.UserInfo;
import com.chenhai.model.vo.UserInfoVo;
import com.chenhai.server.interceptor.UserHolder;
import com.chenhai.server.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 查询用户资料
     *  1. 请求头: token
     *  2. 请求参数: userID
     */
    @GetMapping
    public ResponseEntity<UserInfoVo> users(@RequestHeader("Authorization") String token, Long userId) {
        // 2. 获取用户ID
        // 3. 判断用户ID
        if (userId == null) {
            userId = UserHolder.getUserId();
        }
        UserInfoVo userInfoVo = userInfoService.findById(userId);
        System.out.println(userInfoVo.getNickname());
        return ResponseEntity.ok(userInfoVo);
    }

    /**
     * 更新用户资料
     */
    @PutMapping
    public ResponseEntity updateUserInfo(@RequestBody UserInfo userInfo, @RequestHeader("Authorization") String token) {
        // 2. 获取用户ID
        // 3. 设置用户ID
        userInfo.setId(UserHolder.getUserId());
        userInfoService.update(userInfo);
        return ResponseEntity.ok(null);
    }
}
