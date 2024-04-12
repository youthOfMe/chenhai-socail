package com.chenhai.server.service.impl;

import com.chenhai.dubbo.api.QuestionApi;
import com.chenhai.dubbo.api.SettingsApi;
import com.chenhai.model.domain.Question;
import com.chenhai.model.domain.Settings;
import com.chenhai.model.vo.SettingsVo;
import com.chenhai.server.interceptor.UserHolder;
import com.chenhai.server.service.SettingsService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

@Service
public class SettiingsServiceImpl implements SettingsService {

    @DubboReference
    private QuestionApi questionApi;

    @DubboReference
    private SettingsApi settingsApi;

    /**
     * 查询通用设置
     */
    public SettingsVo settings() {
        SettingsVo vo = new SettingsVo();
        // 1. 获取用户ID
        Long userId = UserHolder.getUserId();
        vo.setId(userId);
        // 2. 获取用户的手机号码
        vo.setPhone(UserHolder.getMobile());
        // 3. 获取用户的陌生人问题
        Question question = questionApi.findByUserId(userId);
        String txt = question == null ? "你喜欢JAVA吗" : question.getTxt();
        vo.setStrangerQuestion(txt);
        // 4. 获取用户的APP通知开关数据
        Settings settings = settingsApi.findByUserId(userId);
        if (settings != null) {
            vo.setGonggaoNotification(settings.getGonggaoNotification());
            vo.setPinglunNotification(settings.getPinglunNotification());
            vo.setLikeNotification(settings.getLikeNotification());
        }
        return vo;
    }
}
