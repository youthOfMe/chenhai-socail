package com.chenhai.dubbo.api;

import com.chenhai.model.domain.Question;

public interface QuestionApi {

    /**
     * 根据用户ID拆线呢陌生热的问题
     * @param userId
     * @return
     */
    Question findByUserId(Long userId);

    /**
     * 保存
     * @param question
     */
    void save(Question question);

    /**
     * 更新
     * @param question
     */
    void update(Question question);
}
