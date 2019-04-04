package com.sureal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import util.IdWorker;

/**
 * @Author Jesusaichao
 * @Date 2019/3/11
 * @Time 11:32
 * @PackageName com.sureal
 * @Project_Name tensquare_parent
 * @Description 启动类
 */
@SpringBootApplication
@EnableEurekaClient
public class BaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(BaseApplication.class, args);
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker(1L, 1L);
    }
}
