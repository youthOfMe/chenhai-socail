package com.chenhai.server.controller;

import com.chenhai.model.vo.ErrorResult;
import com.chenhai.server.exception.BusinessException;
import com.chenhai.server.service.UserService;
import com.tanhua.commons.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 获取登录验证码
     *  请求参数: phone (Map)
     *  响应: void
     *  ResponseEntity
     */
    @PostMapping("/login")
    public Result login(@RequestBody Map map) throws Exception {
        String phone = (String) map.get("phone");
        // userService.sendMsg(phone);
        // return ResponseEntity.status(500).body("出错啦");
        // return ResponseEntity.ok(null); // 正常返回状态码200
        return userService.sendMsg(phone);
    }

    /**
     * 验证登录
     * @param map
     * @return
     */
    @PostMapping("/loginVerification")
    public ResponseEntity loginVerfication(@RequestBody Map map) {
        try {
            // 1. 调用map集合获取请求参数
            String phone = (String) map.get("phone");
            String code = (String) map.get("verificationCode");
            // 2. 调用userService完成用户登录
            Map retMap = userService.loginVerification(phone, code);
            // 3. 构造返回
            return ResponseEntity.ok(retMap);
        } catch (BusinessException be) {
            ErrorResult errorResult = be.getErrorResult();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResult);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResult.error());
        }

    }
}
