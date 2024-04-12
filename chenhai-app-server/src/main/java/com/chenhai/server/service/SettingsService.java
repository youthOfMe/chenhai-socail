package com.chenhai.server.service;


import com.chenhai.model.vo.PageResult;
import com.chenhai.model.vo.SettingsVo;

import java.util.Map;

public interface SettingsService {
    /**
     * 查询通用设置
     */
    SettingsVo settings();

    /**
     * 设置陌生人问题
     * @param content
     */
    void saveQuestion(String content);

    /**
     * 通知设置
     * @param map
     */
    void saveSettings(Map map);

    /**
     * 分页查询黑名单列表
     * @param page
     * @param size
     * @return
     */
    PageResult blacklist(int page, int size);
}
