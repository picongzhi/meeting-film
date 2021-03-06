package com.stylefeng.guns.user.config;

import com.stylefeng.guns.user.modular.auth.security.DataSecurityAction;
import com.stylefeng.guns.user.modular.auth.security.impl.Base64SecurityAction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * web配置
 *
 * @author fengshuonan
 * @date 2017-08-23 15:48
 */
@Configuration
public class WebConfig {
    @Bean
    public DataSecurityAction dataSecurityAction() {
        return new Base64SecurityAction();
    }
}
