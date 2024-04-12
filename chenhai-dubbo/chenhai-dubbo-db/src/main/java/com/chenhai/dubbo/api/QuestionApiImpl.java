package com.chenhai.dubbo.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chenhai.dubbo.mappers.QuestionMapper;
import com.chenhai.model.domain.Question;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

@DubboService
public class QuestionApiImpl implements QuestionApi {

    @Autowired
    private QuestionMapper questionMapper;


    public Question findByUserId(Long userId) {
        QueryWrapper<Question> qw = new QueryWrapper<>();
        qw.eq("user_id", userId);
        return questionMapper.selectOne(qw);
    }
}
