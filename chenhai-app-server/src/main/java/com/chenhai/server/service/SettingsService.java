package com.chenhai.server.service;


import com.chenhai.model.vo.SettingsVo;

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
}
