package com.chenhai.autoconfig;


import com.chenhai.autoconfig.properties.AipFaceProperties;
import com.chenhai.autoconfig.properties.OssProperties;
import com.chenhai.autoconfig.properties.SmsProperties;
import com.chenhai.autoconfig.template.AipFaceTemplate;
import com.chenhai.autoconfig.template.OssTemplate;
import com.chenhai.autoconfig.template.SmsTemplate;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties({
        SmsProperties.class,
        OssProperties.class,
        AipFaceProperties.class
})
public class TanhuaAutoConfiguration {

    @Bean
    public SmsTemplate smsTemplate(SmsProperties properties) {
        return new SmsTemplate(properties);
    }

    @Bean
    public OssTemplate ossTemplate(OssProperties ossProperties) {
        return new OssTemplate(ossProperties);
    }

    @Bean
    public AipFaceTemplate apiFaceTemplate() {
        return new AipFaceTemplate();
    }
}
