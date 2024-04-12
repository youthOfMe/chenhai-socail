package com.chenhai.server.controller;

import com.chenhai.model.vo.SettingsVo;
import com.chenhai.server.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class SettingsController {

    @Autowired
    private SettingsService settingsService;

    /**
     * 查询通用设置
     */
    @GetMapping("/settings")
    public ResponseEntity settings() {
        SettingsVo settingsVo = settingsService.settings();
        return ResponseEntity.ok(settingsVo);
    }
}
