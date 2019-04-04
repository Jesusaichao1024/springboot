package com.tensquare.spit;

import com.tensquare.spit.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @Author: ztq161024zsc
 * @DATE: 2019/3/19
 * @TIME: 17:46
 * @PROJECT_NAME: tensquare_parent
 * @PACKAGE_NAME: com.tensquare.user
 */
@Configuration
public class ApplicationConfig extends WebMvcConfigurationSupport {
    @Autowired
    private JwtFilter jwtFilter;

    /**
     * 拦截所有的请求,除了登录请求不拦截
     * @param registry
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtFilter).addPathPatterns("/**").excludePathPatterns("/**/login");
    }
}
