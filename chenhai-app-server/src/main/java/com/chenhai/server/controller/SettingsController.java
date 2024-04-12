package com.chenhai.server.controller;

import com.chenhai.model.vo.SettingsVo;
import com.chenhai.server.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    /**
     * 设置陌生人问题
     * @return
     */
    @PostMapping("/questions")
    public ResponseEntity questions(@RequestBody Map map) {
        // 获取参数
        String content = (String) map.get("content");
        settingsService.saveQuestion(content);
        return ResponseEntity.ok(null);
    }
}
