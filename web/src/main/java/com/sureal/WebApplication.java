package com.sureal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @Author: ztq161024zsc
 * @DATE: 2019/3/21
 * @TIME: 18:56
 * @PROJECT_NAME: tensquare_parent
 * @PACKAGE_NAME: com.sureal
 */
@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
