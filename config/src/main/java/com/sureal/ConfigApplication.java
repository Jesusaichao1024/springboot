package com.sureal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @Author: ztq161024zsc
 * @DATE: 2019/3/25
 * @TIME: 15:10
 * @PROJECT_NAME: tensquare_parent
 * @PACKAGE_NAME: com.sureal
 */
@SpringBootApplication
@EnableConfigServer
public class ConfigApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigApplication.class, args);
    }
}
