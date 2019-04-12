package com.tensquare.user;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @Author: ztq161024zsc
 * @DATE: 2019/3/19
 * @TIME: 11:10
 * @PROJECT_NAME: tensquare_parent
 * @PACKAGE_NAME: com.tensquare.user
 * 安全配置类
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers("**").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable()
        ;
    }
}
