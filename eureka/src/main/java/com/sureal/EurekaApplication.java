package com.sureal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Author:ztq161024zsc
 * @PACKAGE_NAME:com.sureal
 * @DATE:2019/3/20
 * @TIME:14:36
 * @PROJECT_NAME:tensquare_parent
 * @DAY_NAME_SHORT:星期三
 * @MINUTE:36
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class, args);
    }
}
