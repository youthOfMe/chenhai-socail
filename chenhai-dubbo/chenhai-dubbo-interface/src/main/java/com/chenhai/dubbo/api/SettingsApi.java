package com.chenhai.dubbo.api;

import com.chenhai.model.domain.Settings;

public interface SettingsApi {

    /**
     * 根据用户ID查询
     * @param userId
     * @return
     */
    Settings findByUserId(Long userId);

    /**
     * 保存
     * @param settings
     */
    void save(Settings settings);

    /**
     * 更新
     * @param settings
     */
    void update(Settings settings);
}
