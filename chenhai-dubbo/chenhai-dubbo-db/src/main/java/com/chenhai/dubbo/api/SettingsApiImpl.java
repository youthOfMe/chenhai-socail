package com.chenhai.dubbo.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chenhai.dubbo.mappers.SettingsMapper;
import com.chenhai.model.domain.Settings;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

@DubboService
public class SettingsApiImpl implements SettingsApi {

    @Autowired
    private SettingsMapper settingsMapper;

    /**
     * 根据用户ID查询
     * @param userId
     * @return
     */
    public Settings findByUserId(Long userId) {
        QueryWrapper<Settings> qw = new QueryWrapper<>();
        qw.eq("user_id", userId);
        return settingsMapper.selectOne(qw);
    }

    /**
     * 保存
     * @param settings
     */
    public void save(Settings settings) {
        settingsMapper.insert(settings);
    }

    /**
     * 更新
     * @param settings
     */
    public void update(Settings settings) {
        settingsMapper.updateById(settings);
    }
}
