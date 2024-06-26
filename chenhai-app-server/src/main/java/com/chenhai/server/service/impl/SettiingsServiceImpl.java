package com.chenhai.server.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.chenhai.dubbo.api.BlackListApi;
import com.chenhai.dubbo.api.QuestionApi;
import com.chenhai.dubbo.api.SettingsApi;
import com.chenhai.model.domain.Question;
import com.chenhai.model.domain.Settings;
import com.chenhai.model.domain.UserInfo;
import com.chenhai.model.vo.PageResult;
import com.chenhai.model.vo.SettingsVo;
import com.chenhai.server.interceptor.UserHolder;
import com.chenhai.server.service.SettingsService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SettiingsServiceImpl implements SettingsService {

    @DubboReference
    private QuestionApi questionApi;

    @DubboReference
    private SettingsApi settingsApi;

    @DubboReference
    private BlackListApi blackListApi;

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

    /**
     * 通知设置
     * @param map
     */
    public void saveSettings(Map map) {
        boolean pinglunNotification = (Boolean) map.get("pinglunNotification");
        boolean likeNotification = (Boolean) map.get("likeNotification");
        boolean gonggaoNotification = (Boolean) map.get("gonggaoNotification");
        // 1. 获取当前用户ID
        Long userId = UserHolder.getUserId();
        // 2. 根据用户ID, 查询用户的通知设置
        Settings settings = settingsApi.findByUserId(userId);
        // 3. 判断
        if (settings == null) {
            // 保存
            settings = new Settings();
            settings.setUserId(userId);
            settings.setPinglunNotification(pinglunNotification);
            settings.setLikeNotification(likeNotification);
            settings.setGonggaoNotification(gonggaoNotification);
            settingsApi.save(settings);
        } else {
            settings.setPinglunNotification(pinglunNotification);
            settings.setLikeNotification(likeNotification);
            settings.setGonggaoNotification(gonggaoNotification);
            settingsApi.update(settings);
        }
    }

    /**
     * 分页查询黑名单列表
     * @param page
     * @param size
     * @return
     */
    public PageResult blacklist(int page, int size) {
        // 1. 获取当前用户的ID
        Long userId = UserHolder.getUserId();
        // 2. 调用API查询用户的黑名单分页列表 Ipage对象
        IPage<UserInfo> iPage = blackListApi.findByUserId(userId, page, size);
        // 3. 对象转换, 将查询的Ipage对象的内容封装到PageResult中
        int total = (int) iPage.getTotal();
        PageResult pr = new PageResult(page, size, total, iPage.getRecords());
        // 4. 返回
        return pr;
    }
}
