package com.jesusaichao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import us.codecraft.webmagic.scheduler.RedisScheduler;
import util.IdWorker;

/**
 * @Author: ztq161024zsc
 * @DATE: 2019/3/28
 * @TIME: 9:14
 * @PROJECT_NAME: tensquare_parent
 * @PACKAGE_NAME: com.jesusaichao
 */
@SpringBootApplication
@EnableScheduling
public class UserCrawlerApplication {
    @Value("${redis.host}")
    private String redis_host;
    public static void main(String[] args) {
        SpringApplication.run(UserCrawlerApplication.class, args);
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker(1, 1);
    }

    @Bean
    public RedisScheduler redisScheduler() {
        return new RedisScheduler(redis_host);
    }

}
