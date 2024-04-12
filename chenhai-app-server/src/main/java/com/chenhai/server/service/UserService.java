package com.chenhai.server.service;

import com.chenhai.autoconfig.template.SmsTemplate;
import com.chenhai.dubbo.api.UserApi;
import com.chenhai.model.vo.ErrorResult;
import com.chenhai.server.exception.BusinessException;
import com.tanhua.commons.result.Result;
import com.tanhua.commons.utils.JwtUtils;
import com.chenhai.model.domain.User;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private SmsTemplate template;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @DubboReference
    private UserApi userApi;

    /**
     * 发送短信验证码
     * @param phone
     */
    public Result sendMsg(String phone) throws Exception {
        // 1. 随机生成6位数字
        String code = RandomStringUtils.randomNumeric(6);
        String key = "CHECK_CODE" + phone;
        String value = redisTemplate.opsForValue().get(key);
        if (value != null) {
            return Result.error("获取验证码太频繁了！！！");
        }
        // 2. 调用template对象, 发送手机对象
        template.sendSms(phone, code);
        // 3. 讲验证码存入到redis
        redisTemplate.opsForValue().set("CHECK_CODE_" + phone, code, Duration.ofMinutes(5));
        return Result.success();
    }

    /**
     * 验证登录
     * @param phone
     * @param code
     * @return
     */
    public Map loginVerification(String phone, String code) {
        String key = "CHECK_CODE" + phone;
        // 1. 从redis中获取下发的验证码
        String redisCode = redisTemplate.opsForValue().get("CHECK_CODE_" + phone);
        // 2. 对验证码进行校验(验证码是否存在, 是否和输入的验证码一致)
        if (StringUtils.isEmpty(redisCode) || !redisCode.equals(code)) {
            // 验证码无效
            throw new BusinessException(ErrorResult.loginError());
        }
        // 3. 删除redis中的验证码
        redisTemplate.delete(key);
        // 4. 通过手机号码查询用户
        User user = userApi.findByMobile(phone);
        boolean isNew = false;
        // 5. 如果用户不存在, 创建用户保存到数据库中
        if (user == null) {
            isNew = true;
            user = new User();
            user.setMobile(phone);
            user.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
            Long userId = userApi.save(user);
            user.setId(userId);
        }
        // 6. 通过JWT生成token(存入id和手机密码)
        Map tokenMap = new HashMap();
        tokenMap.put("id", user.getId());
        tokenMap.put("mobile", phone);
        String token = JwtUtils.getToken(tokenMap);
        // 7. 构造返回值
        Map retMap = new HashMap();
        retMap.put("token", token);
        retMap.put("isNew", isNew);

        return retMap;
    }
}
