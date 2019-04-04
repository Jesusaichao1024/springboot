package com.sureal;

import com.sureal.client.UserClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import util.IdWorker;
import util.JwtUtil;

/**
 * @Author: ztq161024zsc
 * @PACKAGE_NAME: com.sureal
 * @DATE: 2019/3/20
 * @TIME: 16:55
 * @PROJECT_NAME: tensquare_parent
 * @DAY_NAME_SHORT: 星期三
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
public class FriendApplication {
    public static void main(String[] args) {
        SpringApplication.run(FriendApplication.class,args);
    }

    /**
     * 添加主键
     * @return
     */
    @Bean
    public IdWorker idWorker() {
        return new IdWorker(1, 1);
    }

    /**
     * 密码加密
     *
     * @return
     */
    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil();
    }
}
