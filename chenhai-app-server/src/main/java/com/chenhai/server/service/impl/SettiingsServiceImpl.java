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

    /**
     * 设置陌生人问题
     * @param content
     */
    public void saveQuestion(String content) {
        // 1. 获取当前用户ID
        Long userId = UserHolder.getUserId();
        // 2. 调用api查询当前用户的陌生人问题
        Question question = questionApi.findByUserId(userId);
        // 3. 判断问题是否存在
        if (question == null) {
            // 3.1 如果不存在, 保存
            question = new Question();
            question.setUserId(userId);
            question.setTxt(content);
            questionApi.save(question);
        } else {
            // 3.2 如果存在, 更新
            question.setTxt(content);
            questionApi.update(question);
        }
    }
}
